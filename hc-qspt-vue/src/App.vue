<template>
  <div id="app" >
    <router-view></router-view>
  </div>
</template>

<script>
  import Common from "@/lib/common"
  import BottomBar from "@/components/CommComponents/BottomTableBar"
  import NewsManage from "@/components/NewsManage/NewsManage"
  import BusinessHandle from "@/components/BusinessHandManage/BusinessHandle"
  import ProcessManage from "@/components/ProcessManage/ProcessManage"
  import UserCenter from "@/components/UserCenter/UserCenter"
  import SignBoard from "@/components/SignBoard"
  import Sign from "@/components/SubPages/Sign"
  export default {
    name: 'App',
    components: {
      BottomBar,
      NewsManage,
      BusinessHandle,
      UserCenter,
      ProcessManage,
      SignBoard,
      Sign
    },
    data(){
      return {
        showPage:null,
        userControl:false
      }
    } ,
    methods:{
      changeShowPage(val){
        this.showPage=val
      }
    },
    mounted(){
      var that = this;
      Common.login(this.$route.query.token,function(){
        that.$router.push({
          path:"index",
          query:{
            token:that.$route.query.token
          }
        });
      });

    }
  }
</script>

<style>
  body{
    background-color: #F9F9F9;
  }
</style>
