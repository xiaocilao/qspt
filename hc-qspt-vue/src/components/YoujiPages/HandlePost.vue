<template>
  <div class="table">
    <div class="head">
      <div class="title">邮寄方式</div>
    </div>
    <div class="body">
      <div class="takeStyle">
        <el-radio v-model="radio" label="1"  @change="sourceFilter">自取</el-radio>
        <el-radio v-model="radio" label="2"  @change="sourceFilter1">邮寄</el-radio>
      </div>
      <div class="bySelf" v-if="showPrise">
        四川省自贡市自流井区自流景区汇东路230号
      </div>
      <div class="post" v-if="showPriseBody">
        <div>
          <div class="name">姓名：</div>
          <div class="name1"><el-input v-model="name" placeholder="请输入真实姓名"></el-input></div>
        </div>
        <div class="clear"></div>
        <div>
          <div class="phone">电话：</div>
          <div class="phone1"><el-input v-model="phone" placeholder="请输入有效号码"></el-input></div>
        </div>
        <div class="clear"></div>
        <div>
          <div class="post">邮编：</div>
          <div class="post1"><el-input v-model="post" placeholder="请输入邮编"></el-input></div>
        </div>
        <div class="clear"></div>
        <div>
          <div class="address">详细地址：</div>
          <div class="address1"><el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" v-model="address" placeholder="请输入详细地址"></el-input></div>
        </div>
      </div>
    </div>
    <div class="clear"></div>
    <div class='returnPage' @click='returnPage()'>保存并返回主页</div>
  </div>
</template>

<script>
  import Common from '@/lib/common'
  export default {
    data () {
      return {
        radio: '1',
        name: '',
        phone:'',
        post:'',
        address:'',
        showPrise:true,
        showPriseBody:false,
        chooseMothed:1, // 1  自取  2  邮寄
        clientKey:''
      };
    },
    created(){
      if(!window.localStorage){

        alert("浏览器支持localstorage");
        return false;
      }else{
        var storage=window.localStorage;
        this.clientKey= storage.getItem("clientKey");
        Common.clientKey=storage.getItem("clientKey");
      }
    },
    methods: {
      sourceFilter(){
        var that = this;
        that.showPrise=true;
        that.showPriseBody=false
        that.chooseMothed=1
      },
      sourceFilter1(){
        var that = this;
        that.showPrise=false;
        that.showPriseBody=true;
        that.chooseMothed=2
      },
      returnPage(){
        var that = this;
        this.$confirm('确认将不可更改获取方式, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // alert(that.name);
          if(that.chooseMothed==1){
            Common.getByself(this);
          }else if(that.chooseMothed=2){
            if(that.name=='' || that.phone=='' || that.post=='' || that.address==''){
              this.$message({
                type: 'fail',
                message: '检查地址信息是否有遗漏！'
              });
              return;
            }else{
              Common.insertAddress(this);
            }
          }
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '用户已取消'
          });
        });
      }
    }
  }
</script>

<style>
.title{
  text-align: center;
  font-size: 20px;
  font-weight: bolder;
}
.post .name {
  float: left;
}
.post .name1{
    float: left;
}
.post .phone{
  float: left;
}
.post .phone1{
  float: left;
}
.clear{
  clear:both;
}
.returnPage{
  width: 92%;
  height: 2em;
  margin: auto;
  background-color: #1c3e72;
  line-height: 2em;
  text-align: center;
  margin-top: 0.8em;
  font-size: 1em;
  color: white;
  border-radius: 0.3em;
  position: fixed;
  bottom: 1em;
  left: 3%;
}
</style>
