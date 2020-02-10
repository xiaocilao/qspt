<template>
    <div>
        <div class='bookstext'>
            请依次浏览《家庭住房情况说明书》、《家庭住房情况查询授权书》、《婚姻声明书》、并依次进行电子签名，我们将对您的信息进行审核。
        </div>
        <div class='books' style='position:relative'>
        <div class='bookimgs' v-for="(item,index) in books" :key="index">
            <div class='bookimg'>
              <div class="reson">{{cards[0].files[index].reson}}</div>
            <div class="seebook" @click="photo(0,index)">预览</div>
              <img :style="'border:'+cards[0].files[1].border+';height:100%;width:100%'"
                     :src='cards[0].files[index].flowFileUrl'>
            </div>
            <div class='see' :style="'background-color:'+item.back">{{item.state}}</div>
        </div>
        </div>

      <canvas id="can_vans1"></canvas>
    </div>
</template>
<style scoped>
  .reson{
    position: absolute;
    color: red;
  }
.bookimgs{
  width: 6.5em;
  margin: auto;
  margin-top: 1em;
  float: left;
  margin-left: 0.5em;
  height: 9em;

}
  .bookimg{
    width: 100%;
    height: 100%;
    position: relative;
  }
  .seebook{
    position: absolute;
    width: 100%;
    height: 100%;
    text-align: center;
    line-height: 9em;
    background-color: rgba(0,0,0,0.5);
    color: white;
  }
  .see{
    color: white;
    text-align: center;
  }
</style>
<script>
  import Common from '@/lib/common';
  import axios from "axios";
  import { Loading } from 'element-ui';
export default {
  mounted(){

  },
  props:{
    cards:{
      type:Array,
      default:null
    }
  },
    data(){
            return{
            books: [
                {
                    id: 0,
                    bookname: require("@/assets/static/images/bookimg/book1.jpg"),
                    url: '@/assets/static/images/bookimg/bookimg?id=0',
                    src: '/pages/sign/sign?',
                    state: '未签名',
                    back:"#cdd4df"
                },
                {
                    id: 1,
                    bookname: require("@/assets/static/images/bookimg/book2.jpg"),
                    url: '@/assets/static/images/bookimg/bookimg?id=1',
                    src: '/pages/sign/sign?',
                    state: '未签名',
                    back: "#cdd4df"
                },
                {
                    id: 2,
                    bookname: require("@/assets/static/images/bookimg/book3.jpg"),
                    url: '@/assets/static/images/bookimg/bookimg?id=2',
                    src: '/pages/sign/sign?',
                    state: '未签名',
                    back: "#cdd4df"
                },
            ],
            back:"#cdd4df",
            tag:'1',
        }
    },
    mounted:function(){
      for (var i = 0; i < this.cards[0].files.length; i++) {
        if(this.cards[0].files[i].flowFileUrl.substr(0,17)=="/qishui/qsapp/api" && this.cards[0].files[i].reson==='yes'){
              this.books[i].state="已签名";
              this.books[i].back="#28507B";
        }
      }
    },
    methods:{
      photo(i,index){
        var that = this;
        if(that.cards[i].files[index].flowFileUrl.substr(0,17)!="/qishui/qsapp/api") {
          let canvas = document.getElementById('can_vans1')
          let ctx = canvas.getContext('2d')
          var oImg = new Image();
          ctx.drawImage(oImg, 0, 0);
          var img = dataURLtoBlob(canvas.toDataURL('image/png'))
          let formData = new FormData();
          if (this.cards[i].upfiledata.flowType) {
            formData.append('flowType', this.cards[i].upfiledata.flowType);
          }
          if (this.cards[i].upfiledata.applyId) {
            formData.append('applyId', this.cards[i].upfiledata.applyId);
          }
          if (this.cards[i].upfiledata.flowId) {
            formData.append('flowId', this.cards[i].upfiledata.flowId);
          }
          if (this.cards[i].upfiledata.infoId) {
            formData.append('infoId', this.cards[i].upfiledata.infoId);
          }
          if (this.cards[i].files[index].flowFileId) {
            formData.append('fileId', this.cards[i].files[index].flowFileId);
          }
          switch (index) {
            case 0:
              formData.append('qmType', "jtzfqkcxsqs");
              break;
            case 1:
              formData.append('qmType', "jtzfqksms");
              break;
            case 2:
              formData.append('qmType', "hysms");
              break;
          }
          formData.append('file', img);
          formData.append("clientKey", Common.clientKey)
          const instance1 = axios.create({
            withCredentials: true
          })
          var options = {fullscreen: true, text: '加载中', body: false, target: 'gocontainer', lock: true}
          let loading = Loading.service(options);
          instance1.post('/qishui/qsapp/api/apply/zlsc', formData).then(res => {
            loading.close();
            Common.upfile(res.data, index, i, that, function () {
                that.cards[0].files[index].reson='no';
            Common.seeImg(i,index,that,1);
            })
          })
        }else{
          Common.seeImg(i,index,this,1);
        }
      },

    }
}
  function dataURLtoBlob(dataurl) {
    var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
      bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while(n--){
      u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], {type:mime});
  }
</script>
