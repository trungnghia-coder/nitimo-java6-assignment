import { ref } from 'vue'
import api from '../utils/api'

export default function useCart() {
    const cart = ref([]);
    const items = ref([]);
    const successMessage = ref('')
    const errorMessage = ref('')

    const addToCart = async (productCode, quantity, size) => {
        try {
            const response = await api.post(`/api/cart-item/add`, {
                productCode,
                quantity,
                size
            });

            if (response.data.message) {
                successMessage.value = response.data.message
                setTimeout(() => { successMessage.value = '' }, 3000)
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


    const fetchItemToCart = async () => {
        try {
            const response = await api.get(`/api/cart-item/slide-bar`);
            items.value = response.data;
        } catch (error) {
            console.error('Error fetching cart items:', error);
            items.value = [];
        }
    };

    return {
        cart,
        items,
        successMessage,
        errorMessage,
        addToCart,
        fetchItemToCart
    }
}