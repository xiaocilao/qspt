<template>
    <div>
        <div class='background' :hidden='backhidden'></div>
        <div class='toperimg'>
        </div>
        <div class='inut1' :hidden='inputhidden'>
            <image src='@/assets/static/images/wechat.png' class='wechaticon'></image>
            <div class='phonetip' :hidden='tipdis'>*手机号不正确</div>
            <x-input :max='11' type='number' class='wechat' placeholder='手机号码' placeholder-class='pla' 
            @on-blur='wechatchange'></x-input>
        </div>
        <div style='margin-top:20px;' class='inut1' :hidden='inputhidden'>
            <image src='@/assets/static/images/yzm.png' class='wechaticon'></image>
            <div class='hqyzm' :style="'color:'+djscolor" @click='djstap'>{{djstxt}}</div>
            <x-input :disabled="disabled" type='number' :max='6' class='wechat' placeholder='验证码' 
                placeholder-class='pla' @on-input='yzm' @on-blur='yzmtc'></x-input>
        </div>
        <div class='inut' :hidden='hasnamehidden'>{{text}},欢迎！</div>
        <div class='login' @click='login' :style="'background-color:'+color">登录</div>
        <div class='tip'>* 未登录用户将不能办理业务</div>
        <div class='bkcz' :style="'display:'+bkcz">页面加载中，请稍等......</div>
    </div>
