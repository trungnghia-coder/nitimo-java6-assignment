import { ref, onMounted } from 'vue'
import api from '../../utils/api'
import { productManageService } from '../../service/productManageService';

export default function useProductManagement() {
    const products = ref([])
    const currentPage = ref(0);
    const totalPages = ref(0);
    const loading = ref(false)
    const hasMore = ref(true)
    const productDetail = ref(null)
    const loadingDetail = ref(false)
    const currentProduct = ref({
        productId: '',
        productName: '',
        description: '',
        price: '',
        discount: '',
        categoryCode: '',
        productVariantRequest: {
            sizeId: '',
            stockQuantity: ''
        }
    });

    const fetchProducts = async () => {
        if (loading.value || !hasMore.value) return

        try {
            loading.value = true
            console.log('Fetching page:', currentPage.value); // Debug log
            const response = await productManageService.getAll(currentPage.value); // Sửa từ totalPages → currentPage

            products.value = [...products.value, ...response.data.content];

            totalPages.value = response.data.totalPages;
            currentPage.value++;
            hasMore.value = currentPage.value < totalPages.value;

            console.log('Loaded products:', response.data.content.length);
            console.log('Current page:', currentPage.value, '/', totalPages.value);
        } catch (error) {
            console.error('Error fetching products:', error)
            throw error
        } finally {
            loading.value = false
        }
    }

    const fetchProductDetail = async (productId) => {
        try {
            loadingDetail.value = true
            const response = await api.get(`/api/admin/product/${productId}`)
            productDetail.value = response.data
            return response.data
        } catch (error) {
            console.error('Error fetching product detail:', error)
            throw error
        } finally {
            loadingDetail.value = false
        }
    }

    const createProduct = async (currentProduct, imageFiles) => {
        try {
            const formData = new FormData()

            formData.append('productData', JSON.stringify(currentProduct))
            imageFiles.forEach((file) => {
                formData.append('images', file)
            })

            const response = await api.post('/api/admin/product/create', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })

            return response.data
        } catch (error) {
            console.error('Error creating product:', error)
            throw error
        }
    }

    // Delete product
    const deleteProduct = async (productId) => {
        try {
            const response = await api.delete('/api/admin/product/delete', {
                params: { productId }
            })

            // Remove from local array
            products.value = products.value.filter(p => p.id !== productId)

            return response.data
        } catch (error) {
            console.error('Error deleting product:', error)
            throw error
        }
    }

    // Reset pagination
    const resetPagination = () => {
        products.value = []
        currentPage.value = 0
        hasMore.value = true
    }

    onMounted(() => {
        fetchProducts()
    });

    return {
        products,
        loading,
        loadingDetail,
        productDetail,
        hasMore,
        currentProduct,
        fetchProducts,
        fetchProductDetail,
        createProduct,
        deleteProduct,
        resetPagination
    }
}