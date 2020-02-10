<template>
<div>
    <div class='title'>
        <div class='titleimg'>
            <img :src="userInfo.headPic" class="titleimg1">
        </div>
        <div class='titletext'>
            <div class="userNickName">{{userInfo.userName}}</div>
        </div>
        <div class='titletextscend'>轻松办理个人业务</div>
   </div>
    <div style='background-color: #FFFFFF;padding-top:1.5em;'>
         <router-link v-for="(item,index) in text" :key="index" :to="{name:item.url,
                 params: {userInfo:userInfo}}" @click.native="clickRoute">
             <div class='go'>
                 <i style="float: left;position: relative;left: 10px;top:3px" :class='item.icon'></i>
                 <div class='goto'>{{item.text}}</div>
                <img class='gotoicon' src='@/assets/static/images/go.png'>
                <div class='tip'>{{item.tip}}</div>
            </div>
        </router-link>
        <div @click="payAgain">
            <div class='go' >
                <i style="float: left; position: relative;left: 10px;top:5px" class="el-icon-bell"></i>
                <div class='goto' >再缴一笔</div>
                <img class='gotoicon' src='@/assets/static/images/go.png' >
                <div class='tip'></div>
            </div>
        </div>
        <div @click="reload" v-if="isTrue">
            <div class='go' >
                <i style="float: left; position: relative;left: 10px;top:5px" class="el-icon-delete"></i>
                <div class='goto' >重置（测试）</div>
                <img class='gotoicon' src='@/assets/static/images/go.png' >
                <div class='tip'></div>
            </div>
        </div>

    </div>
</div>  
</template>
<script>
import Common from "../../lib/common.js"
export default {
    data(){
        return {
          isTrue:false,

          userInfo:{headPic: require('@/assets/static/images/touxiang.jpg'),userName:'默认'},
          text:[
            {
                text: '我的业务',
                url: 'Process',
                tip: '',
                icon:'el-icon-share',
            },
            {
              text: '交易状态查询',
              url: 'Deal',
              tip: '',
              icon:'el-icon-document',
            },
              {
              text: '我的快递单号',
              url: 'PostPage',
              tip: '',
              icon:'el-icon-zoom-in',
            },
            //     {
            //     text: '在缴一笔',
            //     url: 'payAgain',
            //     tip: ''
            //   },
            {
              text: '联系我们',
              url: 'Help',
              tip: '',
              icon:'el-icon-phone',
            }
          ]
        }
    },
    methods:{
      clickRoute(){
        this.$emit('handerSelect',4);  //子组建触发父组建方法
      },
        loading(that,data){
            let arr = [data];
            let str = arr.join('</br>');
            that.$message({
                dangerouslyUseHTMLString:true,
                message:str,
                type:'warning'
            })
        },
        reload(){
          var that = this;
            Common.reload(that);
        },
        payAgain(){
          var that = this;
            this.$confirm('此次操作将覆盖之前的记录，您确认操作吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                Common.payAgain(function(res){
                    if(res.data.rows[0].code=='200'){
                        that.loading(that,"操作成功");
                    }else if(res.data.rows[0].code=='201'){
                        that.loading(that,"请您先完成当前交易");
                    }else{
                        that.loading(that,"不允许操作");
                    }
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '用户已取消'
                });
            });
        }
    },
    mounted() {
      var that = this;
      Common.FindUser(function (res) {
        that.userInfo.userName = res.data;
        if(res.data==='宋俊松'|| res.data==='默认1'||res.data ==='胡小天'
            ||res.data ==='谢天培'||res.data ==='李大江'||res.data ==='邹念超'){
            that.isTrue=true;
        }
      },function () {

      })
    }
}
</script>
<style scoped>
.title{
  border: 1rpx solid white;
  background-color: white;
  height: 320rpx;
  width: 100%;
  border-bottom: 11rpx solid #F8F8F8;
}
.title1{
  height: 102rpx;
  width: 710rpx;
  margin-left: 42rpx;
  line-height: 102rpx;
  border-top: 1rpx solid#e9e9e9;
}
.titleimg{
  width: 4em;
  height:4em;
  margin: auto;
  border-radius: 4em;
  background-color: #FBFCFF;
  border: 1px solid #C7CBD4;
  margin-top: 1em;
  overflow: hidden
}
.titleimg1{
    width: 5em;
    height: 5em;
    /* margin: auto; */
    margin-left: -0.5em;
    margin-top: -0.5em;
    border-radius: 3em;
    background-color: #FBFCFF;
}
.titletext{
  width: 100%;
  margin: auto;
  text-align: center;
  font-size: 1em;
  margin-top: 0.2em;
}
.titletextscend{
  text-align: center;
  font-size: 0.8em;
  color: #A8A8A8;
  margin-top: 0.2em;
}

.go{
  width: 100%;
  height: 3em;
}
.go1{
  width: 100%;
  height: 2em;
  border-top: 1px solid #F9F9F9;
  border-bottom: 1px solid #F9F9F9;
}
.goto{
  font-size: 1em;
  color: #000000;
  display: inline-block;
  margin-top: 0.2em;
  margin-bottom: 0.2em;
  margin-left: 22px;
}
.gotoicon{
  float: right;
  width: 0.8em;
  height: 1em;
  margin-top: 0.2em;
  margin-bottom: 0.2em;
  margin-right: 0.5em;
}
.tip{
  float: right;
  font-size: 1em;
  margin-top: 0em;
  margin-bottom: 0.2em;
  margin-right: 0.8em;
  color: #A7A7A7;
}
.error{
  width: 100%;
  height: 150rpx;
}
</style>
