<template>
<div>
  <title>帮助</title>
  <div class="teach">
    <div align="center">
      <img  src="../../assets/static/images/touch.png" style="width: 150px;height: 120px;position:relative;top:20px">
    </div>
      <br/>
  <div></div>

  <div>四川天府银行自贡分行营业部</div>
  <div>电话：0813-8566999</div>
  <div>地址：自贡市自流井区龙汇南街79号</div>
    <br/>
  <div>四川天府银行自贡南湖支行</div>
  <div>电话：0813-8566333</div>
  <div>地址：自贡市自流井区兴川南街144号</div>
    <br/>
  <div>四川天府银行自贡沿滩支行</div>
  <div>电话：0813-8566111</div>
  <div>地址：自贡市沿滩区时代大道286号</div>
  <!--<div style="vertical-align: center">-->
    <!--<img src="./img/map.jpg" height="60%" width="60%">-->
  <!--</div>-->
  </div>
  <div class='next' @click='goback1()'>
    <div class='nexttext' :style="'background-color::#c0c0c0'"><span style="position: relative;top: 10%;font-size: large">返回</span></div>
  </div>
</div>

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

  .nexttext{

    width: 29.5em;
    position: fixed;
    bottom: 12px;
    width: 29.5em;
    width: 90%;
    right: 0px;
    height: 6%;
    left: 0;
    margin: 0 auto;
    background-color: #2c4c7f;
    line-height: normal;
    margin-top: 0.3em;
    margin-top: auto;
    text-align: center;
    table-layout: fixed;
    text-align: center;
    font-size: 13pt;
    color: white;
    border-radius: 1.3em;
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
