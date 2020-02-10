<template>
    <div>
        <div v-if="!seeImg">
            <img style="position: fixed;top:5px;left:5px;z-index: 1000" src="@/assets/static/images/back.png" width="40px" height="40px" @click="upway" >
            <!--<div style="font-size: 5px;position: fixed;top:-7px;right:5px;color:slategrey">上一步</div>-->
            <flow>
            <template v-for="(item,index) in processStep">
              <flow-state :state="index+1" :title="item.name" :is-done="item.isdone"></flow-state>
              <flow-line :tip="item.tip" v-if="item.showtip" :is-done="item.isdone"></flow-line>
            </template>
          </flow>
        </div>
        <div class='background' :hidden='backhidden'></div>
        <Process01 v-if="active==0&&!seeImg&&!signBoard" @upfile="upfile" :cards="cards" @upValue="setValue" @seeImg="checkImg" @add="add" @deleteAll="deleteAll"></Process01>
        <Process02 v-if="active==1&&!seeImg&&!signBoard" @upfile="upfile" :cards="cards" @upValue="setValue" @seeImg="checkImg"></Process02>
        <Process03 v-if="active==2&&!seeImg&&!signBoard" @upfile="upfile" :title="title" :cards="cards" @upValue="setValue" @seeImg="checkImg" @add="add" @deleteAll="deleteAll"></Process03>
        <Process04 v-if="active==3&&!seeImg&&!signBoard" @upfile="upfile" :cards="cards" @upValue="setValue" @seeImg="checkImg"></Process04>
        <Process05 v-if="active==4&&!seeImg&&!signBoard" @upfile="upfile" :cards="cards" @upValue="setValue" @seeImg="checkImg"></Process05>
        <Process06 v-if="active==5&&!seeImg&&!signBoard" @confirm="signComplete"  @upfile="upfile" :cards="cards" @upValue="setValue" @seeImg="checkImg"></Process06>
        <Process07 v-if="active==6&&!seeImg&&!signBoard" @upfile="upfile" :cards="cards" @upValue="setValue" @seeImg="checkImg" @skip="skip"></Process07>
        <Process08 v-if="active==7||active==8&&!seeImg&&!signBoard" @upfile="upfile" :cards="cards" @upValue="setValue" @seeImg="checkImg" :spzt="spzt" @goOtherPage="goOtherPage"></Process08>
        <!--<Process09 v-if="active==8&&!seeImg&&!signBoard" @upfile="upfile" :applyId="applyId" @upValue="setValue" @seeImg="checkImg" @delImfo="delImfo"></Process09>-->
        <SeeImg v-if="seeImg&&!signBoard" :cards="cards" :site="site" :imgType="imgType" @back="goback" @delImage="delImage" @upfile="upfile" @sign="sign"></SeeImg>
        <SignBoard v-if="signBoard&&!seeImg" @confirm="signComplete" :cards="cards" :site="site" @upfile="upfile"></SignBoard>
        <div class="Filler" v-if="!seeImg&&!signBoard"></div>
        <div v-if="active!=7&&active!=8&&!seeImg">
            <div class='error'></div>
            <div class='next' @click='next()'>
            <div class='nexttext' :style="'background-color:'+back"><span style="position: relative;top: 10%;font-size: large">下一步</span></div>
            </div>
            <div class='bkcz' :style="'display:'+bkcz">上传中，请稍等......</div>
        </div>
    </div>
