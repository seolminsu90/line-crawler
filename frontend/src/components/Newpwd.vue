  
<template>
  <section id="login">
    <section id="loginWrap">
      <h2>LINE SQUARE</h2>
      <div>
        <p>
          <input placeholder="현재 비밀번호 (8자 이상)" maxlength="30" v-model.trim="user.pwd" id="pwd" type="password"/>
        </p>
        <p>
          <input placeholder="새로운 비밀번호 (8자 이상)" maxlength="30" v-model.trim="user.pwdNew" id="pwdNew" type="password"/>
        </p>
        <p>
          <input placeholder="새로운 비밀번호 재입력 (8자 이상)" maxlength="30" v-model.trim="user.pwdRe" id="pwdRe" type="password"/>
        </p>
        <button type="button" @click="update">비밀번호 변경</button>
        <div class="otherAction">
          <span @click="navigate('/list')">취소</span>
        </div>
      </div>
    </section>
  </section>
</template>

<script>
export default {
  name: 'pwdNew',
  data () {
    return {
      user : {
        pwd : "",
        pwdNew : "",
        pwdRe : ""
      },
      userReset : {
        pwd : "",
        pwdNew : "",
        pwdRe : ""
      }
    }
  },
  methods : {
    update (){
      var $this = this;
      if(this.user.pwdNew !== this.user.pwdRe){
        return alert("비밀번호 확인이 일치하지 않습니다.");
      }
      this.$axios.put("/api/users", this.user,{
         headers: {
           Authorization: 'Bearer ' + $this.$store.state.token
         }
       }).then((response) => {
        var result = response.data;
        if(result.code == "0000"){
          alert("비밀번호가 수정되었습니다.");
          this.navigate('/');
        } else if(result.code == "0102"){
          this.user = this.userReset;
          return alert("기존 패스워드가 일치하지 않습니다.");
        } else {
          this.user = this.userReset;
          return alert("오류");
        }
      }).catch(err => {
        console.log(err.response);
        if(err.response.status == 400){
          return alert("아이디와 패스워드 형식이 맞지 않습니다. \n'이메일형식 8~30자'");
        }
      })
    }
  
  }
}
</script>

<style scoped>
#login{
  background: #000;
  width:100%;
  height:100%;
  position:relative;
  color:#fff;
}
#loginWrap{
  width:400px;height:300px;
  padding:50px;
  position:absolute;
  left: calc(50% - 250px);
  top: calc(50% - 200px);
}
#loginWrap > div{
  width:300px;
  margin:0 auto;
}
h2{
  font-weight:bold;
  font-size:2.9em;
  text-align:center;
  margin-bottom:25px;
}
.otherAction {
}
.otherAction span{
  display:inline-block;
  font-size:0.9em;
  text-decoration:underline;
  margin:13px 2px;
}
.otherAction span:hover{
  color:#00B900;
  cursor:pointer;
}
input{
  background:rgba(0,0,0,0);
  border-bottom:1px solid #fff;
  color:#fff;
  width:100%;
}
label{
  font-weight:bold;
  font-size:0.9em;
  display:inline-block;
  width:40px;height:30px;
}
button{
  margin-top:17px;
  width:100%;
  height:30px;
  color:#fff;
  font-size:1.0em;
}
</style>