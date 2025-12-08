import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '../utils/api'
import { useFlowStore } from '../stores/userStore';

const errorMessage = ref('')
const successMessage = ref('')
const orderId = ref('')
const orders = ref([])

export default function useOrder() {
    const router = useRouter()
    const flowStore = useFlowStore();

    const createOrder = async (shippingAddress, paymentMethod, voucherId) => {
        try {
            const response = await api.post(`/api/order/create-order`, {
                shippingAddress,
                paymentMethod,
                voucherId
            });

            console.log('API Response:', response.data);

            if (response.data.message) {
                successMessage.value = response.data.message
                orderId.value = response.data.orderID
                flowStore.setOrderSuccessAccess(true);
                alert(successMessage.value)
                router.push({
                    name: 'OrderSuccess',
                    query: { id: orderId.value }
                });
                setTimeout(() => { successMessage.value = '' }, 3000)
                return true
            } else {
                errorMessage.value = 'Tạo đơn hàng thất bại'
                return false
            }
        } catch (error) {
            console.error('Error creating order:', error);
            errorMessage.value = error.response?.data?.error || 'Có lỗi xảy ra'
            return false
        }
    };

    const fetchOrderHistory = async () => {
        try {
            const response = await api.get(`/api/order/fetch-order-history`);
            orders.value = response.data;
        } catch (error) {
            console.error('Error fetching order history:', error);
            orders.value = null;
        }
    };

    return {
        successMessage,
        errorMessage,
        orders,
        createOrder,
        fetchOrderHistory
    }
}