import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)


const routes = [
    {
        path: '/login',
        name: 'Login',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes

})
export const setRoutes = () => {
    const manageRoute = {
        path: '/',
        name: 'Manage',
        component: () => import('../views/Manage.vue'),
        redirect: "/film",
        children: [
            {path: 'home', name: 'home', component: () => import('../views/Home')},
            {path: 'user', name: 'user', component: () => import('../views/User.vue')},
            {path: 'film', name: 'film', component: () => import('../views/Film.vue')},
            {path: 'cinema', name: 'cinema', component: () => import('../views/Cinema.vue')},
            {path: 'evaluate', name: 'evaluate', component: () => import('../views/Evaluate.vue')},
            {path: 'showings', name: 'showings', component: () => import('../views/Showings.vue')},
            {path: 'bill', name: 'bill', component: () => import('../views/Bill.vue')},
            {path: 'hall', name: 'hall', component: () => import('../views/Hall.vue')},
            {path: 'seat', name: 'seat', component: () => import('../views/Seat.vue')},
            {path: 'swiper', name: 'swiper', component: () => import('../views/Swiper.vue')},



        ]
    }
// 动态添加到现在的路由对象中去
    router.addRoute(manageRoute)
}
// 重置我就再set一次路由
setRoutes()




//路由守卫
router.beforeEach((to, from, next) => {

  localStorage.setItem("currentPathName",to.name)
    console.log("这是才进入的时候 ", to.name)
    if (!to.matched.length) {
        next("/login")
    }
  next()

})

//  这个错误是vue-router内部错误,没有进行catch处理,导致的编程式导航跳转问题,往同一地址跳转时会报错的情况。
//  解决办法
//  解决报错
const originalPush = VueRouter.prototype.push
const originalReplace = VueRouter.prototype.replace
// push
VueRouter.prototype.push = function push (location, onResolve, onReject) {
    if (onResolve || onReject) return originalPush.call(this, location, onResolve, onReject)
    return originalPush.call(this, location).catch(err => err)
}
// replace
VueRouter.prototype.replace = function push (location, onResolve, onReject) {
    if (onResolve || onReject) return originalReplace.call(this, location, onResolve, onReject)
    return originalReplace.call(this, location).catch(err => err)
}


export default router
