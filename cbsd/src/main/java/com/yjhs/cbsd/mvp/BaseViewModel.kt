package com.yjhs.cbsd.mvp

import android.content.Intent
import android.text.TextUtils
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonElement
import com.yjhs.cbsd.App
import com.yjhs.cbsd.enty.CBSDResultEntity
import com.yjhs.cbsd.enty.ResultVO
import com.yjhs.cbsd.http.ExceptionHandle
import com.yjhs.cbsd.utils.ActivityUtil
import kotlinx.coroutines.*

/**
 *
 * @author  Lai
 *
 * @time 2019/9/19 14:41
 * @describe describe
 *
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {

    //异常数据
    val mException: MutableLiveData<Throwable> = MutableLiveData()


    /**
     * 用来包裹协程的错误信息
     */
    private suspend fun <T> tryCatch(tryBlock: suspend CoroutineScope.() -> ResultVO<T>): ResultVO<T> {
        return coroutineScope {
            try {
                return@coroutineScope tryBlock()
            } catch (e: Throwable) {
                e.printStackTrace()
                val message = ExceptionHandle.handleException(e)?.message
                val response = ResultVO<T>()
                if (!TextUtils.isEmpty(message)) {
                    response.msg = message
                } else {
                    response.msg = "未知异常"
                }
                return@coroutineScope response
            }
        }
    }


     private fun <T> runOnIo(
        request: suspend CoroutineScope.() -> ResultVO<T>,
        success: ((info: T) -> Unit),
        error: ((info: ResultVO<T>) -> Unit)
    ) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                tryCatch(request)
            }
            if (response.code == 1) {
                success.invoke(response.data)
            } else {
                if (response.code == -1){
                    //登录已过期  或 用户未登录
                    mException.value = Throwable("登录已过期,请重新登录")
                    val currentActivity = ActivityUtil.getCurrentActivity()
                    if (currentActivity != null) {
                        val intent = Intent(currentActivity, Class.forName("com.yjhs.cbsdbase.LoginActivity"))
                        currentActivity.startActivity(intent)
                    }
                }else{
                    if (response.data == null && !TextUtils.isEmpty(response.msg)) {
                        mException.value = Throwable(response.msg)
                    } else {
                        mException.value = Throwable("未知异常")
                    }
                }
                error.invoke(response)
            }
        }
    }


    fun <T> request(
        request: suspend CoroutineScope.() -> ResultVO<T>,
        success: ((info: T) -> Unit)
    ) {
        runOnIo(request, success, {})
    }
}


