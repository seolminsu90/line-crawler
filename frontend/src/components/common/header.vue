  
<template>
  <header>
    <h1 title="logo">LINE SQUARE COMMUNITY</h1>
  </header>
</template>

<script>
export default {
  name: 'main-header'
}
</script>

<style scoped>
  header{
    position:fixed;
    z-index:30000;
    top:0px;
    left:0px;
    width:100%;
    height:60px;
    background: #000;
    text-align:center;
  }
  header h1{
    font-size:24px;
    font-weight:bold;
    height:60px;
    line-height:60px;
    color:#fff;
  }
</style>