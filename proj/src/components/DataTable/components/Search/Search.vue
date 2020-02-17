<template>
  <div class="search-root">
    <span class="search-title">购房人资料</span>
    <div class="search-input">
      <!--<img src="./assets/search.png"/>-->
      <input :placeholder="placeholder" type="text" @keydown.enter="search" v-model="input"/>
    </div>
    <div class="search-filter">
      <div class="search-filter-button" v-for="(filter,index) in filters" :class="filterIndex==index?'active':''" @click="filterIndex=index">{{filter.title}}</div>
      <div class="search-filter-button" :class="filterIndex==-1?'active':''" @click="filterIndex=-1">全部</div>
    </div>
    <div class="right">
      <slot></slot>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Search",
    data(){
      return{
        placeholder:"Search here...",
        input:"",
        filterIndex:-1,
        filters:[],
        callback:{}
      }
    },
    watch:{
      filterIndex(val){
        if(typeof this.callback.onFilterChange=="function"){
          this.callback.onFilterChange(val,val==-1?null:this.filters[val]);
        }
      }
    },
    methods:{
      initSearch(data){
        if(data.callback){
          this.callback.onSearch=data.callback.onSearch;
          this.callback.onFilterChange=data.callback.onFilterChange;
        }
        this.filterIndex=-1;
        this.filters=data.filters;
      },
      search(e){
        if(typeof this.callback.onSearch=="function"){
          debugger;
          this.callback.onSearch(this.input);
        }
        if(this.input){
          this.placeholder=this.input;
        }else{
          this.placeholder="Search here...";
        }
        this.input="";
      }
    }
  }
</script>

<style scoped>
  .search-root{
    position: relative;
    height: 42px;
    line-height: 42px;
    background-color: white;
    overflow: hidden;
    -webkit-border-radius: 0px 3.5px 3.5px 0px;
    -moz-border-radius: 0px 3.5px 3.5px 0px;
    border-radius: 0px 3.5px 3.5px 0px;
    font-size: 0px;
    white-space: nowrap;
  }

  .search-title {
    font-size: 14px;
    margin: 0px 0px 0px 21.7px;
    vertical-align: middle;
  }

  .search-input {
    position: relative;
    display: inline-block;
    width: 210px;
    height: 28px;
    border: 0.7px solid #c1e1ff;
    margin-left: 21.7px;
    vertical-align: middle;
    overflow: hidden;
    -webkit-border-radius: 3.5px;
    -moz-border-radius: 3.5px;
    border-radius: 3.5px;
  }

  .search-input > input {
    position: absolute;
    right: 0px;
    top: 0px;
    width: 178.5px;
    height: 100%;
    border: 0px;
    background: none;
    font-size: 14px;
    color: #74c1fe;
    outline:none;
  }

  .search-input > img {
    position: absolute;
    left: 10.5px;
    top: 8.4px;
    width: 11.2px;
    height: 11.2px;
  }

  .search-filter {
    display: inline-block;
    width: auto;
    height: 100%;
    margin-left: 21px;
  }

  .search-filter-button {
    display: inline-block;
    width: auto;
    height: 25.2px;
    line-height: 25.2px;
    vert-align: middle;
    text-align: center;
    padding: 0px 16.1px;
    border: 0.7px solid #c1e1ff;
    margin-right: 7.7px;
    color: #339afe;
    font-size: 13.3px;
    vertical-align: middle;

    -webkit-border-radius: 0px 3.5px 3.5px 3.5px;
    -moz-border-radius: 0px 3.5px 3.5px 3.5px;
    border-radius: 0px 3.5px 3.5px 3.5px;

    -moz-user-select: none;
    -o-user-select: none;
    -khtml-user-select: none;
    -webkit-user-select: none;
    -ms-user-select: none;
    user-select: none;
    cursor: pointer;
  }

  .search-filter-button.active {
    color: white;
    background-color: #339afe;
  }

  .right{
    position: absolute;
    right: 21px;
    top: 0px;
    width: auto;
    height: 100%;
  }
</style>



// WEBPACK FOOTER //
// src/components/DataTable/components/Search/Search.vue