package com.lai.comicmtc_v2.http


import com.yjhs.cbsd.enty.ResultTVO
import com.yjhs.cbsd.enty.ResultVO
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 * @author  Lai
 *
 * @time 2019/9/20 15:56
 * @describe describe
 *
 */
interface ComicService {
    companion object {
        const val BASE_URL = "http://app.u17.com/v3/appV3_3/android/phone/"
    }

    @GET("comic/boutiqueListNew")
    suspend fun boutiqueList(@Query("sexType") sexType: Int = 1): ResultVO<Any>

    @GET("comic/detail_static_new")
    suspend fun comicDetail(@Query("comicid") comicId: String): ResultTVO<Any>



}