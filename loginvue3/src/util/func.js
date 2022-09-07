import {SID, TOKEN} from "/@/util/finalName";
import store from "/@/vuex/store";

export const getToken = ()=>{
    return sessionStorage.getItem(TOKEN)
}
export const getSign = ()=>{
    return sessionStorage.getItem(SID)
}
export const getSignAndTokenStatus = ()=>{
    // token和sid不存在需要重新登录
    return getSign() === null || getToken() === null;
}
export const getUserName = ()=>{
    return sessionStorage.getItem("username");
}

const hasPermission = (auth) =>{
    let permission = store.state.userAuth;
    return permission.includes(auth)
}

export {
    hasPermission
}

