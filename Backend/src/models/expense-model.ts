import { Expenses } from "@prisma/client"

export interface CreateExpenseRequest {
    game_id: number
    spent_currency: number
    spent_money: number
    spent_tickets: number
}

export interface ExpenseResponse {
    expense_id: number
    game_id: number
    spent_currency: number
    spent_money: number
    spent_tickets: number
}

export function toExpenseResponse(expense: Expenses): ExpenseResponse {
    return {
        expense_id: expense.expense_id,
        game_id: expense.game_id,
        spent_currency: expense.spent_currency,
        spent_money: expense.spent_money,
        spent_tickets: expense.spent_tickets
    }
}