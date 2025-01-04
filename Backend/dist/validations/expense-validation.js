"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ExpenseValidation = void 0;
const zod_1 = require("zod");
class ExpenseValidation {
}
exports.ExpenseValidation = ExpenseValidation;
ExpenseValidation.CREATE = zod_1.z.object({
    game_id: zod_1.z.number().min(1),
    spent_currency: zod_1.z.number().min(0),
    spent_money: zod_1.z.number().min(0),
    spent_tickets: zod_1.z.number().min(0)
});
