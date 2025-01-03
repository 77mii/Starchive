import { NextFunction, Request, Response } from "express"
import { CreateItemRequest } from "../models/item-model"
import { ItemService } from "../services/item-service"

export class ItemController {
    static async create(req: Request, res: Response, next: NextFunction) {
        try {
            const request: CreateItemRequest = req.body as CreateItemRequest
            const response = await ItemService.create(request)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async getByGameId(req: Request, res: Response, next: NextFunction) {
        try {
            const gameId = parseInt(req.params.gameId)
            const response = await ItemService.getByGameId(gameId)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async getByBannerId(req: Request, res: Response, next: NextFunction) {
        try {
            const bannerId = parseInt(req.params.bannerId)
            const response = await ItemService.getByBannerId(bannerId)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }
}