<template>
    <div>
        <div class='topertext'>此处要填写并上传
            <div class='topertext1'>房屋所有人</div>
            的身份信息
            <div class='tip1'>*第一条数据的信息将作为您的个人信息</div>
        </div>
        <div v-for='(item,index) in cards' >
            <div style="height: 1em;width: 100%">
              <img alt="" src="@/assets/static/images/delete.png" style="width: 1em;height: 1em;float: right;margin-right: 0.5em" @click="deleteAll(index)"/>
            </div>
            <group title="">
              <x-input title="真实姓名" placeholder="真实姓名" novalidate :icon-type="item.icontype[0]" :show-clear="false" @on-blur="(value) => upValue(value, index, '0','真实姓名','name')" placeholder-align="left" :value="item.data[0].keyValue[0]"></x-input>
              <x-input title="身份证号" placeholder="身份证号" novalidate :icon-type="item.icontype[1]" :show-clear="false" @on-blur="(value) => upValue(value, index, '1','身份证号','idcard')" placeholder-align="left" :value="item.data[1].keyValue[0]"></x-input>
            </group>
            <div class='idcardphoto'>
                <el-upload
                  v-if="item.display[0].viewdis"
                  action="/qishui/qsapp/api/apply/zlsc"
                  list-type="picture-card"
                  :data="item.upfiledata"
                  :before-upload="beforeUpload"
                  :on-success="(response, file, fileList) => upfile(response, file, fileList,'0',index)"
                  :show-file-list="false"
                  :on-change="handleChange"
                  :limit="1"
                  :on-exceed="handleExceed"
                  accept=".jpg,.jpeg,.png,.JPG,.JPEG,.PBG">
                  身份证正面
                </el-upload>

              <div class='idcardphotoinner1'v-if="item.display[0].imagedis">
                <div class="reson" v-if="item.display[0].imagedis">{{item.files[0].reson}}</div>
                <img alt=""  :src='item.files[0].flowFileUrl'  class='idcardphotoinner2'
                     :style="'border:'+item.files[0].border" v-if="item.display[0].imagedis" @click="photo(index,0)">
              </div>

              <el-upload
                v-if="item.display[1].viewdis"
                action="/qishui/qsapp/api/apply/zlsc"
                list-type="picture-card"
                :data="item.upfiledata"
                :on-change="handleChange"
                :before-upload="beforeUpload"
                :on-success="(response, file, fileList) => upfile(response, file, fileList,'1',index)"
                :show-file-list="false"
                :limit="1"
                :on-exceed="handleExceed"
                accept=".jpg,.jpeg,.png,.JPG,.JPEG,.PBG">
                身份证反面
              </el-upload>
              <div class='idcardphotoinner1' v-if="item.display[1].imagedis">
                <div class="reson" v-if="item.display[1].imagedis">{{item.files[1].reson}}</div>
                <img alt="" :src='item.files[1].flowFileUrl' class='idcardphotoinner2'
                     :style="'border:'+item.files[1].border" v-if="item.display[1].imagedis" @click="photo(index,1)">
              </div>
            </div>
        </div>
      <div v-show="showProcess">正在上传...
      <el-progress  :percentage="processLength" :stroke-width="2"></el-progress>
      </div>
      <div class='add' @click='add'>{{ishidde}}</div>
    </div>
</template>
<script>
  import { XInput } from 'vux'
  import { Group } from 'vux'
  import Common from '@/lib/common'
  import imageConversion from 'image-conversion'
