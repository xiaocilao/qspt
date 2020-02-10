<template>
    <div>
        <div class='background' :hidden='backhidden'></div>
        <img src='../../assets/static/images/comple.png' class='comple'>
        <div class='titletext'>{{spwz}}</div>
        <div class='titletext1'>{{spwzsecend}}</div>
        <div class='returnindex' @click='returnindex()'>{{spljwz}}</div>
    </div>
</template>
<script>
  import Common from '@/lib/common'

export default {
  props:{
    spzt:{
      type:String,
      default:null
    },
    cards:{
      type:Array,
      default:null
    }
  },
    data(){
        return{
            backhidden:true,
            spwz:"您的资料已经提交税务局审核",
            spwzsecend:"3-5个工作日内审核资料，请耐心等待",//灰色提示字
            spljwz:"下一步"//审批连接的文字变化
        }
    },
  mounted(){//根据状态修改文字
    switch (this.spzt) {
      case 1:
      case "1":
        this.spwz="您的资料已经提交税务局审核"
        this.spwzsecend="3-5个工作日内审核资料，请耐心等待"
        break;
      case 2:
      case "2":
        this.spwz="您的资料已经通过税务局审核"
        this.spwzsecend="税单正在打印申请中，请您耐心等候"
        break;
      case 3:
      case "3":
          this.spwz="您的资料已经审核通过"
          this.spwzsecend="请到首页进行缴费"
        // this.$emit('goOtherPage');
        break;
      case 4:
      case "4":
        this.spwz="您的缴费申请已提交"
        this.spwzsecend="银行正在处理您的缴费申请，请您耐心等候"
        break;
      case 6:
      case "6":
        this.spwz="缴费成功"
        this.spwzsecend="税务局正在处理您的资料，请耐心等候..."
        this.spljwz="返回首页"
        break;
      case 7:
      case "7":

        this.spwz="邮件已寄出"
        this.spwzsecend="您的邮件已寄出"
        break;
      case "8":
        this.spwz="已签收"
        this.spwzsecend="已完成在线扣税"
        break;

      case 9:
      case "9":
        this.spwz="缴费成功"
        this.spwzsecend="正在处理您的申请，我们将在3-5个工作日提醒您前来自取"
        break;
    }
  },
    methods:{
        returnindex(){
            var that = this;
            //这里根据spzt（审批状态）做对应的文字修改与其它操作就行
            if(this.spzt=="7"){
              this.$confirm('是否确认签收?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
              }).then(() => {
                Common.postGet(that);
                this.$router.push("/index")
              }).catch(() => {
                this.$message({
                  type: 'info',
                  message: '用户已取消'
                });
                  this.$router.push("/index")
              });
            } else if (this.spzt == "1" || this.spzt == "2" || this.spzt == "8" || this.spzt == "9"){
              this.$router.push("index")
            }else if(this.spzt=="5"){
              this.spljwz = "配置邮寄"
              this.$router.push("/YoujiPages/HandlePost")
            }
            else{
              this.$router.push("index")
            }
            
        },
    }
}
</script>
<style scoped>
.titletext{
  width: 80%;
  margin: auto;
  margin-top: 2em;
  text-align: center;
  font-size: 1em;
  color: #5a7b9f;
}
.titletext1{
  width: 80%;
  margin: auto;
  margin-top: 0.5em;
  text-align: center;
  font-size: 1em;
  color: #c0c0c0;
}
.next{
  text-align: center;
  width: 80%;
  margin: auto;
  height: 80rpx;
  line-height: 80rpx;
  font-weight: 500;
  border-radius: 50rpx;
  background-color: white;
  margin-top: 480rpx;
  box-shadow: 0px 0px 10rpx 10rpx #F8F8F8;
}
.comple{
  width: 8em;
  height:8em;
  margin-left: 7em;
  margin-top: 5em;
}
.returnindex{
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
