<template>
    <div class="shangchuan">
        <div class="container-top">
            <img class="container-top-icon" src="./assets/icon.png"/>
            <span class="container-top-text">税单扫描上传</span>
            <div class="container-top-close" @click="$router.replace({name:'SDSCLieBiao'})">返回列表</div>
        </div>
        <div class="container-center">
            <div class="container-center-left">
                <iframe class="gpy" src="/static/gaopaiyi/index.html"></iframe>
            </div>
            <div class="container-center-right">
                <p>征收项目名称</p>
                <input type="text" v-model="formData.zsxmmc"/>
                <p>税务机关名称</p>
                <input type="text" v-model="formData.swjgmc"/>
                <p>纳税人名称</p>
                <input type="text" v-model="formData.nsrmc"/>
                <p>纳税人识别号</p>
                <input type="text" v-model="formData.nsrbh"/>
                <p>凭证序号</p>
                <input type="text" v-model="formData.pzxh"/>
                <p>金额合计（小写）</p>
                <input type="text" v-model="formData.jehjxx"/>
                <p>金额合计（大写）</p>
                <input type="text" v-model="formData.jehjdx"/>
                <p>税款限缴日期</p>
                <input type="text" v-model="formData.skxjrq"/>
                <div class="buttons">
                    <button class="button-shibie" :class="shibieTag==1?'disabled':(shibieTag==2?'retry':'')" @click="shibie">
                        {{shibieTag==0?'扫描识别':(shibieTag==1?'识别中...':'重拍')}}
                    </button>
                    <button class="button-save" :class="baocunTag==1?'disabled':''" @click="tijiao" v-if="shibieTag==2">
                        {{baocunTag==1?'正在保存':'保存'}}
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "ShangChuan",
        props: ["applyId"],
        data() {
            return {
                req: null,
                shibieTag: 0,
                baocunTag: 0,
                formData: {
                    applyId: "",
                    zsxmmc: "",
                    swjgmc: "",
                    nsrmc: "",
                    nsrbh: "",
                    pzxh: "",
                    jehjxx: "",
                    jehjdx: "",
                    skxjrq: ""
                }
            }
        },
        mounted() {
            this.formData.applyId = this.$route.params.applyId;
            if(!this.formData.applyId){
                this.$router.replace({name:'SDSCLieBiao'});
            }
            var iframe=this.$el.getElementsByClassName("gpy")[0];
            iframe.onload=function () {
                setTimeout(function () {
                    try {
                        iframe.contentWindow.start();
                    }catch (e) {
                        // console.log("高拍仪启动失败");
                    }
                },100);
            };
        },
        beforeDestroy() {

            this.$el.getElementsByClassName("gpy")[0].contentWindow.stop();

            if (this.req) {
                this.req.abort();
            }
        },
        methods: {
            shibie() {
                var self = this;
                var gpy = self.$el.getElementsByClassName("gpy")[0].contentWindow;
                if (self.shibieTag == 0) {
                    self.shibieTag = 1;
                    setTimeout(function () {
                        try {
                            gpy.shibie(function (result) {
                                self.shibieTag = 2;
                                self.formData.zsxmmc = result.zsxmmc ? result.zsxmmc : "";
                                self.formData.swjgmc = result.swjgmc ? result.swjgmc : "";
                                self.formData.nsrmc = result.nsrmc ? result.nsrmc : "";
                                self.formData.nsrbh = result.nsrbh ? result.nsrbh : "";
                                self.formData.pzxh = result.pzxh ? result.pzxh : "";
                                self.formData.jehjxx = result.jehjxx ? result.jehjxx : "";
                                self.formData.jehjdx = result.jehjdx ? result.jehjdx : "";
                                self.formData.skxjrq = result.skxjrq ? result.skxjrq : "";
                                self.formData.sdtp = gpy.photo;
                            }, function () {
                                //  myalert("识别失败");
                                self.shibieTag = 0;
                                try {
                                    gpy.start();
                                }catch (e) {
                                    // console.log("高拍仪启动失败");
                                }
                            });
                        }catch (e) {
                            //  myalert("识别失败");
                            self.shibieTag = 0;
                            try {
                                gpy.start();
                            }catch (e) {
                                //   console.log("高拍仪启动失败");
                            }
                        }
                    }, 100);
                } else if (self.shibieTag == 2) {
                    if(self.req){
                        self.req.abort();
                    }
                    try {
                        gpy.start();
                    }catch (e) {
                        //  console.log("高拍仪启动失败");
                    }
                    self.shibieTag = 0;
                    self.baocunTag = 0;
                    self.formData.zsxmmc = "";
                    self.formData.swjgmc = "";
                    self.formData.nsrmc = "";
                    self.formData.nsrbh = "";
                    self.formData.pzxh = "";
                    self.formData.jehjxx = "";
                    self.formData.jehjdx = "";
                    self.formData.skxjrq = "";
                    self.formData.sdtp = "";
                }
            },
            tijiao() {
                var self = this;
                if (self.baocunTag == 1) {
                    return;
                }
                for (var i in self.formData) {
                    if (!self.formData[i]) {
                        // myalert("信息填写不完整");
                        return;
                    }
                }
                if (!new RegExp("^\\d\\d\\d\\d-\\d{1,2}-\\d{1,2}$", "g").test(self.formData.skxjrq)) {
                    //   myalert("税款限缴日期格式错误");
                    return;
                }
                self.baocunTag = 1;
                self.req = self.ajax({
                    url: "/api/apply/scsd",
                    timeout:0,
                    data: this.formData,
                    success: function (data) {
                        if (data.code == 200) {
                            // myalert("保存成功");
                            self.$router.replace({name:'SDSCLieBiao'});
                        } else {
                            //  myalert("保存失败");
                        }
                        self.baocunTag = 0;
                    },
                    error: function () {
                        self.baocunTag = 0;
                    }
                });
            }
        }
    }
