<template>
  <a-result status="403" title="403" sub-title="Sorry, you are not authorized to access this page.">
    <template #extra>
      <a-button type="primary" @click="backHome">
        Back Home
      </a-button>
    </template>
  </a-result>
</template>

<script>
export default {
  name: "httpStatus",
  methods: {
    backHome () {
      this.$router.push({name: "home"})
    }
  }
}
</script>

<style scoped>

</style>
