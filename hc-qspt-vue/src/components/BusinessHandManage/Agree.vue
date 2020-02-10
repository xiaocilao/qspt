<template>
  <div>
    <h2 style="text-align: center">用户须知与信息授权</h2>
    用户使用房管局同步功能需要您提供房产合同备案号和个人姓名信息，协助您更加方便准确的录入个人信息。
    <div/>
    如您需要使用此功能，您必须遵守以下规则，如违反以下规则，您将受到法律追究责任。
    <div/>
      &nbsp; &nbsp;·不得使用与本人无关的合同备案号。
    <div/>
      &nbsp; &nbsp;·不得泄露与本人无关的信息。
    <div/>
      &nbsp; &nbsp;·使用房管局同步功能只用于本人进行契税缴纳。
    <div/>
      &nbsp; &nbsp;·因个人操作不当引起的信息泄露责任与本系统无关。
    <div/>
      &nbsp; &nbsp;·用户同意使用房管局数据同步功能说明已同意授权系统收集个人信息用于契税缴纳。
    <div/>
    <input type="checkBox" @click="checked($event)">同意协议
    <div/>
    <el-button class="accept"  :disabled="isAble" @click="gotofg">确定</el-button>
  </div>
</template>
<script>
  import Common from "@/lib/common";
  export default {
    name: "Agree",
    data(){
      return{
        isAble:true,
        applyId:"",
        data:{id:''}
      }
    },
    mounted(){
        if (this.$route.query && this.$route.query.data) {
          var data = this.$route.query.data
          this.data.id = data.id;
      }
    },
    methods:{
      checked:function(e){
        let isChecked = e.target.checked;
        if(isChecked === true){
          this.isAble = false;
        }else{
          this.isAble = true;
        }
      },
      gotofg(){
        Common.Sqtg(this.data);
        this.$router.push({
          path:"SyncFgyInfo",
          query: {data:this.data}
        });
      }
    }
  }
</script>

<style scoped>
.accept{
  width: 100%;
  height: 2em;
  margin: auto;
  line-height: 2em;
  text-align:center;
  padding:0px;
  vertical-align:middle ;
  font-size: 1em;
  border-radius: 0.5em;
  vertial-align:middle;
  position: absolute;
  bottom:0
}
</style>
