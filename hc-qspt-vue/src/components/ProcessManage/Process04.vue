<template>
    <div>
        <div class='selections'>
        <div class='realname'>
            <popup-picker title="获取方式" :data="array" v-model="cards[0].data[0].keyValue" :placeholder="array[0][hnindex]" @on-change="(value) => upValue(value, '0', '0','获取方式')">
                 </popup-picker>
        </div>
        <div class='realname'>
            <popup-picker title="第几套房" :data="housenum" v-model="cards[0].data[1].keyValue" :placeholder="housenum[0][hnindex]" @on-change="(value) => upValue(value, '0', '1','第几套房')">
                 </popup-picker>
        </div>
        <div class='realname'>
          <x-input title="证　　号" placeholder="《房屋所有权证》证号" novalidate icon-type="" placeholder-align="left" :value="cards[0].data[2].keyValue[0]" @on-blur="(value) => upValue(value, '0', '2','《房屋所有权证》证号')"></x-input>
        </div>
        <div class='realname1'>
          <x-textarea title="购房地址" placeholder="市+县（乡）+街道+号" :show-counter="false" :rows="1" autosize :value="cards[0].data[3].keyValue[0]" @on-change="(value) => upValue(value, '0', '3','购买房屋的详细地址')"></x-textarea>
        </div>
            <div class="fontsize" style="color:firebrick">&nbsp;&nbsp;&nbsp;&nbsp;如:自贡市XXX街XXX花园X栋XX号</div>
        </div>
        <div class='phototext'>请上传购房发票</div>

        <div class='add' style='position:relative;'>
        <div style='float:left;position:relative' class='photo' v-for="(item1,index) in cards[0].files" v-if="cards[0].display[0].imagedis">
          <div class="reson">
            {{item1.reson}}
          </div>
          <img :style="'border:'+item1.border"
               :src='item1.flowFileUrl' class='photo1' @click="photo(0,index)"/>
        </div>
          <el-upload
            v-if="addhidden"
            action="/qishui/qsapp/api/apply/zlsc"
            list-type="picture-card"
            :data="cards[0].upfiledata"
            :on-change="handleChange"
            :before-upload="beforeUpload"
            :on-success="(response, file, fileList) => upfile(response, file, fileList,cards[0].files.length,0)"
            :show-file-list="false"
            accept=".jpg,.jpeg,.png,.JPG,.JPEG,.PBG">
            +
          </el-upload>
        </div>
      <div v-show="showProcess">正在上传...
        <el-progress  :percentage="processLength" :stroke-width="2"></el-progress>
      </div>
    </div>
