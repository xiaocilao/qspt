<template>
    <div class="main">
        <div class="left">
            <Left></Left>
        </div>
        <div class="right">
            <div>
                <div class="top">
                    <img src="img/timg.jpg">
                </div>
                <div class="buttom">
                    <Right></Right>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import Left from '../components/Left.vue'
    import Right from '../components/Right.vue'
    export default {
        name: "First",
        components:{
            Left,
            Right
        }
    }
</script>

<style type="text/css">
    *{
        padding:0;
        margin:0;
    }
    .left{
        width: 100px;
        float: left;
        margin-right: 10px;
    }
    .right{
        width:1000px;
        float: left;
        margin-left: 10px;
    }
    .main{
        width:12000px;
        margin: 20px auto;
    }
    .top img{
        height: 200px;
        width: 1000px;
    }
    .left,.right{
        background-color: red;
        height: 500px;
    }
</style>