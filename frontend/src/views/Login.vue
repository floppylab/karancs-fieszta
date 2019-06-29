<template>
  <div v-if="loginError">
    nope
  </div>
  <div v-else>
    <form @submit.prevent="callLogin()">
      <input type="text" placeholder="username" v-model="username"><br>
      <input type="password" placeholder="password" v-model="password"><br>
      <button type="submit">Login</button>
      <p v-if="error" class="error">Bad login information</p>
    </form>
  </div>

</template>

<script>
export default {
  name: 'login',

  data () {
    return {
      loginError: false,
      username: '',
      password: '',
      error: false,
      errors: []
    }
  },
  methods: {
    callLogin() {
      this.errors = [];
      this.$store.dispatch("login",
        { username: this.username,
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