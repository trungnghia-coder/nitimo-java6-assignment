<template>
  <div class="account-page-bg">
    <div class="container-management">
      <h1 class="h1-account mb-4">My Account</h1>

      <div class="account-layout">
        <aside class="account-sidebar">
          <ul class="sidebar-menu list-unstyled">
            <li 
              :class="['sidebar-item', activeTab === 'info' ? 'active' : '']" 
              @click="activeTab = 'info'"
            >
              Account Information
            </li>
            
            <li 
              :class="['sidebar-item', activeTab === 'orders' ? 'active' : '']" 
              @click="activeTab = 'orders'"
            >
              Order History
            </li>

            <li 
              :class="['sidebar-item', activeTab === 'security' ? 'active' : '']" 
              @click="activeTab = 'security'"
            >
              Change Password
            </li>

            <li 
              :class="['sidebar-item', activeTab === 'management' ? 'active' : '']" 
              @click="goToManagement"
            >
              Management Page
            </li>

            <li class="sidebar-item" @click="handleLogout">
              Logout
            </li>
          </ul>
        </aside>

        <main class="account-content">
          <!-- Account Information Tab -->
          <div class="card border" v-show="activeTab === 'info'">
            <div class="card-body">
              <h2 class="card-title h5 mb-3 pb-2 border-bottom">Account Information</h2>
              
              <div v-if="errorMessage" class="alert alert-danger mb-3">{{ errorMessage }}</div>
              
              <form class="form info-form" @submit.prevent="handleInformationUpdate">
                <div class="form-group full-width">
                  <label class="form-label">Full Name</label>
                  <input v-model="profile.fullName" type="text" class="form-control" placeholder="Your full name"/>
                </div>
                <div class="form-group">
                  <label class="form-label">Email</label>
                  <input v-model="profile.email" type="email" class="form-control" placeholder="Your email"/>
                </div>
                <div class="form-group">
                  <label class="form-label">Phone Number</label>
                  <input v-model="profile.phone" type="text" class="form-control" placeholder="Your number" />
                </div>
                <div class="form-group full-width">
                  <label class="form-label">Address</label>
                  <div class="d-flex gap-2">
                    <input v-model="profile.address" type="text" class="form-control flex-grow-1" placeholder="Your address" />
                    <button type="button" class="btn btn-secondary" @click="activeTab = 'location'">Change Location</button>
                  </div>
                </div>

                
                <button type="submit" class="btn btn-orange w-100 mt-3 fw-bold">Update</button>
              </form>
            </div>
          </div>

          <!-- Order History Tab -->
          <div class="card border" v-show="activeTab === 'orders'">
            <div class="card-body">
              <h2 class="card-title h5 mb-3 pb-2 border-bottom">Order History</h2>
              
              <!-- Orders Table -->
              <div class="table-responsive" style="max-height: 500px; overflow-y: auto;">
                <table class="table table-hover align-middle mb-0">
                  <thead class="table-light">
                    <tr>
                      <th>Order ID</th>
                      <th>Date</th>
                      <th>Total</th>
                      <th>Payment</th>
                      <th>Status</th>
                      <th>Action</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="item in orders" :key="item.orderId">
                      <td>
                        <span class="fw-semibold text-truncate d-inline-block" style="max-width: 120px;" :title="item.orderId">
                          {{ item.orderId ? item.orderId.substring(0, 10) + '...' : 'N/A' }}
                        </span>
                      </td>
                      <td>{{ formatDate(item.orderDate) }}</td>
                      <td class="fw-bold text-danger">{{ formatCurrency(item.orderAmount) }}</td>
                      <td>
                        <span 
                          class="badge"
                          :class="{
                            'bg-warning text-dark': item.paymentMethod === 'COD',
                            'bg-primary': item.paymentMethod === 'BANK',
                            'bg-success': item.paymentMethod === 'MOMO'
                          }"
                        >
                          {{ item.paymentMethod }}
                        </span>
                      </td>
                      <td>
                        <span 
                          class="badge"
                          :class="{
                            'bg-success': item.orderStatus === 'PENDING',
                            'bg-info': item.orderStatus === 'CONFIRMED',
                            'bg-primary': item.orderStatus === 'SHIPPING',
                            'bg-secondary': item.orderStatus === 'COMPLETED',
                            'bg-danger': item.orderStatus === 'CANCELLED'
                          }"
                        >
                          {{ item.orderStatus }}
                        </span>
                      </td>
                      <td>
                        <button class="btn btn-sm btn-outline-primary" @click="openOrderDetails(item.orderId)">View Details</button>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>

          <!-- Change Password Tab -->
          <div class="card border" v-show="activeTab === 'security'">
            <div class="card-body">
              <h2 class="card-title h5 mb-3 pb-2 border-bottom">Change Password</h2>
              
              <div v-if="errorMessage" class="alert alert-danger mb-3">{{ errorMessage }}</div>
              
              <form class="form security-form" @submit.prevent="handlePasswordChange">
                <div class="form-group">
                  <label class="form-label">Current Password</label>
                  <input v-model="oldPassword" type="password" class="form-control" placeholder="Current Password" />
                </div>
                <div class="form-group">
                  <label class="form-label">New Password</label>
                  <input v-model="newPassword"  type="password" class="form-control" placeholder="New Password"/>
                </div>
                <div class="form-group full-width">
                  <label class="form-label">Confirm New Password</label>
                  <input v-model="confirmPassword" type="password" class="form-control" placeholder="Confirm New Password"/>
                </div>
                <button type="submit" class="btn btn-orange w-100 mt-3 fw-bold">Change Password</button>
              </form>
            </div>
          </div>
        </main>
        
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
import '../assets/css/accountManage.css'
import { useAuthHandle } from '../composables/authHandle'
import  useOrder  from '../composables/useOrder'
import { ref, computed , onMounted} from 'vue'
import { useRouter } from 'vue-router'
import LocationPiker from '../components/LocationPiker.vue'
import OrderDetailModal from '../components/OrderDetailModal.vue'
import useOrderDetail from '../composables/useOrderDetail'
import {
  selectedProvince,
  selectedDistrict,
  selectedWard,
  streetAddress,
} from '../composables/locationPiker'

