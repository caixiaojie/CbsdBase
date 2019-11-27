package com.yjhs.cbsdbase.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.*
import android.view.View
import android.webkit.JavascriptInterface
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.orhanobut.logger.Logger
import com.ycbjie.webviewlib.InterWebListener
import com.ycbjie.webviewlib.VideoWebListener
import com.ycbjie.webviewlib.X5WebUtils
import com.yjhs.cbsd.base.BaseVMFragment
import com.yjhs.cbsd.utils.FormFile
import com.yjhs.cbsdbase.ApiService
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.activity.PubActivity
import com.yjhs.cbsdbase.bean.EditArticleResp
import com.yjhs.cbsdbase.bean.JsonBridgeBean
import com.yjhs.cbsdbase.common.EditInfoHandle
import com.yjhs.cbsdbase.common.IWebCallback
import com.yjhs.cbsdbase.common.PhotoHandle
import com.yjhs.cbsdbase.common.VideoHandle
import com.yjhs.cbsdbase.model.FileUploadModel
import kotlinx.android.synthetic.main.common_preview_title.*
import kotlinx.android.synthetic.main.fragment_pub.*
import java.lang.ref.WeakReference
import kotlin.collections.ArrayList

/**
 * author: Administrator
 * date: 2019-11-11
 * desc:
 */
@SuppressLint("SetJavaScriptEnabled")
class PubFragment : BaseVMFragment(),IWebCallback {
    private val SELECTIMG = "selectImg"
    private val SELECTVIDEO = "selectVideo"
    private val EDITINFO = "editInfo"
    private val EDITINFOCALLBACK = "cbsdConnectionCallback.editInfoCallBack"
    private val HANDLE_MESSAGE = 4
    private var mainThreadHandler: MyHandler? = null
    private var themeId: Int = R.style.picture_default_style
    private val mViewModel by lazy { createViewModel<FileUploadModel>() }
    private val mPhotoHandle by lazy { PhotoHandle(this) }
    private val mVideoHandle by lazy { VideoHandle(this) }
    private val mEditInfoHandle by lazy { EditInfoHandle(this) }

    @SuppressLint("HandlerLeak")
    private inner class MyHandler internal constructor(context: Context) : Handler(Looper.getMainLooper()) {

        private val mContextReference: WeakReference<Context> = WeakReference(context)

        override fun handleMessage(msg: Message) {
            val context = mContextReference.get()
            if (context != null) {
                when (msg.what) {
                    HANDLE_MESSAGE -> handleMessage(msg.obj as JsonBridgeBean)
                    else -> {

                    }
                }
            }
        }
    }

    /**
     * 处理网页返回的消息
     */
    private fun handleMessage(info: JsonBridgeBean) {
        when(info.cmd){
            SELECTIMG ->{
                mPhotoHandle.setCallbackname(info.callback)
                openGalleryImg()
            }
            SELECTVIDEO ->{
                mVideoHandle.setCallbackname(info.callback)
                openGalleryVideo()
            }
            EDITINFO ->{
                mEditInfoHandle.setCallbackname(info.callback)
                mEditInfoHandle.setSuccess("","")
            }
        }
    }

    override fun initView() {
        img_back.visibility = View.GONE
        title_right.visibility = View.VISIBLE
        title_right.text = "下一步"
        setToolBarTitle(toolbar,"发布")
        progress.show()
        progress.setColor(
            ContextCompat.getColor(_mActivity,R.color.colorAccent),
            ContextCompat.getColor(_mActivity,R.color.colorPrimaryDark)
        )

        web_view.x5WebChromeClient.setWebListener(interWebListener)
        web_view.x5WebChromeClient.setVideoWebListener(videoWebListener)
        web_view.x5WebViewClient.setWebListener(interWebListener)
        mainThreadHandler = MyHandler(context!!)
    }

    override fun lazyLoad() {
        web_view.loadUrl(ApiService.WEP_IP + "/#/templateAdd")
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_pub
    }

