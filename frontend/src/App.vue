<template>
  <div id="app" class="d-flex flex-column min-vh-100">
    <Header v-if="!hideHeaderFooter" @open-cart="isCartOpen = true" />
    <main class="flex-grow-1">
      <router-view></router-view>
    </main>
    <Footer v-if="!hideHeaderFooter" />
    
    <CartSlide :isOpen="isCartOpen" @close="isCartOpen = false" />
  </div>
</template>


<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import Header from './components/Header.vue'
import Footer from './components/Footer.vue'
import CartSlide from './components/CartSlide.vue'

const isCartOpen = ref(false)
const route = useRoute()

const hideHeaderFooter = computed(() => {
  return route.meta.hideHeaderFooter || false
})
</script>