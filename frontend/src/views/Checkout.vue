<template>
  <div class="checkout-container py-5" style="background-color: #f9f9f9; min-height: 100vh;">
    <div class="container-fluid px-4">
      <h1 class="mb-3 fw-bold" style="font-size: 32px; color: #333;">Thanh Toán</h1>

      <div class="row g-5">
        
        <!-- LEFT: Forms -->
        <div class="col-lg-8">

          <!-- Products card -->
          <div class="card mb-4" style="border: 1px solid #ddd; border-radius: 8px;">
            <div class="card-body p-4">
              <h4 class="mb-4 fw-bold" style="color: #333;">Sản Phẩm Đã Chọn</h4>
              <div v-if="products.length === 0" class="text-muted text-center py-4">Không có sản phẩm nào</div>

              <div v-for="(item, index) in products" :key="index" class="d-flex align-items-center py-3 position-relative" :class="{ 'border-bottom': index < products.length - 1 }">
                <!-- Nút xóa góc trên bên trái -->
                <button class="btn btn-sm position-absolute rounded-circle" @click="removeItem(item.id)" style="top: 8px; left: -16px; width: 32px; height: 32px; background: #e0e0e0; border: none; padding: 0; z-index: 10; font-size: 11px; color: #666;">
                  Xóa
                </button>
                
                <img :src="item.imageUrl" alt="img" class="me-3" style="width: 80px; height: 80px; object-fit: cover; border-radius: 6px;" />
                <div class="flex-grow-1">
                  <div class="fw-semibold" style="font-size: 14px; color: #333;">{{ item.name }}</div>
                  <div class="text-muted small mt-1">{{ (item.price).toLocaleString() }}₫</div>
                </div>
                <div class="text-end">
                  <div class="d-flex align-items-center gap-2 mb-2">
                    <button class="btn btn-sm" style="border: 1px solid #ddd; background: white; color: #666; padding: 4px 8px;" @click="decreaseQuantity(item.id, item.quantity)">−</button>
                    <span style="min-width: 20px; text-align: center;">{{ item.quantity }}</span>
                    <button class="btn btn-sm" style="border: 1px solid #ddd; background: white; color: #666; padding: 4px 8px;" @click="increaseQuantity(item.id, item.quantity)">+</button>
                  </div>
                  <div class="small text-muted">Tạm tính: <span class="fw-bold" style="color: #dc3545;">{{ (item.price).toLocaleString() }}₫</span></div>
                </div>
              </div>
            </div>
          </div>

          <!-- Shipping info -->
          <div class="card mb-4" style="border: 1px solid #ddd; border-radius: 8px;">
            <div class="card-body p-4">
              <h4 class="mb-4 fw-bold" style="color: #333;">Thông Tin Giao Hàng</h4>
              <div class="row g-3">
                <div class="col-12">
                  <input type="text" v-model="profile.fullName" class="form-control" placeholder="Họ tên *" style="border: 1px solid #ddd; padding: 10px 12px; border-radius: 6px;" />
                </div>
                <div class="col-12">
                  <input type="email" v-model="profile.email" class="form-control" placeholder="Email *" style="border: 1px solid #ddd; padding: 10px 12px; border-radius: 6px;" />
                </div>
                <div class="col-12">
                  <input type="tel" v-model="profile.phone" class="form-control" placeholder="Số điện thoại *" style="border: 1px solid #ddd; padding: 10px 12px; border-radius: 6px;" />
                </div>
              </div>
            </div>
          </div>

          <!-- Address -->
          <div class="card mb-4" style="border: 1px solid #ddd; border-radius: 8px;">
            <div class="card-body p-4">
              <h4 class="mb-4 fw-bold" style="color: #333;">Địa Chỉ Giao Hàng</h4>
              <div class="d-flex gap-2">
                <input v-model="profile.address"  type="text" class="form-control flex-grow-1" placeholder="Your address" />
                <button type="button" class="btn btn-secondary" @click="activeTab = 'location'">Change Location</button>
              </div>
            </div>
          </div>
          
          <!-- Payment -->
          <div class="card mb-4" style="border: 1px solid #ddd; border-radius: 8px;">
            <div class="card-body p-4">
              <h4 class="mb-4 fw-bold" style="color: #333;">Phương Thức Thanh Toán</h4>

              <div class="mb-3">
                <div class="form-check mb-2">
                  <input class="form-check-input" type="radio" id="payCard" value="BANK" v-model="selectedPaymentMethod" />
                  <label class="form-check-label" for="payCard">Thẻ Tín Dụng / Ghi Nợ</label>
                </div>
                <div class="form-check mb-2">
                  <input class="form-check-input" type="radio" id="payMomo" value="MOMO" v-model="selectedPaymentMethod" />
                  <label class="form-check-label" for="payMomo">Ví MoMo</label>
                </div>
                <div class="form-check">
                  <input class="form-check-input" type="radio" id="payCod" value="COD" v-model="selectedPaymentMethod"/>
                  <label class="form-check-label" for="payCod">Thanh Toán Khi Nhận Hàng (COD)</label>
                </div>
              </div>

            </div>
          </div>
        </div>

        <!-- RIGHT: order summary -->
        <div class="col-lg-4">
          <div class="card" style="border: 1px solid #ddd; border-radius: 8px; position: sticky; top: 100px;">
            <div class="card-body p-4">
              <h4 class="mb-4 fw-bold" style="color: #333;">Tóm Tắt Đơn Hàng</h4>

              <div class="mb-3 pb-3" style="border-bottom: 1px solid #eee;">
                <div class="d-flex justify-content-between mb-2 small">
                  <span>Tạm tính:</span>
                  <span>{{ totalPrice.toLocaleString('vi-VN') }}đ</span>
                </div>
                <div class="d-flex justify-content-between mb-2 small">
                  <span>Vận chuyển:</span>
                  <span style="color: #28a745;">Miễn phí</span>
                </div>
              </div>

              <!-- Total -->
              <div class="d-flex justify-content-between mb-4">
                <span class="fw-bold" style="font-size: 16px;">TỔNG CỘNG:</span>
                <span class="fw-bold c-f27127" style="font-size: 20px;">{{ totalPrice.toLocaleString('vi-VN') }}đ</span>
              </div>

              <!-- Buttons -->
              <button class="btn w-100 mb-2 fw-bold" style="background-color: #F9943B; color: white; padding: 12px; border-radius: 6px; border: none;" @click="createOrderHandler">
                THANH TOÁN
              </button>
              <button class="btn w-100" style="border: 1px solid #ddd; color: #666; padding: 12px; border-radius: 6px;" @click="goToBuyMore">
                Tiếp tục mua sắm
              </button>
            </div>
          </div>
        </div>
      </div> <!-- row -->
      <div v-if="activeTab === 'location'" class="location-picker-fullscreen">
          <div class="location-picker-fullscreen-container">
            <div class="location-picker-header">
              <div>
                <h2 class="location-picker-fullscreen-title">Select Your Location</h2>
                <p class="location-picker-fullscreen-subtitle">Choose your province, district, ward, and street address</p>
              </div>
              <button type="button" class="btn-back-to-account" @click="activeTab = 'info'" title="Close">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" class="bi bi-x-lg" viewBox="0 0 16 16">
                  <path d="M2.146 2.854a.5.5 0 1 1 .708-.708L8 7.293l5.146-5.147a.5.5 0 0 1 .708.708L8.707 8l5.147 5.146a.5.5 0 0 1-.708.708L8 8.707l-5.146 5.147a.5.5 0 0 1-.708-.708L7.293 8z"/>
                </svg>
              </button>
            </div>
            <div class="location-picker-wrapper">
              <LocationPiker />
            </div>
            
            <div class="location-picker-fullscreen-actions">
              <button type="button" class="btn btn-secondary-location" @click="activeTab = 'info'">Cancel</button>
              <button type="button" class="btn btn-primary-location" @click="confirmLocationAndBack">Confirm Location</button>
            </div>
          </div>
        </div>
    </div> <!-- container -->
  </div>
