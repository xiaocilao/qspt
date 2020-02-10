<template>
    <div class="page">
        <!--pages/bindbankcard/bindbankcard.wxml-->
        <div class='background' :hidden='backhidden'></div>
        <div class='bankcards' v-for="(item,index) in bankcard" :key="index">
        <div class='card'>
            <div  class='bankimg'></div>
            <div class='cardinfo'>
            <div class='cardtitle'>{{item.cardbank}}</div>
            <div class='cardtype'>{{item.cardtype}}</div>
            </div>
            <div class='cradid'>{{item.cardid}}</div>
        </div>
        <image :src='item.cardback' class='backimg'></image>
        </div>
        <div class='addcard' >
          <router-link to="/UserCenter/AddCard" style="text-decoration: none;"  class='addcard'>+添加银行卡</router-link>
        </div>
    </div>
</template>
<script>
export default {
    data(){
        return {
            bankcard:[],
            backhidden:true
        }
    }
}
</script>
<style scoped>
.page{
  background: #f9f9f9;
  width: 100%;
  height: 100%;
}
.bankcards{
  position: relative;
  width:98%;
  height: 5.6em;
  margin: auto;
  background-color: white;
  border-radius: 10rpx;
  margin-top: 30rpx;
  border: 1rpx solid #f9f9f9;
}
.backimg{
  z-index: 98;
  position: absolute;
  width: 100%;
  height: 5.6em;
  top: 0rpx;
  border-radius: 10rpx;
}
.card{
  z-index: 99;
  position: relative;
  width: 634rpx;
  margin: auto;
  margin-top: 36rpx;
}
.bankimg{
  width: 70rpx;
  height: 70rpx;
  float: left;
}
.cardinfo{
  float: left;
  margin-left: 32rpx;
}
.cardtitle{
  font-size: 32rpx;
  font-weight: 600;
  letter-spacing: 2rpx;
}
.cardtype{
  font-size: 24rpx;
  letter-spacing: 2rpx;
  margin-top: 10rpx;
}
.cradid{
  font-size: 40rpx;
  font-weight: 600;
  float: right;
  margin-top: 28rpx;
}
.addcard{
  width: 98%;
  height: 2.7em; 
  font-size: 36rpx;
  color: #959494;
  margin: auto;
  margin-top: 0.84em;
  text-align: center;
  background: white;
  border-radius: 0.26em;
  line-height: 2.73em;
}
</style>
