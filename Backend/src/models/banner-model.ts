import { Banners } from "@prisma/client"

export interface CreateBannerRequest {
    game_id: number
    banner_name: string
    type: string
    start_date: Date
    end_date: Date
    hard_pity?: number
    soft_pity?: number
}

export interface BannerResponse {
    banner_id: number
    game_id: number
    banner_name: string
    type: string
    start_date: Date
    end_date: Date
    hard_pity: number | null
    soft_pity: number | null
}

export function toBannerResponse(banner: Banners): BannerResponse {
    return {
        banner_id: banner.banner_id,
        game_id: banner.game_id,
        banner_name: banner.banner_name,
        type: banner.type,
        start_date: banner.start_date,
        end_date: banner.end_date,
        hard_pity: banner.hard_pity,
        soft_pity: banner.soft_pity
    }
}