<template>
  <div class="manage-page">
    <!-- Sidebar Menu -->
    <ManageMenu :activeMenu="currentMenu" @select="handleMenuSelect" />

    <!-- Main Content -->
    <div class="manage-content">

      <!-- Products Management -->
      <div v-if="currentMenu === 'products'" class="manage-section">
        <h2 class="section-title">Products Management</h2>

        <!-- Add/Edit Product Form -->
        <div v-if="showAddProductForm" class="form-container card border mb-4">
          <div class="card-body">
            <h3 class="form-title mb-4">{{ editingId ? 'Edit Product' : 'Add New Product' }}</h3>
            
            <form @submit.prevent="handleSaveProduct" class="product-form">
              <div class="row">
                <div class="col-md-6">
                  <div class="form-group mb-3">
                    <label class="form-label">Product Name</label>
                    <input 
                      v-model="productForm.name" 
                      type="text" 
                      class="form-control" 
                      placeholder="Enter product name"
                      required
                    />
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group mb-3">
                    <label class="form-label">Category</label>
                    <select v-model="productForm.category">
                        <option value="">-- Chọn loại hàng --</option>
                        <option v-for="category in categories" :key="category.categoryId" :value="category.categoryId">{{ category.categoryName }}</option>
                    </select>
                  </div>
                </div>
              </div>

              <div class="row">
                <div class="col-md-6">
                  <div class="form-group mb-3">
                    <label class="form-label">Price (₫)</label>
                    <input 
                      v-model="productForm.price" 
                      type="number" 
                      class="form-control" 
                      placeholder="0"
                      min="0"
                      step="1000"
                      required
                    />
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group mb-3">
                    <label class="form-label">Discount (₫)</label>
                    <input 
                      v-model="productForm.discount" 
                      type="number" 
                      class="form-control" 
                      placeholder="0"
                      min="0"
                      step="1000"
                      required
                    />
                  </div>
                </div>
              </div>

              <div class="form-group mb-3">
                <label class="form-label">Description</label>
                <textarea 
                  v-model="productForm.description" 
                  class="form-control" 
                  placeholder="Enter product description"
                  rows="4"
                ></textarea>
              </div>

              <!-- Product Images Upload -->
              <div class="form-group mb-4">
                <label class="form-label">Product Images</label>
                <input 
                  type="file" 
                  class="form-control mb-2" 
                  @change="handleImageUpload"
                  accept="image/*"
                  multiple
                />
                <small class="text-muted">You can select multiple images</small>
                
                <!-- Preview uploaded images -->
                <div v-if="productForm.images && productForm.images.length > 0" class="mt-3">
                  <div class="row g-2">
                    <div v-for="(image, index) in productForm.images" :key="index" class="col-auto">
                      <div class="position-relative" style="width: 100px; height: 100px;">
                        <img 
                          :src="image" 
                          class="img-thumbnail" 
                          style="width: 100%; height: 100%; object-fit: cover;"
                          alt="Product preview"
                        />
                        <button 
                          type="button"
                          class="btn btn-danger btn-sm position-absolute top-0 end-0 m-1"
                          style="width: 28px; height: 28px; padding: 0; display: flex; align-items: center; justify-content: center;"
                          @click="removeImage(index)"
                          title="Delete image"
                        >
                          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" viewBox="0 0 16 16">
                            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z"/>
                            <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z"/>
                          </svg>
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Product Variants (Size & Stock) -->
              <div class="form-group mb-4">
                <label class="form-label fw-bold">Product Variants (Size & Stock)</label>
                <div class="card border">
                  <div class="card-body">
                    <!-- Add Size Button -->
                    <div class="mb-3">
                      <div class="input-group" style="max-width: 400px;">
                        <input 
                          type="text" 
                          class="form-control" 
                          placeholder="Enter size (e.g., M, L, XL)"
                          @keyup.enter="addVariant"
                          v-model="newVariantSize"
                        />
                        <button 
                          type="button" 
                          class="btn btn-orange"
                          @click="addVariant"
                        >
                        Add Size
                        </button>
                      </div>
                    </div>

                    <!-- Variants Table -->
                    <div v-if="productForm.variants && productForm.variants.length > 0" class="table-responsive">
                      <table class="table table-bordered table-sm">
                        <thead class="table-light">
                          <tr>
                            <th width="40%">Size</th>
                            <th width="40%">Stock Quantity</th>
                            <th width="20%">Action</th>
                          </tr>
                        </thead>
                        <tbody>
                          <tr v-for="(variant, index) in productForm.variants" :key="index">
                            <td>
                              <input 
                                v-model="variant.size" 
                                type="text" 
                                class="form-control form-control-sm"
                                placeholder="Size"
                              />
                            </td>
                            <td>
                              <input 
                                v-model="variant.stock" 
                                type="number" 
                                class="form-control form-control-sm"
                                placeholder="Stock"
                                min="0"
                              />
                            </td>
                            <td class="text-center">
                              <button 
                                type="button"
                                class="btn btn-danger btn-sm"
                                @click="removeVariant(index)"
                                title="Delete variant"
                              >
                               <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" viewBox="0 0 16 16">
                                <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z"/>
                                <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z"/>
                              </svg>
                              </button>
                            </td>
                          </tr>
                        </tbody>
                      </table>
                      <div class="alert alert-info mb-0">
                        <small>
                          <i class="fas fa-info-circle"></i> 
                          Total stock: <strong>{{ calculateTotalStock() }}</strong> units
                        </small>
                      </div>
                    </div>
                    <div v-else class="text-muted text-center py-3">
                      <i class="fas fa-box-open"></i> No variants added yet
                    </div>
                  </div>
                </div>
              </div>

              <div class="form-actions">
                <button type="submit" class="btn btn-orange fw-bold">
                  {{ editingId ? 'Update Product' : 'Add Product' }}
                </button>
                <button type="button" class="btn btn-secondary fw-bold ms-2" @click="resetForm">
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>

        <!-- Products Table -->
        <div class="table-container card border">
          <div class="card-body">
            <button class="btn btn-orange fw-bold mb-3" @click="showAddProductForm = !showAddProductForm">
              <i class="fas fa-plus"></i> Add New Product
            </button>
            <div class="table-responsive">
              <table class="table table-hover">
                <thead class="table-light">
                  <tr>
                    <th>Images</th>
                    <th>Product Name</th>
                    <th>Stock</th>
                    <th>Price</th>
                    <th>Variants</th>
                  </tr>
                </thead>
                <tbody>
                  <!-- Loading state -->
                  <tr v-if="loading">
                    <td colspan="6" class="text-center py-4">
                      <div class="spinner-border text-primary" role="status">
                        <span class="visually-hidden">Loading...</span>
                      </div>
                      <p class="mt-2 mb-0 text-muted">Loading products...</p>
                    </td>
                  </tr>
                  <!-- Empty state -->
                  <tr v-else-if="products.length === 0">
                    <td colspan="6" class="text-center py-4 text-muted">
                      <i class="fas fa-box-open fa-3x mb-3"></i>
                      <p class="mb-0">No products found</p>
                    </td>
                  </tr>
                  <!-- Product rows -->
                  <tr v-else v-for="product in products" :key="product.productId">
                    <td>
                      <!-- Display multiple images -->
                      <div class="d-flex gap-1">
                          <img 

                            :key="idx"
                            :src="getFullImageUrl(product.productImage)" 
                            :alt="product.productName"
                            style="width: 40px; height: 40px; object-fit: cover; border-radius: 4px;"
                          />
                      </div>
                    </td>
                    <td class="fw-semibold">{{ product.productName }}</td>
                    <td>{{ product.inventory }}</td>
                    <td>{{ product.categoryName }}</td>
                    <td>
                      <button class="btn btn-sm btn-warning me-2" @click="editProduct(product.productId)">
                        <i class="fas fa-edit"></i> Edit
                      </button>
                      <button class="btn btn-sm btn-danger" @click="handleDeleteProduct(product.productId)">
                        <i class="fas fa-trash"></i> Delete
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
              
              <!-- Pagination Controls -->
              <div v-if="totalPages > 0" class="d-flex justify-content-between align-items-center mt-3">
                <div class="text-muted">
                  Showing {{ products.length }} of {{ totalElements }} products (Page {{ currentPage + 1 }} / {{ totalPages }})
                </div>
                <nav>
                  <ul class="pagination mb-0">
                    <li class="page-item" :class="{ disabled: currentPage === 0 }">
                      <button class="page-link" @click="prevPage" :disabled="currentPage === 0">
                        Previous
                      </button>
                    </li>
                    
                    <!-- Page numbers -->
                    <li 
                      v-for="page in totalPages" 
                      :key="page" 
                      class="page-item" 
                      :class="{ active: currentPage === page - 1 }"
                    >
                      <button class="page-link" @click="goToPage(page - 1)">
                        {{ page }}
                      </button>
                    </li>
                    
                    <li class="page-item" :class="{ disabled: currentPage >= totalPages - 1 }">
                      <button class="page-link" @click="nextPage" :disabled="currentPage >= totalPages - 1">
                        Next
                      </button>
                    </li>
                  </ul>
                </nav>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Users Management -->
      <div v-if="currentMenu === 'users'" class="manage-section">
        <h2 class="section-title">Users Management</h2>

        <!-- Add User Form -->
        <div v-if="showAddUserForm" class="form-container card border mb-4">
          <div class="card-body">
            <h3 class="form-title mb-4">{{ editingUserId ? 'Edit User' : 'Add New User' }}</h3>
            
            <form @submit.prevent="handleSaveUser" class="product-form">
              <div class="row">
                <div class="col-md-6">
                  <div class="form-group mb-3">
                    <label class="form-label">Full Name</label>
                    <input 
                      v-model="userForm.name" 
                      type="text" 
                      class="form-control" 
                      placeholder="Enter full name"
                      required
                    />
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group mb-3">
                    <label class="form-label">Email</label>
                    <input 
                      v-model="userForm.email" 
                      type="email" 
                      class="form-control" 
                      placeholder="Enter email"
                      required
                    />
                  </div>
                </div>
              </div>

              <div class="row">
                <div class="col-md-6">
                  <div class="form-group mb-3">
                    <label class="form-label">Phone</label>
                    <input 
                      v-model="userForm.phone" 
                      type="tel" 
                      class="form-control" 
                      placeholder="Enter phone number"
                      required
                    />
                  </div>
                </div>
                <div class="col-md-3">
                  <div class="form-group mb-3">
                    <label class="form-label">Role</label>
                    <select v-model="userForm.role" class="form-control" required>
                      <option value="user">User</option>
                      <option value="admin">Admin</option>
                    </select>
                  </div>
                </div>
                <div class="col-md-3">
                  <div class="form-group mb-3">
                    <label class="form-label">Status</label>
                    <select v-model="userForm.status" class="form-control" required>
                      <option value="active">Active</option>
                      <option value="inactive">Inactive</option>
                    </select>
                  </div>
                </div>
              </div>

              <div class="form-actions">
                <button type="submit" class="btn btn-orange fw-bold">
                  {{ editingUserId ? 'Update User' : 'Add User' }}
                </button>
                <button type="button" class="btn btn-secondary fw-bold ms-2" @click="resetUserForm">
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
        
        <div class="table-container card border">
          <div class="card-body">
            <button class="btn btn-orange fw-bold mb-3" @click="showAddUserForm = !showAddUserForm">
              <i class="fas fa-plus"></i> Add New User
            </button>
            <div class="table-responsive">
              <table class="table table-hover">
                <thead class="table-light">
                  <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Role</th>
                    <th>Status</th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="user in users" :key="user.id">
                    <td>{{ user.id }}</td>
                    <td class="fw-semibold">{{ user.name }}</td>
                    <td>{{ user.email }}</td>
                    <td>{{ user.phone }}</td>
                    <td><span class="badge" :class="user.role === 'admin' ? 'bg-danger' : 'bg-primary'">{{ user.role }}</span></td>
                    <td><span class="badge" :class="user.status === 'active' ? 'bg-success' : 'bg-secondary'">{{ user.status }}</span></td>
                    <td>
                      <button class="btn btn-sm btn-warning me-2" @click="editUser(user)">
                        <i class="fas fa-edit"></i> Edit
                      </button>
                      <button class="btn btn-sm btn-danger" @click="deleteUser(user.id)">
                        <i class="fas fa-trash"></i> Delete
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>

      <!-- Customers Management -->
      <div v-if="currentMenu === 'customers'" class="manage-section">
        <h2 class="section-title">Customers Management</h2>

        <!-- Add Customer Form -->
        <div v-if="showAddCustomerForm" class="form-container card border mb-4">
          <div class="card-body">
            <h3 class="form-title mb-4">{{ editingCustomerId ? 'Edit Customer' : 'Add New Customer' }}</h3>
            
            <form @submit.prevent="handleSaveCustomer" class="product-form">
              <div class="row">
                <div class="col-md-6">
                  <div class="form-group mb-3">
                    <label class="form-label">Full Name</label>
                    <input 
                      v-model="customerForm.name" 
                      type="text" 
                      class="form-control" 
                      placeholder="Enter full name"
                      required
                    />
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group mb-3">
                    <label class="form-label">Email</label>
                    <input 
                      v-model="customerForm.email" 
                      type="email" 
                      class="form-control" 
                      placeholder="Enter email"
                      required
                    />
                  </div>
                </div>
              </div>

              <div class="row">
                <div class="col-md-6">
                  <div class="form-group mb-3">
                    <label class="form-label">Phone</label>
                    <input 
                      v-model="customerForm.phone" 
                      type="tel" 
                      class="form-control" 
                      placeholder="Enter phone number"
                      required
                    />
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group mb-3">
                    <label class="form-label">Address</label>
                    <input 
                      v-model="customerForm.address" 
                      type="text" 
                      class="form-control" 
                      placeholder="Enter address"
                      required
                    />
                  </div>
                </div>
              </div>

              <div class="form-actions">
                <button type="submit" class="btn btn-orange fw-bold">
                  {{ editingCustomerId ? 'Update Customer' : 'Add Customer' }}
                </button>
                <button type="button" class="btn btn-secondary fw-bold ms-2" @click="resetCustomerForm">
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
        
        <div class="table-container card border">
          <div class="card-body">
            <button class="btn btn-orange fw-bold mb-3" @click="showAddCustomerForm = !showAddCustomerForm">
              <i class="fas fa-plus"></i> Add New Customer
            </button>
            <div class="table-responsive">
              <table class="table table-hover">
                <thead class="table-light">
                  <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Address</th>
                    <th>Total Orders</th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="customer in customers" :key="customer.id">
                    <td>{{ customer.id }}</td>
                    <td class="fw-semibold">{{ customer.name }}</td>
                    <td>{{ customer.email }}</td>
                    <td>{{ customer.phone }}</td>
                    <td>{{ customer.address }}</td>
                    <td class="fw-bold">{{ customer.totalOrders }}</td>
                    <td>
                      <button class="btn btn-sm btn-warning me-2" @click="editCustomer(customer)">
                        <i class="fas fa-edit"></i> Edit
                      </button>
                      <button class="btn btn-sm btn-danger" @click="deleteCustomer(customer.id)">
                        <i class="fas fa-trash"></i> Delete
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>

      <!-- Orders Management -->
      <div v-if="currentMenu === 'orders'" class="manage-section">
        <h2 class="section-title">Orders Management</h2>

        <!-- Add Order Form -->
        <div v-if="showAddOrderForm" class="form-container card border mb-4">
          <div class="card-body">
            <h3 class="form-title mb-4">{{ editingOrderId ? 'Edit Order' : 'Add New Order' }}</h3>
            
            <form @submit.prevent="handleSaveOrder" class="product-form">
              <div class="row">
                <div class="col-md-6">
                  <div class="form-group mb-3">
                    <label class="form-label">Customer Name</label>
                    <input 
                      v-model="orderForm.customer" 
                      type="text" 
                      class="form-control" 
                      placeholder="Enter customer name"
                      required
                    />
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group mb-3">
                    <label class="form-label">Order Date</label>
                    <input 
                      v-model="orderForm.orderDate" 
                      type="date" 
                      class="form-control"
                      required
                    />
                  </div>
                </div>
              </div>

              <div class="row">
                <div class="col-md-4">
                  <div class="form-group mb-3">
                    <label class="form-label">Total Amount</label>
                    <input 
                      v-model="orderForm.totalAmount" 
                      type="number" 
                      class="form-control" 
                      placeholder="0.00"
                      min="0"
                      step="1000"
                      required
                    />
                  </div>
                </div>
                <div class="col-md-4">
                  <div class="form-group mb-3">
                    <label class="form-label">Status</label>
                    <select v-model="orderForm.status" class="form-control" required>
                      <option value="pending">Pending</option>
                      <option value="confirmed">Confirmed</option>
                      <option value="shipping">Shipping</option>
                      <option value="completed">Completed</option>
                      <option value="cancelled">Cancelled</option>
                    </select>
                  </div>
                </div>
                <div class="col-md-4">
                  <div class="form-group mb-3">
                    <label class="form-label">Payment Status</label>
                    <select v-model="orderForm.paymentStatus" class="form-control" required>
                      <option value="paid">Paid</option>
                      <option value="unpaid">Unpaid</option>
                    </select>
                  </div>
                </div>
              </div>

              <div class="form-actions">
                <button type="submit" class="btn btn-orange fw-bold">
                  {{ editingOrderId ? 'Update Order' : 'Add Order' }}
                </button>
                <button type="button" class="btn btn-secondary fw-bold ms-2" @click="resetOrderForm">
                  Cancel
                </button>
              </div>
            </form>
          </div>
        </div>
        
        <div class="table-container card border">
          <div class="card-body">
            <button class="btn btn-orange fw-bold mb-3" @click="showAddOrderForm = !showAddOrderForm">
              <i class="fas fa-plus"></i> Add New Order
            </button>
            <div class="table-responsive">
              <table class="table table-hover">
                <thead class="table-light">
                  <tr>
                    <th>Order ID</th>
                    <th>Customer</th>
                    <th>Order Date</th>
                    <th>Total Amount</th>
                    <th>Status</th>
                    <th>Payment</th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="order in orders" :key="order.id">
                    <td class="fw-semibold">#{{ order.id }}</td>
                    <td>{{ order.customer }}</td>
                    <td>{{ new Date(order.orderDate).toLocaleDateString('vi-VN') }}</td>
                    <td class="fw-bold">{{ order.totalAmount.toLocaleString() }}₫</td>
                    <td>
                      <span class="badge" :class="getOrderStatusBadge(order.status)">
                        {{ order.status }}
                      </span>
                    </td>
                    <td>
                      <span class="badge" :class="order.paymentStatus === 'paid' ? 'bg-success' : 'bg-warning'">
                        {{ order.paymentStatus }}
                      </span>
                    </td>
                    <td>
                      <button class="btn btn-sm btn-warning me-2" @click="editOrder(order)">
                        <i class="fas fa-edit"></i> Edit
                      </button>
                      <button class="btn btn-sm btn-danger" @click="deleteOrder(order.id)">
                        <i class="fas fa-trash"></i> Delete
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import ManageMenu from '../components/ManageMenu.vue'
import useCategory from '../composables/useCategory'
import useProductManagement from '../composables/management/useProductManagement'
import '../assets/css/manage.css'
import api from '../utils/api';
const BASE_URL = api.defaults.baseURL;

