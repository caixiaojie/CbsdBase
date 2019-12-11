package com.yjhs.cbsd.enty

/**
 * Created by luyao
 * on 2019/10/12 11:08
 */
sealed class CommonResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : CommonResult<T>()
    data class Error(val exception: Exception) : CommonResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}