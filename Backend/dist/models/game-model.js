"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toGameResponse = toGameResponse;
function toGameResponse(game) {
    return {
        game_id: game.game_id,
        game_name: game.game_name,
        income: game.income,
        description: game.description,
        currency_name: game.currency_name,
        tickets_name: game.tickets_name
    };
}
