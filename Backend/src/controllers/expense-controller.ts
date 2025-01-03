import { NextFunction, Response } from "express"
import { CreateExpenseRequest } from "../models/expense-model"
import { ExpenseService } from "../services/expense-service"
import { UserRequest } from "../types/user-request"

export class ExpenseController {
    static async create(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const request: CreateExpenseRequest = req.body as CreateExpenseRequest
            const response = await ExpenseService.create(req.user!, request)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async getByUserId(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const response = await ExpenseService.getByUserId(req.user!)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async getByGameId(req: UserRequest, res: Response, next: NextFunction) {
        try {
            const gameId = parseInt(req.params.gameId)
            const response = await ExpenseService.getByGameId(req.user!, gameId)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }
}