const router = useRouter()
const currentMenu = ref('products')
const editingId = ref(null)

const { categories } = useCategory()

// Product Management API
const {
  products,
  loading,
  currentPage,
  totalPages,
  totalElements,
  updateProduct,
  fetchProducts,
  fetchProductDetail,
  createProduct,
  deleteProduct,
  goToPage,
  nextPage,
  prevPage
} = useProductManagement()

onMounted(() => {
  if (currentMenu.value === 'products') {
    fetchProducts(0);
  }
});

const getFullImageUrl = (relativeUrl) => {
    if (!relativeUrl) {
        return 'https://via.placeholder.com/450';
    }
    
    if (relativeUrl.startsWith('/images/')) {
        return BASE_URL + relativeUrl; 
    } 
    return relativeUrl;
};

const handleSaveProduct = async () => {
  try {
    console.log('handleSaveProduct called, editingId:', editingId.value) // Debug
    
    if (editingId.value) {
      console.log('Calling UPDATE') // Debug
      await handleUpdateProduct()
    } else {
      console.log('Calling CREATE') // Debug
      await handleCreateProduct()
    }
  } catch (error) {
    console.error('Error in handleSaveProduct:', error)
    alert('Error: ' + error.message)
  }
}

const handleCreateProduct = async () => {
  try {
    loading.value = true
    const productData = {
      productName: productForm.name,
      categoryCode: productForm.category,
      price: productForm.price,
      discount: productForm.discount,
      description: productForm.description,
      productVariantRequest: productForm.variants.map(v => ({
        size: v.size,
        stockQuantity: v.stock
      }))
    }
    const response = await createProduct(productData, uploadedImageFiles.value)
    
    alert(response.message)
    resetForm()
  } catch (error) {
    alert('Error: ' + error.message)
  } finally {
    loading.value = false
  }
}

