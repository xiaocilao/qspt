<template>
     <tabbar v-model="selectedBar"  class="tabbarGroup" @on-index-change="handerSelect">
      <!--<tabbar-item class="labelText">-->
        <!--<img alt="" slot="icon" src="@/assets/static/images/news.png">-->
        <!--<span slot="label">房产资讯</span>-->
        <!--<img alt="" slot="icon-active" src="@/assets/static/images/news1.png">-->
      <!--</tabbar-item>-->
      <tabbar-item class="labelText">
        <img alt="" slot="icon" src="@/assets/static/images/bh.png">
        <span slot="label">业务办理</span>
        <img alt="" slot="icon-active" src="@/assets/static/images/bh1.png">
      </tabbar-item>
      <tabbar-item>
        <img alt="" slot="icon" src="@/assets/static/images/my.png">
        <span slot="label" class="labelText">个人中心</span>
        <img alt="" slot="icon-active" src="@/assets/static/images/my1.png">
      </tabbar-item>
    </tabbar>
</template>
<script>
import { Tabbar, TabbarItem, Group, Cell } from 'vux'

export default {
  components: {
    Tabbar,
    TabbarItem,
    Group,
    Cell
  },
  data(){
      return{
          selectedBar:0
      }
  },
  methods:{
      handerSelect(index){
          this.selectedBar=index
          this.$emit('handerSelect', this.selectedBar);
      }
  }
}
</script>
<style scoped >
.tabbarGroup{
 background-color: #FFFFFF;
}
>>> a:-webkit-any-link{
  text-decoration: none;
}
</style>
