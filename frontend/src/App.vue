<template>
  <section id="app">
    <router-view/>
  </section>
</template>

<script>
export default {
  name: 'App',
}
</script>

<style>
html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p,
  blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn,
  em, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var,
  b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend,
  table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas,
  details, embed, figure, figcaption, footer, header, hgroup, menu, nav,
  output, ruby, section, summary, time, mark, audio, video {
  margin: 0;
  padding: 0;
  border: 0;
  font-size: 100%;
  font: inherit;
  vertical-align: baseline;
}
article, aside, details, figcaption, figure, footer, header, hgroup,
  menu, nav, section {
  display: block;
}

body {
  line-height: 1;
}

body, html {
  width:100%;
  height:100%;
}

ol, ul {
  list-style: none;
}

blockquote, q {
  quotes: none;
}

blockquote:before, blockquote:after, q:before, q:after {
  content: '';
  content: none;
}

table {
  border-collapse: collapse;
  border-spacing: 0;
}

#app{
  width:100%; height:100%;
}

button{
  background:#00B900;
  border:none;
}
button:hover{
  background:#339933;
  cursor:pointer;
}
button.disable{
  opacity:0.3;
}

input {
  border:none;
  height:30px;
  font-size:0.9em;
  text-indent:5px;
}

textarea:focus, input:focus{
    outline: none;
}

*:focus {
    outline: none;
}
.paginationWrap{
  width:100%;
}
.pagination{
  width:50%;
  margin:0 auto;
  text-align:center;
}
.pagination li{
  display:inline-block;
  font-size:1.0em;
  padding:3px;
  margin:2px;
}
.pagination li:hover{
  color:#00B900;
}
.pagination .pageItem.active{
  font-weight:bold;
  color:#00B900;
}
.pagination .pageItem{
  font-size:1.1em;
}
</style>
