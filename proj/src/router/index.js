import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Main from '../views/Main.vue'
import ShangChuan from '../components/ShuiDanShangChuan/components/ShangChuan/ShangChuan.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/Main',
    name: 'Main',
    component: Main
  },
  {
    path: '/ShangChuan',
    name: 'ShangChuan',
    component: ShangChuan
  },
]

const router = new VueRouter({
  mode: 'history',
  base: '/qishui/base/',
  // base: process.env.BASE_URL,
  routes
})

export default router
