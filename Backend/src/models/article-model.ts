import { Articles } from "@prisma/client"

export interface CreateArticleRequest {
    title: string
    text: string
    image_url?: string
    game_id: number
}

export interface ArticleResponse {
    article_id: number
    title: string
    text: string
    image_url: string | null
    game_id: number
    createdDate: Date
}

export function toArticleResponse(article: Articles): ArticleResponse {
    return {
        article_id: article.article_id,
        title: article.title,
        text: article.text,
        image_url: article.image_url,
        game_id: article.game_id,
        createdDate: article.createdDate
    }
}