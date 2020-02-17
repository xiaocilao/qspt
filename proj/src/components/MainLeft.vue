<template>
        <el-menu default-active="1-1"
                 class="el-menu-vertical-demo"
                 background-color="rgb(54,139,246)"
                 text-color="#fff"
                 active-text-color="#ffd04b">
            <el-submenu index="1" collapse="true">
                <template slot="title"><i class="el-icon-message"></i>业务专区</template>
                <el-menu-item-group >
                    <el-menu-item index="1-1"  @click.native="checkPaper"><i class="el-icon-edit"></i>资料审核</el-menu-item>
                    <el-menu-item index="1-2"  @click.native="upPaper"><i class="el-icon-position"></i>税单上传</el-menu-item>
                    <el-menu-item index="1-3"  @click.native="sendPaper"><i class="el-icon-s-finance"></i>税单邮寄</el-menu-item>
                </el-menu-item-group>
            </el-submenu>
            <el-submenu index="2">
                <template slot="title"><i class="el-icon-menu"></i>报表专区</template>
                <el-menu-item-group>
                    <el-menu-item index="2-1" @click.native="checkDetail"><i class="el-icon-time"></i>审核记录</el-menu-item>
                </el-menu-item-group>
            </el-submenu>
        </el-menu>
</template>

<script>
    import Msg from './msg.vue'
    export default {
        methods:{
            checkPaper:function () {
                Msg.$emit("val","1");
            },
            upPaper:function () {
                Msg.$emit("val","2");
            },
            sendPaper:function () {
                Msg.$emit("val","5"); //邮寄
            },
            checkDetail:function () {
                Msg.$emit("val","6");  //报表
            }
        }
    }
</script>

<style scoped>
    .el-menu {
        border-right:  0px solid #e6e6e6;
    }
</style>