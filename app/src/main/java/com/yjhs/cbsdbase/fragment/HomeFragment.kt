package com.yjhs.cbsdbase.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.yjhs.cbsd.http.ApiException
import com.yjhs.cbsd.mvp.BaseFragment
import com.yjhs.cbsd.mvp.BaseVMFragment
import com.yjhs.cbsd.utils.Tools
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.activity.DetailActivity
import com.yjhs.cbsdbase.adapter.HomeAdapter
import com.yjhs.cbsdbase.bean.InforModel
import com.yjhs.cbsdbase.model.TestViewModel
import kotlinx.android.synthetic.main.common_preview_title.*
import kotlinx.android.synthetic.main.content_recycler_view.*
import kotlinx.android.synthetic.main.content_refresh.*
import org.jetbrains.anko.support.v4.startActivity
import com.yjhs.cbsd.utils.RecycleViewDivider


/**
 * author: Administrator
 * date: 2019-11-11
 * desc:
 */
class HomeFragment : BaseVMFragment() , BaseQuickAdapter.OnItemClickListener, OnRefreshListener,
    OnLoadMoreListener {
    private val mViewModel by lazy { createViewModel<TestViewModel>() }
    private val adapter by lazy { HomeAdapter(mList) }
    private var mCurrPager = 1
    private var mList = ArrayList<InforModel>()
    private var map: HashMap<String,Any> = HashMap<String,Any>()


    override fun initView() {
        mMultipleStatusView = multiple_status_view
        recyclerView.addItemDecoration(RecycleViewDivider(_mActivity, LinearLayoutManager.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(_mActivity as Context?, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.onItemClickListener = this

        img_back.visibility = View.GONE
        setToolBarTitle(toolbar,"首页")
    }

    override fun lazyLoad() {
        map["pageSize"] = 1000
        map["pageNumber"] = mCurrPager
        multiple_status_view.showLoading()
        mViewModel.queryData(map)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun init(savedInstanceState: Bundle?) {
        mViewModel.infoBean.observe(this, Observer {
            multiple_status_view.showContent()
            smart_refresh_layout.finishRefresh()
            smart_refresh_layout.finishLoadMore()
            if (mCurrPager == 1){
                mList.clear()
            }
            mList.addAll(it)
            if (mList.isEmpty()){
                multiple_status_view.showEmpty()
            }
            adapter.notifyDataSetChanged()
        })

        smart_refresh_layout.setOnRefreshListener(this)
        smart_refresh_layout.setOnLoadMoreListener(this)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val model = mList.get(position)
        startActivity<DetailActivity>(Pair("strinforid",model.strinforid),Pair("strUserinfoid",model.struserinfoid),
            Pair("strUrl",model.strUrl), Pair("isAd",false),Pair("isUrl", Tools.safeString(model.isUrl) == "1"),Pair("strBrandId",model.strbrandid)
            ,Pair("strCarModelId",model.strcarmodelid),Pair("strCarSpecId",model.strcarspecid)
        )
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mCurrPager++
        map["pageNumber"] = mCurrPager
        mViewModel.queryData(map)
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mCurrPager = 1
        map["pageNumber"] = mCurrPager
        mViewModel.queryData(map)
    }

    override fun onError(throwable: ApiException) {
        smart_refresh_layout.finishRefresh()
        smart_refresh_layout.finishLoadMore()
        hideLoading()
        multiple_status_view.showError()
    }
}
