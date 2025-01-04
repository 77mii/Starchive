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
exports.ItemService = void 0;
const database_1 = require("../application/database");
const item_model_1 = require("../models/item-model");
const item_validation_1 = require("../validations/item-validation");
const validation_1 = require("../validations/validation");
class ItemService {
    static create(request) {
        return __awaiter(this, void 0, void 0, function* () {
            const createRequest = validation_1.Validation.validate(item_validation_1.ItemValidation.CREATE, request);
            const item = yield database_1.prismaClient.items.create({
                data: createRequest
            });
            return (0, item_model_1.toItemResponse)(item);
        });
    }
    static getByGameId(gameId) {
        return __awaiter(this, void 0, void 0, function* () {
            const items = yield database_1.prismaClient.items.findMany({
                where: {
                    BannerItems: {
                        some: {
                            Banner: {
                                game_id: gameId
                            }
                        }
                    }
                }
            });
            return items.map(item => (0, item_model_1.toItemResponse)(item));
        });
    }
    static getByBannerId(bannerId) {
        return __awaiter(this, void 0, void 0, function* () {
            const bannerItems = yield database_1.prismaClient.bannerItems.findMany({
                where: {
                    banner_id: bannerId
                }
            });
            return bannerItems.map(bannerItem => (0, item_model_1.toBannerItemResponse)(bannerItem));
        });
    }
}
exports.ItemService = ItemService;
