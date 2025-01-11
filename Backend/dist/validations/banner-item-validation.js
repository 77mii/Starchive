"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.BannerItemValidation = void 0;
const zod_1 = require("zod");
class BannerItemValidation {
}
exports.BannerItemValidation = BannerItemValidation;
BannerItemValidation.CREATE = zod_1.z.object({
    banner_id: zod_1.z.number().int().min(1),
    item_id: zod_1.z.number().int().min(1),
    acquire_rate: zod_1.z.number().min(0).max(1),
});
BannerItemValidation.UPDATE = zod_1.z.object({
    acquire_rate: zod_1.z.number().min(0).max(1).optional(),
});
