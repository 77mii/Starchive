import { HardPity } from "@prisma/client"

export interface CreatePityRequest {
    game_id: number
    banner_id: number
    pull_towards_pity: number
}

export interface UpdatePityRequest {
    pull_towards_pity: number
}

export interface PityResponse {
    pity_id: number
    game_id: number
    banner_id: number
    pull_towards_pity: number
}

export function toPityResponse(pity: HardPity): PityResponse {
    return {
        pity_id: pity.pity_id,
        game_id: pity.game_id,
        banner_id: pity.banner_id,
        pull_towards_pity: pity.pull_towards_pity
    }
}