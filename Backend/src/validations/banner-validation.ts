import { z, ZodType } from "zod"

export class BannerValidation {
    static readonly CREATE: ZodType = z.object({
        game_id: z.number().min(1),
        banner_name: z.string().min(1),
        type: z.string().min(1),
        start_date: z.coerce.date(),
        end_date: z.coerce.date(),
        hard_pity: z.number().min(1).optional(),
        soft_pity: z.number().min(1).optional()
    })

    static readonly UPDATE: ZodType = z.object({
        banner_name: z.string().min(1).optional(),
        type: z.string().min(1).optional(),
        start_date: z.coerce.date().optional(),
        end_date: z.coerce.date().optional(),
        hard_pity: z.number().min(1).optional(),
        soft_pity: z.number().min(1).optional()
    })
}