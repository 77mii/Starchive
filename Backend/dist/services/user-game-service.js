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
exports.UserGameService = void 0;
const database_1 = require("../application/database");
const response_error_1 = require("../error/response-error");
const user_game_model_1 = require("../models/user-game-model");
const user_game_validation_1 = require("../validations/user-game-validation");
const validation_1 = require("../validations/validation");
class UserGameService {
    static add(user, request) {
        return __awaiter(this, void 0, void 0, function* () {
            const addRequest = validation_1.Validation.validate(user_game_validation_1.UserGameValidation.ADD, request);
            const game = yield database_1.prismaClient.games.findUnique({
                where: {
                    game_id: addRequest.game_id
                }
            });
            if (!game) {
                throw new response_error_1.ResponseError(404, "Game not found");
            }
            const userGame = yield database_1.prismaClient.userGames.create({
                data: {
                    user_id: user.user_id,
                    game_id: addRequest.game_id
                }
            });
            return (0, user_game_model_1.toUserGameResponse)(userGame);
        });
    }
    static getByUserId(user) {
        return __awaiter(this, void 0, void 0, function* () {
            const userGames = yield database_1.prismaClient.userGames.findMany({
                where: {
                    user_id: user.user_id
                }
            });
            return userGames.map(userGame => (0, user_game_model_1.toUserGameResponse)(userGame));
        });
    }
    static remove(user, gameId) {
        return __awaiter(this, void 0, void 0, function* () {
            const userGame = yield database_1.prismaClient.userGames.findUnique({
                where: {
                    user_id_game_id: {
                        user_id: user.user_id,
                        game_id: gameId
                    }
                }
            });
            if (!userGame) {
                throw new response_error_1.ResponseError(404, "User game relation not found");
            }
            yield database_1.prismaClient.userGames.delete({
                where: {
                    user_id_game_id: {
                        user_id: user.user_id,
                        game_id: gameId
                    }
                }
            });
        });
    }
}
exports.UserGameService = UserGameService;
