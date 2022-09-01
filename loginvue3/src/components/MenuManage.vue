<template>
  <div>
    menu
    <component :is="name"/>
  </div>
</template>

<script>
import {defineComponent, ref} from "vue";
export default defineComponent({
  setup () {
    let name = ref("UserOutlined");
    return {
      name,
    }
  }
})
</script>

<style scoped>

</style>
