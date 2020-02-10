<template>
    <div class="container">
        <div align="center">
        <img  src="../../assets/static/images/work.png" style="width: 120px;height: 120px;position:relative;top:20px">
        </div>
        <div class='text'>
            <flow orientation="vertical" style="height:250px;">
              <template v-for="(item,index) in text">
                <flow-state :state="index+1" :title="item.info+'  状态：'+item.text" :is-done="item.isdone"></flow-state>
                <flow-line is-done v-if="item.display" :line-span="20"></flow-line>
              </template>
            </flow>
          </div>
          <div class='next' @click='next()'>
            <div class='nexttext' :style="'background-color::#c0c0c0'"><span style="position: relative;top: 10%;font-size: large">返回</span></div>
          </div>
    </div>

</template>
<script>
import Common from "@/lib/common"
import { Flow, FlowState, FlowLine } from 'vux'
export default {
  components: {
    Flow,
    FlowState,
    FlowLine
  },
    data(){
        return {
            text:[
                  {
                      text:"未进行该流程",
                      info:"身份验证",
                      display:true,
                      name:"sfyz",
                      isdone:true
                  },
                  {
                      text: "未进行该流程",
                      info: "婚姻状况",
                      display: true,
                      name: "hyzk",
                      isdone:true
                  },
                  {
                      text: "未进行该流程",
                      info: "户口簿信息",
                      display: true,
                      name: "hkbxx",
                      isdone:true
                  },
                  {
                      text: "未进行该流程",
                      info: "购房发票",
                      display: true,
                      name: "gffp",
                      isdone:true
                  },
                  {
                      text: "未进行该流程",
                      info: "购房合同/备案表",
                      display: true,
                      name: "gfht",
                      isdone:true
                  },
                  {
                      text: "未进行该流程",
                      info: "授权书",
                      display: true,
                      name: "sqs",
                      isdone:true
                  },
                  {
                      text: "未进行该流程",
                      info: "拆迁补偿协议",
                      display: true,
                      name: "cqbc",
                      isdone:true
                  },
                  {
                    text: "待审核",
                    info: "缴纳税款",
                    display: false,
                    name: "jnsk",
                    isdone:true
                  },
                ],
            backcolor:"#2c4c7f",
            backtext:"返回"
          }
    },
  mounted(){
    var that = this;
    Common.Spjl(function (data) {
      console.log(data);
      var flowTypeZt = [];
      if(data){
        flowTypeZt.push(data.sfyz);
        flowTypeZt.push(data.hyzk);
        flowTypeZt.push(data.hkbxx);
        flowTypeZt.push(data.gffp);
        flowTypeZt.push(data.gfht);
        flowTypeZt.push(data.sqs);
        flowTypeZt.push(data.cqbc);
        flowTypeZt.push(data.zlsh);
      }else{
        var flowTypeZt = [0,0,0,0,0,0,0,0];
      }

      switch (data.spzt) {
        case -3:
        case "-3":
          break;
        case -2:
        case "-2":
          break;
        case -1:
        case "-1":
          for(var i=0;i<flowTypeZt.length;i++){
            if(flowTypeZt[i]=="0"||flowTypeZt[i]==0){
              that.text[i].isdone=false;
              if (i != "7" && i != 7) {
                that.text[i].text="未通过"
              }else{
                that.text[i].text="未进行该步骤"
              }
            }else{
              that.text[i].isdone=true;
              if (i != "7" && i != 7) {
                that.text[i].text="已通过"
              }else{
                  if(flowTypeZt[i]=="0"){
                      that.text[i].text="待审核"
                  }else{
                      that.text[i].text="审核中"
                  }

              }
            }
          }
          that.backtext="返回首页"
          break;
        case 0:
        case "0":
        case 1:
        case "1":
          for(var i=0;i<flowTypeZt.length;i++){
            if(flowTypeZt[i]=="0"||flowTypeZt[i]==0){
              that.text[i].isdone=false;
              if (i != "7" && i != 7) {
                that.text[i].text="未进行该步骤"
              }else{
                that.text[i].text="待审核"
              }
            }else{
              that.text[i].isdone=true;
              if (i != "7" && i != 7) {
                that.text[i].text="已提交"
              }else{
                that.text[i].text="已查看"
              }
            }
          }
          that.backtext="返回首页"
          break;
        case 2:
        case "2":
          for(var i=0;i<flowTypeZt.length;i++){
            if(flowTypeZt[i]=="0"||flowTypeZt[i]==0){
              that.text[i].isdone=false;
              if (i != "7" && i != 7) {
                that.text[i].text="未通过"
              }else{
                that.text[i].text="未通过"
              }
            }else{
              that.text[i].isdone=true;
              if (i != "7" && i != 7) {
                that.text[i].text="已提交"
              }else{
                that.text[i].text="已通过"
              }
            }
          }
          that.backtext="返回首页"
          break;
        // case 3:
        // case "3":
          default:
          for(var i=0;i<flowTypeZt.length;i++){
            if(flowTypeZt[i]=="-1"||flowTypeZt[i]==-1){
              that.text[i].isdone=false;
              if (i != "7" && i != 7) {
                that.text[i].text="未通过"
              }else{
                that.text[i].text="未通过"
              }
            }else{
              that.text[i].isdone=true;
              if (i != "7" && i != 7) {
                that.text[i].text="已提交"
              }else{
                that.text[i].text="已通过"
              }
            }
          }
          that.backtext="返回首页"
          break;
        // case 4:
        // case "4":
        //   break;
        // case 5:
        // case "5":
        //   break;
        // case 6:
        // case "6":
        //   break;
        // case 7:
        // case "7":
        //   break;
        // case 8:
        // case "8":
        //   break;
        // case 9:
        // case "9":
        //   break;
      }
    });
  },
    methods:{
      next(){
         var header='';
         var that = this;
         var spzt = this.GetClientKey.spzt;
         header = Common.getHeader(this.GetClientKey.clientKey);
         if (spzt == -1){
             let params={clientKey: that.clientKey}
             Common.httpRequest({
              url: commonJs.url + '/qishui/qsapp/api/apply/spjl',
              method: "GET",
              params:params,
              success:(response)=> {
                if (response.data.code == '200') {
                  if (res.data.code == "200") {
                    var row = res.data.rows[0]
                    var spzt = res.data.rows[0].spzt;
                    var flowTypeZt = [];
                    flowTypeZt.push(row.sfyz);
                    flowTypeZt.push(row.hyzk);
                    flowTypeZt.push(row.hkbxx);
                    flowTypeZt.push(row.gffp);
                    flowTypeZt.push(row.gfht);
                    flowTypeZt.push(row.sqs);
                    flowTypeZt.push(row.cqbc);
                    flowTypeZt.push(row.zlsh);
                    var index = 0;
                    for (var i = 0; i < flowTypeZt.length; i++) {
                        if (flowTypeZt[i] == 0) {
                        index = i + 1;
                        break;
                        }
                    }
                    var applyId = row.id;
                    Common.kydj(that);
                    let path='/ProcessManage/Process' + index ;
                    this.$router.push({
                       path: path,
                       query:{flowTypeZt:flowTypeZt.join(","),clientKey:clientKey,applyId:applyId,index:index,spzt:spzt}
                    });
                    } else {
                      that.$message.error('您未创建任何审核记录！');
                    }
                } else {
                  fail("新增方案失败," + response.data.msg);
                }
              },
              fail() {
                fail("新增方案失败");
              }
            })
         }   else if (spzt == 1 || spzt == 2 || spzt == 6 || spzt == 7){
            let path='/';
            this.$router.push({path: path,query:{applyId:that.applyId,clientKey:that.clientKey}});
            } else if (spzt == 3){
                let path='/ProcessManage/Process' + index;
                this.$router.push({path: path,query:{applyId:that.applyId,clientKey:clientKey}});
             } else if (spzt == 5){
                let path='/SubPages/Jftz';
                this.$router.push({path: path,query:{clientKey:that.clientKey,applyId:that.applyId,value:'1234567'}});
            }else{
                this.$router.push({path: '/index',query:{applyId:that.applyId,clientKey:that.clientKey}});
            }

     }
    },
}
</script>
<style scoped>
  >>>.weui-wepay-flow, .weui-wepay-flow-auto{
    margin-left: -105%;
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
  >>>.weui-wepay-flow, .weui-wepay-flow-auto{
    padding: 0;
  }
.container{
  width: 100%;
  height:100%
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
.title{
    text-align: center;
    font-size: 23px;
    color:#c0c0c0;
}
.time{
  margin-top: 1.1em;
  font-size: 8pt;
  margin-left: 2.5em;
  color: #8b8b8b;
}
.text{
  position: relative;
  margin-top: 10vw;
  margin-left: 12vw;
}
.textstatues{
  position: relative;
  font-size: 4vw;
  z-index: 99;
}
.texttime{
  margin-top: 14rpx;
  font-size: 3vw;
  float: right;
  margin-right: 6vw;
  color: #b2b2b2;
}
.textinfo{
  margin-top: 1vw;
  font-size: 3vw;
  margin-left: 8vw;
  color: #b2b2b2;
}
.textinner{
  position: relative;
  z-index: 98;
  margin-bottom: 3vw;
}
.line{
  position: absolute;
  height:13vw;
  width: 0px;
  border: 1px solid #2c4c7f;
  top: 3vw;
  left: 1.1vw;
}
.cicrl{
  z-index: 99;
  top:2vw;
  left: 2rpx;
  width: 2vw;
  height: 2vw;
  border-radius: 2vw;
  background-color: #163b72;
  position: absolute;
}
</style>
