import Vue from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './vuex/store'
import Antd from 'ant-design-vue'
import {Menu} from "ant-design-vue";
import 'ant-design-vue/dist/antd.css'
import {getSignAndTokenStatus} from "@/util/func";

Vue.config.productionTip = false
Vue.use(Antd)
Vue.use(Menu)



new Vue({
  beforeCreate () {
    Vue.prototype.$bus = this
  },
  store,
  router,
  render: h => h(App),
  beforeMount() {
  },
  created() {
  },
  beforeDestroy () {
  },
  mounted() {
  }
}).$mount('#app')

router.beforeEach((to, from, next)=>{
  // session中不存在token或sid重定向到登录页
  if (getSignAndTokenStatus() && to.name !== "login"){
    next({name: "login"})
  }
  if (!to.name){
    next({name: 'notFound'})
  }
  const authMenu = store.state.filterAuthMenu
  const auth = store.state.userAuth
  if (authMenu.length > 0) {
    if(to.meta && to.meta.auth){
      if (!auth.includes(to.meta.auth)){
        next({name: 'unauthorized'})
      }
    }
    if(to.name === 'home') {
      let homeMenu = store.state.menu.filter(item => item.name==='home');
      if(homeMenu[0].children.length > 0){
        next({name: homeMenu[0].children[0].name})
      }
    }
  //   authMenu.forEach(item=>{
  //     if(to.name === item.name){
  //       if (item.meta.auth && auth.indexOf(item.meta.auth) === -1) {
  //         next({name: 'unauthorized'})
  //       }
  //     }
  //   })
  }
  next();
})

