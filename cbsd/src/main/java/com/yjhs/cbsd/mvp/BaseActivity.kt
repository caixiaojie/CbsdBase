package com.yjhs.cbsd.mvp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseViewHolder
import com.gyf.immersionbar.ImmersionBar
import com.yjhs.cbsd.ui.widget.dialog.BusyView
import com.yjhs.cbsd.widget.MultipleStatusView
import kotlinx.android.synthetic.main.common_preview_title.*
import me.yokeyword.fragmentation.SupportActivity
import pub.devrel.easypermissions.EasyPermissions
import android.R
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
            mLoadingDialog?.loadingTxt = msg
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

    fun toast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
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

}