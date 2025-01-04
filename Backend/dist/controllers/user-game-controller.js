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
exports.UserGameController = void 0;
const user_game_service_1 = require("../services/user-game-service");
class UserGameController {
    static add(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const request = req.body;
                const response = yield user_game_service_1.UserGameService.add(req.user, request);
                res.status(200).json({
                    data: response
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static getByUserId(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const response = yield user_game_service_1.UserGameService.getByUserId(req.user);
                res.status(200).json({
                    data: response
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
    static remove(req, res, next) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const gameId = parseInt(req.params.gameId);
                yield user_game_service_1.UserGameService.remove(req.user, gameId);
                res.status(200).json({
                    message: "Game removed from user's list"
                });
            }
            catch (error) {
                next(error);
            }
        });
    }
}
exports.UserGameController = UserGameController;
