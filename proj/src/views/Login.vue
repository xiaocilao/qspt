<template>
    <div class="body">

        <div id="large-header">
            <canvas id="demo-canvas"></canvas>
        </div>
        <div id="left">
            <img id="lef-img1" src="../assets/1.png"/>
            <img id="lef-img2" src="../assets/2.png"/>
            <img id="lef-phone" src="../assets/phone.png"/>
            <img id="lef-img3" src="../assets/3.png"/>
            <img id="lef-img4" src="../assets/4.png"/>
            <h1>"在线"</h1>
            <h2>缴费</h2>
        </div>
        <div id="container">
            <div id="container-title">互联网税银柜台</div>
            <div style="display: flex;justify-content: center;margin-top: 50px">
                <el-card style="width: 400px">
                    <div slot="header" class="clearfix">
                        <span>登录</span>
                    </div>
                    <table>
                        <tr>
                            <td>用户名</td>
                            <td>
                                <el-input v-model="user.username" placeholder="请输入用户名"></el-input>
                            </td>
                        </tr>
                        <tr>
                            <td>密码</td>
                            <td>
                                <el-input type="password" v-model="user.password" placeholder="请输入密码" @keydown.enter.native="doLogin"></el-input>
                            </td>
                        </tr>
                        <tr>
                            <td>验证</td>
                            <td>
                                <div class="form-inline-input">
                                    <div class="code-box" id="code-box">
                                        <input type="text" name="code" class="code-input" />
                                        <p></p>
                                        <span>>>></span>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <el-button style="width: 300px" type="primary" @click="doLogin">登录</el-button>
                            </td>
                        </tr>
                                <!---->
                                <!--<el-form-item label="验证">-->

                                <!--</el-form-item>-->
                                <!---->
                    </table>
                </el-card>
            </div>

        </div>
        <div id="footer">Copyright 2019 - 2020 Hengchi. All Rights Reserved</div>
    </div>
