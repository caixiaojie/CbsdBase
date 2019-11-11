package com.yjhs.cbsdbase.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.orhanobut.logger.Logger
import com.qingmei2.rximagepicker.core.RxImagePicker
import com.qingmei2.rximagepicker.entity.Result
import com.qingmei2.rximagepicker_extension.MimeType
import com.qingmei2.rximagepicker_extension_zhihu.ZhihuConfigurationBuilder
import com.qingmei2.rximagepicker_extension_zhihu.ui.ZhihuImagePickerFragment
import com.yjhs.cbsd.mvp.BaseActivity
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.ZhihuImagePicker
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_zhihu.*
import pub.devrel.easypermissions.EasyPermissions

/**
 * Created by 二哈 on 2019/11/11.
 */
class ZhihuActivity : BaseActivity() {
    companion object {
        private const val REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CAMERA = 99
        private const val REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_GALLERY_NORMAL = 100
        private const val REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_GALLERY_Dracula = 101
    }
    private lateinit var rxImagePicker: ZhihuImagePicker
    override fun getLayout(): Int {
        return R.layout.activity_zhihu
    }

    override fun initData() {
        initRxImagePicker()
    }

    override fun initView() {
        fabPickCamera.setOnClickListener {
            val perms = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
            checkPermission(perms,REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CAMERA)
        }
        fabPickGalleryNormal.setOnClickListener {
            val perms = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            checkPermission(perms,REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_GALLERY_NORMAL)
        }
        fabPickGalleryDracula.setOnClickListener {
            val perms = arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            checkPermission(perms,REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_GALLERY_Dracula)
        }
    }

    override fun start() {
    }

    override fun init(savedInstanceState: Bundle?) {
    }

    private fun checkPermission(perms: Array<String>,requestCode: Int){
        EasyPermissions.requestPermissions(this, "应用需要以下权限，请允许", requestCode, *perms)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        when(requestCode){
            REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_CAMERA ->{
                if (perms.isNotEmpty()) {
                    if (perms.contains(Manifest.permission.READ_EXTERNAL_STORAGE)&&
                        perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)&&
                        perms.contains(Manifest.permission.CAMERA)){
                        openCamera()
                    }
                }
            }
            REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_GALLERY_NORMAL ->{
                if (perms.isNotEmpty()) {
                    if (perms.contains(Manifest.permission.READ_EXTERNAL_STORAGE)&&
                        perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                        openGalleryAsNormal()
                    }
                }
            }
            REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION_GALLERY_Dracula ->{
                if (perms.isNotEmpty()) {
                    if (perms.contains(Manifest.permission.READ_EXTERNAL_STORAGE)&&
                        perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                        openGalleryAsDracula()
                    }
                }
            }
        }
    }

    private fun initRxImagePicker() {
        rxImagePicker = RxImagePicker
            .create(ZhihuImagePicker::class.java)
    }

    /**
     * open Camera.
     */
    private fun openCamera() {
        rxImagePicker.openCamera(this)
            .subscribe(fetchUriObserver())
    }

    /**
     * Open Gallery as Zhihu normal theme.
     */
    private fun openGalleryAsNormal() {
        rxImagePicker.openGalleryAsNormal(this,
            ZhihuConfigurationBuilder(MimeType.ofImage(), true)
                .capture(true)
                .maxSelectable(9)
                .countable(true)
                .spanCount(4)
                .theme(R.style.Zhihu_Normal)
                .build())
            .subscribe(fetchUriObserver())
    }

    /**
     * Open Gallery as Zhihu dracula theme.
     */
    private fun openGalleryAsDracula() {
        rxImagePicker.openGalleryAsDracula(this,
            ZhihuConfigurationBuilder(MimeType.ofImage(), false)
                .capture(true)
                .spanCount(3)
                .maxSelectable(1)
                .theme(R.style.Zhihu_Dracula)
                .build())
            .subscribe(fetchUriObserver())
    }

    private fun fetchUriObserver(): Observer<Result> = object : Observer<Result> {

        override fun onSubscribe(d: Disposable) {

        }

        override fun onNext(result: Result) {
            // Usage
            // val isGif: Boolean
            //  get() = if (mimeType == null) false else mimeType == MimeType.GIF.toString()
            // val isImage: Boolean
            //  get() = if (mimeType == null) false else mimeType == MimeType.JPEG.toString()
            //        || mimeType == MimeType.PNG.toString()
            //        || mimeType == MimeType.GIF.toString()
            //        || mimeType == MimeType.BMP.toString()
            //        || mimeType == MimeType.WEBP.toString()
            val mimeType = result.getStringExtra(ZhihuImagePickerFragment.EXTRA_OPTIONAL_MIME_TYPE, "")
            Log.d("tag", "mime types: $mimeType")

            Glide.with(this@ZhihuActivity)
                .load(result.uri)
                .into(imageView)
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            Toast.makeText(this@ZhihuActivity, "Failed: $e", Toast.LENGTH_SHORT).show()
        }

        override fun onComplete() {

        }
    }
}