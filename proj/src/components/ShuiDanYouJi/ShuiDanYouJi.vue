<template>
  <div>
    <data-table ref="dataTable"></data-table>

    <el-drawer
            size="50%"
            title="邮寄信息"
            :before-close="handleClose"
            :visible.sync="dialog"
            direction="rtl"
            custom-class="demo-drawer"
            ref="drawer"
    >
      <div style="margin: 20px;"></div>
      <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign">
        <el-form-item label="姓名">
          <el-input v-model="formLabelAlign.name" readonly="readonly" ></el-input>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="formLabelAlign.phone" readonly="readonly" ></el-input>
        </el-form-item>
        <el-form-item label="邮编">
          <el-input v-model="formLabelAlign.postNumber" readonly="readonly" ></el-input>
        </el-form-item>
        <el-form-item label="详细地址">
          <el-input v-model="formLabelAlign.address" readonly="readonly" ></el-input>
        </el-form-item>
      </el-form>

      <br/>
      <br/>
      <div class="demo-drawer__content">
        <el-form :model="form">
          <el-form-item   label="邮寄(快递单号)" required>
            <el-input placeholder="请输入快递单号"  suffix-icon="el-icon-edit" v-model="form.postNumber" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item  label="快递公司" required>
            <el-input  placeholder="请输入快递公司名称" suffix-icon="el-icon-s-goods" v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div class="demo-drawer__footer">
          <el-button style="width: 130px" type="primary" @click="yjtj()" :loading="loading">{{ loading ? '提交中 ...' : '确 定' }}</el-button>
          <el-button style="width: 130px" @click="cancelForm">取 消</el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
  import DataTable from "../../components/DataTable/DataTable.vue";
  export default {
    name: "SuiDanShangChuan",
    components: {
      DataTable,
    },
    data() {
      return {
        table: false,
        dialog: false,
        userId:'',
        loading: false,
        labelPosition: 'right',
        formLabelAlign: {
          name: '',
          phone: '',
          address: '',
          postNumber:''
        },
        form: {
          name: '',
          postNumber:''
        },
      };
    },
    methods:{
      loaddata(that,data){
        let arr = [data];
        let str = arr.join('</br>');
        that.$message({
          dangerouslyUseHTMLString:true,
          message:str,
          type:'warning'
        })
      },
      yjtj(){
        var self = this;
        if('name',self.form.name==='' || self.form.postNumber===''){
          self.loaddata(self,"请填写快递单号和快递公司名称!");
          return;
        }
        let postData= new URLSearchParams();
        var storage=window.localStorage;
        postData.append('clientKey',storage.getItem("ClientKey"));
        postData.append('userId',self.userId);
        postData.append('name',self.form.name);
        postData.append('postNumber',self.form.postNumber);
        self.$http.post('/qishui/base/api/apply/yjsc',postData).then(function (data) {
          if (data.data.rows[0].code === '200') {
            self.$refs.dataTable.loadData();
            self.loaddata(self,"邮寄成功");
            self.cancelForm();
          }else{
            self.loaddata(self,"出错！");
            this.$emit('changePage','',5);
          }
        });
      },

      handleClose(done) {
        if (this.loading) {
          return;
        }
        this.$confirm('确定要提交表单吗？')
                .then(_ => {
                  this.loading = true;
                  this.timer = setTimeout(() => {
                    done();
                    // 动画关闭需要一定的时间
                    setTimeout(() => {
                      this.loading = false;
                    }, 400);
                  }, 2000);
                })
                .catch(_ => {});
      },
      cancelForm() {
        this.loading = false;
        this.dialog = false;
        clearTimeout(this.timer);
      }

    },
    mounted() {
      var self = this;
      var tableData={
        pageSize:10,
        requestUrl:"/qishui/base/api/apply/sdyjlb",
        key:"id",
        filters:[
          {title:"待邮寄",value:6},
          {title:"已邮寄",value:7},
          {title:"已签收",value:8},
          {title:"自取",value:9}
        ],
        columns: [
          {title: "申请编号", name: "num"},
          {title: "申请人姓名", name: "xtZhxgrxm"},
          {title: "申请时间", name: "sqsj"},
          {title: "审批人", name: "sprXm"},
          {title: "审批时间", name: "spsj"},
          {title: "审批状态", name: "spzt"},
        ],
        callback: {
          onPageChange(index) {
            console.log(index);
          },
          onSearch(val) {
            console.log(val);
          },
          onFilterChange(index,filter){
            console.log(index,filter);
          },
          onRowClick(index,row,columns) {
            if(row._spzt!=6){
              return;
            }
            let postData= new URLSearchParams();
            var storage=window.localStorage;
            postData.append('clientKey',storage.getItem("ClientKey"));
            postData.append('userId',row.sqrId);
            self.userId = row.sqrId;
            self.$http.post('/qishui/base/api/apply/yjxx',postData).then(function (data) {
              if (data.data.rows[0].code === '200') {
                debugger;
                self.formLabelAlign.name = data.data.rows[0].data.sjr;
                self.formLabelAlign.phone = data.data.rows[0].data.sjhm;
                self.formLabelAlign.postNumber = data.data.rows[0].data.postalCode;
                self.formLabelAlign.address = data.data.rows[0].data.lqdz;

                self.form.name = data.data.rows[0].data.postName;
                self.form.postNumber = data.data.rows[0].data.postNumber;
              }
            });
              self.dialog = true;
          }
        }
      }
      self.$refs.dataTable.initDataTable(tableData);
    }
  }
</script>

<style scoped>
  >>> td{
    cursor: pointer;
  }
  .demo-drawer__footer{
    /*position: fixed;*/
    bottom: 0px;
  }

</style>
