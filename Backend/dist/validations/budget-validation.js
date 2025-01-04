"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.BudgetValidation = void 0;
const zod_1 = require("zod");
class BudgetValidation {
}
exports.BudgetValidation = BudgetValidation;
BudgetValidation.CREATE = zod_1.z.object({
    game_id: zod_1.z.number().min(1),
    allocated_budget: zod_1.z.number().min(0),
    allocated_currency: zod_1.z.number().min(0),
    allocated_tickets: zod_1.z.number().min(0),
    remaining_budget: zod_1.z.number().min(0),
    remaining_currency: zod_1.z.number().min(0),
    remaining_tickets: zod_1.z.number().min(0)
});
BudgetValidation.UPDATE = zod_1.z.object({
    allocated_budget: zod_1.z.number().min(0).optional(),
    allocated_currency: zod_1.z.number().min(0).optional(),
    allocated_tickets: zod_1.z.number().min(0).optional(),
    remaining_budget: zod_1.z.number().min(0).optional(),
    remaining_currency: zod_1.z.number().min(0).optional(),
    remaining_tickets: zod_1.z.number().min(0).optional()
});
