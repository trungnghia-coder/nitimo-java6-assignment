<template>
    <div class="top-bar bg-f27127 text-white py-2 w-100" style="z-index: 1020; left: 0; right: 0;">
        <div class="container-fluid px-4 d-flex justify-content-between align-items-center">
        <span>Hotline: 0906527626 (8h - 12h, 13h30 - 17h)</span>
        <span>Liên hệ</span>
        </div>
    </div>

    <!-- Main Header - Full Width Sticky -->
    <header class="header navbar navbar-expand-lg navbar-light border-bottom sticky-top w-100" style="z-index: 1019; left: 0; right: 0;">
        <div class="container-fluid px-4">
            <!-- Logo -->
            <a class="navbar-brand me-5" href="/">
                <div class="brand-logo" style="font-weight: bold; font-size: 40px; color: #f27127;">
                LAWAN
                </div>
            </a>

            <!-- Navigation Menu -->
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mx-auto fw-bold fs-5">
                <li class="nav-item">
                    <router-link to="/" class="nav-link">Trang Chủ</router-link>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown1" role="button" data-bs-toggle="dropdown">
                    Thời Trang Nữ
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown1">
                        <li v-for="category in categories" :key="category.categoryId">
                            <router-link class="dropdown-item" :to="`/products?category=${category.categoryCode}`">
                                {{ category.categoryName }}
                            </router-link>
                        </li>
                    </ul>
                </li>
                <li class="nav-item">
                    <router-link to="/promotion" class="nav-link">Đồng Phục</router-link>
                </li>
                <li class="nav-item">
                    <router-link to="/about" class="nav-link">Giới Thiệu</router-link>
                </li>
                </ul>
            </div>

            <!-- Right Icons -->
            <div class="ms-4 d-flex align-items-center gap-3">
                <!-- Account -->
                <button @click="goToUserPage" class="btn btn-link p-0">
                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-person-circle c-black" viewBox="0 0 16 16">
                        <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0"/>
                        <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8m8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1"/>
                    </svg>
                </button>
                <!-- Search -->
                <button class="btn btn-link p-0 ">
                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-search c-black" viewBox="0 0 16 16">
                        <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
                    </svg>
                </button>
                <!-- Shopping Cart -->
                <div class="position-relative">
                <button class="btn btn-link p-0" @click="openCart">
                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-cart c-black" viewBox="0 0 16 16">
                        <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5M3.102 4l1.313 7h8.17l1.313-7zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4m7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4m-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2m7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2"/>
                    </svg>
                </button>
                <span v-if="cartCount > 0" class="badge bg-danger position-absolute top-0 start-100 translate-middle">{{ cartCount }}</span>
                </div>
            </div>
        </div>
    </header>
</template>

<script setup>
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { isUserLoggedIn } from '../composables/authHandle'
import useCategory from '../composables/useCategory'
import useCart from '../composables/useCart'
const router = useRouter()

const emit = defineEmits(['open-cart'])

const { categories } = useCategory()
const { cartCount, fetchItemToCart } = useCart()

onMounted(() => {
  fetchItemToCart()
})

const openCart = () => {
  emit('open-cart')
}

const goToUserPage = async () => {
  const loggedIn = await isUserLoggedIn()
  
  if (loggedIn) {
    router.push('/account')
  } else {
    router.push('/auth')
  }
}
</script>