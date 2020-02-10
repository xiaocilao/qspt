<template>
    <div class="container">
      <div class="title">{{title}}</div>
      <div class="time">
        {{time}}
      </div>
      <div class="content" v-html="content">
        {{content}}
      </div>
    </div>
</template>

<script>
  import Common from "@/lib/common"
    export default {
        name: "NewsDetil",
      data() {
        return {
          id:"",
          title:"这是个测试title",
          time:"2019-07-09",
          content:"&nbsp;&nbsp;&nbsp;&nbsp;测测恻恻恻恻恻恻恻测测测试啊啊啊啊啊啊啊啊啊啊<br/>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;乱测试一下啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊<br/>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;此刻我很烦文化氛围范围"
        }
      },
      mounted() {
        Common.queryNewsById(this,this.$route.query.id);
      }
    }
</script>

<style scoped>
  .title{
    width: 90%;
    margin: auto;
    font-size: 1.5em;
  }
  .time{
    width: 90%;
    margin: auto;
    font-size: 0.5em;
    margin-top: 1em;
    margin-bottom: 3em;
    color: #8b8b8b;
  }
  .content{
    width: 90%;
    margin: auto;
    font-size: 1em;
    line-height: 2em;
  }
  .content img{
    width: 100%;
    height: auto;
  }
</style>
