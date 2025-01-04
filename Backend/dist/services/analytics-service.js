"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.AnalyticsService = void 0;
const database_1 = require("../application/database");
class AnalyticsService {
    static getSpendingAnalysis(user, gameId) {
        return __awaiter(this, void 0, void 0, function* () {
            const expenses = yield database_1.prismaClient.expenses.findMany({
                where: {
                    user_id: user.user_id,
                    game_id: gameId
                }
            });
            const totalSpentCurrency = expenses.reduce((sum, expense) => sum + expense.spent_currency, 0);
            const totalSpentMoney = expenses.reduce((sum, expense) => sum + expense.spent_money, 0);
            const totalSpentTickets = expenses.reduce((sum, expense) => sum + expense.spent_tickets, 0);
            return {
                totalSpentCurrency,
                totalSpentMoney,
                totalSpentTickets
            };
        });
    }
    static getPityHistory(user, bannerId) {
        return __awaiter(this, void 0, void 0, function* () {
            const pities = yield database_1.prismaClient.hardPity.findMany({
                where: {
                    user_id: user.user_id,
                    banner_id: bannerId
                }
            });
            return pities.map(pity => ({
                pity_id: pity.pity_id,
                pull_towards_pity: pity.pull_towards_pity
            }));
        });
    }
    static getBudgetUsage(user, gameId) {
        return __awaiter(this, void 0, void 0, function* () {
            const budget = yield database_1.prismaClient.budget.findFirst({
                where: {
                    user_id: user.user_id,
                    game_id: gameId
                }
            });
            if (!budget) {
                throw new Error("Budget not found");
            }
            return {
                allocated_budget: budget.allocated_budget,
                remaining_budget: budget.remaining_budget,
                allocated_currency: budget.allocated_currency,
                remaining_currency: budget.remaining_currency,
                allocated_tickets: budget.allocated_tickets,
                remaining_tickets: budget.remaining_tickets
            };
        });
    }
}
exports.AnalyticsService = AnalyticsService;
