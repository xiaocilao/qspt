<template>
    <div>
        <div class='topertext'>{{topertext}}</div>
        <div class='tip' :style="'display:'+cgdis">
        <div class='tipinner'>
            <div>支付时间：2018-11-25 10:23:25</div>
            <div>支付类型：契税</div>
            <div>支付状态：支付成功</div>
        </div>
        </div>
        <!--领取方式-->
        <div class='lqfs' :style="'display:'+cgdis">
          <popup-picker title="领取方式" :data="array" v-model="lqindex" value-text-align="left"
          :placeholder="array[0][index]" @on-change="bindPickerChange"></popup-picker>
        </div>
        <div class='lqdz' :style="'display:'+lqdzdis">
          <div class='lqdztext'>领取地址</div>
          <div class='lqdzinput1'>
              四川省自贡市自流井区自流景区汇东
              路230号
          </div>
        </div>
        <div class='sjr' :style="'display:'+yjdis">
          <text class='sjrtext1'>收 件 人</text>
          <el-input @click='sjrinput' class='sjrinput' placeholder='真实姓名' placeholder-class='pla'></el-input>
          <div class='tip1' :hidden='nametip'>*姓名不符合规范</div>
        </div>
        <div class='sjr' :style="'display:'+yjdis">
          <text class='sjrtext'>手机号码</text>
          <el-input type='number' @click='sjhminput()' class='sjhminput' placeholder='收件人的手机号码' placeholder-class='pla'></el-input>
          <div class='tip1' :hidden='idtip'>*手机号不符合规范</div>
        </div>
        <div class='lqdz' :style="'display:'+yjdis">
          <div class='lqdztext'>领取地址</div>
          <textarea @click='lqdzinput()' class='lqdzinput' placeholder='详细收货地址如楼层，门牌号' placeholder-class='pla'></textarea>
        </div>
        <div class='return' @click='return1()' :style="'background-color:'+back+';display:'+bottomdis">{{bottomtext}}</div>
    </div>
