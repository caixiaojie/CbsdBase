package com.yjhs.cbsdbase.view

import android.os.Bundle
import com.yjhs.cbsd.base.BaseActivity
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.view.widget.ArcSeekBar
import kotlinx.android.synthetic.main.activity_arc_seek_bar.*


/**
 * author: Administrator
 * date: 2019-11-29
 * desc:
 */
class ArcSeekBarActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_arc_seek_bar
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun start() {
    }

    override fun init(savedInstanceState: Bundle?) {
        mArcSeekBar.setArcColors(R.array.arc_colors_custom)

        mArcSeekBar.setMaxValue(200)
        mArcSeekBar.setMinValue(50)

        setEngry(mArcSeekBar.getProgress())

        mArcSeekBar.setOnProgressChangeListener(object : ArcSeekBar.OnProgressChangeListener {
            override fun onProgressChanged(seekBar: ArcSeekBar, progress: Int, isUser: Boolean) {
                setEngry(seekBar.progress)
            }

            override fun onStartTrackingTouch(seekBar: ArcSeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: ArcSeekBar) {
                setEngry(seekBar.progress)
            }
        })

        btn_0.setOnClickListener {
            mArcSeekBar.progress = 0
        }

        btn_90.setOnClickListener {
            mArcSeekBar.progress = 90
        }

    }

    private fun setEngry(progress: Int) {
        mProgressText.text = "POWER $progress %"
    }
}