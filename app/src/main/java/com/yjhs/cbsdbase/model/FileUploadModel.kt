package com.yjhs.cbsdbase.model

import androidx.lifecycle.MutableLiveData
import com.yjhs.cbsd.mvp.BaseViewModel
import com.yjhs.cbsd.utils.FormFile
import com.yjhs.cbsdbase.bean.ComparePriceResp
import com.yjhs.cbsdbase.RetrofitClient
import com.yjhs.cbsdbase.bean.FileUploadResp
import com.yjhs.cbsdbase.bean.ImgsUploadResp
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.HashMap

/**
 *
 * @author  Lai
 *
 * @time 2019/10/14 16:02
 * @describe describe
 *
 */
class FileUploadModel : BaseViewModel() {

    val imgdata = MutableLiveData<ImgsUploadResp>()
    val filedata = MutableLiveData<FileUploadResp>()




    fun fileUpload(formFiles: MutableList<FormFile>,temp: Int) {
        val parts = ArrayList<MultipartBody.Part>()
        if (formFiles != null && formFiles.size > 0) {
            for (i in formFiles.indices) {
                val formFile = formFiles[i]
                val body = RequestBody.create(MediaType.parse(formFile.contentType), formFile.file)
                val part = MultipartBody.Part.createFormData("file", formFile.filename, body)
                parts.add(part)
            }
            when(temp){
                0->{
                    request({
                        RetrofitClient.service.imagesUpload(parts)
                    }, {
                        imgdata.value = it
                    })
                }
                1->{
                    request({
                        RetrofitClient.service.fileUpload(parts)
                    }, {
                        filedata.value = it
                    })
                }
            }
        }

    }






}