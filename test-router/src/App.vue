<template>
 <vaadin-app-layout primary-section="drawer">
    <vaadin-drawer-toggle slot="navbar"></vaadin-drawer-toggle>
    <h1 slot="navbar">Dashboard</h1>
    <vaadin-tabs slot="drawer" orientation="vertical">
      <vaadin-tab>
        <a tabindex="-1">
          <vaadin-icon icon="vaadin:dashboard"></vaadin-icon>
          <span>Dashboard</span>
        </a>
      </vaadin-tab>
      <vaadin-tab>
        <a tabindex="-1">
          <vaadin-icon icon="vaadin:cart"></vaadin-icon>
          <span>Orders</span>
        </a>
      </vaadin-tab>
      <vaadin-tab>
        <a tabindex="-1">
          <vaadin-icon icon="vaadin:user-heart"></vaadin-icon>
          <span>Customers</span>
        </a>
      </vaadin-tab>
    </vaadin-tabs>
  </vaadin-app-layout>
  
 <!-- <div id="nav">
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link>
  </div>
  <router-view/>-->
</template>

<script>
import "@vaadin/app-layout";
import "@vaadin/app-layout/vaadin-drawer-toggle";
import "@vaadin/icon";
import "@vaadin/icons";
import "@vaadin/tabs";

export default {
  name: "App",
  components: {},
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
