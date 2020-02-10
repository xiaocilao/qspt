import Vue from 'vue'
import App from './App'
import router from './router'
// import store from './store'
//引入ElementUI
import ElementUI from 'element-ui';
import '@/assets/theme-chalk/index.css';
Vue.use(ElementUI);
//引入awesome-swiper
import vueAwesomeSwiper from 'vue-awesome-swiper'
import 'swiper/dist/css/swiper.css'
Vue.use(vueAwesomeSwiper);
//引入全局clientkey
import getClientKey from '@/lib/getClientKey'
Vue.prototype.GetClientKey = getClientKey
import axios from 'axios'
Vue.prototype.$http = axios;
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
