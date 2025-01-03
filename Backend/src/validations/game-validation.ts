import { z, ZodType } from "zod"

export class GameValidation {
    static readonly CREATE: ZodType = z.object({
        game_name: z.string().min(1),
        income: z.number().min(0),
        description: z.string().min(1),
        currency_name: z.string().min(1),
        tickets_name: z.string().optional()
    })

    static readonly UPDATE: ZodType = z.object({
        game_name: z.string().min(1).optional(),
        income: z.number().min(0).optional(),
        description: z.string().min(1).optional(),
        currency_name: z.string().min(1).optional(),
        tickets_name: z.string().optional()
    })
}