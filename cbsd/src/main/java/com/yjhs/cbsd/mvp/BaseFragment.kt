package com.yjhs.cbsd.mvp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.gyf.immersionbar.ImmersionBar
import com.yjhs.cbsd.R
import com.yjhs.cbsd.widget.MultipleStatusView
import kotlinx.android.synthetic.main.common_preview_title.*
import me.yokeyword.fragmentation.SupportFragment

/**
 *
 * @author  Lai
 *
 * @time 2019/9/22 16:44
 * @describe describe
 *
 */
abstract class BaseFragment : SupportFragment(), IBaseView {

    //是否首次加载
    private var isFirstLoad = true
    private var mMultipleStatusView: MultipleStatusView? = null


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
            .transparentStatusBar()
            .titleBar(toolbar)
            .keyboardEnable(true).init()
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

    fun toast(str: String) {
        Toast.makeText(_mActivity, str, Toast.LENGTH_SHORT).show()
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
}