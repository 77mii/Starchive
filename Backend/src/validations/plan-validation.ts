import { z, ZodType } from "zod"

export class PlanValidation {
    static readonly CREATE: ZodType = z.object({
        game_id: z.number().min(1),
        plan_description: z.string().min(1),
        plan_budget: z.number().min(0),
        plan_currency: z.number().min(0),
        plan_tickets: z.number().min(0)
    })
}