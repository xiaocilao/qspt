<template>


  <div>
    <title>邮递单号</title>


    <div align="center">
      <img  src="../../assets/static/images/post.png" style="width: 120px;height: 120px;position:relative;top:20px">
      <div style="position: relative;top: 20px;"><span>{{name}}</span><span style="color: red">{{postName}}</span></div>
      <div style="position: relative;top: 30px;"><span>{{number}}</span><span style="color: red">{{postNumber}}</span></div>
      <div style="position: relative;top: 35px;"><span>{{status}}</span><span style="color: red">{{taxWrongText}}</span></div>
      <hr style="position: relative;top: 30px;">
      <el-button style="position: relative;top: 30px;" size="small" @click='getPost()'>确认收件</el-button>
      <div style="clear:both"></div>
      <div class="histContent">
        <span style="float: left"><i class="el-icon-warning"></i>温馨提示:</span>
        <br>
        <span style="float: left">请自行在快递公司官网查询物流状态。</span>
      </div>
    </div>
    <div class='next' @click='goback1()'>
      <div class='nexttext' :style="'background-color::#c0c0c0'"><span style="position: relative;top: 10%;font-size: large">返回</span></div>
    </div>
  </div>





<!--<div>-->
  <!--<title>邮递单号</title>-->
  <!--<div class="head">房产契税</div>-->
  <!--<div class='table'>-->
    <!--<div style="float: left" > <i class="el-icon-phone"></i>快递公司</div>-->

  <!--<div :style="'color:'+paySuccessColor">：{{postName}}</div>-->
  <!--<div style="float: left"><i class="el-icon-zoom-in"></i>快递单号</div>-->
  <!--<div :style="'color:'+taxSuccessColor">：{{postNumber}}</div>-->
    <!--<i class="el-icon-star-off"></i>状态：{{taxWrongText}}-->
    <!--<div></div>-->
    <!--&lt;!&ndash;<div style="color: red" @click='getPost()'>确认收件</div>&ndash;&gt;-->
  <!--<div class='next' @click='goback1()'>-->
    <!--<div class='nexttext'>返回</div>-->
  <!--</div>-->
<!--</div>-->
  <!--</div>-->

</template>
<script>
  import Common from '@/lib/common'
  export default {
    data(){
      return{
        postName:"无信息",
        postNumber:"无信息",
        status:'',
        taxWrongText:"",
        paySuccessColor:"red",
        taxSuccessColor:"red",
        name:'',
        number:''
      }
    },
    mounted(){
      var that =this;
      Common.getPostNumber(function(res){
        if(res.data.rows[0].code=='200'){
          that.postName =res.data.rows[0].data.postName;
          that.name='快递公司:';
          that.number='快递单号:';
          that.postNumber =res.data.rows[0].data.postNumber;
          if(res.data.rows[0].data.status==='1'){
            that.taxWrongText = '取件成功！';
            that.status='取件状态:';
          }

        }

      });
    },
    methods: {
      goback1() {
        this.$router.push("/index")
      },
      loading(that,data){
        let arr = [data];
        let str = arr.join('</br>');
        that.$message({
          dangerouslyUseHTMLString:true,
          message:str,
          type:'warning'
        })
      },
      getPost(){
        var that = this;
        Common.changPostStatus(function(res){
          if(res.data.rows[0].code=='200'){
            that.taxWrongText = '已接收'
            that.loading(that,"操作成功！");
          }else if(res.data.rows[0].code=='202'){
            that.loading(that,"请先录入您的信息！");
          }else{
            that.loading(that,"不可操作！");
          }
        });
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
  .refund{
    color: brown;
  }
  .tax-wrong {
    color: red;
  }
  .histContent{
    color:#D1D1D1;
    font-size: 10px;
    top:40px;
    position: relative;
  }
  .pay-wrong{
    color: red;
  }

</style>
