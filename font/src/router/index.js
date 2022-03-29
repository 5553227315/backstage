import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)


const routes = [
  {
    path: '/',
    name: 'Manage',
    component: () => import('../views/Manage.vue'),
    redirect: "/home",
    children: [
      {path: 'home', name: 'home', component: () => import('../views/Home')},
      {path: 'user', name: 'user', component: () => import('../views/User.vue')},
      {path: 'film', name: 'film', component: () => import('../views/Film.vue')},
      {path: 'cinema', name: 'cinema', component: () => import('../views/Cinema.vue')},
      {path: 'evaluate', name: 'evaluate', component: () => import('../views/Evaluate.vue')},
      {path: 'showings', name: 'showings', component: () => import('../views/Showings.vue')},
      {path: 'bill', name: 'bill', component: () => import('../views/Bill.vue')},

    ]
  },
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

export default router
