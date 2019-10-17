import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Signin from '@/components/Signin'
import List from '@/components/List'

import Item from '@/components/Item'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/signin',
      name: 'Signin',
      component: Signin
    },
    {
      path: '/list',
      name: 'List',
      component: List
    },
    
    
    
    {
      path: '/item',
      name: 'Item',
      component: Item
    }
  ],
  mode : 'history'
})