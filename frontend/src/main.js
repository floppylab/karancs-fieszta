import Vue from 'vue'
import App from './App.vue'
import router from './router'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import store from './store'
import {library} from '@fortawesome/fontawesome-svg-core'
import {
  faAngleDoubleLeft,
  faAngleDoubleRight,
  faAngleLeft,
  faAngleRight,
  faHeart,
  faPencilAlt,
  faTrashAlt,
  faCopy,
  faDownload,
  faSyncAlt,
  faPlus,
  faSave
} from '@fortawesome/free-solid-svg-icons'
import {faFacebookSquare} from '@fortawesome/free-brands-svg-icons'
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'

import VuejsDialog from 'vuejs-dialog';
import 'vuejs-dialog/dist/vuejs-dialog.min.css';

import VueCookies from 'vue-cookies'

Vue.use(VueCookies)

Vue.use(VuejsDialog)

const moment = require('moment')
Vue.use(require('vue-moment'), {
  moment
})

library.add(faHeart)
library.add(faAngleDoubleLeft)
library.add(faAngleLeft)
library.add(faAngleDoubleRight)
library.add(faAngleRight)
library.add(faTrashAlt)
library.add(faPencilAlt)
library.add(faFacebookSquare)
library.add(faCopy)
library.add(faDownload)
library.add(faSyncAlt)
library.add(faPlus)
library.add(faSave)
Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.use(BootstrapVue);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');