const handleUpdateProduct = async () => {
  try {
    console.log('handleUpdateProduct called') // Debug
    console.log('editingId:', editingId.value) // Debug
    console.log('productForm:', productForm) // Debug
    
    loading.value = true
    const productData = {
      productName: productForm.name,
      categoryCode: productForm.category,
      price: productForm.price,
      discount: productForm.discount,
      description: productForm.description,
      productVariantRequest: productForm.variants.map(v => ({
        variatId: v.variantId,
        sizeId: v.sizeId,
        stockQuantity: v.stock
      }))
    }
    
    console.log('productData:', productData) // Debug
    console.log('uploadedImageFiles:', uploadedImageFiles.value) // Debug
    
    const response = await updateProduct(editingId.value, productData, uploadedImageFiles.value)
    
    console.log('Update response:', response) // Debug
    alert(response.message || 'Product updated successfully!')
    resetForm()
    
    // Reload lại trang hiện tại
    await fetchProducts(currentPage.value)
  } catch (error) {
    console.error('Update error:', error) // Debug
    alert('Error: ' + (error.response?.data?.message || error.message))
  } finally {
    loading.value = false
  }
}

const handleDeleteProduct = async (productCode) => {
  if (!confirm('Are you sure you want to delete this product?')) {
    return
  }
  
  try {
    const response = await deleteProduct(productCode)
    alert(response.message || 'Product deleted successfully!')
  } catch (error) {
    alert('Error: ' + (error.response?.data?.message || error.message))
  }
}

