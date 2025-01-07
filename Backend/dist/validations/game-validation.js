"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.GameValidation = void 0;
const zod_1 = require("zod");
class GameValidation {
}
exports.GameValidation = GameValidation;
GameValidation.CREATE = zod_1.z.object({
    game_name: zod_1.z.string().min(1),
    income: zod_1.z.number().min(0),
    description: zod_1.z.string().min(1),
    currency_name: zod_1.z.string().min(1),
    tickets_name: zod_1.z.string().optional(),
    image_url: zod_1.z.string().url().optional()
});
GameValidation.UPDATE = zod_1.z.object({
    game_name: zod_1.z.string().min(1).optional(),
    income: zod_1.z.number().min(0).optional(),
    description: zod_1.z.string().min(1).optional(),
    currency_name: zod_1.z.string().min(1).optional(),
    tickets_name: zod_1.z.string().optional(),
    image_url: zod_1.z.string().url().optional()
});
