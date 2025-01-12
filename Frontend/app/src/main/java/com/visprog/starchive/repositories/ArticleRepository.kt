package com.visprog.starchive.repositories

import com.visprog.starchive.models.ArticleModel
import com.visprog.starchive.models.GeneralResponseModel
import com.visprog.starchive.models.GetAllArticleResponse
import com.visprog.starchive.models.GetArticleResponse
import com.visprog.starchive.services.ArticleService
import retrofit2.Call

interface ArticleRepository {
    fun createArticle(article: ArticleModel): Call<GeneralResponseModel>
    fun getAllArticles(): Call<List<GetArticleResponse>>
    fun getArticleById(token: String, articleId: Int): Call<GetArticleResponse>
    fun getArticlesByGameId(token: String, gameId: Int): Call<GetAllArticleResponse>
    fun updateArticle(token: String, articleId: Int, article: ArticleModel): Call<GeneralResponseModel>
}

class NetworkArticleRepository(
    private val articleService: ArticleService
): ArticleRepository {

    override fun createArticle(article: ArticleModel): Call<GeneralResponseModel> {
        return articleService.createArticle(article)
    }

    override fun getAllArticles(): Call<List<GetArticleResponse>> {
        return articleService.getAllArticles()
    }

    override fun getArticleById(token: String, articleId: Int): Call<GetArticleResponse> {
        return articleService.getArticleById(token, articleId)
    }

    override fun getArticlesByGameId(token: String, gameId: Int): Call<GetAllArticleResponse> {
        return articleService.getArticlesByGameId(token, gameId)
    }

    override fun updateArticle(token: String, articleId: Int, article: ArticleModel): Call<GeneralResponseModel> {
        return articleService.updateArticle(token, articleId, article)
    }
}