const uploadedImageFiles = ref([])

const productForm = reactive({
  name: '',
  category: '',
  price: 0,
  description: '',
  images: [],    
  variants: []   
})

const resetForm = () => {
  productForm.name = ''
  productForm.category = ''
  productForm.price = 0
  productForm.discount = 0
  productForm.description = ''
  productForm.images = []
  productForm.variants = []
  uploadedImageFiles.value = []
  newVariantSize.value = ''
  editingId.value = null
  showAddProductForm.value = false
}

const handleImageUpload = (event) => {
  const files = event.target.files
  if (!files || files.length === 0) return

  Array.from(files).forEach(file => {
    if (file.type.startsWith('image/')) {
      uploadedImageFiles.value.push(file)
      const reader = new FileReader()
      reader.onload = (e) => {
        productForm.images.push(e.target.result)
      }
      reader.readAsDataURL(file)
    }
  })
  event.target.value = ''
}

const removeImage = (index) => {
  productForm.images.splice(index, 1)
  uploadedImageFiles.value.splice(index, 1)
}

const addVariant = () => {
  if (!newVariantSize.value.trim()) {
    alert('Please enter a size')
    return
  }
  
  const existingVariant = productForm.variants.find(
    v => v.size.toLowerCase() === newVariantSize.value.trim().toLowerCase()
  )
  
  if (existingVariant) {
    alert('This size already exists')
    return
  }
  
  productForm.variants.push({
    size: newVariantSize.value.trim(),
    stock: 0
  })
  
  newVariantSize.value = ''
}

