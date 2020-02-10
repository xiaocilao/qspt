import Vue from "vue"
import Qs from "qs"
import { Loading } from 'element-ui';
import axios from 'axios';
const zhgdHost="";
var clientId = "";
function getCookie(name)
{
  var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

  if(arr=document.cookie.match(reg))
    return unescape(arr[2]);
  else
    return null;
}
export default {
  clientKey:"",
  token:"",
  httpRequest(args){
    if(!args.data){
      args.data={};
    }
    if(this.clientKey!=null && this.clientKey!=""){
      args.data.clientKey = this.clientKey;
    }
    var options={fullscreen:true,text:'加载中',body:false,target:'gocontainer',lock:true}
    let loading = Loading.service(options);
    var Common=this;
    args.url=zhgdHost+args.url;
	  args.method=args.method?args.method.toLowerCase():"post"
    if((args.method=="post" || args.method=="put" || args.method=="patch") && !args.data && args.params){
      args.data=args.params;
      args.params=undefined;
    }
    if(typeof args.data=="object"){
      args.data=Qs.stringify(args.data);
    }
    Vue.prototype.$http(args).then(function (response) {
      if(typeof args.beforeSuccess!="function" || args.beforeSuccess(response)){
        //success统一处理
        //setCookie("clientKey",getCookie("clientKey"))
        loading.close();
      }
      if(typeof args.success=="function"){
        args.success(response);
      }
    },function (value) {
      if(typeof args.beforeFail!="function" || args.beforeFail()){
        //fail统一处理
        loading.close();
      }
      if(typeof args.fail=="function"){
        args.fail();
      }
    });
  },

  setCookie: function (cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    console.info(cname + "=" + cvalue + "; " + expires);
    document.cookie = cname + "=" + cvalue + "; " + expires;
    console.info(document.cookie);
  },
  getCookie: function (cname,cookies) {
    var name = cname + "=";
    if(cookies!=null && cookies.length!=0){
      for (var i = 0; i < cookies.length; i++) {
        var c = cookies[i];
        // console.log(c)
        while (c.charAt(0) == ' ') c = c.substring(1);
        if (c.indexOf(name) != -1){
          if(c.substring(name.length, c.length)!=''&&c.substring(name.length, c.length)!=null){
            return c.substring(name.length, c.length);
          }
        }
      }
    }else{
      var ca = document.cookie.split(';');
      for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        // console.log(c)
        while (c.charAt(0) == ' ') c = c.substring(1);
        if (c.indexOf(name) != -1){
          if(c.substring(name.length, c.length)!=''&&c.substring(name.length, c.length)!=null){
            return c.substring(name.length, c.length);
          }
        }
      }
    }

    return "";
  },
  login(token,callback,tag) {
    var that = this;
    var header={
      "Content-Type":"application/json",
      "h5sysid":"6FE94B3472469D1F04907F3FC28832AE",
      "token":""
    };
    // if(token){
    //   this.token = token;
    //   header.token = token;
    //   this.httpRequest({
    //     url:"/qishui/qsapp/api/apply/jcrz",
    //     method:"POST",
    //     // headers:header,
    //     data:{
    //       // url:"http://172.64.9.10:20165/safe3d/userinfo", //生产环境
    //       url:"http://172.32.5.206:20165/safe3d/userinfo", //测试环境
    //       token:that.token,
    //       clientKey:this.clientKey
    //     },
    //     success:function (res) {
    //       var data = res.data.rows[0]
    //       if(window.localStorage){
    //         var storage=window.localStorage;
    //         storage.setItem("userId",data.userid[0]);
    //         storage.setItem("phoneNumber",data.mobile[0]);
    //         storage.setItem("name",data.name[0]);
    //       }
    //       that.httpRequest({
    //         url:"/qishui/qsapp/login",
    //         method:"POST",
    //         data:{
    //           loginType:4,
    //           userId:data.userid[0],
    //           phoneNumber:data.mobile[0],
    //           name:data.name[0]
    //         },
    //         success:function (res) {
    //           that.clientKey = res.data.msg;
    //               if(window.localStorage){
    //                 var storage=window.localStorage;
    //                 storage.setItem("clientKey",res.data.msg);
    //               }
    //           if(callback,dataset){
    //             callback();
    //           }
    //         }
    //       })
    //     },
    //     fail(e){
    //       console.log(e)
    //     }
    //   })
    // }else{
    //     var storage=window.localStorage;
    //     that.clientKey= storage.getItem("clientKey");
    //   that.httpRequest({
    //     url:"/qishui/qsapp/login",
    //     method:"POST",
    //     data:{
    //       loginType:4,
    //       userId:storage.getItem(userId),
    //       phoneNumber:storage.getItem(phoneNumber),
    //       name:storage.getItem(name)
    //     },
    //     success:function (res) {
    //       that.clientKey = res.data.msg;
    //       if(window.localStorage){
    //         var storage=window.localStorage;
    //         storage.setItem("clientKey",res.data.msg);
    //       }
    //       if(callback,dataset){
    //         callback();
    //       }
    //     }
    //   })
    // }

   // huxiaotian
    this.httpRequest({
      url: "/qishui/qsapp/login",
      method: "POST",
      header: header,
      data: {
        loginType:"4",
        userId:"123456789",
        name:"默认1",
        phoneNumber:"17711282158"
      },
      success: function (res) {
        that.clientKey=res.data.msg;
        if(!window.localStorage){
          return false;
        }else{
          var storage=window.localStorage;
          storage.setItem("clientKey",res.data.msg);
        }
      }
    })


  },
  httpRequestFail(message,response){
    Vue.prototype.$message(message);
  },
  saveToSessionStorage(key,data){
    if(!window.sessionStorage){
      return false;
    }
    window.sessionStorage[key]=JSON.stringify(data);
    return true;
  },
  getFromSessionStorage(key){
    if(!window.sessionStorage){
      return null;
    }
    try {
      return JSON.parse(window.sessionStorage[key]);
    }catch (e) {
      return null;
    }
  },
  saveToLocalStorage(key,data){
    if(!window.localStorage){
      return false;
    }
    window.localStorage[key]=JSON.stringify(data);
    return true;
  },
  getFromLocalStorage(key){
    if(!window.localStorage){
      return null;
    }
    try {
      return JSON.parse(window.localStorage[key]);
    }catch (e) {
      return null;
    }
  },
  getHeader(clientKey){
      return {
        'content-type': 'application/json',
        'cookie': "clientKey=" +clientKey
      }
    },
  gotoHelpPage(pageUrl){
    alert(pageUrl+"敬请期待！！！");
  },
  reload(that){
    var self =this;
    this.httpRequest({
      url:"/qishui/qsapp/api/apply/reload",
      method:"POST",
      data:{
        clientKey:this.clientKey
      },
      success:function (res) {
        self.loading(that,"重置成功");
      },
      fail(e){
        console.log(e)
      }
    })
  },
  FindUser(Callback,CallbackFail){
    this.httpRequest({
      url:"/qishui/qsapp/api/finduser1",
      method:"POST",
      data:{
        clientKey:this.clientKey
      },
      success:function (res) {

        console.log(res.data)
          if(res.data&&res.data!=""){
            if(Callback){
              Callback(res);
            }
          }else{
            if(CallbackFail){
              CallbackFail();
            }
          }
      },
      fail(e){
        console.log(e)
      }
    })
  },
  Sqtg(data){
    this.httpRequest({
      url: "/qishui/qsapp/api/apply/sqtg",
      method: "POST",
      data: {
        id:data.id,
        clientKey:this.clientKey
      },
    });
  },
  Spjl(callback){
    this.httpRequest({
      url:"/qishui/qsapp/api/apply/spjl",
      method:"POST",
      data:{
        clientKey:this.clientKey
      },
      success:function (res) {
        if (callback){
          console.log(res);
          //这里获取状态，如果是已支付状态就不要进这个callback，直接跳转到地址选择就行
          if(res.data&&res.data.rows&&res.data.rows[0]){
            callback(res.data.rows[0]);
          }else{
            callback();
          }
        }
      },
      fail(e){
        console.log(e)
      }
    })
  },
  insertAddress(that){
    this.httpRequest({
      url:"/qishui/qsapp/api/apply/sqsdyj",
      method:"POST",
      data:{
        sjr:that.name,
        sjhm:that.phone,
        lqdz:that.address,
        post:that.post,
        clientKey:this.clientKey
      },
      success:function (res) {
          if(res.data&&res.data.rows&&res.data.rows[0]){

            if(res.data.rows[0]=="SUCCESS"){
              that.$router.push("/index");
              that.$message({
                type:"成功",
                message: "保存成功"
              });
            }else{
              that.$message({
                type:"失败",
                message: res.data.rows[0]
              });
            }
          }
        }
    })
  },
  postGet(that){
    this.httpRequest({
      url: "/qishui/qsapp/api/apply/yqs",
      method: "POST",
      data: {
        clientKey:this.clientKey
      },
      success:function (res) {
        if(res.data&&res.data.rows&&res.data.rows[0]){
          if(res.data.rows[0]=="SUCCESS"){
            that.$message({
              type:"成功",
              message: "操作成功"
            });
          }else{
            that.$message({
              type:"失败",
              message: res.data.rows[0]
            });
          }
        }
      }
    })
  },
  getByself(that){

    var tempKey='';
    if(!window.localStorage){
      return false;
    }else{
      var storage=window.localStorage;
      tempKey = storage.getItem("clientKey");
    }
    this.httpRequest({
      url:"/qishui/qsapp/api/apply/zq",
      method:"POST",
      data:{clientKey:tempKey},
      success:function (res) {
        if(res.data&&res.data.rows&&res.data.rows[0]){
          if(res.data.rows[0]=="SUCCESS"){
            that.$router.push("/index");
            that.$message({
              type:"成功",
              message: "操作成功"
            });
          }else{
            that.$message({
              type:"失败",
              message: res.data.rows[0]
            });
          }
        }
        that.$router.push("/index");
      }
    })
  },
  upfile(response,i,index,that,callback){

    var data={};
    var viewdis=false;
    var imagedis=true;

    if(response.rows[0].flowFileId){  //装配返回图片地址
      var flowFileUrl='/qishui/qsapp/api/apply/fjxx.png?fileId='+response.rows[0].flowFileId;
    }
    if(response.rows[0].flowFileId){  //请求成功会返回图片id
      var flowFileId=response.rows[0].flowFileId;
    }
    if(response.rows[0].applyId){  //订单id
      var applyId=response.rows[0].applyId;
    }
    if(response.rows[0].flowId){  //？
      var flowId=response.rows[0].flowId;
    }
    if(response.rows[0].flowInfoId){  //?
      var flowInfoId=response.rows[0].flowInfoId
    }
    if(!that.cards[index].files[i]){  //cards不可能为空
      that.$set(that.cards[index].files,i,{border:"none",flowFileUrl:'',reson:''});
    }
    if(!that.cards[index].display[i]){ //
      that.$set(that.cards[index].display,i,{imagedis:false,viewdis:true});
    }
    if(viewdis!=null){  //怎么可能为空？？？
      that.$set(that.cards[index].display[i],'viewdis',viewdis);
    }
    if(imagedis!=null){  //怎么可能为空？？？
      that.$set(that.cards[index].display[i],'imagedis',imagedis);
    }
    if(flowFileUrl){
      that.$set(that.cards[index].files[i],'flowFileUrl',flowFileUrl);
    }else{
      that.$set(that.cards[index].files[i],'flowFileUrl','/qishui/qsapp/api/apply/fjxx.png?fileId='+that.cards[index].files[i].fileId+'&random='+Math.random());
    }
    if(flowFileId){ //声明在判断中，这种写法好吗？
      that.$set(that.cards[index].files[i],'fileId',flowFileId);
      that.$set(that.cards[index].files[i],'reson','');
    }
    if(applyId){
      that.applyId=applyId
      that.$set(that.cards[index].upfiledata,'applyId',applyId)
      that.$set(that.cards[i].upfiledata,'clientKey',this.clientKey)
    }
    if(flowId){
      that.flowId=flowId
      that.$set(that.cards[index].upfiledata,'flowId',flowId)
      try {
        that.$set(that.cards[i].upfiledata,'clientKey',this.clientKey)
      }catch (e) {
        this.loading(that,'图片读取错误，请返回主页重新进入！')
      }
    }
    if (flowInfoId) {
      that.$set(that.cards[index],'infoId',flowInfoId)
      that.$set(that.cards[index].upfiledata,'infoId',flowInfoId)
      that.$set(that.cards[index],'flowInfoId',flowInfoId);
      that.$set(that.cards[i].upfiledata,'clientKey',this.clientKey)
    }

    data.applyId=applyId;
    data.flowId=flowId;
    data.cards=that.cards;
    if(callback){
      data.ex=callback();
    }
    that.$emit('upfile',data)
  },
  delImage(that,deleteFile){
    this.httpRequest({
      url:"/qishui/qsapp/api/apply/delImage",
      method:"POST",
      data:{
        url:deleteFile.url,
        clientKey:this.clientKey
      },
      success:function (res) {
        if(that.active === 1||that.active === 3|| that.active === 4|| that.active === 6){
          that.cards[deleteFile.i].files.splice(deleteFile.index,1);
          if(deleteFile.index != 0){
            that.cards[deleteFile.i].display[deleteFile.index].viewdis=true;
            that.cards[deleteFile.i].display[deleteFile.index].imagedis=false;
          }
        }else{
          that.cards[deleteFile.i].display[deleteFile.index].viewdis=true;
          that.cards[deleteFile.i].display[deleteFile.index].imagedis=false;
        }

      }
  })
  },
  init(that,flowType,count,callback){

    var thus = this;
    this.httpRequest({
      url:"/qishui/qsapp/api/apply/lcxxlb",
      method:"POST",
      data:{
        applyId: that.applyId,
        flowType: flowType,
        clientKey:this.clientKey
      },
      success:function (res) {
        if(res.data.rows){
          var data = res.data.rows;
          for (var i = 0;i<data.length;i++){
            if(!that.cards[i]){

              that.cards.push({
                infoId:'',
                files:[{border:"none",flowFileUrl:'',reson:''}],
                upfiledata:{flowType:'sfyz',clientKey:thus.clientKey},
                data:[],
                display:[{imagedis:false,viewdis:true},{imagedis:false,viewdis:true}],
                border:"none",
                resonhidden:true,
                reson:'',
                icontype:['','','','']
              })
              for(var j=0;j<count;j++){
                that.cards[i].data.push({
                  keyValue:['']
                })
              }
            }
            that.applyId=data[i].applyId
            that.flowId=data[i].flowId
            that.cards[i].flowInfoId=data[i].id;
            if(data[i].flowFiles){
              for(var j=0;j<data[i].flowFiles.length;j++){
                if(!that.cards[i].files[j]){
                  that.$set(that.cards[i].files,j,{border:"none",flowFileUrl:'',reson:''});
                }
                if(!that.cards[i].display[j]){
                  that.$set(that.cards[i].display,j,{imagedis:false,viewdis:true});
                }
                that.$set(that.cards[i].display[j],'viewdis',false);
                that.$set(that.cards[i].display[j],'imagedis',true);
                that.$set(that.cards[i].files[j],'flowFileUrl','/qishui/qsapp/api/apply/fjxx.png?fileId='+data[i].flowFiles[j].id);
                that.$set(that.cards[i].files[j],'fileId',data[i].flowFiles[j].id);
                that.$set(that.cards[i].files[j],'reson',data[i].flowFiles[j].shyj);
                that.$set(that.cards[i].upfiledata,'flowType',flowType);
                that.$set(that.cards[i].upfiledata,'applyId',data[i].applyId)
                that.$set(that.cards[i].upfiledata,'flowId',data[i].flowId)
                that.$set(that.cards[i].upfiledata,'infoId',data[i].flowFiles[j].infoId)
                that.$set(that.cards[i].upfiledata,'clientKey',thus.clientKey)
                that.$set(that.cards[i].upfiledata,'flowInfoId',data[i].flowFiles[j].infoId);
              }
            }else if(data[i].flowInfos){
              for(var j=0;j<data[i].flowInfos.length;j++){
                that.$set(that.cards[i].upfiledata,'applyId',data[i].applyId)
                that.$set(that.cards[i].upfiledata,'flowId',data[i].flowId)
                that.$set(that.cards[i].upfiledata,'infoId',data[i].flowInfos[j].id)
                that.$set(that.cards[i].upfiledata,'clientKey',thus.clientKey)
                that.$set(that.cards[i].upfiledata,'flowInfoId',data[i].flowInfos[j].id);
              }
            }
            if(data[i].flowDatas){
              for(var k=0;k<data[i].flowDatas.length;k++){
                switch (data[i].flowDatas[k].keyName) {
                  case "婚姻状况":
                    SetInitData(that,data,i,0,k);
                    break;
                  case "结婚时间":
                    SetInitData(that,data,i,1,k);
                    break;
                  case "获取方式":
                    SetInitData(that,data,i,0,k);
                    break;
                  case "购买房屋的详细地址":
                    SetInitData(that,data,i,3,k);
                    break;
                  case "《房屋所有权证》证号":
                    SetInitData(that,data,i,2,k);
                    break;
                  case "第几套房":
                    SetInitData(that,data,i,1,k);
                    break;
                  case "真实姓名":
                    SetInitData(that,data,i,0,k);
                    break;
                  case "身份证号":
                    SetInitData(that,data,i,1,k);
                    break;
                  case "房屋面积":
                    SetInitData(that,data,i,1,k);
                    break;
                  case "房屋类型":
                    SetInitData(that,data,i,2,k);
                    break;
                  case "房屋价格":
                    SetInitData(that,data,i,0,k);
                    break;
                  case "证件类型":
                    SetInitData(that,data,i,3,k);
                    break;
                }
              }
            }
          }
        }else{
          for (var i = 0;i<that.cards.length;i++){
            that.$set(that.cards[i].upfiledata,'clientKey',thus.clientKey)
            that.$set(that.cards[i].upfiledata,'flowType',flowType);
            if(that.applyId){
              that.$set(that.cards[i].upfiledata,'applyId',that.applyId);
            }
            if(that.cards[i].length==0){
              for(var j=0;j<count;j++){
                that.cards[i].data.push({
                  keyValue:['']
                })
              }
            }
          }
        }
        if (callback){
          callback(data);
        }
      },
      fail(e){
        console.log(e)
      }
    })
  },
  add(that,callback){
    if(callback){
      callback();
    }
    that.$emit('add');
  },
  cango(that,ex){
    for(var i=0;i<that.cards.length;i++){
      for (var j=0;j<that.countValue;j++) {
        try{
          if(that.cards[i].data[j].keyValue[0]==''){
            break;
          }
        }catch (e) {
          break;
        }

      }
      for(var k=0;k<that.cards[i].files.length;k++){
        if(that.cards[i].files[k].flowFileUrl==''){
          break;
        }
      }
      if(j!=that.cards[i].data.length||k!=that.cards[i].files.length){
        break;
      }
    }
    if(i!=that.cards.length){
      that.cango=false;
    }else{
      that.cango=true;//cango是每个页面的公共条件
    }
    if(that.cards[0].data[0]&&(that.cards[0].data[0].keyValue[0]=="丧偶"||that.cards[0].data[0].keyValue[0]=="未婚")){
      that.cango=true;
    }
    if(typeof ex != "undefined"&&ex!=null){
      that.ex=ex;//ex为每个页面的独特条件
    }
    if(that.ex&&that.cango){
      that.back="#28507B"
    }else{
        that.back="#cdd4df"
    }
  },
  upValue(value,index, i,key,that,type,callback){
    var data={
      cards:{},
      ex:true
    };
    if (!that.cards[index].data[i]) {
      that.cards[index].data[i] = {};
    }
    that.$set(that.cards[index].data[i],'keyName',key);
    if(typeof value=="string"){
      that.$set(that.cards[index].data[i],'keyValue',[value]);
    }else{
      that.$set(that.cards[index].data[i],'keyValue',value);
    }
    if(type){
      switch (type) {
        case 'name':
          var name = /^[\u4e00-\u9fa5]+$/;
          if(name.test(value)){
            that.cards[index].icontype[i]='success'
          }else if(value==''){
            that.cards[index].icontype[i]=''
          }else{
            that.cards[index].icontype[i]='error'
          }
          break;
        case 'idcard':
          var idcard1 = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
          if(idcard1.test(value)){
            that.cards[index].icontype[i]='success'
          }else if(value==''){
            that.cards[index].icontype[i]=''
          } else{
            that.cards[index].icontype[i]='error'
          }
          break;
        default:
          break;
      }
    }
    data.cards=that.cards;
    if(callback){
      data.ex=callback();
    }
    that.$emit('upValue',data)
  },
  seeImg(i,index,that,tag){

    var data={}
    data.i=i;
    data.index=index;
    data.tag=tag
    that.$emit('seeImg',data)
  },
  next(that,callback){
    var thus = this;
    var data = {};

    if(that.processStep[that.active].flowType){
      data.flowType = that.processStep[that.active].flowType;
    }
    if(that.applyId){
      data.applyId = that.applyId;
    }
    if(that.flowId){
      data.flowId = that.flowId;
    }
    if(that.cards[0].data[0]&&(that.cards[0].data[0].keyValue[0]=="丧偶"||that.cards[0].data[0].keyValue[0]=="未婚")){
      data.tag=1;
    }
    var flowData = "[";
    for (var i = 0; i < that.cards.length; i++) {
      for (var j = 0; j < that.cards[i].data.length; j++) {
        var data1 = that.cards[i].data[j];
        if(data1.keyName&&data1.keyValue[0]) {
          flowData += "{"
          data1.infoId = that.cards[i].flowInfoId;
          flowData += "\"infoId\":\"" + data1.infoId + "\",\"keyName\":\"" + data1.keyName + "\",\"keyValue\":\"" + data1.keyValue[0] + "\"";
          if (data1.id) {
            flowData += ",\"id\":\"" + data1.id + "\""
          }
          flowData += "},"
        }
      }
    }
    if(flowData.substr(0,flowData.length)!="["){
      flowData=flowData.substr(0,flowData.length-1);
    }
    flowData+="]"
    console.log(flowData);
    data.flowData=flowData;
    var thus = this;
    this.httpRequest({
      url:"/qishui/qsapp/api/apply/nextflow",
      method:"POST",
      data:data,
      success:function (res) {
        if (that.active++ > 7) that.active = 0;
        for (var i = 0; i < 7; i++) {
          that.processStep[i].name = "";
        }
        that.processStep[that.active - 1].isdone = true;
        switch (that.active) {
          case 0:
            that.processStep[that.active].name = "身份验证";
            that.countValue = 2;
            break;
          case 1:
            that.processStep[that.active].name = "婚姻状况";
            that.countValue = 2;
            break;
          case 2:
            that.processStep[that.active].name = "户口簿信息";
            that.countValue = 2;
            break;
          case 3:
            that.processStep[that.active].name = "购房发票";
            that.countValue = 4
            break;
          case 4:
            that.processStep[that.active].name = "购房合同";
            that.countValue = 4
            break;
          case 5:
            that.processStep[that.active].name = "授权书";
            that.countValue = 0
            break
          case 6:
            that.processStep[that.active].name = "补偿协议";
            that.countValue = 0
            break;
          case 7:
            that.processStep[that.active].name = "缴纳税款";
            break;
        }
        that.cards=[
          {
            infoId:'',
            files:[{border:"none",flowFileUrl:'',reson:''}],
            upfiledata:{flowType:that.processStep[that.active].flowType,applyId:that.applyId,clientKey:thus.clientKey},
            data:[],
            display:[{imagedis:false,viewdis:true},{imagedis:false,viewdis:true}],
            border:"none",
            resonhidden:true,
            reson:'',
            icontype:['',''],
          }
        ]
        that.flowId=null;
        if(that.active==5){
          for (var i=0;i<3;i++) {
            // if(that.cards[0].files[i]||that.cards[0].files[i].flowFileUrl===""){
              that.$set(that.cards[0].files,i,{border:"none",flowFileUrl:that.books[i].bookname,reson:''});
              that.ex=false
            // }
          }
        }
        if(that.active==7){
          thus.httpRequest({
            url:"/qishui/qsapp/api/apply/zlspTj",
            method:"POST",
            data:{
              applyId:that.applyId,
              tgcqbc:0,
              clientKey:this.clientKey
            },
            success:function (res) {

            }
          })
        }
        for(var i=0;i<that.countValue;i++){
          that.cards[0].data.push({
            keyValue:['']
          })
        }
        thus.cango(that);
        if(callback){
          callback();
        }
      }

    })

  },
  skip(that){
    that.$emit("skip");
  },
  skip7(that){
    this.httpRequest({
      url:"/qishui/qsapp/api/apply/zlspTj",
      method:"POST",
      data:{
        applyId:that.applyId,
        tgcqbc:1
      },
      success:function (res) {
        if (that.active++ > 7) that.active = 0;
        for (var i = 0; i < 7; i++) {
          that.processStep[i].name = "";
        }
        that.processStep[that.active - 1].isdone = true;
        switch (that.active) {
          case 0:
            that.processStep[that.active].name = "身份验证";
            that.countValue = 2;
            break;
          case 1:
            that.processStep[that.active].name = "婚姻状况";
            that.countValue = 2;
            break;
          case 2:
            that.processStep[that.active].name = "户口簿信息";
            that.countValue = 2;
            break;
          case 3:
            that.processStep[that.active].name = "购房发票";
            that.countValue = 4
            break;
          case 4:
            that.processStep[that.active].name = "购房合同";
            that.countValue = 4
            break;
          case 5:
            that.processStep[that.active].name = "授权书";
            that.countValue = 3
            break
          case 6:
            that.processStep[that.active].name = "补偿协议";
            that.countValue = 0
            break;
          case 7:
            that.processStep[that.active].name = "缴纳税款";
            break;
        }
      }
    })
  },
  getSd(that){
    var _this = this;
    this.httpRequest({
      url:"/qishui/qsapp/api/apply/sdhq",
      method:"POST",
      data:{
        applyId:that.applyId,
        clientKey:this.clientKey
      },
      success:function(res){
        var data = res.data.rows[0];
        that.zsxmmc=data.zsxmmc;
        that.swjgmc=data.swjgmc;
        that.xjrq=data.skxjrq;
        that.jehjxx=data.jehjxx+"￥";
        that.yhshjxx=data.yhs+"￥";
        that.totalmoney =_this.addfunction(data.jehjxx,data.yhs)
        for(var i=0;i<that.person.length;i++){
          switch (that.person[i].name) {
            case "纳税人名称":
              that.$set(that.person[i],"text",data.nsrmc);
              break;
            case "纳税人识别号":
              that.$set(that.person[i],"text",data.nsrbh);
              break;
            case "凭证序号":
              that.$set(that.person[i],"text",data.pzxh);
              break;
            case "契税":
              that.$set(that.person[i],"text",data.jehjxx+"元");
              break;
            case "印花税":
                that.$set(that.person[i],"text",data.yhs+"元");
                break;
            case "金额合计":
              that.$set(that.person[i],"text",_this.addfunction(data.yhs,data.jehjxx));
              break;
          }
        }
      }
    })
  },
  addfunction(arg1,arg2) {
    var r1,r2,m,n;
    try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
    try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
    m=Math.pow(10,Math.max(r1,r2));
    //last modify by deeka
    //动态控制精度长度
    n=(r1>=r2)?r1:r2;
    return((arg1*m+arg2*m)/m).toFixed(n);
  },

  //huxiaotian
  pay(that){
    var obj = this;
    this.httpRequest({
      url: "/qishui/qsapp/api/apply/orderCreat",
      method: "POST",
      data: {
            // url:"http://mbsdk.tfb.cn:8081/paysdk/orderCreate", //正式环境
          url:"http://172.32.5.206:20165/paysdk/orderCreate",  //测试环境
            token:obj.token,
            applyId:that.applyId
      },
      success(res){
        try{
          if(res.data.rows[0].code=='00'){
            var url = "tfpay://key=" + encodeURIComponent(res.data.rows[0].context.key);
            window.location.href = url;
          }else{
           that.loadMessage(that,res.data.rows[0].message)
          }
        }catch (e) {
          that.loadMessage()
        }

      },
      fail(e){
        this.alertTest(this);
      }
    })
  },
  alertTest(that){
    let arr = ['支付异常！请稍后再试'];
    let str = arr.join('</br>');
    that.$message({
      dangerouslyUseHTMLString:true,
      message:str,
      type:'warning'
    })
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


  imagetest(that){
    // var that = this;
    // let formData = new FormData();
    // formData.append('flowType','sqs');
    // formData.append('tag','1');
    // // formData.append('file',new Blob());
    // formData.append('applyId','1951d6670f104f4097832a7ec16f7b6e');
    // // formData.append('flowId','53d042baaee84d2e9ed2b055feded76d');
    // formData.append('qmType',"jtzfqkcxsqs");
    // formData.append("clientKey",this.clientKey)
    // const instance=axios.create({
    //   withCredentials: true
    // })
    // instance.post('/qishui/qsapp/api/apply/tphc',formData);

    var obj = this;
    this.httpRequest({
      url: "/qishui/qsapp/api/img/sync",
      // url: "/qishui/qsapp/api/img/tphc",
      method: "POST",
      data: {
      },
      success(res){
        if(res.data.rows[0].code=='SUCCESS'){
          that.alertTest(this);
        }
      }
    })
  },
  //huxiaotian zhifu
  payNext(that){
    that.alertTest(that);
    // var url = "tfpay://key=" + encodeURIComponent("120283634");
    // window.location.href = url;
  },
  deleteAll(index,that){
    var infoId="";
    for(var i=0;i<that.cards[index].data.length;i++){
      if(that.cards[index].data[i].keyValue[0]!=""&&that.cards[index].infoId){
        infoId = that.cards[index].infoId;
        break;
      }
    }
    for(var j=0;j<that.cards[index].files.length;j++){
      if(that.cards[index].files[j].flowFileUrl!=""){
        infoId = that.cards[index].infoId;
        break;
      }
    }
    if(that.cards.length==1){
      alert("请至少保留一条信息");
    }else{
      if(i!=that.cards[index].data.length||j!=that.cards[index].files.length){
        this.httpRequest({
          url:'/qishui/qsapp/api/apply/deleteall',
          method:"POST",
          data:{
            infoId:that.cards[index].infoId
          },
          success(res) {
            that.cards.splice(index,1);
            that.$emit("deleteAll")
          }
        })
      }
      if(i==that.cards[index].data.length||j==that.cards[index].files.length){
        that.cards.splice(index,1);
        that.$emit("deleteAll")
      }
    }
  },
  queryAllNews(that,count){
    this.httpRequest({
      url:'/qishui/qsapp/api/news/getWxNews',
      method:"POST",
      data:{
        pagesize:10,
        count:count
      },
      success(res){
        console.log(res);
        var data = res.data.rows;
        for(var i=0;i<data.length;i++){
          var news = {
            id:data[i].id,
            newsimg:"https://www.intax.com.cn/qishui/qsapp/api/news/cover/" + data[i].id + ".png",
            newstitle: data[i].shortTitle,
            newstime: data[i].xtZhxgsj,
            newsknown: "0",
            newsclass: "news",
            content: data[i].title,
            url:""
          }
          that.news.push(news);
        }
      }
    })
  },
  queryNewsById(that,id){
    this.httpRequest({
      url:"/qishui/qsapp/api/news/getNewsById",
      method:"POST",
      data:{
        id:id
      },
      success(res){
        console.log(res);
        var data = res.data.rows[0];
        that.id = id;
        that.title = data.title;
        that.time = data.xtZhxgsj;
        that.content = data.content;
      }
    })
  },
  getPostNumber(callback){
    var that = this;
    this.httpRequest({
          url: "/qishui/qsapp/api/apply/getPostNumber",
          method: "POST",
          data:{
          },
      success(res) {
        if(callback){
          callback(res);
        }
      }
    })
  },
  payAgain(callback){
    var that = this;
    this.httpRequest({
      url: "/qishui/qsapp/api/apply/payAgain",
      method: "POST",
      data:{
      },
      success(res) {
        if(callback){
          callback(res);
        }
      }
    })
  },
  changPostStatus(callback){
    var that = this;
    this.httpRequest({
      url: "/qishui/qsapp/api/apply/changPostStatus",
      method: "POST",
      data:{
      },
      success(res) {
        if(callback){
          callback(res);
        }
      }
    })
  },
  getPayStatus(callback){
    var that = this;
    this.httpRequest({
      url: "/qishui/qsapp/order/apply/getPayStatus",
      method: "POST",
      data:{
      },
      success(res) {

        if(callback){
          callback(res);
        }
      }
    })
  },
  SyncFgjData(that, srcFcrmc, srcHtbah){
    this.httpRequest({
      url:"/qishui/qsapp/api/apply/fgjData",
      method:"POST",
      data:{
        appId:that.applyId,
        fcrmc:srcFcrmc,
        htbah:srcHtbah
      },
      success(res){
        if(res.data.code==500){
          alert('同步数据失败！');
        }else if (res.data.rows[0]==true){
          alert('同步数据成功！');
          that.$router.push('index');
        }else{
          alert('同步数据失败！');
        }
        console.log(res);
      }
    })
  }
}
function SetInitData(that,data,i,j,k) {
  that.$set(that.cards[i].data[j],'keyName',data[i].flowDatas[k].keyName);
  that.$set(that.cards[i].data[j],'keyValue',[data[i].flowDatas[k].keyValue]);
  that.$set(that.cards[i].data[j],'infoId',data[i].flowDatas[k].infoId);
  that.$set(that.cards[i].data[j],'flowId',data[i].flowDatas[k].flowId);
  that.$set(that.cards[i].data[j],'applyId',data[i].flowDatas[k].applyId);
  that.$set(that.cards[i].data[j],'id',data[i].flowDatas[k].id);
}
