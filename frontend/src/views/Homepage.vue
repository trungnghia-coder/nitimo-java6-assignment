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
          <!-- Product Card 1 -->
          <div class="col-lg-3 col-md-6">
            <router-link :to="{ name: 'ProductDetail', params: { id: 1 } }" class="product-link">
              <div class="product-card">
              <!-- Discount Badge -->
              <div class="discount-badge">-55%</div>
              
              <!-- Product Image -->
              <div class="product-image position-relative">
                <img src="https://cdn.hstatic.net/products/200000503583/cdn01085_1_05c9b02871394e849cbbb413fcfeb97b_grande.jpg" alt="Product 1" class="w-100 img-fluid">
                <div class="rating-badge">⭐ 2021 đã bán</div>
              </div>

              <!-- Product Info -->
              <div class="p-3">
                
                <!-- Rating & Reviews -->
                <div class="d-flex align-items-center gap-2 mb-2">
                  <div class="customer-avatars d-flex">
                    <img src="https://n7media.coolmate.me/uploads/August2025/t-shirt-chay-bo-nu-graphic-sky-4-cam-dao_99.jpg?aio=w-700" alt="avatar" class="avatar">
                    <img src="https://product.hstatic.net/200000503583/product/ao-giu-nhiet-nu__5__a5d0f6afcdb14f3a970c0142de55156f_e7ebc1c7e62c4ebeac4a26b9eceb5763_grande.jpg" alt="avatar" class="avatar">
                    <img src="https://product.hstatic.net/200000503583/product/vay-suong-mac-nha__8__6282f89e0c884a44a19ce78b03273d10_grande.jpg" alt="avatar" class="avatar">
                  </div>
                </div>

                <!-- Product Title -->
                <h5 class="product-title mb-2">Áo khoác lông vũ nữ dáng lộ siêu nhẹ</h5>

                <!-- Price -->
                <div class="price-section">
                  <span class="price-current fw-bold">795.000đ</span>
                  <span class="price-original text-muted text-decoration-line-through ms-2">1.250.000đ</span>
                </div>
              </div>
            </div>
            </router-link>
          </div>

          <div class="col-lg-3 col-md-6">
            <div class="product-card">
              <div class="discount-badge">-55%</div>
              <div class="product-image position-relative">
                <img src="https://cdn.hstatic.net/products/200000503583/cdn01085_1_05c9b02871394e849cbbb413fcfeb97b_grande.jpg" alt="Product 1" class="w-100 img-fluid">
                <div class="rating-badge">⭐ 2021 đã bán</div>
              </div>
              <div class="p-3">
                <div class="d-flex align-items-center gap-2 mb-2">
                  <div class="customer-avatars d-flex">
                    <img src="https://n7media.coolmate.me/uploads/August2025/t-shirt-chay-bo-nu-graphic-sky-4-cam-dao_99.jpg?aio=w-700" alt="avatar" class="avatar">
                    <img src="https://product.hstatic.net/200000503583/product/ao-giu-nhiet-nu__5__a5d0f6afcdb14f3a970c0142de55156f_e7ebc1c7e62c4ebeac4a26b9eceb5763_grande.jpg" alt="avatar" class="avatar">
                    <img src="https://product.hstatic.net/200000503583/product/vay-suong-mac-nha__8__6282f89e0c884a44a19ce78b03273d10_grande.jpg" alt="avatar" class="avatar">
                  </div>
                </div>
                <h5 class="product-title mb-2">Áo khoác lông vũ nữ dáng lộ siêu nhẹ</h5>
                <div class="price-section">
                  <span class="price-current fw-bold">795.000đ</span>
                  <span class="price-original text-muted text-decoration-line-through ms-2">1.250.000đ</span>
                </div>
              </div>
            </div>
          </div>

          <div class="col-lg-3 col-md-6">
            <div class="product-card">
              <div class="discount-badge">-55%</div>
              <div class="product-image position-relative">
                <img src="https://cdn.hstatic.net/products/200000503583/cdn01085_1_05c9b02871394e849cbbb413fcfeb97b_grande.jpg" alt="Product 1" class="w-100 img-fluid">
                <div class="rating-badge">⭐ 2021 đã bán</div>
              </div>
              <div class=" p-3">
                <div class="d-flex align-items-center gap-2 mb-2">
                  <div class="customer-avatars d-flex">
                    <img src="https://n7media.coolmate.me/uploads/August2025/t-shirt-chay-bo-nu-graphic-sky-4-cam-dao_99.jpg?aio=w-700" alt="avatar" class="avatar">
                    <img src="https://product.hstatic.net/200000503583/product/ao-giu-nhiet-nu__5__a5d0f6afcdb14f3a970c0142de55156f_e7ebc1c7e62c4ebeac4a26b9eceb5763_grande.jpg" alt="avatar" class="avatar">
                    <img src="https://product.hstatic.net/200000503583/product/vay-suong-mac-nha__8__6282f89e0c884a44a19ce78b03273d10_grande.jpg" alt="avatar" class="avatar">
                  </div>
                </div>
                <h5 class="product-title mb-2">Áo khoác lông vũ nữ dáng lộ siêu nhẹ</h5>
                <div class="price-section">
                  <span class="price-current fw-bold">795.000đ</span>
                  <span class="price-original text-muted text-decoration-line-through ms-2">1.250.000đ</span>
                </div>
              </div>
            </div>
          </div>

          <div class="col-lg-3 col-md-6">
            <div class="product-card">
              <div class="discount-badge">-55%</div>
              <div class="product-image position-relative">
                <img src="https://cdn.hstatic.net/products/200000503583/cdn01085_1_05c9b02871394e849cbbb413fcfeb97b_grande.jpg" alt="Product 1" class="w-100 img-fluid">
                <div class="rating-badge">⭐ 2021 đã bán</div>
              </div>
              <div class=" p-3">
                <div class="d-flex align-items-center gap-2 mb-2">
                  <div class="customer-avatars d-flex">
                    <img src="https://n7media.coolmate.me/uploads/August2025/t-shirt-chay-bo-nu-graphic-sky-4-cam-dao_99.jpg?aio=w-700" alt="avatar" class="avatar">
                    <img src="https://product.hstatic.net/200000503583/product/ao-giu-nhiet-nu__5__a5d0f6afcdb14f3a970c0142de55156f_e7ebc1c7e62c4ebeac4a26b9eceb5763_grande.jpg" alt="avatar" class="avatar">
                    <img src="https://product.hstatic.net/200000503583/product/vay-suong-mac-nha__8__6282f89e0c884a44a19ce78b03273d10_grande.jpg" alt="avatar" class="avatar">
                  </div>
                </div>
                <h5 class="product-title mb-2">Áo khoác lông vũ nữ dáng lộ siêu nhẹ</h5>
                <div class="price-section">
                  <span class="price-current fw-bold">795.000đ</span>
                  <span class="price-original text-muted text-decoration-line-through ms-2">1.250.000đ</span>
                </div>
              </div>
            </div>
          </div>

          <div class="col-lg-3 col-md-6">
            <div class="product-card">
              <div class="discount-badge">-55%</div>
              <div class="product-image position-relative">
                <img src="https://cdn.hstatic.net/products/200000503583/cdn01085_1_05c9b02871394e849cbbb413fcfeb97b_grande.jpg" alt="Product 1" class="w-100 img-fluid">
                <div class="rating-badge">⭐ 2021 đã bán</div>
              </div>
              <div class=" p-3">
                <div class="d-flex align-items-center gap-2 mb-2">
                  <div class="customer-avatars d-flex">
                    <img src="https://n7media.coolmate.me/uploads/August2025/t-shirt-chay-bo-nu-graphic-sky-4-cam-dao_99.jpg?aio=w-700" alt="avatar" class="avatar">
                    <img src="https://product.hstatic.net/200000503583/product/ao-giu-nhiet-nu__5__a5d0f6afcdb14f3a970c0142de55156f_e7ebc1c7e62c4ebeac4a26b9eceb5763_grande.jpg" alt="avatar" class="avatar">
                    <img src="https://product.hstatic.net/200000503583/product/vay-suong-mac-nha__8__6282f89e0c884a44a19ce78b03273d10_grande.jpg" alt="avatar" class="avatar">
                  </div>
                </div>
                <h5 class="product-title mb-2">Áo khoác lông vũ nữ dáng lộ siêu nhẹ</h5>
                <div class="price-section">
                  <span class="price-current fw-bold">795.000đ</span>
                  <span class="price-original text-muted text-decoration-line-through ms-2">1.250.000đ</span>
                </div>
              </div>
            </div>
          </div>

          <div class="col-lg-3 col-md-6">
            <div class="product-card">
              <div class="discount-badge">-55%</div>
              <div class="product-image position-relative">
                <img src="https://cdn.hstatic.net/products/200000503583/cdn01085_1_05c9b02871394e849cbbb413fcfeb97b_grande.jpg" alt="Product 1" class="w-100 img-fluid">
                <div class="rating-badge">⭐ 2021 đã bán</div>
              </div>
              <div class=" p-3">
                <div class="d-flex align-items-center gap-2 mb-2">
                  <div class="customer-avatars d-flex">
                    <img src="https://n7media.coolmate.me/uploads/August2025/t-shirt-chay-bo-nu-graphic-sky-4-cam-dao_99.jpg?aio=w-700" alt="avatar" class="avatar">
                    <img src="https://product.hstatic.net/200000503583/product/ao-giu-nhiet-nu__5__a5d0f6afcdb14f3a970c0142de55156f_e7ebc1c7e62c4ebeac4a26b9eceb5763_grande.jpg" alt="avatar" class="avatar">
                    <img src="https://product.hstatic.net/200000503583/product/vay-suong-mac-nha__8__6282f89e0c884a44a19ce78b03273d10_grande.jpg" alt="avatar" class="avatar">
                  </div>
                </div>
                <h5 class="product-title mb-2">Áo khoác lông vũ nữ dáng lộ siêu nhẹ</h5>
                <div class="price-section">
                  <span class="price-current fw-bold">795.000đ</span>
                  <span class="price-original text-muted text-decoration-line-through ms-2">1.250.000đ</span>
                </div>
              </div>
            </div>
          </div>

          <div class="col-lg-3 col-md-6">
            <div class="product-card">
              <div class="discount-badge">-55%</div>
              <div class="product-image position-relative">
                <img src="https://cdn.hstatic.net/products/200000503583/cdn01085_1_05c9b02871394e849cbbb413fcfeb97b_grande.jpg" alt="Product 1" class="w-100 img-fluid">
                <div class="rating-badge">⭐ 2021 đã bán</div>
              </div>
              <div class=" p-3">
                <div class="d-flex align-items-center gap-2 mb-2">
                  <div class="customer-avatars d-flex">
                    <img src="https://n7media.coolmate.me/uploads/August2025/t-shirt-chay-bo-nu-graphic-sky-4-cam-dao_99.jpg?aio=w-700" alt="avatar" class="avatar">
                    <img src="https://product.hstatic.net/200000503583/product/ao-giu-nhiet-nu__5__a5d0f6afcdb14f3a970c0142de55156f_e7ebc1c7e62c4ebeac4a26b9eceb5763_grande.jpg" alt="avatar" class="avatar">
                    <img src="https://product.hstatic.net/200000503583/product/vay-suong-mac-nha__8__6282f89e0c884a44a19ce78b03273d10_grande.jpg" alt="avatar" class="avatar">
                  </div>
                </div>
                <h5 class="product-title mb-2">Áo khoác lông vũ nữ dáng lộ siêu nhẹ</h5>
                <div class="price-section">
                  <span class="price-current fw-bold">795.000đ</span>
                  <span class="price-original text-muted text-decoration-line-through ms-2">1.250.000đ</span>
                </div>
              </div>
            </div>
          </div>

          <div class="col-lg-3 col-md-6">
            <div class="product-card">
              <div class="discount-badge">-55%</div>
              <div class="product-image position-relative">
                <img src="https://cdn.hstatic.net/products/200000503583/cdn01085_1_05c9b02871394e849cbbb413fcfeb97b_grande.jpg" alt="Product 1" class="w-100 img-fluid">
                <div class="rating-badge">⭐ 2021 đã bán</div>
              </div>
              <div class=" p-3">
                <div class="d-flex align-items-center gap-2 mb-2">
                  <div class="customer-avatars d-flex">
                    <img src="https://n7media.coolmate.me/uploads/August2025/t-shirt-chay-bo-nu-graphic-sky-4-cam-dao_99.jpg?aio=w-700" alt="avatar" class="avatar">
                    <img src="https://product.hstatic.net/200000503583/product/ao-giu-nhiet-nu__5__a5d0f6afcdb14f3a970c0142de55156f_e7ebc1c7e62c4ebeac4a26b9eceb5763_grande.jpg" alt="avatar" class="avatar">
                    <img src="https://product.hstatic.net/200000503583/product/vay-suong-mac-nha__8__6282f89e0c884a44a19ce78b03273d10_grande.jpg" alt="avatar" class="avatar">
                  </div>
                </div>
                <h5 class="product-title mb-2">Áo khoác lông vũ nữ dáng lộ siêu nhẹ</h5>
                <div class="price-section">
                  <span class="price-current fw-bold">795.000đ</span>
                  <span class="price-original text-muted text-decoration-line-through ms-2">1.250.000đ</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const products = ref([])
