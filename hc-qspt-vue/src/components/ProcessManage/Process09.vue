<template>
    <div>
        <div class='table'>
            <div class='tabletitle'>缴纳税款</div>
            <div class='tabletitle1'>
                <div class='projectname'>征收项目名称：{{zsxmmc}}</div>
                <div class='orname'>税务机关名称：{{swjgmc}}</div>
            </div>
            <div class='personinner'>
                <div class='pclass' v-for="(item,index) in person" :key="index">
                    <div class='persontitle'>{{item.name}}</div>
                    <div class='personname' :style="'color:'+item.pcolor">{{item.text}}</div>
                </div>
                <div class='status'>待支付</div>
                <div class='time'>税款限缴日期：{{xjrq}}</div>
            </div>
            <div class='money'>{{totalmoney}}</div>
            <div class='totle'>总金额:</div>
        </div>
        <div class='next' @click="pay" >
          <div class='nexttext'>确认缴纳</div>
        </div>
    </div>
</template>
<script>
  import Common from '@/lib/common'
  export default {
    props: {
      applyId: {
        type: String,
        default: null
      }
    },
    data() {
      return {
        zsxmmc:"",
        swjgmc:'',
        xjrq:'',
        jehjxx:'',
        jehjdx:'',
        yhshjxx:'',
        totalmoney:0,
        person:[
          {
            name:'纳税人名称',
            text:''
          },
          {
            name:'纳税人识别号',
            text:''
          },
          {
            name:'凭证序号',
            text:''
          },
          {
            name:'契税',
            text:''
          },
          {
             name:'印花税',
             text:''
          },
          {
            name:'金额合计',
            text:''
          }
        ]
      }
    },
    mounted() {
      Common.getSd(this);
    },
    methods:{
      pay() {
          Common.pay(this);
      },
        loadMessage(){
            Common.loading(this,"支付订单获取异常");
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
  margin-top: 1em;
  background-color: white;
  border: 1px solid white;
  padding-bottom: 1em;
  box-shadow: 0em 0em 0.1em 0.1em #D0D0D0;
}
.tabletitle{
  margin: auto;
  width: 100%;
  height: 2em;
  line-height: 2em;
  text-align: center;
  font-size: 1.1em;
  color: black;
  font-weight: 600;
  background-color: #f8f8f8;
  border-radius: 0.5em 0.5em 0em 0em
}
.tabletitle1{
  width: 100%;
  height: 2.5em;
  border-bottom: 1px dashed #eeeeee;
}
.projectname{
  margin-top: 0.2em;
  font-size: 0.5em;
  margin-left: 5%;
}
.orname{
  margin-top: 0.2em;
  font-size: 0.5em;
  margin-left: 5%;
}
.pclass{
  width: 100%;
  height: 2em;
}
.personinner{
  margin: auto;
  width: 100%;
  margin-top: 34rpx;
}
.persontitle{
  float: left;
  font-size: 1em;
  margin-left: 5%;
  line-height: 2em;
  height: 2em;
}
.status{
  text-align: center;
  font-size: 1.5em;
  color: #79838b;
  margin-top: 1em;
}
.time{
  width: 90%;
  font-size: 1em;
  color: #79838b;
  margin-top: 1em;
  margin-left: 5%;
  padding-bottom: 1em;
  border-bottom: 1px solid #f4f4f4;
}
.totle{
  margin-left: 5%;
  font-size: 1em;
  margin-top: 1em;
  font-weight: 600;
}
.money{
  color: #d00200;
  font-size: 1em;
  float: right;
  margin-top: 1em;
  margin-right: 1em;
}
.personname{
  float: right;
  font-size: 1em;
  margin-right: 5%;
  height: 2em;
  line-height: 2em;
  color: #79838b;
}
.next{
  position: fixed;
  text-align: center;
  margin: auto;
  width: 100%;
  height: 3em;
  font-weight: 600;
  background-color: white;
  bottom: 0em;
  box-shadow: 0px 0px 0.2em 0.2em #F8F8F8;
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
.error{
  width: 100%;
  height: 8em;
}
</style>
