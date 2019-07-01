import axios from 'axios'

export const api = axios.create({
  baseURL: `/api`,
  timeout: 10000
});

//
// export default {
//     hello() {
//         return AXIOS.get(`/hello`);
//     },
//     getUser(userId) {
//         return AXIOS.get(`/user/` + userId);
//     },
//     createUser(firstName, lastName) {
//         return AXIOS.post(`/user/` + firstName + '/' + lastName);
//     },
//     getSecured(user, password) {
//         return AXIOS.get(`/secured/`,{
//             auth: {
//                 username: user,
//                 password: password
//             }});
//     }
// }
//