export default {
  components: {
    XInput,
    Group
  },
  props:{
    cards:{
      type:Array,
      default:null
     }
  },
    data(){
        return{
            processStatus:"warning",
            cango1:"0",
            cango2: "0",
            back:"#cdd4df",
            nametip:[true],
            idtip:[true],
            ishidde: "添加持有人",
            idcard:[],
            name:[],
            backhidden:true,
            deletehidden:[],
            processLength:0,
            showProcess:false

        }
    },
  mounted(){
    // alert(this.cards);
  },
  methods:{
      beforeUpload (file) {
          var that = this;
          return new Promise((resolve, reject) => {
              let isLt2M = file.size / 1024 / 1024 < 4
              if (isLt2M) {
                  resolve(file)
              }
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
    handleExceed(files, fileList){
      if (fileList.length > 1) {
        this.$message.error('最多允许上传1个文件');
        return false;
      }
    },
    format(percentage) {

      return percentage === 100 ? '满' : `${percentage}%`;
    },
    deleteAll(index){
      Common.deleteAll(index,this);
    },
    photo(i,index){
      Common.seeImg(i,index,this);
    },
    upValue:function(value,index, i,key,type){
      var that = this;
      Common.upValue(value,index, i,key,this,type,function () {
        var ex = true;
        for(var k=0;k<that.cards.length;k++){
          for(var q=0;q<that.cards[k].data.length;q++){
            if(that.cards[k].icontype[q]!='success'){
              break;
            }
            if(q!=that.cards[k].data.length){
              break;
            }
          }
          if(that.cards[k].files.length!=2){
            break;
          }else{
            for (var j = 0; j < that.cards[k].files.length; j++) {
              if(that.cards[k].files[j].flowFileUrl==""){
                break;
              }

            }
            if(j!=that.cards[k].files.length){
              break;
            }
          }
        }
        if(k!=that.cards.length){
          ex = false
        }
        return ex;
      });
    },
    upfile:function(response, file, fileList,i,index){
      var that = this;
      Common.upfile(response,i,index,this,function () {
        var ex = true;
        for(var k=0;k<that.cards.length;k++){
          for(var j=0;j<that.cards[k].icontype.length;j++){
            if(that.cards[k].icontype[j]!='success'){
              break;
            }
          }
          if(j!=that.cards[k].icontype.length){
            break;
          }
          if(that.cards[k].files.length!=2){
            break;
          }else{
            for (var w = 0; w < that.cards[k].files.length; w++) {
              if(that.cards[k].files[w].flowFileUrl==""){
                break;
              }
            }
            if(w!=that.cards[k].files.length){
              break;
            }
          }
        }
        if(k!=that.cards.length){
          ex = false
        }
        return ex;
      });
    },
    next(){
      if (this.data.back =="#1c3e72"){
        commonJs.nextPersonal(this);
      }
    },
    add(){

        var that = this;
        let cango = 1;
       this.cards.forEach((item,index) => {
           if(item.data[0].keyValue[0]=='' || item.data[0].keyValue[1]==''||item.files.length<2){
               Common.loading(that,"请先确定前一人的信息已录入完成！")
               cango = 0;
           }
        });
       if( cango===1){
           Common.add(this);
       }
    }
  },
}
</script>
<style scoped>
.toper{
  position: relative;
  text-align: center;
  height: 60rpx;
  margin: auto;
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
  font-size: 30rpx;
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
  margin-left: 20rpx;
  margin-top: 20rpx;
  margin-bottom: 10rpx;
}
.topertext{
  text-align: center;
  width: 100%;
  font-size: 0.5em;
  height: 4em;
}
.topertext1{
  line-height: 2em;
  display: inline-block;
  color: #FEA828;
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
.realnameinput{
  width:520rpx;
  height: 100rpx;
  line-height: 100rpx;
  float: left;
  /* margin-top: 25rpx; */
  margin-left: 30rpx;
  font-size: 32rpx;
  color: #4d4d4d;
}
.pla{
  color:#E9E9E9;
}
.idcardphoto{
  position: relative;
  width: 100%;
  margin-top: 2em;
  margin-bottom: 0.5em;
  height: 7em;
  line-height: 2em;
  display: block;
}
.idcardphotoinner{
  width: 48%;
  height: 6em;
  background-color: #FFFFFF;
  display: block;
  border-radius: 0.5em;
  border: 1px #BBBBBB dashed;
  margin-left: 1%;
  line-height: 6em;
  text-align: center;
  color: #EAEAEA;
  font-size: 1em;
  float: left
}
>>> .el-upload--picture-card{
  width: 48%;
  height: 6em;
  background-color: #FFFFFF;
  display: block;
  border-radius: 0.5em;
  border: 1px #BBBBBB dashed;
  margin-left: 1%;
  line-height: 6em;
  text-align: center;
  color: #EAEAEA;
  font-size: 1em;
  float: left
}
.idcardphotoinner1{
   width: 48%;
   height: 6em;
   display: block;
   border-radius: 0.5em;
   margin-left: 1%;
   float: left;
 }
.idcardphotoinner2{
  width: 100%;
  height: 6em;
  display: block;
  border-radius: 0.5em;
  border: 1px #BBBBBB dashed;
  margin-left: 1%;
  float: left;
}
.add{
  margin-top: 1em;
  text-align: center;
  font-size: 1em;
  font-weight: 600;
  letter-spacing: 1px;
  color: #1c3e72;
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
.tip1{
  color: red;
  font-size: 20rpx;
  right: 50rpx;
  line-height: 2rpx;
  letter-spacing: 2rpx;
}
.reson{
  position: absolute;
  color: red;
}
.delete{
  width: 30rpx;
  height: 30rpx;
  margin: auto;
}
.deleteview{
  width: 100rpx;
  margin-left: 86%;
  margin-bottom: 14rpx;
  text-align: center;
}
</style>
