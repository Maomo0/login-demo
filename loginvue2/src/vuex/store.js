import Vue from "vue";
import Vuex from 'vuex'
import route from '@/router/index'
import {getPermission} from "@/util/api";
import * as type from './mutation-types'
import {TOKEN} from "@/util/request";

Vue.use(Vuex)

const store = new Vuex.Store({
    state:  {
        menu: route.options.routes,
        fullPath: '',
        filterAuthMenu: [],  // 用户拥有的菜单
        username: '',
        userAuth: [],    // 用户权限
    },
    actions: {
        async setAuthMenu ({state, commit, getters}, permissionData) {
            if (permissionData && permissionData !== "") {
                console.log("tryLogin")
                const auth = permissionData.menuAuth
                let childMenu = getters.menuList
                childMenu = childMenu.filter(item => auth.indexOf(item.meta.auth.trim()) !== -1)
                commit(type.SET_USER_AUTH, auth)
                commit(type.SET_AUTH_MENU, childMenu)
                commit(type.SET_USERNAME, permissionData.username)
            }else{
                console.log("请求菜单")
                getPermission(state.username).then(res=>{
                    if (res && res.code === 200) {
                        const auth = res.data.menuAuth
                        commit(type.SET_USER_AUTH, auth)
                        let childMenu = getters.menuList
                        childMenu = childMenu.filter(item => auth.indexOf(item.meta.auth.trim()) !== -1)
                        commit(type.SET_AUTH_MENU, childMenu)
                        if (res.data.username){
                            commit(type.SET_USERNAME, res.data.username)
                        }
                    }
                })
            }
        },
        logout (context) {
            sessionStorage.removeItem(TOKEN)
            sessionStorage.removeItem("sid")
            sessionStorage.removeItem("username")
            route.push({name:"login"})
        }
    },
    mutations: {
        [type.SET_AUTH_MENU] (state, menu) {
            state.filterAuthMenu = menu
        },
        [type.SET_USERNAME] (state, username) {
            state.username = username
        },
        [type.SET_USER_AUTH] (state, auth) {
            state.userAuth = auth
            // state.userAuth.push(...auth)
        }
    },
    getters: {
        menuList (state) {
            return state.menu.filter((item) => item.name === 'home')[0].children
        },
        filterAuthMenu (state) {
            return state.filterAuthMenu
        }
    }
})
export default store