</template>
<script>
import Process01 from './Process01'
import Process02 from './Process02'
import Process03 from './Process03'
import Process04 from './Process04'
import Process05 from './Process05'
import Process06 from './Process06'
import Process07 from './Process07'
import Process08 from './Process08'
// import Process09 from './Process09'
import Common from '@/lib/common'
import SeeImg from '@/components/CommComponents/SeeImg'
import SignBoard from '@/components/CommComponents/SignBoard'
import { Flow, FlowState, FlowLine } from 'vux'
export default {
    components:{
        Process01,
        Process02,
        Process03,
        Process04,
        Process05,
        Process06,
        Process07,
        Process08,
        // Process09,
        Flow,
        FlowState,
        FlowLine,
        SeeImg,
        SignBoard
    },
    data(){
        return {
          cards: [
            {

              infoId:'',
              files:[{border:"none",flowFileUrl:'',reson:''}],
              upfiledata:{flowType:'sfyz',clientKey:Common.clientKey},
              data:[],
              display:[{imagedis:false,viewdis:true},{imagedis:false,viewdis:true}],
              border:"none",
              resonhidden:true,
              reson:'',
              icontype:['',''],
            }
          ],
          title:[
            {
              first:'副页',
              secend:'副页',
            }
          ],
          countValue:0,
          ex:true,
          cango:false,
          seeImg:false,
          signBoard:false,
          imgType:"normal",
          site:{
            i:0,
            index:0
          },
          books: [
            {
              bookname: require("@/assets/static/images/bookimg/book1.jpg"),
            },
            {
              bookname: require("@/assets/static/images/bookimg/book2.jpg"),
            },
            {
              bookname: require("@/assets/static/images/bookimg/book3.jpg"),
            },
          ],
          processStep:[
            {
              name:'',
              showtip:true,
              isdone:false,
              tip:"",
              flowType: "sfyz"
            },
            {
              name:'',
              showtip:true,
              isdone:false,
              tip:"",
              flowType: "hyzk"
            },
            {
              name:'',
              showtip:true,
              isdone:false,
              tip:"",
              flowType: "hkbxx"
            },
            {
              name:'',
              showtip:true,
              isdone:false,
              tip:"",
              flowType: "gffp"
            },
            {
              name:'',
              showtip:true,
              isdone:false,
              tip:"",
              flowType: "gfht"
            },
            {
              name:'',
              showtip:true,
              isdone:false,
              tip:"",
              flowType: "sqs"
            },
            {
              name:'',
              showtip:true,
              isdone:false,
              tip:"",
              flowType: "cqbc"
            },
            {
              name:'',
              showtip:false,
              isdone:false,
              tip:""
            }
          ],
            bkcz:"none",
            back: "#cdd4df",
            backhidden:true,
            active: -1,
          applyId:'',
          flowId:'',
          spzt:0
        }
    },
  created(){
    console.log(this.$route);
    var index = 0;
    if (this.$route.query && this.$route.query.data) {
      //1-8步骤状态传到此处
      var data = this.$route.query.data
      this.applyId=data.id;
      this.spzt=data.spzt;
      var flowTypeZt = [];
      flowTypeZt.push(data.sfyz);
      flowTypeZt.push(data.hyzk);
      flowTypeZt.push(data.hkbxx);
      flowTypeZt.push(data.gffp);
      flowTypeZt.push(data.gfht);
      flowTypeZt.push(data.sqs);
      flowTypeZt.push(data.cqbc);
      flowTypeZt.push(data.zlsh);
      for (var i = 0; i < flowTypeZt.length; i++) {
        if (flowTypeZt[i] == 0) {
          break;
        }else if(flowTypeZt[i] == -1){
          break;
        }
        this.processStep[i].isdone=true;  //将完成的设置成true
      }
      if(i==8){
        i=7;
      }
      index = i;  //index用于判断标题内容
    }else{
      index = 0;
    }
    switch (index) {
      case 0:
        this.processStep[index].name="身份验证";
        this.countValue=2;
        break;
      case 1:
        this.processStep[index].name="婚姻状况";
        this.countValue=2;
        break;
      case 2:
        this.processStep[index].name="户口簿信息";
        this.countValue=2;
        break;
      case 3:
        this.processStep[index].name="购房发票";
        this.countValue=4
        break;
      case 4:
        this.processStep[index].name="购房合同";
        this.countValue=4
        break;
      case 5:
        this.processStep[index].name="授权书";
        this.countValue=0
        break
      case 6:
        this.processStep[index].name="补偿协议";
        this.countValue=0
        break;
      case 7:
        this.processStep[index].name="缴纳税款";
        break;
    }
    for(var j=0;j<this.countValue;j++){
      this.cards[0].data.push({
        keyValue:['']
      })
    }
    if(index==5){
      for (var i=0;i<3;i++) {
        if(!this.cards[0].files[i]||this.cards[0].files[i].flowFileUrl==""){
          this.$set(this.cards[0].files,i,{border:"none",flowFileUrl:this.books[i].bookname});
        }
      }
    }

    if (this.$route.query && this.$route.query.data) {//重复了
      var that = this
      Common.init(this,this.processStep[index].flowType,this.countValue,function () {
        if(index==5){
          for (var i=0;i<3;i++) {
            if(that.cards[0].files[i].flowFileUrl.substr(0,17)!="/qishui/qsapp/api"){
              that.ex=false;
            }
          }
        }
        Common.cango(that,that.ex);
        if(index==2){
          var length = that.cards.length;
          for(var i=0;i<length-1;i++){
            that.title.push({
              first:'附页',
              secend:'附页',
            })
          }
        }
        that.active=index;
      })
    }else{
      this.active=0;
    }
  },
    methods:{
      delImfo:function(data){
     
        Common.loading(this,data);
      },
        upway(){
            var that = this
            that.cango = true;
            that.ex=true;
            if(this.active>=2&&this.active<7){
                this.active =  this.active-2;
                if(this.active==5){
                    that.skip();
                }
                Common.next(this,function () {
                    Common.init(that,that.processStep[that.active].flowType,that.countValue,function () {
                        Common.cango(that);
                        if(that.active==2){
                            var length = that.cards.length;
                            for(var i=0;i<length-1;i++){
                                that.title.push({
                                    first:'附页',
                                    secend:'附页',
                                })
                            }
                        }
                    })
                });
            }else if(that.active ===1){
                that.active =0;
                Common.init(that,that.processStep[that.active].flowType,that.countValue,function () {
                that.ex==true;
                that.cango==true;
                Common.cango(that);
                });
            }else if(that.active ===0){
                this.$router.push("index")
            } else{
                Common.loading(this,"信息已存档，不可逆操作！");
            }
        },
      deleteAll(){
        var ex = true;
        for(var k=0;k<this.cards.length;k++){
          for(var j=0;j<this.cards[k].data.length;j++){
            if(this.cards[k].icontype[j]!='success'){
              break;
            }
          }
          if(j!=this.cards[k].data.length){
            break;
          }
          if(this.cards[k].files.length!=2){
            break;
          }else{
            for (var j = 0; j < this.cards[k].files.length; j++) {
              if(this.cards[k].files[j].flowFileUrl==""){
                break;
              }
            }
            if(j!=this.cards[k].files.length){
              break;
            }
          }
        }
        if(k!=this.cards.length){
          ex = false
        }
        Common.cango(this,ex);
      },
      delImage(deleteFile){
        Common.delImage(this,deleteFile);
        this.seeImg=false;
        },
      goOtherPage(){

        switch (this.spzt) {
          case 3:
          case "3":
            this.active=8
            break;
          case 6:
          case "6":
            this.$router.push("payTest")
        }
      },
      skip(){
        Common.skip7(this);
      },
      signComplete(){
        this.signBoard=false;
        this.seeImg=true;
      },
      sign(){
        this.signBoard=true;
        this.seeImg=false;
      },
      add(){
        this.cards.push({
          infoId:'',
          files:[{border:"none",flowFileUrl:'',reson:''}],
          upfiledata:{flowType:this.processStep[this.active].flowType,applyId:this.applyId,flowId:this.flowId,clientKey:Common.clientKey},
          data:[],
          display:[{imagedis:false,viewdis:true},{imagedis:false,viewdis:true}],
          border:"none",
          resonhidden:true,
          reson:'',
          icontype:[]
        });
        for(var j=0;j<this.countValue;j++){
          this.cards[this.cards.length-1].data.push({
            keyValue:['']
          })
          this.cards[this.cards.length-1].icontype.push('');
        }
        this.cango=false;
        Common.cango(this);
      },
      goback(){
        this.seeImg=false;
      },
      checkImg(data){

        this.site.i=data.i;
        this.site.index=data.index;
        this.seeImg=true;
        if(data.tag){
          this.imgType="sign"
        }else{
          this.imgType="normal"
        }
      },
      setValue(data) {
        this.cards=data.cards;
        if(typeof data.ex != "undefined"&&data.ex!=null){
          Common.cango(this,data.ex);
        }else{
          Common.cango(this);
        }
      },
      upfile(data){

        this.cards=data.cards;
        if(data.applyId){
          this.applyId=data.applyId
        }
        if(data.flowId){
          this.flowId=data.flowId
        }
        if(typeof data.ex != "undefined"&&data.ex!=null){
          Common.cango(this,data.ex);
        }else{
          Common.cango(this);
        }
      },
      next() { //点击下一步
        var that = this
        if(this.active==5){ //要签名啦
            for (var i=0;i<3;i++) { //这里有点问题
                if(!this.cards[0].files[i]||this.cards[0].files[i].flowFileUrl==""){
                    this.$set(this.cards[0].files,i,{border:"none",flowFileUrl:this.books[i].bookname});
                }
            }
        }
          if(this.active==6){
                that.skip();
          }
          if(this.active==2){
              debugger;
              this.ex=true;
          }
        if(this.ex==true&&this.cango==true){
          Common.next(this,function () {
            Common.init(that,that.processStep[that.active].flowType,that.countValue,function () {
              that.cango = false;
              Common.cango(that);
              if(that.active==2){
                var length = that.cards.length;
                for(var i=0;i<length-1;i++){
                  that.title.push({
                    first:'附页',
                    secend:'附页',
                  })
                }
              }
            })
          });
        }else{
            if(this.active==5){
                Common.loading(this,"您还没完成签名呢，请用正楷字体进行签名，确认字体规范，请重新进行签名。如果在此过程中遇到问题，请返回首页重新进入尝试。");
            }else{
                Common.loading(this,"信息录入不全，请检查！");
            }
        }
      }
    }
}
</script>
<style scoped>
  >>> .weui-wepay-flow__title-bottom{
    top: 0em;
    background-color: #2B4D7E;
    border-radius: 3em;
    width: 6em;
  }
  >>> .weui-wepay-flow__li_done .weui-wepay-flow__title-bottom{
    color: white;
    font-size: 0.5em;
  }
  >>> .weui-wepay-flow, .weui-wepay-flow-auto{
    padding: 0px;
    padding-bottom: 2em;
    padding-top: 0em;
    padding-left: 2em;
    padding-right: 2em;
  }
  >>> .weui-wepay-flow__li_done .weui-wepay-flow__state{
    background-color: #2B4D7E;
  }
