<template>
  <a-layout id="components-layout-demo-custom-trigger">
    <IndexSider/>
    <a-layout>
      <IndexHeader/>
      <IndexContent/>
    </a-layout>
  </a-layout>
</template>

<script>
import { UserOutlined, VideoCameraOutlined, UploadOutlined, MenuUnfoldOutlined, MenuFoldOutlined } from '@ant-design/icons-vue';
import { defineComponent } from 'vue';
import IndexSider from "/@/components/IndexSider";
import IndexHeader from "/@/components/IndexHeader";
import IndexContent from "/@/components/IndexContent";
export default defineComponent({
  name: 'IndexShow',
  components: {
    IndexContent,
    IndexHeader,
    IndexSider,
    UserOutlined,
    VideoCameraOutlined,
    UploadOutlined,
    MenuUnfoldOutlined,
    MenuFoldOutlined
  },
  setup () {

  }
})

</script>

<style>
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