const activeTab = ref('info')
const router = useRouter()

const oldPassword = ref('')
const newPassword = ref('')
const confirmPassword = ref('')

const { logout, passwordChange, fetchProfile, profile, informationUpdate, errorMessage } = useAuthHandle()
const { orders,  fetchOrderHistory } = useOrder()


// Format date to DD/MM/YYYY
const formatDate = (dateString) => {
  if (!dateString) return 'N/A'
  const date = new Date(dateString)
  const day = String(date.getDate()).padStart(2, '0')
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const year = date.getFullYear()
  return `${day}/${month}/${year}`
}

// Format currency to Vietnamese format
const formatCurrency = (amount) => {
  if (!amount && amount !== 0) return '0₫'
  return new Intl.NumberFormat('vi-VN', { 
    style: 'currency', 
    currency: 'VND' 
  }).format(amount)
}

const handlePasswordChange = async () => {
  const success = await passwordChange(oldPassword.value, newPassword.value, confirmPassword.value)
  if (success) {
    oldPassword.value = ''
    newPassword.value = ''
    confirmPassword.value = ''
  }
}

const handleInformationUpdate = async () => {
  const success = await informationUpdate(profile.value.fullName, profile.value.email, profile.value.phone, profile.value.address)
  if (success) {
    // Profile updated successfully
  }
}
  
const handleLogout = async () => { 
  await logout()
}

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

function goToManagement() {
  router.push('/manage')
}

onMounted(() => {
  fetchProfile()
  fetchOrderHistory()
})

const { infoOrderDetail, fetchOrderDetail } = useOrderDetail()

const isModalOpen = ref(false)
const viewingOrder = ref(null)

const openOrderDetails = async (orderId) => {
  // Fetch data với orderId từ parameter
  await fetchOrderDetail(orderId)
  
  console.log('Fetching order:', orderId)
  console.log('Order detail data:', infoOrderDetail.value)
  
  if (infoOrderDetail.value) {
    const orderData = infoOrderDetail.value 
    
    const products = orderData.items ? orderData.items.map(item => ({
      productName: item.productName || item.name || "N/A",
      productCode: item.productCode || item.code || "N/A",
      quantity: item.quantity || 0,
      unitPrice: item.unitPrice || item.price || 0,
      discount: item.discountProduct || 0,
      subTotal: item.subtotalProduct || (item.quantity * (item.unitPrice || item.price || 0))
    })) : []
    
    const subtotal = products.reduce((sum, item) => sum + item.subTotal, 0)
    
    viewingOrder.value = {
      orderId: orderId, // Sửa từ orderId.value → orderId
      orderDate: orderData.orderDate || new Date().toISOString(),
      recipientName: orderData.username || "N/A",
      recipientPhone: orderData.phone || "N/A",
      recipientAddress: orderData.address || "N/A",
      products: products,
      paymentMethod: orderData.paymentMethod || "COD",
      paymentStatus: orderData.paymentStatus || "UNPAID",
      shippingFee: orderData.shippingFee || 0,
      discountAmount: orderData.discount || 0,
      subtotal: subtotal,
      total: orderData.total || 0
    }
    
    console.log('Viewing order data:', viewingOrder.value)
  } else {
    console.error('No order data found for:', orderId)
  }
  
  isModalOpen.value = true
}

const closeModal = () => {
  viewingOrder.value = null
  isModalOpen.value = false
}
</script>