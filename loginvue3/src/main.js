import { createApp } from 'vue';
import App from './App.vue';
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
import store from '/@/vuex/store';
import router from "/@/router/index";
import bus from "/@/common/bus";
import {Menu} from "ant-design-vue";
import * as icon from '@ant-design/icons-vue';

let app = createApp(App);
app.use(Antd);
app.use(Menu);
app.use(store);
app.use(router);
app.config.globalProperties.$bus = bus;
router.isReady().then(()=> app.mount('#app'));

for (const i in icon){
    app.component(i, icon[i])
}
