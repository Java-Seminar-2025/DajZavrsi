import { createRouter, createWebHistory } from 'vue-router'

import AssignmentInfoView from '@/assignments/AssignmentInfoView.vue'
import Home from '@/Home.vue'
import Login from '@/users/Login.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        { path: '/', component: Home, },
        { path: '/users/login', component: Login, },
        { path: '/assignments/:uuid', component: AssignmentInfoView, props: true, },
    ],
})

export default router
