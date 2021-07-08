package syntex_error.simec.technical_test.verseApp.network

import syntex_error.simec.technical_test.verseApp.data.models.VerseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VerseInterface {


    @GET("search")
    suspend fun getAllNews(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("size") pageSize: Int
    ): Response<VerseResponse>



}