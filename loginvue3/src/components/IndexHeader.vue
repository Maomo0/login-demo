<template>
  <a-layout-header style="background: #fff; padding: 0">
    <menu-unfold-outlined
        v-if="flag"
        class="trigger"
        @click="handleIconClick"
    />
    <menu-fold-outlined v-else class="trigger" @click="handleIconClick" />
  </a-layout-header>
</template>

<script >
import {ref, defineComponent, onBeforeUnmount, getCurrentInstance} from "vue";
import {MenuFoldOutlined, MenuUnfoldOutlined} from "@ant-design/icons-vue";
export default defineComponent({
  name: 'IndexContent',
  components: {
    MenuFoldOutlined,
    MenuUnfoldOutlined,
  },
  setup (props, ctx) {
    let flag = ref(false);
    const {proxy} = getCurrentInstance();
    const handleIconClick = () => {
      flag.value = !flag.value;
      console.log("click", flag.value)
      proxy.$bus.emit("updateCollapsed", flag.value);
    }

    onBeforeUnmount(()=>{
      proxy.$bus.off("updateCollapsed")
    })
    return {
      flag,
      handleIconClick
    }
  },
})

// export default {
//   name: 'IndexHeader',
//   data () {
//     return {
//       flag: false
//     }
//   },
//   methods: {
//     handleIconClick(){
//       this.flag = !this.flag
//       this.$bus.$emit("updateCollapsed", this.flag)
//     }
//   },
//   mounted() {
//   }
// }
</script>

<style scoped>

</style>
