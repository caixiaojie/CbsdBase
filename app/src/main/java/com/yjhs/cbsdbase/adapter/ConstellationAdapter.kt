package com.yjhs.cbsdbase.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yjhs.cbsd.utils.ResUtils
import com.yjhs.cbsdbase.R

/**
 * Created by 二哈 on 2019/12/13.
 */
class ConstellationAdapter(data: ArrayList<String>) :
    BaseQuickAdapter<String,BaseViewHolder>(R.layout.adapter_drop_down_constellation,data) {
    private var mSelectPosition = -1
    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.text,item)
        if (mSelectPosition !== -1) {
            if (mSelectPosition === helper?.layoutPosition) {
                helper?.getView<TextView>(R.id.text).isSelected = true
            } else {
                helper?.getView<TextView>(R.id.text)?.isSelected = false
            }
        }
    }

    fun setSelectPosition(selectPosition: Int): ConstellationAdapter {
        mSelectPosition = selectPosition
        notifyDataSetChanged()
        return this
    }
    fun getSelectPosition(): Int {
        return mSelectPosition
    }

    /**
     * 获取当前列表选中项
     *
     * @return 当前列表选中项
     */
    fun getSelectItem(): String? {
        return getItem(mSelectPosition)
    }
}