</template>
<script>
import { Datetime, PopupPicker, Group, Cell, Picker, Divider, XSwitch } from 'vux'
export default {
    components:{
        Datetime,
        PopupPicker,
        Group,
        Cell,
        Picker,
        Divider,
        XSwitch
    },
    data(){
        return{
            bottomtext:'保存并退出',
            bottomdis:'none',
            topertext:"",
            toperimg:"img/ing.png",
            zt:4,
            lqindex:0,
            array:["完税凭证邮寄方式","自取","邮寄"],
            color:"#CCCCCC",
            lqdzdis:"true",
            yjdis:"true",
            cgdis:"true",
            sjr:"",
            sjhm:"",
            lqdz:"",
            applyId:"",
            back:"#CDD4E0",
            nametip:true,
            idtip:true
        }
    },
    methods:{
        bindPickerChange:function(e){
            var i = e.detail.value;
            if(i==0){
            this.data.color = "#CCCCCC;"
            this.data.lqindex = i;
            this.data.lqdzdis = "none";
            this.data.yjdis="none";
            }else if(i==1){
            this.data.color = "#666666";
            this.data.lqindex = i;
            this.data.lqdzdis = "block";
            this.data.yjdis = "none";
            }else{
            this.data.color = "#666666";
            this.data.lqindex = i;
            this.data.lqdzdis = "none";
            this.data.yjdis = "block";
            }
            this.setData(this.data);
            sfgl(this);
        },
        return1:function(){
            if (this.data.lqindex == 2 && this.data.sjr != "" && this.data.sjhm != "" && this.data.lqdz != ""){
            var url = commonJs.url;
            var that = this;
              that.$router.push('index');
            } else if (this.data.topertext != "支付成功" && this.data.lqindex != 1 ){
              let path="/ProcessManage/Process09"
              this.$router.push({
                       path: path,
                       query:{applyId:applyId,clientKey:""}
                    });
            // wx.redirectTo({
            //     url: '/pages/personal9/personal9?applyId=' + this.data.applyId + "&clientKey=" + this.data.clientKey,
            // })
            } else if (this.data.lqindex == 1 ){
            var url = commonJs.url;
            var that = this;
            wx.showModal({
                title: '警告！',
                content: '一旦选择自取将无法再选择邮寄，确认要自取么？',
                confirmText: '确认',
                cancelText: '取消',
                success: function (res) {
                if (res.confirm) {
                    wx.request({
                    url: url + "/qishui/qsapp/api/apply/zq",
                    method: "GET",
                    header: commonJs.getHeader(that.data.clientKey),
                    data: {
                        applyId: that.data.applyId,
                    },
                    success: function () {
                    }
                    })
                    wx.showToast({
                    title: '选择自取成功！',
                    icon: "none",
                    duration: 2000,
                    })
                    wx.navigateBack({
                    delta: 1,
                    })
                }
                }
            })

            }else {
            this.setData({
                back: "#CDD4E0",
            })
            }

        },
        sjrinput:function(e){
            if (commonJs.xm(e.detail.value) || e.detail.value==""){
            this.setData({
                nametip:true
            })
            }else{
            this.setData({
                nametip: false
            })
            }
            this.setData({
            sjr: e.detail.value
            });
            sfgl(this);
        },
        sjhminput:function(e){
            if (commonJs.phone(e.detail.value) || e.detail.value == "") {
            this.setData({
                idtip: true
            })
            } else {
            this.setData({
                idtip: false
            })
            }
            this.setData({
            sjhm:e.detail.value
            });
            sfgl(this);
        },
        lqdzinput:function(e){
            this.setData({
            lqdz:e.detail.value
            });
            sfgl(this);
        }
    }
}
</script>
<style scoped>
.lqfspicker{
  width: 720rpx;
  height: 82rpx;
}
.toperimg{
  width: 110rpx;
  height: 110rpx;
  margin-left: 322rpx;
  margin-top: 58rpx;
}
.topertext{
  color: #2C4C80;
  text-align: center;
  font-size: 33rpx;
  margin-top: 30rpx;
}
.tip{
  margin-top: 30rpx;
  color: #B3B3B3;
  border-top: 1rpx solid;
  border-bottom: 1rpx solid;
}
.tipinner{
  margin-top: 24rpx;
  margin-left: 48rpx;
  margin-bottom: 26rpx;
  font-size: 22rpx;
}
.lqfs{
  margin-top: 34rpx;
  background-color: white;
  height: 82rpx;
  width: 100%;
  border-bottom: 1rpx solid #F9F9F9;
}
.lqfstext{
  font-size: 30rpx;
  float: left;
  margin-left: 46rpx;
  /* margin-top: 24rpx; */
  height: 82rpx;
  line-height: 82rpx;
}
.lqdz{
  border: 1rpx solid white;
  height: 164rpx;
  width: 100%;
  background-color: white;
}
.lqdztext{
  margin-top: 28rpx;
  margin-left: 46rpx;
  font-size: 30rpx;
  float: left;
}
.lqdzinput{
  width:500rpx;
  height: 140rpx;
  float: left;
  margin-left: 46rpx;
  margin-top: 30rpx;
  font-size: 30rpx;
  color:#4d4d4d;
}
.lqdzinput1{
  width:500rpx;
  height: 140rpx;
  float: left;
  margin-left: 41rpx;
  margin-top: 28rpx;
  font-size: 30rpx;
  color: #666666;
}
.pikerimg{
  width: 28rpx;
  height: 28rpx;
  float: right;
  margin-top: 30rpx;
  margin-right: 10rpx;
}
.picker{
  width: 400rpx;
  float: left;
  font-size: 30rpx;
  margin-left: 41rpx;
  height: 82rpx;
  line-height: 82rpx;
  color: #CCCCCC;
}
.sjr{
  border: 1rpx solid white;
  height: 82rpx;
  width: 100%;
  background-color: white;
  border-bottom-color: #F9F9F9;
  position: relative;
}
.sjrtext{
  /* margin-top: 22rpx; */
  line-height: 82rpx;
  height: 82rpx;
  margin-left: 46rpx;
  font-size: 30rpx;
  float: left;
}
.sjrtext1{
  margin-left: 46rpx;
  font-size: 30rpx;
  float: left;
  letter-spacing: 3rpx;
  height: 82rpx;
  line-height: 82rpx;
}
.sjrinput{
  width: 300rpx;
  float: left;
  margin-left: 41rpx;
  font-size: 30rpx;
  color:#4d4d4d;
  line-height: 82rpx;
  height: 82rpx;
}
.pla{
  color: #CCCCCC;
}
.sjhminput{
  width: 300rpx;
  float: left;
  margin-left: 44rpx;
  /* margin-top: 16rpx; */
  font-size: 30rpx;
  color:#4d4d4d;
  line-height: 82rpx;
  height: 82rpx;
}
.return{
  width:544rpx;
  height:72rpx;
  background:rgba(44,76,128,1);
  border-radius:6rpx;
  position: absolute;
  top: 1024rpx;
  left: 100rpx;
  color: white;
  text-align: center;
  line-height: 72rpx;
  font-size: 26rpx;
}
.tip1{
  color: red;
  font-size: 20rpx;
  position: absolute;
  right: 50rpx;
  border:none;
}
</style>
