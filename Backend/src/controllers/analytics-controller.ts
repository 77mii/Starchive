import { NextFunction, Response } from "express"
import { AnalyticsService } from "../services/analytics-service"
import { UserRequest } from "../types/user-request"

export class AnalyticsController {
    static async getSpendingAnalysis(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const gameId = parseInt(req.params.gameId)
            const response = await AnalyticsService.getSpendingAnalysis(req.user!, gameId)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async getPityHistory(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const bannerId = parseInt(req.params.bannerId)
            const response = await AnalyticsService.getPityHistory(req.user!, bannerId)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async getBudgetUsage(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const gameId = parseInt(req.params.gameId)
            const response = await AnalyticsService.getBudgetUsage(req.user!, gameId)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }
}