</script>

<style scoped>
    .shangchuan {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        position: relative;
        width: 100%;
        height: 100%;
        padding-top: 35px;
        background-color: white;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        border-radius: 5px;
        overflow: hidden;
        box-shadow: 0px 0px 10px gainsboro;
    }

    .container-top {
        position: absolute;
        left: 0px;
        top: 0px;
        width: 100%;
        height: 35px;
        line-height: 30px;
        background-color: #ebf0f1;
    }

    .container-top-icon {
        width: 20px;
        height: 20px;
        vertical-align: middle;
        margin-left: 28px;
    }

    .container-top-text {
        font-size: 16px;
        vertical-align: middle;
        margin-left: 8px;
        color: #646363;
    }

    .container-top-close {
        position: absolute;
        right: 28px;
        top: 50%;
        width: 61px;
        height: 20px;
        line-height: 21px;
        margin: -10px 0px;
        font-size: 12px;
        vertical-align: middle;
        color: black;
        border: 1px solid #d7dee4;
        text-align: center;
        cursor: pointer;
        border-radius: 3px;
        background-color: white;
    }

    .container-center {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        position: relative;
        width: 100%;
        height: 100%;
        padding-right: 343px;
        overflow: hidden;
    }

    .container-center-right {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        position: absolute;
        right: 0px;
        top: 0px;
        padding-top: 14px;
        width: 343px;
        height: 100%;
        overflow: auto;
    }

    .container-center-right > p {
        font-size: 13px;
        color: #000000;
        width: 252px;
        margin: 7px auto 7px;
    }

    .container-center-right > input {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        display: block;
        width: 252px;
        height: 32.2px;
        font-size: 11.9px;
        color: #000000;
        border: 0.7px solid #d7dee4;
        padding-left: 7px;
        -webkit-border-radius: 0px 4.9px 4.9px 4.9px;
        -moz-border-radius: 0px 4.9px 4.9px 4.9px;
        border-radius: 0px 4.9px 4.9px 4.9px;
        margin: 0px auto;
        outline: none;

    }

    .buttons {
        width: 252px;
        height: auto;
        font-size: 0px;
        margin: 30px auto 0px;
    }

    .buttons > button {
        outline: none;
    }

    .button-shibie {
        width: 84px;
        height: 26px;
        background-color: #4381e6;
        color: white;
        font-size: 11.9px;
        border: none;
        -webkit-border-radius: 0px 3px 3px 3px;
        -moz-border-radius: 0px 3px 3px 3px;
        border-radius: 0px 3px 3px 3px;
        cursor: pointer;
    }

    .button-shibie:hover {
        background-color: #326aba;
    }

    .button-shibie:active {
        background-color: #4688f2;
    }

    .button-shibie.disabled {
        background-color: #81b3f0;
        cursor: not-allowed;
    }

    .button-shibie.retry {
        background-color: white;
        border: 1px solid #dcdfe6;
        color: black;
    }

    .button-shibie.retry:hover {
        border: 1px solid #c6e2ff;
        background-color: #ecf5ff;
        color: #409eff;
    }

    .button-shibie.retry:active {
        border: 1px solid #3a8ee6;
        color: #3a8ee6;
    }

    .button-save {
        width: 84px;
        height: 26px;
        background-color: #4381e6;
        color: white;
        font-size: 11.9px;
        border: none;
        margin-left: 28px;
        -webkit-border-radius: 0px 3px 3px 3px;
        -moz-border-radius: 0px 3px 3px 3px;
        border-radius: 0px 3px 3px 3px;
        cursor: pointer;
    }

    .button-save:hover {
        background-color: #326aba;
    }

    .button-save:active {
        background-color: #4688f2;
    }

    .button-save.disabled {
        background-color: #81b3f0;
        cursor: not-allowed;
    }

    .container-center-left {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        position: relative;
        width: 100%;
        height: 100%;
        padding: 21px 0px 21px 35px;
        overflow: hidden;
    }

    .gpy {
        width: 100%;
        height: 100%;
        background-color: #f2f7f8;
        border: none;
    }

</style>
