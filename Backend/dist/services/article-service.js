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
exports.ArticleService = void 0;
const database_1 = require("../application/database");
const article_model_1 = require("../models/article-model");
const article_validation_1 = require("../validations/article-validation");
const validation_1 = require("../validations/validation");
class ArticleService {
    static create(request) {
        return __awaiter(this, void 0, void 0, function* () {
            const createRequest = validation_1.Validation.validate(article_validation_1.ArticleValidation.CREATE, request);
            const article = yield database_1.prismaClient.articles.create({
                data: createRequest
            });
            return (0, article_model_1.toArticleResponse)(article);
        });
    }
    static getAll() {
        return __awaiter(this, void 0, void 0, function* () {
            const articles = yield database_1.prismaClient.articles.findMany();
            return articles.map(article => (0, article_model_1.toArticleResponse)(article));
        });
    }
    static getById(articleId) {
        return __awaiter(this, void 0, void 0, function* () {
            const article = yield database_1.prismaClient.articles.findUnique({
                where: {
                    article_id: articleId
                }
            });
            if (!article) {
                throw new Error("Article not found");
            }
            return (0, article_model_1.toArticleResponse)(article);
        });
    }
    static getByGameId(gameId) {
        return __awaiter(this, void 0, void 0, function* () {
            const articles = yield database_1.prismaClient.articles.findMany({
                where: {
                    game_id: gameId
                }
            });
            return articles.map(article => (0, article_model_1.toArticleResponse)(article));
        });
    }
    static update(articleId, request) {
        return __awaiter(this, void 0, void 0, function* () {
            const updateRequest = validation_1.Validation.validate(article_validation_1.ArticleValidation.UPDATE, request);
            const existingArticle = yield database_1.prismaClient.articles.findUnique({
                where: {
                    article_id: articleId
                }
            });
            if (!existingArticle) {
                throw new Error("Article not found");
            }
            const article = yield database_1.prismaClient.articles.update({
                where: {
                    article_id: articleId
                },
                data: updateRequest
            });
            return (0, article_model_1.toArticleResponse)(article);
        });
    }
}
exports.ArticleService = ArticleService;
