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
              
              <form class="form info-form">
                <div class="form-group full-width">
                  <label class="form-label">Full Name</label>
                  <input type="text" class="form-control" placeholder="Your full name"/>
                </div>
                <div class="form-group">
                  <label class="form-label">Email</label>
                  <input type="email" class="form-control" placeholder="Your email"/>
                </div>
                <div class="form-group">
                  <label class="form-label">Phone Number</label>
                  <input type="text" class="form-control" placeholder="Your number" />
                </div>
                <div class="form-group full-width">
                  <label class="form-label">Address</label>
                  <input type="text" class="form-control" placeholder="Your address" />
                </div>
                <button type="button" class="btn btn-orange w-100 mt-3 fw-bold">Update</button>
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
      </div>
    </div>
  </div>
</template>

<script setup>
import '../assets/css/accountManage.css'
import { useAuthHandle } from '../composables/authHandle'
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'

const activeTab = ref('info')
const router = useRouter()

const oldPassword = ref('')
const newPassword = ref('')
const confirmPassword = ref('')

const { logout, passwordChange } = useAuthHandle()

const handlePasswordChange = async () => {
  await passwordChange(oldPassword.value, newPassword.value, confirmPassword.value)
}
  
const handleLogout = async () => { 
  await logout()
}

function goToManagement() {
  router.push('/manage')
}
</script>