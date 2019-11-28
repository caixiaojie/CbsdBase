package com.yjhs.cbsdbase.view

import android.os.Bundle
import com.yjhs.cbsd.base.BaseActivity
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.view.model.PieData
import kotlinx.android.synthetic.main.activity_pie.*


/**
 * author: Administrator
 * date: 2019-11-28
 * desc:
 */
class PieActivity : BaseActivity() {
    private val datas = ArrayList<PieData>()
    override fun getLayout(): Int {
        return R.layout.activity_pie
    }

    override fun initData() {
        val pieData = PieData("sloop", 60f)
        val pieData2 = PieData("sloop", 30f)
        val pieData3 = PieData("sloop", 40f)
        val pieData4 = PieData("sloop", 20f)
        val pieData5 = PieData("sloop", 20f)
        datas.add(pieData)
        datas.add(pieData2)
        datas.add(pieData3)
        datas.add(pieData4)
        datas.add(pieData5)
    }

    override fun initView() {
        pieView.setData(datas)
    }

    override fun start() {
    }

    override fun init(savedInstanceState: Bundle?) {

    }
}