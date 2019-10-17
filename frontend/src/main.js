import Vue from 'vue'
import App from '@/App'
import router from '@/lib/router'
import store from '@/lib/store'

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  store
})
