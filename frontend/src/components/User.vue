  
<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <h2>{{ this.$store.state.count }}</h2>
    <h2><button type="button" v-on:click="$store.commit('increment')">공유증가</button></h2>
  </div>
</template>

<script>
export default {
  name: 'User',
  data () {
    return {
      msg: 'Thie route component is User'
    }
  }
}
</script>

<style scoped>
h1, h2 {
  font-weight: normal;
  color: red;
}
</style>