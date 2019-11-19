package com.legion1900.cleannews.data.base.data

data class Response(
    val status: String,
    val code: String,
    val message: String,
    val articles: List<Article>
)