import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '../utils/api'

const infoOrderDetail = ref(null);
const loadingDetail = ref(false);

export default function useOrderDetail() {
    const router = useRouter()

    const fetchOrderDetail = async (orderId) => {
        loadingDetail.value = true;
        try {
            const response = await api.get(`/api/order-detail/fetch?orderId=${orderId}`);
            infoOrderDetail.value = response.data;
            console.log('Fetched order detail:', response.data);
        } catch (error) {
            console.error('Error fetching order details:', error);
            infoOrderDetail.value = null;
        } finally {
            loadingDetail.value = false;
        }
    };

    return {
        infoOrderDetail,
        loadingDetail,
        fetchOrderDetail
    }
}