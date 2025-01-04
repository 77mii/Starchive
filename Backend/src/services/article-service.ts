import { prismaClient } from "../application/database"
import { CreateArticleRequest, ArticleResponse, toArticleResponse } from "../models/article-model"
import { ArticleValidation } from "../validations/article-validation"
import { Validation } from "../validations/validation"

export class ArticleService {
    static async create(request: CreateArticleRequest): Promise<ArticleResponse> {
        const createRequest = Validation.validate(ArticleValidation.CREATE, request)

        const article = await prismaClient.articles.create({
            data: createRequest
        })

        return toArticleResponse(article)
    }

    static async getAll(): Promise<ArticleResponse[]> {
        const articles = await prismaClient.articles.findMany()
        return articles.map(article => toArticleResponse(article))
    }

    static async getById(articleId: number): Promise<ArticleResponse> {
        const article = await prismaClient.articles.findUnique({
            where: {
                article_id: articleId
            }
        })

        if (!article) {
            throw new Error("Article not found")
        }

        return toArticleResponse(article)
    }

    static async update(articleId: number, request: Partial<CreateArticleRequest>): Promise<ArticleResponse> {
        const updateRequest = Validation.validate(ArticleValidation.UPDATE, request)

        const existingArticle = await prismaClient.articles.findUnique({
            where: {
                article_id: articleId
            }
        })

        if (!existingArticle) {
            throw new Error("Article not found")
        }

        const article = await prismaClient.articles.update({
            where: {
                article_id: articleId
            },
            data: updateRequest
        })

        return toArticleResponse(article)
    }
}