"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ArticleValidation = void 0;
const zod_1 = require("zod");
class ArticleValidation {
}
exports.ArticleValidation = ArticleValidation;
ArticleValidation.CREATE = zod_1.z.object({
    title: zod_1.z.string().min(1),
    text: zod_1.z.string().min(1),
    image_url: zod_1.z.string().url().optional(),
    game_id: zod_1.z.number().min(1),
    createdDate: zod_1.z.coerce.date().optional()
});
ArticleValidation.UPDATE = zod_1.z.object({
    title: zod_1.z.string().min(1).optional(),
    text: zod_1.z.string().min(1).optional(),
    image_url: zod_1.z.string().url().optional(),
    game_id: zod_1.z.number().min(1).optional(),
    createdDate: zod_1.z.coerce.date().optional()
});