</template>
<script>
    import Common from "../js/common.js"
    export default {
        data() {
            return{
                cangoo:false,
                user:{
                    username:'',
                    password:'',
                }
            }
        },
        methods:{
            loading(data){
                let arr = [data];
                let str = arr.join('</br>');
                this.$message({
                    dangerouslyUseHTMLString:true,
                    message:str,
                    type:'warning'
                })
            },
            doLogin(){
                var self=this;
                //登录
                debugger;
                if(!self.cangoo){
                    self.loading("请拖动验证码！");
                    return;
                }
                let postData= new URLSearchParams();
                postData.append('loginType',1);
                var that = this;
                postData.append('username',that.user.username);
                postData.append('password', that.user.password);
                self.$http.post('/qishui/base/api/apply/login',postData).then(function (data) {
                debugger;
                   if(data.data.rows[0].code==='200'){
                       var storage=window.localStorage;
                       storage.removeItem("ClientKey");
                       storage.removeItem("user");
                       storage.setItem("ClientKey",data.data.rows[0].ClientKey);
                       storage.setItem("user",that.user.username);
                       self.$router.push({
                           path:"Main",
                           query:{
                               token:data.data.rows[0].ClientKey
                           }
                       });
                   }else {
                       self.loading("用户名或者密码错误！");
                   }

                });

            },
        //    记得删除
            //获取元素距离页面边缘的距离
            getOffset(box,direction){
                var setDirection = (direction == 'top') ? 'offsetTop' : 'offsetLeft' ;
                var offset = box[setDirection];
                var parentBox = box.offsetParent;
                while(parentBox){
                    offset+=parentBox[setDirection];
                    parentBox = parentBox.offsetParent;
                }
                parentBox = null;
                return parseInt(offset);
            },
            moveCode(code,_this){
                var fn = {codeVluae : code};
                var box = document.querySelector("#code-box"),
                    progress = box.querySelector("p"),
                    codeInput = box.querySelector('.code-input'),
                    evenBox = box.querySelector("span");
                var boxEven = ['mousedown','mousemove','mouseup'];
                if(typeof document.ontouchstart == 'object'){
                    boxEven = ['touchstart','touchmove','touchend'];
                }
                var goX,offsetLeft,deviation,evenWidth,endX;
                function moveFn(e){
                    e.preventDefault();
                    e = (boxEven['0'] == 'touchstart') ? e.touches[0] : e || window.event;
                    endX = e.clientX - goX;
                    endX = (endX > 0) ? (endX > evenWidth) ? evenWidth : endX : 0;
                    if(endX > evenWidth * 0.7){
                        progress.innerText = '松开验证';
                        progress.style.backgroundColor = "#66CC66";
                    }else{
                        progress.innerText = '';
                        progress.style.backgroundColor = "#FFFF99";
                    }
                    progress.style.width = endX+deviation+'px';
                    evenBox.style.left = endX+'px';
                }
                function removeFn() {
                    document.removeEventListener(boxEven['2'],removeFn,false);
                    document.removeEventListener(boxEven['1'],moveFn,false);
                    if(endX > evenWidth * 0.7){
                        progress.innerText = '验证成功';
                        _this.cangoo=true;
                        progress.style.width = evenWidth+deviation+'px';
                        evenBox.style.left = evenWidth+'px'
                        codeInput.value = fn.codeVluae;
                        evenBox.onmousedown = null;
                        // that.ruleForm.verification = true;
                    }else{
                        progress.style.width = '0px';
                        evenBox.style.left = '0px';
                    }
                };
                function getOffset(box,direction){
                    var setDirection = (direction == 'top') ? 'offsetTop' : 'offsetLeft' ;
                    var offset = box[setDirection];
                    var parentBox = box.offsetParent;
                    while(parentBox){
                        offset+=parentBox[setDirection];
                        parentBox = parentBox.offsetParent;
                    }
                    parentBox = null;
                    return parseInt(offset);
                };
                evenBox.addEventListener(boxEven['0'], function(e) {
                    e = (boxEven['0'] == 'touchstart') ? e.touches[0] : e || window.event;
                    goX = e.clientX,
                        offsetLeft = getOffset(box,'left'),
                        deviation = this.clientWidth,
                        evenWidth = box.clientWidth - deviation,
                        endX;
                    document.addEventListener(boxEven['1'],moveFn,false);
                    document.addEventListener(boxEven['2'],removeFn,false);
                },false);
                fn.setCode = function(code){
                    if(code)
                        fn.codeVluae = code;
                }
                fn.getCode = function(){
                    return fn.codeVluae;
                }
                fn.resetCode = function(){
                    evenBox.removeAttribute('style');
                    progress.removeAttribute('style');
                    codeInput.value = '';
                };
                return fn;
            },

        },
        mounted(){
            var _this = this;

            // var code = "jsaidaisd656",
            var code = "1234556",
             codeFn = new _this.moveCode(code,_this);
        }
    }

