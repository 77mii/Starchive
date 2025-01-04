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
exports.AnalyticsController = void 0;
const analytics_service_1 = require("../services/analytics-service");
class AnalyticsController {
    static getSpendingAnalysis(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const gameId = parseInt(req.params.gameId);
                const response = yield analytics_service_1.AnalyticsService.getSpendingAnalysis(req.user, gameId);
                res.status(200).json({
                    data: response
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static getPityHistory(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const bannerId = parseInt(req.params.bannerId);
                const response = yield analytics_service_1.AnalyticsService.getPityHistory(req.user, bannerId);
                res.status(200).json({
                    data: response
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static getBudgetUsage(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const gameId = parseInt(req.params.gameId);
                const response = yield analytics_service_1.AnalyticsService.getBudgetUsage(req.user, gameId);
                res.status(200).json({
                    data: response
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
}
exports.AnalyticsController = AnalyticsController;
