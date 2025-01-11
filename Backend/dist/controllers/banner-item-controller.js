"use strict";
// import { NextFunction, Request, Response } from "express";
// import { BannerItem } from "../models/banner-item-model";
// import {BannerItemService} from "../services/banner-item-service";
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
exports.BannerItemController = void 0;
const banner_item_service_1 = require("../services/banner-item-service");
class BannerItemController {
    static create(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const request = req.body;
                const response = yield banner_item_service_1.BannerItemService.create(request);
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static getByBannerId(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const bannerId = parseInt(req.params.bannerId);
                const response = yield banner_item_service_1.BannerItemService.getAllItemsInBanner(bannerId);
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static get(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const bannerId = parseInt(req.params.bannerId);
                const itemId = parseInt(req.params.itemId);
                const response = yield banner_item_service_1.BannerItemService.get(bannerId, itemId);
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static update(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const bannerId = parseInt(req.params.bannerId);
                const itemId = parseInt(req.params.itemId);
                const request = req.body;
                const response = yield banner_item_service_1.BannerItemService.update(bannerId, itemId, request);
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static delete(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const bannerId = parseInt(req.params.bannerId);
                const itemId = parseInt(req.params.itemId);
                const response = yield banner_item_service_1.BannerItemService.delete(bannerId, itemId);
                res.status(200).json({
                    data: response,
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
}
exports.BannerItemController = BannerItemController;
