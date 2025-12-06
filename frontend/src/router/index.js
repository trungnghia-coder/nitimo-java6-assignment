import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Homepage.vue";
import ProducDetail from "../views/ProductDetail.vue";
import CheckOut from "../views/Checkout.vue";
import Account from "../views/AccountManage.vue";
import Auth from "../views/Auth.vue";
import Manage from "../views/Manage.vue";
import OrderSuccess from "../views/OrderSuccess.vue";

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
        component: CheckOut,
        meta: { requiresAuth: true }
    },
    {
        path: '/account',
        name: 'Account',
        component: Account,
        meta: { requiresAuth: true }
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
    {
        path: '/order-success',
        name: 'OrderSuccess',
        component: OrderSuccess
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

export default router;

const isAuthenticated = async () => {
    try {
        const response = await fetch('http://localhost:8080/api/auth/check-auth-status', {
            credentials: 'include'
        })
        const data = await response.json()
        return data.isAuthenticated === true
    } catch (err) {
        console.error('Error checking auth:', err)
        return false
    }
};

router.beforeEach(async (to, from, next) => {
    if (to.meta.requiresAuth) {
        const authenticated = await isAuthenticated()
        if (!authenticated) {
            next('/auth');
        } else {
            next();
        }
    } else {
        next();
    }
});