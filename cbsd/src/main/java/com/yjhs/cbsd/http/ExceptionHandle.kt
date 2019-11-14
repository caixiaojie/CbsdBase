package com.yjhs.cbsd.http

import android.text.TextUtils
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.UnknownHostException


/**
 * @author Lai
 * @time 2018/10/19 15:09
 * @Description
 * 400 坏请求（Bad Request）

　　　　401 无授权（Unauthorized）

　　　　402 所需付款 （Payment Required）

　　　　403 禁止（Forbidden）

　　　　404 未找到 （Not Found）

　　　　405 方法不允许 （Method Not Allowed）

　　　　406 非可接受的（Not Acceptable）

　　　　407 需要代理身份验证（Proxy Authentication Required）

　　　　408 请求超时 (Request Timeout)

　　　　409 冲突(Conflict)

　　　　410 好了（Gone）？？？

　　　　411 所需长度（Length Required ）

　　　　412  先决条件失败（Precondition Failed）

　　　　413 请求实体太大（Request Entity Too Large）

　　　　414 请求URI太长（Request-URI Too Long）

　　　　415 不支持的媒体类型（Unsupported Media Type）

　　　　416 不能满足所请求的范围（Requested Range Not Stisfiable）

　　　　417 期望失败（Expectation Failed）
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
        //500 内部服务器错误（Internal Server Error）
        //
        // 　　　　501 未执行（Not Implemented）
        //
        //　　　　502 坏网关 （Bad Gateway）
        //
        // 　　　　503 服务不可用（Service Unavailable）
        //
        // 　　　　504 网关超时（Gateway Timeout）
        //
        // 　　　　505 不支持的HTTP版本（HTTP Version Not Supported ）

        fun handleException(e: Throwable): ApiException? {
//            var ex: ApiException
            when (e) {
                is HttpException -> {
                    when (e.code()) {
                        UNAUTHORIZED ->{
                            return ApiException("未授权",e.code())
                        }
                        FORBIDDEN ->{
                            return ApiException("禁止访问",e.code())
                        }
                        NOT_FOUND ->
//                            return Throwable("网络异常，请检查网络设置")
                            return ApiException("未找到",e.code())
                        REQUEST_TIMEOUT ->{
                            return ApiException("请求超时",e.code())
                        }
                        SERVICE_UNAVAILABLE ->{
                            return ApiException("服务不可用",e.code())
                        }
                        GATEWAY_TIMEOUT ->{
                            return ApiException("网关超时",e.code())
                        }
                        INTERNAL_SERVER_ERROR ->{
                            return ApiException("服务器内部错误",e.code())
                        }
                        BAD_GATEWAY -> {
//                            return Throwable("服务器异常")
                            return ApiException("服务器异常",e.code())
                        }
                    }
                }
                is ConnectException -> {
//                    return Throwable("网络异常，请检查网络设置")
                    return ApiException("网络异常，请检查网络设置",GATEWAY_TIMEOUT)
                }
                is java.net.SocketTimeoutException -> {
//                    return Throwable("网络超时，请检查网络设置")
                    return ApiException("网络超时，请检查网络设置",GATEWAY_TIMEOUT)
                }
                is UnknownHostException -> {
//                    return Throwable("网络异常，请检查网络设置")
                    return ApiException("未知名称或服务",GATEWAY_TIMEOUT)
                }
                is IOException -> {
//                    return Throwable("网络异常，请检查网络设置")
                    return ApiException("网络异常，请检查网络设置",GATEWAY_TIMEOUT)
                }
                is SocketException -> {
//                    return Throwable("网络异常，请检查网络设置")
                    return ApiException("网络异常，请检查网络设置",GATEWAY_TIMEOUT)
                }
            }

            if(!TextUtils.isEmpty(e.message) && e.message!!.contains("Exception")) {
//                return Throwable("网络异常，请检查网络设置")
                return ApiException("网络异常，请检查网络设置",GATEWAY_TIMEOUT)
            }

            return if(e.message == null) null else return ApiException(e.message,GATEWAY_TIMEOUT)
        }
    }
}