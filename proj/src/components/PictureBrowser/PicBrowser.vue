<template>
    <div>
        <div class="container">
            <div class="center">
                <div class="status-mark" v-if="currentIndex>=0&&pictures[currentIndex].status!=0">
                    <img src="./assets/tgbq.png" v-if="pictures[currentIndex].status>0"/>
                    <img src="./assets/btgbq.png" v-else/>
                </div>
                <div class="picture">
                    <img class="picture-cropper" src="" alt=" "/>
                    <!--btn-prev1 btn-next1 -->
                    <img class="picture-prev button" src="./assets/btn-prev2.png" v-on:click="prev"/>
                    <img class="picture-next button" src="./assets/btn-next2.png" v-on:click="next"/>
                    <div class="picture-toggle">
                        <img class="picture-rotate_ac button" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAtCAYAAADsvzj/AAAAAXNSR0IArs4c6QAAAAlwSFlzAAALEwAACxMBAJqcGAAAAVlpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IlhNUCBDb3JlIDUuNC4wIj4KICAgPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4KICAgICAgPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIKICAgICAgICAgICAgeG1sbnM6dGlmZj0iaHR0cDovL25zLmFkb2JlLmNvbS90aWZmLzEuMC8iPgogICAgICAgICA8dGlmZjpPcmllbnRhdGlvbj4xPC90aWZmOk9yaWVudGF0aW9uPgogICAgICA8L3JkZjpEZXNjcmlwdGlvbj4KICAgPC9yZGY6UkRGPgo8L3g6eG1wbWV0YT4KTMInWQAABtpJREFUaAXFmluIlVUUxzszORfLkDIxUZqGssZb5kNmVviQFFH0UGBQGFGSUVT0kGgIBpHZhYhuEBiURGURQRck8qUoaNI0u4lljrfKnKLUUmfGmX7/Pfv/tWc8Z+acOXPOLPiftfbe61uXb9+/mdxJZVJPT08OE6NAZy6X65E56mphU8CFYHqUm+HjgNqOgd9AG9gOvgXfSU5s1FCupdwJrywR8MmCvSBPAEvAetAOSiHpfwgWAyUcCLkOKKnKEMbrbRl5PFgGtoN8dIzKv8ABsB8o6L9BF8hHP1B5PxgjH/BaoF4vSBoWJRNGG+jyo3oQ+TbYcnCuygntRW4Fm8BP4FdwEHSBBnAamAxmgdlAw3AsSGkzhQfx9bEq8VWH3JEqDFnGWGM0OgZ5LehPn1FxH7igWCfoavjMAavATpBSN4WHbAu5zvKQOUacxDnIm0BK31NYBEanDiiPAg0FUE99n/FPeRJ4GGgoipSI6BXbRR56MjwcAoRPA7tBSk9TOD1xpEVAQYoPOHxprwFKVvqZLvIM8AkQHe1lPetSH5aL5hjRmNYY1dtqi0bFOsBCG0JW4AoqC8htxXCeU1JZQsia5GuAyMk8b1vU9elN1+flKIduhCtIjX/TvwhX+CHkISdgG+bYCgkl5WeiU7040RK1wXOC9QpylGRQG5geeg6YtGzOj/WZjsrDSfhIl/jX7BzeCWbKF3zwXkHJQ+oa5JQWRSN6I4MbKiM77DuGRuSvkiA22Cx1hXuFRg8pjdkvEgNrEgMVTSLx44XmMuLwXFFIN0oHXlQid+qJSPvgE+PDYcjZWSU5PtXzfrHPxljEWgf0i0I4DsDVnZ8D071+kIrCb8FKw8jx5yF2NvLvwHR1QTdoOPvrkL1SaMMLhzl4dlAsaKQCDfgNkx/+IjCtlasTxjitOjr7PHMVOj6svUV9e4zveOTVZo73dRz7eL+AmMeG5QshOypLVnTwM4CPIYeRL4/12k8CVK4m4ddbgTbKrcB0ZQ1vuVs9IE6tFLtjcC3w86KsS883UdYJtEuI+rG68gyfx/FZL463LxOPF4euonGCKqOCV6OpVIX7APxr2nSIU+La0YOO9JGrOumJxcNrI7JpunblRyhthq9QLcHpGipqCr+9P9vEaFOvzUD8CP4mGEcdrKrJhOs0MWwHHj3NmgspraYxEJVvxAYFf6sq4bOA9hLTHbHevRiereQPjr1yTUX+JwayS8GtigWdX0QrY3Bv9xZ7dE2dCc4C6T38T8rqHdlwd6tYUcKX97jJyIpBdCg4RfDpMtTyo/vFB7GwH677c7pKZCdf6quWhILFnxOZiOwXeyR7e1Q+BUTuGZ9pjlCnTVETW5QuxcUdozMv5Qv49/bQjHxQAUHtyjDbpZGfUC3k3dzXTCd3iLZ5CgeutbyqvRH9eo7MTeLUd7EQVJrMahREmhtKRD0iUvaXRv0RSSL69neDW0JUvT/vqy0Q5TSZx6KS5oJIScyVInzEkoj+fXh8UoFFylbbfMmsjEoaZj6ejEKu9gYYYotJaN8L/uGfAtMNmZIFWrI9AXk+uChpq/qcsG9xYnFvtCDrS6VI8zbckVLdINPQZzWiPKIJOEDi8PxYgWz6f35YsT9HU/Mh66H+7dUsE4f3D33s093IdHM14yjbF1H73n6PM4DvBKeWbbxaBgjWQ2oc8g5gekAxUCh9AeIhDTd9WcmW60omhJ9spUR+GZi2IZwypER4sM+kpxyOC5VKBPu6ifpI4q85Pm1cH5PIluSi4kiTQJ4NPGbDkliUkRKUsK+ecBLXIms/061U9JJNIfd5ua7Py1HWchxWL/hyIIMbQPgCD9dKMmyrG7bCnFAwyPqSo2OSD62tyH6JpfnkwWw+IO8Fpi0I50eHSrYRlD7x4uvjWc29NIm7KevA6iT2IDdFf2E5jo8WzzAQkoHfBUQ2rr8LhhtkdKBxPRpkk3QgL+hp8VCPZkMUWZe4V4HIJ+/dyNOij6Js5/WLkWxSIS8G/eldKsLB0gYoO0j1lBACjrLKfRYLylpelwEFLfLL2ojcVHYSSWDZxMLwArAPpKSx/A5YCMb7ucE4ulo8Hge7QH9aQ4WX2YI9UfJ4xqgmmL6FyaGCfRTcnifYX6jTt7Ct4EewDxwGmqhngkmgBejePwWECQw3tSEsxc86VeBLc0Lf0/wVRdXlEUY1zNLemUfZd3zEgqTx7n2gkNLPNCwF2Z+qkUtbnUpJD+PqkiwZPUv5EvAC2AFKoT9Qfg/cBMIwivZO8FEoxpKHVn9DOJYNejznj2VKSCuQ/gFgDtD/ojQD/9W3A1mfb/YAffjTPwVs4fkD8EDRZpCLHUr/AR0kMTfly+kOAAAAAElFTkSuQmCC"
                             v-on:click="rotateAC"/>
                        <img class="picture-rotate_c button" src="data:image/png;base64,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"
                             v-on:click="rotateC"/>
                        <img class="picture-adapt button" src="data:image/png;base64,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"
                             v-on:click="adapt"/>
                    </div>
                </div>
            </div>

            <div class="bottom">
                <swiper class="thumbs-swiper" :options="thumbsSwiperOption">
                    <swiper-slide :key="index" v-for="(picture,index) in pictures">
                        <div v-on:click="to(index);">
                            <div class="thumbs-swiper-picture" :class="(index==currentIndex?'active':'')+' '+(picture.status==1?'tg':(picture.status==-1?'btg':''))"
                                 :style="'background-image: url('+picture.small+');'"></div>
                        </div>
                    </swiper-slide>
                </swiper>
                <img class="thumbs-swiper-prev button" src="./assets/btn-prev2.png"
                     v-on:click="thumbsSwiper.slidePrev()"/>
                <img class="thumbs-swiper-next button" src="./assets/btn-next2.png"
                     v-on:click="thumbsSwiper.slideNext()"/>
            </div>
        </div>
    </div>
