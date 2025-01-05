"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.BannerValidation = void 0;
const zod_1 = require("zod");
class BannerValidation {
}
exports.BannerValidation = BannerValidation;
BannerValidation.CREATE = zod_1.z.object({
    game_id: zod_1.z.number().min(1),
    banner_name: zod_1.z.string().min(1),
    type: zod_1.z.string().min(1),
    start_date: zod_1.z.coerce.date(),
    end_date: zod_1.z.coerce.date(),
    hard_pity: zod_1.z.number().min(1).optional(),
    soft_pity: zod_1.z.number().min(1).optional(),
    image_url: zod_1.z.string().url().optional()
});
BannerValidation.UPDATE = zod_1.z.object({
    banner_name: zod_1.z.string().min(1).optional(),
    type: zod_1.z.string().min(1).optional(),
    start_date: zod_1.z.coerce.date().optional(),
    end_date: zod_1.z.coerce.date().optional(),
    hard_pity: zod_1.z.number().min(1).optional(),
    soft_pity: zod_1.z.number().min(1).optional(),
    image_url: zod_1.z.string().url().optional()
});
