import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Homepage.vue";
import ProducDetail from "../views/ProductDetail.vue";
import CheckOut from "../views/Checkout.vue";
import Account from "../views/AccountManage.vue";
import Auth from "../views/Auth.vue";
import Manage from "../views/Manage.vue";


const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home
    },
    {
        path: '/products/:id',
        name: 'ProductDetail',
        component: ProducDetail
    },
    {
        path: '/checkout',
        name: 'Checkout',
        component: CheckOut
    },
    {
        path: '/account',
        name: 'Account',
        component: Account
    },
    {
        path: '/auth',
        name: 'Auth',
        component: Auth,
        meta: { hideHeaderFooter: true }
    },
    {
        path: '/manage',
        name: 'Manage',
        component: Manage,
        meta: { hideHeaderFooter: true }
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;
