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
import com.yjhs.cbsd.mvp.BaseVMActivity
import com.yjhs.cbsd.utils.Tools
import com.yjhs.cbsdbase.adapter.HomeAdapter
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.bean.InforModel
import com.yjhs.cbsdbase.model.TestViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseVMActivity(), BaseQuickAdapter.OnItemClickListener, OnRefreshListener,
    OnLoadMoreListener {
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val model = mList.get(position)
        startActivity<DetailActivity>(Pair("strinforid",model.strinforid),Pair("strUserinfoid",model.struserinfoid),
            Pair("strUrl",model.strUrl), Pair("isAd",false),Pair("isUrl", Tools.safeString(model.isUrl) == "1"),Pair("strBrandId",model.strbrandid)
            ,Pair("strCarModelId",model.strcarmodelid),Pair("strCarSpecId",model.strcarspecid)
        )
    }

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


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun init(savedInstanceState: Bundle?) {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv.adapter = adapter
        adapter.onItemClickListener = this


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
