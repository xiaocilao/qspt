<template>
     <actionsheet v-model="show3" :menus="menus3" @on-click-menu-delete="onDelete" show-cancel></actionsheet>
</template>
<script>
import { Actionsheet } from 'vux'
export default {
    components:{
        Actionsheet
    },
    data(){
        return{
              show3: false,
               menus3: {
                'title.noop': this.$t('Actionsheet header'),
                delete: '<span style="color:red">Delete</span>'
               },
        }
    },
    methods:{
        onDelete () {
            this.showSuccess = true
            }
    }
}
</script>
