<template>

<div>
  <title>交易状态</title>


  <div align="center">
    <img v-if="showRight" src="../../assets/static/images/right.png" style="width: 120px;height: 120px;position:relative;top:20px">
    <img v-if="showWrong" src="../../assets/static/images/wrong.png" style="width: 120px;height: 120px;position:relative;top:20px">
    <div style="position: relative;top: 20px;"><span style="color: red">{{dealStatus}}</span></div>
    <div style="position: relative;top: 20px;"><span style="color: red"><span :style="'color:'+taxSuccessColor">{{taxStatus}}</span></span></div>
    <div style="position: relative;top: 30px;">本次支付共计<span style="color: red">{{money}}</span>元,印花税<span style="color: red">{{yhs}}元</span></div>
    <hr style="position: relative;top: 30px;">
    <div style="position: relative;top: 30px;"><span style="float: left">{{kksjContent}}</span><span style="float: right">{{payTime}}</span></div>
    <div style="clear:both"></div>
    <div class="histContent">
      <span style="float: left"><i class="el-icon-warning"></i>温馨提示:</span>
      <br>
      <span style="float: left">扣款成功表示银行已经收取到了费用，缴税成功表示税务扣款成功。</span>
    </div>
  </div>
  <div class='next' @click='goback1()'>
    <div class="nexttext"  :style="'background-color::#c0c0c0'"><span style="position: relative;top: 10%;font-size: large">返回</span></div>
  </div>
</div>
  <!--</div>-->

</template>
<script>
  import Common from '@/lib/common'
  export default {
    data(){
      return{
        dealStatus:"未扣款",
        taxStatus:"未纳税",
        kksjContent:'',
        payWrongText:"",
        taxWrongText:"",
        paySuccessColor:"red",
        taxSuccessColor:"red",
        payTime:'',
        userName:'',
        money:'0',
        yhs:'0',
        showRight:false,
        showWrong:true,
      }
    },
    mounted(){
      var that =this;
      Common.getPayStatus(function(res){

        if(res.data.code!="200"){
          that.dealStatus="未缴费";
          that.taxStatus="未缴税";
          that.payWrongText="无此记录！";
          that.taxWrongText="无此记录！";
        }else{
          if(res.data.context[0]==''){
            that.dealStatus="未缴费";
            that.taxStatus="未缴税";
            that.payWrongText="无此记录！";
            that.taxWrongText="无此记录！";
          }else{
            if( res.data.context[0].status=="0"){

              that.userName = res.data.userName;
              that.showRight=true;
              that.showWrong=false;
              that.dealStatus="缴费成功！";
              that.kksjContent='扣款时间:'
              that.payTime=res.data.context[0].xtLrsj;
              that.money = res.data.context[2].jehjxx;
              that.yhs = res.data.context[2].yhs;
              that.paySuccessColor="green"
              that.payWrongText="";
            }
            if( res.data.context[0].status=="2"){
              that.dealStatus="退款成功！";
              that.paySuccessColor="green"
              that.payWrongText="";
            }
            if( res.data.context[0].status=="3"){
              that.dealStatus="退款失败！";
              that.paySuccessColor="green"
              that.payWrongText="";
            }
          }
          if(res.data.context[1]==''){
            that.taxStatus="未缴税";
            that.taxWrongText="无此记录！";
          }else{
            if(res.data.context[1].procResult=="000000"){
              that.taxStatus="缴税成功";
              that.taxWrongText="";
              that.taxSuccessColor="green";
            }else{
              that.taxStatus="未缴税";
              that.taxWrongText=''+res.data.context[1].procResult + ',' +res.data.context[1].errTex;
              that.taxSuccessColor="red";
            }
          }
        }
      });
    },
    methods: {
      goback1() {
        this.$router.push("/index")
      },
      loadAlert(){
        Common.loading(this,'敬请期待！');
      }
    }

  }
</script>
<style scoped>
  .table{
    position: relative;
    margin: auto;
    border-radius: 0.5em;
    width: 98%;
    font-size: 15px;
    margin-top: 1em;
    background-color: white;
    border: 1px solid white;
    padding-bottom: 1em;
    box-shadow: 0em 0em 0.1em 0.1em #D0D0D0;
  }
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
  .nexttext{width: 29.5em;
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
  .refund{
    color: brown;
  }
  .tax-wrong {
    color: red;
  }
  .pay-wrong{
    color: red;
  }
  .histContent{
    color:#D1D1D1;
    font-size: 10px;
    top:40px;
    position: relative;
  }
</style>
