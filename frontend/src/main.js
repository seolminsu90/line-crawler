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
    },
    logOut(){
      this.deleteCookie('crawler-access-key')
      this.$store.commit('logout')
      this.navigate("/")
    },
    deleteCookie(name) {
      document.cookie = name + '=; expires=Thu, 01 Jan 1999 00:00:10 GMT;';
    },
    deleteUser(){
      if(!confirm("진심으로 탈퇴하시겠습니까? 복구는 이루어지지 않습니다.")){
        return;
      }
      var $this = this;
      this.$axios.delete("/api/users", {
        headers: {
          Authorization: 'Bearer ' + $this.$store.state.token
        }
      }).then((response) => {
       var result = response.data;
       if(result.code == "0000"){
         alert("탈퇴하셨습니다. 그동안 감사했습니다. 수고하세요.");
         this.logOut();
       } else if(result.code == "0104" || result.code == "0200"){
         return alert("인증 정보 오류.");
       } else {
         return alert("오류");
       }
     }).catch(err => {
       console.log(err.response);
       if(err.response.status == 400){
         return alert("오류");
       }
     })
      
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
