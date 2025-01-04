"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toExpenseResponse = toExpenseResponse;
function toExpenseResponse(expense) {
    return {
        expense_id: expense.expense_id,
        game_id: expense.game_id,
        spent_currency: expense.spent_currency,
        spent_money: expense.spent_money,
        spent_tickets: expense.spent_tickets
    };
}
