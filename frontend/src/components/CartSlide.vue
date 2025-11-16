<template>
  <!-- Overlay -->
  <transition name="fade">
    <div 
      v-if="isOpen"
      class="cart-overlay"
      @click="closeCart"
    ></div>
  </transition>

  <!-- Slide Panel -->
  <transition name="slide">
    <div v-if="isOpen" class="cart-slide-panel">
      <!-- Header -->
      <div class="cart-slide-header">
        <h3>Giỏ hàng</h3>
        <button class="btn-close" @click="closeCart">
        </button>
      </div>

      <!-- Cart Items -->
      <div v-if="cartItems.length > 0" class="cart-items-slide">
        <div v-for="item in cartItems" :key="item.id" class="cart-item">
          <div class="item-image">
            <img :src="item.image" :alt="item.name">
          </div>
          <div class="item-details">
            <h5 class="item-name">{{ item.name }}</h5>
            <p class="item-price">{{ item.price }}</p>
            <div class="quantity-selector-slide">
              <button class="qty-btn-slide" @click="decreaseQuantity(item.id)">−</button>
              <span>{{ item.quantity }}</span>
              <button class="qty-btn-slide" @click="increaseQuantity(item.id)">+</button>
            </div>
          </div>
          <div class="item-remove">
            <button class="btn-remove" @click="removeItem(item.id)">
              <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" viewBox="0 0 16 16">
                <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z"/>
                <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z"/>
              </svg>
            </button>
          </div>
        </div>
      </div>

      <!-- Empty Cart -->
      <div v-else class="empty-cart-slide text-center py-5">
        <svg xmlns="http://www.w3.org/2000/svg" width="50" height="50" fill="currentColor" class="bi bi-bag-x" viewBox="0 0 16 16">
          <path fill-rule="evenodd" d="M6.146 8.146a.5.5 0 0 1 .708 0L8 9.293l1.146-1.147a.5.5 0 1 1 .708.708L8.707 10l1.147 1.146a.5.5 0 0 1-.708.708L8 10.707l-1.146 1.147a.5.5 0 0 1-.708-.708L7.293 10 6.146 8.854a.5.5 0 0 1 0-.708"/>
          <path d="M8 1a2.5 2.5 0 0 1 2.5 2.5V4h-5v-.5A2.5 2.5 0 0 1 8 1m3.5 3v-.5a3.5 3.5 0 1 0-7 0V4H1v10a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V4zM2 5h12v9a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1z"/>
        </svg>
        <p class="mt-3 text-muted">Giỏ hàng trống</p>
      </div>

      <!-- Footer (Sticky) -->
      <div class="cart-slide-footer">

        <!-- Total -->
        <div class="total-row d-flex justify-content-between mb-3">
          <span class="fw-bold">TỔNG:</span>
          <span class="fw-bold" style="font-size: 18px; color: #F9943B;">
            {{ totalPrice.toLocaleString('vi-VN') }}đ
          </span>
        </div>

        <!-- Buttons -->
        <button class="btn bg-F9943B w-100 mb-2 text-white" style="padding: 12px; font-weight: bold;" @click="goToCheckout">
          THANH TOÁN
        </button>
      </div>
    </div>
  </transition>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const props = defineProps({
  isOpen: {
    type: Boolean,
    required: true
  }
})

const emit = defineEmits(['close'])
const router = useRouter()

// Sample cart items
const cartItems = ref([
  {
    id: 1,
    name: 'Áo thun cotton nam',
    price: '150.000đ',
    quantity: 1,
    image: 'https://cdn.hstatic.net/products/200000503583/ao-kieu-nu__1__d89a4e13aa914a1eb48844d9741815e3_master.jpg'
  },
  {
    id: 2,
    name: 'Quần jean xanh',
    price: '350.000đ',
    quantity: 1,
    image: 'https://cdn.hstatic.net/products/200000503583/ao-phao__4__8c7be53ea3274dec972b1dd3c384d1ee_master.jpg'
  },
  {
    id: 3,
    name: 'Giày thể thao trắng',
    price: '450.000đ',
    quantity: 1,
    image: 'https://cdn.hstatic.net/products/200000503583/ao-giu-nhiet__4__e238afd21a6c430fa7e860dc2a505d6f_master.jpg'
  },
  {
    id: 3,
    name: 'Giày thể thao trắng',
    price: '450.000đ',
    quantity: 1,
    image: 'https://cdn.hstatic.net/products/200000503583/ao-giu-nhiet__4__e238afd21a6c430fa7e860dc2a505d6f_master.jpg'
  }
  ,
  {
    id: 3,
    name: 'Giày thể thao trắng',
    price: '450.000đ',
    quantity: 1,
    image: 'https://cdn.hstatic.net/products/200000503583/ao-giu-nhiet__4__e238afd21a6c430fa7e860dc2a505d6f_master.jpg'
  }
  ,
  {
    id: 3,
    name: 'Giày thể thao trắng',
    price: '450.000đ',
    quantity: 1,
    image: 'https://cdn.hstatic.net/products/200000503583/ao-giu-nhiet__4__e238afd21a6c430fa7e860dc2a505d6f_master.jpg'
  }
])

const totalPrice = computed(() => {
  return cartItems.value.reduce((total, item) => {
    const price = parseInt(item.price.replace(/\D/g, '')) * item.quantity
    return total + price
  }, 0)
})

const removeItem = (id) => {
  cartItems.value = cartItems.value.filter(item => item.id !== id)
}

const increaseQuantity = (id) => {
  const item = cartItems.value.find(item => item.id === id)
  if (item) {
    item.quantity++
  }
}

const decreaseQuantity = (id) => {
  const item = cartItems.value.find(item => item.id === id)
  if (item && item.quantity > 1) {
    item.quantity--
  }
}

const closeCart = () => {
  emit('close')
}

const goToCheckout = () => {
  console.log('Navigating to checkout...')
  router.push('/checkout')
  emit('close')
}
</script>
