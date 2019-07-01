<template>
    <section>
        <div v-if="loginError">
            Hiba!
        </div>
        <div v-else>
            <h2>bejelentkezés</h2>
            <p v-if="isRegistrationSuccessful">sikeres regisztráció! lépjen be!</p>
            <p v-if="isLogoutSuccessful">sikeres kilépés!</p>
            <p v-if="error" class="error">hibás adatok!</p>
            <form @submit.prevent="callLogin()">
                <div>
                    <label for="username">felhasználónév</label>
                    <input type="text" id="username" v-model="username" required>
                </div>
                <div>
                    <label for="password">jelszó</label>
                    <input type="password" id="password" v-model="password" required>
                </div>
                <button type="submit">bejelentkezés</button>

            </form>
        </div>
    </section>

</template>

<script>
  import {api} from '../api'

  export default {
    name: 'Login',
    data() {
      return {
        loginError: false,
        username: '',
        password: '',
        error: false,
        errors: []
      }
    },
    computed: {
      isRegistrationSuccessful () {
        return this.$route.query.successfulRegistration
      },
      isLogoutSuccessful () {
        return this.$route.query.successfulLogout
      }
    },
    created() {
      if(this.isLogoutSuccessful) {
        this.$store.commit('logout')
        window.$cookies.remove("karancs-fieszta-token")
        window.$cookies.remove("karancs-fieszta-user")
      }
    },
    methods: {
      callLogin() {
        this.errors = [];
        this.$store.dispatch("login",
          {
            username: this.username,
            password: this.password
          })
          .then(() => {
            this.$router.push('/')
          })
          .catch(error => {
            this.loginError = true;
            this.errors.push(error);
            this.error = true
          })
      }
    }
  }
</script>

<style scoped>

    section {
        padding: 20px;
    }

    h2 {
        padding: 20px 0;
    }

    label {
        display: block;
        font-size: 14px;
        margin-top: 0.5rem
    }

    input {
        border: none;
        padding: 3px;
    }

    button {
        border: none;
        background-color: #ffd152;
        margin: 20px;
        padding: 3px 20px;
    }

    button:hover {
        background-color: #ffad2d;
    }

    .error {
        color: #fc8c84;
    }
</style>