import express from "express"
import { authMiddleware } from "../middlewares/auth-middleware"
import { UserController } from "../controllers/user-controller"
import { PlanController } from "../controllers/plan-controller"

export const apiRouter = express.Router()
apiRouter.use(authMiddleware)

apiRouter.post("/api/logout", UserController.logout)
apiRouter.post("/api/plans", PlanController.create)
apiRouter.get("/api/plans", PlanController.getByUserId)

// apiRouter.get("/api/todo-list", TodoController.getAllTodos)
// apiRouter.post("/api/todo-list", TodoController.createTodo)
// \\d+ means regex to only allow digit as url param
// apiRouter.get("/api/todo-list/:todoId(\\d+)", TodoController.getTodo)
// apiRouter.put("/api/todo-list/:todoId(\\d+)", TodoController.updateTodo)
// apiRouter.delete("/api/todo-list/:todoId(\\d+)", TodoController.deleteTodo)