import { defineStore } from 'pinia';

export const useUserStore = defineStore('user', {
    state: () => ({
        token: sessionStorage.getItem('authToken') || null,
        role: sessionStorage.getItem('userRole') || null,
    }),
    actions: {
        setRoleAndToken(token, role) {
            this.token = token;
            this.role = role;

            sessionStorage.setItem('authToken', token);
            sessionStorage.setItem('userRole', role);
        },
    },
    getters: {
        isAdmin: (state) => state.role === 'ADMIN',
    }
});

export const useFlowStore = defineStore('flow', {
    state: () => ({
        canAccessOrderSuccess: false,
    }),
    actions: {
        setOrderSuccessAccess(status) {
            this.canAccessOrderSuccess = status;
        },
        clearOrderSuccessAccess() {
            this.canAccessOrderSuccess = false;
        }
    }
});