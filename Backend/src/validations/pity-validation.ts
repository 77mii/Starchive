import { z, ZodType } from "zod"

export class PityValidation {
    static readonly CREATE: ZodType = z.object({
        game_id: z.number().min(1),
        banner_id: z.number().min(1),
        pull_towards_pity: z.number().min(0)
    })

    static readonly UPDATE: ZodType = z.object({
        pull_towards_pity: z.number().min(0)
    })
}