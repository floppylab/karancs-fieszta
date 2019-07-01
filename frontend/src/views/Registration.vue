<template>
    <section>
        <div>
            <h2>regisztráció</h2>
            <p v-if="duplicationError" class="error">már létezik ilyen felhasználónév!</p>
            <p v-if="otherError" class="error">hiba történt!</p>
            <form @submit.prevent="callRegistration()">
                <div>
                    <label for="username">felhasználónév</label>
                    <input type="text" id="username" v-model="username" min="4" required>
                </div>
                <div>
                    <label for="password">jelszó</label>
                    <input type="password" id="password" v-model="password" minlength="8" required>
                </div>
                <div>
                    <label for="password">jelszó még egyszer</label>
                    <input type="password" id="password2" v-model="password2" minlength="8" required>
                </div>
                <button :disabled="registrationDisabled" type="submit">regisztráció</button>
            </form>
        </div>
    </section>

</template>

<script>
  import {api} from '../api'

  export default {
    name: 'Registration',
    data() {
      return {
        username: '',
        password: '',
        password2: '',
        duplicationError: false,
        otherError: false
      }
    },
    computed: {
      registrationDisabled() {
        return this.password !== this.password2
      }
    },
    methods: {
      callRegistration() {
        if (this.registrationDisabled) return;
        api.get('/users', { params: { username: this.username } })
          .then((response) => {
            console.log(response.data)
            if(response.data !== true) {
              api.post('/users', {
                username: this.username,
                password: this.password
              })
                .then(() => {
                  this.$router.push('/login?successfulRegistration=true')
                })
                .catch(error => {
                  this.otherError = true
                })
            } else {
              this.duplicationError = true
            }
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