"use strict";
// import { prismaClient } from "../application/database";
// import { ResponseError } from "../error/response-error";
// import { BannerItem } from "../models/banner-item-model";
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
exports.BannerItemService = void 0;
// export class BannerItemService {
//   static async create(data: BannerItem) {
//     const banner = await prismaClient.banners.findUnique({
//       where: {
//         banner_id: data.banner_id,
//       },
//     });
//     if (!banner) {
//       throw new ResponseError(404, "Banner not found");
//     }
//     const item = await prismaClient.items.findUnique({
//       where: {
//         item_id: data.item_id,
//       },
//     });
//     if (!item) {
//       throw new ResponseError(404, "Item not found");
//     }
//     return await prismaClient.bannerItems.create({
//       data,
//     });
//   }
//   static async get(banner_id: number, item_id: number) {
//     const bannerItem = await prismaClient.bannerItems.findUnique({
//       where: {
//         banner_id_item_id: {
//           banner_id,
//           item_id,
//         },
//       },
//     });
//     if (!bannerItem) {
//       throw new ResponseError(404, "BannerItem not found");
//     }
//     return bannerItem;
//   }
//   static async update(banner_id: number, item_id: number, data: Partial<BannerItem>) {
//     const bannerItem = await prismaClient.bannerItems.findUnique({
//       where: {
//         banner_id_item_id: {
//           banner_id,
//           item_id,
//         },
//       },
//     });
//     if (!bannerItem) {
//       throw new ResponseError(404, "BannerItem not found");
//     }
//     return await prismaClient.bannerItems.update({
//       where: {
//         banner_id_item_id: {
//           banner_id,
//           item_id,
//         },
//       },
//       data,
//     });
//   }
//   static async delete(banner_id: number, item_id: number) {
//     const bannerItem = await prismaClient.bannerItems.findUnique({
//       where: {
//         banner_id_item_id: {
//           banner_id,
//           item_id,
//         },
//       },
//     });
//     if (!bannerItem) {
//       throw new ResponseError(404, "BannerItem not found");
//     }
//     return await prismaClient.bannerItems.delete({
//       where: {
//         banner_id_item_id: {
//           banner_id,
//           item_id,
//         },
//       },
//     });
//   }
//   static async getAllItemsInBanner(banner_id: number) {
//     const bannerItems = await prismaClient.bannerItems.findMany({
//       where: {
//         banner_id,
//       },
//     });
//     if (bannerItems.length === 0) {
//       throw new ResponseError(404, "No items found for this banner");
//     }
//     return bannerItems;
//   }
// }
const database_1 = require("../application/database");
const response_error_1 = require("../error/response-error");
const banner_item_validation_1 = require("../validations/banner-item-validation");
const validation_1 = require("../validations/validation");
class BannerItemService {
    static create(data) {
        return __awaiter(this, void 0, void 0, function* () {
            const validatedData = validation_1.Validation.validate(banner_item_validation_1.BannerItemValidation.CREATE, data);
            const banner = yield database_1.prismaClient.banners.findUnique({
                where: {
                    banner_id: validatedData.banner_id,
                },
            });
            if (!banner) {
                throw new response_error_1.ResponseError(404, "Banner not found");
            }
            const item = yield database_1.prismaClient.items.findUnique({
                where: {
                    item_id: validatedData.item_id,
                },
            });
            if (!item) {
                throw new response_error_1.ResponseError(404, "Item not found");
            }
            return yield database_1.prismaClient.bannerItems.create({
                data: validatedData,
            });
        });
    }
    static get(banner_id, item_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const bannerItem = yield database_1.prismaClient.bannerItems.findUnique({
                where: {
                    banner_id_item_id: {
                        banner_id,
                        item_id,
                    },
                },
            });
            if (!bannerItem) {
                throw new response_error_1.ResponseError(404, "BannerItem not found");
            }
            return bannerItem;
        });
    }
    static update(banner_id, item_id, data) {
        return __awaiter(this, void 0, void 0, function* () {
            const validatedData = validation_1.Validation.validate(banner_item_validation_1.BannerItemValidation.UPDATE, data);
            const bannerItem = yield database_1.prismaClient.bannerItems.findUnique({
                where: {
                    banner_id_item_id: {
                        banner_id,
                        item_id,
                    },
                },
            });
            if (!bannerItem) {
                throw new response_error_1.ResponseError(404, "BannerItem not found");
            }
            return yield database_1.prismaClient.bannerItems.update({
                where: {
                    banner_id_item_id: {
                        banner_id,
                        item_id,
                    },
                },
                data: validatedData,
            });
        });
    }
    static delete(banner_id, item_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const bannerItem = yield database_1.prismaClient.bannerItems.findUnique({
                where: {
                    banner_id_item_id: {
                        banner_id,
                        item_id,
                    },
                },
            });
            if (!bannerItem) {
                throw new response_error_1.ResponseError(404, "BannerItem not found");
            }
            return yield database_1.prismaClient.bannerItems.delete({
                where: {
                    banner_id_item_id: {
                        banner_id,
                        item_id,
                    },
                },
            });
        });
    }
    static getAllItemsInBanner(banner_id) {
        return __awaiter(this, void 0, void 0, function* () {
            const bannerItems = yield database_1.prismaClient.bannerItems.findMany({
                where: {
                    banner_id,
                },
            });
            if (bannerItems.length === 0) {
                throw new response_error_1.ResponseError(404, "No items found for this banner");
            }
            return bannerItems;
        });
    }
}
exports.BannerItemService = BannerItemService;
