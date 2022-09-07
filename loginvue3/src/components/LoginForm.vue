<template>
  <div id="loginStyle">
    <a-card title="登录" :head-style="{textAlign: 'center'}">
      <div id="loginForm">
        <a-form
            ref="loginForm"
            :model="formState"
            name="basic"
            :label-col="{ span: 8 }"
            :wrapper-col="{ span: 16 }"
            autocomplete="off"
            @finish="onFinish"
            @finishFailed="onFinishFailed"
        >
          <a-form-item
              label="Username"
              name="username"
              :rules="[{ required: true, message: 'Please input your username!' }]"
          >
            <a-input v-model:value="formState.username"  style="width: 220px"/>
          </a-form-item>

          <a-form-item
              label="Password"
              name="password"
              :rules="[{ required: true, message: 'Please input your password!' }]"
          >
            <a-input-password v-model:value="formState.password" style="width: 220px"/>
            <div v-show="formState.msg"><span style="color: red">{{ formState.msg }}</span></div>
          </a-form-item>
          <a-form-item :wrapper-col="{ offset: 8, span: 16, width: 220 }">
            <a-button type="primary" html-type="submit" :loading="formState.btnLoading" :style="{width: '220px'}">Submit</a-button>
          </a-form-item>
        </a-form>
      </div>
    </a-card>
  </div>
</template>

<script>
import { reactive, defineComponent } from 'vue';
import {loginFetch} from "/@/util/api";
import {SET_USERNAME} from "/@/vuex/mutation-types";
import {SID, TOKEN} from "/@/util/finalName";
import store from "/@/vuex/store";
import router from "/@/router/index"

export default defineComponent({
  name: 'LoginFrom',
  setup () {
    return {
      formState,
      onFinish,
      onFinishFailed,
    }
  }
})
let formState = reactive({
  username: '',
  password: '',
  btnLoading: false,
  msg: ''
});
const onFinish = async values => {
  formState.btnLoading = true;
  const res = await loginFetch(values)
  if (res.code === 200){
    if (res.data) {
      sessionStorage.setItem(TOKEN, res.data.token)
      sessionStorage.setItem(SID, res.data.sid)
      sessionStorage.setItem("username", values.username)
      store.commit(SET_USERNAME, values.username)
      formState.msg = ''
      await store.dispatch("setAuthMenu", '')
      await router.push({name: 'home'})
    }
  }else {
    formState.msg = res.message
  }
  formState.btnLoading = false;
  formState.msg = '登录成功'
};
const onFinishFailed = errorInfo => {
  console.log('Failed:', errorInfo);
};

</script>

<style scoped>
#loginStyle{
  display: flex;
  background-color: #f5f5f5;
  width: 100%;
  height: 100%;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
#loginForm{
  margin-right: 36px;
  text-align: center;
}
</style>
