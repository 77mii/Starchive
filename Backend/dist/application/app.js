"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const public_api_1 = require("../routes/public-api");
const error_middleware_1 = require("../middlewares/error-middleware");
const api_1 = require("../routes/api");
const app = (0, express_1.default)();
app.use(express_1.default.json());
app.use(public_api_1.publicRouter);
app.use(api_1.apiRouter);
app.use(error_middleware_1.errorMiddleware);
exports.default = app;
