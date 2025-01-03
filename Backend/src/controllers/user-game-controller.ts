import { NextFunction, Response } from "express"
import { AddUserGameRequest } from "../models/user-game-model"
import { UserGameService } from "../services/user-game-service"
import { UserRequest } from "../types/user-request"

export class UserGameController {
    static async add(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const request: AddUserGameRequest = req.body as AddUserGameRequest
            const response = await UserGameService.add(req.user!, request)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async getByUserId(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const response = await UserGameService.getByUserId(req.user!)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async remove(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const gameId = parseInt(req.params.gameId)
            await UserGameService.remove(req.user!, gameId)

            res.status(200).json({
                message: "Game removed from user's list"
            })
        } catch (error) {
            next(error)
        }
    }
}