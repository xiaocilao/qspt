<template>
    <swiper :options="swiperOption" class="swiper-container swiper-pagination1"  ref="mySwiper">
        <!-- 添加的图片 -->
        <swiper-slide  v-for="(item, index) in swiperSourse" :key="index">
            <img alt="" :src="item.img" style="width: 100%;height: 100%"></swiper-slide>
        <div class="swiper-pagination"  slot="pagination"></div>
        <!--<div class="swiper-button-prev" slot="button-prev"></div>-->
        <!--<div class="swiper-button-next" slot="button-next"></div>-->
    </swiper>
</template>
<script>
    require('swiper/dist/css/swiper.css');
    import { swiper, swiperSlide } from 'vue-awesome-swiper'
    import 'swiper/dist/css/swiper.css'
    export default {
        props:['images'],
        components: {
            swiper,
            swiperSlide
        },
        data() {
            return {
                swiperSourse:this.images,
                swiperOption: {
                    pagination: '.swiper-pagination1',
                    slidesPerView: 1,
                    spaceBetween: 30,
                    centeredSlides: false,
                    spaceBetween: 0,
                    onSlideChangeEnd: swiper => {
                        //这个位置放swiper的回调方法
                        this.page = swiper.realIndex+1;
                        this.index = swiper.realIndex;
                    },
                    navigation: {
                        nextEl: '.swiper-button-next',
                        prevEl: '.swiper-button-prev',
                    },
                    //自动播放
                    autoplay:{
                        delay:2000,
                        disableOnInteraction:false
                    },
                    pagination: {
                        el: '.swiper-pagination',
                        clickable: true,
                    },
                    //循环
                    loop:true
                }
            }
        },
        //定义swiper对象
        computed: {
            swiper() {
                return this.$refs.mySwiper.swiper;
            }
        },
        mounted () {
            this.swiper.slideTo(0, 0, false);
        }

    }

</script>
<style scoped>

</style>
