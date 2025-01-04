"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toUserResponse = toUserResponse;
function toUserResponse(prismaUser) {
    var _a;
    return {
        token: (_a = prismaUser.token) !== null && _a !== void 0 ? _a : "",
        username: prismaUser.username,
    };
}
