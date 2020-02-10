<template>
    <div class="shenhe">
        <div class="left">
            <div class="left-top">
                <tab class="left-top-tab" ref="tab"></tab>
                <div class="left-top-button">
                    <div class="left-top-button-btg button">
                        不通过
                        <div class="left-top-button-btg-reasons">
                            <div @click="btg('图片模糊')">图片模糊</div>
                            <div @click="btg('信息有误')">信息有误</div>
                            <div @click="btg('其他')">其他</div>
                        </div>
                    </div>
                    <div class="left-top-button-tg button"  @click="tg">通过</div>
                </div>
            </div>
            <pic-browser ref="picBrowser" class="left-pic-browser"></pic-browser>
        </div>

        <div class="right">
            <div class="right-top">
                <div class="back button" @click="goback">返回列表</div>
            </div>
            <div class="right-center">
                <div class="right-center-content">
                    <template v-for="kv in kvs" v-if="kv.infoId==infoId">
                        <p>{{kv.keyName}}</p>
                        <input :value="kv.keyValue" readonly/>
                    </template>
                </div>
            </div>
            <div class="right-bottom button" :class="(remain>0 || ytj==1)?'disabled':''" @click="tj">{{remain>0?'剩余'+remain+'张图片未审核':(ytj==0?'提交资料':(ytj==1?'正在提交':'审核下一位'))}}</div>
        </div>
    </div>
