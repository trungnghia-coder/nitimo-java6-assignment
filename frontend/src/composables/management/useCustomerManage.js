import { ref } from 'vue';
import { customerManageService } from '../../service/customerManageService';

export function useCustomerManage() {
    const customers = ref([]);
    const currentCustomer = ref({
        customerId: null,
        fullName: '',
        email: '',
        phone: '',
        address: '',
        joined: '',
    });
    const loading = ref(false);
    const currentPage = ref(0);
    const totalPages = ref(0);
    const totalElements = ref(0);

    const fetchCustomers = async (page = 0) => {
        loading.value = true;
        try {
            const response = await customerManageService.getAll(page);
            customers.value = response.data.content;
            currentPage.value = response.data.currentPage;
            totalPages.value = response.data.totalPages;
            totalElements.value = response.data.totalElements;
        } catch (error) {
            console.error('Lỗi tải danh sách:', error);
            console.error('Error details:', error.response?.data || error.message);
            alert('Không thể tải danh sách khách hàng!');
        } finally {
            loading.value = false;
        }
    };

    const goToPage = (page) => {
        fetchCustomers(page);
    };

    const nextPage = () => {
        if (currentPage.value < totalPages.value - 1) {
            fetchCustomers(currentPage.value + 1);
        }
    };

    const prevPage = () => {
        if (currentPage.value > 0) {
            fetchCustomers(currentPage.value - 1);
        }
    };

    const fetchCustomerById = async (id) => {
        loading.value = true;
        try {
            const response = await customerManageService.getById(id);
            currentCustomer.value = response.data;
        } catch (error) {
            console.error('Lỗi tải khách hàng:', error);
            alert('Không thể tải thông tin khách hàng!');
        } finally {
            loading.value = false;
        }
    };

    const updateCustomer = async () => {
        if (!currentCustomer.value.customerId) {
            alert('Vui lòng chọn khách hàng cần cập nhật!');
            return;
        }
        if (!validateCustomer()) return;

        loading.value = true;
        try {
            const response = await customerManageService.update(currentCustomer.value.customerId, currentCustomer.value);
            alert(response.data.message);
            await fetchCustomers();
            resetFormCustomer();
        } catch (error) {
            console.error('Lỗi cập nhật khách hàng:', error);
            alert('Không thể cập nhật khách hàng!');
        } finally {
            loading.value = false;
        }
    };

    const deleteCustomer = async (id) => {
        loading.value = true;
        try {
            const response = await customerManageService.delete(id);
            alert(response.data.message);
            await fetchCustomers();
        } catch (error) {
            console.error('Lỗi xóa khách hàng:', error);
            alert('Không thể xóa khách hàng!');
        } finally {
            loading.value = false;
        }
    };

    const editCustomer = async (id) => {
        await fetchCustomerById(id);
    };

    const validateCustomer = () => {
        if (!currentCustomer.value.customerId || !currentCustomer.value.customerId.trim()) {
            alert('Vui lòng nhập mã khách hàng!');
            return false;
        }
        if (!currentCustomer.value.fullName || !currentCustomer.value.fullName.trim()) {
            alert('Vui lòng nhập tên khách hàng!');
            return false;
        }
        if (!currentCustomer.value.email || !currentCustomer.value.email.trim()) {
            alert('Vui lòng nhập email hợp lệ!');
            return false;
        }
        if (!currentCustomer.value.phone || !currentCustomer.value.phone.trim()) {
            alert('Vui lòng nhập số điện thoại hợp lệ!');
            return false;
        }
        if (!currentCustomer.value.address || !currentCustomer.value.address.trim()) {
            alert('Vui lòng nhập địa chỉ!');
            return false;
        }
        return true;
    };

    const resetFormCustomer = () => {
        currentCustomer.value = {
            customerId: null,
            fullName: '',
            email: '',
            phone: '',
            address: '',
        };
        fetchCustomers();
    };

    return {
        customers,
        currentCustomer,
        loading,
        currentPage,
        totalPages,
        totalElements,
        fetchCustomers,
        fetchCustomerById,
        updateCustomer,
        editCustomer,
        resetFormCustomer,
        deleteCustomer,
        goToPage,
        nextPage,
        prevPage
    };
}