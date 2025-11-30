<template>
  <div class="product-detail-container py-5">
    <!-- Loading Spinner -->
    <div v-if="loadingDetail" class="text-center py-5">
      <div class="spinner-border text-primary" style="width: 3rem; height: 3rem;" role="status">
        <span class="visually-hidden">Đang tải...</span>
      </div>
    </div>

    <!-- Product Content -->
    <div v-else-if="product.id" class="container">
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

            <!-- Product Name -->
            <h1 class="product-name">{{ product.name }}</h1>

            <!-- Price Section -->
            <div class="price-section bg-light p-3 rounded mb-4">
              <div class="d-flex align-items-center gap-3 mb-2">
                <span class="price-current fw-bold">{{ formatPrice(product.currentPrice) }}đ</span>
                <span 
                  v-if="product.discount > 0"
                  class="price-original text-muted text-decoration-line-through">
                  {{ formatPrice(product.originalPrice) }}đ
                </span>
              </div>
              <div v-if="product.discount > 0" class="discount-info">
                <strong>Tiết kiệm: {{ formatPrice(product.discountAmount) }}đ</strong>
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

    <!-- Error State: Không tìm thấy sản phẩm -->
    <div v-else class="container text-center py-5">
      <h3>Không tìm thấy sản phẩm</h3>
      <p class="text-muted">Sản phẩm bạn đang tìm không tồn tại hoặc đã bị xóa.</p>
      <router-link to="/" class="btn btn-primary">Về trang chủ</router-link>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import useProduct from '../composables/useProduct'

const { productDetail, loadingDetail, fetchProductDetail } = useProduct();
const route = useRoute()

// State
const mainImage = ref('')
const selectedSize = ref('')
const quantity = ref(1)

// Computed: Chuyển đổi productDetail từ backend thành format cho template
const product = computed(() => {
  if (!productDetail.value) {
    return {
      id: '',
      name: '',
      images: [],
      currentPrice: 0,
      originalPrice: 0,
      discount: 0,
      stock: 0,
      sizes: [],
      colors: [],
      description: ''
    }
  }

  const detail = productDetail.value;
  
  // Lấy danh sách ảnh từ images array
  const imageList = detail.images?.map(img => img.imageUrl) || [];
  
  // Lấy danh sách size từ size array
  const sizeList = detail.size?.map(s => s.sizeName) || [];
  
  // Tính tổng stock từ tất cả variants
  const totalStock = detail.productVariant?.reduce((sum, variant) => sum + (variant.stock || 0), 0) || 0;
  
  // Backend lưu: productPrice = giá sau giảm, discount = số tiền giảm
  const discountAmount = detail.discount || 0; 
  const currentPrice = detail.productPrice - discountAmount; 
  const originalPrice = detail.productPrice; 
  
  const discountPercent = originalPrice > 0 
    ? Math.round((discountAmount / originalPrice) * 100)
    : 0;

  return {
    id: detail.productId,
    name: detail.productName,
    images: imageList,
    currentPrice: currentPrice,
    originalPrice: originalPrice,
    discount: discountPercent, // % giảm giá (26)
    discountAmount: discountAmount, // Số tiền giảm (50000)
    stock: totalStock,
    sizes: sizeList,
    description: detail.productDescription || 'Chưa có mô tả'
  }
});

// Watch: Khi product thay đổi, set ảnh chính và size mặc định
watch(product, (newProduct) => {
  if (newProduct.images.length > 0 && !mainImage.value) {
    mainImage.value = newProduct.images[0];
  }
  if (newProduct.sizes.length > 0 && !selectedSize.value) {
    selectedSize.value = newProduct.sizes[0];
  }
}, { immediate: true });

// Load product detail khi component mount
onMounted(async () => {
  const productCode = route.params.id; 
  if (productCode) {
    await fetchProductDetail(productCode); 
  }
});

// Methods
const formatPrice = (price) => {
  return new Intl.NumberFormat('vi-VN').format(price)
}

const addToCart = () => {
  if (!product.value.id) {
    alert('Sản phẩm chưa được tải!');
    return;
  }
  
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
