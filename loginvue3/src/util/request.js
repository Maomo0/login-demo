import axios from "axios";
import {getSign, getToken, getUserName} from "/@/util/func";
import store from "/@/vuex/store";
import {AUTHORIZATION, SIGN} from "/@/util/finalName";
import {message} from "ant-design-vue";

let service = axios.create({
    baseURL: '/api',
    timeout: 60000
});

service.interceptors.request.use((config)=>{
    let token = getToken()
    let sid = getSign()
    let username = getUserName()
    if (token){
        config.headers[AUTHORIZATION] = token
    }
    if (sid){
        config.headers[SIGN] = sid
    }
    if (username){
        config.headers['username'] = username
    }
    return config
})
// 返回正常的响应码200不会执行
const err = (error=>{
    const data = error.response.data
    console.log("err", data)
    if (data){
        if (data.code === 401){
            store.dispatch("logout").then(()=>{

            })
        }
        if (data.code === 500) {
            message.error("服务异常!");
        }
    }
    return Promise.reject(error)
})

service.interceptors.response.use((response)=>{
    console.log("res", response)
    return response.data
}, err)


export {
    service as axios
}