</template>

<script>
    import Cropper from 'cropperjs';
    import "@/../node_modules/cropperjs/src/index.css"
    // import Swiper from 'swiper'
    // import 'swiper/dist/css/swiper.min.css'
    export default {
        name: "PicBrowser",
        data() {
            return {
                cropper:null,
                currentIndex: -1,
                pictures: [],
                callback: {},
                gallerySwiperOption: {
                    direction: 'horizontal',
                    loop: false,
                    lazy: true,
                    navigation: {
                        prevEl: '.gallery-swiper-prev',
                        nextEl: '.gallery-swiper-next'
                    }
                },
                thumbsSwiperOption: {
                    direction: 'horizontal',
                    loop: false,
                    slidesPerView: 'auto'
                }
            }
        },
        methods: {
            initPicBrowser(data) {
                if(data.callback){
                    this.callback.onPicChange=data.callback.onPicChange;
                    this.callback.onCrossEnd=data.callback.onCrossEnd;
                    this.callback.onCrossStart=data.callback.onCrossStart;
                }
                if(data.pictures.length==0){
                    this.currentIndex = -1;
                }else{
                    this.currentIndex = 0;
                }
                this.pictures = data.pictures;
                for(var i in this.pictures){
                    if(!this.pictures[i].rotate){
                        this.pictures[i].rotate=0;
                    }
                }

                var pic="";
                if(data.pictures.length>0){
                    pic=data.pictures[0].big;
                }else{
                    pic="/qishui/base/static/res/img/blank.png"
                }
                var image = this.$el.getElementsByClassName("picture-cropper")[0];
                image.src=pic;
                var self=this;
                this.cropper = new Cropper(image, {
                    dragMode:"move",
                    autoCrop: false,
                    ready(event) {
                        if(self.currentIndex>0){
                            this.cropper.rotateTo(self.pictures[self.currentIndex].rotate);
                        }
                    }
                });

                if(typeof this.callback.onPicChange=="function"){
                    if(this.currentIndex>=0){
                        this.callback.onPicChange(this.currentIndex,this.pictures[this.currentIndex]);
                    }else{
                        this.callback.onPicChange(this.currentIndex,null);
                    }
                }
            },
            updatePictures(pictures){
                if(pictures.length==0){
                    this.currentIndex = -1;
                }else{
                    this.currentIndex = 0;
                }
                this.pictures = pictures;
                for(var i in this.pictures){
                    if(!this.pictures[i].rotate){
                        this.pictures[i].rotate=0;
                    }
                }

                var pic="";
                if(pictures.length>0){
                    pic=pictures[0].big;
                }else{
                    pic="/qishui/base/static/res/img/blank.png"
                }
                this.cropper.replace(pic);

                if(typeof this.callback.onPicChange=="function"){
                    if(this.currentIndex>=0){
                        this.callback.onPicChange(this.currentIndex,this.pictures[this.currentIndex]);
                    }else{
                        this.callback.onPicChange(this.currentIndex,null);
                    }
                }
            },
            getCurrentIndex() {
                return this.currentIndex;
            },
            getCurrentPicture() {
                if(this.currentIndex==-1){
                    return null;
                }
                return this.pictures[this.currentIndex];
            },
            next() {
                if (this.currentIndex + 1 < this.pictures.length) {
                    this.currentIndex++;
                    // this.thumbsSwiper.slideTo(this.currentIndex-2);
                    var pic=this.pictures[this.currentIndex];
                    this.cropper.replace(pic.big);
                    if(typeof this.callback.onPicChange=="function"){
                        this.callback.onPicChange(this.currentIndex,pic);
                    }
                }else if(this.currentIndex + 1 == this.pictures.length){
                    if(typeof this.callback.onCrossEnd=="function"){
                        this.callback.onCrossEnd();
                    }
                }
            },
            prev() {
                if (this.currentIndex - 1 >= 0) {
                    this.currentIndex--;
                    // this.thumbsSwiper.slideTo(this.currentIndex-2);
                    var pic=this.pictures[this.currentIndex];
                    this.cropper.replace(pic.big);
                    if(typeof this.callback.onPicChange=="function"){
                        this.callback.onPicChange(this.currentIndex,pic);
                    }
                }else if(this.currentIndex - 1 == -1){
                    if(typeof this.callback.onCrossStart=="function"){
                        this.callback.onCrossStart();
                    }
                }
            },
            to(index){
                if (index >= 0 && index < this.pictures.length){
                    this.currentIndex=index;
                    // this.thumbsSwiper.slideTo(this.currentIndex-2);
                    var pic=this.pictures[this.currentIndex];
                    this.cropper.replace(pic.big);
                    if(typeof this.callback.onPicChange=="function"){
                        this.callback.onPicChange(this.currentIndex,pic);
                    }
                }
            },
            rotateC(){
                if(this.currentIndex>=0){
                    var pic=this.pictures[this.currentIndex];
                    pic.rotate=(pic.rotate+360+90)%360;
                    this.cropper.rotateTo(pic.rotate);
                }
            },
            rotateAC(){
                if(this.currentIndex>=0){
                    var pic=this.pictures[this.currentIndex];
                    pic.rotate=(pic.rotate+360-90)%360;
                    this.cropper.rotateTo(pic.rotate);
                }
            },
            adapt(){
                if(this.currentIndex>=0){
                    this.cropper.replace("");
                    this.cropper.replace(this.pictures[this.currentIndex].big);
                }
            },
        },
        mounted() {
            const self = this;
            this.thumbsSwiperOption.on = {
                init() {
                    self.thumbsSwiper = this;
                }
            }
        }
    }
