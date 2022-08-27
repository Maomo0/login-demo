<template>
    <a-layout-content >
<!--      <div v-show="path==='userManage'">-->
<!--        <UserManage />-->
<!--      </div>-->
<!--      <div v-show="path==='menuManage' || path==='home'">-->
<!--        <MenuManage />-->
<!--      </div>-->
      <router-view></router-view>
    </a-layout-content>
</template>

<script>
export default {
  name: 'IndexContent',
  components: {},
  data() {
    return {
      path:this.$router.history.current.name,
      menu: this.$store.state.menu,
    };
  },
  watch: {
  },
  mounted() {
    console.log(this.$router.history)
    console.log(this.$router.options)
    this.$bus.$on("changePath", (path)=>{
      this.path = path
    })
  },

}
</script>

<style scoped>
.contentStyle {
  margin: 24px 16px;
  padding: 24px;
  background: #fff;
  minHeight: 280px
}
</style>
