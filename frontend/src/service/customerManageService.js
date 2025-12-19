import api from '../utils/api'
export const customerManageService = {
    getAll(currentPage) {
        return api.get(`/api/admin/customer/?page=${currentPage}&size=10`);
    },
    getById(id) {
        return api.get(`api/admin/customer/${id}`);
    },
    update(id, customer) {
        return api.patch(`/api/admin/customer/${id}`, customer);
    },
    delete(id) {
        return api.delete(`/api/admin/customer/${id}`);
    }
};