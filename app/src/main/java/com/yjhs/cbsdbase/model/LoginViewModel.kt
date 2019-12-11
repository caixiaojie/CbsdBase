package com.yjhs.cbsdbase.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yjhs.cbsd.base.BaseViewModel
import com.yjhs.cbsdbase.bean.LoginResp
import com.yjhs.cbsdbase.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    private val _uiState = MutableLiveData<LoginUiModel>()
    val uiState: LiveData<LoginUiModel>
        get() = _uiState

    private fun inputValid(userName: String, passWord: String) = userName.isBlank() || passWord.isBlank()

    fun login(user: String,pwd: String) {
        viewModelScope.launch(Dispatchers.Default){
            if (inputValid(user,pwd)) {
                withContext(Dispatchers.Main){emitUiState(showProgress = false, showErr = true)}
                return@launch
            }
            withContext(Dispatchers.Main){emitUiState(true)}
            request({
                RetrofitClient.service.login(user, pwd)
            }, {
                data.value = it
                emitUiState(showProgress = false, showErr = false, showSuccess = it)
            })
        }
    }

    /**
     * 发射UI状态
     */
    private fun emitUiState(
        showProgress: Boolean = false,
        showErr: Boolean = false,
        showSuccess: LoginResp? = null
    ) {
        val uiModel = LoginUiModel(showProgress, showErr,showSuccess)
        _uiState.value = uiModel
    }

    data class LoginUiModel(val showProgress: Boolean,
                            val showErr: Boolean,
                            val showSuccess: LoginResp?)



}