package com.yjhs.cbsdbase.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.yjhs.cbsd.utils.Tools

/**
 * Created by 二哈 on 2019/11/7.
 */
data class CommonListResp(
    val iTotalpages: Int,
    val iTotalrecords: Int,
    val inforModel: List<InforModel>,
    val strImgRootPath: String
)

data class InforModel(
    val icollectiontimes: Int,
    val iforwarding: Int,
    val ihitcount: Int,
    val iisdelete: Int,
    val irank: Int,
    val isModel: String,
    val isOutsideHtml: String,
    val isQuote: String,
    val isRead: String,
    val isUrl: String,
    val isVideo: String,
    val ishow: Int,
    val quotePrice: Double,
    val showcompanyaddress: String,
    val showcompanyname: String,
    val showcompanytelphone: String,
    val shownickname: String,
    val showphone: String,
    val strCompanyName: String,
    val strUrl: String,
    val strVideo: String,
    val strbrandid: String,
    val strbrandtypemodel: String,
    val strcarmodelid: String,
    val strcarspecid: String,
    val strcolor: String,
    val strcolumnid: String,
    val strcolumnname: String,
    val strcreatetime: String,
    val strdeletetime: String,
    val strimage: String?,
    val strinforid: String,
    val strremark: String,
    val strstate: String,
    val strtitle: String,
    val strtype: String,
    val strupdatetime: String,
    val struserinfoid: String,
    val strusername: String,
    val videoImg: String
): MultiItemEntity {
    companion object {
        const val VIDEO = 1
        const val TEXT = 2
        const val IMG = 3
    }
    override fun getItemType(): Int {
        val isVideo = Tools.safeString(isVideo)
        if (isVideo.equals("1")){
            return VIDEO
        }else{
            if (strimage == null || "".equals(strimage)) {
                return TEXT;
            } else {
                return IMG;
            }
        }
    }
}