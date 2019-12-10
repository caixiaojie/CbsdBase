package com.yjhs.cbsd.base

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.gyf.immersionbar.ImmersionBar
import com.yjhs.cbsd.ui.widget.dialog.BusyView
import com.yjhs.cbsd.widget.MultipleStatusView
import kotlinx.android.synthetic.main.common_preview_title.*
import me.yokeyword.fragmentation.SupportActivity
import pub.devrel.easypermissions.EasyPermissions
import android.R
import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import org.jetbrains.anko.toast
import pub.devrel.easypermissions.AppSettingsDialog


/**
 *
 * @author  Lai
 *
 * @time 2019/9/18 13:58
 * @describe describe
 *
 */
abstract class BaseActivity : SupportActivity(), IBaseView, EasyPermissions.PermissionCallbacks {

    private var mLoadingDialog: BusyView? = null
    private var mMultipleStatusView: MultipleStatusView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        initStatusBar()
        init(savedInstanceState)
        initData()
        initView()
        start()
        initListener()
    }


    inline fun <reified T : RecyclerView.Adapter<BaseViewHolder>> getAdapter(recyclerView: RecyclerView): T? {
        recyclerView.adapter?.apply {
            return this as T
        }
        return null
    }
    /**
     *  加载布局
     */
    @LayoutRes
    abstract fun getLayout(): Int

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 初始化 View
     */
    abstract fun initView()

    /**
     * 开始请求
     */
    abstract fun start()

    private fun initListener() {
        mMultipleStatusView?.setOnClickListener(mRetryClickListener)
        mMultipleStatusView?.setOnViewStatusChangeListener(mViewStatusChangeListener)
    }

    abstract fun init(savedInstanceState: Bundle?)

    private fun initStatusBar() {
        ImmersionBar.with(this)
                .flymeOSStatusBarFontColor(R.color.black)  //修改flyme OS状态栏字体颜色
                .statusBarDarkFont(true)
                .transparentStatusBar()
                .titleBar(toolbar)
                .keyboardEnable(true).init()
    }

    open fun setToolBarTitle(toolBar: androidx.appcompat.widget.Toolbar, title: String?){
        val tvTitle = toolBar.findViewById<TextView>(R.id.title)
        tvTitle?.text = title
    }


    override fun showLoading(msg: String) {
        if (this.isFinishing || this.isDestroyed) {
            return
        }
        if (mLoadingDialog == null) {
            mLoadingDialog = BusyView(this)
            mLoadingDialog!!.loadingTxt = msg
        }
        if (!mLoadingDialog!!.isShowing) {
            mLoadingDialog!!.show()
        }
    }

    override fun hideLoading() {
        if (this.isFinishing || this.isDestroyed) {
            return
        }

        if (isLoadingDialogShowing()) {
            mLoadingDialog?.dismiss()
            mLoadingDialog = null
        }
    }

    private fun isLoadingDialogShowing(): Boolean {
        return mLoadingDialog != null && mLoadingDialog!!.isShowing
    }

    fun toastMsg(str: String) {
        toast(str)
    }

    private val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        start()
    }

    private val mViewStatusChangeListener: MultipleStatusView.OnViewStatusChangeListener =
        MultipleStatusView.OnViewStatusChangeListener { oldViewStatus, newViewStatus ->
            /**
             * 视图状态改变时回调
             *
             * @param oldViewStatus 之前的视图状态
             * @param newViewStatus 新的视图状态
             */
            Log.d(
                "MultipleStatusView", "oldViewStatus=" + oldViewStatus
                        + ", newViewStatus=" + newViewStatus
            )
        }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        //处理权限名字字符串
        val sb = StringBuffer()
        for (str in perms) {
            sb.append(str)
            sb.append("\n")
        }
        sb.replace(sb.length - 2, sb.length, "")
        //用户点击拒绝并不在询问时候调用
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Toast.makeText(this, "已拒绝权限" + sb + "并不再询问", Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this)
                .setRationale("此功能需要" + sb + "权限，否则无法正常使用，是否打开设置")
                .setPositiveButton("好")
                .setNegativeButton("不行")
                .build()
                .show()
        }
    }

    //自动弹出软键盘
    open fun openSoftKey(editText: EditText) {
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
//        val inputManager = /*editText.context.*/getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
//        inputManager.showSoftInput(editText, 0)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    open fun hideSoftKey() {
        // 当按了搜索之后关闭软键盘
//        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
//            currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
//        )

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

}