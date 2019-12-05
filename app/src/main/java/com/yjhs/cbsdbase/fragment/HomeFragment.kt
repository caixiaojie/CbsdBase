package com.yjhs.cbsdbase.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.google.android.material.appbar.AppBarLayout
import com.orhanobut.logger.Logger
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.yjhs.cbsd.http.ApiException
import com.yjhs.cbsd.http.ExceptionHandle
import com.yjhs.cbsd.base.BaseVMFragment
import com.yjhs.cbsd.ui.widget.dialog.PopDialog
import com.yjhs.cbsd.utils.DisplayUtils
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.adapter.HomeAdapter
import com.yjhs.cbsdbase.bean.InforModel
import com.yjhs.cbsdbase.model.TestViewModel
import kotlinx.android.synthetic.main.common_preview_title.*
import kotlinx.android.synthetic.main.content_recycler_view.*
import kotlinx.android.synthetic.main.content_refresh.*
import com.yjhs.cbsd.utils.RecycleViewDivider
import com.yjhs.cbsd.utils.Tools
import com.yjhs.cbsdbase.GlideImageLoader
import kotlinx.android.synthetic.main.common_preview_title.toolbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


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
    private lateinit var pop: PopDialog
    private val banners = arrayListOf(R.mipmap._1,R.mipmap._2,R.mipmap._3,R.mipmap._4,R.mipmap._5,R.mipmap._6)


    override fun initView() {
        mMultipleStatusView = multiple_status_view
        recyclerView.addItemDecoration(RecycleViewDivider(_mActivity, LinearLayoutManager.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(_mActivity as Context?, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.onItemClickListener = this

        toolbar.background.alpha = 0
        txt_title.alpha = 0f
        banner.setImageLoader(GlideImageLoader()).setImages(banners).start()
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
        val alphaMaxOffset = DisplayUtils.dp2px(89f) - Tools.getStatusBarHeight(_mActivity)
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            Logger.d("$verticalOffset------$alphaMaxOffset------${Tools.getStatusBarHeight(_mActivity)}")
            // 设置 toolbar 背景
            if (verticalOffset > -alphaMaxOffset) {
                toolbar.background.alpha = 255 * -verticalOffset / alphaMaxOffset;
                txt_title.alpha = -verticalOffset / alphaMaxOffset.toFloat();
                Logger.d("alpha:${255 * -verticalOffset / alphaMaxOffset}")
            } else {
                toolbar.background.alpha = 255;
                txt_title.alpha = 1f;
                Logger.d("alpha:255")
            }
        })
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val model = mList[position]

//        showPop(position)
//        showLoading("测试")
        pop = PopDialog(_mActivity)
        pop.model = model
        pop.show()
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
        if (throwable.code == ExceptionHandle.NETWORK_DISCONNECT){
            multiple_status_view.showError()
        }else {
            multiple_status_view.showError()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (pop != null){
            pop.dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        banner.stopAutoPlay()
    }
}
