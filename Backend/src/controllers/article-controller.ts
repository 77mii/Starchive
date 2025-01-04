import { NextFunction, Request, Response } from "express"
import { CreateArticleRequest } from "../models/article-model"
import { ArticleService } from "../services/article-service"

export class ArticleController {
    static async create(req: Request, res: Response, next: NextFunction) {
        try {
            const request: CreateArticleRequest = req.body as CreateArticleRequest
            const response = await ArticleService.create(request)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async getAll(req: Request, res: Response, next: NextFunction) {
        try {
            const response = await ArticleService.getAll()
            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async getById(req: Request, res: Response, next: NextFunction) {
        try {
            const articleId = parseInt(req.params.articleId)
            const response = await ArticleService.getById(articleId)
            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }

    static async update(req: Request, res: Response, next: NextFunction) {
        try {
            const articleId = parseInt(req.params.articleId)
            const request = req.body as Partial<CreateArticleRequest>
            const response = await ArticleService.update(articleId, request)

            res.status(200).json({
                data: response
            })
        } catch (error) {
            next(error)
        }
    }
}