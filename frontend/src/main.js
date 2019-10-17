import Vue from 'vue'
import App from '@/App'
import router from '@/lib/router'
import store from '@/lib/store'
import axios from 'axios'
import VueAxios from 'vue-axios'
import moment from 'vue-moment'
 
Vue.use(moment)
Vue.use(VueAxios, axios)
Vue.config.productionTip = false

Vue.prototype.$axios = axios

Vue.mixin({
  methods : {
    navigate(path) {
      this.$router.push(path)
    }
  }
})

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  store,
  created: function () {
    var token = this.getCookie('crawler-access-key');
    if(token != null){
      this.$store.commit('loginAfter',token);
    }
  },
  methods : {
    getCookie(name) {
      var v = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
      return v ? v[2] : null;
    }
  }
})
