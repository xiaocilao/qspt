<template>
  <div class="sign_board">
    <canvas ref="canvas"></canvas>
  </div>
</template>

<script>
  export default {
    props:["penColor","lineWidth","backgroundColor"],
    data(){
      return{
        penDown:false
      }
    },
    watch:{
      penColor(val){
        this.ctx.strokeStyle=val?val:"#000000";
        this.clear();
      },
      lineWidth(val){
        this.ctx.lineWidth=val?val:15;
        this.clear();
      },
      backgroundColor(val){
        this.ctx.fillStyle=val?val:"#ffffff";
        this.clear();
      }
    },
    mounted() {
      var self=this;
      var canvas=this.$refs.canvas;

      self.init();
      setInterval(function () {
        if(canvas.width !== self.$el.clientWidth || canvas.height !== self.$el.clientHeight){
          self.init();
        }
      },1000);

      function penDown(x,y){
        self.ctx.moveTo(x,y);
        self.ctx.beginPath();
        self.penDown=true;
      }

      function penUp(){
        self.ctx.closePath();
        self.penDown=false;
      }

      function penMove(x,y){
        if(self.penDown){
          self.ctx.lineTo(x,y);
          self.ctx.stroke();
        }
      }

      function getElementPageOffset(el){
        var x=el.offsetLeft;
        var y=el.offsetTop;
        var parent=el.offsetParent;
        while (parent){
          x+=parent.clientLeft+parent.offsetLeft-parent.scrollLeft;
          y+=parent.clientTop+parent.offsetTop-parent.scrollTop;
          parent=parent.offsetParent;
        }
        return {x:x,y:y};
      }

      //电脑

      canvas.onmousedown=function (e) {
        if(e.offsetX!==undefined && e.offsetY!==undefined&& false){
          penDown(e.offsetX,e.offsetY);
        }else{
          var canvasPageOffset=getElementPageOffset(self.$refs.canvas);
          penDown(e.pageX-canvasPageOffset.x,e.pageY-canvasPageOffset.y);
        }
      };

      canvas.onmouseup=function (e) {
        penUp();
      };

      canvas.onmouseleave=function (e) {
        penUp();
      };

      canvas.onmousemove=function (e) {
        if(e.offsetX!==undefined && e.offsetY!==undefined && false){
          penMove(e.offsetX,e.offsetY);
        }else {
          var canvasPageOffset=getElementPageOffset(self.$refs.canvas);
          penMove(e.pageX-canvasPageOffset.x,e.pageY-canvasPageOffset.y);
        }
      };

      //手机

      canvas.addEventListener('touchstart',function (e) {
        var touch=e.touches[0];
        if(touch.offsetX!==undefined && touch.offsetY!==undefined){
          penDown(touch.offsetX,touch.offsetY);
        }else{
          var canvasPageOffset=getElementPageOffset(self.$refs.canvas);
          penDown(touch.pageX-canvasPageOffset.x,touch.pageY-canvasPageOffset.y);
        }
      }, false);

      canvas.addEventListener('touchend',function (e) {
        penUp();
      }, false);

      canvas.addEventListener('touchcancel',function (e) {
        penUp();
      }, false);

      canvas.addEventListener('touchmove',function (e) {
        var touch=e.touches[0];
        var canvasPageOffset=getElementPageOffset(self.$refs.canvas);
        if(touch.offsetX!==undefined && touch.offsetY!==undefined){
          penMove(touch.offsetX,touch.offsetY);
        }else{
          penMove(touch.pageX-canvasPageOffset.x,touch.pageY-canvasPageOffset.y);
        }
      }, false);

    },
    methods:{
      init(){
        var canvas=this.$refs.canvas;
        canvas.width = this.$el.clientWidth;
        canvas.height = this.$el.clientHeight;
        this.ctx = canvas.getContext("2d");
        this.ctx.lineWidth=this.lineWidth?lineWidth:15;
        this.ctx.strokeStyle=this.penColor?this.penColor:"#000000";
        this.ctx.fillStyle=this.backgroundColor?this.backgroundColor:"#ffffff";
        this.ctx.lineCap="round";

        this.ctx.fillRect(0,0,canvas.width,canvas.height);
      },
      clear(){
        this.ctx.fillRect(0,0,this.$refs.canvas.width,this.$refs.canvas.height);
        self.penDown=false;
      },
      getData(success){
        if(typeof success=="function"){
          return this.$refs.canvas.toBlob(function (blob) {
            success(blob);
          });
        }
      },
      getBase64Data(){
        return this.$refs.canvas.toDataURL("image/png");
      }
    }
  }
</script>

<style scoped>
  .sign_board{
    overflow: hidden !important;
  }
</style>
