import { prismaClient } from "../application/database"
import { ResponseError } from "../error/response-error"
import { CreatePlanRequest, PlanResponse, toPlanResponse } from "../models/plan-model"
import { PlanValidation } from "../validations/plan-validation"
import { Validation } from "../validations/validation"
import { Users } from "@prisma/client"

export class PlanService {
    static async create(user: Users, request: CreatePlanRequest): Promise<PlanResponse> {
        const createRequest = Validation.validate(PlanValidation.CREATE, request)

        const game = await prismaClient.games.findUnique({
            where: {
                game_id: createRequest.game_id
            }
        })

        if (!game) {
            throw new ResponseError(404, "Game not found")
        }

        const plan = await prismaClient.plans.create({
            data: {
                user_id: user.user_id,
                game_id: createRequest.game_id,
                plan_description: createRequest.plan_description,
                plan_budget: createRequest.plan_budget,
                plan_currency: createRequest.plan_currency,
                plan_tickets: createRequest.plan_tickets
            }
        })

        return toPlanResponse(plan)
    }

    static async getByUserId(user: Users): Promise<PlanResponse[]> {
        const plans = await prismaClient.plans.findMany({
            where: {
                user_id: user.user_id
            }
        })
    
        return plans.map(plan => toPlanResponse(plan))
    }

    static async delete(user: Users, planId: number): Promise<void> {
        const plan = await prismaClient.plans.findUnique({
            where: {
                plan_id: planId,
                user_id: user.user_id
            }
        })

        if (!plan) {
            throw new ResponseError(404, "Plan not found")
        }

        await prismaClient.plans.delete({
            where: {
                plan_id: planId
            }
        })
    }
}