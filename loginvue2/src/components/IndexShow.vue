<template>
  <a-layout id="components-layout-demo-custom-trigger">
      <IndexSider />
    <a-layout>
        <IndexHeader />
<!--      <router-view ref="content">-->
      <router-view>
        <IndexContent />

      </router-view>
<!--      </router-view>-->
    </a-layout>
  </a-layout>
</template>
<script>
import IndexContent from "@/components/IndexContent";
import IndexHeader from "@/components/IndexHeader";
import IndexSider from "@/components/IndexSider";
export default {
  name: 'IndexShow',
  components: {IndexSider, IndexHeader, IndexContent},
  data() {
    return {
      collapsed: false,
    };
  },
  mounted() {
    // console.log(this.$refs.content)
  }
}
</script>

<style >
.ant-layout{
  display: flex !important;
  width: 100% !important;
  min-height: 100% !important;
}
#components-layout-demo-custom-trigger .trigger {
  font-size: 18px;
  line-height: 64px;
  padding: 0 24px;
  cursor: pointer;
  transition: color 0.3s;
}

#components-layout-demo-custom-trigger .trigger:hover {
  color: #1890ff;
}

#components-layout-demo-custom-trigger .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}
</style>