    override fun init(savedInstanceState: Bundle?) {
        title_right.setOnClickListener {
            web_view.evaluateJavascript("javascript:getArticleInfo()",null)
        }
        web_view.addJavascriptInterface(object : Any() {
            @JavascriptInterface
            fun cbsd(info: String) {
                val jsonVO = JsonBridgeBean.getInstance(info) ?: return
                val msg: Message? = mainThreadHandler?.obtainMessage(HANDLE_MESSAGE, jsonVO)
                mainThreadHandler?.sendMessage(msg!!)
            }
        }, "mobile")



        mViewModel.imgdata.observe(this, Observer {
            mPhotoHandle.setSuccess(it.filePath,it.strImgrootpath)
            hideLoading()
        })

        mViewModel.filedata.observe(this, Observer {
            mVideoHandle.setSuccess(it.filePath,it.strImgrootpath)
            hideLoading()
        })
    }


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

    private fun openGalleryImg(){
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
            .theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
            .maxSelectNum(9)// 最大图片选择数量
            .minSelectNum(1)// 最小选择数量
            .imageSpanCount(4)// 每行显示个数
            .cameraFileName("")// 使用相机时保存至本地的文件名称,注意这个只在拍照时可以使用，选图时不要用
            .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
            .isSingleDirectReturn(false)// 单选模式下是否直接返回
            .previewImage(true)// 是否可预览图片
            .previewVideo(true)// 是否可预览视频
            .enablePreviewAudio(true) // 是否可播放音频
            .isCamera(true)// 是否显示拍照按钮
            .isChangeStatusBarFontColor(true)// 是否关闭白色状态栏字体颜色
//            .setStatusBarColorPrimaryDark(R.color.white)// 状态栏背景色
            .setUpArrowDrawable(R.mipmap.img_back)// 设置标题栏右侧箭头图标
//            .setDownArrowDrawable(downResId)// 设置标题栏右侧箭头图标
            .isOpenStyleCheckNumMode(true)// 是否开启数字选择模式 类似QQ相册
            .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
            //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
            //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
            .enableCrop(false)// 是否裁剪
            .compress(true)// 是否压缩
            .synOrAsy(false)//同步true或异步false 压缩 默认同步
            //.compressSavePath(getPath())//压缩图片保存地址
            //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
            .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//            .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
            .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
            .isGif(true)// 是否显示gif图片
            .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
            .circleDimmedLayer(false)// 是否圆形裁剪
            .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
            .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
            .openClickSound(false)// 是否开启点击声音
//            .selectionMedia(selectList)// 是否传入已选图片
            //.isDragFrame(false)// 是否可拖动裁剪框(固定)
            //                        .videoMaxSecond(15)
            //                        .videoMinSecond(10)
            //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
            //.cropCompressQuality(90)// 裁剪压缩质量 默认100
            .minimumCompressSize(100)// 小于100kb的图片不压缩
            //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
            //.rotateEnabled(true) // 裁剪是否可旋转图片
            //.scaleEnabled(true)// 裁剪是否可放大缩小图片
            //.videoQuality()// 视频录制质量 0 or 1
            //.videoSecond()//显示多少秒以内的视频or音频也可适用
            //.recordVideoSecond()//录制视频秒数 默认60s
            .forResult(PictureConfig.CHOOSE_REQUEST)//结果回调onActivityResult code
    }