>>> .weui-wepay-flow__process{
  background-color: #2B4D7E;
}
>>> .weui-wepay-flow__state{
  background-color:#9AA9C1;
}
>>> .weui-wepay-flow__line{
  background-color:#9AA9C1;
}
>>> [class^="weui-wepay-flow__title-"], [class*=" weui-wepay-flow__title-"]{
  color: white;
}
  .Filler{
    width: 100%;
    height: 5em;
  }
.buttomBtn{
    position: fixed;
    bottom: 100px;
}
.toper{
  text-align: center;
  width: 100%;
  height: 60rpx;
}
.num{
  width: 40rpx;
  height: 40rpx;
  border-radius: 40rpx;
  background-color: #2b4d7e;
  color: white;
  font-size: 30rpx;
  text-align: center;
  line-height: 40rpx;
  display: inline-block;
  margin-top: 20rpx;
}
.num1{
  width: 40rpx;
  height: 40rpx;
  border-radius: 40rpx;
  background-color: #9aa9c1;
  color: white;
  font-size: 30rpx;
  text-align: center;
  line-height: 40rpx;
  display: inline-block;
  margin-top: 20rpx;
}
.num2{
   width: 40rpx;
  height: 40rpx;
  border-radius: 40rpx;
  background-color: red;
  color: white;
  font-size: 20rpx;
  text-align: center;
  line-height: 40rpx;
  display: inline-block;
  margin-top: 20rpx;
}
/*.next{*/
  /*height: 3em;*/
  /*width: 100%;*/
  /*background: white;*/
  /*position: fixed;*/
  /*bottom: 0;*/
/*}*/
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
  .back{
    width: 92%;
    height: 2em;
    background-color: #1c3e72;
    line-height: 2em;
    text-align: center;
    font-size: 1em;
    color: white;
    border-radius: 0.3em;
    position: fixed;
    bottom: 4em;
    left: 3%;
  }
.error{
  width: 100%;
  height: 200rpx;
}
.bkcz{
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.8);
  opacity: 0.8;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 99;
  text-align: center;
  font-size: 32rpx;
  color: white;
  line-height: 1000rpx;
  display: none;
}
</style>
