<template>
    <div>
        <div v-for='(item,index) in cards' >
          <group>
                 <popup-picker title="婚姻状况" :data="array" v-model="item.data[0].keyValue" :placeholder="array[0][hhindex]" @on-change="jlhzt">
                 </popup-picker>
                <datetime :min-year="1900" :title="dtext" v-model="item.data[1].keyValue[0]" v-if='!dhidden' @on-change="changeTime"></datetime>
          </group>
            <div class='phototext'>{{phototext}}</div>
            <div class='add' style="position:relative">
                <div style='float:left;position:relative' class='photo' v-for="(item1,index1) in cards[0].files" v-if="item.display[0].imagedis">
                  <div class="reson">
                    {{item1.reson}}
                  </div>
                    <img :style="'border:'+item1.border"
                        :src='item1.flowFileUrl' class='photo1' @click="photo(0,index1)"/>
                </div>
                <el-upload
                v-if="!addhidden"
                action="/qishui/qsapp/api/apply/zlsc"
                list-type="picture-card"
                :data="item.upfiledata"
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
        </div>
    </div>
</template>
<script>
import { Datetime, PopupPicker, Group, Cell, Picker, XButton, Divider, XSwitch } from 'vux'
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
        Datetime
    },
  props:{
    cards:{
      type:Array,
      default:null
    }
  },
    data(){
        return{
                sclass: "select",
                dclass: "select",
                value:[''],
                dtext: '离异时间',
                bkcz:'none',
                dhidden: true,
                backhidden:true,
                array: [['现在的婚姻状况', '已婚', '未婚', '离异',"丧偶"]],
                phototext: '已婚需上传结婚证，离异需上传离婚证（如有法院判决书则需一并上传），未婚需上传未婚声明。',
                hhindex: 0,
                cango1: "0",
                cango2: "0",
                back: "#cdd4df",
                addhidden:true,
          processLength:0,
          showProcess:false
        }
    },
    computed:{
        date(){
            var year  = this.now().substring(0, 4) + "年" 
            var month= this.now().substring(5, 7) + "月" 
            var day=this.now().substring(8, 10) + "日"
            return year+month+day
        },
        enddate(){
            this.now()
        },
        startdate(){
            this.starttime()
        }
    },
  mounted(){

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
        jlhzt(value){
            var value=value[0];
            if (value == "现在的婚姻状况") {
                this.sclass="select"
                this.dhidden=true
                this.phototext='已婚需上传结婚证，离异需上传离婚证（如有法院判决书则需一并上传）'
                return;
            } else if (value == "已婚") {
                this.hhindex = 1;
                this.sclass= "select2";
                this.dtext= "结婚时间";
                this.dhidden= false;
                this.phototext= '请上传结婚证';
                this.cards[0].image1dis="block";
                this.addhidden=false;
            } else if (value == "离异") {
                this.hhindex = 3;
                this.sclass= "select2";
                this.dtext= "离婚时间";
                this.dhidden=false;
                this.phototext= '请上传离婚证（如有法院判决书则需一并上传）。'
                this.cards[0].image1dis="block";
                this.addhidden=false;
            } else if (value == "丧偶") {
                this.hhindex = 4
                this.sclass= "select2",
                this.dhidden= true,
                this.phototext= ''
            } else {
                this.hhindex = 2
                this.sclass= "select2",
                this.dhidden= true,
                this.phototext= ''
            }
            Common.upValue(value,0, 0,"婚姻状况",this);
         },
      changeTime(value){
        console.log(value);
        Common.upValue(value,0, 1,this.dtext,this);
      },
            //结婚离婚时间
            jlhsj(that,date){
            this.dclass="select2",
               this.date=date
            },
            now(){
            var i = 0;
            var timestamp = Date.parse(new Date());
            timestamp = timestamp / 1000;
            //获取当前时间
            var n = timestamp * 1000;
            var date = new Date(n);
            //年
            var Y = date.getFullYear();
            //月
            var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1);
            //日
            var D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
            return Y + '-' + M + '-' + D
            },
             starttime(){
                var i = 0;
                var timestamp = Date.parse(new Date());
                timestamp = timestamp / 1000;
                //获取当前时间
                var n = timestamp * 1000;
                var date = new Date(n);
                //年
                var Y = date.getFullYear();
                //月
                var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1);
                //日
                var D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
                return (Y-80) + '-' + M + '-' + D
                },
  },
       deleteimg(index){
            var i = index;
            var that = this;
            wx.showModal({
            title: '提示',
            content: '您确定要删除么？',
            success:function(res){
                if(res.confirm){
                commonJs.deleteonephoto(that, i,function(){
                    sfgl(that);
                });
                }
            }
            })
        },
        next(){

        }
}
</script>

<style scoped>
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
  margin-left: 58rpx;
  margin-top: 20rpx;
}
.realname{
  background-color: white;
  width: 100%;
  height: 3em;
}
.realnametext{
  float: left;
  font-size: 33rpx;
  height: 100rpx;
  line-height: 100rpx;
  margin-left: 35rpx;
}
.pla{
  color:#E9E9E9;
}
.select{
  position: relative;
  width: 500rpx;
  height: 100rpx;
  float: left;
  /* margin-top: 17rpx; */
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
  /* margin-top: 17rpx; */
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
  top: 33rpx;
}
.phototext{
  width: 94%;
  margin-left: 1em;
  margin-top: 1em;
  font-size: 1em;
  font-weight: 600;
  letter-spacing: 0.1em;
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
.cards{
  margin: auto;
  width: 380rpx;
  height: 230rpx;
  background-color: #FFFFFF;
  border-radius: 20rpx;
  border: 1px #BBBBBB dashed;
  line-height: 230rpx;
  text-align: center;
  color: #EAEAEA;
  font-size: 30rpx;
  margin-top: 20rpx;
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
.add{
  margin-left: 7rpx;
  margin-top: 4rpx;
}
.reson{
  position: absolute;
  color: red;
}
.photo{
  float: left;
  width: 4.5em;
  height: 4.5em;
  margin-top: 1.5em;
  border-radius: 0.2em;
  background-color: white;
  margin-left: 1em;
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
.addphoto{
  float: left;
  width: 3em;
  height: 3em;
  border: 1px dashed #CBCBCB;
  border-radius: 0.2em;
  background-color: white;
  margin-left: 1em;
  line-height: 3em;
  font-size: 1.5em;
  text-align: center;
  color: #C1C1C1;
  font-weight: lighter;
  margin-top: 1em;
}
>>> .el-upload--picture-card{
  float: left;
  width: 3em;
  height: 3em;
  border: 1px dashed #CBCBCB;
  border-radius: 0.2em;
  background-color: white;
  margin-left: 1em;
  line-height: 3em;
  font-size: 1.5em;
  text-align: center;
  color: #C1C1C1;
  font-weight: lighter;
  margin-top: 1em;
}
.delete{
  width: 30rpx;
  height: 30rpx;
  margin-left: 10rpx;
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
