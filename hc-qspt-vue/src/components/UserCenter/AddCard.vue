<template>
    <div>
        <div class='infos'>
        <div class='info'>
            <popup-picker title="银行卡" :data="array" v-model="value" @on-change="bindPickerChange(value)" value-text-align="center" :placeholder="array[0][index]">
            </popup-picker>
        </div>
        <div class='info1' v-for="(item,index) in text" :key="index">
            <x-input :title="item.name" :placeholder="item.pla" novalidate  :show-clear="true"  v-model="cardid"
                @on-blur="input(index)" placeholder-align="left" data-index='-100' @on-focus='lose(index)' 
                :disabled='item.focus' @click='item.tap'></x-input>
            <div class='tip' :hidden='item.tip'>{{item.tipval}}</div>
        </div>
        </div>
        <div class="infos">
            <div class='info'>
              <x-input title="手机号" placeholder="银行预留手机" novalidate  :show-clear="false" v-model="bankphone"
                @on-blur="inputphone(index)" placeholder-align="left" data-index='-100' @on-focus='lose(index)'></x-input>
                <div class='tip' :hidden='tip'>*手机号不正确!</div>
            </div>
            <div class='info1' :hidden='yzmhidden'>
                <x-input title="验证码" placeholder="6位验证码" novalidate :show-clear="false" v-model="yzm"
                @on-blur="inputyzm(index)" placeholder-align="left" data-index='-100' 
                @on-focus='lose(index)' type='number' :max='6' :disabled='disabled' @click='yzmtc'></x-input>
                <div class='send' :style="'color:'+djscolor+';'+'font-size:'+djssize" @click='yanzheng(djstap)'>{{djstxt}}</div>
            </div>
        </div>
        <div class='error'></div>
        <div class='next' >
          <div class='nexttext' :style="'background-color:'+back" @click='nexttext()'>完成绑定</div>
        </div>
    </div>
