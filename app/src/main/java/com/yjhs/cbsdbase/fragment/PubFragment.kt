package com.yjhs.cbsdbase.fragment

import android.os.Bundle
import android.view.View
import com.yjhs.cbsd.mvp.BaseVMFragment
import com.yjhs.cbsdbase.R
import kotlinx.android.synthetic.main.common_preview_title.*

/**
 * author: Administrator
 * date: 2019-11-11
 * desc:
 */
class PubFragment : BaseVMFragment() {
    override fun initView() {
        img_back.visibility = View.GONE
        setToolBarTitle(toolbar,"发布")
    }

    override fun lazyLoad() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_pub
    }

    override fun init(savedInstanceState: Bundle?) {
    }
}