const removeVariant = (index) => {
  productForm.variants.splice(index, 1)
}

const calculateTotalStock = () => {
  return productForm.variants.reduce((total, variant) => {
    return total + (parseInt(variant.stock) || 0)
  }, 0)
}

const editProduct = async (productCode) => {
  try {
    loading.value = true
    const detailData = await fetchProductDetail(productCode)
    
    productForm.name = detailData.productName || ''
    productForm.category = detailData.productCategory || ''
    productForm.price = detailData.productPrice || 0
    productForm.discount = detailData.discount || 0
    productForm.description = detailData.productDescription || ''
    
    if (detailData.images && detailData.images.length > 0) {
      productForm.images = detailData.images.map(img => img.imageUrl)
      uploadedImageFiles.value = [] 
    } else {
      productForm.images = []
    }
    if (detailData.productVariant && detailData.productVariant.length > 0 && detailData.size) {
      productForm.variants = detailData.productVariant.map(variant => {
        const sizeInfo = detailData.size.find(s => s.sizeId === variant.size)
        
        return {
          size: sizeInfo ? sizeInfo.sizeName : `Size ${variant.sizeId}`,
          stock: variant.stock || 0,
          sizeId: variant.size,
          variantId: variant.variantId
        }
      })
    } else {
      productForm.variants = []
    }
    
    editingId.value = productCode
    showAddProductForm.value = true
    
    window.scrollTo({ top: 0, behavior: 'smooth' })
    
  } catch (error) {
    alert('Error loading product: ' + error.message)
    console.error('Edit product error:', error)
  } finally {
    loading.value = false
  }
}

