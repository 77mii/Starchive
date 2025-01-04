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
exports.PlanService = void 0;
const database_1 = require("../application/database");
const response_error_1 = require("../error/response-error");
const plan_model_1 = require("../models/plan-model");
const plan_validation_1 = require("../validations/plan-validation");
const validation_1 = require("../validations/validation");
class PlanService {
    static create(user, request) {
        return __awaiter(this, void 0, void 0, function* () {
            const createRequest = validation_1.Validation.validate(plan_validation_1.PlanValidation.CREATE, request);
            const game = yield database_1.prismaClient.games.findUnique({
                where: {
                    game_id: createRequest.game_id
                }
            });
            if (!game) {
                throw new response_error_1.ResponseError(404, "Game not found");
            }
            const plan = yield database_1.prismaClient.plans.create({
                data: {
                    user_id: user.user_id,
                    game_id: createRequest.game_id,
                    plan_description: createRequest.plan_description,
                    plan_budget: createRequest.plan_budget,
                    plan_currency: createRequest.plan_currency,
                    plan_tickets: createRequest.plan_tickets
                }
            });
            return (0, plan_model_1.toPlanResponse)(plan);
        });
    }
    static getByUserId(user) {
        return __awaiter(this, void 0, void 0, function* () {
            const plans = yield database_1.prismaClient.plans.findMany({
                where: {
                    user_id: user.user_id
                }
            });
            return plans.map(plan => (0, plan_model_1.toPlanResponse)(plan));
        });
    }
    static delete(user, planId) {
        return __awaiter(this, void 0, void 0, function* () {
            const plan = yield database_1.prismaClient.plans.findUnique({
                where: {
                    plan_id: planId,
                    user_id: user.user_id
                }
            });
            if (!plan) {
                throw new response_error_1.ResponseError(404, "Plan not found");
            }
            yield database_1.prismaClient.plans.delete({
                where: {
                    plan_id: planId
                }
            });
        });
    }
}
exports.PlanService = PlanService;
