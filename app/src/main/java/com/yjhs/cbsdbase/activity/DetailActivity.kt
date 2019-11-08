package com.yjhs.cbsdbase.activity

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import com.orhanobut.logger.Logger
import com.yjhs.cbsd.mvp.BaseVMActivity
import com.yjhs.cbsd.utils.Preference
import com.yjhs.cbsd.utils.Tools
import com.yjhs.cbsdbase.ApiService
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.bean.ComparePriceReq
import com.yjhs.cbsdbase.model.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * author: Administrator
 * date: 2019-11-8
 * desc:
 */
class DetailActivity : BaseVMActivity() {
    private var strBrandId = ""
    private var strCarModelId = ""
    private var strCarSpecId = ""
    private var strUserinfoid = ""
    private var strinforid = ""
    private var url = ""
    private var isAd = false
    private var isUrl = false
    private var session_id by Preference(Preference.session_id, "")
    private var strUserType by Preference(Preference.strUserType, "")
    private val mViewModel by lazy { createViewModel<DetailViewModel>() }
    private val mPriceReq by lazy { ComparePriceReq() }

    override fun getLayout(): Int {
        return R.layout.activity_detail
    }

    override fun init(savedInstanceState: Bundle?) {
        val extras = this.intent.extras
        extras?.let {
            strBrandId = it.getString("strBrandId","")
            strCarModelId = it.getString("strCarModelId","")
            strCarSpecId = it.getString("strCarSpecId","")
            strUserinfoid = it.getString("strUserinfoid","")
            strinforid = it.getString("strinforid","")
            url = it.getString("strUrl","")
            isAd = it.getBoolean("isAd",false)
            isUrl = it.getBoolean("isUrl",false)
        }

        initWeb()

        mViewModel.data.observe(this, Observer {

        })

        mViewModel.mdataList.observe(this, Observer { it ->
            it.forEach {
                Logger.d(it.toString())
            }
        })

        mViewModel.collectState(strinforid)
        mPriceReq.strBrandId = strBrandId
        mPriceReq.strCarModelId = strCarModelId
        mPriceReq.strCarSpecId = strCarSpecId
        mPriceReq.strInforId = strinforid
        mViewModel.priceList(Tools.objectToMap(mPriceReq))
    }

    private fun initWeb(){

        val webSetting = webView.settings
        webSetting.setJavaScriptEnabled(true)
        webSetting.javaScriptCanOpenWindowsAutomatically = true
        webSetting.setAllowFileAccess(true)
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS)
        webSetting.setSupportZoom(true)
        webSetting.setBuiltInZoomControls(true)
        webSetting.setUseWideViewPort(true)
//		webSetting.setMediaPlaybackRequiresUserGesture(false);
        webSetting.setSupportMultipleWindows(true)
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true)
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true)
        webSetting.setGeolocationEnabled(true)
        webSetting.setAppCacheMaxSize(java.lang.Long.MAX_VALUE)
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND)
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE)
        webView.webViewClient = client
        loadUrl()
    }

    private fun loadUrl() {
        if (isAd) {
            if (isUrl) {
                webView.loadUrl(ApiService.WEP_IP + "/#/AppAdvertisement/" + strinforid + "/" + session_id)
            } else {
                ///#/AppAdvertisement /传资讯ID/ 传session_id
                webView.loadUrl(ApiService.WEP_IP + "/#/AppAdvertisement/" + strinforid + "/" + session_id)
            }
        } else {
            if (isUrl) {
                webView.loadUrl(ApiService.WEP_IP + "/#/HomeAppArticle/" + strinforid + "/" + session_id+ "/" + strUserType)
            } else {
                webView.loadUrl(ApiService.WEP_IP + "/#/HomeAppArticle/" + strinforid + "/" + session_id+ "/" + strUserType)
            }
        }
        Logger.d(ApiService.WEP_IP + "/#/HomeAppArticle/" + strinforid + "/" + session_id+ "/" + strUserType)
    }

    private val client = object : WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }
}