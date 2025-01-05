"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.toArticleResponse = toArticleResponse;
function toArticleResponse(article) {
    return {
        article_id: article.article_id,
        title: article.title,
        text: article.text,
        image_url: article.image_url,
        game_id: article.game_id,
        createdDate: article.createdDate
    };
}
