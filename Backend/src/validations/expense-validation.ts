import { z, ZodType } from "zod"

export class ExpenseValidation {
    static readonly CREATE: ZodType = z.object({
        game_id: z.number().min(1),
        spent_currency: z.number().min(0),
        spent_money: z.number().min(0),
        spent_tickets: z.number().min(0)
    })
}