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
import { ref } from 'vue'
import OrderDetailModal from '../components/OrderDetailModal.vue'

const isModalOpen = ref(false)
const viewingOrder = ref(null)

const openOrderDetails = () => {
  viewingOrder.value = {
    orderId: "ORD001",
    orderDate: "2025-12-06",
    staffName: "Nhân viên A",
    staffEmail: "staff@email.com",
    customerName: "Nguyễn Văn A",
    customerPhone: "0123456789",
    recipientName: "Nguyễn Văn A",
    recipientPhone: "0123456789",
    recipientAddress: "123 Đường ABC, Quận 1, TP.HCM",
    products: [
      { name: "Áo thun", quantity: 2, price: 150000 }
    ],
    paymentMethod: "COD",
    shippingFee: 0,
    discountAmount: 0,
    subtotal: 300000,
    total: 300000
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