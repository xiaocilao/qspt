<template>
    <div>
        <div style="display: flex;justify-content: center;margin-top: 150px">
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
                        <td colspan="2">
                            <el-button style="width: 300px" type="primary" @click="doLogin">登录</el-button>
                        </td>
                    </tr>
                </table>
            </el-card>
        </div>
    </div>
</template>
<script>
    import Common from "../js/common.js"
    export default {
        data() {
            return{
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
                let postData= new URLSearchParams();
                postData.append('loginType',1);
                var that = this;
                postData.append('userName',that.user.username);
                postData.append('password', that.user.password);
                self.$http.post('/qishui/base/api/apply/login',postData).then(function (data) {
                   if(data.data.rows[0].code==='200'){
                       var storage=window.localStorage;
                       storage.removeItem("ClientKey");
                       storage.setItem("ClientKey",data.data.rows[0].ClientKey);
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

            }
        }
    }
</script>
