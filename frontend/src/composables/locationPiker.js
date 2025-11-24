import { ref } from 'vue'
import ky from 'ky'
import { array, mask } from 'superstruct'
import { debounce } from 'ts-debounce'

import { Base, Ward, WardSchema, District, DistrictShema, Province, ProvinceSchema } from '../structs'

const BASE_API_URL = 'https://provinces.open-api.vn/api'

const provinceSearch = ref('')
const provinceListShown = ref(false)
const filteredProvinces = ref([])
const selectedProvince = ref(null)
const districtSearch = ref('')
const districtListShown = ref(false)
const filteredDistricts = ref([])
const selectedDistrict = ref(null)
const wardSearch = ref('')
const wardListShown = ref(false)
const filteredWards = ref([])
const selectedWard = ref(null)

/*
* The Lunr engine consider each keyword optional, but in the context of
* this demo, we want all keywords to be present.
* This function is to prefix all keywords with plus ("+"),
* ex: "Bà Rịa" -> "+Bà +Rịa".
*/
function markRequireAll(query) {
    const words = query.split(/\s+/)
    return words.map(w => `+${w}`).join(' ')
}

function highlightName(item) {
    if (!item.matches) {
        return item.name
    }
    const name = item.name
    const matches = Object.values(item.matches)
    matches.sort((v1, v2) => v1[0] - v2[0])
    const parts = []
    var lastPos = 0
    for (const [s, e] of matches) {
        parts.push(name.slice(lastPos, s))
        parts.push(`<strong>${name.slice(s, e)}</strong>`)
        lastPos = e
    }
    parts.push(name.slice(lastPos))
    return parts.join('')
}

const searchDistrict = async (term, provinceCode) => {
    if (selectedDistrict.value && selectedDistrict.value.name === term) {
        return
    }
    const rdata = await ky.get(`${BASE_API_URL}/d/search/`, {
        searchParams: { q: markRequireAll(term), p: provinceCode }
    }).json()
    filteredDistricts.value = mask(rdata, array(DistrictShema))
}
const searchDistrictOnTyping = debounce(async () => {
    const term = districtSearch.value.trim()
    if (!term || !selectedProvince.value) {
        return
    }
    await searchDistrict(term, selectedProvince.value.code)
}, 300)

function resetProvince() {
    provinceSearch.value = ''
    selectedProvince.value = null
    filteredProvinces.value = []
    provinceListShown.value = false
}

function resetDistrict() {
    districtSearch.value = ''
    selectedDistrict.value = null
    filteredDistricts.value = []
    districtListShown.value = false
}

function resetWard() {
    wardSearch.value = ''
    selectedWard.value = null
    filteredWards.value = []
    wardListShown.value = false
}

function hideProvinceList() {
    setTimeout(() => provinceListShown.value = false, 100)
}

function hideDistrictList() {
    setTimeout(() => districtListShown.value = false, 100)
}

function hideWardList() {
    setTimeout(() => wardListShown.value = false, 100)
}

async function fetchProvinces() {
    const rdata = await ky.get(`${BASE_API_URL}/p/`).json()
    filteredProvinces.value = mask(rdata, array(ProvinceSchema))
}

async function fetchDistricts(provinceCode) {
    const rdata = await ky.get(`${BASE_API_URL}/p/${provinceCode}`, { searchParams: { depth: 2 } }).json()
    const province = mask(rdata, ProvinceSchema)
    filteredDistricts.value = province.districts
}

async function fetchWards(districtCode) {
    const rdata = await ky.get(`${BASE_API_URL}/d/${districtCode}`, { searchParams: { depth: 2 } }).json()
    const district = mask(rdata, DistrictShema)
    filteredWards.value = district.wards
}

async function searchProvince(term) {
    if (selectedProvince.value && selectedProvince.value.name === term) {
        return
    }
    const rdata = await ky.get(`${BASE_API_URL}/p/search/`, {
        searchParams: { q: markRequireAll(term) }
    }).json()
    filteredProvinces.value = mask(rdata, array(ProvinceSchema))
}

async function searchWard(term, districtCode) {
    if (selectedWard.value && selectedWard.value.name === term) {
        return
    }
    const rdata = await ky.get(`${BASE_API_URL}/w/search/`, {
        searchParams: { q: markRequireAll(term), d: districtCode }
    }).json()
    filteredWards.value = mask(rdata, array(WardSchema))
}

async function startSearchingProvince() {
    provinceListShown.value = true
    if (!filteredProvinces.value.length) {
        await fetchProvinces()
    }
}

async function startSearchingDistrict() {
    districtListShown.value = true
    if (filteredDistricts.value.length || !selectedProvince.value) {
        return
    }
    await fetchDistricts(selectedProvince.value.code)
}

async function startSearchingWard() {
    wardListShown.value = true
    if (filteredWards.value.length || !selectedDistrict.value) {
        return
    }
    await fetchWards(selectedDistrict.value.code)
}

function selectProvince(province) {
    hideProvinceList()
    selectedProvince.value = province
    provinceSearch.value = province.name
    resetDistrict()
    resetWard()
}

function selectDistrict(district) {
    hideDistrictList()
    selectedDistrict.value = district
    districtSearch.value = district.name
    resetWard()
}

function selectWard(ward) {
    hideWardList()
    selectedWard.value = ward
    wardSearch.value = ward.name
}

export {
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
    searchDistrict,
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
}