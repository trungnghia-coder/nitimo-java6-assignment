import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import { createPinia } from 'pinia';
import router from './router/index.js'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'bootstrap-icons/font/bootstrap-icons.min.css'

const pinia = createPinia();
const app = createApp(App)

app.use(pinia);
app.use(router)
app.mount('#app')
