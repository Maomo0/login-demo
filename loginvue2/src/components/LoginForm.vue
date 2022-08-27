<template>
  <a-form
      id="components-form-demo-normal-login"
      :form="form"
      class="login-form"
      @submit="handleSubmit"
  >
    <a-form-item>
      <a-input
          v-decorator="[
          'username',
          { rules: [{ required: true, message: 'Please input your username!' }] },
        ]"
          placeholder="Username"
      >
        <a-icon slot="prefix" type="user" style="color: rgba(0,0,0,.25)" />
      </a-input>
    </a-form-item>
    <a-form-item>
      <a-input
          v-decorator="[
          'password',
          {
            rules: [
              { required: true, message: 'Please input your Password!' }
            ]
          },
        ]"
          type="password"
          placeholder="Password"
      >
        <a-icon slot="prefix" type="lock" style="color: rgba(0,0,0,.25)" />
      </a-input>
      <div v-show="msg"><span style="color: red">{{ msg }}</span></div>
    </a-form-item>
    <a-button type="primary" html-type="submit" class="login-form-button" :loading="btnLoading">
        Log in
      </a-button>
  </a-form>
</template>

<script>
import {TOKEN} from '@/util/request'
import {loginFetch} from "@/util/api"
import {mapMutations, mapActions} from 'vuex'
import {SET_USERNAME} from '@/vuex/mutation-types'

export default {
  name: "LoginForm",
  beforeCreate() {
    this.form = this.$form.createForm(this, { name: 'normal_login' });
  },
  data() {
    return {
      btnLoading: false,
      msg: '',
    }
  },
  methods: {
    ...mapActions(["setAuthMenu"]),
    ...mapMutations([SET_USERNAME]),
    handleSubmit(e) {
      e.preventDefault();
      this.btnLoading = true;
      this.form.validateFields((err, values) => {
        if (!err) {
          loginFetch(values).then(res=>{
            if(res.code === 200) {
              console.log(res)
              sessionStorage.setItem(TOKEN, res.data.token)
              sessionStorage.setItem("sid", res.data.sid)
              sessionStorage.setItem("username", values.username)
              this.SET_USERNAME(values.username)
              this.setAuthMenu("")
              this.msg = '';
              this.$router.push('/index');
            }else{
              this.msg = res.message === null? '': res.message;
            }
            this.btnLoading = false
          })
        }
      });
    },
  },
}
</script>

<style scoped>
#components-form-demo-normal-login .login-form {
  max-width: 300px;
}
#components-form-demo-normal-login .login-form-forgot {
  float: right;
}
#components-form-demo-normal-login .login-form-button {
  width: 100%;
}
</style>
