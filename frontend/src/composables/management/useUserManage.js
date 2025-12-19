import { ref } from 'vue';
import { userManageService } from '../../service/userManageService';

export function useUserManage() {
    const users = ref([]);
    const currentUser = ref({
        userId: null,
        fullName: '',
        email: '',
        phone: '',
        role: '',
        status: '',
        password: '',
        confirmPassword: '',
    });
    const loading = ref(false);
    const currentPage = ref(0);
    const totalPages = ref(0);
    const totalElements = ref(0);

    const fetchUsers = async (page = 0) => {
        loading.value = true;
        try {
            const response = await userManageService.getAll(page);
            users.value = response.data.content;
            currentPage.value = response.data.currentPage;
            totalPages.value = response.data.totalPages;
            totalElements.value = response.data.totalElements;
        } catch (error) {
            console.error('Lỗi tải danh sách:', error);
            console.error('Error details:', error.response?.data || error.message);
            alert('Không thể tải danh sách người dùng!');
        } finally {
            loading.value = false;
        }
    };

    const goToPage = (page) => {
        fetchUsers(page);
    };

    const nextPage = () => {
        if (currentPage.value < totalPages.value - 1) {
            fetchUsers(currentPage.value + 1);
        }
    };

    const prevPage = () => {
        if (currentPage.value > 0) {
            fetchUsers(currentPage.value - 1);
        }
    };

    const fetchUserById = async (id) => {
        loading.value = true;
        try {
            const response = await userManageService.getById(id);
            currentUser.value = response.data;
        } catch (error) {
            console.error('Lỗi tải người dùng:', error);
            alert('Không thể tải thông tin người dùng!');
        } finally {
            loading.value = false;
        }
    };

    const createUser = async () => {
        if (!validateUser()) return;

        loading.value = true;
        try {
            await userManageService.create(currentUser.value);
            alert('Tạo người dùng thành công!');
            await fetchUsers();
            resetFormUser();
        } catch (error) {
            console.error('Lỗi tạo người dùng:', error);
            alert('Không thể tạo người dùng mới!');
        } finally {
            loading.value = false;
        }
    };

    const updateUser = async () => {
        if (!currentUser.value.userId) {
            alert('Vui lòng chọn người dùng cần cập nhật!');
            return;
        }
        if (!validateUser()) return;

        loading.value = true;
        try {
            const response = await userManageService.update(currentUser.value.userId, currentUser.value);
            alert(response.data.message);
            await fetchUsers();
            resetFormUser();
        } catch (error) {
            console.error('Lỗi cập nhật người dùng:', error);
            alert('Không thể cập nhật người dùng!');
        } finally {
            loading.value = false;
        }
    };

    const deleteUser = async (id) => {
        loading.value = true;
        try {
            const response = await userManageService.delete(id);
            alert(response.data.message);
            await fetchUsers();
        } catch (error) {
            console.error('Lỗi xóa người dùng:', error);
            alert('Không thể xóa người dùng!');
        } finally {
            loading.value = false;
        }
    };

    const editUser = async (id) => {
        await fetchUserById(id);
    };

    const validateUser = () => {
        if (!currentUser.value.fullName || !currentUser.value.fullName.trim()) {
            alert('Vui lòng nhập tên người dùng!');
            return false;
        }
        if (!currentUser.value.email || !currentUser.value.email.trim()) {
            alert('Vui lòng nhập email hợp lệ!');
            return false;
        }
        if (!currentUser.value.phone || !currentUser.value.phone.trim()) {
            alert('Vui lòng nhập số điện thoại hợp lệ!');
            return false;
        }
        return true;
    };

    const resetFormUser = () => {
        currentUser.value = {
            userId: null,
            fullName: '',
            email: '',
            phone: '',
            address: '',
        };
        fetchUsers();
    };

    return {
        users,
        currentUser,
        loading,
        currentPage,
        totalPages,
        totalElements,
        createUser,
        fetchUsers,
        fetchUserById,
        updateUser,
        editUser,
        resetFormUser,
        deleteUser,
        goToPage,
        nextPage,
        prevPage
    };
}