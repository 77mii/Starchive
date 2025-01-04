"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.PlanValidation = void 0;
const zod_1 = require("zod");
class PlanValidation {
}
exports.PlanValidation = PlanValidation;
PlanValidation.CREATE = zod_1.z.object({
    game_id: zod_1.z.number().min(1),
    plan_description: zod_1.z.string().min(1),
    plan_budget: zod_1.z.number().min(0),
    plan_currency: zod_1.z.number().min(0),
    plan_tickets: zod_1.z.number().min(0)
});
