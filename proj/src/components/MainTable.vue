<template>
    <el-table
            :data="tableData"
            border
            size="small"
            style="width: 100%"
            >
        <el-table-column
                align="center"
                prop="index"
                label="序号"
                height="10"
                width="60">
        </el-table-column>
        <el-table-column
                align="center"
                prop="sqsj"
                label="申请日期"
                width="170">
        </el-table-column>
        <el-table-column
                prop="name"
                align="center"
                label="申请人姓名"
                width="100">
        </el-table-column>
        <el-table-column
                align="center"
                prop="xt_zhxgsj"
                label="最后修改时间"
                width="170">
        </el-table-column>
        <el-table-column
                prop=""
                align="center"
                label="审批人"
                width="100">
        </el-table-column>
        <el-table-column
                align="center"
                prop="xt_zxyy"
                label="备注"
                min-width="200">
        </el-table-column>
        <el-table-column
                align="center"
                prop="spzt"
                label="状态"
                width="100">
        </el-table-column>
        <el-table-column
                align="center"
                label="操作"
                min-width="100"
                >
            <template slot-scope="scope">
                <el-button @click="handleClick(scope.row)" type="text" size="small">审核</el-button>
                <!--<el-button type="text" size="small">编辑</el-button>-->
            </template>
        </el-table-column>
    </el-table>
</template>

<script>
    export default {
        name: "MainTable",
        data() {
            return {
                kk:0,
                active:0,
                tableData: []
            }
        },methods:{
            loading(that,data){
                let arr = [data];
                let str = arr.join('</br>');
                that.$message({
                    dangerouslyUseHTMLString:true,
                    message:str,
                    type:'warning'
                })
            },
            handleClick(row) {
                if(row.spzt==="审核不通过"){
                    this.loading(this,'审核不通过,待用户重新提交！');
                }else if(row.spzt==="审核通过") {
                    this.loading(this,'审核通过,不可操作！');
                }else{
                    this.$emit('setIndex',row.index)
                    this.$emit('changePage','',3)
                }
            },
            formatter(row, column) {
                return row.address;
            }
        },
        mounted() {
            var self=this;
            let postData= new URLSearchParams();
            var storage=window.localStorage;
            postData.append('clientKey',storage.getItem("ClientKey"));
            self.$http.post('/qishui/base/api/apply/shenhe',postData).then(function (data) {
                if(data.data.rows[0].code==='200'){
                    data.data.rows[0].list.forEach((item,index) => {
                    var status = '';
                    if(item.spzt==='1'){
                        status = '待审核';
                    }else if(item.spzt==='-1'){
                        status = '审核不通过';
                    }else if(item.spzt==='2'){
                        status = '审核通过';
                    }
                    var obj = {
                        index : item.num,
                        name  : item.xtZhxgrxm,
                        sqsj  : item.sqsj,
                        sqr_id: item.sqrId,
                        xt_zxyy:item.xtZxyy,
                        spzt  : status,
                        sqr_xm:item.sqrXm,
                        xt_zhxgsj: item.xtZhxgsj
                    }
                        self.tableData.push(obj)
                    })
                }else if(data.data.rows[0].code==='201'){
                    self.loading(self,"无审核数据");
                }

            });
        }
    }
</script>

<style scoped>

</style>