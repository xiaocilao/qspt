<template>
    <div>
        <img src="../assets/layout_set_logo.png" style="float: left;height: 60px" >
        <el-dropdown>
        <i class="el-icon-setting" style="margin-right: 15px"></i>
        <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
            <el-dropdown-item>关于</el-dropdown-item>
        </el-dropdown-menu>
    </el-dropdown>
    <span>{{user}}</span>
    </div>
</template>

<script>
    export default {
        name: "MainTop",
        data(){
            return {
                user:''
            }
        },
        methods:{
            logout(){
                var storage=window.localStorage;
                storage.removeItem("ClientKey");
                this.$router.push("/")
            }
        },
        mounted(){
            var storage=window.localStorage;
            this.user = storage.getItem("user");
        }

    }
</script>

<style scoped>

</style>