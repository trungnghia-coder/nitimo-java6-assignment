import { ref, onMounted } from 'vue'
import api from '../utils/api'

export default function useProduct() {
    const products = ref([]);
    const currentPage = ref(0);
    const totalPages = ref(0);
    const loading = ref(false);
    const hasMore = ref(true);
    const productDetail = ref(null);
    const loadingDetail = ref(false);

    const fetchProducts = async () => {
        if (loading.value || !hasMore.value) return;

        try {
            loading.value = true;
            const response = await api.get(`/api/product/all?page=${currentPage.value}&size=10`);

            products.value = [...products.value, ...response.data.content];

            totalPages.value = response.data.totalPages;
            currentPage.value++;
            hasMore.value = currentPage.value < totalPages.value;
        } catch (error) {
            console.error('Error fetching products:', error);
        } finally {
            loading.value = false;
        }
    };

    const fetchProductDetail = async (productId) => {
        try {
            loadingDetail.value = true;
            const response = await api.get(`/api/product/${productId}`);
            productDetail.value = response.data;
        } catch (error) {
            console.error('Error fetching products:', error);
        } finally {
            loadingDetail.value = false;
        }
    };

    onMounted(() => {
        fetchProducts(),
            fetchProductDetail();
    });

    return {
        products,
        loading,
        hasMore,
        fetchProducts,
        productDetail,
        loadingDetail,
        fetchProductDetail
    }
}