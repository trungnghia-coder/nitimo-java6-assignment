<template>
  <div class="product-detail-container py-5">
    <div class="container">
      <div class="row g-5">
        <!-- Product Images Gallery -->
        <div class="col-lg-5">
          <div class="product-gallery d-flex gap-3">
            <!-- Thumbnail Images (Left Side) -->
            <div class="thumbnail-gallery-vertical d-flex flex-column gap-2">
              <img 
                v-for="(image, index) in product.images" 
                :key="index"
                :src="image"
                :alt="`Product ${index + 1}`"
                class="thumbnail-image"
                :class="{ active: mainImage === image }"
                @click="mainImage = image"
              >
            </div>

            <!-- Main Image (Right Side) -->
            <div class="main-image-wrapper flex-grow-1">
              <img 
                :src="mainImage" 
                :alt="product.name" 
                class="main-image"
              >
              <div class="discount-badge-large" v-if="product.discount">
                {{ product.discount }}%
              </div>
            </div>
          </div>
        </div>

        <!-- Product Info -->
        <div class="col-lg-6">
          <div class="product-info">
            <!-- Rating -->
            <div class="rating-section d-flex align-items-center gap-2 mb-3">
              <div class="stars">⭐⭐⭐⭐⭐</div>
              <span class="rating-text">(2.021 đã bán)</span>
            </div>

            <!-- Product Name -->
            <h1 class="product-name">{{ product.name }}</h1>

            <!-- Price Section -->
            <div class="price-section bg-light p-3 rounded mb-4">
              <div class="d-flex align-items-center gap-3 mb-2">
                <span class="price-current fw-bold">{{ formatPrice(product.currentPrice) }}đ</span>
                <span class="price-original text-muted text-decoration-line-through">
                  {{ formatPrice(product.originalPrice) }}đ
                </span>
              </div>
              <div class="discount-info">
                <strong>Tiết kiệm: {{ formatPrice(product.originalPrice - product.currentPrice) }}đ</strong>
              </div>
            </div>

            <!-- Size Selection -->
            <div class="size-section mb-4">
              <label class="form-label fw-bold">Kích cỡ:</label>
              <div class="size-options d-flex gap-2">
                <button 
                  v-for="size in product.sizes" 
                  :key="size"
                  class="size-btn"
                  :class="{ active: selectedSize === size }"
                  @click="selectedSize = size"
                >
                  {{ size }}
                </button>
              </div>
            </div>

            <!-- Color Selection -->
            <div class="color-section mb-4">
              <label class="form-label fw-bold">Màu sắc:</label>
              <div class="color-options d-flex gap-3">
                <div 
                  v-for="color in product.colors" 
                  :key="color.name"
                  class="color-option"
                  :class="{ active: selectedColor === color.name }"
                  @click="selectedColor = color.name"
                >
                  <div 
                    class="color-circle" 
                    :style="{ backgroundColor: color.code }"
                  ></div>
                  <span class="color-name">{{ color.name }}</span>
                </div>
              </div>
            </div>

            <!-- Quantity Selection -->
            <div class="quantity-section">
              <label class="form-label fw-bold">Số lượng:</label>
              <div class="quantity-selector d-flex align-items-center gap-2">
                <button 
                  class="qty-btn"
                  @click="quantity > 1 && quantity--"
                >
                  −
                </button>
                <input 
                  v-model.number="quantity"
                  type="number"
                  class="qty-input"
                  min="1"
                  max="999"
                >
                <button 
                  class="qty-btn"
                  @click="quantity < product.stock && quantity++"
                >
                  +
                </button>
                <span class="ms-3">Còn lại: {{ product.stock }} sản phẩm</span>
              </div>
            </div>

            <!-- Action Buttons -->
            <div class="action-buttons d-flex gap-3 mb-4">
              <button class="btn btn-add-to-cart flex-grow-1" @click="addToCart">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bag-heart-fill" viewBox="0 0 16 16">
                  <path d="M11.5 4v-.5a3.5 3.5 0 1 0-7 0V4H1v10a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V4zM8 1a2.5 2.5 0 0 1 2.5 2.5V4h-5v-.5A2.5 2.5 0 0 1 8 1m0 6.993c1.664-1.711 5.825 1.283 0 5.132-5.825-3.85-1.664-6.843 0-5.132"/>
                </svg>
                Thêm vào giỏ
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Product Description Section -->
      <div class="row mt-5">
        <div class="col-12">
          <div class="description-section">
            <h3 class="section-title mb-4 pb-3 border-bottom">Chi tiết sản phẩm</h3>

            <!-- Tab Content -->
            <div class="tab-content">
              <!-- Description Tab -->
              <div 
                class="tab-pane fade show active"
                id="description"
                role="tabpanel"
              >
                <div class="description-content">
                  <p>{{ product.description }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()

// Product Data
const product = ref({
  id: 1,
  name: 'Áo khoác lông vũ nữ dáng lộ siêu nhẹ',
  images: [
    'https://cdn.hstatic.net/products/200000503583/cdn01085_1_05c9b02871394e849cbbb413fcfeb97b_grande.jpg',
    'https://cdn.hstatic.net/products/200000503583/cdn01075_e3f46d3081f7468ea00d4a758d87e974_master.jpg',
    'https://cdn.hstatic.net/products/200000503583/cdn01111_a586823a2f244d2d99fe7c6b4705e44d_compact.jpg',
    'https://cdn.hstatic.net/products/200000503583/cdn01156_06c8c7e5f7714322903e61f6a21a0f4a_master.jpg',
  ],
  currentPrice: 795000,
  originalPrice: 1250000,
  discount: 55,
  stock: 150,
  sizes: ['XS', 'S', 'M', 'L', 'XL', 'XXL'],
  colors: [
    { name: 'Đỏ Bordo', code: '#8B1538' },
    { name: 'Đen', code: '#000000' },
    { name: 'Xám', code: '#808080' },
  ],
  description: `Áo khoác lông vũ nữ với thiết kế dáng lộ hiện đại, được làm từ chất liệu lông vũ cao cấp siêu nhẹ. 
  Thiết kế tôn dáng, thoải mái, phù hợp cho mọi mùa. Sản phẩm đã qua kiểm tra chất lượng nghiêm ngặt.
  
  Đặc điểm:
  - Chất liệu lông vũ 100% siêu nhẹ
  - Dáng lộ tôn dáng
  - Khóa kéo êm
  - Túi tay tiện dụng
  - Thiết kế thời trang, phù hợp với nhiều phong cách`,
})

// State
const mainImage = ref(product.value.images[0])
const selectedSize = ref('M')
const selectedColor = ref('Đỏ Bordo')
const quantity = ref(1)

// Methods
const formatPrice = (price) => {
  return price.toLocaleString('vi-VN')
}

const addToCart = () => {
  console.log({
    productId: product.value.id,
    productName: product.value.name,
    size: selectedSize.value,
    color: selectedColor.value,
    quantity: quantity.value,
    price: product.value.currentPrice,
  })
  alert(`Đã thêm ${quantity.value} sản phẩm vào giỏ!`)
}
</script>
