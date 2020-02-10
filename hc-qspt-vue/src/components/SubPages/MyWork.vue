<template>
    <div>
        <div class='background' :hidden='backhidden'></div>
        <div class='works' v-for="(item,index) in text" :key="index">
        <div class='worktitle'>
            <div class='time'>{{item.time}}</div>
            <div class='status'>{{item.status}}</div>
        </div>
         <router-link :to="item.url" class='worktext'>
            <div class='name'>{{item.user}}</div>
            <div class='go'>查看</div>
        </router-link>
        </div>
        <router-link class='new' to="/pages/personal/personal">办理新业务
        </router-link>
    </div>
</template>
<script>
export default {
    data(){
        return{
             text:[
                {
                    time:"办理时间:2018-11-12",
                    status:"审核中",
                    user:"张泽轩",
                    url:"/pages/process/process",
                },
            ]
        }
    }
}
</script>

<style scoped>
.works{
  margin: auto;
  width: 644rpx;
  height: 160rpx;
  border-radius: 10rpx;
  margin-top: 50rpx;
  box-shadow: 0rpx 0rpx 5rpx 5rpx #f3f4f6;
  background: white;
}
.worktitle{
  width: 644rpx;
  height: 60rpx;
  background: #2c4c7f;
  border-radius: 10rpx 10rpx 0rpx 0rpx;
}
.worktext{
  width: 644rpx;
  height: 100rpx;
  background: white;
  border-radius: 0rpx 0rpx 10rpx 10rpx;
}
.time{
  font-size: 18rpx;
  color: white;
  float: left;
  margin-left: 32rpx;
  line-height: 60rpx;
}
.status{
  float: right;
  font-size: 26rpx;
  color: white;
  line-height: 60rpx;
  margin-right: 32rpx;
}
.name{
  font-size: 28rpx;
  line-height: 100rpx;
  float: left;
  margin-left: 32rpx;
}
.go{
  float: right;
  margin-right: 48rpx;
  line-height: 100rpx;
  font-size: 28rpx;
  color: #989898;
}
.new{
  margin: auto;
  margin-top: 50rpx;
  width: 624rpx;
  height: 74rpx;
  background: #ffffff;
  border:1rpx dashed #ebebeb;
  border-radius: 10rpx;
  text-align: center;
  font-size: 24rpx;
  line-height: 74rpx;
  color: #aaaaaa;
}
</style>
