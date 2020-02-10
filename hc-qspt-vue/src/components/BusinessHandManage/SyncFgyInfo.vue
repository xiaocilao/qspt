<template>
  <div>
    <div class="body">
    <div class='topertext'>房产数据同步</div>

    <div style="height: 2em;width: 100%">
    </div>

    <group title="此处要填写房屋所有人的信息">
      <x-input title="姓     名： " placeholder="真实姓名" :show-clear="false" v-model="fcrMc"></x-input>
      <x-input title="合同备案号： " placeholder="合同备案号" :show-clear="false" v-model="htbah"></x-input>
    </group>
    <div class="divErrTooltip" :hidden='isTooltip'>{{toolTipStr}}</div>
    </div>
    <div class='nexttext' @click='SubmitSyncData'>同步房产数据</div>
    <div class='next' @click='goback1()'>
      <div class='nexttext' :style="'background-color:blue'">返回</div>
    </div>
  </div>
</template>

<script>
  import { XInput } from 'vux';
  import { Group } from 'vux';
  import common from "@/lib/common";

  export default {
    components:{
      XInput,
      Group
    },
    name: "SyncFgyInfo",
    data(){
      return{
        isTooltip:true,
        toolTipStr:"",
        fcrMc:"",
        htbah:"",
        applyId:''
      }
    },
    mounted(){
      if (this.$route.query && this.$route.query.data) {
        var data = this.$route.query.data
        this.applyId = data.id;
      }
    },
    methods:{
      SubmitSyncData() {
        if (this.fcrMc==""){
          this.toolTipStr="房产人姓名不能为空！";
          this.isTooltip=false;
        } else if (this.htbah==""){
          this.toolTipStr="合同备案号不能为空！";
          this.isTooltip=false;
        }else{
          this.isTooltip=true;
          common.SyncFgjData(this, this.fcrMc, this.htbah);
        }
      },
      goback1() {
        this.$router.push("/index")
      },
    }
  }
</script>

<style scoped>
  .topertext{
    text-align: center;
    width: 100%;
    font-size: 2em;
    height: 4em;
  }
  .nexttext{   width: 29.5em;
    height: 2.5em;
    margin: auto;
    background-color: #2c4c7f;
    line-height: 2.5em;
    text-align: center;
    margin-top: -0.7em;
    font-size: 6pt;
    color: white;
    border-radius: 1.3em;
  }
  .divErrTooltip{
    width: 90%;
    font-size: 1em;
    color: red;
  }
  .next{
    z-index: 99;
    position: fixed;
    margin: 0 auto;
    width: 95%;
    height: 2em;
    font-weight: 300;
    background-color: white;
    bottom: 0em;
    box-shadow: 0em 0em 1em 1em #F8F8F8;
  }
  .nexttext{width: 29.5em;
    position: fixed;
    bottom: 12px;
    width: 29.5em;
    width: 90%;
    right: 0px;
    height: 6%;
    left: 0;
    margin: 0 auto;
    background-color: #2c4c7f;
    line-height: normal;
    margin-top: 0.3em;
    margin-top: auto;
    text-align: center;
    table-layout: fixed;
    text-align: center;
    font-size: 13pt;
    color: white;
    border-radius: 1.3em;
  }

</style>
