<template>
  <div>
    <slot></slot>
  </div>

</template>

<script>
import {mapGetters} from "vuex";
import UserManage from "@/components/UserManage";
import MenuManage from "@/components/MenuManage";

export default {
  name: "show-content",
  components: {MenuManage, UserManage},
  computed:{
    ...mapGetters(['menuList'])
  }
}
</script>

<style scoped>

</style>
