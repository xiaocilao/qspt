<template>
    <div>
        <div class='background' :hidden='backhidden'></div>
        <image src='@/assets/static/images/error.png' class='erroeimg'></image>
        <div class='errortip'>系统出错，请刷新</div>
    </div>
</template>
<script>
export default {
    data(){
        return{
            backhidden:true
        }
    }
}
</script>
<style scoped>
.erroeimg{
  width: 312rpx;
  height: 246rpx;
  margin-top: 234rpx;
  margin-left: 226rpx;
}
.errortip{
  margin-top: 34rpx;
  color: #656565;
  text-align: center;
  font-size: 30rpx;
}
</style>