// Form visibility refs for each section
const showAddProductForm = ref(false)
const showAddUserForm = ref(false)
const showAddCustomerForm = ref(false)
const showAddOrderForm = ref(false)

// Editing refs for each section
const editingUserId = ref(null)
const editingCustomerId = ref(null)
const editingOrderId = ref(null)

// New: Temporary variable for adding new variant
const newVariantSize = ref('')

// User form data
const userForm = reactive({
  name: '',
  email: '',
  phone: '',
  role: 'user',
  status: 'active'
})

// Customer form data
const customerForm = reactive({
  name: '',
  email: '',
  phone: '',
  address: ''
})

// Order form data
const orderForm = reactive({
  customer: '',
  orderDate: '',
  totalAmount: 0,
  status: 'pending',
  paymentStatus: 'unpaid'
})

// Mock users data
const users = ref([
  {
    id: 1,
    name: 'John Doe',
    email: 'john@example.com',
    phone: '0987654321',
    role: 'admin',
    status: 'active'
  },
  {
    id: 2,
    name: 'Jane Smith',
    email: 'jane@example.com',
    phone: '0987654322',
    role: 'user',
    status: 'active'
  },
  {
    id: 3,
    name: 'Bob Wilson',
    email: 'bob@example.com',
    phone: '0987654323',
    role: 'user',
    status: 'inactive'
  }
])

