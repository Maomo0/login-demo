<template>
  <div>
    menu2
  </div>
</template>

<script>
import {defineComponent, ref, onMounted, watch} from "vue";
import {useRouter} from "vue-router";
export default defineComponent({
  setup () {
    let name = ref("UserOutlined");
    let router = useRouter();
    let routerName = ref(router.currentRoute.value.name);
    onMounted(()=>{
      routerName = router.currentRoute.value.name;
    })

    return {
      name,
      routerName
    }
  }
})
</script>

<style scoped>

</style>
