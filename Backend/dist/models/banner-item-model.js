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
exports.createBannerItem = createBannerItem;
exports.getBannerItem = getBannerItem;
exports.updateBannerItem = updateBannerItem;
exports.deleteBannerItem = deleteBannerItem;
exports.getAllItemsInBanner = getAllItemsInBanner;
const client_1 = require("@prisma/client");
const prisma = new client_1.PrismaClient();
function createBannerItem(data) {
    return __awaiter(this, void 0, void 0, function* () {
        return yield prisma.bannerItems.create({
            data,
        });
    });
}
function getBannerItem(banner_id, item_id) {
    return __awaiter(this, void 0, void 0, function* () {
        return yield prisma.bannerItems.findUnique({
            where: {
                banner_id_item_id: {
                    banner_id,
                    item_id,
                },
            },
        });
    });
}
function updateBannerItem(banner_id, item_id, data) {
    return __awaiter(this, void 0, void 0, function* () {
        return yield prisma.bannerItems.update({
            where: {
                banner_id_item_id: {
                    banner_id,
                    item_id,
                },
            },
            data,
        });
    });
}
function deleteBannerItem(banner_id, item_id) {
    return __awaiter(this, void 0, void 0, function* () {
        return yield prisma.bannerItems.delete({
            where: {
                banner_id_item_id: {
                    banner_id,
                    item_id,
                },
            },
        });
    });
}
function getAllItemsInBanner(banner_id) {
    return __awaiter(this, void 0, void 0, function* () {
        return yield prisma.bannerItems.findMany({
            where: {
                banner_id,
            },
        });
    });
}
