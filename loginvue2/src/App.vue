<template>
  <div id="app">
<!--    <HelloWorld msg="Welcome to Your Vue.js App"/>-->
    <router-view>
      <LoginForm/>
      <IndexShow/>
      <not-found/>
      <http-status/>
    </router-view>
  </div>
</template>

<script>
import LoginForm from "@/components/LoginForm";
import IndexShow from '@/components/IndexShow'
import NotFound from "@/components/404";
import HttpStatus from "@/components/403";
import {tryLoginInfo} from "@/util/api";
import store from "@/vuex/store";

export default {
  name: 'App',
  components: {
    HttpStatus,
    NotFound,
    LoginForm,
    IndexShow
  },
  created() {
    this.tryInfo()
  },
  methods: {
    tryInfo () {
      tryLoginInfo().then(async res => {
        if (res && res.code === 200) {
          await store.dispatch("setAuthMenu", res.data)
        }
      })
    }
  },
  mounted() {
  }
}
</script>

<style>
#app, body, html{
  height: 100%;
}
.contentStyle {
  margin: 24px 16px;
  padding: 24px;
  background: #fff;
  minHeight: 280px;
  width: 95%;
  height: 90%;
}

</style>