</script>

<style scoped>
    .button {
        -moz-user-select: none;
        -o-user-select: none;
        -khtml-user-select: none;
        -webkit-user-select: none;
        -ms-user-select: none;
        user-select: none;
        cursor: pointer;
    }

    .container {
        position: relative;
        width: 100%;
        height: 600px;
        overflow: hidden;
    }

    /*--------------------------------*/

    .center {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        position: absolute;
        left: 0px;
        top: 0px;
        width: 100%;
        height: 100%;
        padding-bottom: 59.5px;
    }

    .status-mark{
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        position: absolute;
        left: 0px;
        top: 0px;
        z-index:1;
        width: 100%;
        height: 100%;
        padding-bottom: 59.5px;
        overflow: hidden;
    }

    .status-mark > img{
        display: block;
        width: 30%;
        height: auto;
        margin-left: auto;
        margin-right: auto;
        margin-top: 10%;
    }

    .picture {
        position: relative;
        width: 100%;
        height: 100%;
        background-color: #f7f7f7;
    }

    .picture-cropper {
        display: block;
        max-width: 100%;
        max-height: 100%;
        margin: 0px auto;
    }

    .picture-prev {
        position: absolute;
        left: 14px;
        top: 50%;
        z-index: 1;
        width: 19.6px;
        height: 32.2px;
        margin: -16.1px 0px;
    }

    .picture-next {
        position: absolute;
        right: 14px;
        top: 50%;
        z-index: 1;
        width: 19.6px;
        height: 32.2px;
        margin: -16.1px 0px;
    }

    .picture-toggle {
        position: absolute;
        left: 50%;
        bottom: 21px;
        z-index: 1;
        width: 140px;
        height: 28px;
        margin:0px -70px;
        border-radius: 3.5px;
        background-color: rgba(0,0,0,0.5);
        text-align: center;
    }

    .picture-rotate_ac{
        width: 18.9px;
        height: 17.5px;
        margin-top: 4.9px;
    }

    .picture-rotate_c{
        width: 18.9px;
        height: 17.5px;
        margin-top: 4.9px;
        margin-left: 14px;
    }

    .picture-adapt{
        width: 17.5px;
        height: 17.5px;
        margin-top: 4.9px;
        margin-left: 35px;
    }

    >>> .cropper-bg{
        background-image: none;
        background-color: #f7f7f7;
    }

    /*--------------------------------*/

    .bottom {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        position: absolute;
        left: 0px;
        bottom: 0px;
        z-index:2;
        width: 100%;
        height: 59.5px;
        padding: 0px 35px;
        background-color: white;
    }

    .thumbs-swiper {
        display: inline-block;
        width: 100%;
        height: 100%;
    }

    .thumbs-swiper-prev {
        position: absolute;
        left: 14px;
        top: 50%;
        z-index: 10;
        width: 9.8px;
        height: 16.1px;
        margin: -8.05px 0px;
    }

    .thumbs-swiper-next {
        position: absolute;
        right: 14px;
        top: 50%;
        z-index: 10;
        width: 9.8px;
        height: 16.1px;
        margin: -8.05px 0px;
    }

    .thumbs-swiper >>> .swiper-slide {
        position: relative;
        width: 59.5px;
        height: 100%;
        margin: 0px auto;
    }

    .thumbs-swiper-picture {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        position: absolute;
        left: 50%;
        top: 50%;
        width: 45.5px;
        height: 45.5px;
        margin: -22.75px -22.75px;
        border: 1px solid darkgray;
        background-position: center center;
        background-size: contain;
        background-repeat: no-repeat;
        cursor: pointer;
    }

    .thumbs-swiper-picture.active {
        box-shadow: 0px 0px 8px black;
    }

    .thumbs-swiper-picture.tg.active {
        box-shadow: 0px 0px 8px limegreen;
    }

    .thumbs-swiper-picture.btg.active {
        box-shadow: 0px 0px 8px red;
    }

    .thumbs-swiper-picture.tg {
        border-color: limegreen;
    }

    .thumbs-swiper-picture.btg {
        border-color: red;
    }
</style>