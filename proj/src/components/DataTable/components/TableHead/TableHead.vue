<template>
  <tr>
    <th v-for="column in columns">{{column.title}}</th>
  </tr>
</template>

<script>
    export default {
        name: "TableHead",
        props:["columns"]
    }
</script>

<style scoped>

</style>



// WEBPACK FOOTER //
// src/components/DataTable/components/TableHead/TableHead.vue