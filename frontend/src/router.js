import Vue from 'vue'
import Router from 'vue-router'
// import Hello from '@/components/Hello'
// import Service from '@/components/Service'
// import Bootstrap from '@/components/Bootstrap'
// import User from '@/components/User'

import Home from '@/views/Home'
import Login from '@/views/Login'
import Editor from '@/views/Editor'

import store from './store'

Vue.use(Router);

const router = new Router({
  mode: 'history', // uris without hashes #, see https://router.vuejs.org/guide/essentials/history-mode.html#html5-history-mode
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    // {path: '/callservice', component: Service},
    // {path: '/bootstrap', component: Bootstrap},
    // {path: '/user', component: User},
    // {path: '/login', component: Login},
    {
      path: '/editor',
      name: 'editor',
      component: Editor
    },
    {
      path: '*',
      redirect: '/'
    }
  ]
});

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // this route requires auth, check if logged in
    // if not, redirect to login page.
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