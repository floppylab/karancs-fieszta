import axios from 'axios'
import router from './router'

export const api = axios.create({
  baseURL: `/api`,
  timeout: 10000
});

api.interceptors.response.use(function (response) {
  return response
}, function (error) {
  console.log(error)
  // if (403 === error.response.status) {
  //   this.$router.push('/login?accessDenied=true')
  // }
  return Promise.reject(error)
})

