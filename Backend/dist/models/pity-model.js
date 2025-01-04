"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toPityResponse = toPityResponse;
function toPityResponse(pity) {
    return {
        pity_id: pity.pity_id,
        game_id: pity.game_id,
        banner_id: pity.banner_id,
        pull_towards_pity: pity.pull_towards_pity
    };
}
