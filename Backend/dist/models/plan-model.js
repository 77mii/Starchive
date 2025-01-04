"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toPlanResponse = toPlanResponse;
function toPlanResponse(plan) {
    return {
        plan_id: plan.plan_id,
        game_id: plan.game_id,
        plan_description: plan.plan_description,
        plan_budget: plan.plan_budget,
        plan_currency: plan.plan_currency,
        plan_tickets: plan.plan_tickets
    };
}
