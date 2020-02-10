<template>
        <div class='info'>
            <div class='title1' v-for="(item,index) in text" :key="index">
                <div class='titletext'>{{item.title}}</div>
                <div class='titletext1'>{{item.text}}</div>
                <div class='titletext2'>
                  <div class="userNickName">{{information.userName}}</div>
                </div>
            </div>
        </div>
</template>
<script>
export default {
    data(){
        return {
             information:{},
             text:[
                {
                    title:"昵　称",
                    opentype:"userNickName",
                    text: "",
                }
            ]
        }
    },
    created(){
      this.information=this.$route.params.userInfo
    }
}
</script>
<style scoped>
.info{
  margin-top: 6rpx;
  background-color: white;
  height: 600rpx;
}
.title{
  height: 4em;
  width: 22em;
  margin-left: 1em;
  line-height: 4em;
}
.titletext{
  font-size: 12pt;
  float: left;
  margin-left: 0.2em;
  color: #222222;
}
.title1{
  height: 4em;
  width: 22em;
  margin-left: 1em;
  line-height: 4em;
}
.titleimg{
  float: right;
  width: 2.1em;
  height: 2.1em;
  border-radius: 2.1em;
  margin-right: 0.8em;
  margin-top: 0.9em;
  border: 1px solid #e7e7e7;
  overflow: hidden;
}
.titleimg1{
  width: 2.2em;
  height: 2.2em;
  margin: auto;
  /* margin-left: 0.5em; */
  margin-bottom: 0.1em;
  border-radius:3em;
  background-color: #FBFCFF;
}
.titletext1{
  font-size: 36rpx;
  float: right;
  margin-right: 42rpx;
  margin-top: 11rpx;
  height: 80rpx;
  line-height: 80rpx;
  color: #b6b6b6;
}
.titletext2{
  font-size: 36rpx;
  float: right;
  margin-right: 0rpx;
  margin-top: 11rpx;
  height: 80rpx;
  line-height: 80rpx;
  color: #b6b6b6;
}
.userNickName{
  font-size: 12pt;
  margin-right: 1em;
  color: #b6b6b6;
}
</style>
