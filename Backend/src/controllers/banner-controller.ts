

import { NextFunction, Request, Response } from "express"
import { CreateBannerRequest } from "../models/banner-model"
import { BannerService } from "../services/banner-service"

export class BannerController {
    static async create(req: Request, res: Response, next: NextFunction) {
        try {
            const request: CreateBannerRequest = req.body as CreateBannerRequest
            const response = await BannerService.create(request)

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
            const response = await BannerService.getByGameId(gameId)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async update(req: Request, res: Response, next: NextFunction) {
        try {
            const bannerId = parseInt(req.params.bannerId)
            const request = req.body as Partial<CreateBannerRequest>
            const response = await BannerService.update(bannerId, request)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async getActive(req: Request, res: Response, next: NextFunction) {
        try {
            const response = await BannerService.getActive()

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }
}