<template>
    <div>
        <div v-if="kk==1">
            111111111111111111
        </div>
        <div v-if="kk==2">
            222222222222222222
        </div>
        <div v-if="kk==3">
            333333333333333333
        </div>
        <div v-if="kk==4">
            444444444444444444
        </div>

    </div>
</template>

<script>
    import Msg from './msg.vue'
    export default {
        data(){
            return{
                kk:0
            }
        },
        mounted:function () {
            let _this=this;
            Msg.$on('val',function (m) {
                _this.kk=m;
            })
        }
    }
</script>

<style scoped>

</style>