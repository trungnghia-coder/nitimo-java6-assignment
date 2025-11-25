<template>
  <div class="location-picker">
    <div class="grid md:grid-cols-4 gap-4">
      <!-- Street Address Input -->
      <div class="location-group">
        <label class="location-label">Street Address</label>
        <div class="relative">
          <input
            v-model.trim="streetAddress"
            type="text"
            class="location-input"
            placeholder="e.g., 123 Main Street, Apt 4B"
          />
        </div>
      </div>

      <!-- Province Input -->
      <div class="location-group">
        <label class="location-label">Province / City</label>
        <div class="relative">
          <input
            v-model.trim="provinceSearch"
            type="text"
            class="location-input"
            placeholder="Search province..."
            @focus="startSearchingProvince"
            @blur="hideProvinceListWithDelay"
          />
          <div
            class="location-dropdown"
            v-show="provinceListShown && filteredProvinces.length"
          >
            <ul class="location-list">
              <li
                v-for="(item, idx) of filteredProvinces"
                :key="idx"
                class="location-item"
                @click="selectProvince(item)"
              >
                <span v-html="highlightName(item)"></span>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <!-- District Input -->
      <div class="location-group">
        <label class="location-label">District</label>
        <div class="relative">
          <input
            v-model.trim="districtSearch"
            type="text"
            class="location-input"
            placeholder="Search district..."
            @focus="startSearchingDistrict"
            @keyup="searchDistrictOnTyping()"
            @blur="hideDistrictListWithDelay"
          />
          <div
            class="location-dropdown"
            v-show="districtListShown && filteredDistricts.length"
          >
            <ul class="location-list">
              <li
                v-for="(item, idx) of filteredDistricts"
                :key="idx"
                class="location-item"
                @click="selectDistrict(item)"
              >
                <span v-html="highlightName(item)"></span>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <!-- Ward Input -->
      <div class="location-group">
        <label class="location-label">Ward</label>
        <div class="relative">
          <input
            v-model.trim="wardSearch"
            type="text"
            class="location-input"
            placeholder="Search ward..."
            @focus="startSearchingWard"
            @blur="hideWardListWithDelay"
          />
          <div
            class="location-dropdown"
            v-show="wardListShown && filteredWards.length"
          >
            <ul class="location-list">
              <li
                v-for="(item, idx) of filteredWards"
                :key="idx"
                class="location-item"
                @click="selectWard(item)"
              >
                <span v-html="highlightName(item)"></span>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>

    <!-- Selected Location Display -->
    <div class="selected-location mt-4 p-3 bg-blue-50 border border-blue-200 rounded-lg">
      <p class="text-sm font-semibold text-gray-700 mb-1">Selected Location:</p>
      <p class="text-base text-blue-600 font-medium">
        {{ selectedLocation || "No location selected" }}
      </p>
    </div>
  </div>
</template>

<style scoped>
.location-picker {
  font-family: inherit;
}

.location-group {
  display: flex;
  flex-direction: column;
  position: relative;
}

.location-label {
  font-size: 0.875rem;
  font-weight: 600;
  color: #374151;
  margin-bottom: 0.5rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.location-input {
  padding: 0.75rem;
  border: 2px solid #e5e7eb;
  border-radius: 0.5rem;
  font-size: 0.95rem;
  color: #1f2937;
  outline: none;
  transition: all 0.2s ease;
  background-color: #fff;
}

.location-input:focus {
  border-color: #3b82f6;
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
  background-color: #f0f9ff;
}

.location-input::placeholder {
  color: #9ca3af;
}

.location-dropdown {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  margin-top: 0.5rem;
  background-color: #fff;
  border: 1px solid #d1d5db;
  border-radius: 0.5rem;
  max-height: 12rem;
  overflow-y: auto;
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
  z-index: 1051;
}

.location-list {
  list-style: none;
  padding: 0;
  margin: 0;
}

.location-item {
  padding: 0.75rem;
  cursor: pointer;
  border-bottom: 1px solid #f3f4f6;
  transition: all 0.15s ease;
  color: #374151;
  font-size: 0.95rem;
}

.location-item:hover {
  background-color: #eff6ff;
  color: #1e40af;
  font-weight: 500;
}

.location-item:last-child {
  border-bottom: none;
}

.selected-location {
  animation: slideIn 0.3s ease-out;
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(-0.5rem);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@media (max-width: 768px) {
  .location-dropdown {
    max-height: 10rem;
  }
}

/* Tailwind classes for compatibility */
:deep(.highlight) {
  font-weight: 700;
  color: #1e40af;
}
</style>

<script setup>
import { computed } from 'vue'
import {
  provinceSearch,
  provinceListShown,
  filteredProvinces,
  selectedProvince,
  districtSearch,
  districtListShown,
  filteredDistricts,
  selectedDistrict,
  wardSearch,
  wardListShown,
  filteredWards,
  selectedWard,
  streetAddress,
  highlightName,
  searchDistrictOnTyping,
  startSearchingProvince,
  startSearchingDistrict,
  startSearchingWard,
  selectProvince,
  selectDistrict,
  selectWard,
  hideProvinceList,
  hideDistrictList,
  hideWardList
} from '../composables/locationPiker'

const selectedLocation = computed(() => {
  const parts = [
    streetAddress.value,
    selectedWard.value?.name,
    selectedDistrict.value?.name,
    selectedProvince.value?.name
  ].filter(Boolean)
  
  return parts.length ? parts.join(', ') : null
})

const hideProvinceListWithDelay = () => {
  setTimeout(hideProvinceList, 150)
}

const hideDistrictListWithDelay = () => {
  setTimeout(hideDistrictList, 150)
}

const hideWardListWithDelay = () => {
  setTimeout(hideWardList, 150)
}
</script>