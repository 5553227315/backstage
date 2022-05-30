import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css';
import request from "../utils/request";
import Nprogress from 'nprogress';
import 'nprogress/nprogress.css';
import VueCropper from "vue-cropper";
import {VueJsonp} from 'vue-jsonp'

Vue.use(VueCropper);
Vue.config.productionTip = false
Vue.use(ElementUI,{size:"mini"});
Vue.prototype.request=request
Vue.use(VueJsonp)


new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
