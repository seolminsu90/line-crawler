  
<template>
  <section id="login">
    <section id="loginWrap">
      <h2>LINE SQUARE</h2>
      <div>
        <p>
          <label for="id">ID</label>
          <input placeholder="Email (8자 이상)" maxlength="30" v-model.trim="user.id" id="id" type="text"/>
        </p>
        <p>
          <label for="pwd">PWD</label>
          <input placeholder="Password (8자 이상)" maxlength="30" v-model.trim="user.pwd" id="pwd" type="password"/>
        </p>
        <p>
          <label for="pwdRe">PWD</label>
          <input placeholder="Password repeat (8자 이상)" maxlength="30" v-model.trim="user.pwdRe" id="pwdRe" type="password"/>
        </p>
        <button type="button" @click="signin">회원 가입</button>
        <div class="otherAction">
          <span @click="navigate('/')">취소</span>
        </div>
      </div>
    </section>
  </section>
</template>

<script>
export default {
  name: 'Signin',
  data () {
    return {
      user : {
        id : "",
        pwd : "",
        pwdRe : ""
      },
      userReset : {
        id : "",
        pwd : "",
        pwdRe : ""
      }
    }
  },
  methods : {
    signin (){
      if(this.user.pwd !== this.user.pwdRe){
        return alert("비밀번호 확인이 일치하지 않습니다.");
      }
      
      this.$axios.post("/api/users/signin", this.user).then((response) => {
        var result = response.data;
        if(result.code == "0000"){
          console.log("회원가입 성공");
          alert("이제 서비스를 이용할 수 있습니다.");
          this.navigate('/');
        } else if(result.code == "0103"){
          console.log("회원가입 실패");
          this.user = this.userReset;
          return alert("아이디 또는 비밀번호 값 오류");
        } else if(result.code == "0101"){
          console.log("회원가입 실패");
          this.user = this.userReset;
          return alert("이미 가입된 아이디입니다.");
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
  width:250px;
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