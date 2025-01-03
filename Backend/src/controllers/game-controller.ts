import { NextFunction, Request, Response } from "express"
import { CreateGameRequest } from "../models/game-model"
import { GameService } from "../services/game-service"

export class GameController {
    static async create(req: Request, res: Response, next: NextFunction) {
        try {
            const request: CreateGameRequest = req.body as CreateGameRequest
            const response = await GameService.create(request)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async getAll(req: Request, res: Response, next: NextFunction) {
        try {
            const response = await GameService.getAll()
            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async getById(req: Request, res: Response, next: NextFunction) {
        try {
            const gameId = parseInt(req.params.gameId)
            const response = await GameService.getById(gameId)
            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async update(req: Request, res: Response, next: NextFunction) {
        try {
            const gameId = parseInt(req.params.gameId)
            const request = req.body as Partial<CreateGameRequest>
            const response = await GameService.update(gameId, request)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }
}