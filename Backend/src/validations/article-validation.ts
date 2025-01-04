import { z, ZodType } from "zod"

export class ArticleValidation {
    static readonly CREATE: ZodType = z.object({
        title: z.string().min(1),
        text: z.string().min(1),
        image_url: z.string().url().optional()
    })

    static readonly UPDATE: ZodType = z.object({
        title: z.string().min(1).optional(),
        text: z.string().min(1).optional(),
        image_url: z.string().url().optional()
    })
}