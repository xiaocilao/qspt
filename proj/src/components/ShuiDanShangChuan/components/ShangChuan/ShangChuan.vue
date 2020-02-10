<template>
  <div class="shangchuan">
    <div class="container-top">
      <img class="container-top-icon" src="data:image/png;base64,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cy8xLjEvIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOnhtcE1NPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvbW0vIiB4bWxuczpzdEV2dD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL3NUeXBlL1Jlc291cmNlRXZlbnQjIiB4bWxuczp0aWZmPSJodHRwOi8vbnMuYWRvYmUuY29tL3RpZmYvMS4wLyIgeG1sbnM6ZXhpZj0iaHR0cDovL25zLmFkb2JlLmNvbS9leGlmLzEuMC8iIHBob3Rvc2hvcDpJQ0NQcm9maWxlPSJEaXNwbGF5IiBwaG90b3Nob3A6Q29sb3JNb2RlPSIzIiBkYzpmb3JtYXQ9ImltYWdlL3BuZyIgeG1wOk1ldGFkYXRhRGF0ZT0iMjAxOC0xMS0yM1QxNjo1ODoxNyswODowMCIgeG1wOkNyZWF0ZURhdGU9IjIwMTgtMTEtMTZUMTU6NTI6MjIrMDg6MDAiIHhtcDpNb2RpZnlEYXRlPSIyMDE4LTExLTIzVDE2OjU4OjE3KzA4OjAwIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOmU2ODlhYjZiLWZjNGQtNDg2MS1hZDBlLTJhNjkwMWNjZTNlMCIgeG1wTU06T3JpZ2luYWxEb2N1bWVudElEPSJ4bXAuZGlkOmE5ODkyYjkwLWZiYmEtNDc4Yi04YTE1LWMyMWM2NWIyYTMyOCIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDphOTg5MmI5MC1mYmJhLTQ3OGItOGExNS1jMjFjNjViMmEzMjgiIHRpZmY6T3JpZW50YXRpb249IjEiIGV4aWY6UGl4ZWxYRGltZW5zaW9uPSI3NCIgZXhpZjpQaXhlbFlEaW1lbnNpb249Ijc0Ij4gPHhtcE1NOkhpc3Rvcnk+IDxyZGY6U2VxPiA8cmRmOmxpIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCBDQyAoTWFjaW50b3NoKSIgc3RFdnQ6Y2hhbmdlZD0iLyIgc3RFdnQ6d2hlbj0iMjAxOC0xMS0yMlQxNDoyNDozOSswODowMCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDphOTg5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"/>
      <span class="container-top-text">税单扫描上传</span>
      <div class="container-top-close" @click="goback">返回列表</div>
    </div>
    <div class="container-center">
      <div class="container-center-left">
        <!--<iframe class="gpy" src="/static/gaopaiyi/index.html"></iframe>-->
        <div class="gpy"></div>
      </div>
      <div class="container-center-right">
        <p>征收项目名称</p>
        <input type="text" v-model="formData.zsxmmc"/>
        <p>税务机关名称</p>
        <input type="text" v-model="formData.swjgmc"/>
        <p>纳税人名称</p>
        <input type="text" v-model="formData.nsrmc"/>
        <p>纳税人编号</p>
        <input type="text" v-model="formData.nsrbh"/>
        <p>凭证序号</p>
        <input type="text" v-model="formData.pzxh"/>
        <p>金额合计（小写）</p>
        <input type="text" v-model="formData.jehjxx"/>
        <p>金额合计（大写）</p>
        <input type="text" v-model="formData.jehjdx"/>
        <p>税款限缴日期</p>
        <input type="text" v-model="formData.skxjrq"/>
        <p>扣款</p>
        <input type="text" v-model="formData.kk"/>
        <p>印花税</p>
        <input type="text" v-model="formData.yhs"/>
        <div class="buttons">
          <button class="button-shibie" :class="shibieTag==1?'disabled':(shibieTag==2?'retry':'')" @click="shibie">
            {{shibieTag==0?'扫描识别':(shibieTag==1?'识别中...':'重拍')}}
          </button>
          <button class="button-save" :class="baocunTag==1?'disabled':''" @click="tijiao" v-if="shibieTag==2">
            {{baocunTag==1?'正在保存':'保存'}}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "ShangChuan",
    props:{
      applyId:{
        type:String,
        default:null
      }
    },
    data() {
      return {
        req: null,
        shibieTag: 2,
        baocunTag: 0,
        formData: {
          applyId: "",
          zsxmmc: "",
          swjgmc: "",
          nsrmc: "",
          nsrbh: "",
          pzxh: "",
          jehjxx: "",
          jehjdx: "",
          skxjrq: "",
          kk: "",
          yhs: ""
        }
      }
    },
    mounted() {
      this.formData.applyId = this.applyId;
      if(!this.formData.applyId){
        this.$router.replace({name:'SDSCLieBiao'});
      }
      // var iframe=this.$el.getElementsByClassName("gpy")[0];
      // iframe.onload=function () {
      //   setTimeout(function () {
      //     try {
      //       iframe.contentWindow.start();
      //     }catch (e) {
      //       console.log("高拍仪启动失败");
      //     }
      //   },100);
      // };
    },
    beforeDestroy() {
      // try {
      //   this.$el.getElementsByClassName("gpy")[0].contentWindow.stop();
      // } catch (e) {}
      // if (this.req) {
      //   this.req.abort();
      // }
    },
    methods: {
      goback(){
        debugger;
        this.$emit('changePage','',2)
      },
      shibie() {
        var self = this;
        var gpy = self.$el.getElementsByClassName("gpy")[0].contentWindow;
        if (self.shibieTag == 0) {
          self.shibieTag = 1;
          setTimeout(function () {
            try {
              gpy.shibie(function (result) {
                self.shibieTag = 2;
                self.formData.zsxmmc = result.zsxmmc ? result.zsxmmc : "";
                self.formData.swjgmc = result.swjgmc ? result.swjgmc : "";
                self.formData.nsrmc = result.nsrmc ? result.nsrmc : "";
                self.formData.nsrbh = result.nsrbh ? result.nsrbh : "";
                self.formData.pzxh = result.pzxh ? result.pzxh : "";
                self.formData.jehjxx = result.jehjxx ? result.jehjxx : "";
                self.formData.jehjdx = result.jehjdx ? result.jehjdx : "";
                self.formData.skxjrq = result.skxjrq ? result.skxjrq : "";
                self.formData.sdtp = gpy.photo;
              }, function () {
                myalert("识别失败");
                self.shibieTag = 0;
                try {
                  gpy.start();
                }catch (e) {
                  console.log("高拍仪启动失败");
                }
              });
            }catch (e) {
              myalert("识别失败");
              self.shibieTag = 0;
              try {
                gpy.start();
              }catch (e) {
                console.log("高拍仪启动失败");
              }
            }
          }, 100);
        } else if (self.shibieTag == 2) {
          if(self.req){
            self.req.abort();
          }
          try {
            gpy.start();
          }catch (e) {
            console.log("高拍仪启动失败");
          }
          self.shibieTag = 0;
          self.baocunTag = 0;
          self.formData.zsxmmc = "";
          self.formData.swjgmc = "";
          self.formData.nsrmc = "";
          self.formData.nsrbh = "";
          self.formData.pzxh = "";
          self.formData.jehjxx = "";
          self.formData.jehjdx = "";
          self.formData.skxjrq = "";
          self.formData.sdtp = "";
        }
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
      tijiao() {
        var self = this;
        if (self.baocunTag == 1) {
          return;
        }
        for (var i in self.formData) {
          if (!self.formData[i]) {
            self.loading(self,"信息填写不完整");
            return;
          }
        }
        if (!new RegExp("^\\d\\d\\d\\d-\\d{1,2}-\\d{1,2}$", "g").test(self.formData.skxjrq)) {
          self.loading(self,"税款限缴日期格式错误");
          return;
        }
        self.baocunTag = 1;

        let postData= new URLSearchParams();
        postData.append('applyId',self.formData.applyId);
        postData.append('zsxmmc',self.formData.zsxmmc);
        postData.append('swjgmc',self.formData.swjgmc);
        postData.append('nsrmc',self.formData.nsrmc);
        postData.append('nsrbh',self.formData.nsrbh);
        postData.append('pzxh', self.formData.pzxh);
        postData.append('jehjxx',self.formData.jehjxx);
        postData.append('jehjdx',self.formData.jehjdx);
        postData.append('skxjrq',self.formData.skxjrq);
        postData.append('kk',self.formData.kk);
        postData.append('yh',self.formData.yh);
        debugger;
        this.$http.post('/qishui/qsapp/api/apply/scsd',postData).then(function (data) {
          if(data.data.code==200){
            self.$emit('changePage',"",2);
            this.loading(this,'上传成功');
          }else{
            this.loading(this,'操作失败');
          }
        });
      }
    }
  }
