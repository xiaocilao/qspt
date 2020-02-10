<template>
  <div>
    <div>待更新</div>
  </div>

</template>
<script>
  export default {
    methods: {
      goback1() {
        this.$router.push("/index")
      }
    }
  }
</script>
<style scoped>
  .next{
    z-index: 99;
    position: fixed;
    margin: 0 auto;
    width: 95%;
    height: 2em;
    font-weight: 300;
    background-color: white;
    bottom: 0em;
    box-shadow: 0em 0em 1em 1em #F8F8F8;
  }
  .nexttext{width: 29.5em;
    position: fixed;
    bottom: 12px;
    width: 29.5em;
    width: 90%;
    right: 0px;
    height: 6%;
    left: 0;
    margin: 0 auto;
    background-color: #2c4c7f;
    line-height: normal;
    margin-top: 0.3em;
    margin-top: auto;
    text-align: center;
    table-layout: fixed;
    text-align: center;
    font-size: 13pt;
    color: white;
    border-radius: 1.3em;
  }
</style>
