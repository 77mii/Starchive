"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toBannerResponse = toBannerResponse;
function toBannerResponse(banner) {
    return {
        banner_id: banner.banner_id,
        game_id: banner.game_id,
        banner_name: banner.banner_name,
        type: banner.type,
        start_date: banner.start_date,
        end_date: banner.end_date,
        hard_pity: banner.hard_pity,
        soft_pity: banner.soft_pity
    };
}
