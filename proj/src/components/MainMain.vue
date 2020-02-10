<template>
    <div>
        <MainTable v-if="kk== 1" @changePage="changePage" @setIndex="setIndex"></MainTable>
        <LieBiao v-if="kk == 2" @changePage="changePage"></LieBiao>
        <MainMainCheck v-if="kk == 3" @changePage="changePage" :index="index"></MainMainCheck>
        <ShangChuan v-if="kk == 4" :applyId="applyId" :kk="kk" @changePage="changePage"></ShangChuan>
        <ShuiDanYouJi v-if="kk == 5"></ShuiDanYouJi>
    </div>
</template>

<script>
    import Msg from './msg.vue';
    import MainMainCheck from './MainMainCheck.vue';
    import MainTable from './MainTable.vue';
    import LieBiao from './ShuiDanShangChuan/components/LieBiao/LieBiao.vue';
    import ShangChuan from './ShuiDanShangChuan/components/ShangChuan/ShangChuan.vue'
    import ShuiDanYouJi from './ShuiDanYouJi/ShuiDanYouJi.vue'

    export default {
        components: {MainMainCheck,MainTable,LieBiao,ShangChuan,ShuiDanYouJi},
        name: "MainMain",
        mounted:function () {
            let _this=this;
            Msg.$on('val',function (m) {
                _this.kk=m;
            })
        },data(){
            return{
                kk:0,
                applyId:'',
                index:''
            }
        },
        methods:{
            changePage(applyId,kk){
                this.applyId = applyId;
                this.kk = kk;
            },
            setIndex(index){
                debugger;
                this.index = index;
            }
        }
    }
</script>

<style scoped>

</style>