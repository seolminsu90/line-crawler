<template>
  <section id="login">
    <section id="loginWrap">
      <h2>LINE SQUARE</h2>
      <div>
        <p>
          <input placeholder="가입했던 이메일을 입력하세요" maxlength="30" v-model.trim="user.id" id="id" type="text"/>
        </p>

        <button v-if="!waiting" type="button" @click="findPwd">임시 비밀번호 보내기</button>
        <button v-else class="disable" type="button">메일을 보내는 중...</button>

        <div class="otherAction">
          <span @click="navigate('/')">취소</span>
        </div>
      </div>
    </section>
  </section>
</template>

<script>
export default {
  name: 'Fndpwd',
  data () {
    return {
      waiting : false,
      user : {
        id : ""
      }
    }
  },
  methods : {
    findPwd(){
    var $this = this;
      if(this.user.id == "" || this.user.id == undefined){
        return alert("메일을 입력하세요.");
      }

      $this.waiting = true;
      this.$axios.post("/api/users/send-temp-pwd", this.user).then((response) => {
        $this.waiting = false;
        var result = response.data;
        if(result.code == "0000"){
          return alert("메일이 발송되었습니다. 메일함을 확인해 주세요.");
        } else if(result.code == "0104"){
          return alert("없는 유저입니다.");
        } else{
          return alert("오류");
        }
      }).catch(err => {
        $this.waiting = false;
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