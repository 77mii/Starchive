import { Plans } from "@prisma/client"

export interface CreatePlanRequest {
    game_id: number
    plan_description: string
    plan_budget: number
    plan_currency: number 
    plan_tickets: number
}

export interface PlanResponse {
    plan_id: number
    game_id: number
    plan_description: string
    plan_budget: number
    plan_currency: number
    plan_tickets: number
}

export function toPlanResponse(plan: Plans): PlanResponse {
    return {
        plan_id: plan.plan_id,
        game_id: plan.game_id,
        plan_description: plan.plan_description,
        plan_budget: plan.plan_budget,
        plan_currency: plan.plan_currency,
        plan_tickets: plan.plan_tickets
    }
}