import Vue from 'vue'
import Router from 'vue-router'
// import Hello from '@/components/Hello'
// import Service from '@/components/Service'
// import Bootstrap from '@/components/Bootstrap'
// import User from '@/components/User'

import Home from '@/views/Home'
import Login from '@/views/Login'
import Registration from '@/views/Registration'
import Editor from '@/views/Editor'

import {api} from './api'

import store from './store'

Vue.use(Router);

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/registration',
      name: 'Registration',
      component: Registration
    },
    {
      path: '/editor',
      name: 'Editor',
      component: Editor,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '*',
      redirect: '/'
    }
  ]
});

router.beforeEach((to, from, next) => {
  if (window.$cookies.get("karancs-fieszta-token")) {
    api.defaults.headers.common['Authorization'] = `${window.$cookies.get("karancs-fieszta-token")}`
    store.commit('loginSuccess', {
      username: window.$cookies.get("karancs-fieszta-user")
    })
  } else {
    api.defaults.headers.common['Authorization'] = ''
    store.commit('logout')
    window.$cookies.remove("karancs-fieszta-token")
    window.$cookies.remove("karancs-fieszta-user")
  }

  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!store.getters.isLoggedIn) {
      next({
        path: '/login'
      })
    } else {
      next();
    }
  } else {
    next(); // make sure to always call next()!
  }
});

export default router;