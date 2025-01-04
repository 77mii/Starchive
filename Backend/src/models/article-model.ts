import { Articles } from "@prisma/client"

export interface CreateArticleRequest {
    title: string
    text: string
    image_url?: string
}

export interface ArticleResponse {
    article_id: number
    title: string
    text: string
    image_url: string | null
}

export function toArticleResponse(article: Articles): ArticleResponse {
    return {
        article_id: article.article_id,
        title: article.title,
        text: article.text,
        image_url: article.image_url
    }
}