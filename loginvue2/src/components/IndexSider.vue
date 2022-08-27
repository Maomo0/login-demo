<template>
  <a-layout-sider v-model="collapse" :trigger="null" collapsible>
    <div class="logo" />
    <template v-if="filterAuthMenu.length > 0">
      <a-menu theme="dark" mode="inline" :default-selected-keys="[0]" @click="menuClick">
        <a-menu-item v-for="(item, index) in filterAuthMenu" :key="index">
          <router-link :to="'/index/' + item.path">
            <a-icon :type="item.meta.icon" />
            <span>{{ item.meta.title }}</span>
          </router-link>
        </a-menu-item>
        <!--      <a-menu-item key="1">-->
        <!--        <a-icon type="user" />-->
        <!--        <span>nav 1</span>-->
        <!--      </a-menu-item>-->
      </a-menu>
    </template>

  </a-layout-sider>
</template>

<script>
import {mapGetters} from 'vuex'
export default {
  name: "IndexSider",
  props:{
  },
  data(){
    return{
      collapse: false,
    }
  },
  computed:{
    ...mapGetters(['menuList', 'filterAuthMenu'])
    // ...mapState(['menu'])
  },
  created() {
  },
  methods:{
    updateCollapsed(){
      this.$bus.$on("updateCollapsed", (flag)=>{
        this.collapse = flag
      })
    },
    menuClick(){
      this.$bus.$emit("changePath",this.$router.history.current.name)
      // this.$router.replace("/userManage")
    }
  },
  mounted() {
    this.updateCollapsed();
    // this.menu = this.$store.state.menu
  }
}
</script>

<style scoped>

</style>
