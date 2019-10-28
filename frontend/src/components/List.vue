  
<template>
  <section id="article">
    <main-header/>
    <nav>
      <p v-if="!crawlLock">
        <button @click="getNewArticles" type="button" title="신규 게시글을 수동으로 크롤링 하여 가져옵니다.">새글가져오기</button>
      </p>
      <p v-if="crawlLock">
        <button class="disable" type="button">가져오는 중..</button>
      </p>
      <p v-if="!updateLock">
        <button @click="updateArticles" type="button" title="화면에 표시된 게시글 목록을 크롤링 하여 업데이트 합니다.">목록업데이트</button>
      </p>
      <p v-if="updateLock">
        <button class="disable" type="button">업데이트 중..</button>
      </p>
      <p>
        <button @click="navigate('/newpwd')" type="button" title="비밀번호변경">비밀번호변경</button>
      </p>
      <p>
        <button @click="logOut" type="button" title="로그아웃">로그아웃</button>
      </p>
      <p>
        <button @click="deleteUser" type="button" title="탈퇴하기">탈퇴하기</button>
      </p>
    </nav>
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
        <li style="padding:50px;line-height:2em;font-size:1.2em;" v-if="articleList.length == 0">게시글이 존재하지 않습니다. <br>아직 크롤링 중일 수 있습니다. <br>새로고침 해 보세요.</li>
      </ul>
      <div class="paginationWrap">
        <paginate
          v-model="pageable.pageNumber"
          :page-count="pageable.totalPage"
          :page-range="5"
          :margin-pages="1"
          :click-handler="pageMove"
          :hide-prev-next="true"
          :prev-text="'이전'"
          :break-view-text="'...'"
          :next-text="'다음'"
          :container-class="'pagination'"
          :page-link-class="'pageLink'"
          :page-class="'pageItem'">
        </paginate>
      </div>
      <article id="articleDetail" v-bind="article" v-if="articleDetailView">
        <p style="font-size:18px;">
          [ <span class="gameName">{{article.gameName}}</span> ] <span class="title">{{article.title}}</span>
        </p>
        <p>
          <span class="writer">{{article.writer}}</span> 
        </p>
        <p class="regDate">{{article.regDate | moment("YYYY.MM.DD")}}</p>
        <div class="content" v-html="article.content">
        </div>
        <p class="sub">
          댓글 : <span>{{article.commentCnt}}</span> | 좋아요 : <span>{{article.likeCnt}}</span> | 싫어요 : <span>{{article.unlikeCnt}}</span> | 조회수 : <span>{{article.viewCnt}}</span>
        </p>
        <div>
          <comment :article="article"></comment>
        </div>
      </article>
      <button type="button" v-if="articleDetailView" @click="closeDetail">상세보기 팝업 닫기</button>
    </section>
  </section>
</template>

<script>
import MainHeader from '@/Components/common/header'
import Paginate from 'vuejs-paginate'
import Comment from '@/components/Comment'

export default {
  name: 'List',
  mounted() {
    console.log("로그인 권한 체크를 해야한다");
    this.getArticleList();
  },
  components: {
    'main-header' : MainHeader,
    'paginate' : Paginate,
    'comment' : Comment
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
        offset: 0,
        pageNumber: 0,
        pageSize: 10,
        paged: true,
        sort: "",
        totalCnt: 0,
        totalPage: 0
      },
      crawlLock : false,
      updateLock : false
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
    pageMove(pageNum) {
      this.getArticleList(pageNum)
    },
    getNewArticles(){
      var $this = this;
      $this.crawlLock = true;
      this.$axios.post('/api/articles/crawl',{}, {
         headers: {
           Authorization: 'Bearer ' + $this.$store.state.token
         }
       }).then((response) => {
        $this.crawlLock = false;
        var result = response.data;
        if(result.code == "0000"){
          console.log("새 게시글 크롤링 성공");
          $this.getArticleList();
          return alert("새 게시글을 크롤링하는데 성공하였습니다.");
        } else{
          alert("권한이 없습니다.");
          $this.navigate('/');
        }
      }).catch(err => {
      console.log(err);
        $this.crawlLock = false;
        return alert("오류");
      })
    },
    updateArticles(){
      var $this = this;
      $this.updateLock = true;
      
      var articleIds = [];
      $this.articleList.forEach(function(article){
        articleIds.push(article.articleId);
      });
      
      this.$axios.post('/api/articles/update', {
          articleIds : articleIds
        }, {
         headers: {
           'Content-Type': 'application/json',
           'Authorization': 'Bearer ' + $this.$store.state.token
         }
       }).then((response) => {
        $this.updateLock = false;
        var result = response.data;
        if(result.code == "0000"){
          console.log("업데이트 성공");
          $this.articleList = result.list;
          return alert("보이는 목록만 새로 크롤링하여 업데이트 하였습니다.");
        } else{
          alert("권한이 없습니다.");
          $this.navigate('/');
        }
      }).catch(err => {
      console.log(err)
        $this.updateLock = false;
        return alert("오류");
      })
    },
    getArticleList(page) {
      var url = "/api/articles";

      if(page != undefined){
        url += "?page=" + page;
      }

      var $this = this;
      this.$axios.get(url, {
         headers: {
           Authorization: 'Bearer ' + $this.$store.state.token
         }
       }).then((response) => {
        var result = response.data;
        if(result.code == "0000"){
          console.log("조회 성공");
          $this.articleList = result.pagingList.content;

          var pagingObject = result.pagingList.pageable;
          pagingObject.pageNumber = pagingObject.pageNumber + 1; //JPA paging은 Zero base
          pagingObject.totalCnt = result.pagingList.totalElements;
          pagingObject.totalPage = result.pagingList.totalPages;
          $this.pageable = pagingObject;
        } else{
          alert("권한이 없습니다.");
          $this.navigate('/');
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
  cursor:pointer;
}
nav{
  position:fixed;
  top:80px;
  left:calc(50% + 465px);
  z-index:1000;
}
nav button{
  color:#fff;
  font-size:0.8em;
  padding:12px;
  font-weight:bold;
  margin-bottom:5px;
  width:120px;
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
  height:400px;
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
#articleDetail p.sub{
  font-size:0.9em;
  color:#aaa;
}
#articleDetail p.sub span{
  color:rgb(0,200,0);
  font-weight:bold;
}
#articleList .pagination{
  margin-top:10px;
}
</style>
