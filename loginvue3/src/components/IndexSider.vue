<template>
  <a-layout-sider :collapsed="collapse" :trigger="null" collapsible>
    <div class="logo" />
    <template v-if="permissionMenu.length > 0">
      <a-menu theme="dark" mode="inline"  :selectedKeys="selectKeys" @click="menuClick">
        <a-menu-item v-for="(item, index) in permissionMenu" :key="index + 1 + ''">
          <router-link :to="'/index/' + item.path">
            <component :is="item.meta.icon"/>
            <span>{{ item.meta.title }}</span>
          </router-link>
        </a-menu-item>
      </a-menu>
    </template>
  </a-layout-sider>
</template>

<script>
import {ref, defineComponent, onMounted, getCurrentInstance, computed} from "vue";
import { useRouter} from "vue-router";
import {useStore} from "vuex";
export default defineComponent({
  name: "IndexSider",
  components: {

  },
  setup () {
    let collapse = ref(false);
    let selectKeys = ref(['1']);
    const store = useStore()
    const {proxy} = getCurrentInstance();
    const router = useRouter();
    const updateCollapsed = () => {
      proxy.$bus.on("updateCollapsed", (flag)=>{
        console.log("on", flag)
        collapse.value = flag;
      })
    }
    const menuClick = (e) =>{
      selectKeys.value = [e.key]
      proxy.$bus.emit("changePath", router.currentRoute)
    }
    const permissionMenu = computed(()=> store.getters.menuList)
    onMounted(()=>{
      console.log(permissionMenu)
      updateCollapsed();
    })
    return {
      collapse,
      selectKeys,
      permissionMenu,
      menuClick
    }
  }
})
</script>

<style scoped>

</style>
