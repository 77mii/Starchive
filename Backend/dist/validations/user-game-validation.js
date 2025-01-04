"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.UserGameValidation = void 0;
const zod_1 = require("zod");
class UserGameValidation {
}
exports.UserGameValidation = UserGameValidation;
UserGameValidation.ADD = zod_1.z.object({
    game_id: zod_1.z.number().min(1)
});
