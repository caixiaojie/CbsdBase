package com.yjhs.cbsdbase

import android.content.Context
import android.widget.ImageView
import com.yjhs.cbsd.utils.GlideUtils
import com.youth.banner.loader.ImageLoader

/**
 * author: Administrator
 * date: 2019-11-20
 * desc:
 */
class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        GlideUtils.loadImage(context!!, path,imageView!!,0f)
    }
}