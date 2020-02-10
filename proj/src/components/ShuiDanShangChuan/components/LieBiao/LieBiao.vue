<template>
  <div>
    <data-table ref="dataTable"></data-table>
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
    },
    mounted() {
      var self = this;
      var tableData={
        pageSize:10,
        requestUrl:"/qishui/base/api/apply/sdsclb",
        key:"id",
        filters:[
          {title:"未上传",value:"2"},
          {title:"已上传",value:"3"}
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
            if(row._spzt!=2){
              self.loading(self,"待缴费")
              return;
            }
            self.$emit('changePage',row.id,4);
            // self.$router.replace({ name: 'ShangChuan', params: { applyId:row.id }});
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
