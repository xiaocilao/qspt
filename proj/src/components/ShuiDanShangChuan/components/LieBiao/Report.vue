<template>
  <div>
    <data-table ref="dataTable"></data-table>

    <el-button size="small" type="primary" @click="handleDownLoad">下载</el-button>
  </div>
</template>

<script>
  import DataTable from "../../../../components/DataTable/DataTable.vue";
  export default {
    name: "SDSCLieBiao",
    components: {
      DataTable
    },
    props:{
      applyId:{
        type:String,
        default:null
      },
      kk:{
        type:String,
        default:null
      },
    },
    methods:{
      loading(that,data){
        let arr = [data];
        let str = arr.join('</br>');
        that.$message({
          dangerouslyUseHTMLString:true,
          message:str,
          type:'warning'
        })
      },
      open(row) {
          debugger;

          var storage = window.localStorage;
          let postData = new URLSearchParams();
          postData.append('clientKey', storage.getItem("ClientKey"));
          postData.append('id',row.id);
          window.location.href = '/qishui/base/api/apply/dowload?clientKey='+storage.getItem("ClientKey")+'&id='+row.id

        // this.$http.post('/qishui/base/api/apply/dowload'
        //         ,{area:this.area,id:row.id, name:row.name},{responseType: 'blob'})
        //         .then(response =>{
        //           let data = response.data;
        //           if (!data) {
        //             return
        //           }
        //           console.log(response);
        //           let contentDisposition = response.headers['content-disposition'];
        //           let fileName = contentDisposition.substring(contentDisposition.indexOf('=')+1);
        //           console.log('fileName=' + fileName);
        //           let url = window.URL.createObjectURL(new Blob([data]))
        //           let a = document.createElement('a')
        //           a.style.display = 'none'
        //           a.href = url
        //           a.setAttribute('download',fileName)
        //           document.body.appendChild(a)
        //           //点击下载
        //           a.click()
        //           // 下载完成移除元素
        //           document.body.removeChild(a);
        //           // 释放掉blob对象
        //           window.URL.revokeObjectURL(url);
        //           console.log("下载完成");
        //         }).catch(function (error) {
        //   console.log(error);
        // });
        // this.$confirm('下载', '提示', {
        //   confirmButtonText: '确定',
        //   cancelButtonText: '取消',
        //   type: 'warning'
        // }).then(() => {
        //
        //               debugger;
        //               let postData = new URLSearchParams();
        //               var storage = window.localStorage;
        //               postData.append('clientKey', storage.getItem("ClientKey"));
        //               postData.append('userId', this.userId);
        //               postData.append('id', data.id);
        //               this.$http.post('/qishui/base/api/apply/dowload', postData).then(function (data) {
        //                 debugger;
        //                 data = response.data;
        //                 // 创建a标签并点击， 即触发下载
        //                 let url = window.URL.createObjectURL(new Blob([data]));
        //                 let link = document.createElement("a");
        //                 link.style.display = "none";
        //                 link.href = url;
        //                 link.setAttribute("download", self.plist_file_name);
        //
        //                 document.body.appendChild(link);
        //                 link.click()
        //                 if (data.data.rows[0].code === '200') {
        //                   debugger;
        //                 } else {
        //                   self.loaddata(self, "出错！");
        //                   this.$emit('changePage', '', 5);
        //                 }
        //               });
        //             }).catch((e) => {
        //               debugger;
        //               this.$message({
        //                 type: 'info',
        //                 message: '已取消下载'
        //               });
        //             });
                  },
      handleDownLoad() {
        var storage = window.localStorage;
          let postData = new URLSearchParams();
          postData.append('clientKey', storage.getItem("ClientKey"));
        window.location.href = '/qishui/base/api/apply/dowload?clientKey='+storage.getItem("ClientKey")
      },

    },
    mounted() {
      var self = this;
      var tableData={
        pageSize:10,
        requestUrl:"/qishui/base/api/apply/report",
        key:"id",
        filters:[
          {title:"审核通过",value:"2"},
          {title:"审核不通过",value:"-1"},
        ],
        columns: [
          {title: "申请编号", name: "num"},
          {title: "申请人姓名", name: "xtZhxgrxm"},
          {title: "申请时间", name: "sqsj"},
          {title: "审批人", name: "sprXm"},
          {title: "审批时间", name: "spsj"},
          {title: "审批状态", name: "spzt"},
          {title: "联系方式", name: "xtPhone"}
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
            // if(row._spzt!=2){
            //   self.loading(self,"待缴费")
            //   return;
            // }
            // self.$emit('changePage',row.id,4);
            debugger;
            self.open(row);
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
</style>
