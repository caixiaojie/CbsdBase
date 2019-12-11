package com.yjhs.cbsdbase.activity

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.yjhs.cbsd.base.BaseVMActivity
import com.yjhs.cbsd.utils.Preference
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.model.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.startActivity

/**
 * Created by 二哈 on 2019/12/11.
 */
class LoginActivityCopy : BaseVMActivity() {
    private val mViewModel by lazy { createViewModel<LoginViewModel>() }
    private var sessionId by Preference(Preference.session_id, "")
    private var strImgRootPath by Preference(Preference.strImgRootPath, "")
    private var strUserType by Preference(Preference.strUserType, "")
    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun initData() {

    }

    override fun initView() {
    }

    override fun start() {
        mViewModel.apply {
            this.uiState.observe(this@LoginActivityCopy, Observer {
                //显示加载框
                if (it.showProgress){
                    showLoading("登录中")
                }
                if (it.showErr){
                    toastMsg("用户名或密码为空")
                }
                it.showSuccess?.let { resp ->
                    hideLoading()
                    sessionId = resp.session_id
                    strImgRootPath = resp.strImgRootPath
                    val userInfo = resp.userinfo
                    strUserType = userInfo.strUserType
                    if (userInfo.iState == 0){
                        startActivity<MainActivity>()
                        finish()
                    }
                }
            })
        }
    }

    override fun init(savedInstanceState: Bundle?) {
        txt_login.setOnClickListener(onClickListener)
    }
    private fun login(){
        mViewModel.login(et_account.text.toString(),et_pwd.text.toString())
    }
    private val onClickListener = View.OnClickListener {
        when(it.id){
            R.id.txt_login -> login()
        }
    }
}