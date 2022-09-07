<template>
  <a-sub-menu :key="menuInfo.name">
      <template #icon>
        <component :is="collapsed?'frown-outlined': 'smile-outlined'"/>
      </template>
      <template #title>{{ menuInfo.meta.title }}</template>
      <template v-for="item in menuInfo.children" :key="item.name">
        <template v-if="!item.children && hasPermission(item.meta.auth)">
            <a-menu-item :key="item.name">
              <template #icon>
                <component v-if="item.meta.icon" :is="item.meta.icon"></component>
                <component v-else is="smile-outlined"/>
              </template>
              {{ item.meta.title }}
            </a-menu-item>
        </template>
        <template v-else-if="hasPermission(item.meta.auth)">
            <SubMenuItem :menu-info="item" :key="item.name" />
        </template>
      </template>
  </a-sub-menu>
</template>

<script setup>
import {hasPermission} from "/@/util/func";
const {menuInfo, collapsed} = defineProps(['menu-info', 'collapsed'])
</script>

<style scoped>

</style>
