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
                    <input 
                      v-model="productForm.category" 
                      type="text" 
                      class="form-control" 
                      placeholder="Enter category"
                      required
                    />
                  </div>
                </div>
              </div>

              <div class="row">
                <div class="col-md-3">
                  <div class="form-group mb-3">
                    <label class="form-label">Price</label>
                    <input 
                      v-model="productForm.price" 
                      type="number" 
                      class="form-control" 
                      placeholder="0.00"
                      min="0"
                      step="0.01"
                      required
                    />
                  </div>
                </div>
                <div class="col-md-3">
                  <div class="form-group mb-3">
                    <label class="form-label">Stock</label>
                    <input 
                      v-model="productForm.stock" 
                      type="number" 
                      class="form-control" 
                      placeholder="0"
                      min="0"
                      required
                    />
                  </div>
                </div>
                <div class="col-md-3">
                  <div class="form-group mb-3">
                    <label class="form-label">SKU</label>
                    <input 
                      v-model="productForm.sku" 
                      type="text" 
                      class="form-control" 
                      placeholder="SKU"
                      required
                    />
                  </div>
                </div>
                <div class="col-md-3">
                  <div class="form-group mb-3">
                    <label class="form-label">Status</label>
                    <select v-model="productForm.status" class="form-control" required>
                      <option value="active">Active</option>
                      <option value="inactive">Inactive</option>
                    </select>
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

              <div class="form-group mb-4">
                <label class="form-label">Image URL</label>
                <input 
                  v-model="productForm.imageUrl" 
                  type="url" 
                  class="form-control" 
                  placeholder="https://..."
                />
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
              <i class="fas fa-plus"></i> Add New User
            </button>
            <div class="table-responsive">
              <table class="table table-hover">
                <thead class="table-light">
                  <tr>
                    <th>Image</th>
                    <th>Product Name</th>
                    <th>Category</th>
                    <th>Price</th>
                    <th>Stock</th>
                    <th>Status</th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="product in products" :key="product.id">
                    <td>
                      <img 
                        :src="product.imageUrl || 'https://via.placeholder.com/50'" 
                        :alt="product.name"
                        style="width: 50px; height: 50px; object-fit: cover; border-radius: 4px;"
                      />
                    </td>
                    <td class="fw-semibold">{{ product.name }}</td>
                    <td>{{ product.category }}</td>
                    <td class="fw-bold">{{ product.price.toLocaleString() }}₫</td>
                    <td>
                      <span :class="['badge', product.stock > 0 ? 'bg-success' : 'bg-danger']">
                        {{ product.stock }}
                      </span>
                    </td>
                    <td>
                      <span :class="['badge', product.status === 'active' ? 'bg-success' : 'bg-secondary']">
                        {{ product.status }}
                      </span>
                    </td>
                    <td>
                      <button class="btn btn-sm btn-warning me-2" @click="editProduct(product)">
                        <i class="fas fa-edit"></i> Edit
                      </button>
                      <button class="btn btn-sm btn-danger" @click="deleteProduct(product.id)">
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
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import ManageMenu from '../components/ManageMenu.vue'
import '../assets/css/manage.css'

const router = useRouter()
const currentMenu = ref('products')
const showAddForm = ref(false)
const editingId = ref(null)

// Form visibility refs for each section
const showAddProductForm = ref(false)
const showAddUserForm = ref(false)
const showAddCustomerForm = ref(false)
const showAddOrderForm = ref(false)

// Editing refs for each section
const editingUserId = ref(null)
const editingCustomerId = ref(null)
const editingOrderId = ref(null)

// Product form data
const productForm = reactive({
  name: '',
  category: '',
  price: 0,
  stock: 0,
  sku: '',
  status: 'active',
  description: '',
  imageUrl: ''
})

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

// Mock products data
const products = ref([
  {
    id: 1,
    name: 'Product 1',
    category: 'Electronics',
    price: 500000,
    stock: 50,
    sku: 'SKU001',
    status: 'active',
    description: 'High quality product',
    imageUrl: 'https://via.placeholder.com/100'
  },
  {
    id: 2,
    name: 'Product 2',
    category: 'Clothing',
    price: 200000,
    stock: 100,
    sku: 'SKU002',
    status: 'active',
    description: 'Comfortable and stylish',
    imageUrl: 'https://via.placeholder.com/100'
  }
])

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

const handleSaveProduct = async () => {
  if (editingId.value) {
    // Update existing product
    const index = products.value.findIndex(p => p.id === editingId.value)
    if (index !== -1) {
      products.value[index] = {
        ...products.value[index],
        ...productForm
      }
    }
  } else {
    // Add new product
    products.value.push({
      id: Math.max(...products.value.map(p => p.id), 0) + 1,
      ...productForm
    })
  }
  
  alert(editingId.value ? 'Product updated!' : 'Product added!')
  resetForm()
}

const editProduct = (product) => {
  editingId.value = product.id
  productForm.name = product.name
  productForm.category = product.category
  productForm.price = product.price
  productForm.stock = product.stock
  productForm.sku = product.sku
  productForm.status = product.status
  productForm.description = product.description
  productForm.imageUrl = product.imageUrl
  showAddForm.value = true
  window.scrollTo(0, 0)
}

const deleteProduct = (id) => {
  if (confirm('Are you sure you want to delete this product?')) {
    products.value = products.value.filter(p => p.id !== id)
    alert('Product deleted!')
  }
}

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

const resetForm = () => {
  productForm.name = ''
  productForm.category = ''
  productForm.price = 0
  productForm.stock = 0
  productForm.sku = ''
  productForm.status = 'active'
  productForm.description = ''
  productForm.imageUrl = ''
  editingId.value = null
  showAddProductForm.value = false
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
