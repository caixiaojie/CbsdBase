package com.yjhs.cbsdbase.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.yjhs.cbsd.base.BaseVMActivity
import com.yjhs.cbsd.utils.Preference
import com.yjhs.cbsd.utils.Tools
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.model.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import pub.devrel.easypermissions.EasyPermissions
import android.widget.Toast
import com.yjhs.cbsd.http.ApiException
import pub.devrel.easypermissions.AppSettingsDialog



/**
 * author: Administrator
 * date: 2019-11-8
 * desc:
 */
class LoginActivity : BaseVMActivity(), View.OnClickListener {
    companion object {
        private const val REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CAMERA = 99
        private const val REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_GALLERY_NORMAL = 100
        private const val REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_GALLERY_Dracula = 101
    }
    override fun initData() {
    }

    override fun initView() {
        val perms = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA)
        checkPermission(perms,REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CAMERA)
    }

    override fun start() {
        if (!session_id.isNullOrEmpty()){
            startActivity<MainActivity>()
            finish()
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.txt_login ->{
                showLoading("登录中")
                mViewModel.login(Tools.safeString(et_account),Tools.safeString(et_pwd))
            }
            R.id.txt_forgot_pwd ->{
                startActivity<SimpleActivity>()
            }
        }
    }

    private val mViewModel by lazy { createViewModel<LoginViewModel>() }
    private var session_id by Preference(Preference.session_id, "")
    private var strImgRootPath by Preference(Preference.strImgRootPath, "")
    private var strUserType by Preference(Preference.strUserType, "")
    private var phoneNum= ""

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun init(savedInstanceState: Bundle?) {
        mViewModel.data.observe(this, Observer {
            hideLoading()
            session_id = it.session_id
            strImgRootPath = it.strImgRootPath
            val userinfo = it.userinfo
            strUserType = userinfo.strUserType
            if (userinfo.iState == 0){
                startActivity<MainActivity>()
                finish()
            }
        })

        txt_login.setOnClickListener(this)
        txt_forgot_pwd.setOnClickListener(this)
    }

    override fun onError(throwable: ApiException) {
        hideLoading()
    }




    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            // 当用户从应用设置界面返回的时候，可以做一些事情，比如弹出一个土司。
            Toast.makeText(this, "从设置页面返回", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun callPhone(phoneNum: String) {
        val intent: Intent = Intent(Intent.ACTION_DIAL)
        val data: Uri = Uri.parse("tel:$phoneNum")
        intent.data = data
        startActivity(intent)
    }

    private fun checkPermission(perms: Array<String>,requestCode: Int){
        EasyPermissions.requestPermissions(this, "应用需要以下权限，请允许", requestCode, *perms)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        when(requestCode){
            REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CAMERA ->{
                if (perms.isNotEmpty()) {
                    if (perms.contains(Manifest.permission.READ_EXTERNAL_STORAGE)&&
                        perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)&&
                        perms.contains(Manifest.permission.CAMERA)){
//                        openCamera()
                    }
                }
            }
            REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_GALLERY_NORMAL ->{
                if (perms.isNotEmpty()) {
                    if (perms.contains(Manifest.permission.READ_EXTERNAL_STORAGE)&&
                        perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
//                        openGalleryAsNormal()
                    }
                }
            }
            REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_GALLERY_Dracula ->{
                if (perms.isNotEmpty()) {
                    if (perms.contains(Manifest.permission.READ_EXTERNAL_STORAGE)&&
                        perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
//                        openGalleryAsDracula()
                    }
                }
            }
        }
    }

}