</template>
<script>
import { Datetime, PopupPicker, Group, Cell, Picker, XButton, Divider, XSwitch, XInput, XTextarea } from 'vux'
import Common from '@/lib/common'
import imageConversion from 'image-conversion'
export default {
    components: {
        PopupPicker,
        Group,
        Picker,
        XButton,
        Divider,
        Cell,
        XSwitch,
        Datetime,
        XInput,
        XTextarea
    },
  props:{
    cards:{
      type:Array,
      default:null
    }
  },
  mounted(){

      if(this.cards[0].data[0].keyValue==""){
        this.cards[0].data[0].keyValue=["房屋获取的方式"]
      }
    if(this.cards[0].data[1].keyValue==""){
      this.cards[0].data[1].keyValue=["购买的第几套房"]
    }
  },
    data(){
        return{
            address: '',
            hpc: '',
            value:[''],
            value1:[''],
            array: [['房屋的获取方式', '倒迁房', '直接购买', '房屋中介']],
            hqindex: 0,
            sclass: "select",
            housenum: [['购买的第几套房', '第一套', '第二套', '第三套及以上']],
            hnindex: 0,
            hnclass: "select",
            cango:"0",
            back: "#cdd4df",
            zhvalue:"",
            gfdzvalue:"",
            margin:"-13rpx",
            detail: 'realnameinput1',
            addhidden:true,
          processLength:0,
          showProcess:false
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
      photo(i,index){
        Common.seeImg(i,index,this);
      },
      upfile:function(response, file, fileList,i,index){
        if(i==1&&this.cards[0].files[0].flowFileUrl==""){
          i=0;
        }
        Common.upfile(response,i,index,this);
        console.log(response);
      },
      upValue:function(value,index, i,key){
        Common.upValue(value,index, i,key,this);
      },
    }
}
</script>
<style scoped>
.toper{
  text-align: center;
  width: 100%;
  height: 60rpx;
}
>>> .weui-textarea{
  font-size: 1.3em;
}
.fontsize{
    font-size: 10px;
}
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
  margin-left: 250rpx;
  margin-top: 20rpx;
}
.selections{
  margin-top: 30rpx;
}
.realname{
  background-color: white;
  width: 99%;
  height: 100rpx;
  line-height: 100rpx;
  position: relative;
}
.realname1{
  background-color: white;
  width: 99%;
  position: relative;
  padding-bottom: 24rpx;
}
.realnametext{
  float: left;
  height: 100rpx;
  /* margin-top: 30rpx; */
  line-height: 100rpx;
  font-size: 33rpx;
  margin-left: 35rpx;
}
.realnametext1
{
  float: left;
  height: 40rpx;
  margin-top: 30rpx;
  line-height: 40rpx;
  font-size: 33rpx;
  margin-left: 35rpx;
}
.pla{
  font-size: 32rpx;
  color: #D5D5D5;
}
.pla1{
  font-size: 32rpx;
  color: #D5D5D5;
  margin-left: -13rpx;
}
.select{
  position: relative;
  width: 500rpx;
  height: 100rpx;
  float: left;
  /* margin-top: 18rpx; */
  margin-left: 30rpx;
  color: #D5D5D5;
  font-size: 30rpx;
  line-height: 100rpx;
}
.select2{
  position: relative;
  width: 500rpx;
  height: 100rpx;
  float: left;
  /* margin-top: 18rpx; */
  margin-left: 30rpx;
  color: #4d4d4d;
  font-size: 30rpx;
  line-height: 100rpx;
  overflow: none;
}
.pikerimg{
  position: absolute;
  right: -10rpx;
  width: 18rpx;
  height: 34rpx;
  margin-top: 33rpx;
}
.realnameinput{
  width: 520rpx;
  height: 100rpx;
  line-height: 100rpx;
  float: left;
  /* margin-top: 30rpx; */
  margin-left: 40rpx;
  font-size: 32rpx;
  color: #4d4d4d;
}
.realnameinput2{
  width: 520rpx;
  height: 100rpx;
  line-height: 100rpx;
  float: left;
  /* margin-top: 24rpx; */
  margin-left: 30rpx;
  font-size: 32rpx;
  color: #4d4d4d;
}
.realnameinput1{
  padding-top: 0rpx;
  width: 520rpx;
  height: 40rpx;
  display: inline-block;
  margin-top: 32rpx;
  margin-left: 35rpx;
  line-height: 10rpx;
  font-size: 32rpx;
  color: #4d4d4d;
}
.realnameinputios{
  padding-top: 0rpx;
  width: 520rpx;
  height: 40rpx;
  display: inline-block;
  margin-top: 17rpx;
  margin-left: 22rpx;
  line-height: 10rpx;
  font-size: 32rpx;
  color: #4d4d4d;
}
.hpc{
  margin-top: 30rpx;
}
.hpctext{
  font-size: 28rpx;
  font-weight: 600;
  margin-left: 25rpx;
  height: 52rpx;
}
/* .realnametext1{
  float: left;
  font-size: 33rpx;
  margin-left: 100rpx;
} */
.phototext{
  margin-left: 1.3em;
  margin-top: 1em;
  margin-bottom: 1em;
  font-size: 0.5em;
  font-weight: 600;
  letter-spacing: 0.1em;
}
.addphoto{
  float: left;
  width: 2.5em;
  height: 2.5em;
  border: 1px dashed #CBCBCB;
  border-radius: 0.2em;
  background-color: white;
  margin-left: 0.5em;
  line-height: 2.5em;
  font-size: 2em;
  text-align: center;
  color: #C1C1C1;
  font-weight: lighter;
  /*margin-top: 20rpx;*/
}
>>> .el-upload--picture-card{
  float: left;
  width: 2.5em;
  height: 2.5em;
  border: 1px dashed #CBCBCB;
  border-radius: 0.2em;
  background-color: white;
  margin-left: 0.5em;
  line-height: 2.5em;
  font-size: 2em;
  text-align: center;
  color: #C1C1C1;
  font-weight: lighter;
  /*margin-top: 20rpx;*/
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
  float: left;
  width: 5em;
  height: 5em;
  border-radius: 0.2em;
  background-color: white;
  margin-left: 0.5em;
  line-height: 2.5em;
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
  margin-left: 7rpx;
  margin-top: 4rpx;
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
.tip{
  color: red;
  font-size: 20rpx;
  position: absolute;
  right: 50rpx;
  line-height: 100rpx;
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
  right: -16rpx;
  top: 4rpx;
  opacity: 0.8;
  width: 50rpx;
  height: 50rpx;
  line-height: 50rpx;
}
</style>