// Mock customers data
const customers = ref([
  {
    id: 1,
    name: 'Nguyễn Văn A',
    email: 'nguyena@example.com',
    phone: '0901234567',
    address: '123 Nguyễn Hữu Cảnh, Bình Thạnh, HCM',
    totalOrders: 5
  },
  {
    id: 2,
    name: 'Trần Thị B',
    email: 'tranb@example.com',
    phone: '0901234568',
    address: '456 Lê Văn Sỹ, Phú Nhuận, HCM',
    totalOrders: 3
  },
  {
    id: 3,
    name: 'Lê Văn C',
    email: 'levanc@example.com',
    phone: '0901234569',
    address: '789 Trần Hữu Trang, Quận 1, HCM',
    totalOrders: 8
  }
])

// Mock orders data
const orders = ref([
  {
    id: 'ORD001',
    customer: 'Nguyễn Văn A',
    orderDate: '2025-11-10',
    totalAmount: 1500000,
    status: 'completed',
    paymentStatus: 'paid'
  },
  {
    id: 'ORD002',
    customer: 'Trần Thị B',
    orderDate: '2025-11-12',
    totalAmount: 750000,
    status: 'shipping',
    paymentStatus: 'paid'
  },
  {
    id: 'ORD003',
    customer: 'Lê Văn C',
    orderDate: '2025-11-14',
    totalAmount: 2200000,
    status: 'pending',
    paymentStatus: 'unpaid'
  }
])

// User management functions
const handleSaveUser = async () => {
  if (editingUserId.value) {
    // Update existing user
    const index = users.value.findIndex(u => u.id === editingUserId.value)
    if (index !== -1) {
      users.value[index] = {
        ...users.value[index],
        ...userForm
      }
    }
  } else {
    // Add new user
    users.value.push({
      id: Math.max(...users.value.map(u => u.id), 0) + 1,
      ...userForm
    })
  }
  
  alert(editingUserId.value ? 'User updated!' : 'User added!')
  resetUserForm()
}

const editUser = (user) => {
  editingUserId.value = user.id
  userForm.name = user.name
  userForm.email = user.email
  userForm.phone = user.phone
  userForm.role = user.role
  userForm.status = user.status
  showAddUserForm.value = true
  window.scrollTo(0, 0)
}

