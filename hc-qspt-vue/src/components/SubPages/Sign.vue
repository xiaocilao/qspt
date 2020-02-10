<template>
    <div>
        <!--pages/sign/sign.wxml-->
        <div class='background' :hidden='backhidden'></div>
        <div class='content'>
            <div class='button'>
                <div class='fh' @click='back()'>返回</div>
                <div class='qc' @click='clearClick()'>
                    <image src='img/qc.png' class='qcimg'></image>
                    清除签名
                </div>
                <div class='qx' @click='saveClick()'>保存上传</div>
            </div>
             <canvas id="canvas"  width="350em" height="500em" >Canvas画板</canvas>

        </div>
        <div class='bkcz' :style="'display:'+bkcz">上传中，请稍等......</div>

    </div>
</template>
<script>
var draw;
var preHandler = function(e){e.preventDefault();}
class Draw {
  constructor(el) {
    this.el = el
    this.canvas = document.getElementById(this.el)
    this.cxt = this.canvas.getContext('2d')
    this.stage_info = canvas.getBoundingClientRect()
    this.path = {
      beginX: 0,
      beginY: 0,
      endX: 0,
      endY: 0
    }
  }
  init(btn) {
    var that = this; 
     
    this.canvas.addEventListener('touchstart', function(event) {
      document.addEventListener('touchstart', preHandler, false); 
      that.drawBegin(event)
    })
    this.canvas.addEventListener('touchend', function(event) { 
      document.addEventListener('touchend', preHandler, false); 
      that.drawEnd()
       
    })
    this.clear(btn)
  }
  drawBegin(e) {
    var that = this;
    window.getSelection() ? window.getSelection().removeAllRanges() : document.selection.empty()
    this.cxt.strokeStyle = "#000"
    this.cxt.beginPath()
    this.cxt.moveTo(
      e.changedTouches[0].clientX - this.stage_info.left,
      e.changedTouches[0].clientY - this.stage_info.top
    )
    this.path.beginX = e.changedTouches[0].clientX - this.stage_info.left
    this.path.beginY = e.changedTouches[0].clientY - this.stage_info.top
    canvas.addEventListener("touchmove",function(){
      that.drawing(event)
    })
  }
  drawing(e) {
    this.cxt.lineTo(
      e.changedTouches[0].clientX - this.stage_info.left,
      e.changedTouches[0].clientY - this.stage_info.top
    )
    this.path.endX = e.changedTouches[0].clientX - this.stage_info.left
    this.path.endY = e.changedTouches[0].clientY - this.stage_info.top
    this.cxt.stroke()
  }
  drawEnd() {
    document.removeEventListener('touchstart', preHandler, false); 
    document.removeEventListener('touchend', preHandler, false);
    document.removeEventListener('touchmove', preHandler, false);
    //canvas.ontouchmove = canvas.ontouchend = null
  }
  clear(btn) {
    this.cxt.clearRect(0, 0, 300, 600)
  }
  save(){
    return canvas.toDataURL("image/png")
  }
}
 
