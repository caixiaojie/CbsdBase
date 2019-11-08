package com.yjhs.cbsd.ui.widget.dialog

import android.content.Context
import android.view.View
import com.yjhs.cbsd.R
import com.yjhs.cbsd.widget.dialog.BaseDialog
import kotlinx.android.synthetic.main.busydialog.*
import android.animation.ValueAnimator







/**
 *
 * @author  Lai
 *
 * @time 2019/9/29 17:27
 * @describe describe
 *
 */
class BusyView(context: Context) : BaseDialog(context, R.style.LoadingDialogStyle) {
    private val dotText = arrayOf(" .", " .. ", " ...")
    private var valueAnimator: ValueAnimator? = null
    private val loadingTxt = "加载中"

    override fun getLayout(): Int {
        return R.layout.busydialog
    }

    override fun init(view: View) {
        message.text = "加载中"
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofInt(0, 3).setDuration(1000)
            valueAnimator?.repeatCount = ValueAnimator.INFINITE
            valueAnimator?.addUpdateListener { animation ->
                val i = animation.animatedValue as Int
                message.text = loadingTxt + dotText[i % dotText.size]
            }
        }
        valueAnimator?.start()
    }

    override fun dismiss() {
        super.dismiss()
    }


}