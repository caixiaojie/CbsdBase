package com.yjhs.cbsdbase.activity

import android.os.Bundle
import android.os.Environment
import com.yjhs.cbsd.base.BaseActivity
import com.yjhs.cbsd.utils.SDUtils
import com.yjhs.cbsdbase.R
import kotlinx.android.synthetic.main.activity_file.*

/**
 * author: Administrator
 * date: 2019-11-26
 * desc:
 */
class FileActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_file
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun start() {
    }

    override fun init(savedInstanceState: Bundle?) {
        btn_1.setOnClickListener {
            val sdCardAvailableSize = SDUtils.getSDCardAvailableSize()
            val sdCardCacheDir = SDUtils.getSDCardCacheDir(this)
            val sdCardRootDir = SDUtils.getSDCardRootDir()
            val sdCardTotalSize = SDUtils.getSDCardTotalSize()
            SDUtils.saveFileToPublicDirectory(ByteArray(5),Environment.DIRECTORY_DOWNLOADS,"test.txt")
            SDUtils.saveFileToExternalFileDir(this,ByteArray(5),"test2.txt")
            SDUtils.saveFileToExternalCacheDir(this,ByteArray(5),"test3.txt")
            txt_log.text = "sdCardTotalSize=$sdCardTotalSize+\n" +
                    "sdCardAvailableSize=$sdCardAvailableSize+\n"+
                    "sdCardCacheDir=$sdCardCacheDir+\n"+
                    "sdCardRootDir=$sdCardRootDir+\n"
        }
    }
}