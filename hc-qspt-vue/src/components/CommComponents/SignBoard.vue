<template>
    <div class="signatureBox">
        <div class="canvasBox" ref="canvasHW">
            <canvas @touchstart='touchStart'
                    @touchmove='touchMove'
                    @touchend='touchEnd'
                    ref="canvasF"
                    @mousedown="mouseDown"
                    @mousemove="mouseMove"
                    @mouseup="mouseUp"
                    id="can_vans"></canvas>
            <div class="btnBox">
                <button @click="overwrite">重写</button>
                <button @click="confirm">提交签名</button>
            </div>
        </div>
    </div>
</template>
<script>
    import axios from "axios";
    import Common from '@/lib/common'
    import { Loading } from 'element-ui';
    export default {
        props:{
            cards:{
                type:Array,
                default:null
            },
            site:{
                type:Object,
                default:{i:0,index:0}
            }
        },
        name: "SignBoard",
        data(){
            return{
                points:[],
                canvasTxt:null,
                startX:0,
                startY:0,
                moveY:0,
                moveX:0,
                endY:0,
                endX:0,
                w:null,
                h:null,
                isDown:false,
                ImgSrc:'',
                bosHigh:0,
                bosWidth:0,
                canvas1:null
            }
        },
        created(){

        },
        mounted(){
            let canvas=this.$refs.canvasF;
            canvas.height = this.$refs.canvasHW.offsetHeight;// - 60;
            canvas.width =this.$refs.canvasHW.offsetWidth;//-10;
            this.canvasTxt=canvas.getContext("2d");

            this.canvas1 = document.getElementById('can_vans');

        },
        methods:{
            confirm(){
                var that = this;
                let canvas = document.getElementById('can_vans')
                let ctx = canvas.getContext('2d')
                var oImg = new Image();
                ctx.drawImage(oImg, 0, 0);
                var img =  dataURLtoBlob(canvas.toDataURL('image/png'))
                let formData = new FormData();
                if(this.cards[this.site.i].upfiledata.flowType){
                    formData.append('flowType',this.cards[this.site.i].upfiledata.flowType);
                }
                if(this.cards[this.site.i].upfiledata.applyId){
                    formData.append('applyId',this.cards[this.site.i].upfiledata.applyId);
                }
                if(this.cards[this.site.i].upfiledata.flowId){
                    formData.append('flowId',this.cards[this.site.i].upfiledata.flowId);
                }
                if(this.cards[this.site.i].upfiledata.infoId){
                    formData.append('infoId',this.cards[this.site.i].upfiledata.infoId);
                }
                if(this.cards[this.site.i].files[this.site.index].flowFileId){
                    formData.append('fileId',this.cards[this.site.i].files[this.site.index].flowFileId);
                }
                switch (this.site.index) {
                    case 0:
                        formData.append('qmType',"jtzfqkcxsqs");
                        break;
                    case 1:
                        formData.append('qmType',"jtzfqksms");
                        break;
                    case 2:
                        formData.append('qmType',"hysms");
                        break;
                }
                formData.append('file',img);
                formData.append("clientKey",Common.clientKey)
                const instance=axios.create({
                    withCredentials: true
                })
                var options={fullscreen:true,text:'加载中',body:false,target:'gocontainer',lock:true}
                let loading = Loading.service(options);
                instance.post('/qishui/qsapp/api/apply/zlsc',formData).then(res=>{
                    loading.close();
                    Common.upfile(res.data,that.site.index,that.site.i,that,function () {
                        that.cards[0].files[that.site.index].reson='yes';
                        that.$emit("confirm")
                        var ex=true
                        for (var i=0;i<3;i++) {
                                if(that.cards[0].files[i].flowFileUrl.substr(0,18)!="/qishui/qsapp/api/"){
                                    ex=false;
                                }
                            }
                            return ex;
                        })

                })
            },
            backHome(){
                window.history.back();
            },
            //电脑设备事件
            mouseDown(ev){
                ev = ev || event;
                ev.preventDefault();
                console.log(ev);
                if(1){
                    let obj={
                        x:ev.offsetX,
                        y:ev.offsetY
                    };
                    console.log(obj);
                    this.startX=obj.x;
                    this.startY=obj.y;
                    this.canvasTxt.beginPath();
                    this.canvasTxt.lineWidth="5";

                    this.canvasTxt.moveTo(this.startX, this.startY);
                    this.canvasTxt.lineTo(obj.x*1.5, obj.y);
                    this.canvasTxt.stroke();
                    this.canvasTxt.closePath();
                    this.points.push(obj);
                    this.isDown=true;
                }
            },
            //移动设备事件
            touchStart(ev){
                ev = ev || event;
                ev.preventDefault();
                if(ev.touches.length == 1) {
                    let obj={
                        x:ev.targetTouches[0].clientX,
                        y:ev.targetTouches[0].clientY //-48
                    };
                    this.startX=obj.x;
                    this.startY=obj.y;
                    this.canvasTxt.beginPath();
                    this.canvasTxt.lineWidth="5";
                    console.log("x:"+this.startX+":y="+this.startY);
                    //this.canvasTxt.moveTo(this.startX, this.startY);
                    this.canvasTxt.lineTo(obj.x, obj.y);
                    this.canvasTxt.stroke();
                    this.canvasTxt.closePath();
                    this.points.push(obj);
                }
            },
            //电脑设备事件
            mouseMove(ev){
                ev = ev || event;
                ev.preventDefault();
                if(this.isDown){
                    let obj={
                        x:ev.offsetX,
                        y:ev.offsetY
                    };
                    this.moveY=obj.y;
                    this.moveX=obj.x;
                    this.canvasTxt.beginPath();
                    this.canvasTxt.lineWidth="5";
                    // this.canvasTxt.moveTo(this.startX, this.startY);

                    this.canvasTxt.lineTo(obj.x, obj.y);
                    this.canvasTxt.stroke();
                    this.canvasTxt.closePath();
                    this.startY=obj.y;
                    this.startX=obj.x;
                    this.points.push(obj);
                }
            },
            //移动设备事件
            touchMove(ev){
                ev = ev || event;
                ev.preventDefault();
                // if(ev.touches.length == 1) {
                    let obj={
                        x:ev.targetTouches[0].clientX,
                        y:ev.targetTouches[0].clientY //-48
                    };
                    this.moveY=obj.y;
                    this.moveX=obj.x;
                    this.canvasTxt.beginPath();
                    this.canvasTxt.lineWidth="5";
                    this.canvasTxt.moveTo(this.startX, this.startY);
                    this.canvasTxt.lineTo(obj.x, obj.y);
                    this.canvasTxt.stroke();
                    this.canvasTxt.closePath();
                    this.startY=obj.y;
                    this.startX=obj.x;
                    this.points.push(obj);
                // }
            },
            //电脑设备事件
            mouseUp(ev){
                ev = ev || event;
                ev.preventDefault();
                if(1){
                    let obj={
                        x:ev.offsetX,
                        y:ev.offsetY
                    };
                    this.canvasTxt.beginPath();
                    this.canvasTxt.lineWidth="5";
                    this.canvasTxt.moveTo(this.startX, this.startY);
                    this.canvasTxt.lineTo(obj.x, obj.y);
                    this.canvasTxt.stroke();
                    this.canvasTxt.closePath();
                    this.points.push(obj);
                    this.points.push({x:-1,y:-1});
                    this.isDown=false;
                }
            },
            //移动设备事件
            touchEnd(ev){
                ev = ev || event;
                ev.preventDefault();
                console.log(ev);
                if(ev.touches.length == 1) {
                    let obj={
                        x:ev.targetTouches[0].clientX,
                        y:ev.targetTouches[0].clientY //-48
                    };
                    this.canvasTxt.beginPath();
                    this.canvasTxt.lineWidth="5";
                    this.canvasTxt.moveTo(this.startX, this.startY);
                    this.canvasTxt.lineTo(obj.x, obj.y);
                    this.canvasTxt.stroke();
                    this.canvasTxt.closePath();
                    this.points.push(obj);
                    this.points.push({x:-1,y:-1});
                }
            },
            //重写
            overwrite(){
                this.canvasTxt.clearRect(0, 0, this.$refs.canvasF.width, this.$refs.canvasF.height);
                this.points=[];
            }
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

<style scoped>
    .signatureBox{
        position: absolute;
        top: 0px;
        left: 0px;
        width: 100%;
        height: 100%;
        box-sizing: border-box;
        overflow: hidden;
        background: #fff;
        z-index: 100;
        display: flex;
        flex-direction: column;
    }
    .visaDetailTop{
        /*position: absolute;*/
        /*top: 0px;*/
        /*left: 0px;*/
        width: 100%;
        /*overflow: hidden;*/
        padding: 5px;
        box-sizing: border-box;
        z-index: 2;
        border-bottom: 1px solid #e5e5e5;
    }
    .visaDetailTop p{
        margin: 0px;
        text-align: center;
        color: #000;
        font-size: 1em;
        position: relative;
    }
    p.visaTitle{
        width: 100%;
        position: absolute;
        top: 5px;
        left: 0px;
        text-align: center;
        font-size: 1.2em;
    }
    .btnBack{
        display: block;
        position: absolute;
        top: 0px;
        left: 0px;
        width: 100%;
        height: 100%;
        z-index: 1;
        background: transparent;
        border-color: transparent;
        outline: none;
    }
    .btnDaoHang{
        display: block;
        position: absolute;
        left: 0px;
        top: 0px;
        height: 2.2em;
        width: 2em;
        z-index: 1;
        background: transparent;
        border-color: transparent;
        outline: none;
    }
    .visaDetailTop p span{
        color: #fff;
        font-size: 1.2em;
    }
    .visaDetailTop p:first-of-type{
        float: left;
    }
    .visaDetailTop p:nth-of-type(2){
        float: right;
    }
    .canvasBox{
        padding: 10px 5px;
        box-sizing: border-box;
        flex: 1;
    }
    canvas{
        border: 1px solid #2B4D7E;
        height: 100%;
        width: 90%;
    }
    .btnBox{
        position: absolute;
        right: -3.1em;
        top:3.3em;
        height: 30px;
        padding: 5px;
        text-align: right;
        line-height: 30px;
        transform:rotate(90deg);
        -ms-transform:rotate(90deg); 	/* IE 9 */
        -moz-transform:rotate(90deg); 	/* Firefox */
        -webkit-transform:rotate(90deg); /* Safari 和 Chrome */
        -o-transform:rotate(90deg);
    }
    .btnBox button:first-of-type{
        border: 1px solid #2B4D7E;
        background: transparent;
        border-radius: 4px;
        padding: 5px 10px;
    }
    .btnBox button:last-of-type{
        border: 1px solid #2B4D7E;
        background: #2B4D7E;
        color: #fff;
        border-radius: 4px;
        padding: 5px 10px;
    }
    @media only screen and (min-width: 750px){
        .signatureBox{
            position: absolute;
            top: 0px;
            left: 0px;
            width: 100%;
            min-height: 100%;
            box-sizing: border-box;
            overflow: visible;
        }
    }
</style>

