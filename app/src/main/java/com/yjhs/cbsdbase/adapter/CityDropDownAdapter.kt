package com.yjhs.cbsdbase.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yjhs.cbsd.utils.ResUtils
import com.yjhs.cbsdbase.R

/**
 * Created by 二哈 on 2019/12/13.
 */
class CityDropDownAdapter(data: ArrayList<String>) :
    BaseQuickAdapter<String,BaseViewHolder>(R.layout.adapter_drop_down_list_item,data) {
    private var mSelectPosition = -1
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.text,item)
        if (mSelectPosition !== -1) {
            if (mSelectPosition === helper?.layoutPosition) {
                helper?.getView<TextView>(R.id.text).isSelected = true
                helper?.getView<TextView>(R.id.text).setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    ResUtils.getVectorDrawable(
                        mContext,
                        R.drawable.ic_checked_right
                    ),
                    null
                )
            } else {
                helper?.getView<TextView>(R.id.text)?.isSelected = false
                helper?.getView<TextView>(R.id.text)?.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }
        }
    }

    fun setSelectPosition(selectPosition: Int): CityDropDownAdapter {
        mSelectPosition = selectPosition
        notifyDataSetChanged()
        return this
    }
}