export default {
 data () {
  return {
   msg: 'Welcome to Your Vue.js App',
   val:true,
   url:""
  }
 },
 mounted() {
   draw=new Draw('canvas');
   draw.init();
 },
 methods:{
  clearClick:function(){
    draw.clear();
  },
   saveClick: function () {
       var data=draw.save();
       this.url = data;
    console.log(data)
      var that = this
      wx.canvasToTempFilePath({
        canvasId: 'firstCanvas',
        success: function (fileRes) {
          commonJs.bkcz(that);
          //打印图片路径
          console.log(fileRes.tempFilePath)
          //设置保存的图片
          if (!that.data.cards[0].files[that.data.imgid]) {
            that.data.cards[0].files[that.data.imgid] = {};
          }
          var header = {
            'cookie': "clientKey=" + that.data.clientKey,
            "Content-Type": "multipart/form-data"
          };
          var formData = { flowType: that.data.toper[that.data.index - 1].flowType };
          if (that.data.applyId) {
            formData.applyId = that.data.applyId;
          }
          if (that.data.flowId) {
            formData.flowId = that.data.flowId;
          }
          if (that.data.cards[0].flowInfoId) {
            formData.infoId = that.data.cards[0].flowInfoId;
          }
          if (that.data.cards[0].files[that.data.imgid].flowFileId) {
            formData.fileId = that.data.cards[0].files[that.data.imgid].flowFileId;
          }
          if (that.data.qmType){
            formData.qmType = that.data.qmType;
          }
          console.log(formData);
          wx.uploadFile({
            url: commonJs.url + '/qishui/qsapp/api/apply/zlsc',
            filePath: fileRes.tempFilePath,
            header: header,
            method: "POST",
            formData: formData,
            name: 'file',
            success: function (res) {
              res.data = JSON.parse(res.data);
              if (res.data.code == "200") {
                if (res.data.rows[0].applyId) {
                  that.data.applyId = res.data.rows[0].applyId;
                }
                if (res.data.rows[0].flowId) {
                  that.data.flowId = res.data.rows[0].flowId;
                }
                if (res.data.rows[0].flowInfoId) {
                  that.data.cards[0].flowInfoId = res.data.rows[0].flowInfoId;
                }
                if (res.data.rows[0].flowFileId){
                  that.data.cards[0].files[that.data.imgid].flowFileId = res.data.rows[0].flowFileId;
                }
                that.data.cards[0].files[that.data.imgid].flowFileUrl = fileRes.tempFilePaths;
                that.setData(that.data);
                var pages = getCurrentPages();
                var prevPage = pages[pages.length - 2]; //上一个页面
                //直接调用上一个页面的setData()方法，把数据存到上一个页面中去
                if (!prevPage.data.cards[0].files[that.data.imgid]) {
                  prevPage.data.cards[0].files[that.data.imgid] = {};
                }
                prevPage.data.cxqmdis = false;
                prevPage.data.qmdis = true;
                prevPage.data.cards[0].files[that.data.imgid].shzt = 0;
                prevPage.data.cards[0].files[that.data.imgid].flowFileBz = that.data.qmType;
                prevPage.data.cards[0].files[that.data.imgid].flowFileId = that.data.cards[0].files[that.data.imgid].flowFileId;
                prevPage.data.cards[0].flowInfoId = that.data.cards[0].flowInfoId;
                prevPage.data.cards[0].flowId = that.data.cards[0].flowId;
                prevPage.data.uploadtext = "同意上传";
                prevPage.data.imgurl = commonJs.url + '/qishui/qsapp/api/apply/fjxx.png?fileId=' + that.data.cards[0].files[that.data.imgid].flowFileId + "&random=" + Math.random();
                prevPage.setData(prevPage.data);
                wx.navigateBack({
                  delta: 1,
                })
              } else {
                console.log(res.data.msg);
              }
              commonJs.kydj(that);
            },
            fail:function(){
                console.log("请求失败");
            }
          })
        }
      })

    },
 
　　　mutate(word) {
     this.$emit("input", word);
   },
} 
} 
</script> 
<style scoped>
/* pages/sign/sign.wxss */
.content {
  width: 100%;
  height: 500px;
  background-color: white;
}
/* .firstCanvas {
  float: left;
  background-color: white;
  width: 80%;
  height: 100%;
  background-color: blue; 
}
image {
  width: 100%;
  height: 200px;
  background-color: white;
} */
.canvas{
  z-index: 1;
  position: absolute;
  width: 90%;
  height: 100%;
  background: white;
}
.bu{
  float: left;
  width: 50%;
  height: 100%;
  line-height: 80rpx;
}
/* .button{
  z-index: 99;
  position: absolute;
 -webkit-transform:rotate(90deg);
  width: 1225rpx;
  height: 80rpx;
  line-height: 80rpx;
  top: 48%;
  left: 13%;
  background-color: #F9F9F9;
} */
.button{
  z-index: 99;
  position: absolute;
  width: 10%;
  height: 100%;
  left: 90%;
  line-height: 80rpx;
  /* top: 48%;
  left: 13%; */
  background-color: #F9F9F9;
}
.fh{
   -webkit-transform:rotate(90deg);
  width: 150rpx;
  height: 50rpx;
  margin-top: 50%;
  margin-left: -52%;
  /* margin-left: 28rpx; */
  line-height: 50rpx;
  text-align: center;
  /* float: left; */
  /* position: relative; */
  font-size: 28rpx;
  color: #8E8E8E;
  /* margin-top: 17rpx; */
}
.fhimg{
  position: absolute;
  width: 20rpx;
  height: 32rpx;
  top: 10rpx;
  left: 16rpx;
}
.qc{
   -webkit-transform:rotate(90deg);
  width: 150rpx;
  height: 50rpx;
  margin-top: 650%;
  margin-left: -52%;
  /* margin-top: 17rpx; */
  line-height: 50rpx;
  font-size: 28rpx;
  text-align: center;
  /* float: left; */
  /* position: relative; */
  color: #929292;
}
.qcimg{
  position: absolute;
  width: 34rpx;
  height: 30rpx;
  top: 10rpx;
  left: -15rpx;
}
.qx{
   -webkit-transform:rotate(90deg);
  width: 154rpx;
  height: 44rpx;
  margin-top: 608%;
  margin-left: -52%;
  /* margin-top: 18rpx; */
  /* margin-right: 48rpx; */
  line-height: 44rpx;
  font-size: 20rpx;
  text-align: center;
  /* float: right; */
  background-color: #4381e6;
  color: white;
  border-radius: 44rpx;
}
.bkcz{
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.8);
  opacity: 0.8;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 99;
  text-align: center;
  font-size: 32rpx;
  color: white;
  line-height: 1000rpx;
  display: none;
}
</style>
