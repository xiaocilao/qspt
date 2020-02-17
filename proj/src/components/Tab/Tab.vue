<template>
    <div class="tab-root">
        <div class="tab-item" v-for="(item,index) in items" :class="index==activeIndex?'active':''"
             @click="activeIndex=index">
            {{item.title}}{{item.remain>=0?'('+item.remain+')':''}}
            <div></div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Tab",
        data() {
            return {
                activeIndex: -1,
                callback: {},
                items: []
            }
        },
        watch: {
            activeIndex(val,old) {
                if (typeof this.callback.onIndexChange == "function") {
                    this.callback.onIndexChange(val, this.items[val]);
                }
            }
        },
        methods: {
            initTab(data) {
                if(data.callback){
                    this.callback.onIndexChange=data.callback.onIndexChange;
                }
                var oldIndex=this.activeIndex;
                this.items = data.items;
                this.activeIndex = 0;
                if(oldIndex==0 && typeof this.callback.onIndexChange == "function"){
                    this.callback.onIndexChange(0, this.items[0]);
                }
            },
            getActiveIndex() {
                return this.activeIndex;
            },
            getActiveItem() {
                if(this.activeIndex==-1){
                    return null;
                }
                return this.items[this.activeIndex];
            },
            next(){
                if(this.activeIndex+1<this.items.length){
                    this.activeIndex++;
                }
            },
            prev(){
                if(this.activeIndex-1>=0){
                    this.activeIndex--;
                }
            }
        }
    }
</script>

<style scoped>
    .tab-root{
        height: 29.4px;
        min-width: 340px;
        font-size: 0px;
    }

    .tab-item {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
        position: relative;
        display: inline-block;
        width: 85px;
        height: 29.4px;
        line-height: 28px;
        border-right: 0.7px solid darkgray;
        border-top: 0.7px solid darkgray;
        border-bottom: 0.7px solid darkgray;
        font-size: 11px;
        text-align: center;
        vertical-align: top;

        -moz-user-select: none;
        -o-user-select: none;
        -khtml-user-select: none;
        -webkit-user-select: none;
        -ms-user-select: none;
        user-select: none;
        cursor: pointer;
    }

    .tab-item > div {
        position: absolute;
        left: 50%;
        top: 0px;
        width: 0%;
        height: 2.1px;
        background-color: #339eff;
        transition: all 0.2s;
        -moz-transition: all 0.2s;
        -webkit-transition: all 0.2s;
        -ms-transition: all 0.2s;
    }

    .tab-item.active > div {
        left: 0px;
        width: 100%;
    }
</style>