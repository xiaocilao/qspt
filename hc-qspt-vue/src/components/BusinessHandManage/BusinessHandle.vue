<template>
    <div>
        <div class='background' :hidden='backhidden'></div>
        <div class='toperswiper1'>
             <SwipperImage :images="swiperimgs"/>
        <div class='dots'>
            <div class='dot' v-for='(item,index) in swiperimgs' :key="index" :style="'background-color:' +item.background"></div>
        </div>
        </div>
        <div class='gocontainer'>
        <div class='goitem' :style="'border-width:'+item.border" v-for="(item,index) in goitem" :key="index"
                  @click='personal(item.url)' @touchstart='bright1(index)' @touchend='normal(index)' :hidden='item.hidden'>
            <div :class="'titles'+ bright[index]">
                <div class='first'>{{item.first}}</div>
                <div class='secend'>{{item.secend}}</div>
            </div>
            <img :src="item.src" :class="'goimg '+bright[index]" />
        </div>
            <div class="histContent">
                <i class="el-icon-warning"></i>
                温馨提示:
                <br>
                您正在操作的是在线缴纳房产契税功能，如果您有宝贵的意见或者建议，欢迎联系我们，您的支持是我们改进的最大动力。
            </div>
        </div>

        <view class='error'></view>

    </div>
