package com.yjhs.cbsdbase.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.yjhs.cbsd.base.BaseVMActivity
import com.yjhs.cbsd.utils.Preference
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.model.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import android.text.TextUtils
import com.gyf.immersionbar.ImmersionBar
import com.yjhs.cbsd.utils.SoftHideKeyBoardUtil
import kotlinx.android.synthetic.main.common_preview_title.*


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
        if (!sessionId.isNullOrEmpty()){
            startActivity<MainActivity>()
            finish()
        }else {
            openSoftKey(et_account)
        }
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
        var cancel = false
        var focusView: View? = null

        //检查手机号的合法性
        if (TextUtils.isEmpty(et_account.text.toString())) {
            et_account.error = "手机号不能为空！"
            focusView = et_account
            cancel = true
        } else if (et_account.text.toString().trim().length != 11) {
            //如果手机号长度不足11位
            et_account.error = "手机号不足11位！"
            focusView = et_account
            cancel = true
        }

        //检查密码的合法性
        if (!cancel) {
            if (TextUtils.isEmpty(et_pwd.text.toString())) {
                et_pwd.error = "密码不能为空！"
                focusView = et_pwd
                cancel = true
            } else if (et_pwd.text.toString().length < 6 || et_pwd.text.toString().length > 12) {
                et_pwd.error = "密码长度不能小于6位，也不能大于12位！"
                focusView = et_pwd
                cancel = true
            }
        }


        //检查图形验证码的合法性
        if (!cancel) {
            if (TextUtils.isEmpty(tuxing_code.text.toString())) {
                tuxing_code.error = "图形验证码不能为空！"
                focusView = tuxing_code
                cancel = true
            } else if (tuxing_code.text.toString().length !== 4) {
                tuxing_code.error = "图形验证码只允许是4位！"
                focusView = tuxing_code
                cancel = true
            }
        }

        //检查图形验证码的合法性
        if (!cancel) {
            if (TextUtils.isEmpty(sms_code.text.toString())) {
                sms_code.error = "短信验证码不能为空！"
                focusView = sms_code
                cancel = true
            } else if (sms_code.text.toString().length !== 4) {
                sms_code.error = "短信验证码只允许是4位！"
                focusView = sms_code
                cancel = true
            }
        }

        if (cancel) {
            focusView!!.requestFocus()
        } else {
            hideSoftKey()
            mViewModel.login(et_account.text.toString(),et_pwd.text.toString())
        }

    }
    private val onClickListener = View.OnClickListener {
        when(it.id){
            R.id.txt_login -> login()
        }
    }
}