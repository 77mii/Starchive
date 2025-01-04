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
exports.PityService = void 0;
const database_1 = require("../application/database");
const response_error_1 = require("../error/response-error");
const pity_model_1 = require("../models/pity-model");
const pity_validation_1 = require("../validations/pity-validation");
const validation_1 = require("../validations/validation");
class PityService {
    static create(user, request) {
        return __awaiter(this, void 0, void 0, function* () {
            const createRequest = validation_1.Validation.validate(pity_validation_1.PityValidation.CREATE, request);
            const game = yield database_1.prismaClient.games.findUnique({
                where: {
                    game_id: createRequest.game_id
                }
            });
            if (!game) {
                throw new response_error_1.ResponseError(404, "Game not found");
            }
            const banner = yield database_1.prismaClient.banners.findUnique({
                where: {
                    banner_id: createRequest.banner_id
                }
            });
            if (!banner) {
                throw new response_error_1.ResponseError(404, "Banner not found");
            }
            const pity = yield database_1.prismaClient.hardPity.create({
                data: {
                    user_id: user.user_id,
                    game_id: createRequest.game_id,
                    banner_id: createRequest.banner_id,
                    pull_towards_pity: createRequest.pull_towards_pity
                }
            });
            return (0, pity_model_1.toPityResponse)(pity);
        });
    }
    static getByBannerId(user, bannerId) {
        return __awaiter(this, void 0, void 0, function* () {
            const pities = yield database_1.prismaClient.hardPity.findMany({
                where: {
                    user_id: user.user_id,
                    banner_id: bannerId
                }
            });
            return pities.map(pity => (0, pity_model_1.toPityResponse)(pity));
        });
    }
    static update(pityId, request) {
        return __awaiter(this, void 0, void 0, function* () {
            const updateRequest = validation_1.Validation.validate(pity_validation_1.PityValidation.UPDATE, request);
            const existingPity = yield database_1.prismaClient.hardPity.findUnique({
                where: {
                    pity_id: pityId
                }
            });
            if (!existingPity) {
                throw new response_error_1.ResponseError(404, "Pity not found");
            }
            const pity = yield database_1.prismaClient.hardPity.update({
                where: {
                    pity_id: pityId
                },
                data: updateRequest
            });
            return (0, pity_model_1.toPityResponse)(pity);
        });
    }
}
exports.PityService = PityService;
