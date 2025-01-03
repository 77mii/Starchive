import { Users } from "@prisma/client"
import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { CreateBudgetRequest, BudgetResponse, toBudgetResponse } from "../models/budget-model"
import { BudgetValidation } from "../validations/budget-validation"
import { Validation } from "../validations/validation"

export class BudgetService {
    static async create(user: Users, request: CreateBudgetRequest): Promise<BudgetResponse> {
        const createRequest = Validation.validate(BudgetValidation.CREATE, request)

        const game = await prismaClient.games.findUnique({
            where: {
                game_id: createRequest.game_id
            }
        })

        if (!game) {
            throw new ResponseError(404, "Game not found")
        }

        const budget = await prismaClient.budget.create({
            data: {
                user_id: user.user_id,
                game_id: createRequest.game_id,
                allocated_budget: createRequest.allocated_budget,
                allocated_currency: createRequest.allocated_currency,
                allocated_tickets: createRequest.allocated_tickets,
                remaining_budget: createRequest.remaining_budget,
                remaining_currency: createRequest.remaining_currency,
                remaining_tickets: createRequest.remaining_tickets
            }
        })

        return toBudgetResponse(budget)
    }

    static async getByUserId(user: Users): Promise<BudgetResponse[]> {
        const budgets = await prismaClient.budget.findMany({
            where: {
                user_id: user.user_id
            }
        })

        return budgets.map(budget => toBudgetResponse(budget))
    }

    static async getByGameId(user: Users, gameId: number): Promise<BudgetResponse> {
        const budget = await prismaClient.budget.findFirst({
            where: {
                user_id: user.user_id,
                game_id: gameId
            }
        })

        if (!budget) {
            throw new ResponseError(404, "Budget not found")
        }

        return toBudgetResponse(budget)
    }

    static async update(user: Users, budgetId: number, request: Partial<CreateBudgetRequest>): Promise<BudgetResponse> {
        const updateRequest = Validation.validate(BudgetValidation.UPDATE, request)

        const existingBudget = await prismaClient.budget.findUnique({
            where: {
                budget_id: budgetId,
                user_id: user.user_id
            }
        })

        if (!existingBudget) {
            throw new ResponseError(404, "Budget not found")
        }

        const budget = await prismaClient.budget.update({
            where: {
                budget_id: budgetId
            },
            data: updateRequest
        })

        return toBudgetResponse(budget)
    }
}