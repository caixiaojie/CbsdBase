package com.yjhs.cbsdbase.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.yjhs.cbsd.mvp.BaseVMActivity
import com.yjhs.cbsd.utils.Preference
import com.yjhs.cbsd.utils.Tools
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.model.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

/**
 * author: Administrator
 * date: 2019-11-8
 * desc:
 */
class LoginActivity : BaseVMActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.txt_login ->{
                mViewModel.login(Tools.safeString(et_account),Tools.safeString(et_pwd))
            }
        }
    }

    private val mViewModel by lazy { createViewModel<LoginViewModel>() }
    private var session_id by Preference(Preference.session_id, "")
    private var strImgRootPath by Preference(Preference.strImgRootPath, "")
    private var strUserType by Preference(Preference.strUserType, "")

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun init(savedInstanceState: Bundle?) {
        mViewModel.data.observe(this, Observer {
            session_id = it.session_id
            strImgRootPath = it.strImgRootPath
            val userinfo = it.userinfo
            strUserType = userinfo.strUserType
            if (userinfo.iState == 0){
                startActivity<MainActivity>()
            }
        })

        txt_login.setOnClickListener(this)
    }
}