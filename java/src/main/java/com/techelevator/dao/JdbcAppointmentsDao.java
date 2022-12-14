package com.techelevator.dao;

import com.techelevator.model.Appointments;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JdbcAppointmentsDao implements AppointmentsDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAppointmentsDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Appointments getApptById(int apptId) {
        Appointments appointment = null;
        String sql = "SELECT a.appt_id, a.doctor_id, du.first_name ||' '|| du.last_name AS doctor_name, a.patient_id, pu.first_name ||' '|| pu.last_name AS patient_name, a.appt_date, a.appt_time, a.purpose_of_visit, a.is_read, a.is_available " +
                "FROM appointments a " +
                "JOIN users du " +
                "ON a.doctor_id = du.user_id " +
                "JOIN users pu " +
                "ON a.patient_id = pu.user_id " +
                "WHERE appt_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, apptId);
        if(result.next()) {
            appointment = mapRowToAppointments(result);
        }
        return appointment;
    }

    @Override
    public List <Appointments> getAllBookedApptsByDoctor(String userName) {
        List <Appointments> appointments = new ArrayList<>();

        String sql = "SELECT a.appt_id, a.doctor_id, du.first_name ||' '|| du.last_name AS doctor_name, a.patient_id, pu.first_name ||' '|| pu.last_name AS patient_name, a.appt_date, a.appt_time, a.purpose_of_visit, a.is_read, a.is_available " +
                "FROM appointments a " +
                "JOIN users du " +
                "ON a.doctor_id = du.user_id " +
                "JOIN users pu " +
                "ON a.patient_id = pu.user_id " +
                "WHERE du.username = ? " +
                "ORDER BY appt_date ASC, appt_time ASC;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userName);
        while (results.next()) {
            Appointments appointment = mapRowToAppointments(results);
            appointments.add(appointment);
        }
        return appointments;
    }

    @Override
    public List<Appointments> getAllBookedApptsByPatient(String username){
        List<Appointments> appointments = new ArrayList<>();
        String sql = "SELECT a.appt_id, a.doctor_id, du.first_name ||' '|| du.last_name AS doctor_name, a.patient_id, pu.first_name ||' '|| pu.last_name AS patient_name, a.appt_date, a.appt_time, a.purpose_of_visit, a.is_read, a.is_available " +
                "FROM appointments a " +
                "JOIN users du " +
                "ON a.doctor_id = du.user_id " +
                "JOIN users pu " +
                "ON a.patient_id = pu.user_id " +
                "WHERE pu.username = ? AND is_available = false " +
                "ORDER BY appt_date ASC, appt_time ASC;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        while (results.next()) {
            Appointments appointment = mapRowToAppointments(results);
            appointments.add(appointment);
        }
        return appointments;
    }

    @Override
    public List <Appointments> getAllAvailableApptsByDoctor(int userId) {
        List <Appointments> appointments = new ArrayList<>();
        String sql = "SELECT a.appt_id, a.doctor_id, du.first_name ||' '|| du.last_name AS doctor_name, a.patient_id, pu.first_name ||' '|| pu.last_name AS patient_name, a.appt_date, a.appt_time, a.purpose_of_visit, a.is_read, a.is_available " +
                "FROM appointments a " +
                "JOIN users du " +
                "ON a.doctor_id = du.user_id " +
                "JOIN users pu " +
                "ON a.patient_id = pu.user_id " +
                "WHERE doctor_id = ? AND is_available = true;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            Appointments appointment = mapRowToAppointments(results);
            appointments.add(appointment);
        }
        return appointments;
    }

    @Override
    public List <Appointments> getApptsByDoctorAndDate(int doctorId, String date) {
        List <Appointments> appointments = new ArrayList<>();
        String sql = "SELECT a.appt_id, a.doctor_id, du.first_name ||' '|| du.last_name AS doctor_name, a.patient_id, pu.first_name ||' '|| pu.last_name AS patient_name, a.appt_date, a.appt_time, a.purpose_of_visit, a.is_read, a.is_available " +
                "FROM appointments a " +
                "JOIN users du " +
                "ON a.doctor_id = du.user_id " +
                "JOIN users pu " +
                "ON a.patient_id = pu.user_id " +
                "WHERE doctor_id = ? AND appt_date = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, doctorId, date);
        while (results.next()) {
            Appointments appointment = mapRowToAppointments(results);
            appointments.add(appointment);
        }
        return appointments;
    }

    @Override
    public List<Appointments> getAllAvailableAppts() {
        List <Appointments> appointments = new ArrayList<>();
        String sql = "SELECT a.appt_id, a.doctor_id, du.first_name ||' '|| du.last_name AS doctor_name, " +
                "a.patient_id, pu.first_name ||' '|| pu.last_name AS patient_name, " +
                "a.appt_date, a.appt_time, a.purpose_of_visit, a.is_read, a.is_available " +
                "FROM appointments a " +
                "JOIN users du " +
                "ON a.doctor_id = du.user_id " +
                "JOIN users pu " +
                "ON a.patient_id = pu.user_id " +
                "WHERE is_available = true;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Appointments appointment = mapRowToAppointments(results);
            appointments.add(appointment);
        }
        return appointments;
    }

    @Override
    public void createAppt(Appointments appointment) {
        String sql = "INSERT INTO appointments (doctor_id, patient_id, appt_date, appt_time, purpose_of_visit, is_read, is_available) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql, appointment.getDoctorId(), appointment.getPatientId(),
                appointment.getApptDate(), appointment.getApptTime(), appointment.getPurposeOfVisit(), appointment.isRead(),
                appointment.isAvailable());
        }

    @Override
    public List<Appointments> getUnreadAppts() {
        List <Appointments> appointments = new ArrayList<>();
        String sql = "SELECT a.appt_id, a.doctor_id, du.first_name ||' '|| du.last_name AS doctor_name, " +
                "a.patient_id, pu.first_name ||' '|| pu.last_name AS patient_name, " +
                "a.appt_date, a.appt_time, a.purpose_of_visit, a.is_read, a.is_available " +
                "FROM appointments a " +
                "JOIN users du " +
                "ON a.doctor_id = du.user_id " +
                "JOIN users pu " +
                "ON a.patient_id = pu.user_id " +
                "WHERE is_read = false;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Appointments appointment = mapRowToAppointments(results);
            appointments.add(appointment);
        }
        return appointments;
    }

    @Override
    public void markApptRead(int apptId) {
        String sql = "UPDATE appointments " +
                "SET is_read = true " +
                "WHERE appt_id = ?;";
        jdbcTemplate.update(sql, apptId);
    }

    private Appointments mapRowToAppointments(SqlRowSet rs) {
        Appointments appointment = new Appointments();
        appointment.setApptId(rs.getInt("appt_id"));
        appointment.setDoctorId(rs.getInt("doctor_id"));
        appointment.setDoctorName(rs.getString("doctor_name"));
        appointment.setPatientId(rs.getInt("patient_id"));
        appointment.setPatientName(rs.getString("patient_name"));
        appointment.setApptDate(rs.getString("appt_date"));
        appointment.setApptTime(rs.getTime("appt_time"));
        appointment.setPurposeOfVisit(rs.getString("purpose_of_visit"));
        appointment.setRead(rs.getBoolean("is_read"));
        appointment.setAvailable(rs.getBoolean("is_available"));
        return appointment;
    }

}