</script>

<style scoped>
  .shangchuan {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    position: relative;
    width: 100%;
    height: 600px;
    padding-top: 35px;
    background-color: white;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
    overflow: hidden;
    box-shadow: 0px 0px 10px gainsboro;
  }

  .container-top {
    position: absolute;
    left: 0px;
    top: 0px;
    width: 100%;
    height: 35px;
    line-height: 30px;
    background-color: #ebf0f1;
  }

  .container-top-icon {
    width: 20px;
    height: 20px;
    vertical-align: middle;
    margin-left: 28px;
  }

  .container-top-text {
    font-size: 16px;
    vertical-align: middle;
    margin-left: 8px;
    color: #646363;
  }

  .container-top-close {
    position: absolute;
    right: 28px;
    top: 50%;
    width: 61px;
    height: 20px;
    line-height: 21px;
    margin: -10px 0px;
    font-size: 12px;
    vertical-align: middle;
    color: black;
    border: 1px solid #d7dee4;
    text-align: center;
    cursor: pointer;
    border-radius: 3px;
    background-color: white;
  }

  .container-center {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    position: relative;
    width: 100%;
    height: 100%;
    padding-right: 343px;
    overflow: hidden;
  }

  .container-center-right {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    position: absolute;
    right: 0px;
    top: 0px;
    padding-top: 14px;
    width: 343px;
    height: 100%;
    overflow: auto;
  }

  .container-center-right > p {
    font-size: 13px;
    color: #000000;
    width: 252px;
    margin: 7px auto 7px;
  }

  .container-center-right > input {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    display: block;
    width: 252px;
    height: 32.2px;
    font-size: 11.9px;
    color: #000000;
    border: 0.7px solid #d7dee4;
    padding-left: 7px;
    -webkit-border-radius: 0px 4.9px 4.9px 4.9px;
    -moz-border-radius: 0px 4.9px 4.9px 4.9px;
    border-radius: 0px 4.9px 4.9px 4.9px;
    margin: 0px auto;
    outline: none;

  }

  .buttons {
    width: 252px;
    height: auto;
    font-size: 0px;
    margin: 30px auto 0px;
  }

  .buttons > button {
    outline: none;
  }

  .button-shibie {
    width: 84px;
    height: 26px;
    background-color: #4381e6;
    color: white;
    font-size: 11.9px;
    border: none;
    -webkit-border-radius: 0px 3px 3px 3px;
    -moz-border-radius: 0px 3px 3px 3px;
    border-radius: 0px 3px 3px 3px;
    cursor: pointer;
  }

  .button-shibie:hover {
    background-color: #326aba;
  }

  .button-shibie:active {
    background-color: #4688f2;
  }

  .button-shibie.disabled {
    background-color: #81b3f0;
    cursor: not-allowed;
  }

  .button-shibie.retry {
    background-color: white;
    border: 1px solid #dcdfe6;
    color: black;
  }

  .button-shibie.retry:hover {
    border: 1px solid #c6e2ff;
    background-color: #ecf5ff;
    color: #409eff;
  }

  .button-shibie.retry:active {
    border: 1px solid #3a8ee6;
    color: #3a8ee6;
  }

  .button-save {
    width: 84px;
    height: 26px;
    background-color: #4381e6;
    color: white;
    font-size: 11.9px;
    border: none;
    margin-left: 28px;
    -webkit-border-radius: 0px 3px 3px 3px;
    -moz-border-radius: 0px 3px 3px 3px;
    border-radius: 0px 3px 3px 3px;
    cursor: pointer;
  }

  .button-save:hover {
    background-color: #326aba;
  }

  .button-save:active {
    background-color: #4688f2;
  }

  .button-save.disabled {
    background-color: #81b3f0;
    cursor: not-allowed;
  }

  .container-center-left {
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    position: relative;
    width: 100%;
    height: 100%;
    padding: 21px 0px 21px 35px;
    overflow: hidden;
  }

  .gpy {
    width: 100%;
    height: 100%;
    background-color: #f2f7f8;
    border: none;
  }

</style>
