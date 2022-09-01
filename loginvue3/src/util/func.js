import {SID, TOKEN} from "/@/util/finalName";

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

