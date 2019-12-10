package com.yjhs.cbsd.base

import android.os.Bundle
import android.util.Log
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
import android.content.Context
import android.content.IntentFilter
import android.graphics.PixelFormat
import android.view.*
import android.widget.EditText
import com.yjhs.cbsd.Constant
import com.yjhs.cbsd.R
import com.yjhs.cbsd.event.NetworkChangeEvent
import com.yjhs.cbsd.receiver.NetworkChangeReceiver
import com.yjhs.cbsd.utils.CommonUtil
import com.yjhs.cbsd.utils.KeyBoardUtil
import com.yjhs.cbsd.utils.Preference
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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
    /**
     * 缓存上一次的网络状态
     */
    protected var hasNetwork: Boolean by Preference(Constant.HAS_NETWORK_KEY, true)

    /**
     * 网络状态变化的广播
     */
    protected var mNetworkChangeReceiver: NetworkChangeReceiver? = null
    /**
     * 提示View
     */
    private lateinit var mTipView: View
    private lateinit var mWindowManager: WindowManager
    private lateinit var mLayoutParams: WindowManager.LayoutParams


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        initStatusBar()
        init(savedInstanceState)
        initData()
        initTipView()
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_UP) {
            val v = currentFocus
            // 如果不是落在EditText区域，则需要关闭输入法
            if (KeyBoardUtil.isHideKeyboard(v, ev)) {
                KeyBoardUtil.hideKeyBoard(this, v)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 初始化 TipView
     */
    private fun initTipView() {
        mTipView = layoutInflater.inflate(R.layout.layout_network_tip, null)
        mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mLayoutParams = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            PixelFormat.TRANSLUCENT)
        mLayoutParams.gravity = Gravity.TOP
        mLayoutParams.x = 0
        mLayoutParams.y = 0
        mLayoutParams.windowAnimations = R.style.anim_float_view // add animations
    }

    override fun onResume() {
        // 动态注册网络变化广播
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        mNetworkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(mNetworkChangeReceiver, filter)
        super.onResume()
    }

    /**
     * 是否使用 EventBus
     */
    open fun useEventBus(): Boolean = true

    /**
     * 是否需要显示 TipView
     */
    open fun enableNetworkTip(): Boolean = true

    /**
     * 无网状态—>有网状态 的自动重连操作，子类可重写该方法
     */
    open fun doReConnected() {
        start()
    }

    /**
     * Network Change
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetworkChangeEvent) {
        hasNetwork = event.isConnected
        checkNetwork(event.isConnected)
    }

    private fun checkNetwork(isConnected: Boolean) {
        if (enableNetworkTip()) {
            if (isConnected) {
                doReConnected()
                if (mTipView != null && mTipView.parent != null) {
                    mWindowManager.removeView(mTipView)
                }
            } else {
                if (mTipView.parent == null) {
                    mWindowManager.addView(mTipView, mLayoutParams)
                }
            }
        }
    }


    override fun onPause() {
        if (mNetworkChangeReceiver != null) {
            unregisterReceiver(mNetworkChangeReceiver)
            mNetworkChangeReceiver = null
        }
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
        CommonUtil.fixInputMethodManagerLeak(this)
    }

    override fun finish() {
        super.finish()
        if (mTipView != null && mTipView.parent != null) {
            mWindowManager.removeView(mTipView)
        }
    }

}