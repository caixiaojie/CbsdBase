package com.yjhs.cbsdbase.activity

import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import com.aspose.words.Document
import com.aspose.words.SaveFormat
import com.yjhs.cbsd.base.BaseActivity
import com.yjhs.cbsdbase.R
import java.io.File

/**
 * author: Administrator
 * date: 2019-11-15
 * desc:
 */
class PubActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_detail
    }

    override fun initData() {
    }

    override fun initView() {
        //D:\ganhuo\CbsdBase\app\src\main\assets\Proxifier使用手册.docx
    }

    override fun start() {
    }

    override fun init(savedInstanceState: Bundle?) {
//        val inputFile = File(getExternalFilesDir("Proxifier使用手册.docx"))
//        val document = Document(getExternalFilesDir("test.docx")?.absolutePath)
//        val outputFile = File(getExternalFilesDir(null)?.absolutePath+"/hh.pdf")
//        Thread(Runnable {
//            document.save(outputFile.absolutePath, SaveFormat.PDF)
//        }).start()

    }
}