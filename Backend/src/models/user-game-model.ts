import { UserGames } from "@prisma/client"

export interface AddUserGameRequest {
    game_id: number
}

export interface UserGameResponse {
    user_id: number
    game_id: number
}

export function toUserGameResponse(userGame: UserGames): UserGameResponse {
    return {
        user_id: userGame.user_id,
        game_id: userGame.game_id
    }
}