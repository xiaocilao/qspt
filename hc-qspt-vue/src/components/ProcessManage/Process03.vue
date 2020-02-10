<template>
    <div>
        <div class='topertext'>上传
            <div class='topertextfont'>本人、本人配偶、本人未成年子女</div>的户口簿信息
        </div>
         <div v-for='(item,index) in cards' :key="item.id" :index="index">
        
        <div class='renametoper'>{{title[index].first}}</div>
           <div style="height: 1em;width: 100%">
             <img src="@/assets/static/images/delete.png" style="width: 1em;height: 1em;float: right;margin-right: 0.5em" @click="deleteAll(index)"/>
           </div>
           <group title="">
             <x-input title="真实姓名" placeholder="真实姓名" novalidate :icon-type="item.icontype[0]" :show-clear="false" @on-blur="(value) => upValue(value, index, '0','真实姓名','name')" placeholder-align="left" :value="item.data[0].keyValue[0]"></x-input>
             <x-input title="身份证号" placeholder="身份证号" novalidate :icon-type="item.icontype[1]" :show-clear="false" @on-blur="(value) => upValue(value, index, '1','身份证号','idcard')" placeholder-align="left" :value="item.data[1].keyValue[0]"></x-input>
           </group>
        <div class='idcardphoto' style='position:relative;margin-top:50rpx;'>
          <el-upload
            v-if="item.display[0].viewdis"
            action="/qishui/qsapp/api/apply/zlsc"
            list-type="picture-card"
            :data="item.upfiledata"
            :before-upload="beforeUpload"
            :on-change="handleChange"
            :limit="1"
            :on-success="(response, file, fileList) => upfile(response, file, fileList,'0',index)"
            :show-file-list="false"
            accept=".jpg,.jpeg,.png,.JPG,.JPEG,.PBG">
            {{title[index].secend}}
          </el-upload>
            <div class='reson' :hidden='item.resonhidden'>{{item.reson}}</div>
          <div class="idcardphotoinner"v-if="item.display[0].imagedis">
            <div class="reson" v-if="item.display[0].imagedis">{{item.files[0].reson}}</div>
            <img :src='item.files[0].flowFileUrl' class='idcardphotoinner1' :data-index='item.id' @tap='photo'
                 :style="'border:'+item.files[0].border" v-if="item.display[0].imagedis" @click="photo(index,0)"/>
          </div>

        </div>
        </div>
      <div v-show="showProcess">正在上传...
        <el-progress  :percentage="processLength" :stroke-width="2"></el-progress>
      </div>
        <div class='add' @click='add' :hidden='ishiddenadd'>点击添加家庭成员</div>
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
    },
    title:{
      type:Array,
      default:null
    }
  },
    data(){
        return{
            ishidden: true,
            ishiddenadd: false,
            cango1: "0",
            cango2: "0",
            back: "#cdd4df",
            nametip:[true],
            idtip:[true],
            idcard:[],
            name:[],
            deletehidden: [],
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
      deleteAll(index){
        Common.deleteAll(index,this);
      },
      photo(i,index){
        Common.seeImg(i,index,this);
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
          }
          if(k!=that.cards.length){
            ex = false
          }
          return ex;
        });
        console.log(response);
      },
      upValue:function(value,index, i,key,type){
        var that = this;
        Common.upValue(value,index, i,key,this,type,function () {
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
          }
          if(k!=that.cards.length){
            ex = false
          }
          return ex;
        });
      },
        add:function(){
          var that = this
          Common.add(this,function () {
            that.title.push({
              first:'附页',
              secend:'附页',
            })
          });
        },
        bkcz(that){
            if(that){
                that.data.kydjtimeout = setTimeout(function () {
                wx.showLoading({
                    title: '加载中',
                    mask: true,
                })
                }, 500)
                that.setData({
                backhidden: false
                })
            }else{
                wx.showLoading({
                title: '加载中',
                mask: true,
                })
            }
        },
        next(){

        },
         deletev: function () {
            var cards = this.data.cards;
            var that = this;
            if (cards[cards.length - 1].data.length == 0 && cards[cards.length - 1].files.length == 0) {
            if (cards.length - 2 != 0) {
                that.data.deletehidden[cards.length - 2] = false;
            }
            that.data.idcard[cards.length - 1] = "";
            that.data.name[cards.length - 1] = "";
            that.data.nametip[cards.length - 1] = true;
            that.data.idtip[cards.length - 1] = true;
            cards.splice(cards.length - 1, 1);
            that.data.ishiddenadd = false;
            sfgl(that);
            that.setData(that.data);
            }
        },

        namefocus: function (e) {
            this.data.nametip[e.currentTarget.dataset.index] = true;
            this.setData(this.data);
        },
        input(e) {
            var that = this;
            that.data.name[e.currentTarget.dataset.index] = e.detail.value;
            that.setData(that.data);
            commonJs.upValue(this, e.currentTarget.dataset.index, 0, e.currentTarget.dataset.keyname, e.detail.value, function () {
            sfgl(that);
            if (that.data.back == "#1c3e72") {
                that.data.ishiddenadd = false;
            } else {
                that.data.ishiddenadd = true;
            }
            if (!commonJs.xm(e.detail.value) && e.detail.value != "") {
                that.data.nametip[e.currentTarget.dataset.index] = false;
            } else {
                that.data.nametip[e.currentTarget.dataset.index] = true;
            }
            that.setData(that.data);
            });
        },
        idcardsfocus: function (e) {
            this.data.idtip[e.currentTarget.dataset.index] = true;
            this.setData(this.data);
        },
        input1(e) {
            var that = this;
            that.data.idcard[e.currentTarget.dataset.index] = e.detail.value;
            that.setData(that.data);
            commonJs.upValue(this, e.currentTarget.dataset.index, 1, e.currentTarget.dataset.keyname, e.detail.value, function () {
            sfgl(that);
            if (that.data.back == "#1c3e72") {
                that.data.ishiddenadd = false;
            } else {
                that.data.ishiddenadd = true;
            }
            if (!commonJs.idcard(e.detail.value) && e.detail.value != "") {
                that.data.idtip[e.currentTarget.dataset.index] = false;
            } else {
                that.data.idtip[e.currentTarget.dataset.index] = true;
            }
            that.setData(that.data);
            });
        },
        tip: function (e) {
            var index = e.currentTarget.dataset.index;
            if (!this.data.toper[index].url) {
            wx.showToast({
                title: '请点击下一步继续！',
                icon: "none",
                duration: 2000,
            })
            }
        }
    }
}
</script>