</template>
<script>
import SwipperImage from "@/components/CommComponents/SwipperImage"
import Common from "@/lib/common"
export default {
     components:{
        SwipperImage
    },
    data(){
        return{
            bright: ['', '', '', ''],
            swiperimgs: [
                {
                    url: 'javascript',
                  img: require('@/assets/static/images/1.jpg')
                },
                {
                    url: 'javascript',
                    img: require('@/assets/static/images/a1.jpg')
                },
                {
                    url: 'javascript',
                    img: require('@/assets/static/images/a2.jpg')
                }
            ],
            text: [
                {
                    text: '自助办理',
                    url: 'personal',
                    tip: '',
                    sclass: 'go',
                    img: 'img/personal.png'
                },
            ],
            goitem: [

                {
                    border: '0rpx 1rpx 0rpx 0rpx',
                    first: '资料审核',
                    secend: '在家体验便民服务',
                    src: require('@/components/BusinessHandManage/img/BusinessManagement.png'),
                    url: 'personal',
                    hidden:false
                },
                {
                    border: '0rpx 0rpx 0rpx 1rpx',
                    first: '契税缴费',
                    secend: '方便快捷',
                    src: require('@/components/BusinessHandManage/img/synchronization.png'),
                    url: 'none',
                    hidden: false
                }
                // ,
                // {
                //     border: '0rpx 0rpx 0rpx 1rpx',
                //     first: '房产数据',
                //     secend: '一次授权自动同步',
                //     src: require('@/components/BusinessHandManage/img/synchronization.png'),
                //     url: 'none',
                //     hidden: false
                // }

            ],
            clientKey: "",
            backhidden:false,
            kydjtimeout:'',
            gotoUrl:''
        }
    },
    methods:{
         bright1(key){
            var i = key
            this.bright[i] = 'bright';
        },
        normal:function(key){
            var i = key
            this.bright[i] = '';
        },
        personal(val){
           if(val=="personal"){
             var that = this
             Common.FindUser(function (){
               Common.Spjl(function (data) {
                 that.$router.push({
                   path:"process",
                   query: {data:data}
                 });
               });
             },function () {
               that.$router.push({
                 path:"login",
                 params: {}
               });
             });
           }else{
               var that1 = this
               Common.FindUser(function (){
                   Common.Spjl(function (data) {
                     if(data.spzt=="3"){
                         that1.$router.push({
                             path:"/ProcessManage/Process09",
                             params: {}
                         });
                     }else{
                         if(data.spzt=="8" ||data.spzt=="9"){
                             Common.loading(that1,'费用已缴纳，请勿重复提交');

                         }else if(data.spzt=="1"){
                             Common.loading(that1,'正在查看您的缴税资料，暂时不能进行缴费操作');
                         }
                         else{
                             Common.loading(that1,'请先在资料审核中填写相信息，税务局审核后才可以缴费哦~');

                         }
                         that1.$router.push({
                             path:"index",
                             params: {}
                         });
                     }
                   });
               },function () {
                   that.$router.push({
                       path:"index",
                       params: {}
                   });
               });



             //跳转到房管数据同步接口
             // var that1 = this
             // Common.FindUser(function (){
             //   Common.Spjl(function (data) {
             //       that1.$router.push({
             //         path:"Agree",
             //         query: {data:data}
             //       });
             //   });
             // }, function () {
             //   that.$router.push({
             //     path: "login",
             //     params: {}
             //   });
             // });
           }

        },

    },
    mounted(){

    }
}
</script>
<style  scoped>
.gocontainer{
  width:98%;
  height:5.3em;
  background:rgba(255,255,255,1);
  box-shadow:0em 0em 0.1em 0.1em rgba(120,120,120,0.1);
  border-radius:0.3em;
  margin: auto;
}
.goitem{
  width: 48.2%;
  height: 5em;
  border: solid #F2F2F2;
  box-shadow: none;
  float: left;
}
.titles{
  margin-left: 2%;
  margin-top: 10%;
  width: 60%;
  float: left;
}
.first{
  color: #333333;
  font-size: 0.9em;
  font-weight: 600;
  font-family: initial;
}
.secend{
  font-size: 0.7em;
  color: #808080;
  font-weight: 500;
}
.goimg{
  width: 32%;
  height: 65%;
  margin-top: 9%;
  margin-bottom: 2%;
  margin-left: 2%;
  float: left;
  border-radius: 0.5em;
}
@media screen and (max-width: 320px){
  .goitem{
    width: 47.9%;
    height: 5em;
    border: solid #F2F2F2;
    box-shadow: none;
    float: left;
  }
}
@media screen and (min-width: 700px){
  .goitem{
    height: 10em;
  }
  .gocontainer{
    height:10.4em;
  }
  .first{
    font-size: 2em;
  }
  .secend{
    font-size: 1.5em;
  }
  .titles{
    margin-top: 12%;
    margin-left: 3%;
  }
}
.toperswiper{
  width: 690rpx;
  height: 352rpx;
  margin: auto;
  border-radius: 10rpx;
  overflow: hidden;
}
.toperswiper1{
  width: 690rpx;
  margin: auto;
  border-bottom: 10px solid #F9F9F9;
  border-radius: 10rpx;
  overflow: hidden;
  margin-bottom: 18rpx;
}
.dots{
  width: 690rpx;
  height: 15rpx;
  margin: auto;
  margin-top: 22rpx;
  text-align: center;
  line-height: 0rpx;
}
.dot{
  width: 15rpx;
  height: 15rpx;
  border-radius: 15rpx;
  background-color: #E6E6E6;
  margin-left: 23rpx;
  display: inline-block;
}
.slide-image{
  width: 100%;
  height: 100%;
  border-radius: 10rpx;
}
.go{
  width: 100%;
  height: 100rpx;
}
.go1{
  width: 100%;
  height: 100rpx;
  border-top: 1px solid #F9F9F9;
}
.goto{
  font-size: 35rpx;
  color: #000000;
  display: inline-block;
  margin-top: 30rpx;
  margin-left: 30px;
}
.gotoicon{
  float: right;
  width: 20rpx;
  height: 35rpx;
  margin-top: 35rpx;
  margin-right: 20rpx;
}
.gotoimg{
  float: left;
  width: 35rpx;
  height: 35rpx;
  margin-top: 35rpx;
  margin-left: 30rpx;
  margin-right: -28rpx;
}
.bottom{
  width: 747rpx;
  height: 120rpx;
  position: fixed;
  bottom: 0px;
  background-color: #FFFFFF;
  border-top: 1rpx solid #D9D9D9;
  margin: auto;
}
.bottomimgs{
  text-align: center;
  width: 230rpx;
  margin-top: 23rpx;
  float: left;
}
.bottomimg{
  width: 40rpx;
  height: 35rpx;
}
.bottomimg1{
  width: 42rpx;
  height: 40rpx;
}
.bottomimg2{
  width: 40rpx;
  height: 40rpx;
}
.bottomtext{
  font-size: 25rpx;
  color: #D4D4D4;
  margin-top: 5rpx;
}
.bottomtext1{
  margin-top: 5rpx;
  font-size: 25rpx;
  color: #294680;
}
.tip{
  float: right;
  font-size: 25rpx;
  margin-top: 38rpx;
  margin-right: 15rpx;
  color: #A7A7A7;
}
.error{
  width: 100%;
  height: 200rpx;
}
.histContent{
    z-index:-1;
    color:#D1D1D1;
    font-size: 14px;
    top:10px;
    position: relative;
}

</style>
