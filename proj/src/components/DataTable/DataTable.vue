<template>
  <div>
    <search class="search" ref="search">
      <slot></slot>
    </search>
    <table cellspacing="0px">
      <table-head :columns="columns"></table-head>
      <table-row v-for="(row,index) in rows" :key="row[key]" :columns="columns" :row="row" @click.native="rowClick(index,row,columns)"></table-row>
      <tr>
        <td :colspan="columns.length">
          <fen-ye ref="fenye"></fen-ye>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
  import TableRow from "./components/TableRow/TableRow";
  import TableHead from "./components/TableHead/TableHead"
  import Search from "./components/Search/Search"
  import FenYe from "./components/FenYe/FenYe"

  export default {
    name: "DataTable",
    components: {
      TableRow,
      TableHead,
      Search,
      FenYe
    },
    data() {
      return {
        keywords:"",
        filter:"",
        pageIndex:0,
        pageSize:1,
        requestUrl:"",
        key: "",
        filters:[],
        columns: [],
        rows: [],
        callback:{}
      }
    },
    methods:{
      loadData(){
        var self=this;
        let postData= new URLSearchParams();
        var storage=window.localStorage;
        postData.append('clientKey',storage.getItem("ClientKey"));
        postData.append('pageOffset',self.pageIndex*self.pageSize);
        postData.append('pageRowSize',self.pageSize);
        postData.append('filter',self.filter);
        postData.append('keywords',self.keywords);
        self.$http.post(self.requestUrl,postData).then(function (data) {
          if(data.data.code==200){
            if(self.requestUrl ==='/qishui/base/api/apply/report'){
                if(data.data.rows[0].code ==='200'){
                  self.rows=data.data.rows[0].data;
                }else{
                  self.rows=[];
                  self.$refs.fenye.setPageCount(1);
                  this.loading(self,'操作失败');
                }
            }else{
              self.rows=data.data.rows;
            }
            for(var i in self.rows){
              self.rows[i]._sprXm=self.rows[i].sprXm;
              if(!self.rows[i].sprXm){
                self.rows[i].sprXm="待分配"
              }
              self.rows[i]._spzt=self.rows[i].spzt;
              switch (self.rows[i].spzt) {
                case '-3':self.rows[i].spzt="过期";break;
                case '-2':self.rows[i].spzt="扣款失败";break;
                case '-1':self.rows[i].spzt="审核失败";break;
                case '0':self.rows[i].spzt="新建";break;
                case '1':self.rows[i].spzt="未审核";break;
                case '2':self.rows[i].spzt="审核通过";break;
                case '3':self.rows[i].spzt="待缴费";break;
                case '4':self.rows[i].spzt="扣款请求";break;
                case '5':self.rows[i].spzt="扣款成功";break;
                case '6':self.rows[i].spzt="待邮寄";break;
                case '7':self.rows[i].spzt="已邮寄";break;
                case '8':self.rows[i].spzt="已签收";break;
                case '9':self.rows[i].spzt="自取";break;
              }
            }
            var total=data.total;
            var pageCount=Math.ceil(total/self.pageSize);
            self.$refs.fenye.setPageCount(pageCount);
          }else{
            self.rows=[];
            self.$refs.fenye.setPageCount(1);
            self.loading(self,'操作失败');
          }
        });
      },
      loading(that,data){
        let arr = [data];
        let str = arr.join('</br>');
        that.$message({
          dangerouslyUseHTMLString:true,
          message:str,
          type:'warning'
        })
      },
      initDataTable(data){
        var self=this;
        if(data.callback){
          debugger;
          self.callback.onRowClick=data.callback.onRowClick;
          self.callback.onPageChange=data.callback.onPageChange;
          self.callback.onSearch=data.callback.onSearch;
          self.callback.onFilterChange=data.callback.onFilterChange;
        }
        self.pageSize=data.pageSize;
        self.requestUrl=data.requestUrl;
        self.key=data.key;
        self.filters=data.filters;
        self.columns=data.columns;
        self.rows=data.rows

        self.$refs.fenye.initFenYe({
          pageCount: 10,
          callback: {
            onPageChange(index){
              self.pageIndex=index;
              self.loadData();
              if(typeof self.callback.onPageChange=="function"){
                self.callback.onPageChange(index);
              }
            }
          }
        });
        self.$refs.search.initSearch({
          filters:self.filters,
          callback:{
            onSearch(val){
              self.keywords=val;
              if(typeof self.callback.onSearch == "function"){
                debugger;
                self.callback.onSearch(val);
              }
            },
            onFilterChange(index,filter){
              if(index==-1){
                self.filter="";
              }else{
                self.filter=filter.value;
              }
              self.loadData();

              if(typeof self.callback.onFilterChange == "function"){
                self.callback.onFilterChange(index,filter);
              }
            }
          }
        });
      },
      rowClick(index,row,columns){
        if(typeof this.callback.onRowClick=="function"){
          this.callback.onRowClick(index,row,columns);
        }
      }
    }
  }
</script>

<style scoped>

  .search {
    width: 100%;
  }

  table {
    width: 100%;
    border-bottom: 0.7px solid #f5f5f5;
    margin-top: 14px;
    margin-bottom: 14px;
  }

  >>> tr {
    border: 0.7px solid #f5f5f5;
  }

  >>> td {
    height: 42px;
    text-align: center;
    font-size: 12px;
    border-top: 0.7px solid #f5f5f5;
    background-color: white;
  }

  >>> td:first-child {
    border-left: 0.7px solid #f5f5f5;
  }

  >>> td:last-child {
    border-right: 0.7px solid #f5f5f5;
    color: #6eb5ff;
  }

  >>> th {
    height: 42px;
    text-align: center;
    font-size: 12px;
    background-color: white;
  }

</style>



// WEBPACK FOOTER //
// src/components/DataTable/DataTable.vue