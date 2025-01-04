"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ItemValidation = void 0;
const zod_1 = require("zod");
class ItemValidation {
}
exports.ItemValidation = ItemValidation;
ItemValidation.CREATE = zod_1.z.object({
    rarity: zod_1.z.string().min(1),
    item_name: zod_1.z.string().min(1)
});
