<template>
  <tr>
    <td v-for="column in columns">{{row[column.name]}}</td>
  </tr>
</template>

<script>
    export default {
        name: "TableRow",
        props:["columns","row"]
    }
</script>

<style scoped>

</style>



// WEBPACK FOOTER //
// src/components/DataTable/components/TableRow/TableRow.vue