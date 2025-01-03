import { Users } from "@prisma/client"
import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { CreateExpenseRequest, ExpenseResponse, toExpenseResponse } from "../models/expense-model"
import { ExpenseValidation } from "../validations/expense-validation"
import { Validation } from "../validations/validation"

export class ExpenseService {
    static async create(user: Users, request: CreateExpenseRequest): Promise<ExpenseResponse> {
        const createRequest = Validation.validate(ExpenseValidation.CREATE, request)

        const game = await prismaClient.games.findUnique({
            where: {
                game_id: createRequest.game_id
            }
        })

        if (!game) {
            throw new ResponseError(404, "Game not found")
        }

        const expense = await prismaClient.expenses.create({
            data: {
                user_id: user.user_id,
                game_id: createRequest.game_id,
                spent_currency: createRequest.spent_currency,
                spent_money: createRequest.spent_money,
                spent_tickets: createRequest.spent_tickets
            }
        })

        return toExpenseResponse(expense)
    }

    static async getByUserId(user: Users): Promise<ExpenseResponse[]> {
        const expenses = await prismaClient.expenses.findMany({
            where: {
                user_id: user.user_id
            }
        })

        return expenses.map(expense => toExpenseResponse(expense))
    }

    static async getByGameId(user: Users, gameId: number): Promise<ExpenseResponse[]> {
        const expenses = await prismaClient.expenses.findMany({
            where: {
                user_id: user.user_id,
                game_id: gameId
            }
        })

        return expenses.map(expense => toExpenseResponse(expense))
    }
}