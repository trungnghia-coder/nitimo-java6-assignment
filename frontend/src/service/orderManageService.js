import api from '../utils/api'
export const orderManageService = {
    getAll(currentPage) {
        return api.get(`/api/admin/order?page=${currentPage}&size=10`);
    },
    getById(id) {
        return api.get(`api/admin/order/${id}`);
    },
    update(id, order) {
        return api.patch(`/api/admin/order/${id}`, order);
    },
    delete(id) {
        return api.delete(`/api/admin/order/${id}`);
    }
};