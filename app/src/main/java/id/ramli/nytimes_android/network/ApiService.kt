package id.ramli.nytimes_android.network

import id.ramli.nytimes_android.model.ResponseArticle
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by ramliy10 on 17/11/20.
 */
interface ApiService {

    @GET("articlesearch.json")
    fun article(
        @Query("api-key") api_key: String,
        @Query("q") region: String,
        @Query("page") page: Int
    ): Call<ResponseArticle>

    @GET("articlesearch.json")
    fun searchArticle(
        @Query("q") query: String,
        @Query("api-key") api_key: String
    ): Call<ResponseArticle>

}