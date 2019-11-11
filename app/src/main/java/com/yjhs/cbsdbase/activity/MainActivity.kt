package com.yjhs.cbsdbase.activity

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.yjhs.cbsd.mvp.BaseActivity
import com.yjhs.cbsd.mvp.BaseVMActivity
import com.yjhs.cbsd.utils.Tools
import com.yjhs.cbsdbase.adapter.HomeAdapter
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.bean.InforModel
import com.yjhs.cbsdbase.fragment.HomeFragment
import com.yjhs.cbsdbase.fragment.MeFragment
import com.yjhs.cbsdbase.fragment.PubFragment
import com.yjhs.cbsdbase.model.TestViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.common_preview_title.*
import kotlinx.android.synthetic.main.content_recycler_view.*
import kotlinx.android.synthetic.main.content_refresh.*
import me.yokeyword.fragmentation.SupportFragment
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity() {
    private val mFragments = arrayOfNulls<SupportFragment>(3)
    override fun initData() {
        val firstFragment = findFragment(HomeFragment::class.java)

        if (firstFragment == null) {
            mFragments[0] = HomeFragment()
            mFragments[1] = PubFragment()
            mFragments[2] = MeFragment()
            loadMultipleRootFragment(
                R.id.fl_main, 0, mFragments[0],
                mFragments[1],mFragments[2]
            )
        } else {
            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment
            mFragments[1] = findFragment(PubFragment::class.java)
            mFragments[2] = findFragment(MeFragment::class.java)
        }

    }

    override fun initView() {
    }

    override fun start() {

    }


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun init(savedInstanceState: Bundle?) {
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                showHideFragment(mFragments[0])
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                showHideFragment(mFragments[1])
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                showHideFragment(mFragments[2])
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


}
