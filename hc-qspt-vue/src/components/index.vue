<template>
    <div>
      <div v-if="showPage!=4">
        <!--<div class="content" v-if="showPage==0">-->
          <!--<NewsManage class="bottomBar" ref="newsManage"/>-->
        <!--</div>-->
        <div class="content" v-if="showPage==0">
          <BusinessHandle class="bottomBar" ref="businessHandle" @handerSelect="changeShowPage"/>
        </div>
        <div class="content" v-if="showPage==1">
          <UserCenter class="bottomBar"  ref="userCenter"    />
        </div>
        <div class="Filler"></div>
        <BottomBar class="bottomBar1" ref="bottomBar"  @handerSelect="changeShowPage" />
      </div>
    </div>
</template>

<script>
  import BottomBar from "@/components/CommComponents/BottomTableBar"
  import NewsManage from "@/components/NewsManage/NewsManage"
  import BusinessHandle from "@/components/BusinessHandManage/BusinessHandle"
  import ProcessManage from "@/components/ProcessManage/ProcessManage"
  import UserCenter from "@/components/UserCenter/UserCenter"
  import SignBoard from "@/components/SignBoard"
  import PayTest from "@/components/ProcessManage/PayTest"
  import HandlePost from "@/components/YoujiPages/HandlePost"
    export default {
        name: "index",
      components: {
        BottomBar,
        NewsManage,
        BusinessHandle,
        UserCenter,
        ProcessManage,
        SignBoard,
        PayTest,
        HandlePost
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

      }
    }
</script>

<style scoped>
  .bottomBar1{
    position: fixed;
  }
  .Filler{
    height: 10em;
  }
  .histContent{
    color:#D1D1D1;
    font-size: 10px;
    bottom:90px;
    position: fixed;
  }

</style>
