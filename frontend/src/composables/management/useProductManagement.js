import { ref, onMounted } from 'vue'
import api from '../../utils/api'
import { productManageService } from '../../service/productManageService';
const successMessage = ref('')

export default function useProductManagement() {
    const products = ref([])
    const currentPage = ref(0);
    const totalPages = ref(0);
    const totalElements = ref(0);
    const loading = ref(false)
    const productDetail = ref(null)
    const loadingDetail = ref(false)
    const fetchProducts = async (page = 0) => {
        try {
            loading.value = true
            console.log('Fetching page:', page);
            const response = await productManageService.getAll(page);

            products.value = response.data.content;
            totalPages.value = response.data.totalPages;
            totalElements.value = response.data.totalElements;
            currentPage.value = page;
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
            const response = await productManageService.getById(productId)
            productDetail.value = response.data
            return response.data
        } catch (error) {
            console.error('Error fetching product detail:', error)
            throw error
        } finally {
            loadingDetail.value = false
        }
    }

    const createProduct = async (productData, imageFiles) => {
        try {
            if (!imageFiles || imageFiles.length === 0) {
                throw new Error('Please upload at least one image')
            }

            const formData = new FormData()
            formData.append('productData', JSON.stringify(productData))
            imageFiles.forEach((file, index) => {
                formData.append('images', file)
            })

            const response = await productManageService.create(formData)
            successMessage.value = response.data.message
            console.log(successMessage.value)
            return response.data
        } catch (error) {
            console.error('Error creating product:', error)
            console.error('Error details:', error.response?.data)
            throw error
        }
    }

    const updateProduct = async (id, productData, imageFiles) => {
        try {
            const formData = new FormData()
            formData.append('data', JSON.stringify(productData))
            if (imageFiles && imageFiles.length > 0) {
                imageFiles.forEach((file) => {
                    formData.append('images', file)
                })
            }

            const response = await productManageService.update(id, formData)
            await fetchProducts(currentPage.value)

            return response.data
        } catch (error) {
            console.error('Error updating product:', error)
            console.error('Error details:', error.response?.data)
            throw error
        }
    }

    const deleteProduct = async (productId) => {
        try {
            const response = await productManageService.delete(productId)
            products.value = products.value.filter(p => p.productId !== productId)
            if (products.value.length === 0 && currentPage.value > 0) {
                await fetchProducts(currentPage.value - 1)
            } else if (products.value.length === 0 && currentPage.value === 0) {
                await fetchProducts(0)
            }

            return response.data
        } catch (error) {
            console.error('Error deleting product:', error)
            console.error('Error details:', error.response?.data)
            throw error
        }
    }

    const goToPage = (page) => {
        if (page >= 0 && page < totalPages.value) {
            fetchProducts(page)
        }
    }

    const nextPage = () => {
        if (currentPage.value < totalPages.value - 1) {
            fetchProducts(currentPage.value + 1)
        }
    }

    const prevPage = () => {
        if (currentPage.value > 0) {
            fetchProducts(currentPage.value - 1)
        }
    }

    return {
        products,
        loading,
        loadingDetail,
        productDetail,
        currentPage,
        totalPages,
        totalElements,
        updateProduct,
        fetchProducts,
        fetchProductDetail,
        createProduct,
        deleteProduct,
        goToPage,
        nextPage,
        prevPage
    }
}