</script>

<style scoped>
.homepage {
  background-color: #f8f9fa;
}

.products-section {
  padding-top: 3rem;
  padding-bottom: 3rem;
}

/* Product Card Styles */
.product-card {
  background: white;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;
  position: relative;
}

.product-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
}

/* Discount Badge */
.discount-badge {
  position: absolute;
  top: 12px;
  left: 12px;
  background: black;
  color: white;
  padding: 6px 12px;
  border-radius: 4px;
  font-weight: bold;
  font-size: 14px;
  z-index: 10;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

/* Product Image */
.product-image {
  overflow: hidden;
  background-color: #f5f5f5;
}

.product-image img {
  object-fit: cover;
  width: 100%;
  height: 450px;
  transition: transform 0.3s;
}

.product-card:hover .product-image img {
  transform: scale(1.05);
}

/* Rating Badge */
.rating-badge {
  position: absolute;
  bottom: 12px;
  right: 12px;
  background: rgba(0, 0, 0, 0.7);
  color: white;
  padding: 6px 10px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 500;
}

/* Product Info */
.product-info {
  background: white;
  display: flex;
  flex-direction: column;
}

.product-title {
  color: #333;
  font-size: 14px;
  font-weight: 600;
  line-height: 1.4;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

/* Customer Avatars */
.customer-avatars {
  gap: -8px;
}

.avatar {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  border: 2px solid white;
  object-fit: cover;
  margin-left: -8px;
}

.avatar:first-child {
  margin-left: 0;
}

/* Price Section */
.price-section {
  display: flex;
  align-items: center;
  gap: 8px;
}

.price-current {
  color: black;
  font-size: 20px;
}

.price-original {
  font-size: 13px;
}

.product-link {
  text-decoration: none;
  color: inherit;
}

/* Responsive */
@media (max-width: 768px) {
  .product-card {
    margin-bottom: 1rem;
  }

  .product-image {
    height: 200px;
  }

  .product-info {
    min-height: 250px;
  }
}
</style>
