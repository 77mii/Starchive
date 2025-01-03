import { z, ZodType } from "zod"

export class BudgetValidation {
    static readonly CREATE: ZodType = z.object({
        game_id: z.number().min(1),
        allocated_budget: z.number().min(0),
        allocated_currency: z.number().min(0),
        allocated_tickets: z.number().min(0),
        remaining_budget: z.number().min(0),
        remaining_currency: z.number().min(0),
        remaining_tickets: z.number().min(0)
    })

    static readonly UPDATE: ZodType = z.object({
        allocated_budget: z.number().min(0).optional(),
        allocated_currency: z.number().min(0).optional(),
        allocated_tickets: z.number().min(0).optional(),
        remaining_budget: z.number().min(0).optional(),
        remaining_currency: z.number().min(0).optional(),
        remaining_tickets: z.number().min(0).optional()
    })
}