const deleteUser = (id) => {
  if (confirm('Are you sure you want to delete this user?')) {
    users.value = users.value.filter(u => u.id !== id)
    alert('User deleted!')
  }
}

const resetUserForm = () => {
  userForm.name = ''
  userForm.email = ''
  userForm.phone = ''
  userForm.role = 'user'
  userForm.status = 'active'
  editingUserId.value = null
  showAddUserForm.value = false
}

// Customer management functions
const handleSaveCustomer = async () => {
  if (editingCustomerId.value) {
    // Update existing customer
    const index = customers.value.findIndex(c => c.id === editingCustomerId.value)
    if (index !== -1) {
      customers.value[index] = {
        ...customers.value[index],
        ...customerForm
      }
    }
  } else {
    // Add new customer
    customers.value.push({
      id: Math.max(...customers.value.map(c => c.id), 0) + 1,
      ...customerForm,
      totalOrders: 0
    })
  }
  
  alert(editingCustomerId.value ? 'Customer updated!' : 'Customer added!')
  resetCustomerForm()
}

const editCustomer = (customer) => {
  editingCustomerId.value = customer.id
  customerForm.name = customer.name
  customerForm.email = customer.email
  customerForm.phone = customer.phone
  customerForm.address = customer.address
  showAddCustomerForm.value = true
  window.scrollTo(0, 0)
}

const deleteCustomer = (id) => {
  if (confirm('Are you sure you want to delete this customer?')) {
    customers.value = customers.value.filter(c => c.id !== id)
    alert('Customer deleted!')
  }
}

const resetCustomerForm = () => {
  customerForm.name = ''
  customerForm.email = ''
  customerForm.phone = ''
  customerForm.address = ''
  editingCustomerId.value = null
  showAddCustomerForm.value = false
}

// Order management functions
const handleSaveOrder = async () => {
  if (editingOrderId.value) {
    // Update existing order
    const index = orders.value.findIndex(o => o.id === editingOrderId.value)
    if (index !== -1) {
      orders.value[index] = {
        ...orders.value[index],
        ...orderForm
      }
    }
  } else {
    // Add new order - generate order ID
    const newOrderId = `ORD${String(orders.value.length + 1).padStart(3, '0')}`
    orders.value.push({
      id: newOrderId,
      ...orderForm
    })
  }
  
  alert(editingOrderId.value ? 'Order updated!' : 'Order added!')
  resetOrderForm()
}

const editOrder = (order) => {
  editingOrderId.value = order.id
  orderForm.customer = order.customer
  orderForm.orderDate = order.orderDate
  orderForm.totalAmount = order.totalAmount
  orderForm.status = order.status
  orderForm.paymentStatus = order.paymentStatus
  showAddOrderForm.value = true
  window.scrollTo(0, 0)
}

const deleteOrder = (id) => {
  if (confirm('Are you sure you want to delete this order?')) {
    orders.value = orders.value.filter(o => o.id !== id)
    alert('Order deleted!')
  }
}

const resetOrderForm = () => {
  orderForm.customer = ''
  orderForm.orderDate = ''
  orderForm.totalAmount = 0
  orderForm.status = 'pending'
  orderForm.paymentStatus = 'unpaid'
  editingOrderId.value = null
  showAddOrderForm.value = false
}

const handleMenuSelect = (menuId) => {
  if (menuId === 'leave') {
    router.push('/')
  } else if (menuId === 'logout') {
    alert('Đăng xuất')
  } else {
    currentMenu.value = menuId
  }
}

const getMenuLabel = (menuId) => {
  const labels = {
    products: 'Products Management',
    categories: 'Categories',
    orders: 'Orders',
    users: 'Users',
    customers: 'Customers',
    analytics: 'Analytics',
    settings: 'Settings'
  }
  return labels[menuId] || 'Dashboard'
}

const getOrderStatusBadge = (status) => {
  const statusMap = {
    'pending': 'bg-warning',
    'confirmed': 'bg-info',
    'shipping': 'bg-primary',
    'completed': 'bg-success',
    'cancelled': 'bg-danger'
  }
  return statusMap[status] || 'bg-secondary'
}
</script>
