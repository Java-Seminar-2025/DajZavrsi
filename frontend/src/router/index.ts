import { createRouter, createWebHistory } from 'vue-router'

import Home from '@/Home.vue'
import Login from '@/users/Login.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', component: Home, },
    { path: '/users/login', component: Login, },
],
})

export default router
