import { z, ZodType } from "zod"

export class UserGameValidation {
    static readonly ADD: ZodType = z.object({
        game_id: z.number().min(1)
    })
}