import { ref } from 'vue';
import { orderManageService } from '../../service/orderManageService';

export function useOrderManage() {
    const orders = ref([]);
    const currentOrder = ref({
        orderId: null,
        customerName: '',
        orderDate: '',
        orderAmount: '',
        orderStatus: '',
        paymentStatus: '',
    });
    const loading = ref(false);
    const currentPage = ref(0);
    const totalPages = ref(0);
    const totalElements = ref(0);

    const fetchOrders = async (page = 0) => {
        loading.value = true;
        try {
            const response = await orderManageService.getAll(page);
            orders.value = response.data.content;
            currentPage.value = response.data.currentPage;
            totalPages.value = response.data.totalPages;
            totalElements.value = response.data.totalElements;
        } catch (error) {
            console.error('Lỗi tải danh sách:', error);
            console.error('Error details:', error.response?.data || error.message);
            alert('Không thể tải danh sách đơn hàng!');
        } finally {
            loading.value = false;
        }
    };

    const goToPage = (page) => {
        fetchOrders(page);
    };

    const nextPage = () => {
        if (currentPage.value < totalPages.value - 1) {
            fetchOrders(currentPage.value + 1);
        }
    };

    const prevPage = () => {
        if (currentPage.value > 0) {
            fetchOrders(currentPage.value - 1);
        }
    };

    const fetchOrderById = async (id) => {
        loading.value = true;
        try {
            const response = await orderManageService.getById(id);
            currentOrder.value = response.data;

            if (currentOrder.value.orderDate) {
                currentOrder.value.orderDate = currentOrder.value.orderDate.split('T')[0];
            }
        } catch (error) {
            console.error('Lỗi tải đơn hàng:', error);
            alert('Không thể tải thông tin đơn hàng!');
        } finally {
            loading.value = false;
        }
    };

    const updateOrder = async () => {
        if (!currentOrder.value.orderId) {
            alert('Vui lòng chọn đơn hàng cần cập nhật!');
            return;
        }

        loading.value = true;
        try {
            const response = await orderManageService.update(currentOrder.value.orderId, currentOrder.value);
            alert(response.data.message);
            await fetchOrders();
            resetFormOrder();
        } catch (error) {
            console.error('Lỗi cập nhật đơn hàng:', error);
            alert('Không thể cập nhật đơn hàng!');
        } finally {
            loading.value = false;
        }
    };

    const deleteOrder = async (id) => {
        loading.value = true;
        try {
            const response = await orderManageService.delete(id);
            alert(response.data.message);
            await fetchOrders();
        } catch (error) {
            console.error('Lỗi xóa đơn hàng:', error);
            alert('Không thể xóa đơn hàng!');
        } finally {
            loading.value = false;
        }
    };

    const editOrder = async (id) => {
        await fetchOrderById(id);
    };

    const validateOrder = () => {
        if (!currentOrder.value.fullName || !currentOrder.value.fullName.trim()) {
            alert('Vui lòng nhập tên người dùng!');
            return false;
        }
        if (!currentOrder.value.email || !currentOrder.value.email.trim()) {
            alert('Vui lòng nhập email hợp lệ!');
            return false;
        }
        if (!currentOrder.value.phone || !currentOrder.value.phone.trim()) {
            alert('Vui lòng nhập số điện thoại hợp lệ!');
            return false;
        }
        return true;
    };

    const resetFormOrder = () => {
        currentOrder.value = {
            orderId: null,
            customerName: '',
            orderDate: '',
            orderAmount: '',
            orderStatus: '',
            paymentStatus: '',
        };
        fetchOrders();
    };

    return {
        orders,
        currentOrder,
        loading,
        currentPage,
        totalPages,
        totalElements,
        fetchOrders,
        fetchOrderById,
        updateOrder,
        editOrder,
        resetFormOrder,
        deleteOrder,
        goToPage,
        nextPage,
        prevPage
    };
}