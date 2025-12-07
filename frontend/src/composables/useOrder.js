import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '../utils/api'


const errorMessage = ref('')
const successMessage = ref('')
const orderId = ref('')

export default function useOrder() {
    const router = useRouter()

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

    return {
        successMessage,
        errorMessage,
        createOrder
    }
}