  
<template>
  <div class="comment">
    <ul>
      <template v-if="childCommentList != null">
      <li class="reply" v-for="(comment, idx) in childCommentList">
        <strong>{{comment.writerId}}</strong>
        <span>{{comment.content}}</span>
      </li>
      </template>
      <template v-if="childCommentList.length == 0 || childCommentList == null">
      <li class="noReply">댓글이 없습니다.</li>
      </template>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'Comment',
  props : ['article'],
  mounted () {
    this.childArticle = this.article,
    this.getComments(1);
  },
  methods : {
    getComments(page){
      var $this = this;
      this.$axios.get('/api/comments', {
         params : {
           articleId : $this.article.articleId,
           gameName : $this.article.gameName,
           page : $this.commentPagination
         },
         headers: {
           'Content-Type': 'application/json',
           'Authorization': 'Bearer ' + $this.$store.state.token
         }
       }).then((response) => {
        $this.updateLock = false;
        var result = response.data;
        if(result.code == "0000"){
          console.log("댓글 가져오기 성공");
          if($this.commentPagination == 1){
            $this.childCommentList = result.list;
          } else{
            $this.childCommentList.push(result.list);
          }

          console.log(result);
        } else{
          alert("권한이 없습니다.");
          $this.navigate('/');
        }
      }).catch(err => {
      console.log(err)
        return alert("오류");
      })
    }
  
  },
  data () {
    return {
      childArticle : {},
      childCommentList : [],
      commentPagination : 1
    }
  }
}
</script>

<style scoped>
.reply{height:auto;min-height:50px;line-height:1.3em;margin-top:10px;border-bottom:1px solid #eee;padding-bottom:10px;}
.reply strong{font-weight:bold;font-size:1.1em;padding:20px 5px 20px 0;color:#555;}
.reply span{font-size:1em;color:#777;}
.noReply{padding:40px 20px;text-align:center;color:#ccc;}
</style>