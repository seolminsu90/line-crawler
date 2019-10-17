  
<template>
  <section id="article">
    <main-header/>
    <section id="articleList">
      <ul v-if="!articleDetailView">
        <li v-for="(article,idx) in articleList" class="article" @click="viewDetail(idx)">
          <p>
            [ <span class="gameName">{{article.gameName}}</span> ] <span class="title">{{article.title}}</span>
          </p>
          <p>
            <span class="writer">{{article.writer}}</span> 
          </p>
          <p class="regDate">{{article.regDate | moment("YYYY.MM.DD")}}</p>
        </li>
      </ul>
      <article id="articleDetail" v-bind="article" v-if="articleDetailView">
        <!--articleId:"",
        likeCnt:0,
        unlikeCnt:0,
        viewCnt:0,-->
        <p style="font-size:18px;">
          [ <span class="gameName">{{article.gameName}}</span> ] <span class="title">{{article.title}}</span>
        </p>
        <p>
          <span class="writer">{{article.writer}}</span> 
        </p>
        <p class="regDate">{{article.regDate | moment("YYYY.MM.DD")}}</p>
        <div class="content" v-html="article.content">
        </div>
        <div>
        
        이거 해야함
          댓글 : 0 | 좋아요 : 0 | 싫어요 : 0 | 조회수 : 0
        </div>
      </article>
      <button v-if="articleDetailView" @click="closeDetail">상세보기 팝업 닫기</button>
    </section>
  </section>
</template>

<script>
import MainHeader from '@/Components/common/header.vue'

export default {
  name: 'List',
  mounted() {
    console.log("로그인 권한 체크를 해야한다");
    this.getArticleList();
  },
  components: {
    'main-header' : MainHeader
  },
  data () {
    return {
      articleDetailView : false,
      article : {
        idx:0,
        articleId:"",
        bbsGroupId:"",
        bbsId:"",
        commentCnt:0,
        content:"",
        gameName:"",
        likeCnt:0,
        regDate:"",
        title:"",
        unlikeCnt:0,
        viewCnt:0,
        writer:""
      },
      articleInit : {
        idx:0,
        articleId:"",
        bbsGroupId:"",
        bbsId:"",
        commentCnt:0,
        content:"",
        gameName:"",
        likeCnt:0,
        regDate:"",
        title:"",
        unlikeCnt:0,
        viewCnt:0,
        writer:""
      },
      articleList : [
      ],
      pageable : {
        offset: 10,
        pageNumber: 1,
        pageSize: 10,
        paged: true,
        sort: ""
      }
    }
  },
  methods : {
    viewDetail(idx){
      window.scrollTo(0,0);
      this.articleDetailView = true;
      this.article = this.articleList[idx];
    },
    closeDetail(){
      this.articleDetailView = false;
      this.article = this.articleInit;
    },
    getArticleList() {
      var $this = this;
      this.$axios.get("/api/articles", {
         headers: {
           Authorization: 'Bearer ' + $this.$store.state.token
         }
       }).then((response) => {
        var result = response.data;
        if(result.code == "0100" || result.code == "0200"){
          alert("권한이 없습니다.");
          $this.navigate('/');
        } else if(result.code == "0000"){
          console.log("조회 성공");
          $this.articleList = result.list;
          $this.pageable = result.pageable;
        }
      });
    }
  }
}
</script>

<style scoped>
#article{
  width:100%;
  height:100%;
  position:relative;
  background:#e3e3e3;
}
#articleList{
  margin:0 auto;
  width:900px;
  height:100%;
  background:#fff;
  position:relative;
}
#articleList ul, #articleDetail{
  margin-top:60px;
}
#articleDetail{
  padding:20px;
}
#articleList button{
  width:100%;
  height:60px;
  font-weight:bold;
  font-size:1.2em;
  color:#fff;
  position:fixed;
  bottom:0px;
  left:0px;
}
.content{
  border-top:1px solid #ccc;
  border-bottom:1px solid #ccc;
  margin:20px 0;
  padding:20px 0;
  line-height:1.5em;
  font-size:1.1em;
  position:relative;
  width:100%;
  height:350px;
  overflow-y:scroll;
}
.article{
  padding: 20px 0 20px 15px;
  border-bottom: 1px solid #e5e5e5;
  position:relative;
}
.article:hover{
  background:#eee;
  cursor:pointer;
}
.article p, #articleDetail p{
  font-size: 1em;
  line-height:1.4em;
}
span.gameName, span.title{
  font-weight:bold;
}
p.regDate{
  font-size:0.9em;
  color:#25a00d;
}
span.writer{
  font-size:0.9em;
  color:#aaa;
  line-height:100%;
  position:absolute;
  right:0px;
  padding:0 20px;
  top:35px;
}
</style>