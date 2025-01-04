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
exports.GameService = void 0;
const database_1 = require("../application/database");
const game_model_1 = require("../models/game-model");
const game_validation_1 = require("../validations/game-validation");
const validation_1 = require("../validations/validation");
class GameService {
    static create(request) {
        return __awaiter(this, void 0, void 0, function* () {
            const createRequest = validation_1.Validation.validate(game_validation_1.GameValidation.CREATE, request);
            const game = yield database_1.prismaClient.games.create({
                data: createRequest
            });
            return (0, game_model_1.toGameResponse)(game);
        });
    }
    static getAll() {
        return __awaiter(this, void 0, void 0, function* () {
            const games = yield database_1.prismaClient.games.findMany();
            return games.map(game => (0, game_model_1.toGameResponse)(game));
        });
    }
    static getById(gameId) {
        return __awaiter(this, void 0, void 0, function* () {
            const game = yield database_1.prismaClient.games.findUnique({
                where: {
                    game_id: gameId
                }
            });
            if (!game) {
                throw new Error("Game not found");
            }
            return (0, game_model_1.toGameResponse)(game);
        });
    }
    static update(gameId, request) {
        return __awaiter(this, void 0, void 0, function* () {
            const updateRequest = validation_1.Validation.validate(game_validation_1.GameValidation.UPDATE, request);
            const existingGame = yield database_1.prismaClient.games.findUnique({
                where: {
                    game_id: gameId
                }
            });
            if (!existingGame) {
                throw new Error("Game not found");
            }
            const game = yield database_1.prismaClient.games.update({
                where: {
                    game_id: gameId
                },
                data: updateRequest
            });
            return (0, game_model_1.toGameResponse)(game);
        });
    }
}
exports.GameService = GameService;
