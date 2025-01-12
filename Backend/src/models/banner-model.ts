

import { Banners, HardPity } from "@prisma/client"

export interface CreateBannerRequest {
    game_id: number
    banner_name: string
    type: string
    start_date: Date
    end_date: Date
    hard_pity?: number
    soft_pity?: number
    image_url?: string
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
    image_url: string | null
    hard_pities: HardPityResponse[]
}

export interface HardPityResponse {
    pity_id: number
    user_id: number
    game_id: number
    banner_id: number
    pull_towards_pity: number
}

export function toHardPityResponse(hardPity: HardPity): HardPityResponse {
    return {
        pity_id: hardPity.pity_id,
        user_id: hardPity.user_id,
        game_id: hardPity.game_id,
        banner_id: hardPity.banner_id,
        pull_towards_pity: hardPity.pull_towards_pity
    }
}

export function toBannerResponse(banner: Banners & { HardPity: HardPity[] }): BannerResponse {
    return {
        banner_id: banner.banner_id,
        game_id: banner.game_id,
        banner_name: banner.banner_name,
        type: banner.type,
        start_date: banner.start_date,
        end_date: banner.end_date,
        hard_pity: banner.hard_pity,
        soft_pity: banner.soft_pity,
        image_url: banner.image_url,
        hard_pities: banner.HardPity.map(toHardPityResponse)
    }
}