"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.PityValidation = void 0;
const zod_1 = require("zod");
class PityValidation {
}
exports.PityValidation = PityValidation;
PityValidation.CREATE = zod_1.z.object({
    game_id: zod_1.z.number().min(1),
    banner_id: zod_1.z.number().min(1),
    pull_towards_pity: zod_1.z.number().min(0)
});
PityValidation.UPDATE = zod_1.z.object({
    pull_towards_pity: zod_1.z.number().min(0)
});
