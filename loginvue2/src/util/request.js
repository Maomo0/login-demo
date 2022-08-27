import axios from "axios";
import {getSign, getToken, getUserName} from "@/util/func";
import store from "@/vuex/store";
import router from "@/router/index"

const AUTHORIZATION = 'Authorization'
export const TOKEN = 'access_token'
export const SIGN = 'Sign'
export const SID = "sid"

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
const err = (error=>{
    const data = error.response.data
    console.log(data)
    if (data){
        if (data.code === 401){
            store.dispatch("logout").then(()=>{
                // setTimeout(()=>{
                //     window.location.reload()
                // }, 1500)
            })
        }
    }
    return Promise.reject(error)
})

service.interceptors.response.use((response)=>{
    return response.data
}, err)



export {
    service as axios
}
