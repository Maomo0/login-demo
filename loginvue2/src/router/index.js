import Vue from 'vue'
import Router from "vue-router"
import VueRouter from "vue-router";
Vue.use(Router)

export default  new Router({
    routes:[
        {
            path: '/',
            name: 'login',
            component: ()=>import("@/components/LoginForm")
        },
        {
            path: '/login',
            redirect: '/'
        },
        {
            path: '/403',
            name: 'unauthorized',
            component: ()=> import("@/components/403")
        },
        {
            path: '/404',
            name: 'notFound',
            component: ()=> import("@/components/404")
        },
        {
            path: '/index',
            name: 'home',
            component: ()=> import("@/components/IndexShow"),
            children: [
                {
                    path: 'menuManage',
                    name: 'menuManage',
                    component: ()=> import("@/components/MenuManage"),
                    meta: {
                        title: '菜单管理',
                        icon: 'video-camera',
                        auth: 'menuAuth',
                    },
                },
                {
                    path: 'userManage',
                    name: 'userManage',
                    component: ()=>import("@/components/UserManage"),
                    meta:{
                        title: '用户管理',
                        icon: 'user',
                        auth: 'userAuth',
                    },
                }
            ]
        }
    ]
})

const origin = VueRouter.prototype.push
VueRouter.prototype.push = function push(location, onResolve, onReject){
    if (onReject || onReject) return origin.call(this, location, onResolve, onReject)
    return origin.call(this, location).catch(err=>err)
}
