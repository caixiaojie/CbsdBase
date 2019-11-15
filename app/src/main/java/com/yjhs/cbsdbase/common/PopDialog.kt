package com.yjhs.cbsd.ui.widget.dialog

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.yjhs.cbsd.utils.Tools
import com.yjhs.cbsd.widget.dialog.BaseDialog
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.activity.DetailActivity
import com.yjhs.cbsdbase.bean.InforModel
import kotlinx.android.synthetic.main.my_pub_pop.*
import org.jetbrains.anko.startActivity


/**
 *
 * @author  Lai
 *
 * @time 2019/9/29 17:27
 * @describe describe
 *
 */
class PopDialog(context: Context) : BaseDialog(context, R.style.popDialogStyle) {
    var model: InforModel? = null

    override fun getLayout(): Int {
        return R.layout.my_pub_pop
    }

    override fun init(view: View) {
        this.setCancelable(true)
        this.setCanceledOnTouchOutside(true)
        ll_chakan.setOnClickListener {
            dismiss()
            context.startActivity<DetailActivity>(Pair("strinforid",model?.strinforid),Pair("strUserinfoid",model?.struserinfoid),
                Pair("strUrl",model?.strUrl), Pair("isAd",false),Pair("isUrl", Tools.safeString(model?.isUrl) == "1"),Pair("strBrandId",model?.strbrandid)
                ,Pair("strCarModelId",model?.strcarmodelid),Pair("strCarSpecId",model?.strcarspecid)
            )
        }
    }
    /**
     * 设置窗口尺寸和位置
     */
    override fun setWindowSize() {
        val dialogWindow = this.window
        dialogWindow!!.setBackgroundDrawableResource(android.R.color.transparent)//设置透明
        val lp = dialogWindow.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.BOTTOM
        lp.y = getDistanceY()
        dialogWindow.attributes = lp
        dialogWindow.setWindowAnimations(R.style.main_menu_photo_anim);
    }


}