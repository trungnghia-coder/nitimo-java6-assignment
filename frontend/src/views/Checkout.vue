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

              <div v-for="(item, index) in products" :key="index" class="d-flex align-items-center py-3" :class="{ 'border-bottom': index < products.length - 1 }">
                <img :src="item.imageUrl" alt="img" class="me-3" style="width: 80px; height: 80px; object-fit: cover; border-radius: 6px;" />
                <div class="flex-grow-1">
                  <div class="fw-semibold" style="font-size: 14px; color: #333;">{{ item.name }}</div>
                  <div class="text-muted small mt-1">{{ (item.price).toLocaleString() }}₫</div>
                </div>
                <div class="text-end">
                  <div class="d-flex align-items-center gap-2 mb-2">
                    <button class="btn btn-sm" style="border: 1px solid #ddd; background: white; color: #666; padding: 4px 8px;">−</button>
                    <span style="min-width: 20px; text-align: center;">{{ item.quantity }}</span>
                    <button class="btn btn-sm" style="border: 1px solid #ddd; background: white; color: #666; padding: 4px 8px;">+</button>
                  </div>
                  <div class="small text-muted">Tạm tính: <span class="fw-bold" style="color: #dc3545;">{{ (item.price * item.quantity).toLocaleString() }}₫</span></div>
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
                  <input type="text" class="form-control" placeholder="Họ tên *" style="border: 1px solid #ddd; padding: 10px 12px; border-radius: 6px;" />
                </div>
                <div class="col-12">
                  <input type="email" class="form-control" placeholder="Email *" style="border: 1px solid #ddd; padding: 10px 12px; border-radius: 6px;" />
                </div>
                <div class="col-12">
                  <input type="tel" class="form-control" placeholder="Số điện thoại *" style="border: 1px solid #ddd; padding: 10px 12px; border-radius: 6px;" />
                </div>
              </div>
            </div>
          </div>

          <!-- Address -->
          <div class="card mb-4" style="border: 1px solid #ddd; border-radius: 8px;">
            <div class="card-body p-4">
              <h4 class="mb-4 fw-bold" style="color: #333;">Địa Chỉ Giao Hàng</h4>
              <div class="mb-3">
                <input type="text" class="form-control" placeholder="Địa chỉ *" style="border: 1px solid #ddd; padding: 10px 12px; border-radius: 6px;" />
              </div>
              <div class="mb-3">
                <select class="form-control" style="border: 1px solid #ddd; padding: 10px 12px; border-radius: 6px;">
                  <option disabled>-- Chọn Tỉnh/Thành phố --</option>
                  <option>Hồ Chí Minh</option>
                  <option>Hà Nội</option>
                </select>
              </div>
              <div class="mb-3">
                <select class="form-control" style="border: 1px solid #ddd; padding: 10px 12px; border-radius: 6px;">
                  <option disabled>-- Chọn Phường/Xã --</option>
                  <option>Quận 1</option>
                  <option>Quận 2</option>
                </select>
              </div>
            </div>
          </div>

          <!-- Payment -->
          <div class="card mb-4" style="border: 1px solid #ddd; border-radius: 8px;">
            <div class="card-body p-4">
              <h4 class="mb-4 fw-bold" style="color: #333;">Phương Thức Thanh Toán</h4>

              <div class="mb-3">
                <div class="form-check mb-2">
                  <input class="form-check-input" type="radio" id="payCard" value="BANK" />
                  <label class="form-check-label" for="payCard">Thẻ Tín Dụng / Ghi Nợ</label>
                </div>
                <div class="form-check mb-2">
                  <input class="form-check-input" type="radio" id="payMomo" value="MOMO" />
                  <label class="form-check-label" for="payMomo">Ví MoMo</label>
                </div>
                <div class="form-check">
                  <input class="form-check-input" type="radio" id="payCod" value="cod" />
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
                  <span>250.000₫</span>
                </div>
                <div class="d-flex justify-content-between mb-2 small">
                  <span>Vận chuyển:</span>
                  <span style="color: #28a745;">Miễn phí</span>
                </div>
              </div>

              <!-- Total -->
              <div class="d-flex justify-content-between mb-4">
                <span class="fw-bold" style="font-size: 16px;">TỔNG CỘNG:</span>
                <span class="fw-bold c-f27127" style="font-size: 20px;">250.000₫</span>
              </div>

              <!-- Buttons -->
              <button class="btn w-100 mb-2 fw-bold" style="background-color: #F9943B; color: white; padding: 12px; border-radius: 6px; border: none;">
                THANH TOÁN
              </button>
              <button class="btn w-100" style="border: 1px solid #ddd; color: #666; padding: 12px; border-radius: 6px;">
                Tiếp tục mua sắm
              </button>
            </div>
          </div>
        </div>
      </div> <!-- row -->
    </div> <!-- container -->
  </div>
</template>

<script setup>
import { ref } from 'vue'

const products = ref([])
</script>
