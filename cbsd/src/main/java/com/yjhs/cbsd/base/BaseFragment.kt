package com.yjhs.cbsd.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.gyf.immersionbar.ImmersionBar
import com.orhanobut.logger.Logger
import com.yjhs.cbsd.Constant
import com.yjhs.cbsd.R
import com.yjhs.cbsd.event.KeyboardShowEvent
import com.yjhs.cbsd.event.NetworkChangeEvent
import com.yjhs.cbsd.utils.Preference
import com.yjhs.cbsd.widget.MultipleStatusView
import com.yjhs.cbsd.widget.toast.XToast
import kotlinx.android.synthetic.main.common_preview_title.*
import me.yokeyword.fragmentation.SupportFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.support.v4.toast
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

/**
 *
 * @author  Lai
 *
 * @time 2019/9/22 16:44
 * @describe describe
 *
 */
abstract class BaseFragment : SupportFragment(), IBaseView, EasyPermissions.PermissionCallbacks {

    //是否首次加载
    private var isFirstLoad = true
    open var mMultipleStatusView: MultipleStatusView? = null
    /**
     * 缓存上一次的网络状态
     */
    protected var hasNetwork: Boolean by Preference(Constant.HAS_NETWORK_KEY, true)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onCreateView(savedInstanceState)
        return inflater.inflate(getLayoutId(), container, false)
    }

    open fun onCreateView(savedInstanceState: Bundle?) {

    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        isFirstLoad = false
        init(savedInstanceState)
        initStatusBar()
        initView()
        lazyLoad()
        //多种状态切换的view 重试点击事件
        mMultipleStatusView?.setOnClickListener(mRetryClickListener)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!isFirstLoad) {
            if (!hidden) {
                onFragmentVisible()
            }
        }
    }

    private fun initStatusBar() {
        ImmersionBar.with(this)
            .flymeOSStatusBarFontColor(R.color.black)  //修改flyme OS状态栏字体颜色
            .statusBarDarkFont(true)
//                .transparentStatusBar()
            .titleBar(toolbar)
            .keyboardEnable(true)
//            .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)  //单独指定软键盘模式
            .setOnKeyboardListener { isPopup, keyboardHeight ->
                Logger.d("isPopup=$isPopup,keyboardHeight=$keyboardHeight")
                EventBus.getDefault().post(KeyboardShowEvent(isPopup))
            }
            .init()
    }
    open fun setToolBarTitle(toolBar: androidx.appcompat.widget.Toolbar, title: String?){
        val tvTitle = toolBar.findViewById<TextView>(R.id.title)
        tvTitle?.text = title
    }
    /**
     * 初始化 ViewI
     */
    abstract fun initView()

    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    /**
     * fragment可见
     */
    open fun onFragmentVisible() {}


    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract fun getLayoutId(): Int

    //时间初始化方法
    protected abstract fun init(savedInstanceState: Bundle?)

    fun toastMsg(str: String) {
//        toast(str)
        XToast.warning(_mActivity,str).show()
    }


    override fun showLoading(msg: String) {
        if (_mActivity is BaseActivity) {
            (_mActivity as BaseActivity).showLoading(msg)
        }
    }

    override fun hideLoading() {
        if (_mActivity is BaseActivity) {
            (_mActivity as BaseActivity).hideLoading()
        }
    }

    private val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        lazyLoad()
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
            Toast.makeText(_mActivity, "已拒绝权限" + sb + "并不再询问", Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this)
                .setRationale("此功能需要" + sb + "权限，否则无法正常使用，是否打开设置")
                .setPositiveButton("好")
                .setNegativeButton("不行")
                .build()
                .show()
        }
    }

    /**
     * 是否使用 EventBus
     */
    open fun useEventBus(): Boolean = true

    /**
     * 无网状态—>有网状态 的自动重连操作，子类可重写该方法
     */
    open fun doReConnected() {
        lazyLoad()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onNetworkChangeEvent(event: NetworkChangeEvent) {
        if (event.isConnected) {
            doReConnected()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }
}