import Vuex from 'vuex'
import route from '/@/router/index.js'
import {getPermission} from '/@/util/api.js'
import * as type from './mutation-types.js'
import {TOKEN} from '/@/util/finalName'

const store = new Vuex.Store({
    state:  {
        menu: route.options.routes,
        permissionMenu: [],  // 用户拥有的菜单
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
        async logout (context) {
            sessionStorage.removeItem(TOKEN)
            sessionStorage.removeItem("sid")
            sessionStorage.removeItem("username")
            await route.push({name:"login"})
        }
    },
    mutations: {
        [type.SET_PERMISSION_MENU] (state, menu) {
            state.permissionMenu = menu
        },
        [type.SET_USERNAME] (state, username) {
            state.username = username
        },
        [type.SET_USER_AUTH] (state, auth) {
            state.userAuth = auth
        }
    },
    getters: {
        menuList (state) {
            return state.menu.filter((item) => item.name === 'home')[0].children
        },
        permissionMenu (state) {
            return state.permissionMenu
        }
    }
})
export default store
