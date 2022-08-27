<template>
  <a-layout-header style="background: #fff; padding: 0">
    <a-icon
        class="trigger"
        :type="flag ? 'menu-unfold' : 'menu-fold'"
        @click="handleIconClick"
    />
  </a-layout-header>
</template>

<script>
export default {
  name: 'IndexHeader',
  data () {
    return {
      flag: false
    }
  },
  methods: {
    handleIconClick(){
      this.flag = !this.flag
      this.$bus.$emit("updateCollapsed", this.flag)
    }
  },
  mounted() {

  }
}
</script>

<style scoped>

</style>
