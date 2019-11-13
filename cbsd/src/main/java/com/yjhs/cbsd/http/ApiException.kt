package com.yjhs.cbsd.http

/**
 * author: Administrator
 * date: 2019-11-13
 * desc:
 */
class ApiException(throwable: Throwable, private val code: Int) : Exception(throwable) {
    var displayMessage: String? = null
        set(msg) {
            field = "$msg(code:$code)"
        }

    companion object {

        val UNKNOWN = 1000
        val PARSE_ERROR = 1001
    }
}
