<template>
    <div>
        <div class='agreement'>请按照拆迁赔偿协议顺序上传</div>
        <div class='add' style='position:relative;'>
        <div class='reson' :hidden='cards[0].resonhidden'>{{cards[0].reson}}</div>
        <div style='float:left;position:relative' class='photo' v-for="(item,index) in cards[0].files" v-if="cards[0].display[0].imagedis">
          <div class="reson">
            {{item.reson}}
          </div>
            <img mode='aspectFill' :style="'border:'+item.border" :src='item.flowFileUrl'
                 class='photo1'  @click="photo('0',index)" />
        </div>
          <el-upload
            action="/qishui/qsapp/api/apply/zlsc"
            list-type="picture-card"
            :data="cards[0].upfiledata"
            :before-upload="beforeUpload"
            :on-change="handleChange"
            :on-success="(response, file, fileList) => upfile(response, file, fileList,cards[0].files.length,0)"
            :show-file-list="false"
            accept=".jpg,.jpeg,.png,.JPG,.JPEG,.PBG">
            +
          </el-upload>
        </div>
      <div v-show="showProcess">正在上传...
        <el-progress  :percentage="processLength" :stroke-width="2"></el-progress>
      </div>
      <div class='skip' :hidden='hidden'>
        非拆迁户可  <div class='skipfont' @click='next1()'>跳过此步骤</div>
      </div>
    </div>
</template>
<script>
  import Common from '@/lib/common'
  import imageConversion from 'image-conversion'
export default {
    data(){
        return{
            array: ["房屋类型", "小户型", "大户型", "别墅"],
            fwindex: 0,
            sclass: "select",
            array1: ["上传方式", "购房合同", "备案表"],
            index1: 0,
            sclass1: "select",
            back:"#cdd4df",
            hidden:false,
          processLength:0,
          showProcess:false
        }
    },
  props:{
    cards:{
      type:Array,
      default:null
    }
  },
    methods:{
        beforeUpload (file) {
            return new Promise((resolve, reject) => {
                let isLt2M = file.size / 1024 / 1024 < 4
                if (isLt2M) {
                    resolve(file)
                }
                console.log(file)
                imageConversion.compressAccurately(file, 400).then(res => {
                    resolve(res)
                })
            })
        },
      handleChange(file,fileList){
        if(file.status === 'ready'){
          this.processLength = 0
          this.showProcess = true
          const interval = setInterval(()=>{
            if(this.processLength>=99){
              clearInterval(interval)
              return
            }
            this.processLength +=1
          },20)
        }
        if(file.status === 'success'){
          this.processLength = 100
          this.showProcess = false
        }
      },
      upfile:function(response, file, fileList,i,index){
        if(i==1&&this.cards[0].files[0].flowFileUrl==""){
          i=0;
        }
        Common.upfile(response,i,index,this);
        console.log(response);
      },
      photo(i,index){
        Common.seeImg(i,index,this);
      },
      next1(){
        Common.skip(this);
      }
    }
}
</script>
<style scoped>
.num{
  width: 40rpx;
  height: 40rpx;
  border-radius: 40rpx;
  background-color: #2b4d7e;
  color: white;
  font-size: 30rpx;
  text-align: center;
  line-height: 40rpx;
  display: inline-block;
  margin-top: 20rpx;
}
.num1{
  width: 40rpx;
  height: 40rpx;
  border-radius: 40rpx;
  background-color: #9aa9c1;
  color: white;
  font-size: 30rpx;
  text-align: center;
  line-height: 40rpx;
  display: inline-block;
  margin-top: 20rpx;
}
.num2{
   width: 40rpx;
  height: 40rpx;
  border-radius: 40rpx;
  background-color: red;
  color: white;
  font-size: 20rpx;
  text-align: center;
  line-height: 40rpx;
  display: inline-block;
  margin-top: 20rpx;
}
.line{
  width: 55rpx;
  height: 40rpx;
  font-size: 30rpx;
  display: inline-block;
  text-align: center;
  color: #9aa9c1;
  font-weight: 600;
}
.idcard{
  font-weight: 600;
  width: 160rpx;
  height: 45rpx;
  font-size: 26rpx;
  text-align: center;
  line-height: 45rpx;
  border-radius: 30rpx;
  background-color: #2b4d7e;
  color: white;
  margin-left: 530rpx;
  margin-top: 20rpx;
}
.agreement{
  text-align: center;
  width: 90%;
  height:100rpx;
  margin: auto;
  margin-top: 32rpx;
  font-size: 28rpx;
  font-weight: 600;
}
.agreementfont{
  color: #fe9510;
}
.addphoto{
  /*float: left;*/
  width: 4em;
  height: 4em;
  border: 1px dashed #CBCBCB;
  border-radius: 0.2em;
  background-color: white;
  margin-left: 2em;
  line-height: 4em;
  font-size: 1em;
  text-align: center;
  color: #C1C1C1;
  font-weight: lighter;
  margin-top: 1em;
}
>>> .el-upload--picture-card{
  /*float: left;*/
  width: 4em;
  height: 4em;
  border: 1px dashed #CBCBCB;
  border-radius: 0.2em;
  background-color: white;
  margin-left: 2em;
  line-height: 4em;
  font-size: 1em;
  text-align: center;
  color: #C1C1C1;
  font-weight: lighter;
  margin-top: 1em;
}
.skip{
  position: fixed;
  bottom: 6em;
  font-size: 1em;
  text-align: center;
  left: 28%;
}
.skipfont{
  display: inline-block;
  color: #48658c;
}
.next{
  position: fixed;
  text-align: center;
  margin: auto;
  width: 100%;
  height: 110rpx;
  font-weight: 600;
  background-color: white;
  bottom: 0rpx;
  box-shadow: 0px 0px 10rpx 10rpx #F8F8F8;
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
.error{
  width: 100%;
  height: 200rpx;
}
.photo{
  margin-top: 1em;
  float: left;
  width: 4em;
  height: 4em;
  border-radius: 0.2em;
  background-color: white;
  margin-left: 2em;
  text-align: center;
  color: #C1C1C1;
  font-weight: lighter;
  /*margin-top: 20rpx;*/
}
.photo1{
  float: left;
  width: 100%;
  height: 100%;
  border-radius: 0.2em;
  background-color: white;
  text-align: center;
  color: #C1C1C1;
  font-weight: lighter;
  /*margin-top: 20rpx;*/
}
.add{
  margin-left: 40rpx;
}
.bkcz{
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.8);
  opacity: 0.8;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 99;
  text-align: center;
  font-size: 32rpx;
  color: white;
  line-height: 1000rpx;
  display: none;
}
.reson{
  position: absolute;
  color: red;
}
.delete{
  width: 35rpx;
  height: 35rpx;
  margin-left: 10rpx;
}
.deleteview{
  position: absolute;
  right: -18rpx;
  top: 4rpx;
  opacity: 0.8;
  width: 50rpx;
  height: 50rpx;
  line-height: 50rpx;
}
</style>
