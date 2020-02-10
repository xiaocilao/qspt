<template>
    <div>
        <div class='background' :hidden='backhidden'></div>
        <image :src='imgurl' class='bookimg'></image>
        <div class='change' :hidden='qmdis'>
        <div class='qm' @click='close()'>取消</div>
        <div class='qx' @click='change()'>签名</div>
        </div>
        <div class='change' :hidden='cxqmdis'>
        <div class='qm' @click='change()'>重新签名</div>
        <div class='qx' @click='close()'>{{uploadtext}}</div>
        </div>
    </div>
</template>
<script>
export default {
    data(){
        return{
            qmdis:false,
            cxqmdis:true,
            uploadtext:'同意上传'
        }
    },
    methods:{
         close:function(){
            wx.navigateBack({
            delta: 1,
            })
        },
        change:function(){
            wx.navigateTo({
            url: '../sign/sign?clientKey=' + this.data.clientKey + "&flowTypeZt=" + this.data.flowTypeZt + "&index=" + 6 + "&applyId=" + this.data.applyId + "&qmType=" + this.data.qmType+"&id="+this.data.imgid,
            })
        }
    }
}
</script>
<style scoped>
.close{
  width: 80%;
  margin: auto;
  height: 100rpx;
  text-align: right;
  font-size: 50rpx;
  line-height: 100rpx;
}
.bookimg{
  width: 100%;
  height: 1100rpx;
  margin-top: 28rpx;
}
.change{
  position: fixed;
  width: 100%;
  height: 104rpx;
  line-height: 104rpx;
  bottom: 0rpx;
  text-align: center;
  color: #575353;
  letter-spacing: 1rpx;
  background-color: #F9F9F9;
}
.qm{
  float: left;
  margin-left: 50rpx;
  width: 184rpx;
  height: 54rpx;
  font-size: 28rpx;
  margin-top: 28rpx;
  line-height: 54rpx;
  border-radius: 54rpx;
  text-align: center;
  color: #8cb2ec;
  border: 1rpx solid #8cb2ec;
}
.qx{
  float: right;
  margin-right: 50rpx;
  width: 184rpx;
  height: 54rpx;
  font-size: 28rpx;
  margin-top: 28rpx;
  line-height: 54rpx;
  background-color: #4382e6;
  border-radius: 54rpx;
  text-align: center;
  color: white;
}
</style>