</template>
<script>
import { XInput } from 'vux'
export default {
    components:{
        XInput
    },
    data(){
        return{
            backhidden:true,
            wechatId: "",
            hasnamehidden:true,
            inputhidden:false,
            text:"",
            djs: 60,
            djstxt: "获取验证码",
            djscolor:"#1c3e72",
            djstap:"djs",
            cango1:0,
            cango2:0,
            color:'#cdd4df',
            yzmdis:true,
            tipdis:true,
            disabled:true,
            yzm:"",
            tag:0,
        }
    },
    methods:{
        wechatchange:function(e){
            var wechat = e.detail.value;
            if (commonJs.phone(wechat)){
            this.data.cango1 = 1;
            this.data.yzmdis = false;
            this.data.tipdis=true;
            }else{
            this.data.cango1 = 0;
            this.data.yzmdis = true;
            this.data.tipdis=false;
            }
            if (this.data.cango1 == 1 && this.data.cango2 == 1) {
            this.data.color = "#1c3e72";
            } else {
            this.data.color = "#cdd4df";
            }
            this.setData(this.data);
            this.setData({
            wechatId:wechat,
            phone:wechat,
            })
        },
        yzmtc:function(){
            if (this.data.djstap=="djs"&&this.data.tag==0){
            wx.showToast({
                title: '您还没有获取验证码！',
                icon:"none",
                duration:2000,
            })
            }
        },
        djs:function(){
            this.data.disabled = false;
            this.data.tag = 1;
            this.setData(this.data);
            var that = this;
            djs(this);
            wx.request({
            url: commonJs.url+'/qishui/qsapp/api/hqyzm',
            method:"GET",
            header: commonJs.getHeader(that.data.clientKey),
            data:{
                phoneNumber:that.data.phone,
            },
            success:function(res){
                console.log("验证码返回:");
                console.log(res);
            },
            fail: function () {
                commonJs.kydj(that);
                wx.showToast({
                title: '网络故障',
                })
            }
            })
        },
        djs1:function(){

        },
        login:function(){
            var that = this;
            var header;
            if ((this.data.cango1 == 1 && this.data.cango2 == 1) || this.data.text!="") {
            this.data.color = "#1c3e72";
            this.setData(this.data);
            commonJs.login(this, function () {
                header = commonJs.getHeader(that.data.clientKey);
                wx.request({
                url: commonJs.url + '/qishui/qsapp/api/apply/spjl',
                method: "GET",
                data: {
                    clientKey: that.data.clientKey
                },
                header: header,
                success: function (res) {
                    console.log(res);
                    if (res.statusCode == 200) {
                    if (res.data.code == "200") {
                        if (res.data.rows[0].spzt != 1 || res.data.rows[0].spzt != "1"){
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
                        if (i == flowTypeZt.length){
                            index = 8;
                        }
                        var id = row.id
                        wx.redirectTo({
                            url: '/pages/personal' + index + '/personal' + index + '?clientKey=' + that.data.clientKey + "&flowTypeZt=" + flowTypeZt.join(",") + "&applyId=" + id + "&index=" + index + "&spzt=" + spzt,
                        });
                        }else{
                        var row1 = res.data.rows[0]
                        var spzt = res.data.rows[0].spzt;
                        var id = row1.id
                        wx.redirectTo({
                            url: '/pages/personal' + 8 + '/personal' + 8 + '?clientKey=' + that.data.clientKey + "&flowTypeZt=1,1,1,1,1,1,1,0" + "&applyId=" + id + "&index=" + 8 + "&spzt=" + spzt,
                        });
                        }
                        
                    } else {
                        console.log(res.data.msg);
                        wx.redirectTo({
                        url: '/pages/personal1/personal1?clientKey=' + that.data.clientKey + "&&flowTypeZt=" + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "," + 0 + "&index=" + 1 + "&spzt=0"
                        });
                    }
                    } else {
                    wx.showToast({
                        title: '网络请求失败！',
                        icon: 'none',
                        duration: 2000//持续的时间

                    })
                    }


                }
                });
            });
            } else {
            this.data.color = "#cdd4df";
            }
            
        },
        yzm:function(e){
            this.data.yzm = e.detail.value;
            if(e.detail.value!=""){
            this.data.cango2 = 1;
            }else{
            this.data.cango2 = 0;
            }
            if (this.data.cango1 == 1 && this.data.cango2 == 1) {
            this.data.color = "#1c3e72";
            } else {
            this.data.color = "#cdd4df";
            }
            this.setData(this.data);
        }
    }
}
</script>
<style scoped>
.toperimg{
  width: 154rpx;
  height: 154rpx;
  margin-left: 298rpx;
  margin-top: 100rpx;
  overflow: hidden;
  border-radius: 10rpx;
  
}
.inut1{
  width: 602rpx;
  height: 86rpx;
  margin: auto;
  margin-top: 100rpx;
  border-radius: 86rpx;
  font-size: 30rpx;
  border: 1rpx solid #c8c8c8;
}
.inut{
  width: 602rpx;
  height: 86rpx;
  margin: auto;
  margin-top: 100rpx;
  border-radius: 86rpx;
  font-size: 30rpx;
  text-align: center;
  line-height: 86rpx;
  color:  #b0afb0;
}
.wechat{
  width: 400rpx;
  height: 86rpx;
  margin-left: 24rpx;
  font-size: 30rpx;
  float: left;
}
.pla{
  font-size: 30rpx;
  color: #b0afb0;
}
.wechaticon{
  width: 26rpx;
  height: 36rpx;
  margin-left: 40rpx;
  margin-top: 28rpx;
  float: left;
}
.login{
  width: 602rpx;
  height: 86rpx;
  background-color: #85b2dc;
  margin: auto;
  margin-top: 64rpx;
  border-radius: 86rpx;
  text-align: center;
  color: white;
  line-height: 86rpx;
}
.tip{
  height: 30rpx;
  font-size: 24rpx;
  color: #b7b6b7;
  text-align: center;
  width: 100%;
  margin: auto;
  margin-top: 32rpx;
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
.hqyzm{
  position: absolute;
  font-size: 26rpx;
  color: #395585;
  text-align: center;
  line-height: 86rpx;
  right: 110rpx;
}
.phonetip{
  color: red;
  font-size: 20rpx;
  position: absolute;
  right: 50rpx;
  margin-right: 50rpx;
  line-height: 86rpx;
}
</style>
