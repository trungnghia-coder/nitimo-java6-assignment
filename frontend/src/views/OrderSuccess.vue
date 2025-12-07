<template>
  <div class="container">
    <div class="order-success">
      <div class="icon mb-3">
        <svg xmlns="http://www.w3.org/2000/svg" height="70px" viewBox="0 0 24 24" width="70px" fill="currentColor">
          <path d="M0 0h24v24H0V0z" fill="none"/>
          <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8zm-2.07-4.64l-3.53-3.53 1.41-1.41L9.93 12.5l5.65-5.65 1.41 1.41-7.06 7.07z"/>
        </svg>
      </div>

      <h2 class="title fw-bold mb-3">Order Successful!</h2>

      <p class="message text-muted mb-4">
        Thank you for your purchase. We will contact you soon to confirm your order.
      </p>

      <div class="actions d-flex justify-content-center gap-3 flex-wrap">
        <button
          class="btn btn-outline-dark px-4 py-2 my-1"
          @click="openOrderDetails"
        >
          <i class="bi bi-file-text"></i> View Order Details
        </button>

        <router-link to="/" class="btn btn-primary px-4 py-2">
          <i class="bi bi-cart"></i> Continue Shopping
        </router-link>
      </div>
    </div>
  </div>
  <OrderDetailModal 
    :isVisible="isModalOpen"
    v-if="isModalOpen" 
    v-bind="viewingOrder"
    @close="closeModal" 
  />
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import OrderDetailModal from '../components/OrderDetailModal.vue'
import useOrderDetail from '../composables/useOrderDetail'

const route = useRoute()
const { infoOrderDetail, fetchOrderDetail } = useOrderDetail()

const isModalOpen = ref(false)
const viewingOrder = ref(null)
const orderId = ref('')

onMounted(async () => {
  orderId.value = route.query.id
})

const openOrderDetails = async () => {
  if (!infoOrderDetail.value) {
    await fetchOrderDetail(orderId.value)
  }
  
  if (infoOrderDetail.value) {
    const orderData = infoOrderDetail.value 
    
    const products = orderData.items ? orderData.items.map(item => ({
      productName: item.productName || item.name || "N/A",
      productCode: item.productCode || item.code || "N/A",
      quantity: item.quantity || 0,
      unitPrice: item.unitPrice || item.price || 0,
      discount: item.discountProduct || 0,
      subTotal: item.subTotal || (item.quantity * (item.unitPrice || item.price || 0))
    })) : []
    
    const subtotal = products.reduce((sum, item) => sum + item.subTotal, 0)
    
    viewingOrder.value = {
      orderId: orderId.value || "N/A",
      orderDate: orderData.orderDate || new Date().toISOString(),
      recipientName: orderData.username || "N/A",
      recipientPhone: orderData.phone || "N/A",
      recipientAddress: orderData.address || "N/A",
      products: products || [],
      paymentMethod: orderData.paymentMethod || "COD",
      paymentStatus: orderData.paymentStatus || "UNPAID",
      shippingFee: orderData.shippingFee || 0,
      discountAmount: orderData.discount || 0,
      subtotal: subtotal || 0,
      total: orderData.total || 0
    }
    
    console.log('Viewing order data:', viewingOrder.value)
  }
  
  isModalOpen.value = true
}

const closeModal = () => {
  viewingOrder.value = null
  isModalOpen.value = false
}
</script>

<style scoped>
@import '../assets/css/orderSuccess.css';
</style>