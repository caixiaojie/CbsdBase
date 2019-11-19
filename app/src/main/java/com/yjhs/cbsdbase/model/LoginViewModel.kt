package com.yjhs.cbsdbase.model

import androidx.lifecycle.MutableLiveData
import com.yjhs.cbsd.base.BaseViewModel
import com.yjhs.cbsdbase.bean.LoginResp
import com.yjhs.cbsdbase.RetrofitClient

/**
 *
 * @author  Lai
 *
 * @time 2019/10/14 16:02
 * @describe describe
 *
 */
class LoginViewModel : BaseViewModel() {

    val data = MutableLiveData<LoginResp>()




    fun login(user: String,pwd: String) {
        request({
            RetrofitClient.service.login(user,pwd)
        }, {
            data.value = it
        })
    }





}