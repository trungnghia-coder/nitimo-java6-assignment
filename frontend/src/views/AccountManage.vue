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
              
              <div class="text-center py-5">
                <img src="https://i.imgur.com/g8f0gN4.png" alt="No orders" class="img-fluid" style="width: 100px; opacity: 0.5;">
                <p class="text-muted mt-3">You have no orders yet.</p>
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
</template>

<script setup>
import '../assets/css/accountManage.css'
import { useAuthHandle } from '../composables/authHandle'
import { ref, computed , onMounted} from 'vue'
import { useRouter } from 'vue-router'
import LocationPiker from '../components/LocationPiker.vue'
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
})
</script>