</template>
<script>
import { XInput } from 'vux'
import { Datetime, PopupPicker, Group, Cell, Picker, XButton, Divider, XSwitch } from 'vux'
export default {
    components: {
    PopupPicker,
    Group,
    Picker,
    XButton,
    Divider,
    Cell,
    XSwitch,
    Datetime,
    XInput
  },
    data(){
        return {
            yzmhidden:true,
            djssize:'30rpx',
            disabled:true,
            value:[''],
            array: [["选择你的银行卡", "中国建设银行", "乐山市商业银行", "中国邮政银行","中国招商银行"]],
            index:0,
            cardclass:"inputpla",
            text:[
                {
                    name:"卡　号",
                    pla:"填写你的银行卡号",
                    value:"",
                    tip:true,
                    tipval:"*银行卡号不正确",
                    type1:'number',
                    maxlength:19,
                    focus: true,
                    tap:'tap'
                },
                {
                    name: "姓　名",
                    pla: "持卡人姓名",
                    value: "",
                    tip: true,
                    tipval: "*姓名不正确",
                    type1:'text',
                    maxlength:12,
                    focus: false,
                    tap: 'tap1'
                },
                {
                    name: "身份证",
                    pla: "持卡人身份证",
                    value: "",
                    tip: true,
                    tipval: "*身份证不正确",
                    type1:'text',
                    maxlength:18,
                    focus: false,
                    tap: 'tap1'
                }
            ],
            cardid:"",
            banktype:"",
            bankphone:"",
            yzm:"",
            back:"#cdd4df",
            djs: 60,
            djstxt: "获取验证码",
            djscolor: "#1c3e72",
            djstap: "djs",
            width:"300rpx",
            tip:true,
        }
    },
    methods:{
        nexttext(){
            if (this.data.length){
            if (this.data.back =="#1c3e72"){
                window.history(-1)
            }
                
            }
            
        },
          bindPickerChange(value){
            var i = value[0];
            var that = this;
            
            console.log(that.text[0])
            if (this.index != 0 && that.text[0].value != ""){
            wx.showModal({
                title: '提示：',
                content: '更换银行卡会导致清空当前卡号',
                success: function(res) {
                if(res.confirm){
                    that.text[0].value = "";
                    that.text[0].tip = true;
                }
                },
            })
            }else{
            this.text[0].value = "";
            this.text[0].tip = true;
            }
            if(i==0){
            this.text[0].focus=true;
            this.text[0].tap='tap';
            this.cardclass = "inputpla";
            this.setData(this.data);
            }else{
            this.text[0].focus = false;
            this.text[0].tap = 'tap1';
            this.cardclass = "inputpla1";
            }
            switch(i){
            case "0":
                this.data.cardtype="";
                break;
            case "1":
                this.data.cardtype = "中国建设银行";
                break;
            case "2":
                this.data.cardtype = "乐山市商业银行";
                break;
            case "3":
                this.data.cardtype = "中国邮政银行";
                break;
            case "4":
                this.data.cardtype = "中国招商银行";
                break;
            }
            this.index=value
            sfgl(this);
        },
        yzmtc: function () {
            // if (this.data.djstap == "djs") {
            // wx.showToast({
            //     title: '您还没有获取验证码！',
            //     icon: "none",
            //     duration: 2000,
            // })
            // }
        },
        tap:function(e){
            var i = e.currentTarget.dataset.index;
            if (i == "0" || i == 0){
            wx.showToast({
                title: '您还没有选择开户行',
                icon: "none",
                duration: 2000,
            })
            }
        },
        tap1:function(){

        },
        lose:function(index){
            var i =index;
            switch (i) {
            case 0:
            case "0":
                this.text[i].tip = true;
                break;
            case 1:
            case "1":
                this.text[i].tip = true;
                break;
            case 2:
            case "2":
                this.text[i].tip = true;
                break;
            default:
                this.tip = true;
                break;
            }
        },
        input(index){
            this.data.text[i].value =this.cardid;
            switch(i){
            case 0:
            case "0":
                if (!commonJs.yhkh(this.cardid) && this.cardid != ""){
                this.text[i].tip = false;
                this.text[i].value = this.cardid;
                }else{
                this.text[i].tip = true;
                this.text[i].value = this.cardid;
                }
                break;
            case 1:
            case "1":
                if (!commonJs.xm(this.cardid) && this.cardid != "") {
                this.text[i].tip = false;
                this.text[i].value =this.cardid;
                } else {
                this.text[i].tip = true;
                this.text[i].value = this.cardid;
                }
                break;
            case 2:
            case "2":
                if (!commonJs.idcard(e.detail.value) && e.detail.value != "") {
                this.text[i].tip = false;
                } else {
                this.text[i].tip = true;
                }
                break;
            }
            this.setData(this.data);
            sfgl(this);
        },
        inputphone:function(e){
            if (!commonJs.phone(this.bankphone) && this.bankphone!="") {
            this.tip = false;
            this.yzmhidden = true;
            }else if(!commonJs.phone(this.bankphone) && this.bankphone==""){
            this.data.tip = true;
            this.data.yzmhidden = true;
            } else {
            this.tip = true;
            this.yzmhidden=false;
            }
            sfgl(this);
        },
        inputyzm:function(e){
            sfgl(this);
        },
    }
}
</script>
<style scoped>
.infos{
  margin: auto;
  margin-top: 0.92em;
  border-top: 1rpx solid #efefef;
  border-bottom: 1rpx solid #efefef;
  background: white;
  width: 100%;
}
.info{
  margin: auto;
  width: 100%;
  height: 2.7em;
  margin-left: 42rpx;
}
.info1{
  position: relative;
  margin: auto;
  width: 712rpx;
  height: 102rpx;
  margin-left: 42rpx;
  border-top: 1rpx solid #efefef;
}
.title{
  float: left;
  font-size: 34rpx;
  margin-left: 16rpx;
  line-height: 102rpx;
}
.title{
  line-height: 102rpx;
}
.input{
  width: 14.4em;
  height: 102rpx;
  margin-left: 30rpx;
  line-height: 102rpx;
  font-size: 34rpx;
  float: left;
}
.input1{
  width: 100%;
  height: 60rpx;
  margin-left: 30rpx;
  margin-top: 22rpx;
  line-height: 80rpx;
  font-size: 34rpx;
  float: left;
  border-right: 1rpx solid #efefef
}
.inputpla{
  color: #cbcacb;
  line-height: 102rpx;
  font-size: 34rpx;
}
.inputpla1{
  color: black;
  line-height: 102rpx;
  font-size: 34rpx;
}
.infoimg{
  float: right;
  width: 18rpx;
  height: 34rpx;
  margin-right: -2rpx;
  margin-top: 36rpx;
}
.send{
  float: left;
  margin-left: 54rpx;
  color: #375687;
  font-size: 30rpx;
  line-height: 102rpx;
}
.next{
  position: fixed;
  text-align: center;
  margin: 0 auto;
  width: 100%;
  height: 3.16em;
  font-weight: 600;
  background-color: white;
  bottom: 0em;
  box-shadow: 0px 0px 10rpx 10rpx #F8F8F8;
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
  height: 200rpx;
}
.tip{
  color: red;
  font-size: 20rpx;
  position: absolute;
  right: 50rpx;
  line-height: 100rpx;
}
</style>
