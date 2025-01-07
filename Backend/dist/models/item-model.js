"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toItemResponse = toItemResponse;
exports.toBannerItemResponse = toBannerItemResponse;
function toItemResponse(item) {
    return {
        item_id: item.item_id,
        rarity: item.rarity,
        item_name: item.item_name,
        image_url: item.image_url
    };
}
function toBannerItemResponse(bannerItem) {
    return {
        banner_id: bannerItem.banner_id,
        item_id: bannerItem.item_id,
        acquire_rate: bannerItem.acquire_rate
    };
}
