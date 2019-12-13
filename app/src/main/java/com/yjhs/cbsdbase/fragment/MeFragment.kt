package com.yjhs.cbsdbase.fragment

import android.graphics.Color
import android.os.Bundle
import com.azhon.appupdate.config.UpdateConfiguration
import com.azhon.appupdate.listener.OnButtonClickListener
import com.azhon.appupdate.listener.OnDownloadListener
import com.azhon.appupdate.manager.DownloadManager
import com.gyf.immersionbar.ImmersionBar
import com.yjhs.cbsd.base.BaseFragment
import com.yjhs.cbsd.core.ext.startKtxActivity
import com.yjhs.cbsd.utils.Preference
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.activity.DropDownMenuActivity
import com.yjhs.cbsdbase.activity.FileActivity
import com.yjhs.cbsdbase.activity.LoginActivityCopy
import com.yjhs.cbsdbase.view.ArcSeekBarActivity
import com.yjhs.cbsdbase.view.BubbleActivity
import com.yjhs.cbsdbase.view.PieActivity
import kotlinx.android.synthetic.main.common_preview_title.*
import kotlinx.android.synthetic.main.fragment_me.*
import org.jetbrains.anko.support.v4.startActivity
import java.io.File
import java.lang.Exception

/**
 * author: Administrator
 * date: 2019-11-11
 * desc:
 */
class MeFragment : BaseFragment(), OnButtonClickListener, OnDownloadListener {
    override fun start() {
    }

    override fun downloading(max: Int, progress: Int) {
    }

    override fun done(apk: File?) {
    }

    override fun cancel() {
    }

    override fun error(e: Exception?) {
    }

    override fun onButtonClick(id: Int) {
    }

    private lateinit var manager: DownloadManager
    private val url =
        "https://f29addac654be01c67d351d1b4282d53.dd.cdntips.com/imtt.dd.qq.com/16891/DC501F04BBAA458C9DC33008EFED5E7F.apk?mkey=5d6d132d73c4febb&f=0c2f&fsname=com.estrongs.android.pop_4.2.0.2.1_10027.apk&csr=1bbd&cip=115.196.216.78&proto=https"

    private var sessionId by Preference(Preference.session_id, "")

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
        ll_setting.setOnClickListener {
            startUpdate()
        }
        ll_drop_menu.setOnClickListener {
            startActivity<DropDownMenuActivity>()
        }
        ll_exit.setOnClickListener {
            sessionId = ""
            startKtxActivity<LoginActivityCopy>()
        }
    }

    private fun startUpdate() {
        /*
         * 整个库允许配置的内容
         * 非必选
         */
        val configuration = UpdateConfiguration()
            //输出错误日志
            .setEnableLog(true)
            //设置自定义的下载
            //.setHttpManager()
            //下载完成自动跳动安装页面
            .setJumpInstallPage(true)
            //设置对话框背景图片 (图片规范参照demo中的示例图)
            //.setDialogImage(R.drawable.ic_dialog)
            //设置按钮的颜色
            //.setDialogButtonColor(Color.parseColor("#E743DA"))
            //设置对话框强制更新时进度条和文字的颜色
            //.setDialogProgressBarColor(Color.parseColor("#E743DA"))
            //设置按钮的文字颜色
            .setDialogButtonTextColor(Color.WHITE)
            //设置是否显示通知栏进度
            .setShowNotification(true)
            //设置是否提示后台下载toast
            .setShowBgdToast(false)
            //设置强制更新
            .setForcedUpgrade(false)
            //设置对话框按钮的点击监听
            .setButtonClickListener(this)
            //设置下载过程的监听
            .setOnDownloadListener(this)

        manager = DownloadManager.getInstance(_mActivity)
        manager.setApkName("ESFileExplorer.apk")
            .setApkUrl(url)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setShowNewerToast(true)
            .setConfiguration(configuration)
            .setApkVersionCode(2)
            .setApkVersionName("2.1.8")
            .setApkSize("20.4")
            .setAuthorities(_mActivity.packageName)
            .setApkDescription(getString(R.string.dialog_msg))
            //                .setApkMD5("DC501F04BBAA458C9DC33008EFED5E7F")
            .download()
    }
}