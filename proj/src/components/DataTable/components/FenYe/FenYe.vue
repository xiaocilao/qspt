<template>
  <div class="fenye-root">
    <div class="prev" @click="prev">&#60;</div>
    <span class="num"><span>{{currentPage+1}}</span>/{{pageCount}}</span>
    <div class="next" @click="next">&#62;</div>
    <input class="input" type="text" v-model="input" oninput="this.value=this.value.replace(new RegExp('[^0-9]','g'),'')"/>
    <button class="button" :class="input==''?'':'active'" @click="to(parseInt(input)-1)">跳转</button>
  </div>
</template>

<script>
  export default {
    name: "FenYe",
    data() {
      return {
        pageCount: 0,
        currentPage: -1,
        input: "",
        callback: {}
      }
    },
    watch:{
      currentPage(val){
        if(typeof this.callback.onPageChange=="function"){
          this.callback.onPageChange(val);
        }
      },
    },
    methods: {
      initFenYe(data) {
        if(data.pageCount<=0){
          return;
        }
        if(this.callback){
          this.callback.onPageChange = data.callback.onPageChange;
        }
        this.currentPage = 0;
        this.pageCount = data.pageCount;
      },
      setPageCount(pageCount){
        if(pageCount>0){
          this.pageCount=pageCount;
          if(this.currentPage>=pageCount){
            this.currentPage=pageCount-1;
          }
        }
      },
      next() {
        if (this.currentPage + 1 < this.pageCount) {
          this.currentPage++;
        }
      },
      prev() {
        if (this.currentPage - 1 >= 0) {
          this.currentPage--;
        }
      },
      to(index) {
        this.input="";
        if (index < this.pageCount && index >= 0) {
          this.currentPage = index;
        }
      }
    }
  }
</script>

<style scoped>
  .fenye-root {
    height: 42px;
    line-height: 42px;
    font-size: 0px;
    text-align: right;
  }

  .prev,
  .next {
    display: inline-block;
    width: 24.5px;
    height: 24.5px;
    line-height: 24.5px;
    border: 0.7px solid #bedefe;
    font-size: 11.2px;
    color: #bedefe;
    text-align: center;
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

  .prev:hover,
  .next:hover {
    border-color: #2486ff;
    color: #2486ff;
  }

  .num {
    font-size: 11.2px;
    color: #bedefe;
    vertical-align: middle;
    margin: 0px 21px;
  }

  .num > span {
    color: #2486ff;
  }

  .input {
    width: 56px;
    height: 24.5px;
    font-size: 11.2px;
    border: 0.7px solid #bedefe;
    vertical-align: middle;
    margin-left: 14px;
    background-color: #f7fbff;
    color: #bedefe;
    outline: none;

    -webkit-border-radius: 0px 3.5px 3.5px 3.5px;
    -moz-border-radius: 0px 3.5px 3.5px 3.5px;
    border-radius: 0px 3.5px 3.5px 3.5px;

  }

  .input:focus{
    border-color: #2486ff;
    color: #2486ff;
  }

  .button {
    width: 56px;
    height: 25px;
    line-height: 21px;
    font-size: 11.2px;
    border: 0.7px solid #bedefe;
    vertical-align: middle;
    margin-left: 14px;
    background-color: #bedbff;
    color: white;
    outline: none;

    -webkit-border-radius: 0px 3.5px 3.5px 3.5px;
    -moz-border-radius: 0px 3.5px 3.5px 3.5px;
    border-radius: 0px 3.5px 3.5px 3.5px;
    cursor: pointer;
  }

  .button.active {
    background-color: #2486ff;
  }
</style>
