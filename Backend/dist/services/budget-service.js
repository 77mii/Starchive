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
exports.BudgetService = void 0;
const database_1 = require("../application/database");
const response_error_1 = require("../error/response-error");
const budget_model_1 = require("../models/budget-model");
const budget_validation_1 = require("../validations/budget-validation");
const validation_1 = require("../validations/validation");
class BudgetService {
    static create(user, request) {
        return __awaiter(this, void 0, void 0, function* () {
            const createRequest = validation_1.Validation.validate(budget_validation_1.BudgetValidation.CREATE, request);
            const game = yield database_1.prismaClient.games.findUnique({
                where: {
                    game_id: createRequest.game_id
                }
            });
            if (!game) {
                throw new response_error_1.ResponseError(404, "Game not found");
            }
            const budget = yield database_1.prismaClient.budget.create({
                data: {
                    user_id: user.user_id,
                    game_id: createRequest.game_id,
                    allocated_budget: createRequest.allocated_budget,
                    allocated_currency: createRequest.allocated_currency,
                    allocated_tickets: createRequest.allocated_tickets,
                    remaining_budget: createRequest.remaining_budget,
                    remaining_currency: createRequest.remaining_currency,
                    remaining_tickets: createRequest.remaining_tickets
                }
            });
            return (0, budget_model_1.toBudgetResponse)(budget);
        });
    }
    static getByUserId(user) {
        return __awaiter(this, void 0, void 0, function* () {
            const budgets = yield database_1.prismaClient.budget.findMany({
                where: {
                    user_id: user.user_id
                }
            });
            return budgets.map(budget => (0, budget_model_1.toBudgetResponse)(budget));
        });
    }
    static getByGameId(user, gameId) {
        return __awaiter(this, void 0, void 0, function* () {
            const budget = yield database_1.prismaClient.budget.findFirst({
                where: {
                    user_id: user.user_id,
                    game_id: gameId
                }
            });
            if (!budget) {
                throw new response_error_1.ResponseError(404, "Budget not found");
            }
            return (0, budget_model_1.toBudgetResponse)(budget);
        });
    }
    static update(user, budgetId, request) {
        return __awaiter(this, void 0, void 0, function* () {
            const updateRequest = validation_1.Validation.validate(budget_validation_1.BudgetValidation.UPDATE, request);
            const existingBudget = yield database_1.prismaClient.budget.findUnique({
                where: {
                    budget_id: budgetId,
                    user_id: user.user_id
                }
            });
            if (!existingBudget) {
                throw new response_error_1.ResponseError(404, "Budget not found");
            }
            const budget = yield database_1.prismaClient.budget.update({
                where: {
                    budget_id: budgetId
                },
                data: updateRequest
            });
            return (0, budget_model_1.toBudgetResponse)(budget);
        });
    }
}
exports.BudgetService = BudgetService;
