package com.yjhs.cbsdbase.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import androidx.lifecycle.Observer
import com.orhanobut.logger.Logger
import com.ycbjie.webviewlib.InterWebListener
import com.ycbjie.webviewlib.VideoWebListener
import com.ycbjie.webviewlib.X5WebUtils
import com.yjhs.cbsd.base.BaseVMActivity
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
    override fun initData() {
    }

    override fun initView() {
        progress.show()
        progress.setColor(
            this.resources.getColor(R.color.colorAccent),
            this.resources.getColor(R.color.colorPrimaryDark)
        )

        web_view.x5WebChromeClient.setWebListener(interWebListener)
        web_view.x5WebChromeClient.setVideoWebListener(videoWebListener)
        web_view.x5WebViewClient.setWebListener(interWebListener)
    }

    override fun start() {
    }

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

        loadUrl()

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

    private fun loadUrl() {
        if (isAd) {
            if (isUrl) {
                web_view.loadUrl(ApiService.WEP_IP + "/#/AppAdvertisement/" + strinforid + "/" + session_id)
            } else {
                ///#/AppAdvertisement /传资讯ID/ 传session_id
                web_view.loadUrl(ApiService.WEP_IP + "/#/AppAdvertisement/" + strinforid + "/" + session_id)
            }
        } else {
            if (isUrl) {
                web_view.loadUrl(ApiService.WEP_IP + "/#/HomeAppArticle/" + strinforid + "/" + session_id+ "/" + strUserType)
            } else {
                web_view.loadUrl(ApiService.WEP_IP + "/#/HomeAppArticle/" + strinforid + "/" + session_id+ "/" + strUserType)
            }
        }
        Logger.d(ApiService.WEP_IP + "/#/HomeAppArticle/" + strinforid + "/" + session_id+ "/" + strUserType)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onResume() {
        super.onResume()
        if (web_view != null) {
            web_view.settings.javaScriptEnabled = true;
        }
    }

    override fun onStop() {
        super.onStop()
        if (web_view!= null) {
            web_view.settings.javaScriptEnabled = false;
        }
    }

    override fun onDestroy() {
        try {
            if (web_view != null) {
                web_view.stopLoading()
                web_view.destroy()
            }
        } catch (e: Exception) {
            Logger.e("X5WebViewActivity", e.message)
        }
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (web_view.canGoBack() && event?.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            web_view.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private val interWebListener = object : InterWebListener {
        override fun hindProgressBar() {
            progress.hide()
        }

        override fun showErrorView(@X5WebUtils.ErrorType type: Int) {
            when (type) {
                //没有网络
                X5WebUtils.ErrorMode.NO_NET -> {
                }
                //404，网页无法打开
                X5WebUtils.ErrorMode.STATE_404 -> {
                }
                //onReceivedError，请求网络出现error
                X5WebUtils.ErrorMode.RECEIVED_ERROR -> {
                }
                //在加载资源时通知主机应用程序发生SSL错误
                X5WebUtils.ErrorMode.SSL_ERROR -> {
                }
                else -> {
                }
            }
        }

        override fun startProgress(newProgress: Int) {
            progress.setWebProgress(newProgress)
        }

        override fun showTitle(title: String) {

        }
    }

    private val videoWebListener = object : VideoWebListener {
        override fun showVideoFullView() {
            //视频全频播放时监听
        }

        override fun hindVideoFullView() {
            //隐藏全频播放，也就是正常播放视频
        }

        override fun showWebView() {
            //显示webView
        }

        override fun hindWebView() {
            //隐藏webView
        }
    }

}