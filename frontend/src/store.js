import Vue from 'vue'
import Vuex from 'vuex'
import {api} from './api'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    username: null
  },
  mutations: {
    loginSuccess (state, payload) {
      state.loginSuccess = true
      state.username = payload.username
    },
    logout (state) {
      state.username = null
    }
  },
  actions: {
    login({commit}, {username, password}) {
      return new Promise((resolve, reject) => {
        api.post(`/authenticate?username=${username}&password=${password}`)
          .then(response => {
            console.log(response)
            console.log("response: '" + response + "' with statuscode " + response.status)
            if (response.status == 200) {
              api.defaults.headers.common['Authorization'] = response.headers.authorization
              console.log(api.defaults.headers.common['Authorization'])
              window.$cookies.set("karancs-fieszta-token", response.headers.authorization, '10d')
              window.$cookies.set("karancs-fieszta-user", username, '10d')
              commit('loginSuccess', {
                username: username
              })
            }
            resolve(response)
          })
          .catch(error => {
            console.log("error: " + error);
            reject("Invalid credentials!")
          })
      })
    }
  },
  getters: {
    isLoggedIn: state => state.username ? true : false,
    username: state => state.username
  }
})