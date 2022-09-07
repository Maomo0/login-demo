import {createRouter, createWebHashHistory} from "vue-router";
import store from "/@/vuex/store";
import {getSignAndTokenStatus} from "/@/util/func";
import IndexShow from "/@/components/IndexShow";

export const route = [
    {
        path: '/',
        name: 'login',
        component: () => import("/@/components/LoginForm")
    },
    {
        path: '/login',
        redirect: '/'
    },
    {
        path: '/403',
        name: 'unauthorized',
        component: () => import("/@/components/403")
    },
    {
        path: '/404',
        name: 'notFound',
        component: () => import("/@/components/404")
    },
    {
        path: '/index',
        name: 'home',
        component: IndexShow,
        redirect: {name: 'menuManage'},
        children: [
            {
                path: 'menuManage',
                name: 'menuManage',
                // component: IndexShow,
                meta: {
                    title: '菜单管理',
                    icon: "video-camera-outlined",
                    auth: 'menuAuth',
                    hidden: false,
                },
                redirect: {name: 'menuManage2'},
                children: [
                    {
                        path: 'menuManage2',
                        name: 'menuManage2',
                        component: () => import("/@/components/MenuManage"),
                        meta: {
                            title: '菜单管理2',
                            icon: "video-camera-outlined",
                            auth: 'menuAuth',
                        },
                    },
                    {
                        path: 'menuManage3',
                        name: 'menuManage3',
                        component: () => import("/@/components/MenuManage3"),
                        meta: {
                            title: '菜单管理3',
                            icon: "video-camera-outlined",
                            auth: 'menuAuth',
                        },
                    },
                ]
            },
            {
                path: 'userManage',
                name: 'userManage',
                component: () => import("/@/components/UserManage"),
                meta: {
                    title: '用户管理',
                    icon: "UserOutlined",
                    auth: 'userAuth',
                },
            }
        ]
    }
];

export const router = createRouter({
    routes: route,
    history: createWebHashHistory(),
    strict: true,
});

router.beforeEach((to, from , next) =>{
    if (getSignAndTokenStatus() && to.name !== "login"){
        next({name: "login"})
    }
    if (!to.name){
        next({name: 'notFound'})
    }
    const authMenu = store.state.permissionMenu
    const auth = store.state.userAuth
    if (authMenu.length > 0) {
        if(to.meta && to.meta.auth){
            if (!auth.includes(to.meta.auth)){
                next({name: 'unauthorized'})
            }
        }
        // if(to.name === 'home') {
        //     let homeMenu = store.state.menu.filter(item => item.name==='home');
        //     if(homeMenu[0].children.length > 0){
        //         next({name: homeMenu[0].children[0].name})
        //     }
        // }
    }
    next();
})
export default router;

