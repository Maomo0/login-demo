<template>
    <router-view/>
</template>
<script setup>
import {onBeforeMount} from "vue";
import {tryLoginInfo} from "/@/util/api";
import store from "/@/vuex/store";

const tryInfo = async ()=>{
  const res = await tryLoginInfo();
  if (res && res.code === 200) {
    await store.dispatch("setAuthMenu", res.data)
  }
}

onBeforeMount(()=>{
  tryInfo();
})

</script>
<style >
#app, body, html{
  height: 100%;
}
</style>
