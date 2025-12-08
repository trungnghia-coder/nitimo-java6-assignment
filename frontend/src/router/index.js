import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Homepage.vue";
import ProducDetail from "../views/ProductDetail.vue";
import CheckOut from "../views/Checkout.vue";
import Account from "../views/AccountManage.vue";
import Auth from "../views/Auth.vue";
import Manage from "../views/Manage.vue";
import OrderSuccess from "../views/OrderSuccess.vue";
import { useUserStore, useFlowStore } from '../stores/userStore';
import api from '../utils/api'


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
        meta: {
            requiresAuth: true,
            requiresAdmin: true,
            hideHeaderFooter: true
        }
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

const checkCartEmptyStatus = async () => {
    try {
        const response = await api.get('/api/cart-item/is-empty');
        return response.data === true;
    } catch (err) {
        console.error('Lỗi khi kiểm tra giỏ hàng:', err);
        return true;
    }
};

router.beforeEach(async (to, from, next) => {
    const userStore = useUserStore();
    const flowStore = useFlowStore();

    if (to.meta.requiresAuth) {
        const authenticated = await isAuthenticated();

        if (!authenticated) {
            next('/auth');
            return;
        }
    }

    if (to.meta.requiresAdmin) {
        if (!userStore.isAdmin) {
            next('/403');
            return;
        }
    }

    if (to.name === 'Checkout') {
        const isCartEmpty = await checkCartEmptyStatus();
        if (isCartEmpty === true) {
            alert('Giỏ hàng của bạn đang trống. Vui lòng thêm sản phẩm trước khi thanh toán.');
            next('/');
            return;
        }
    }

    if (to.name === 'OrderSuccess') {
        if (flowStore.canAccessOrderSuccess) {
            next();
        } else {
            next('/');
        }
        return;
    }
    next();
});

router.afterEach((to) => {
    const flowStore = useFlowStore();
    if (to.name !== 'OrderSuccess') {
        flowStore.clearOrderSuccessAccess();
    }
});