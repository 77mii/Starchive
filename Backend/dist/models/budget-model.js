"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toBudgetResponse = toBudgetResponse;
function toBudgetResponse(budget) {
    return {
        budget_id: budget.budget_id,
        game_id: budget.game_id,
        allocated_budget: budget.allocated_budget,
        allocated_currency: budget.allocated_currency,
        allocated_tickets: budget.allocated_tickets,
        remaining_budget: budget.remaining_budget,
        remaining_currency: budget.remaining_currency,
        remaining_tickets: budget.remaining_tickets
    };
}
