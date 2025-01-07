package com.visprog.starchive.repositories

import com.visprog.starchive.models.ArticleModel
import com.visprog.starchive.models.GeneralResponseModel
import com.visprog.starchive.services.ArticleService
import retrofit2.Call

interface ArticleRepository {
    fun createArticle(article: ArticleModel): Call<GeneralResponseModel>
    fun getAllArticles(): Call<List<ArticleModel>>
    fun getArticleById(articleId: Int): Call<ArticleModel>
    fun getArticlesByGameId(gameId: Int): Call<List<ArticleModel>>
    fun updateArticle(articleId: Int, article: ArticleModel): Call<GeneralResponseModel>
}

class NetworkArticleRepository(
    private val articleService: ArticleService
): ArticleRepository {

    override fun createArticle(article: ArticleModel): Call<GeneralResponseModel> {
        return articleService.createArticle(article)
    }

    override fun getAllArticles(): Call<List<ArticleModel>> {
        return articleService.getAllArticles()
    }

    override fun getArticleById(articleId: Int): Call<ArticleModel> {
        return articleService.getArticleById(articleId)
    }

    override fun getArticlesByGameId(gameId: Int): Call<List<ArticleModel>> {
        return articleService.getArticlesByGameId(gameId)
    }

    override fun updateArticle(articleId: Int, article: ArticleModel): Call<GeneralResponseModel> {
        return articleService.updateArticle(articleId, article)
    }
}