    private fun openGalleryVideo(){
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofVideo())// 单独拍照，也可录像或也可音频 看你传入的类型是图片or视频
            .theme(themeId)// 主题样式设置 具体参考 values/styles
            .maxSelectNum(1)// 最大图片选择数量
            .minSelectNum(1)// 最小选择数量
            .selectionMode(PictureConfig.SINGLE)// 多选 or 单选
            .previewImage(true)// 是否可预览图片
            .previewVideo(true)// 是否可预览视频
            .enablePreviewAudio(true) // 是否可播放音频
            .isCamera(true)// 是否显示拍照按钮
            .isChangeStatusBarFontColor(false)// 是否关闭白色状态栏字体颜色
//            .setStatusBarColorPrimaryDark(statusBarColorPrimaryDark)// 状态栏背景色
            .isOpenStyleCheckNumMode(true)// 是否开启数字选择模式 类似QQ相册
//            .setUpArrowDrawable(upResId)// 设置标题栏右侧箭头图标
//            .setDownArrowDrawable(downResId)// 设置标题栏右侧箭头图标
            .enableCrop(true)// 是否裁剪
            .compress(true)// 是否压缩
            .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//            .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
            .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示
            .isGif(true)// 是否显示gif图片
            .freeStyleCropEnabled(false)// 裁剪框是否可拖拽
            .circleDimmedLayer(false)// 是否圆形裁剪
            .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
            .showCropGrid(false)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
            .openClickSound(false)// 是否开启点击声音
//            .selectionMedia(selectList)// 是否传入已选图片
            .previewEggs(false)//预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
            //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
            //.cropCompressQuality(90)// 裁剪压缩质量 默认为100
            .minimumCompressSize(100)// 小于100kb的图片不压缩
            //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
            //.rotateEnabled() // 裁剪是否可旋转图片
            //.scaleEnabled()// 裁剪是否可放大缩小图片
            //.videoQuality()// 视频录制质量 0 or 1
            //.videoSecond()////显示多少秒以内的视频or音频也可适用
            .forResult(PictureConfig.REQUEST_CAMERA)//结果回调onActivityResult code
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    // 图片选择结果回调
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    if (selectList.isEmpty()){
                        toastMsg("找不到图片")
                        return
                    }
                    var formFiles: MutableList<FormFile> = ArrayList<FormFile>()
                    for (index in selectList.indices) {
                        val formFile = FormFile(selectList[index].compressPath)
                        formFile.contentType = "image/*"//multipart/form-data
                        formFiles.add(formFile)
                    }
                    showLoading("上传中")
                    mViewModel.fileUpload(formFiles,0)
                }
                PictureConfig.REQUEST_CAMERA -> {
                    // 图片选择结果回调
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    if (selectList.isEmpty()){
                        toastMsg("找不到视屏")
                        return
                    }
                    var formFiles: MutableList<FormFile> = ArrayList<FormFile>()
                    for (index in selectList.indices) {
                        val formFile = FormFile(selectList[index].path)
                        formFile.contentType = "multipart/form-data"//multipart/form-data
                        formFiles.add(formFile)
                    }
                    showLoading("上传中")
                    mViewModel.fileUpload(formFiles,1)
                }
            }
        }
    }

    override fun `fun`(callbackname: String?, param: String?) {
        if (callbackname == null || callbackname.isEmpty()) {
            return
        }
        val callback = String.format("%s('%s');", callbackname, param)
        Logger.d(callback)
        web_view.evaluateJavascript(callback) {
            Logger.d(it)
            when(callbackname){
                "cbsdConnectionCallback.editInfoCallBack"->{
                    handleCallback(it)
                }
            }
        }
    }

    /**
     * 处理下一步的回调信息
     */
    private fun handleCallback(msg: String){
        if (msg.isNullOrBlank()) {
            toastMsg("请先编辑文章")
            return
        }
        val editArticleVo: EditArticleResp? = Gson().fromJson<Any>(msg, object : TypeToken<EditArticleResp>() {}.type) as EditArticleResp
        if (editArticleVo != null) {
            val title = editArticleVo.title
            val content = editArticleVo.content
            val videoSrc = editArticleVo.videoSrc
            val videoImg = editArticleVo.videoImg
            if (title.isNullOrBlank()) {
                toastMsg("文章标题不能为空")
                return
            }
            if (content.isNullOrBlank()) {
                toastMsg("文章内容不能为空")
                return
            }
            if (videoSrc != null && "" != videoSrc) {
                editArticleVo.isVideo = "1"
                editArticleVo.videoImg = videoImg
                editArticleVo.videoSrc = videoSrc
            } else {
                editArticleVo.isVideo = "0"
            }

            editArticleVo.isUrl = "0"
            editArticleVo.title = title
            editArticleVo.content = content
            editArticleVo.isModel = "1"
            val intent = Intent(activity, PubActivity::class.java)
            intent.putExtra("obj", editArticleVo)
            startActivity(intent)
        } else {
            toastMsg("json解析出错")
        }
    }

}