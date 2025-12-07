<template>
  <div v-if="isVisible" class="modal-backdrop-custom show" @click.self="$emit('close')">
    <div class="modal-dialog modal-xl modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header bg-dark text-white">
          <h5 class="modal-title">Order Details: {{ orderId || 'N/A' }}</h5>
          <button type="button" class="btn-close btn-close-white" @click="$emit('close')"></button>
        </div>
        <div class="modal-body p-4">
          <div class="order-meta text-muted small mb-3 pb-2 border-bottom">
            <i class="bi bi-calendar3"></i> Order Date: {{ orderDate ? new Date(orderDate).toLocaleString('en-US') : 'N/A' }}
          </div>

          <div class="row g-3 mb-4">
            <!-- Payment Info -->
            <div class="col-md-6">
              <div class="card border h-100">
                <div class="card-body">
                  <h6 class="card-subtitle mb-3 text-uppercase fw-semibold text-muted small">
                    <i class="bi bi-credit-card"></i> Payment
                  </h6>
                  <p class="fw-bold mb-2">{{ paymentMethod || 'N/A' }}</p>
                  <p class="small text-muted mb-3">Status: {{ paymentStatus || 'N/A' }}</p>
                  <hr>
                  <div class="d-flex justify-content-between mb-2 small">
                    <span>Subtotal (products):</span>
                    <span class="fw-semibold">{{ subtotal.toLocaleString() }}₫</span>
                  </div>
                  <div class="d-flex justify-content-between mb-2 small text-success">
                    <span>Shipping Fee:</span>
                    <span class="fw-semibold">{{ shippingFee.toLocaleString() }}₫</span>
                  </div>
                  <div class="d-flex justify-content-between mb-2 small text-danger">
                    <span>Discount (voucher):</span>
                    <span class="fw-semibold">-{{ discountAmount.toLocaleString() }}₫</span>
                  </div>
                  <hr class="border-dashed">
                  <div class="d-flex justify-content-between align-items-center">
                    <span class="fw-bold text-uppercase small">Total:</span>
                    <span class="fw-bold text-danger fs-5">{{ total.toLocaleString() }}₫</span>
                  </div>
                </div>
              </div>
            </div>

            <!-- Recipient Info -->
            <div class="col-md-6">
              <div class="card border h-100">
                <div class="card-body">
                  <h6 class="card-subtitle mb-3 text-uppercase fw-semibold text-muted small">
                    <i class="bi bi-person-circle"></i> Recipient
                  </h6>
                  <p class="fw-bold mb-1">{{ recipientName || 'N/A' }}</p>
                  <p class="mb-1 small"><i class="bi bi-telephone"></i> {{ recipientPhone || 'N/A' }}</p>
                  <p class="mb-0 small text-muted">
                    <i class="bi bi-geo-alt"></i> {{ recipientAddress || 'N/A' }}
                  </p>
                </div>
              </div>
            </div>
          </div>

          <!-- Products Table -->
          <div class="table-responsive" style="max-height: 300px; overflow-y: auto;">
            <table class="table table-hover table-bordered align-middle mb-0">
              <thead class="table-light sticky-top">
                <tr class="small">
                  <th style="width: 35%">Product</th>
                  <th style="width: 13%" class="text-center">Quantity</th>
                  <th style="width: 17%" class="text-end">Unit Price</th>
                  <th style="width: 17%" class="text-end">Discount</th>
                  <th style="width: 18%" class="text-end">Subtotal</th>
                </tr>
              </thead>
              <tbody>
                <tr v-if="!products || products.length === 0">
                  <td colspan="5" class="text-center text-muted py-4">
                    <i class="bi bi-inbox"></i> No products found for this order.
                  </td>
                </tr>
                <tr v-else v-for="(item, i) in products" :key="item.productCode || i">
                  <td>
                    <div class="fw-semibold small">{{ item.productName || 'N/A' }}</div>
                    <div class="text-muted" style="font-size: 0.75rem;">Code: {{ item.productCode || 'N/A' }}</div>
                  </td>
                  <td class="text-center">{{ item.quantity || 0 }}</td>
                  <td class="text-end">{{ (item.unitPrice || 0).toLocaleString() }}₫</td>
                  <td class="text-end text-danger">{{ (item.discount || 0).toLocaleString() }}₫</td>
                  <td class="text-end fw-semibold">{{ (item.subTotal || 0).toLocaleString() }}₫</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import "../assets/css/orderDetailModal.css"
const props = defineProps({
  isVisible: { type: Boolean, required: true },
  orderId: { type: String, default: "" },
  orderDate: { type: String, default: "" },
  orderStatus: { type: String, default: "" },
  recipientName: { type: String, default: "" },
  recipientPhone: { type: String, default: "" },
  recipientAddress: { type: String, default: "" },
  products: { type: Array, default: () => [] },
  paymentMethod: { type: String, default: "" },
  paymentStatus: { type: String, default: "" },
  shippingFee: { type: Number, default: 0 },
  discountAmount: { type: Number, default: 0 },
  subtotal: { type: Number, default: 0 },
  total: { type: Number, default: 0 }
})
const emit = defineEmits(['close'])
</script>