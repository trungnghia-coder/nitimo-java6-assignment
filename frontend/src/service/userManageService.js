import api from '../utils/api'
export const userManageService = {
    getAll(currentPage) {
        return api.get(`/api/admin/user/?page=${currentPage}&size=10`);
    },
    create(user) {
        return api.post(`/api/admin/user/create`, user);
    },
    getById(id) {
        return api.get(`api/admin/user/${id}`);
    },
    update(id, user) {
        return api.patch(`/api/admin/user/${id}`, user);
    },
    delete(id) {
        return api.delete(`/api/admin/user/${id}`);
    }
};