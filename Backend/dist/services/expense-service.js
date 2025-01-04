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
exports.ExpenseService = void 0;
const database_1 = require("../application/database");
const response_error_1 = require("../error/response-error");
const expense_model_1 = require("../models/expense-model");
const expense_validation_1 = require("../validations/expense-validation");
const validation_1 = require("../validations/validation");
class ExpenseService {
    static create(user, request) {
        return __awaiter(this, void 0, void 0, function* () {
            const createRequest = validation_1.Validation.validate(expense_validation_1.ExpenseValidation.CREATE, request);
            const game = yield database_1.prismaClient.games.findUnique({
                where: {
                    game_id: createRequest.game_id
                }
            });
            if (!game) {
                throw new response_error_1.ResponseError(404, "Game not found");
            }
            const expense = yield database_1.prismaClient.expenses.create({
                data: {
                    user_id: user.user_id,
                    game_id: createRequest.game_id,
                    spent_currency: createRequest.spent_currency,
                    spent_money: createRequest.spent_money,
                    spent_tickets: createRequest.spent_tickets
                }
            });
            return (0, expense_model_1.toExpenseResponse)(expense);
        });
    }
    static getByUserId(user) {
        return __awaiter(this, void 0, void 0, function* () {
            const expenses = yield database_1.prismaClient.expenses.findMany({
                where: {
                    user_id: user.user_id
                }
            });
            return expenses.map(expense => (0, expense_model_1.toExpenseResponse)(expense));
        });
    }
    static getByGameId(user, gameId) {
        return __awaiter(this, void 0, void 0, function* () {
            const expenses = yield database_1.prismaClient.expenses.findMany({
                where: {
                    user_id: user.user_id,
                    game_id: gameId
                }
            });
            return expenses.map(expense => (0, expense_model_1.toExpenseResponse)(expense));
        });
    }
}
exports.ExpenseService = ExpenseService;
