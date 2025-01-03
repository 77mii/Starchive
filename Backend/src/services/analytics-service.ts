import { prismaClient } from "../application/database"
import { Users } from "@prisma/client"

export class AnalyticsService {
    static async getSpendingAnalysis(user: Users, gameId: number) {
        const expenses = await prismaClient.expenses.findMany({
            where: {
                user_id: user.user_id,
                game_id: gameId
            }
        })

        const totalSpentCurrency = expenses.reduce((sum, expense) => sum + expense.spent_currency, 0)
        const totalSpentMoney = expenses.reduce((sum, expense) => sum + expense.spent_money, 0)
        const totalSpentTickets = expenses.reduce((sum, expense) => sum + expense.spent_tickets, 0)

        return {
            totalSpentCurrency,
            totalSpentMoney,
            totalSpentTickets
        }
    }

    static async getPityHistory(user: Users, bannerId: number) {
        const pities = await prismaClient.hardPity.findMany({
            where: {
                user_id: user.user_id,
                banner_id: bannerId
            }
        })

        return pities.map(pity => ({
            pity_id: pity.pity_id,
            pull_towards_pity: pity.pull_towards_pity
        }))
    }

    static async getBudgetUsage(user: Users, gameId: number) {
        const budget = await prismaClient.budget.findFirst({
            where: {
                user_id: user.user_id,
                game_id: gameId
            }
        })

        if (!budget) {
            throw new Error("Budget not found")
        }

        return {
            allocated_budget: budget.allocated_budget,
            remaining_budget: budget.remaining_budget,
            allocated_currency: budget.allocated_currency,
            remaining_currency: budget.remaining_currency,
            allocated_tickets: budget.allocated_tickets,
            remaining_tickets: budget.remaining_tickets
        }
    }
}