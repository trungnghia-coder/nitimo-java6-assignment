<template>
  <div class="location-picker">
    <div class="location-grid">
      <!-- Province Input -->
      <div class="location-group">
        <label class="location-label">Province / City</label>
        <div class="location-input-wrapper">
          <input
            v-model.trim="provinceSearch"
            type="text"
            class="location-input"
            placeholder="Search province..."
            @focus="startSearchingProvince"
            @blur="hideProvinceList"
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
        <div class="location-input-wrapper">
          <input
            v-model.trim="districtSearch"
            type="text"
            class="location-input"
            placeholder="Search district..."
            @focus="startSearchingDistrict"
            @keyup="searchDistrictOnTyping()"
            @blur="hideDistrictList"
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
        <div class="location-input-wrapper">
          <input
            v-model.trim="wardSearch"
            type="text"
            class="location-input"
            placeholder="Search ward..."
            @focus="startSearchingWard"
            @blur="hideWardList"
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
    <div class="selected-location">
      <p class="selected-location-label">Selected Location:</p>
      <p class="selected-location-value">
        {{ selectedLocation || "No location selected" }}
      </p>
    </div>
  </div>
</template>

<style scoped>
.location-picker {
  font-family: inherit;
}

.location-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 16px;
}

.location-group {
  display: flex;
  flex-direction: column;
}

.location-label {
  font-size: 0.875rem;
  font-weight: 600;
  color: #374151;
  margin-bottom: 0.5rem;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.location-input-wrapper {
  position: relative;
}

.location-input {
  width: 100%;
  padding: 0.75rem;
  border: 2px solid #e5e7eb;
  border-radius: 0.5rem;
  font-size: 0.95rem;
  color: #1f2937;
  outline: none;
  transition: all 0.2s ease;
  background-color: #fff;
  box-sizing: border-box;
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
  z-index: 10;
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
  margin-top: 16px;
  padding: 12px;
  background-color: #eff6ff;
  border: 1px solid #bfdbfe;
  border-radius: 6px;
  animation: slideIn 0.3s ease-out;
}

.selected-location-label {
  font-size: 0.875rem;
  font-weight: 600;
  color: #374151;
  margin: 0 0 6px 0;
}

.selected-location-value {
  font-size: 1rem;
  color: #2563eb;
  font-weight: 500;
  margin: 0;
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
  .location-grid {
    grid-template-columns: 1fr;
    gap: 12px;
  }

  .location-dropdown {
    max-height: 10rem;
  }
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
    selectedWard.value?.name,
    selectedDistrict.value?.name,
    selectedProvince.value?.name
  ].filter(Boolean)
  
  return parts.length ? parts.join(', ') : null
})
</script>