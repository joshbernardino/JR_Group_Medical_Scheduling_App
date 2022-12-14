package com.techelevator.controller;


import com.techelevator.dao.AppointmentsDao;
import com.techelevator.dao.JdbcAppointmentsDao;
import com.techelevator.model.Appointments;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@CrossOrigin(origins = "http://localhost:8080")
@PreAuthorize("isAuthenticated()")
public class AppointmentsController {
    private AppointmentsDao appointmentsDao;

    public AppointmentsController(DataSource dataSource) {
        this.appointmentsDao = new JdbcAppointmentsDao(dataSource);
    }

    @PreAuthorize("permitAll()")
    //do we need a preauthorize for user? or just general access for reviews
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Appointments getApptByApptId(@PathVariable int id) {
        return appointmentsDao.getApptById(id);
    }

    @RequestMapping(path = "/doctor/{username}", method = RequestMethod.GET)
    public List<Appointments> getAllBookedApptsByDoctorList(@PathVariable String username, Principal principal) {
        return appointmentsDao.getAllBookedApptsByDoctor(username);
    }

    @RequestMapping(path = "/patient-booked/{username}", method = RequestMethod.GET)
    public List<Appointments> getAllBookedApptsByPatientList(@PathVariable String username, Principal principal) {
        return appointmentsDao.getAllBookedApptsByPatient(username);
    }

    @RequestMapping(path = "/doctor/{id}/date/{date}", method = RequestMethod.GET)
    public List<Appointments> apptsByDoctorAndDate(@PathVariable int id, @PathVariable String date) {
        return appointmentsDao.getApptsByDoctorAndDate(id, date);
    }

    @PreAuthorize("permitAll()")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Appointments> getAllAvailableApptsList() {
        return appointmentsDao.getAllAvailableAppts();
    }

    @RequestMapping(path = "/doctor/notifications", method = RequestMethod.GET)
    public List<Appointments> getUnreadApptsList() {
        return appointmentsDao.getUnreadAppts();
    }

    @RequestMapping(path = "/new-appointment", method = RequestMethod.POST)
    public void createNewAppt(@Valid @RequestBody Appointments appointment) {
        appointmentsDao.createAppt(appointment);
    }

    @RequestMapping(path = "/{apptId}/mark-read", method = RequestMethod.PUT)
    public void markRead(@PathVariable int apptId) {
        appointmentsDao.markApptRead(apptId);
    }

}
