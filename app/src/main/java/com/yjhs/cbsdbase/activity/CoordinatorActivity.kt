package com.yjhs.cbsdbase.activity

import android.os.Bundle
import com.yjhs.cbsd.base.BaseActivity
import com.yjhs.cbsdbase.R
import kotlinx.android.synthetic.main.activity_corrdinate_layout.*

/**
 * author: Administrator
 * date: 2019-11-20
 * desc:
 */
class CoordinatorActivity : BaseActivity(){
    override fun getLayout(): Int {
        return R.layout.activity_corrdinate_layout
    }

    override fun initData() {
    }

    override fun initView() {

    }

    override fun start() {

    }

    override fun init(savedInstanceState: Bundle?) {

    }

//    protected fun initToolBar() {
//        try {
//            if (toolbar != null) {
//                // 沉浸模式
//                int statusBarHeight = getStatusBarHeight()
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    openAndroidLStyle();
//
//                    toolbar.setPadding(0, statusBarHeight, 0, 0);
//                    toolbar.getLayoutParams().height = dpToPx(46) + statusBarHeight;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}