</template>

<script setup>
import { ref, onMounted,computed, } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthHandle } from '../composables/authHandle'
import  useOrder from '../composables/useOrder'
import useCart from '../composables/useCart'
import LocationPiker from '../components/LocationPiker.vue'
import {
  selectedProvince,
  selectedDistrict,
  selectedWard,
  streetAddress,
} from '../composables/locationPiker'

const activeTab = ref('info')
const router = useRouter()
const {fetchProfile, profile } = useAuthHandle()
const { createOrder } = useOrder()
const { items, fetchItemToCart, updateQuantity, deleteItemFromCart } = useCart();
const selectedPaymentMethod = ref('COD')

const confirmLocationAndBack = () => {
  const locationParts = [
    streetAddress.value,
    selectedWard.value?.name, 
    selectedDistrict.value?.name, 
    selectedProvince.value?.name
  ].filter(Boolean)
  
  const location = locationParts.join(', ')
  console.log('Selected location:', location)
  
  profile.value.address = location
  
  activeTab.value = 'info'
}

const products = computed(() => {
  if (!items.value || items.value.length === 0) return [];
  
  return items.value.map(item => ({
    cartCode: item.cartCode,
    id: item.variantId,
    name: item.productName,
    price: item.price,
    quantity: item.quantity,
    imageUrl: item.imageUrl,
    size: item.size
  }));
})

const removeItem = async (itemID) => {
  await deleteItemFromCart(itemID);
}

const increaseQuantity = async (id, quantity) => {
  await updateQuantity(id, quantity + 1);
  const item = cartItems.value.find(item => item.id === id)
  if (item) {
    item.quantity++
  }
}

const decreaseQuantity = async (id, quantity) => {
  await updateQuantity(id, quantity - 1);
  const item = cartItems.value.find(item => item.id === id)
  if (item && item.quantity > 1) {
    item.quantity--
  }
}

const totalPrice = computed(() => {
  return products.value.reduce((total, item) => {
    const price = typeof item.price === 'string' 
      ? parseInt(item.price.replace(/\D/g, '')) 
      : item.price;
    return total + (price);
  }, 0)
})

const createOrderHandler = async () => {
  await createOrder(
    profile.value.address,
    selectedPaymentMethod.value,
    'voucherId'
  )
}

onMounted(() => {
  fetchProfile(),
  fetchItemToCart()
})

const goToBuyMore = () => {
  router.push('/')
  emit('close')
}
</script>
