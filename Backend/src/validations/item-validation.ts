import { z, ZodType } from "zod"

export class ItemValidation {
    static readonly CREATE: ZodType = z.object({
        rarity: z.number().int().min(1),
        item_name: z.string().min(1),
        image_url: z.string().url().optional()
    })
}