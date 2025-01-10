import express from "express"
import { authMiddleware } from "../middlewares/auth-middleware"
import { UserController } from "../controllers/user-controller"
import { PlanController } from "../controllers/plan-controller"
import { GameController } from "../controllers/game-controller"
import { BudgetController } from "../controllers/budget-controller"
import { ExpenseController } from "../controllers/expense-controller"
import { BannerController } from "../controllers/banner-controller"
import { PityController } from "../controllers/pity-controller"
import { UserGameController } from "../controllers/user-game-controller"
import { ItemController } from "../controllers/item-controller"
import { AnalyticsController } from "../controllers/analytics-controller"
import { ArticleController } from "../controllers/article-controller"
import { BannerItemController } from "../controllers/banner-item-controller";
export const apiRouter = express.Router()
apiRouter.use(authMiddleware)
// user
apiRouter.post("/api/logout", UserController.logout)
// plan
apiRouter.post("/api/plans", PlanController.create)
apiRouter.get("/api/plans", PlanController.getByUserId)
apiRouter.delete("/api/plans/:planId(\\d+)", PlanController.delete)
// game
apiRouter.post("/api/games", GameController.create)
apiRouter.get("/api/games", GameController.getAll)
apiRouter.get("/api/games/:gameId(\\d+)", GameController.getById)
apiRouter.put("/api/games/:gameId(\\d+)", GameController.update)
// budget
apiRouter.post("/api/budgets", BudgetController.create)
apiRouter.get("/api/budgets", BudgetController.getByUserId)
apiRouter.get("/api/budgets/:gameId(\\d+)", BudgetController.getByUserIdAndGameId)
apiRouter.put("/api/budgets/:budgetId(\\d+)", BudgetController.update)
// expense
apiRouter.post("/api/expenses", ExpenseController.create)
apiRouter.get("/api/expenses", ExpenseController.getByUserId)
apiRouter.get("/api/expenses/:gameId(\\d+)", ExpenseController.getByGameId)
// banner
apiRouter.post("/api/banners", BannerController.create)
apiRouter.get("/api/banners/:gameId(\\d+)", BannerController.getByGameId)
apiRouter.put("/api/banners/:bannerId(\\d+)", BannerController.update)
apiRouter.get("/api/banners/active", BannerController.getActive)
//banner-item
apiRouter.post("/api/banner-items", BannerItemController.create);
apiRouter.get("/api/banner-items/:bannerId(\\d+)", BannerItemController.getByBannerId);
apiRouter.get("/api/banner-items/:bannerId(\\d+)/:itemId(\\d+)", BannerItemController.get);
apiRouter.put("/api/banner-items/:bannerId(\\d+)/:itemId(\\d+)", BannerItemController.update);
apiRouter.delete("/api/banner-items/:bannerId(\\d+)/:itemId(\\d+)", BannerItemController.delete);
// pity
apiRouter.post("/api/pity", PityController.create)
apiRouter.get("/api/pity/:bannerId(\\d+)", PityController.getByBannerId)
apiRouter.put("/api/pity/:pityId(\\d+)", PityController.update)
// user-game
apiRouter.post("/api/user-games", UserGameController.add)
apiRouter.get("/api/user-games", UserGameController.getByUserId)
apiRouter.delete("/api/user-games/:gameId(\\d+)", UserGameController.remove)
// item
apiRouter.post("/api/items", ItemController.create)
apiRouter.get("/api/items/:gameId(\\d+)", ItemController.getByGameId)
apiRouter.get("/api/banner-items/:bannerId(\\d+)", ItemController.getByBannerId)
// analytics
apiRouter.get("/api/analytics/spending/:gameId(\\d+)", AnalyticsController.getSpendingAnalysis)
apiRouter.get("/api/analytics/pity-history/:bannerId(\\d+)", AnalyticsController.getPityHistory)
apiRouter.get("/api/analytics/budget-usage/:gameId(\\d+)", AnalyticsController.getBudgetUsage)
// article
apiRouter.post("/api/articles", ArticleController.create)
apiRouter.get("/api/articles", ArticleController.getAll)
apiRouter.get("/api/articles/:articleId(\\d+)", ArticleController.getById)
apiRouter.get("/api/articles/game/:gameId(\\d+)", ArticleController.getByGameId)
apiRouter.put("/api/articles/:articleId(\\d+)", ArticleController.update)