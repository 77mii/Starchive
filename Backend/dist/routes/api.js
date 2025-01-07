"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.apiRouter = void 0;
const express_1 = __importDefault(require("express"));
const auth_middleware_1 = require("../middlewares/auth-middleware");
const user_controller_1 = require("../controllers/user-controller");
const plan_controller_1 = require("../controllers/plan-controller");
const game_controller_1 = require("../controllers/game-controller");
const budget_controller_1 = require("../controllers/budget-controller");
const expense_controller_1 = require("../controllers/expense-controller");
const banner_controller_1 = require("../controllers/banner-controller");
const pity_controller_1 = require("../controllers/pity-controller");
const user_game_controller_1 = require("../controllers/user-game-controller");
const item_controller_1 = require("../controllers/item-controller");
const analytics_controller_1 = require("../controllers/analytics-controller");
const article_controller_1 = require("../controllers/article-controller");
exports.apiRouter = express_1.default.Router();
exports.apiRouter.use(auth_middleware_1.authMiddleware);
// user
exports.apiRouter.post("/api/logout", user_controller_1.UserController.logout);
// plan
exports.apiRouter.post("/api/plans", plan_controller_1.PlanController.create);
exports.apiRouter.get("/api/plans", plan_controller_1.PlanController.getByUserId);
exports.apiRouter.delete("/api/plans/:planId(\\d+)", plan_controller_1.PlanController.delete);
// game
exports.apiRouter.post("/api/games", game_controller_1.GameController.create);
exports.apiRouter.get("/api/games", game_controller_1.GameController.getAll);
exports.apiRouter.get("/api/games/:gameId(\\d+)", game_controller_1.GameController.getById);
exports.apiRouter.put("/api/games/:gameId(\\d+)", game_controller_1.GameController.update);
// budget
exports.apiRouter.post("/api/budgets", budget_controller_1.BudgetController.create);
exports.apiRouter.get("/api/budgets", budget_controller_1.BudgetController.getByUserId);
exports.apiRouter.get("/api/budgets/:gameId(\\d+)", budget_controller_1.BudgetController.getByUserIdAndGameId);
exports.apiRouter.put("/api/budgets/:budgetId(\\d+)", budget_controller_1.BudgetController.update);
// expense
exports.apiRouter.post("/api/expenses", expense_controller_1.ExpenseController.create);
exports.apiRouter.get("/api/expenses", expense_controller_1.ExpenseController.getByUserId);
exports.apiRouter.get("/api/expenses/:gameId(\\d+)", expense_controller_1.ExpenseController.getByGameId);
// banner
exports.apiRouter.post("/api/banners", banner_controller_1.BannerController.create);
exports.apiRouter.get("/api/banners/:gameId(\\d+)", banner_controller_1.BannerController.getByGameId);
exports.apiRouter.put("/api/banners/:bannerId(\\d+)", banner_controller_1.BannerController.update);
exports.apiRouter.get("/api/banners/active", banner_controller_1.BannerController.getActive);
// pity
exports.apiRouter.post("/api/pity", pity_controller_1.PityController.create);
exports.apiRouter.get("/api/pity/:bannerId(\\d+)", pity_controller_1.PityController.getByBannerId);
exports.apiRouter.put("/api/pity/:pityId(\\d+)", pity_controller_1.PityController.update);
// user-game
exports.apiRouter.post("/api/user-games", user_game_controller_1.UserGameController.add);
exports.apiRouter.get("/api/user-games", user_game_controller_1.UserGameController.getByUserId);
exports.apiRouter.delete("/api/user-games/:gameId(\\d+)", user_game_controller_1.UserGameController.remove);
// item
exports.apiRouter.post("/api/items", item_controller_1.ItemController.create);
exports.apiRouter.get("/api/items/:gameId(\\d+)", item_controller_1.ItemController.getByGameId);
exports.apiRouter.get("/api/banner-items/:bannerId(\\d+)", item_controller_1.ItemController.getByBannerId);
// analytics
exports.apiRouter.get("/api/analytics/spending/:gameId(\\d+)", analytics_controller_1.AnalyticsController.getSpendingAnalysis);
exports.apiRouter.get("/api/analytics/pity-history/:bannerId(\\d+)", analytics_controller_1.AnalyticsController.getPityHistory);
exports.apiRouter.get("/api/analytics/budget-usage/:gameId(\\d+)", analytics_controller_1.AnalyticsController.getBudgetUsage);
// article
exports.apiRouter.post("/api/articles", article_controller_1.ArticleController.create);
exports.apiRouter.get("/api/articles", article_controller_1.ArticleController.getAll);
exports.apiRouter.get("/api/articles/:articleId(\\d+)", article_controller_1.ArticleController.getById);
exports.apiRouter.get("/api/articles/game/:gameId(\\d+)", article_controller_1.ArticleController.getByGameId);
exports.apiRouter.put("/api/articles/:articleId(\\d+)", article_controller_1.ArticleController.update);
