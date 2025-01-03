import { Users } from "@prisma/client"
import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { AddUserGameRequest, UserGameResponse, toUserGameResponse } from "../models/user-game-model"
import { UserGameValidation } from "../validations/user-game-validation"
import { Validation } from "../validations/validation"

export class UserGameService {
    static async add(user: Users, request: AddUserGameRequest): Promise<UserGameResponse> {
        const addRequest = Validation.validate(UserGameValidation.ADD, request)

        const game = await prismaClient.games.findUnique({
            where: {
                game_id: addRequest.game_id
            }
        })

        if (!game) {
            throw new ResponseError(404, "Game not found")
        }

        const userGame = await prismaClient.userGames.create({
            data: {
                user_id: user.user_id,
                game_id: addRequest.game_id
            }
        })

        return toUserGameResponse(userGame)
    }

    static async getByUserId(user: Users): Promise<UserGameResponse[]> {
        const userGames = await prismaClient.userGames.findMany({
            where: {
                user_id: user.user_id
            }
        })

        return userGames.map(userGame => toUserGameResponse(userGame))
    }

    static async remove(user: Users, gameId: number): Promise<void> {
        const userGame = await prismaClient.userGames.findUnique({
            where: {
                user_id_game_id: {
                    user_id: user.user_id,
                    game_id: gameId
                }
            }
        })

        if (!userGame) {
            throw new ResponseError(404, "User game relation not found")
        }

        await prismaClient.userGames.delete({
            where: {
                user_id_game_id: {
                    user_id: user.user_id,
                    game_id: gameId
                }
            }
        })
    }
}