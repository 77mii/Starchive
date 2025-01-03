import { Items, BannerItems } from "@prisma/client"

export interface CreateItemRequest {
    rarity: string
    item_name: string
}

export interface ItemResponse {
    item_id: number
    rarity: string
    item_name: string
}

export interface BannerItemResponse {
    banner_id: number
    item_id: number
    acquire_rate: number
}

export function toItemResponse(item: Items): ItemResponse {
    return {
        item_id: item.item_id,
        rarity: item.rarity,
        item_name: item.item_name
    }
}

export function toBannerItemResponse(bannerItem: BannerItems): BannerItemResponse {
    return {
        banner_id: bannerItem.banner_id,
        item_id: bannerItem.item_id,
        acquire_rate: bannerItem.acquire_rate
    }
}