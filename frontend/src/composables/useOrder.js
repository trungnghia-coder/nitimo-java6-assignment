import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '../utils/api'


const errorMessage = ref('')
const successMessage = ref('')

export default function useOrder() {
    const router = useRouter()

    const createOrder = async (shippingAddress, paymentMethod, voucherId) => {
        try {
            const response = await api.post(`/api/order/create-order`, {
                shippingAddress,
                paymentMethod,
                voucherId
            });

            if (response.data.message) {
                successMessage.value = response.data.message
                setTimeout(() => { successMessage.value = '' }, 3000)
                alert(successMessage.value)
                router.push('/')
                return true
            } else {
                errorMessage.value = 'Thêm vào giỏ thất bại'
                return false
            }
        } catch (error) {
            console.error('Error add to cart:', error);
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