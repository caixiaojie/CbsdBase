package com.yjhs.cbsdbase.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yjhs.cbsd.utils.DisplayUtils
import com.yjhs.cbsd.utils.GlideUtils
import com.yjhs.cbsdbase.ApiService
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.bean.InforModel

/**
 *
 * @author  Lai
 *
 * @time 2019/10/1 23:20
 * @describe describe
 *
 */
class HomeAdapter(data: List<InforModel>) : BaseMultiItemQuickAdapter<InforModel, BaseViewHolder>(data) {

    init {
        addItemType(InforModel.TEXT, R.layout.text_view)
        addItemType(InforModel.IMG, R.layout.image_view)
        addItemType(InforModel.VIDEO, R.layout.video_view)
    }

    override fun convert(helper: BaseViewHolder?, item: InforModel?) {
        helper?.apply {
            item?.also {
                when (it.itemType) {
                    InforModel.TEXT -> {
                        setText(R.id.txt_title, it.strtitle)
                    }
                    InforModel.IMG -> {
                        setText(R.id.txt_title, it.strtitle)
                        GlideUtils.loadImage(mContext, ApiService.ROOT_PATH +it.strimage, getView(
                            R.id.img_car
                        ),
                            4f,
                            DisplayUtils.dp2px(125f),DisplayUtils.dp2px(80f))
                    }
                    InforModel.VIDEO -> {
                        setText(R.id.txt_title, it.strtitle)
                        GlideUtils.loadImage(mContext, it.strVideo, getView(R.id.img_car),
                            4f,DisplayUtils.dp2px(125f),DisplayUtils.dp2px(80f))
                    }
                }
            }
        }
    }

}