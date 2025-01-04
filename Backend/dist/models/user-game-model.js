"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toUserGameResponse = toUserGameResponse;
function toUserGameResponse(userGame) {
    return {
        user_id: userGame.user_id,
        game_id: userGame.game_id
    };
}
