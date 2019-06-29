import Vue from 'vue'
import Vuex from 'vuex'
import {api} from './api'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    username: null
  },
  mutations: {
    loginSuccess(state, payload) {
      state.loginSuccess = true
      state.username = payload.username
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
              api.defaults.headers.common['Authorization'] = `Bearer ${response.headers.authorization}`
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