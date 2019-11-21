package com.legion1900.cleannews.data.impl.retrofit

import com.legion1900.cleannews.BuildConfig
import com.legion1900.cleannews.data.base.data.Response
import com.legion1900.cleannews.data.impl.utils.TimeUtils
import io.reactivex.Observable

import retrofit2.http.GET
import retrofit2.http.QueryMap
import java.util.*
import kotlin.collections.HashMap

interface NewsService {
    @GET("v2/everything")
    fun queryNews(@QueryMap options: Map<String, String>): Observable<Response>

    companion object {
        const val DEF_PAGE_SIZE = 20

        private const val KEY_TOPIC = "q"
        private const val KEY_DATE = "from"
        private const val KEY_SORT = "sortBy"
        private const val KEY_API_KEY = "apiKey"
        private const val VALUE_SORT = "publishedAt"

        private const val TIME_FORMAT = "yyyy-mm-dd"

        fun buildQuery(topic: String, date: Date): Map<String, String> {
            val query = HashMap<String, String>()
            query[KEY_TOPIC] = topic
            query[KEY_DATE] = TimeUtils.dateToFormatStr(date, TIME_FORMAT)
            query[KEY_SORT] = VALUE_SORT
            query[KEY_API_KEY] = BuildConfig.apiKey
            return query
        }
    }
}