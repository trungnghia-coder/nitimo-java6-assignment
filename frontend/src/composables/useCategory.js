import { ref, onMounted } from 'vue'
import api from '../utils/api'
const categories = ref([]);

export default function useCategory() {
    const fetchCategories = async () => {
        try {
            const response = await api.get('/api/category');
            categories.value = response.data;
        } catch (error) {
            console.error('Error fetching categories:', error);
        }
    };

    onMounted(() => {
        fetchCategories();
    });

    return {
        categories,
        fetchCategories
    }
}



