import { Games } from "@prisma/client"

export interface CreateGameRequest {
    game_name: string
    income: number
    description: string
    currency_name: string
    tickets_name?: string
}

export interface GameResponse {
    game_id: number
    game_name: string
    income: number
    description: string
    currency_name: string
    tickets_name: string | null
}

export function toGameResponse(game: Games): GameResponse {
    return {
        game_id: game.game_id,
        game_name: game.game_name,
        income: game.income,
        description: game.description,
        currency_name: game.currency_name,
        tickets_name: game.tickets_name
    }
}