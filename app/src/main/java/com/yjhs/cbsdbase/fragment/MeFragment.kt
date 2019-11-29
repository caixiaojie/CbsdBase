package com.yjhs.cbsdbase.fragment

import android.os.Bundle
import com.yjhs.cbsd.base.BaseFragment
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.activity.FileActivity
import com.yjhs.cbsdbase.view.ArcSeekBarActivity
import com.yjhs.cbsdbase.view.BubbleActivity
import com.yjhs.cbsdbase.view.PieActivity
import kotlinx.android.synthetic.main.fragment_me.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * author: Administrator
 * date: 2019-11-11
 * desc:
 */
class MeFragment : BaseFragment() {
    override fun initView() {
    }

    override fun lazyLoad() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_me
    }

    override fun init(savedInstanceState: Bundle?) {
        ll_account_manage.setOnClickListener {
            startActivity<FileActivity>()
        }
        ll_change_pwd.setOnClickListener {
            startActivity<PieActivity>()
        }
        ll_banner.setOnClickListener {
            startActivity<ArcSeekBarActivity>()
        }
        ll_shouce.setOnClickListener {
            startActivity<BubbleActivity>()
        }
    }
}