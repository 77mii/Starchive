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
exports.BannerService = void 0;
const database_1 = require("../application/database");
const response_error_1 = require("../error/response-error");
const banner_model_1 = require("../models/banner-model");
const banner_validation_1 = require("../validations/banner-validation");
const validation_1 = require("../validations/validation");
class BannerService {
    static create(request) {
        return __awaiter(this, void 0, void 0, function* () {
            const createRequest = validation_1.Validation.validate(banner_validation_1.BannerValidation.CREATE, request);
            const game = yield database_1.prismaClient.games.findUnique({
                where: {
                    game_id: createRequest.game_id
                }
            });
            if (!game) {
                throw new response_error_1.ResponseError(404, "Game not found");
            }
            const banner = yield database_1.prismaClient.banners.create({
                data: createRequest
            });
            return (0, banner_model_1.toBannerResponse)(banner);
        });
    }
    static getByGameId(gameId) {
        return __awaiter(this, void 0, void 0, function* () {
            const banners = yield database_1.prismaClient.banners.findMany({
                where: {
                    game_id: gameId
                }
            });
            return banners.map(banner => (0, banner_model_1.toBannerResponse)(banner));
        });
    }
    static update(bannerId, request) {
        return __awaiter(this, void 0, void 0, function* () {
            const updateRequest = validation_1.Validation.validate(banner_validation_1.BannerValidation.UPDATE, request);
            const existingBanner = yield database_1.prismaClient.banners.findUnique({
                where: {
                    banner_id: bannerId
                }
            });
            if (!existingBanner) {
                throw new response_error_1.ResponseError(404, "Banner not found");
            }
            const banner = yield database_1.prismaClient.banners.update({
                where: {
                    banner_id: bannerId
                },
                data: updateRequest
            });
            return (0, banner_model_1.toBannerResponse)(banner);
        });
    }
    static getActive() {
        return __awaiter(this, void 0, void 0, function* () {
            const currentDate = new Date();
            const banners = yield database_1.prismaClient.banners.findMany({
                where: {
                    AND: [
                        {
                            start_date: {
                                lte: currentDate
                            }
                        },
                        {
                            end_date: {
                                gte: currentDate
                            }
                        }
                    ]
                }
            });
            return banners.map(banner => (0, banner_model_1.toBannerResponse)(banner));
        });
    }
}
exports.BannerService = BannerService;
