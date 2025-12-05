import { ref } from 'vue'
import api from '../utils/api'

// Singleton state - shared across all components
const cart = ref([]);
const items = ref([]);
const cartCount = ref(0);
const successMessage = ref('')
const errorMessage = ref('')

export default function useCart() {

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
                await fetchItemToCart();
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
            cartCount.value = items.value.length;
        } catch (error) {
            console.error('Error fetching cart items:', error);
            items.value = [];
            cartCount.value = 0;
        }
    };

    const deleteItemFromCart = async (variantId) => {
        try {
            const response = await api.delete(`/api/cart-item/remove`, {
                data: {
                    variantId
                }
            });

            if (response.data) {
                successMessage.value = 'Xóa sản phẩm thành công'
                setTimeout(() => { successMessage.value = '' }, 3000)
                await fetchItemToCart();
                return true
            } else {
                errorMessage.value = 'Xóa sản phẩm thất bại'
                return false
            }
        } catch (error) {
            console.error('Error remove from cart:', error);
            errorMessage.value = error.response?.data?.error || 'Có lỗi xảy ra'
            return false
        }
    };

    const removeAllFromCart = async () => {
        try {
            const response = await api.delete(`/api/cart-item/clear`, {
                data: {
                    variantId
                }
            });

            if (response.data) {
                successMessage.value = 'Xóa thành công'
                setTimeout(() => { successMessage.value = '' }, 3000)
                await fetchItemToCart();
                return true
            } else {
                errorMessage.value = 'Xóa thất bại'
                return false
            }
        } catch (error) {
            console.error('Error remove from cart:', error);
            errorMessage.value = error.response?.data?.error || 'Có lỗi xảy ra'
            return false
        }
    };

    const updateQuantity = async (variantId, quantity) => {
        try {
            const response = await api.put(`/api/cart-item/update?variantId=${variantId}&quantity=${quantity}`, {
                data: {
                    variantId,
                    quantity
                }
            });

            if (response.data) {
                await fetchItemToCart();
                return true
            } else {
                errorMessage.value = 'Xóa sản phẩm thất bại'
                return false
            }
        } catch (error) {
            console.error('Error update quantity in cart:', error);
            errorMessage.value = error.response?.data?.error || 'Có lỗi xảy ra'
            return false
        }
    };

    return {
        cart,
        items,
        cartCount,
        successMessage,
        errorMessage,
        addToCart,
        fetchItemToCart,
        deleteItemFromCart,
        removeAllFromCart,
        updateQuantity
    }
}