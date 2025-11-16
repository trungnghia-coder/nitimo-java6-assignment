<template>
  <nav class="manage-menu">
    <ul class="menu-list">
      <li 
        v-for="item in menuItems" 
        :key="item.id"
        :class="['menu-item', activeMenu === item.id ? 'active' : '']"
        @click="$emit('select', item.id)"
      >
        <i :class="item.icon" class="menu-icon"></i>
        <span class="menu-label">{{ item.label }}</span>
      </li>
    </ul>
  </nav>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue'

defineProps({
  activeMenu: {
    type: String,
    default: 'products'
  }
})

defineEmits(['select'])

const menuItems = [
  {
    id: 'products',
    label: 'Quản Lý Hàng Hóa',
    icon: 'fas fa-box'
  },
  {
    id: 'customers',
    label: 'Quản Lý Khách Hàng',
    icon: 'fas fa-list'
  },
  {
    id: 'users',
    label: 'Quản Lý Người Dùng',
    icon: 'fas fa-list'
  },
  {
    id: 'orders',
    label: 'Quản Lý Đơn Hàng',
    icon: 'fas fa-shopping-cart'
  },
  {
    id: 'leave',
    label: 'Về Trang Chủ',
    icon: 'fas fa-home'
  },
  {
    id: 'logout',
    label: 'Đăng Xuất',
    icon: 'fas fa-sign-out-alt'
  },
]
</script>

<style scoped>
.manage-menu {
  width: 250px;
  height: 100vh;
  background: linear-gradient(135deg, #FF8008 0%, #FFC837 100%);
  box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
  overflow-y: auto;
  position: fixed;
  left: 0;
  top: 0;
}

.menu-list {
  list-style: none;
  padding: 30px 0;
  margin: 0;
}

.menu-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 16px 24px;
  color: rgba(255, 255, 255, 0.8);
  cursor: pointer;
  transition: all 0.3s ease;
  border-left: 4px solid transparent;
}

.menu-item:hover {
  background-color: rgba(255, 255, 255, 0.1);
  color: white;
}

.menu-item.active {
  background-color: rgba(255, 255, 255, 0.2);
  color: white;
  border-left-color: #ff6b35;
}

.menu-icon {
  font-size: 30px;
  width: 24px;
  text-align: center;
}

.menu-label {
  font-weight: 500;
  font-size: 15px;
}

/* Scrollbar styling */
.manage-menu::-webkit-scrollbar {
  width: 8px;
}

.manage-menu::-webkit-scrollbar-track {
  background: rgba(255, 255, 255, 0.1);
}

.manage-menu::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.3);
  border-radius: 4px;
}

.manage-menu::-webkit-scrollbar-thumb:hover {
  background: rgba(255, 255, 255, 0.5);
}

@media (max-width: 768px) {
  .manage-menu {
    width: 200px;
  }

  .menu-item {
    padding: 12px 16px;
    gap: 10px;
  }

  .menu-label {
    font-size: 14px;
  }
}
</style>
