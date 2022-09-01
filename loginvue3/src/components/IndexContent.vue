<template>
    <a-layout-content class="contentStyle">
      <router-view/>
    </a-layout-content>
</template>

<script>
import {defineComponent, ref, getCurrentInstance} from "vue";
import {AppleOutlined} from '@ant-design/icons-vue';
import {useRouter} from "vue-router";
export default defineComponent({
  name: 'IndexContent',
  components: {AppleOutlined},
  setup () {
    const router = useRouter();
    const {proxy} = getCurrentInstance();
    let path = ref(router.currentRoute.value.name)
    proxy.$bus.on("changePath", (param)=>{
      path.value = param.value.name
    })
    return {
      path
    }
  }
})
</script>

<style scoped>
.contentStyle {
  margin: 24px 16px;
  padding: 24px;
  background: #fff;
  minHeight: 280px
}
</style>
