<template>
    <header>
        <h1 id="title">Karancs Fieszta</h1>
        <nav>
            <div v-if="loggedIn"><router-link to="/editor">új animáció</router-link></div>
            <div v-if="!loggedIn"><router-link to="/registration">regisztráció</router-link></div>
            <div v-if="!loggedIn"><router-link to="/login">bejelentkezés</router-link></div>
            <div v-if="loggedIn"><router-link to="/login?successfulLogout=true">kijelentkezés</router-link></div>
            <div><a href="https://www.facebook.com/events/2049310492032209/" target="_blank"><font-awesome-icon :icon="['fab', 'facebook-square']" /></a></div>
        </nav>
    </header>
</template>

<script>
  export default {
    name: 'TheHeader',
    computed: {
      loggedIn () {
        return this.$store.getters.isLoggedIn
      }
    }
  }
</script>

<style scoped>

    header {
        width: 100%;
        max-width: 1000px;
        margin: 0 auto;
        display: flex !important;
        flex-wrap: wrap;
        justify-content: space-between;
        align-items: center;
        padding: 20px;
    }

    nav {
        display: flex !important;
    }

    nav div {
        padding: 10px 20px;
    }

    nav div:last-child {
        padding-right: 0;
    }

    #title {
        text-transform: lowercase;
        font-family: Barriecito, sans-serif;
        letter-spacing: 1px;
        padding: 20px 0;
    }

    @media screen and (max-width: 600px) {

        header {
            padding: 20px 0;
        }

        header, header nav {
            justify-content: center;
        }

        header > * {
            width: 100%;
        }
    }

</style>