import {createRouter, createWebHashHistory} from "vue-router";

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
        component: () => import("/@/components/IndexShow"),
        children: [
            {
                path: 'menuManage',
                name: 'menuManage',
                component: () => import("/@/components/MenuManage"),
                meta: {
                    title: '菜单管理',
                    icon: "video-camera-outlined",
                    auth: 'menuAuth',
                },
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
]
export const router = createRouter({
    routes: route,
    history: createWebHashHistory(),
    strict: true,
})
export default router;

