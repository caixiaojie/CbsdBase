package com.yjhs.cbsd.mvp

import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yjhs.cbsd.http.ApiException

/**
 *
 * @author  Lai
 *
 * @time 2019/9/22 16:58
 * @describe describe
 *
 */
abstract class BaseVMFragment : BaseFragment() {

    /**
     * 初始化默认的viewModel
     */
    inline fun <reified VM : BaseViewModel> createViewModel(): VM {
        val mViewModel = ViewModelProvider(this)[VM::class.java]
        mViewModel.mException.observe(this, Observer {
            hideLoading()
            onError(it)
            toastMessage(it)
        })
        return mViewModel
    }

    open fun toastMessage(throwable: ApiException) {
        if (!TextUtils.isEmpty(throwable.message)) {
            toastMsg(throwable.toString())
        }
    }

    open fun onError(throwable: ApiException) {

    }
}