<template>
        <el-menu :default-openeds="['1', '3']">
            <el-submenu index="1">
                <template slot="title"><i class="el-icon-message"></i>业务专区</template>
                <el-menu-item-group>
                    <el-menu-item index="1-1"  @click.native="checkPaper">资料审核</el-menu-item>
                    <el-menu-item index="1-2"  @click.native="upPaper">税单上传</el-menu-item>
                    <el-menu-item index="1-3"  @click.native="sendPaper">税单邮寄</el-menu-item>
                </el-menu-item-group>
            </el-submenu>
            <el-submenu index="2">
                <template slot="title"><i class="el-icon-menu"></i>报表专区</template>
                <el-menu-item-group>
                    <el-menu-item index="2-1" @click.native="checkDetail">审核记录</el-menu-item>
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
                Msg.$emit("val","4");
            }
        }
    }
</script>

<style scoped>

</style>