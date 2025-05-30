"use strict";
// this router can only be accessed by unauthenticated people
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.publicRouter = void 0;
const express_1 = __importDefault(require("express"));
const user_controller_1 = require("../controllers/user-controller");
exports.publicRouter = express_1.default.Router();
exports.publicRouter.post("/api/register", user_controller_1.UserController.register);
exports.publicRouter.post("/api/login", user_controller_1.UserController.login);
// the below function is for testing only pls delet later thnks
exports.publicRouter.get("/api/users", user_controller_1.UserController.getAllUsers);
