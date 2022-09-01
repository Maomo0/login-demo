<template>
    <router-view/>
</template>
<script >
import {defineComponent, onMounted} from "vue";
import LoginForm from "/@/components/LoginForm";
import IndexShow from "/@/components/IndexShow";
import {useRouter} from "vue-router";
export default defineComponent({
  name: 'App',
  components: {
    LoginForm,
    IndexShow
  },
  setup () {
    const router = useRouter();

    onMounted(()=>{
      console.log(router.currentRoute.value)
    })
  }
})

</script>
<style >
#app, body, html{
  height: 100%;
}
</style>
