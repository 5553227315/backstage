import axios from 'axios'
import ElementUI from "element-ui";
import {serverIp} from "../public/config";
import Vue from "vue";
import router from "../src/router";
import App from "../src/App";
import Nprogress from 'nprogress';
import 'nprogress/nprogress.css';

const request = axios.create({
    // 注意！！ 这里是全局统一加上了 '/api' 前缀，也就是说所有接口都会加上'/api'前缀在，
    // 页面里面写接口的时候就不要加 '/api'了，否则会出现2个'/api'，类似 '/api/api/user'这样的报错，切记！！！
    baseURL: `http://${serverIp}:9090`,

    timeout: 60000
})


var vm = new Vue({
    router,
    render: h => h(App)
}).$mount('#app')


// request 拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    let token = localStorage.getItem("token") ? localStorage.getItem("token") : null
    if (token){
        config.headers['token'] = token;  // 设置请求头

    }
    //请求开始时显示进度条
    Nprogress.start();

    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;

        // 如果是返回的文件
        if (response.config.responseType === 'blob') {
            return res
        }
        // 兼容服务端返回的字符串数据
        if (typeof res === 'string') {
            //原始会对2的53次方的数据全部省略后面的内容，导致id不一致
            res = res ? JSON.parse(res) : res
            // //解决办法
            // res = res ? JSON.parse(res) : res
        }
        //当用户权限不通过的时候给出提示
        if(res.code==='401'){

            ElementUI.Message({
                message:res.msg,
                type:'error'
            })
            vm.$router.push("/login")
        }
        if(res.code==='404'){
            ElementUI.Message({
                message:res.msg,
                type:'error'
            })
        }
        //服务响应时完成进度条
        Nprogress.done()
        return res;
    },
    error => {
        console.log('err' + error) // for debug
        return Promise.reject(error)
    }
)



export default request

