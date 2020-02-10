<template>
    <div>
        <div class='background' :hidden='backhidden'></div>
        <image src='@/assets/static/images/nowork.png' class='noworkimg'></image>
        <div class='noworktext'>
        <div class='noworktip'>亲，你还没有办理完任何业务哟~</div>
        <div class='noworktip1'>进入【业务办理】-【自助办理】办理业务</div>
        </div>
    </div>
</template>
<script>
export default {
    data(){
        return{
            backhidden:true,
        }
    }
}
</script>

<style scoped>
.noworkimg{
  margin-top: 234rpx;
  width: 294rpx;
  height: 248rpx;
  margin-left: 244rpx;
}
.noworktext{
  margin-top: 46rpx;
  text-align: center;
}
.noworktip{
  color: #828282;
  font-weight: 28rpx;
}
.noworktip1{
  font-size: 22rpx;
  color: #bdbdbd;
  margin-top: 24rpx;
}
</style>
