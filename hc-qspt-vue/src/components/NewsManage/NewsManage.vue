<template>
    <div>
        <SwipperImage :images="swiperSourse" />
        <div class="newsContainer">
          <div v-for="item in news" class="news" @click="gotoNews(item.id)">
            <div class="newsImg">
              <img alt="" :src="item.newsimg" style="width: 100%;height: auto">
            </div>
            <div class="newsContent">
              <div class="newsTitle">{{item.newstitle}}</div>
              <div class="read">阅读量：{{item.newsknown}}</div>
              <div class="time">{{item.newstime}}</div>
            </div>
          </div>
        </div>
    </div>
</template>
<style scoped>
  .newsContainer{
    width: 100%;
    margin-top: 1em;
  }
  .news{
    width: 100%;
    height: 6em;
  }
  .newsImg{
    float: left;
    width: 8em;
    height: 5em;
    margin-top: 0.5em;
    margin-left: 0.5em;
    background-color: #8b8b8b;
  }
  .newsContent{
    float: left;
    height: 5em;
    margin-top: 0.5em;
    margin-left: 0.5em;
    width: 12em;
  }
  .newsTitle{
    font-size: 1em;
    letter-spacing: 0.1em;
  }
  .time{
    font-size: 0.5em;
    float: right;
    margin-top: 1.8em;
  }
  .read{
    font-size: 0.5em;
    float: left;
    margin-top: 1.8em;
  }
</style>
<script>
import SwipperImage from "@/components/CommComponents/SwipperImage"
import Common from "@/lib/common"
export default {
    components:{
        SwipperImage
    },
    data(){
        return{
            swiperSourse:[
                {
                    url: 'javascript:',
                    img: require("@/assets/static/images/a1.jpg"),
                },
                {
                    url: 'javascript:',
                    img: require('@/assets/static/images/a2.jpg'),
                },
                {
                    url: 'javascript:',
                    img: require('@/assets/static/images/a3.jpg'),
                }
            ],
            news:[]
        }
    },
    methods:{
      gotoNews(id){
        this.$router.push({
          path:'/NewsDetil',
          query:{
            id:id
          }
        });
      }
    },
    mounted() {
      Common.queryAllNews(this,0);
    }
}
</script>
