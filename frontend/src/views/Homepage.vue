<template>
<div id="carouselExamples" class="carousel slide" data-bs-ride="carousel">
      <!-- Indicators -->
      <div class="carousel-indicators">
          <button type="button" data-bs-target="#carouselExamples" data-bs-slide-to="0" class="active"></button>
          <button type="button" data-bs-target="#carouselExamples" data-bs-slide-to="1"></button>
          <button type="button" data-bs-target="#carouselExamples" data-bs-slide-to="2"></button>
      </div>

      <!-- Slides -->
      <div class="carousel-inner">
          <div class="carousel-item active">
          <img src="../assets/img/img4.webp" class="d-block w-100" alt="Slide 1">
          </div>
          <div class="carousel-item">
          <img src="../assets/img/img5.webp" class="d-block w-100" alt="Slide 2">
          </div>
          <div class="carousel-item">
          <img src="../assets/img/img6.webp" class="d-block w-100" alt="Slide 3">
          </div>
      </div>

      <!-- Controls -->
      <button class="carousel-control-prev" type="button" data-bs-target="#carouselExamples" data-bs-slide="prev">
          <span class="carousel-control-prev-icon"></span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#carouselExamples" data-bs-slide="next">
          <span class="carousel-control-next-icon"></span>
      </button>
  </div>
  <div class="homepage">
    <section class="products-section py-5">
      <div class="container">
        <div class="row g-4">
          <!-- Product Card -->
          <div v-for="product in products" :key="product.productId" class="col-lg-3 col-md-6">
            <router-link :to="{ name: 'ProductDetail', params: { id: product.productId } }" class="product-link">
              <div class="product-card">
              <!-- Discount Badge -->
              <div class="discount-badge" v-if="product.discount > 0">-{{ calculateDiscountPercent(product.productPrice, product.discount) }}%</div>
              
              <!-- Product Image -->
              <div class="product-image position-relative">
                <img 
                  :src="getPrimaryImage(product.images)" 
                  :alt="product.productName" 
                  class="w-100 img-fluid">
              </div>

              <!-- Product Info -->
              <div class="p-3">
                
                <!-- Rating & Reviews -->
                <div class="d-flex align-items-center gap-2 mb-2" v-if="getSecondaryImages(product.images).length > 0">
                  <div class="customer-avatars d-flex">
                    <img 
                      v-for="(image, index) in getSecondaryImages(product.images)" 
                      :key="index"
                      :src="image.imageUrl" 
                      :alt="`${product.productName} - Image ${index + 1}`" 
                      class="avatar">
                  </div>
                </div>

                <!-- Product Title -->
                <h5 class="product-title mb-2">{{ product.productName }}</h5>

                <!-- Price -->
                <div class="price-section">
                  <span class="price-current fw-bold">{{ formatPrice(calculateCurrentPrice(product.productPrice, product.discount)) }}đ</span>
                  <span 
                    class="price-original text-muted text-decoration-line-through ms-2"
                    v-if="product.discount > 0">
                    {{ formatPrice(product.productPrice) }}đ
                  </span>
                </div>
              </div>
            </div>
            </router-link>
          </div>
        </div>

        <!-- Loading Spinner -->
        <div v-if="loading" class="text-center my-4">
          <div class="spinner-border text-primary" role="status">
            <span class="visually-hidden">Đang tải...</span>
          </div>
        </div>

        <!-- Scroll Observer (invisible element to trigger load more) -->
        <div ref="scrollObserver" class="scroll-observer"></div>
      </div>
    </section>
  </div>
</template>

<script setup>
import '../assets/css/homepage.css'
import { ref, onMounted, onUnmounted } from 'vue'
import useProduct from '../composables/useProduct'

const { products, loading, hasMore, fetchProducts } = useProduct();
const scrollObserver = ref(null);

const getPrimaryImage = (images) => {
  if (!images || images.length === 0) {
    return 'https://via.placeholder.com/450';
  }
  const primaryImage = images.find(img => img.isPrimary);
  return primaryImage ? primaryImage.imageUrl : images[0].imageUrl;
}

const getSecondaryImages = (images) => {
  if (!images || images.length === 0) {
    return [];
  }
  return images
    .filter(img => !img.isPrimary)
    .slice(0, 3);
}
const formatPrice = (price) => {
  return new Intl.NumberFormat('vi-VN').format(price);
}

const calculateCurrentPrice = (originalPrice, discountAmount) => {
  return originalPrice - discountAmount;
}

const calculateDiscountPercent = (originalPrice, discountAmount) => {
  return originalPrice > 0 ? Math.round((discountAmount / originalPrice) * 100) : 0;
}

let observer = null;

onMounted(() => {
  observer = new IntersectionObserver((entries) => {
    if (entries[0].isIntersecting && hasMore.value && !loading.value) {
      fetchProducts(); 
    }
  }, {
    rootMargin: '200px'
  });

  if (scrollObserver.value) {
    observer.observe(scrollObserver.value);
  }
});

onUnmounted(() => {
  if (observer && scrollObserver.value) {
    observer.unobserve(scrollObserver.value);
  }
});

</script>