</template>
<script>
    import Tab from "./Tab/Tab";
    import PicBrowser from "./PictureBrowser/PicBrowser";
    import $ from 'jquery';
    export default {
        name: "index",
        components: {
            PicBrowser,
            Tab
        },
        props:{
            index:{
                type:String,
                default:null
            }
        },
        data() {
            return {
                ytj:0,
                infoId:-1,
                sqId:-1,
                remain:-1,
                applyData:{},
                kvs:[]
            }
        },
        methods:{
            updateRemain(){
                var pictures=this.$refs.picBrowser.pictures;
                var old=this.$refs.tab.getActiveItem().remain;
                var count=0;
                for(var i in pictures){
                    if(pictures[i].status==0){
                        count++;
                    }
                }
                this.$refs.tab.getActiveItem().remain=count;
                this.remain=this.remain-(old-count);
            },
            goback(){
                self.$emit('changePage','',2);
            },
            tg(){
                if(this.ytj>0){
                    this.loading(this,'已经提交的审核不能再编辑');
                    return;
                }
                $(this.$el).find(".left-top-button-btg-reasons").slideUp(200);
                var picture=this.$refs.picBrowser.getCurrentPicture();
                var self=this;
                if(picture!=null){

                    let postData= new URLSearchParams();
                    postData.append('id',picture.id);
                    self.$http.post('/qishui/base/api/apply/tptg',postData).then(function (data) {
                        if(data.data.code==200){
                            picture.file.shzt=1;
                            picture.status=1;
                            self.updateRemain();
                        }else{
                            this.loading(this,'操作失败');
                        }
                    });
                }
            },
            btg(reason){
                if(this.ytj>0){
                    this.loading(this,'已经提交的审核不能再编辑');
                    return;
                }
                var self=this;
                var picture=self.$refs.picBrowser.getCurrentPicture();
                if(picture!=null){

                    let postData= new URLSearchParams();
                    postData.append('id',picture.id);
                    postData.append('reason',reason);
                    this.$http.post('/qishui/base/api/apply/tpbtg',postData).then(function (data) {

                        if(data.data.code==200){
                            picture.file.shzt=-1;
                            picture.status=-1;
                            self.updateRemain();
                        }else{
                            this.loading(this,'操作失败');
                        }
                    });
                }
            },
            tj(){
                var self=this;
                if(self.remain>0 || self.ytj==1){
                    return;
                }else if(self.ytj==2){
                    self.loadData();
                    return;
                }

                this.$confirm('提交后将不可撤销', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(()=>{
                    var flowType=self.$refs.tab.getActiveItem();
                    if(flowType!=null){
                        self.ytj=1;
                        let postData= new URLSearchParams();
                        postData.append('applyId',this.sqId);
                        this.$http.post('/qishui/base/api/apply/tjzl',postData).then(function (data) {
                            switch (data.data.code) {
                                case 200:
                                    self.loading(self,"审核通过！");
                                    // self.loadData();
                                    self.$emit('changePage','',2);
                                    self.ytj=2;
                                    break;
                                case 201:
                                    self.loading(self,"审核不通过！");
                                    self.$emit('changePage','',2);
                                    // self.loadData();
                                    break;
                                case 202:this.loading(self,"提交失败：审核未完成");self.ytj=2;self.ytj=0;break;
                                default:this.loading(self,"提交失败");self.ytj=0;break;
                            }
                        });
                    }
                });



            },
            showFlow(flowType){
                if(this.applyData&&this.applyData[flowType]){
                    var data=this.applyData[flowType];
                    //取出所有图片
                    var pictures=[];
                    for(var i in data){
                        var flow=data[i];
                        if(!flow.flowFiles){
                            continue;
                        }
                        for (var j in flow.flowFiles) {
                            var file=flow.flowFiles[j];
                            var picture={};
                            picture.id=file.id;
                            picture.big="/qishui/base/api/apply/fjxx.png?fileId="+file.id;
                            picture.small="/qishui/base/api/apply/fjxx.png?fileId="+file.id;
                            picture.status=file.shzt;
                            picture.infoId=file.infoId;
                            picture.file=file;
                            pictures.push(picture);
                        }
                    }
                    //设置图片
                    this.$refs.picBrowser.updatePictures(pictures);

                    //取出所有信息
                    var kvs=[];
                    for(var i in data){
                        var flow=data[i];
                        if(!flow.flowDatas){
                            continue;
                        }
                        for (var j in flow.flowDatas) {
                            kvs.push(flow.flowDatas[j]);
                        }
                    }
                    //设置信息
                    this.kvs=kvs;
                }else {
                    this.$refs.picBrowser.updatePictures([]);
                    this.kvs=[];
                    this.loading(this,'此步骤被跳过');
                }
            },
            //提示框
            loading(that,data){
                let arr = [data];
                let str = arr.join('</br>');
                that.$message({
                    dangerouslyUseHTMLString:true,
                    message:str,
                    type:'warning'
                })
            },
            loadData(){
                var self=this;
                //从队列中获取Apply
                var storage=window.localStorage;
                var ClientKey = storage.getItem("ClientKey");
                let postData= new URLSearchParams();
                postData.append('clientKey',ClientKey);
                postData.append('index',this.index);
                self.$http.post('/qishui/base/api/apply/zlspRecord',postData).then(function (data) {
                    if(data.data.code==200 && data.data.rows){
                        self.sqId=data.data.rows[0].id;
                        self.ytj=0;
                        //取得applyData
                        let postData= new URLSearchParams();
                        postData.append('applyId',self.sqId);
                        self.$http.post('/qishui/base/api/apply/sqxx',postData
                        ).then(function (data) {
                            for(var i in data.data.rows){
                                var flow=data.data.rows[i];
                                self.applyData[flow.flowType]=flow.infos;
                            }
                            var tabItems=self.$refs.tab.items;
                            var COUNT=0;
                            for(var i in tabItems){
                                var tabItem=tabItems[i];
                                var count=0;
                                var infos=self.applyData[tabItem.type];
                                if(infos){
                                    for(var j in infos){
                                        var info=infos[j];
                                        if(info.flowFiles){
                                            for(var k in info.flowFiles){
                                                var file=info.flowFiles[k];
                                                if(file.shzt==0){
                                                    count++;
                                                    COUNT++;
                                                }
                                            }
                                        }
                                    }
                                }
                                tabItem.remain=count;
                            }
                            self.remain=COUNT;

                            if(self.$refs.tab.getActiveIndex()==0){
                                self.showFlow(self.$refs.tab.getActiveItem().type);
                            }else{
                                self.$refs.tab.activeIndex=0;
                            }
                        });
                    }else {
                        self.$router.replace({name:'ZLSHLieBiao'});
                    }

                });
                
            }
        },
        mounted() {
            var self=this;
            //初始化图片浏览器
            self.$refs.picBrowser.initPicBrowser({
                pictures:[],
                callback: {
                    onPicChange(index,pic){
                        if(pic==null){
                            self.infoId=-1;
                        }else{
                            self.infoId=pic.infoId;
                        }
                    },
                    onCrossEnd(){
                       
                        self.$refs.tab.next();
                    },
                    onCrossStart(){
                       
                        self.$refs.tab.prev();
                    }
                }
            });

            //初始化Tab
            self.$refs.tab.initTab({
                items: [
                    {title: "身份认证",type:"sfyz",remain:-1},
                    {title: "婚姻状况",type:"hyzk",remain:-1},
                    {title: "户口簿信息",type:"hkbxx",remain:-1},
                    {title: "购房发票",type:"gffp",remain:-1},
                    {title: "购房合同",type:"gfht",remain:-1},
                    {title: "授权书",type:"sqs",remain:-1},
                    {title: "拆迁补偿",type:"cqbc",remain:-1},
                ],
                callback:{
                    //当Tab切换时加载数据
                    onIndexChange(index, item) {
                        if(self.sqId==-1){
                            return;
                        }
                        self.showFlow(item.type);
                    }
                }
            });

            self.loadData();

            var el = $(this.$el);
           
            el.find(".left-top-button-btg").click(function () {
                el.find(".left-top-button-btg-reasons").slideToggle(200);
            });
        }
    }
</script>

<style scoped>
    .button {
        moz-user-select: -moz-none;
        -moz-user-select: none;
        -o-user-select: none;
        -khtml-user-select: none;
        -webkit-user-select: none;
        -ms-user-select: none;
        user-select: none;
        cursor: pointer;
    }

    .shenhe{
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        position: relative;
        width: 100%;
        height: 100%;
        padding-right: 231px;
        background-color: white;
    }

    >>> .cropper-crop-box{
        display: none;
    }

    >>> .cropper-drag-box{
        background: none;
    }

    /*------------------------------------------*/

    .right {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        position: absolute;
        top: 0px;
        right: 0px;
        width: 231px;
        height: 100%;
        padding-bottom: 60.2px;
        border: 1px solid darkgray;
        vertical-align: top;
    }

    .right-top {
        left: 0px;
        top:0px;
        width: 100%;
        height: 29px;
        line-height: 29px;
        background-color: #eceff1;
        font-size: 0px;
        text-align: right;
    }

    .back{
        display: inline-block;
        width: 72px;
        height: 20px;
        margin-right: 5px;
        line-height: 19px;
        font-size: 11px;
        border: 1px solid #dcdcdc;
        -webkit-border-radius: 0px 3.5px 3.5px 3.5px;
        -moz-border-radius: 0px 3.5px 3.5px 3.5px;
        border-radius: 0px 3.5px 3.5px 3.5px;
        text-align: center;
        cursor: pointer;
        background-color: white;
        vertical-align: middle;
    }

    .right-center {
        width: 100%;
        height: 100%;
        overflow: auto;
    }

    .right-center-content{
        margin-top: 20px;
        margin-bottom: 30px;
    }

    .right-center-content > p {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        font-size: 13px;
        color: #000000;
        padding-left: 10px;
        margin: 7px auto 12px;
    }

    .right-center-content > input {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        display: block;
        width: 180px;
        height: 32.2px;
        font-size: 11.9px;
        color: #000000;
        border: 1px solid #d7dee4;
        padding-left: 7px;
        margin: 0px auto;
        -webkit-border-radius: 0px 4.9px 4.9px 4.9px;
        -moz-border-radius: 0px 4.9px 4.9px 4.9px;
        border-radius: 0px 4.9px 4.9px 4.9px;
        margin: 0px auto;
        outline:none;
    }

    .right-bottom {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        position: absolute;
        left: 0px;
        bottom: 0px;
        width: 100%;
        height: 60px;
        line-height: 60px;
        font-size: 13px;
        text-align: center;
        background-color: #fafafa;
        color: black;
        border-top: 1px solid darkgray;
    }

    .right-bottom:hover{
        background-color: #339eff;
        color: white;
    }

    .right-bottom:active{
        background-color: #73bcfa;
        color: white;
    }

    .right-bottom.disabled{
        background-color: #fafafa;
        color: black;
        cursor: not-allowed;
    }

    /*----------------------------------------------*/

    .left {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        position: relative;
        width: 100%;
        height: 100%;
        border-left: 1px solid darkgray;
        border-bottom: 1px solid darkgray;
        vertical-align: top;
        overflow: hidden;
        padding-top: 29px;
    }

    .left-top {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        position: absolute;
        top: 0px;
        left: 0px;
        width: 100%;
        height: 29px;
        padding-right: 168px;
        background-color: #f6f9f9;
    }

    .left-top-tab{
        position: relative;
        width: 100%;
        height: auto;
    }

    .left-top-tab:hover{
        z-index: 10;
    }

    .left-top-tab::-webkit-scrollbar{
        display: none;
    }

    .left-top-button {
        position: absolute;
        right: 0px;
        top: 0px;
        z-index: 10;
        width: auto;
        height: 29px;
        text-align: right;
        font-size: 0px;
        white-space: nowrap;
    }

    .left-top-button-btg {
        position: relative;
        display: inline-block;
        width: 84px;
        height: 29px;
        line-height: 28px;
        background-color: #eeeeee;
        font-size: 12.6px;
        text-align: center;
        vertical-align: top;
    }

    .left-top-button-btg-reasons {
        display: none;
        position: absolute;
        left: 0px;
        top: 100%;
        background-color: white;
    }

    .left-top-button-btg-reasons > div {
        width: 84px;
        height: 28.7px;
        line-height: 28.7px;
    }

    .left-top-button-btg-reasons > div:hover {
        background-color: #c8c8c8;
    }

    .left-top-button-btg-reasons > div:active {
        background-color: #e2e2e2;
    }

    .left-top-button-tg {
        display: inline-block;
        width: 84px;
        height: 29px;
        line-height: 28px;
        background-color: #339eff;
        font-size: 12.6px;
        text-align: center;
        color: white;
        vertical-align: top;
    }

    .left-top-button-tg:hover{
        background-color: #2d89da;
    }

    .left-top-button-tg:active{
        background-color: #77bcfa;
    }

    .left-pic-browser {
        width: 100%;
        height: 100%;
    }

    @media only screen and (max-width: 1152px) {
        .left {
            padding-top: 58px;
        }
        .left-top {
            height: 58.8px;
        }
        .left-top-button{
            height: 58.8px;
            background-color: white;
        }
        .left-top-button-tg{
            height: 58.8px;
            line-height: 58.8px;
        }
        .left-top-button-btg{
            height: 58.8px;
            line-height: 58.8px;
        }
    }
</style>