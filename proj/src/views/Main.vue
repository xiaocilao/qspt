<template>
    <div class="body">
        <div style="height: 100%;width:100%;position: fixed">
        <el-container style="height: 100%; border: 1px solid #eee">
            <el-aside width="14.9%" style="background-color:rgb(54,139,246)">
            <MainLeft></MainLeft>
            </el-aside>
            <el-container>
                <el-header style="text-align: right; font-size: 12px;">
                  <MainTop></MainTop>
                </el-header>
                  <MainMain></MainMain>
            </el-container>
        </el-container>
        </div>
    </div>
</template>

<script>
    import MainLeft from '../components/MainLeft.vue'
    import MainMain from '../components/MainMain.vue'
    import MainTop from '../components/MainTop.vue'
    import Comment from '../js/common.js'
    export default {
        name: "Main",
        components: {MainTop, MainMain, MainLeft},
        created() {
            var storage=window.localStorage;
            var ClientKey = storage.getItem("ClientKey");
            debugger;
            if(Comment.commData.ClientKey=='' && ClientKey ==null){
                this.loading("用户信息已过期，请重新登录！");
                this.$router.push("/")
            }
        },
        methods:{
            loading(data){
                let arr = [data];
                let str = arr.join('</br>');
                this.$message({
                    dangerouslyUseHTMLString:true,
                    message:str,
                    type:'warning'
                })
            },
        }
    }

</script>

<style scoped>
    .el-header {
        background-color: rgb(44,116,247);
        color: #333;
        line-height: 60px;
    }

    .el-aside {
        color: #333;
    }
</style>