</script>
<style scoped>
    #large-header {
        position: fixed;
        left: 0px;
        top: 0px;
        z-index: -1;
        width: 100%;
        height: 100%;
        background: -webkit-linear-gradient(45deg, #48b1fd, #1240f1);
        background: -o-linear-gradient(45deg, #48b1fd, #1240f1);
        background: -moz-linear-gradient(45deg, #48b1fd, #1240f1);
        background: linear-gradient(45deg, #48b1fd, #1240f1);
    }

    #large-header > canvas {
        width: 100%;
        height: 100%;
    }

    #container {
        position: absolute;
        right: 214px;
        top: 45%;
        z-index: 3;
        width: 350px;
        height: 330px;
        margin-top: -165px;
    }

    #container-title{
        position: absolute;
        left: 0px;
        top: -60px;
        width: 100%;
        height: 60px;
        text-align: center;
        font-size: 30px;
        color: #f5f5f5;
        font-weight: normal;
    }

    #mmdl {
        display: none;
    }

    #mmdl, #smdl, #smcg {
        position: absolute;
        left: 0px;
        top: 0px;
        width: 100%;
        height: 100%;
        background-color: white;
        border-radius: 5px;
    }

    #mmdl > h1, #smdl > h1, #smcg > h1 {
        width: 280px;
        font-size: 18px;
        color: #333333;
        font-size: normal;
        margin: 44px auto 0px;
    }

    #mmdl > input, #smcg > input {
        box-sizing: border-box;
        -moz-box-sizing: border-box;
        -moz-box-sizing: border-box;
        display: block;
        width: 280px;
        height: 42px;
        margin-left: auto;
        margin-right: auto;
        padding: 0px 22px;
        border: 1px solid #e5e5e5 !important;
        font-size: 18px;
        font-size: normal;
        color: #767676;
        outline: none !important;
    }

    #userName, #userName2 {
        margin-top: 39px;
    }

    #password, #password2 {
        margin-top: 26px;
    }

    #login-btn, #login-btn2 {
        box-sizing: border-box;
        -moz-box-sizing: border-box;
        -moz-box-sizing: border-box;
        display: block;
        width: 280px;
        height: 42px;
        margin: 24px auto 0px;
        font-size: 18px;
        background-color: #fe8100;
        color: white;
        font-size: normal;
        border: none;
        outline: none;
        cursor: pointer;
    }

    #login-btn:hover, #login-btn2:hover {
        background-color: #ff982e;
    }

    #login-btn:active, #login-btn2:active {
        background-color: #fe8100;
        transform: scale(0.95);
        -ms-transform: scale(0.95);
        -moz-transform: scale(0.95);
        -webkit-transform: scale(0.95);
    }

    #mmdl > img, #smdl > img, #smcg > img {
        position: absolute;
        right: 24px;
        top: 24px;
        width: 57px;
        height: 59px;
        cursor: pointer;
    }

    #mmdl > h2, #smdl > h2, #smcg > h2 {
        position: absolute;
        right: 97px;
        top: 24px;
        width: 82px;
        height: 26px;
        line-height: 26px;
        font-size: 12px;
        color: #276df6;
        background-color: #bee5ff;
        border: 1px solid #72a9fe;
        margin: 0px;
        cursor: pointer;
        text-align: center;
    }

    #mmdl > h2 > span, #smdl > h2 > span, #smcg > h2 > span {
        box-sizing: border-box;
        -moz-box-sizing: border-box;
        -moz-box-sizing: border-box;
        display: block;
        position: absolute;
        top: 50%;
        right: 0px;
        width: 12px;
        height: 12px;
        margin: -6px -6px;
        background-color: #bee5ff;
        border-top: 1px solid #72a9fe;
        border-right: 1px solid #72a9fe;
        transform: scaleY(0.8) rotate(45deg);
        -ms-transform: scaleY(0.8) rotate(45deg);
        -moz-transform: scaleY(0.8) rotate(45deg);
        -webkit-transform: scaleY(0.8) rotate(45deg);
    }

    #smdl > img {
        width: 43px;
        height: 39px;
    }

    #smdl > div {
        width: 138px;
        height: 138px;
        margin: 45px auto 0px;
        background-color: lightgray;
    }

    #smdl > h3 {
        text-align: center;
        font-size: 14px;
        font-weight: normal;
        color: #b8bbba;
        margin: 15px 0px 0px;
        letter-spacing: 1px;
    }

    #smcg {
        display: none;
    }

    #left {
        position: absolute;
        left: 136px;
        top: 50%;
        z-index: 1;
        width: 780px;
        height: 900px;
        color: #f5f5f5;;
        margin: -450px 0px;
    }

    @keyframes myAnimate {
        0% {
            transform: translateY(30px);
        }
        50% {
            transform: translateY(-30px);
        }
        100% {
            transform: translateY(30px);
        }
    }

    @-moz-keyframes myfirst /* Firefox */
    {
        0% {
            transform: translateY(30px);
        }
        50% {
            transform: translateY(-30px);
        }
        100% {
            transform: translateY(30px);
        }
    }

    @-webkit-keyframes myfirst /* Safari 和 Chrome */
    {
        0% {
            transform: translateY(30px);
        }
        50% {
            transform: translateY(-30px);
        }
        100% {
            transform: translateY(30px);
        }
    }

    @-o-keyframes myfirst /* Opera */
    {
        0% {
            transform: translateY(30px);
        }
        50% {
            transform: translateY(-30px);
        }
        100% {
            transform: translateY(30px);
        }
    }

    #lef-img1 {
        position: absolute;
        left: 40px;
        top: 215px;
        width: 127px;
        height: 374px;
        animation: myAnimate 5s infinite;
        -moz-animation: myAnimate 5s infinite;
        -webkit-animation: myAnimate 5s infinite;
        -o-animation: myAnimate 5s infinite;
    }

    #lef-img2 {
        position: absolute;
        left: 375px;
        top: 238px;
        width: 126px;
        height: 263px;
        animation: myAnimate 3s infinite;
        -moz-animation: myAnimate 3s infinite;
        -webkit-animation: myAnimate 3s infinite;
        -o-animation: myAnimate 3s infinite;
    }

    #lef-img3 {
        position: absolute;
        left: 268px;
        top: 552px;
        width: 127px;
        height: 307px;
        animation: myAnimate 4s infinite;
        -moz-animation: myAnimate 4s infinite;
        -webkit-animation: myAnimate 4s infinite;
        -o-animation: myAnimate 4s infinite;
    }

    #lef-img4 {
        position: absolute;
        left: 624px;
        top: 455px;
        width: 127px;
        height: 189px;
        animation: myAnimate 3.5s infinite;
        -moz-animation: myAnimate 3.5s infinite;
        -webkit-animation: myAnimate 3.5s infinite;
        -o-animation: myAnimate 3.5s infinite;
    }

    #lef-phone {
        position: absolute;
        left: 123px;
        top: 299px;
        width: 446px;
        height: 337px;
    }

    #left > h1 {
        position: absolute;
        left: 123px;
        top: 68px;
        font-size: 45px;
        font-weight: normal;
        margin: 0px;
    }

    #left > h2 {
        position: absolute;
        left: 271px;
        top: 135px;
        font-size: 45px;
        font-weight: normal;
        margin: 0px;
    }

    #footer {
        position: fixed;
        left: 0px;
        bottom: 30px;
        z-index: 2;
        width: 100%;
        height: auto;
        text-align: center;
        font-size: 15px;
        font-weight: normal;
        color: white;
    }
    /*记得删除*/
    .form-inline-input{
        border:1px solid #dadada;
        border-radius:5px;
    }
    .form-inline-input input,
    .code-box{
        padding: 0 3px;
        width: 240px;
        height: 40px;
        color: #fff;
        text-shadow: 1px 1px 1px black;
        background: #efefef;
        border: 0;
        border-radius: 5px;
        outline: none;
    }
    .code-box{
        position: relative;
    }
    .code-box p,
    .code-box span{
        display:block;
        position: absolute;
        left: 0;
        height: 40px;
        text-align: center;
        line-height: 40px;
        border-radius: 5px;
        padding:0;
        margin:0;
    }
    .code-box span{
        width: 40px;
        background-color:#fff;
        font-size: 16px;
        cursor: pointer;
        margin-right:1px;
    }
    .code-box p{
        width: 0;
        background-color: #FFFF99;
        overflow: hidden;
        text-indent: -20px;
        transition: background 1s ease-in;
    }
    .code-box .code-input{
        display: none;
    }
    .code-box .code-input{
        display: none;
    }
    .form-inline-input{
        border:1px solid #dadada;
        border-radius:5px;
    }
    .form-inline-input input,
    .code-box{
        padding: 0 3px;
        width: 240px;
        height: 40px;
        color: #fff;
        text-shadow: 1px 1px 1px black;
        background: #efefef;
        border: 0;
        border-radius: 5px;
        outline: none;
    }
    .code-box{
        position: relative;
    }
    .code-box p,
    .code-box span{
        display:block;
        position: absolute;
        left: 0;
        height: 40px;
        text-align: center;
        line-height: 40px;
        border-radius: 5px;
        padding:0;
        margin:0;
    }
    .code-box span{
        width: 40px;
        background-color:#fff;
        font-size: 16px;
        cursor: pointer;
        margin-right:1px;
    }
    .code-box p{
        width: 0;
        background-color: #FFFF99;
        overflow: hidden;
        text-indent: -20px;
        transition: background 1s ease-in;
    }
    .code-box .code-input{
        display: none;
    }
    .code-box .code-input{
        display: none;
    }
</style>