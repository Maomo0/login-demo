<template>
  <a-layout-sider :collapsed="collapse" :trigger="null" collapsible>
    <div class="logo" />
      <a-menu theme="dark" mode="inline"  :selectedKeys="selectKeys" @click="menuClick">
        <template v-for="item in permissionMenu" :key="item.name">
            <template v-if="!item.children">
                <a-menu-item :key="item.name">
                  <template #icon>
                    <component :is="collapse?'frown-outlined': 'smile-outlined'"/>
                  </template>
                  {{ item.meta.title }}
                </a-menu-item>
            </template>
            <template v-else>
              <SubMenuItem :key="item.name" :menu-info="item" :collapsed="collapse"/>
            </template>
        </template>
      </a-menu>
  </a-layout-sider>
</template>

<script>
import {ref, defineComponent, onMounted, getCurrentInstance, computed, toRef, reactive} from "vue";
import { useRouter} from "vue-router";
import {useStore} from "vuex";
import {SmileOutlined} from '@ant-design/icons-vue'
import SubMenuItem from "/@/components/SubMenuItem";
export default defineComponent({
  name: "IndexSider",
  components: {
    SubMenuItem
  },
  setup () {
    const router = useRouter();
    const store = useStore();
    const state = reactive({
      selectKeys: [router.currentRoute.value.name]
    })
    let collapse = ref(false);
    let selectKeys = toRef(state, 'selectKeys');
    const {proxy} = getCurrentInstance();
    const updateCollapsed = () => {
      proxy.$bus.on("updateCollapsed", (flag)=>{
        console.log("on", flag)
        collapse.value = flag;
      })
    }
    const menuClick = (e) =>{
      selectKeys.value = [e.key];
      router.push({name: e.key});
      proxy.$bus.emit("changePath", router.currentRoute)
    }
    const permissionMenu = computed(()=> store.getters.permissionMenu)
    onMounted(()=>{
      updateCollapsed();
    })
    return {
      collapse,
      selectKeys,
      permissionMenu,
      menuClick,
      SmileOutlined,
      list
    }
  }
})
const list = [
  {
    key: '1',
    title: 'Option 1',
  },
  {
    key: '2',
    title: 'Navigation 2',
    children: [
      {
        key: '2.1',
        title: 'Navigation 3',
        children: [{ key: '2.1.1', title: 'Option 2.1.1' }],
      },
    ],
  },
];
</script>

<style scoped>

</style>
