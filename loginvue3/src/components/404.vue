<template>
  <a-result status="404" title="403" sub-title="Sorry, you are not authorized to access this page.">
    <template #extra>
      <a-button type="primary" @click="backHome">
        Back Home
      </a-button>
    </template>
  </a-result>
</template>

<script>
import {defineComponent} from "vue";
import {useRouter} from "vue-router";
export default defineComponent({
  name: "notFound",
  setup () {
    const router = useRouter();
    const backHome = ()=>{
      router.push({name: 'home'});
    }
    return {
      backHome
    }
  },
})
</script>

<style scoped>

</style>
