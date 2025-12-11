import api from '../utils/api'
export const productManageService = {
    getAll(currentPage) {
        return api.get(`/api/admin/product/?page=${currentPage}&size=10`);
    },
    getById(id) {
        return api.get(`api/admin/product/${id}`);
    },
    create(product) {
        return api.post('api/admin/product/create', product);
    },
    update(id, product) {
        return api.patch(`/api/admin/product/${id}`, product);
    },
    delete(id) {
        return api.delete(`/api/admin/product/${id}`);
    }
};