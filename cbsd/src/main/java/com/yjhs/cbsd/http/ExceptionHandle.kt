package com.yjhs.cbsd.http

import android.text.TextUtils
import com.yjhs.cbsd.mvp.BaseError
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException


/**
 * @author Lai
 * @time 2018/10/19 15:09
 * @Description
 */
class ExceptionHandle {
    companion object {
        private val UNAUTHORIZED = 401
        private val FORBIDDEN = 403
        private val NOT_FOUND = 404
        private val REQUEST_TIMEOUT = 408
        private val INTERNAL_SERVER_ERROR = 500
        private val BAD_GATEWAY = 502
        private val SERVICE_UNAVAILABLE = 503
        private val GATEWAY_TIMEOUT = 504

        fun handleException(e: Throwable): Throwable? {
            val ex: ApiException
            when (e) {
                is HttpException -> {
                    ex = ApiException(e, e.code())
                    when (e.code()) {
                        UNAUTHORIZED, FORBIDDEN, NOT_FOUND, REQUEST_TIMEOUT, GATEWAY_TIMEOUT, INTERNAL_SERVER_ERROR, SERVICE_UNAVAILABLE ->
//                            return Throwable("网络异常，请检查网络设置")
                            return BaseError("网络异常，请检查网络设置",e.code())
                        BAD_GATEWAY -> {
                            return BaseError("服务器异常",e.code())
                        }
                    }
                }
                is ConnectException -> {
                    return BaseError("网络异常，请检查网络设置",REQUEST_TIMEOUT)
                }
                is java.net.SocketTimeoutException -> {
                    return BaseError("网络超时，请检查网络设置",REQUEST_TIMEOUT)
                }
                is UnknownHostException -> {
                    return BaseError("网络异常，请检查网络设置",REQUEST_TIMEOUT)
                }
                is IOException -> {
                    return BaseError("网络异常，请检查网络设置",REQUEST_TIMEOUT)
                }
                is SocketException -> {
                    return BaseError("网络异常，请检查网络设置",REQUEST_TIMEOUT)
                }
            }

            if(!TextUtils.isEmpty(e.message) && e.message!!.contains("Exception")) {
                return BaseError("网络异常，请检查网络设置",REQUEST_TIMEOUT)
            }

            return if(e.message == null) null else BaseError(e.message,REQUEST_TIMEOUT)
        }
    }
}