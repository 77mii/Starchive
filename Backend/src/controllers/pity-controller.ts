import { NextFunction, Response } from "express"
import { CreatePityRequest, UpdatePityRequest } from "../models/pity-model"
import { PityService } from "../services/pity-service"
import { UserRequest } from "../types/user-request"

export class PityController {
    static async create(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const request: CreatePityRequest = req.body as CreatePityRequest
            const response = await PityService.create(req.user!, request)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async getByBannerId(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const bannerId = parseInt(req.params.bannerId)
            const response = await PityService.getByBannerId(req.user!, bannerId)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async update(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const pityId = parseInt(req.params.pityId)
            const request = req.body as UpdatePityRequest
            const response = await PityService.update(pityId, request)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }
}