<template>

  <div id="app">
      <head>
        <link href="https://fonts.googleapis.com/css?family=Crimson+Text|Work+Sans:400,700" rel="stylesheet">
      </head>
    <div id="header">
      <img src="../public/clearLogo.png" alt="Medical App logo" id="logo" />
      <p id="header-text"> MEDICAL SCHEDULING </p>
      <img src="../public/jr-logo.jpg" alt="J R Group logo" id="jr-group" />
    </div>

  
      
    <div id="nav">
      <ul>
        <li><router-link v-bind:to="{ name: 'home' }">Home
          <img src="../public/home_alt.png" alt="stethoscope image" class="dr-logo"/>
          </router-link></li>
      </ul>
      <userNav v-if="$store.state.token !== ''" />
      <img src="../public/never-miss.jpg" alt="Never Miss Appt" id="never-miss" />
    </div>

    <div id="main">
      <div id="logout"><router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout {{this.$store.state.user.firstName}} {{this.$store.state.user.lastName}}</router-link></div>
      <router-view />
      <!-- <img src="../public/clearLogo.png" alt="Medical App Logo" id="logo-clear" /> -->
    </div>

  </div>
</template>

<script>
import userNav from '@/views/Nav.vue';

export default {
  components: {
    userNav
  },
  methods: {
  }
}

</script>

<style>
html, body, #app {
  min-height: 100vh;
  padding: 0;
  margin: 0;
}

#app {
  display: grid;
  grid-template-columns: 250px 1fr;
  grid-template-rows: 100px 1fr;
  grid-template-areas: 
  "header header"
  "nav main";
  font-family: Arial, Helvetica, sans-serif;
}

#header {
  width:100%;
  /* padding-top: 10px;
  padding-bottom: 10px; */
  background-image: linear-gradient(to right, rgba(2,48,71,0.25), rgba(2,48,71,1));
  grid-area: header;
  display: grid;
  grid-template-columns: .65fr 1fr 312.53px;
}


#logo {
  width: 90px;
  padding-left: 80px;
  padding-top: 5px;
  vertical-align: bottom;
}

#logo-clear{
  margin-top: -20px;
  opacity: 1;
}

 #main {
 background-image: url("../public/medpage-opa.png");
 background-repeat: no-repeat;
 background-position: top;
 background-size: 100%;
background-color: #F2F0EF;
}

#jr-group {
  height: 100px;
  text-align: right;
}

#never-miss {
  width: 247.5px;
  padding-top: 10px;
}

#header-text {
  display: block;
  padding-top: -30px;
  margin-top: 18px;
  margin-bottom: 10px;
  max-height: 100px;
  font-size: 50px;
  color: white;
  font-family: 'Crimson Text', serif;
  
  
}

#nav {
  grid-area: nav;
  border-right: solid 1px rgb(194, 205, 229);
  height: 100%;
  background-color: #27A2bf;
}

#nav ul {
  list-style-type: none;
  font-size: 20px;
  padding:0 0 0 0;
  margin: 0;
}

#nav ul li {
  text-align: center;
}

#main {
  position: relative;
  grid-area: main;
  padding:20px;
}

#logout {
  position: absolute;
  right: 10px;
  top: 10px;
}

@media only screen and (max-width: 600px) {

  #nav {
    text-align: center;
  }

  #main {
    padding: 5px 20px;
  }

}

</style>