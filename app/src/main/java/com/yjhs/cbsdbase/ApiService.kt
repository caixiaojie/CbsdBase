package com.yjhs.cbsdbase


import com.yjhs.cbsd.enty.ResultVO
import retrofit2.http.*
import java.util.HashMap

/**
 *
 * @author  Lai
 *
 * @time 2019/9/20 15:56
 * @describe describe
 *
 */
interface ApiService {
    companion object {
        const val BASE_URL = "http://api.cheyuanxiang.com:8080/share_ad_api_test/"
        const val ROOT_PATH = "http://api.cheyuanxiang.com:88/picpath/share_ad/"
    }

    //登录
    @POST("app/phone/password/login")
    suspend fun login(@Query("strUserPhone") strUserPhone: String, @Query("strPassword") strPassword: String): ResultVO<LoginResp>

    //公共查询  全部文章
    @FormUrlEncoded
    @POST("app/manageinfor/selectByPageHome")
    suspend fun commonQuery(@FieldMap req: HashMap<String, Any>): ResultVO<CommonListResp>


}