package com.yjhs.cbsd.http

/**
 * author: Administrator
 * date: 2019-11-13
 * desc:
 */
class ApiException(private val msg: String?, val code: Int) : Throwable(msg) {

//    var displayMessage: String? = null
//        set(msg) {
//            field = "$msg(code:$code)"
//        }
//
//    companion object {
//
//        val UNKNOWN = 1000
//        val PARSE_ERROR = 1001
//    }

    override fun toString(): String {
        return "$msg(code:$code)"
    }
}
