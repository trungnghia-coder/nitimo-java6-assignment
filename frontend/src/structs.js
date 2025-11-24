import { object, string, number, array, optional } from 'superstruct'

export const Base = object({
    code: number(),
    name: string(),
    matches: optional(object())
})

export const WardSchema = object({
    code: number(),
    name: string(),
    district_code: number(),
    matches: optional(object())
})

export const DistrictShema = object({
    code: number(),
    name: string(),
    province_code: number(),
    wards: optional(array(WardSchema)),
    matches: optional(object())
})

export const ProvinceSchema = object({
    code: number(),
    name: string(),
    districts: optional(array(DistrictShema)),
    matches: optional(object())
})

export const Ward = WardSchema
export const District = DistrictShema
export const Province = ProvinceSchema
