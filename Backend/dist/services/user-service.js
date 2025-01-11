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
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.UserService = void 0;
const database_1 = require("../application/database");
const response_error_1 = require("../error/response-error");
const user_model_1 = require("../models/user-model");
const user_validation_1 = require("../validations/user-validation");
const validation_1 = require("../validations/validation");
const bcrypt_1 = __importDefault(require("bcrypt"));
const uuid_1 = require("uuid");
class UserService {
    static register(request) {
        return __awaiter(this, void 0, void 0, function* () {
            // validate request
            const registerRequest = validation_1.Validation.validate(user_validation_1.UserValidation.REGISTER, request);
            const email = yield database_1.prismaClient.users.findFirst({
                where: {
                    username: registerRequest.username,
                },
            });
            if (email) {
                throw new response_error_1.ResponseError(400, "Username already exists!");
            }
            // encrypt password
            registerRequest.password = yield bcrypt_1.default.hash(registerRequest.password, 10);
            // add user to db
            const user = yield database_1.prismaClient.users.create({
                data: {
                    username: registerRequest.username,
                    password: registerRequest.password,
                    token: (0, uuid_1.v4)(),
                },
            });
            // convert user to UserResponse and return it
            return (0, user_model_1.toUserResponse)(user);
        });
    }
    static login(request) {
        return __awaiter(this, void 0, void 0, function* () {
            const loginRequest = validation_1.Validation.validate(user_validation_1.UserValidation.LOGIN, request);
            let user = yield database_1.prismaClient.users.findFirst({
                where: {
                    username: loginRequest.username,
                },
            });
            if (!user) {
                throw new response_error_1.ResponseError(400, "Invalid username or password!");
            }
            const passwordIsValid = yield bcrypt_1.default.compare(loginRequest.password, user.password);
            if (!passwordIsValid) {
                throw new response_error_1.ResponseError(400, "Invalid email or password!");
            }
            user = yield database_1.prismaClient.users.update({
                where: {
                    user_id: user.user_id,
                },
                data: {
                    token: (0, uuid_1.v4)(),
                },
            });
            const response = (0, user_model_1.toUserResponse)(user);
            return response;
        });
    }
    static logout(user) {
        return __awaiter(this, void 0, void 0, function* () {
            const result = yield database_1.prismaClient.users.update({
                where: {
                    user_id: user.user_id,
                },
                data: {
                    token: null,
                },
            });
            return "Logout Successful!";
        });
    }
    //the below function is for testing only delete later pls thnks
    static getAllUsers() {
        return __awaiter(this, void 0, void 0, function* () {
            return yield database_1.prismaClient.users.findMany({
                select: {
                    user_id: true,
                    username: true,
                    password: true,
                    token: true,
                },
            });
        });
    }
}
exports.UserService = UserService;
