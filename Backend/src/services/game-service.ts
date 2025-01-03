import { prismaClient } from "../application/database"
import { CreateGameRequest, GameResponse, toGameResponse } from "../models/game-model"
import { GameValidation } from "../validations/game-validation"
import { Validation } from "../validations/validation"

export class GameService {
    static async create(request: CreateGameRequest): Promise<GameResponse> {
        const createRequest = Validation.validate(GameValidation.CREATE, request)

        const game = await prismaClient.games.create({
            data: createRequest
        })

        return toGameResponse(game)
    }

    static async getAll(): Promise<GameResponse[]> {
        const games = await prismaClient.games.findMany()
        return games.map(game => toGameResponse(game))
    }

    static async getById(gameId: number): Promise<GameResponse> {
        const game = await prismaClient.games.findUnique({
            where: {
                game_id: gameId
            }
        })

        if (!game) {
            throw new Error("Game not found")
        }

        return toGameResponse(game)
    }

    static async update(gameId: number, request: Partial<CreateGameRequest>): Promise<GameResponse> {
        const updateRequest = Validation.validate(GameValidation.UPDATE, request)

        const existingGame = await prismaClient.games.findUnique({
            where: {
                game_id: gameId
            }
        })

        if (!existingGame) {
            throw new Error("Game not found")
        }

        const game = await prismaClient.games.update({
            where: {
                game_id: gameId
            },
            data: updateRequest
        })

        return toGameResponse(game)
    }
}