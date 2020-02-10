<template>
    <div>
    <el-dropdown>
        <i class="el-icon-setting" style="margin-right: 15px"></i>
        <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
            <el-dropdown-item>关于</el-dropdown-item>
        </el-dropdown-menu>
    </el-dropdown>
    <span>测试用户</span>
    </div>
</template>

<script>
    export default {
        name: "MainTop",
        methods:{
            logout(){
                var storage=window.localStorage;
                storage.removeItem("ClientKey");
                this.$router.push("/")
            }
        }
    }
</script>

<style scoped>

</style>