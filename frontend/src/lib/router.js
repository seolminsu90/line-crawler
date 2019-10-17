import Vue from 'vue'
import Router from 'vue-router'
import User from '@/components/User'
import Item from '@/components/Item'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/user',
      name: 'User',
      component: User
    },
    {
      path: '/item',
      name: 'Item',
      component: Item
    }
  ]
})