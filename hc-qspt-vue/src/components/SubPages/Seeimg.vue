<template>
    <div>
        <div class='background' :hidden='backhidden'></div>
        <image :class='textclass[0]' :src="imgurl" @click='seebigimg()' mode='aspectFit'></image>
        <div :class='textclass[1]'></div>
        <div :class='textclass[2]'>
        <div :class='textclass[4]' @click='rephoto()'>重拍</div>
        <div :class='textclass[3]' @click='cancle()'>{{text}}</div>
        </div>
    </div>
</template>
<script>
export default {
    data(){
        return{
            backhidden:true
        }
    },
    methods:{
         cancle:function(){
            var pages = getCurrentPages();
            var prevPage = pages[pages.length - 2];
            if (this.data.cards[this.data.i].resonhidden){
            if (this.data.cards[this.data.i].resonhidden == "true" || this.data.cards[this.data.i].resonhidden ==true){
                prevPage.data.cards[this.data.i].resonhidden = true;
            }else{
                prevPage.data.cards[this.data.i].resonhidden = false;
            }
            }
            if (this.data.cards[this.data.i].files[this.data.j].border){
            prevPage.data.cards[this.data.i].files[this.data.j].border = this.data.cards[this.data.i].files[this.data.j].border;
            }
            prevPage.data.cards[this.data.i].files[this.data.j].flowFileUrl = this.data.imgurl;
            prevPage.setData(prevPage.data);
            prevPage.sfgl();
            wx.navigateBack({
            delta: 1,
            })
        },
        rephoto:function(){
            var that = this;
            commonJs.upfile(this, this.data.i, this.data.j,function(){
            that.data.imgurl = that.data.cards[that.data.i].files[that.data.j].flowFileUrl[0];
            var phone = wx.getSystemInfoSync();  //调用方法获取机型
            if (phone.platform == 'ios') {
                that.setData({
                text: "使用照片"
                });
            }else{
                that.data.text = "确定"
            }
            that.setData(that.data);
            },1);
        },
        seebigimg:function(){
            var that = this;
            wx.previewImage({
            current: that.data.imgurl, 
            urls: [that.data.imgurl]
            })
        },
    }
}
</script>
<style scoped>
.seeimg{
  width: 100%;
  height: 994rpx;
}
.seeimgios{
  width: 100%;
  height: 1100rpx;
}
.buttom{
  position: fixed;
  bottom: 0rpx;
  width: 100%;
  height: 100rpx;
  background-color: #F9F9F9;
}
.buttomios{
  position: fixed;
  bottom: 0rpx;
  width: 100%;
  height: 130rpx;
  background-color: #131313;
}
.tianchong{
  width: 100%;
  height: 110rpx;
}
.tianchongios{
  width: 100%;
  height: 38rpx;
}
.rephoto{
  width: 180rpx;
  height: 50rpx;
  border: 1rpx solid #4382E6;
  text-align: center;
  font-size: 25rpx;
  line-height: 50rpx;
  margin-top: 25rpx;
  float: left;
  border-radius: 50rpx;
  margin-left: 50rpx;
  color: #4382E6;
}
.cancle{
  width: 180rpx;
  height: 50rpx;
  border: 1rpx solid #4382E6;
  text-align: center;
  font-size: 25rpx;
  line-height: 50rpx;
  margin-top: 25rpx;
  float: right;
  border-radius: 50rpx;
  margin-right: 50rpx;
  color: #ffffff;
  background-color: #4382E6;
}
.rephotoios{
  width: 66rpx;
  height: 34rpx;
  text-align: center;
  font-size: 34rpx;
  line-height: 34rpx;
  margin-top: 46rpx;
  float: left;
  margin-left: 26rpx;
  color: white;
}
.cancleios{
  width: 132rpx;
  height: 34rpx;
  text-align: center;
  font-size: 34rpx;
  line-height: 34rpx;
  margin-top: 46rpx;
  float: right;
  margin-right: 26rpx;
  color: white;
}
</style>
