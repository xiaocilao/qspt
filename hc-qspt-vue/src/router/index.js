import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

import App from "@/App"
import YourInfo from "@/components/UserCenter/YourInfo"
import Help from "@/components/UserCenter/Help"
import payAgain from "@/components/UserCenter/payAgain"
import Teach from "@/components/UserCenter/Teach"
import Deal from "@/components/UserCenter/Deal"
import PostPage from "@/components/UserCenter/PostPage"
import Process from "@/components/UserCenter/Process"
import Process09 from "@/components/ProcessManage/Process09"
import Process08 from "@/components/ProcessManage/Process08"
import Process07 from "@/components/ProcessManage/Process07"
import Process06 from "@/components/ProcessManage/Process06"
import Process05 from "@/components/ProcessManage/Process05"
import Process04 from "@/components/ProcessManage/Process04"
import Process03 from "@/components/ProcessManage/Process03"
import Process02 from "@/components/ProcessManage/Process02"
import Process01 from "@/components/ProcessManage/Process01"
import Jftz from "@/components/SubPages/jftz"
import Bindbankcard from "@/components/UserCenter/Bindbankcard"
import AddCard from "@/components/UserCenter/AddCard"
import process from "@/components/ProcessManage/Process"
import index from "@/components/index"
import Login from "@/components/SubPages/Login"
import SignBoard from "@/components/CommComponents/SignBoard"
import NewsDetil from "@/components/NewsManage/NewsDetil"
import PayTest from "@/components/ProcessManage/PayTest"
import SyncFgyInfo from "@/components/BusinessHandManage/SyncFgyInfo"
import HandlePost from "@/components/YoujiPages/HandlePost";
import Agree from "@/components/BusinessHandManage/Agree"
export default new Router({
  mode: 'history',
  base: '/qishui/qsapp/',
  routes: [
    {
      path: '/',
      name: 'App',
      component: App
    },
    {
      path: '/NewsDetil',
      name: 'NewsDetil',
      component: NewsDetil
    },
    {
      path: '/SignBoard',
      name: 'SignBoard',
      component: SignBoard
    },
    {
      path: '/index',
      name: 'index',
      component: index
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/process',
      name: 'process',
      component: process
    },
    {
      path: '/UserCenter/YourInfo',
      name: 'YourInfo',
      component: YourInfo
    },
    {
      path: '/UserCenter/Help',
      name: 'Help',
      component: Help
    },
    {
      path: '/UserCenter/PostPage',
      name: 'PostPage',
      component: PostPage
    },
    {
      path: '/UserCenter/payAgain',
      name: 'payAgain',
      component: payAgain
    },
    {
      path: '/UserCenter/Teach',
      name: 'Teach',
      component: Teach
    },{
      path: '/UserCenter/Deal',
      name: 'Deal',
      component: Deal
    },
    {
      path: '/UserCenter/AddCard',
      name: 'AddCard',
      component: AddCard
    },
    {
      path: '/UserCenter/Process',
      name: 'Process',
      component: Process
    },
    {
      path: '/UserCenter/Bindbankcard',
      name: 'Bindbankcard',
      component: Bindbankcard
    },
    {
      path: '/ProcessManage/Process09',
      name: 'Process09',
      component: Process09
    },
    {
      path: '/ProcessManage/Process08',
      name: 'Process08',
      component: Process08
    },
    {
      path: '/ProcessManage/Process07',
      name: 'Process07',
      component: Process07
    },
    {
      path: '/ProcessManage/Process06',
      name: 'Process06',
      component: Process06
    },
    {
      path: '/ProcessManage/Process05',
      name: 'Process05',
      component: Process05
    },
    {
      path: '/ProcessManage/Process04',
      name: 'Process04',
      component: Process04
    },
    {
      path: '/ProcessManage/Process03',
      name: 'Process03',
      component: Process03
    },
    {
      path: '/ProcessManage/Process02',
      name: 'Process02',
      component: Process02
    },
    {
      path: '/ProcessManage/Process01',
      name: 'Process01',
      component: Process01
    },
    {
      path: '/SubPages/Jftz',
      name: 'Jftz',
      component: Jftz
    },
    {
      path: '/ProcessManage/PayTest',
      name: 'PayTest',
      component: PayTest
    },
    {
      path: '/SyncFgyInfo',
      name: 'SyncFgyInfo',
      component: SyncFgyInfo
    },
    {
      path: '/YoujiPages/HandlePost',
      name: 'HandlePost',
      component: HandlePost
    },{
      path: '/Agree',
      name: 'Agree',
      component: Agree
    }
  ]
})
