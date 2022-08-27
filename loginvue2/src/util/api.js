import {axios} from '@/util/request'

export const authBaseUrl = 'http://localhost:9001/api/auth/'

export const loginFetch = (params)=>{
    return axios.post(authBaseUrl + "login", params)
}

export const tryLoginInfo = ()=>{
    return axios.get(authBaseUrl + 'tryLoginInfo')
}
export const getPermission = (username)=>{
    return axios.get(authBaseUrl + `permission/${username}`)
}
export const logOut = ()=>{
    return axios.get(authBaseUrl + 'logout')
}