<style scoped>

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
  margin-left: 153rpx;
  margin-top: 20rpx;
}
/*.topertext{*/
/*  text-align: center;*/
/*  width: 100%;*/
/*  font-size: 26rpx;*/
/*  height: 90rpx;*/
/*  line-height: 90rpx;*/
/*}*/
.topertext{
  text-align: center;
  width: 100%;
  font-size: 0.5em;
  height: 4em;
}
.topertextfont{
  display: inline-block;
  color: red;
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
  width: 520rpx;
  height: 100rpx;
  line-height: 100rpx;
  float: left;
  /* margin-top: 24rpx; */
  margin-left: 30rpx;
  font-size: 32rpx;
  color: #4d4d4d;
}
.pla{
  color:#E9E9E9;
}
.idcardphoto{
  width: 100%;
  margin-top: 2em;
  margin-bottom: 2.5em;
  height: 7em;
}
.idcardphotoinner{
   margin: auto;
   width: 60%;
   height: 7em;
   background-color: #FFFFFF;
   border-radius: 0.5em;
   text-align: center;
   color: #EAEAEA;
   font-size: 1em;
   margin-left: 20%;
 }
.idcardphotoinner1{
  margin: auto;
  width: 100%;
  height: 7em;
  background-color: #FFFFFF;
  border-radius: 0.5em;
  line-height: 7em;
  text-align: center;
  color: #EAEAEA;
  font-size: 1em;
  float: left;
}
>>> .el-upload--picture-card{
  margin-left: 20%;
  width: 60%;
  height: 7em;
  background-color: #FFFFFF;
  border-radius: 0.5em;
  border: 1px #BBBBBB dashed;
  line-height: 7em;
  text-align: center;
  color: #EAEAEA;
  font-size: 1em;
}
.renametoper{
  font-size: 0.8em;
  margin-left: 1em;
  margin-bottom:0em;
  margin-top: 0em;
  font-weight: 600;
  display: inline-block;
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
.add{
  text-align: center;
  font-size: 30rpx;
  font-weight: 600;
  letter-spacing: 1px;
  color: #1c3e72;
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
  width: 30rpx;
  height: 30rpx;
  margin: auto;
}
.deleteview{
  width: 100rpx;
  text-align: center;
  margin-left: 550rpx;
  display: inline-block;
}
</style>
