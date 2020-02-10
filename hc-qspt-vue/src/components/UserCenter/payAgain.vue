<template>
<div>
  <div class="teach">
  <div class="head">在缴一笔</div>
    <br/>
  <div></div>

  </div>
  <div class='next' @click='goback1()'>
    <div class='nexttext' :style="'background-color::#c0c0c0'">返回</div>
  </div>
</div>
  <!--<img src=""/>-->

</template>
<script>
  export default {
    methods: {
      goback1() {
        this.$router.push("/index")
      }
    }
  }
</script>
<style scoped>
  .next{
    z-index: 99;
    position: fixed;
    margin: 0 auto;
    width: 90%;
    height: 2em;
    font-weight: 300;
    background-color: white;
    bottom: 0em;
    box-shadow: 0em 0em 1em 1em #F8F8F8;
  }
  .nexttext{
    width: 28.5em;
    height: 2.5em;
    margin: auto;
    background-color: #2c4c7f;
    /*line-height: 2.5em;*/
    text-align: center;
    margin-top: 0.3em;
    font-size: 8pt;
    color: white;
    border-radius: 0.3em;
  }
  .head{
    text-align: center;
    font-size: 23px;
    color:#c0c0c0;
  }
  .teach{
    font-family:"STKaiti";
    font-size:17px;
    text-align: center;
  }
</style>
