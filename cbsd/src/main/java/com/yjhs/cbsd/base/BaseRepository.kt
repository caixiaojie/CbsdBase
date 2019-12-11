package com.yjhs.cbsd.base

import com.yjhs.cbsd.enty.CommonResult
import com.yjhs.cbsd.enty.ResultVO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import java.io.IOException

/**
 * Created by
 * on 2019/4/10 9:41
 */
open class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> ResultVO<T>): ResultVO<T> {
        return call.invoke()
    }

    suspend fun <T : Any> safeApiCall(call: suspend () -> CommonResult<T>, errorMessage: String): CommonResult<T> {
        return try {
            call()
        } catch (e: Exception) {
            // An exception was thrown when calling the API so we're converting this to an IOException
            CommonResult.Error(IOException(errorMessage, e))
        }
    }

    suspend fun <T : Any> executeResponse(response: ResultVO<T>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
                                          errorBlock: (suspend CoroutineScope.() -> Unit)? = null): CommonResult<T> {
        return coroutineScope {
            if (response.code == -1) {
                errorBlock?.let { it() }
                CommonResult.Error(IOException(response.msg))
            } else {
                successBlock?.let { it() }
                CommonResult.Success(response.data)
            }
        }
    }


}