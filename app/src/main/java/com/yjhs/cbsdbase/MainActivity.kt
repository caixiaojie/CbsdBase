package com.yjhs.cbsdbase

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.yjhs.cbsd.mvp.BaseVMActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseVMActivity(), BaseQuickAdapter.OnItemChildClickListener, OnRefreshListener,
    OnLoadMoreListener {
    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mCurrPager++
        map.put("pageNumber",mCurrPager)
        mViewModel.queryData(map)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mCurrPager = 1
        map.put("pageNumber",mCurrPager)
        mViewModel.queryData(map)
    }

    override fun onError(throwable: Throwable) {
        sr_layout.finishRefresh()
        sr_layout.finishLoadMore()
        hideLoading()
    }


    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun init(savedInstanceState: Bundle?) {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv.adapter = adapter


        mViewModel.infoBean.observe(this, Observer {
            sr_layout.finishRefresh()
            sr_layout.finishLoadMore()
            hideLoading()
            if (mCurrPager == 1){
                mList.clear()
            }
            mList.addAll(it)
            if (mList.isEmpty()){
                val nodata = TextView(this)
                nodata.text = "暂无数据"
                adapter.emptyView = nodata
            }
            adapter.notifyDataSetChanged()
        })


        sr_layout.setOnRefreshListener(this)
        sr_layout.setOnLoadMoreListener(this)


        showLoading()
        map.put("pageSize",10)
        map.put("pageNumber",mCurrPager)
        mViewModel.queryData(map)
    }

    private val mViewModel by lazy { createViewModel<TestViewModel>() }
    private val adapter by lazy { HomeAdapter(mList) }
    private var mCurrPager = 1
    private var mList = ArrayList<InforModel>()
    private var map: HashMap<String,Any> = HashMap<String,Any>()
    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
