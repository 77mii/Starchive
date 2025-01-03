import { Budget } from "@prisma/client"

export interface CreateBudgetRequest {
    game_id: number
    allocated_budget: number
    allocated_currency: number
    allocated_tickets: number
    remaining_budget: number
    remaining_currency: number
    remaining_tickets: number
}

export interface BudgetResponse {
    budget_id: number
    game_id: number
    allocated_budget: number
    allocated_currency: number
    allocated_tickets: number
    remaining_budget: number
    remaining_currency: number
    remaining_tickets: number
}

export function toBudgetResponse(budget: Budget): BudgetResponse {
    return {
        budget_id: budget.budget_id,
        game_id: budget.game_id,
        allocated_budget: budget.allocated_budget,
        allocated_currency: budget.allocated_currency,
        allocated_tickets: budget.allocated_tickets,
        remaining_budget: budget.remaining_budget,
        remaining_currency: budget.remaining_currency,
        remaining_tickets: budget.remaining_tickets
    }
}