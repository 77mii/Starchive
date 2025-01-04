import { NextFunction, Response } from "express"
import { CreatePlanRequest } from "../models/plan-model"
import { PlanService } from "../services/plan-service"
import { UserRequest } from "../types/user-request"

export class PlanController {
    static async create(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const request: CreatePlanRequest = req.body as CreatePlanRequest
            const response = await PlanService.create(req.user!, request)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async getByUserId(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const response = await PlanService.getByUserId(req.user!)
    
            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async delete(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const planId = parseInt(req.params.planId)
            await PlanService.delete(req.user!, planId)

            res.status(200).json({
                message: "Plan deleted successfully"
            })
        } catch (error) {
            next(error)
        }
    }
}