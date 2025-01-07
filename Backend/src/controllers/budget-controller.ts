import { NextFunction, Response } from "express"
import { CreateBudgetRequest } from "../models/budget-model"
import { BudgetService } from "../services/budget-service"
import { UserRequest } from "../types/user-request"

export class BudgetController {
    static async create(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const request: CreateBudgetRequest = req.body as CreateBudgetRequest
            const response = await BudgetService.create(req.user!, request)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async getByUserId(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const response = await BudgetService.getByUserId(req.user!)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async getByUserIdAndGameId(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const gameId = parseInt(req.params.gameId)
            const response = await BudgetService.getByUserIdAndGameId(req.user!, gameId)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async update(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const budgetId = parseInt(req.params.budgetId)
            const request = req.body as Partial<CreateBudgetRequest>
            const response = await BudgetService.update(req.user!, budgetId, request)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }
}