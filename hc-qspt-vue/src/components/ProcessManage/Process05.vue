<template>
    <div>
        <div class='contract'>
            <div class='realname'>
              <x-input title="房屋价格" type="number" placeholder="房屋价格" novalidate icon-type="" placeholder-align="left" :value="cards[0].data[0].keyValue[0]" @on-blur="(value) => upValue(value, '0', '0','房屋价格')">
                <div class="unit" slot="right-full-height" :style="'display:'+jg ">元</div>
              </x-input>
            </div>
          <div class='realname'>
            <x-input title="房屋面积" type="number" placeholder="房屋面积" novalidate icon-type="" placeholder-align="left" :value="cards[0].data[1].keyValue[0]" @on-blur="(value) => upValue(value, '0', '1','房屋面积')">
              <div class="unit" slot="right-full-height" :style="'display:'+mj ">平方米</div>
            </x-input>
          </div>
          <div class='realname'>
              <popup-picker title="房屋类型" :data="array" v-model="cards[0].data[2].keyValue" :placeholder="array[0][fwindex]" @on-change="(value) => upValue(value, '0', '2','房屋类型')">
              </popup-picker>
          </div>
          <div class='realname'>
              <popup-picker title="证件类型" :data="array1" v-model="cards[0].data[3].keyValue" :placeholder="array1[0][index1]" @on-change="(value) => upValue(value, '0', '3','证件类型')">
              </popup-picker>
          </div>
        </div>
        <div class='bottomtexts'>请按照{{tiptext}}页面顺序依次上传。</div>
            <div class='add' style='position:relative'>
            <div class='reson' :hidden='cards[0].resonhidden'>{{cards[0].reson}}</div>
            <div class='photo' style='float:left;position:relative' v-for="(item,index) in cards[0].files" v-if="cards[0].display[0].imagedis">
              <div class="reson">
                {{item.reson}}
              </div>
              <img mode='aspectFill' :style="'border:'+item.border" :src='item.flowFileUrl'
                   class="photo1" @click="photo(0,index)">
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
import { Datetime, PopupPicker, Group, Cell, Picker, XButton, Divider, XSwitch, XInput } from 'vux'
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
        XInput
    },
  props:{
    cards:{
      type:Array,
      default:null
    }
  },
    data(){
        return{
            array: [["房屋类型", "住宅", "非住宅"]],
            fwindex: 0,
            value:[''],
            value1:[''],
            sclass: "select",
            array1: [["文件类型", "购房合同", "备案表"]],
            index1: 0,
            sclass1: "select",
            fwjgvalue:"",
            fwmjvalue:"",
            cango:"0",
            back:"#cdd4df",
            tiptext:"合同或备案表",
            addhidden:true,
            jg:"none",
            mj:"none",
          processLength:0,
          showProcess:false
        }
    },
  mounted(){

      if(this.cards[0].data[2].keyValue){
        this.cards[0].data[2].keyValue=["房屋类型"]
      }
      if(this.cards[0].data[3].keyValue){
        this.cards[0].data[3].keyValue=["文件类型"]
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
      upValue:function(value,index, i,key){
        //
        var that = this;
        Common.upValue(value,index, i,key,this,null,function () {
          if(key=="房屋价格"){
            if (value!="") {
              that.jg="block";
            }else{
              that.jg="none";
            }
          }
          if(key=="房屋面积"){
            if (value!="") {
              that.mj="block";
            }else{
              that.mj="none";
            }
          }
        });
      },
      photo(i,index){
        Common.seeImg(i,index,this);
      },
         blur(){
            var that = this;
            if (e.currentTarget.dataset.keyname == '房屋价格') {
            if (e.detail.value.substring(e.detail.value.length - 1, e.detail.value.length) == "元") {
                that.data.fwjgvalue = e.detail.value.substring(0, e.detail.value.length - 1) + "元";
            } else if (e.detail.value.substring(e.detail.value.length - 2, e.detail.value.length - 1) == "元") {
                that.data.fwjgvalue = e.detail.value.substring(0, e.detail.value.length - 2) + e.detail.value.substring(e.detail.value.length - 1, e.detail.value.length) + "元";
            } else if (e.detail.value == "元") {
                that.data.fwjgvalue = "";
            } else if(e.detail.value==""){
                that.data.fwjgvalue = "";
            } else {
                that.data.fwjgvalue = e.detail.value + "元";
            }
            } else {
            if (e.detail.value.substring(e.detail.value.length - 2, e.detail.value.length) == "m²") {
                that.data.fwmjvalue = e.detail.value.substring(0, e.detail.value.length - 2) + "m²";
            } else if (e.detail.value.substring(e.detail.value.length - 1, e.detail.value.length) == "m") {
                that.data.fwmjvalue = e.detail.value.substring(0, e.detail.value.length - 1) + "m²";
            } else if (e.detail.value.substring(e.detail.value.length - 2, e.detail.value.length - 1) == "²") {
                that.data.fwmjvalue = e.detail.value.substring(0, e.detail.value.length - 3) + e.detail.value.substring(e.detail.value.length - 1, e.detail.value.length) + "m²";
            } else if (e.detail.value == "m²") {
                that.data.fwmjvalue = "";
            } else if (e.detail.value == "") {
                that.data.fwmjvalue = "";
            } else {
                that.data.fwmjvalue = e.detail.value + "m²";
            }
            }
            this.setData(this.data);
        },
    }
}
</script>
<style scoped>
  .unit{
    margin-right: 1em;
    line-height: 2.5em;
  }
.toper{
  text-align: center;
  width: 100%;
  height: 60rpx;
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
  margin-left: 340rpx;
  margin-top: 20rpx;
}
.realname{
  background-color: white;
  width: 100%;
  height: 100rpx;
  line-height: 100rpx;
  position: relative;
}
.realnametext{
  float: left;
  height: 100rpx;
  /* margin-top: 30rpx; */
  line-height: 100rpx;
  font-size: 33rpx;
  margin-left: 35rpx;
}

.pla{
  font-size: 32rpx;
  color: #D5D5D5;
  /* font-family:Cambria, Cochin, Georgia, Times, Times New Roman, serif; */
}
.realnameinput{
  width: 520rpx;
  height: 100rpx;
  line-height: 100rpx;
  float: left;
  /* margin-top: 30rpx; */
  margin-left: 30rpx;
  font-size: 32rpx;
  color: #4d4d4d;
}
.contract{
  margin-top: 40rpx;
}
.select{
  position: relative;
  width: 500rpx;
  height: 100rpx;
  float: left;
  /* margin-top: 22rpx; */
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
  /* margin-top: 22rpx; */
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
.bottomtexts{
  margin-top: 1.5em;
  margin-bottom: 1.5em;
  margin-left: 1.3em;
  font-size: 0.8em;
  font-weight: 600;
  letter-spacing: 0.1em;
  /*line-height: 60rpx;*/
}
.bottomtextfont{
  display: inline-block;
  color: #FEA728;
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
  float: left;
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
.reson{
  position: absolute;
  color: red;
}
.delete{
  width: 35rpx;
  height: 35rpx;
  margin-left: 6rpx;
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
