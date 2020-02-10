<template>
    <div :style="'height:'+screenHeight">
      <div class="seeimg">
        <img alt="" :src="cards[site.i].files[site.index].flowFileUrl" class="img" @click="show('0')">
        <previewer :list="[{src:cards[site.i].files[site.index].flowFileUrl}]" ref="previewer"></previewer>
      </div>
      <div class="choses">
        <div v-if="imgType=='sign'" class="leftBotton" @click="sign">签名</div>
        <div class="rightBotton" @click="back">确定</div>
        <div class="centerBotton" v-if="imgType!='sign'" @click="delImage(cards[site.i].files[site.index].flowFileUrl,site.i,site.index)">删除</div>
        <!--<el-upload-->
          <!--v-if="imgType=='normal'"-->
          <!--action="/qishui/qsapp/api/apply/zlsc"-->
          <!--list-type="picture-card"-->
          <!--:data="upfiledata"-->
          <!--:on-success="(response, file, fileList) => upfile(response, file, fileList,site.index,site.i)"-->
          <!--:show-file-list="false">-->
          <!--上传-->
        <!--</el-upload>-->

      </div>
    </div>
</template>

<script>
  import Common from '@/lib/common'
  import { Previewer } from 'vux'
    export default {
      components: {
        Previewer
      },
      props:{
        site:{
          type:Object,
          default:{i:0,index:0}
        },
        cards:{
          type:Array,
          default:null
        },
        imgType:{
          type:String,
          default:null
        }
      },
      name: "seeimg",
      data() {
        return {
          upfiledata:{clientKey:Common.clientKey},
          screenHeight:'0px',
          determine:"上传",
          deleteFile:{
            url:'',
            index:0,
            i:0
          }

        }
      },
      methods:{
        upfile:function(response, file, fileList,i,index){
          Common.upfile(response,i,index,this);
        },
        show(index){
          this.$refs.previewer.show(index)
        },
        back(){
          this.$emit("back");
        },
        delImage(url,i,index){
          this.deleteFile.url=url;
          this.deleteFile.index=index;
          this.deleteFile.i=i;
          this.$emit("delImage",this.deleteFile);

        },
        sign(){
          this.$emit("sign");
        }
      },
      mounted() {

        this.screenHeight=(screen.height-30)+"px";
        this.upfiledata.applyId = this.cards[this.site.i].upfiledata.applyId;
        this.upfiledata.flowId = this.cards[this.site.i].upfiledata.flowId;
        this.upfiledata.infoId = this.cards[this.site.i].upfiledata.infoId;
        if(this.cards[this.site.i].files[this.site.index].fileId){
          this.upfiledata.fileId=this.cards[this.site.i].files[this.site.index].fileId;
        }
        if(this.imgType=="normal"){
          this.determine="上传"
        }else if(this.imgType=="sign"){
          this.determine="签名"
        }
      }
    }
</script>

<style scoped>
  .seeimg{
    width: 100%;
    height: 78%;
    bottom: 0;
    background-color: white;
    overflow: hidden;
  }
  .choses{
    width: 100%;
    height: 10%;
  }
  .img{
    width: 100%;
    height: auto;
  }
  .leftBotton{
    height: 50%;
    margin-top: 5%;
    border: #4382E6 1px solid;
    width: 30%;
    border-radius: 50px;
    float: left;
    /*margin-left: 5%;*/
    text-align: center;
    line-height: 200%;
    color: #4382E6;
  }
  .centerBotton{
    height: 50%;
    margin-top: 5%;
    border: #4382E6 1px solid;
    width: 30%;
    border-radius: 50px;
    float: left;
    text-align: center;
    line-height: 200%;
    color: #4382E6;
  }
  .rightBotton{
    height: 50%;
    margin-top: 5%;
    border: 1px solid;
    width: 30%;
    border-radius: 50px;
    float: right;
    margin-right: 5%;
    text-align: center;
    line-height: 200%;
    background-color: #4382E6;
    color: white;
  }
  >>> .el-upload--picture-card{
    height: 50%;
    margin-top: 5%;
    border: 1px solid;
    width: 30%;
    border-radius: 50px;
    float: right;
    margin-right: 5%;
    text-align: center;
    line-height: 200%;
    background-color: #4382E6;
    color: white;
  }
</style>
