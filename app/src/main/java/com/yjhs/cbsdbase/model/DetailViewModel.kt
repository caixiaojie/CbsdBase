package com.yjhs.cbsdbase.model

import androidx.lifecycle.MutableLiveData
import com.yjhs.cbsd.mvp.BaseViewModel
import com.yjhs.cbsdbase.bean.ComparePriceResp
import com.yjhs.cbsdbase.RetrofitClient
import java.util.HashMap

/**
 *
 * @author  Lai
 *
 * @time 2019/10/14 16:02
 * @describe describe
 *
 */
class DetailViewModel : BaseViewModel() {

    val data = MutableLiveData<String>()
    val mdataList = MutableLiveData<ArrayList<ComparePriceResp>>()




    fun collectState(strinfoid: String) {
        request({
            RetrofitClient.service.collectState(strinfoid)
        }, {
            data.value = it
        })
    }

    fun priceList(req: HashMap<String, Any>) {
        request({
            RetrofitClient.service.comparePrice(req)
        }, {
            mdataList.value = it
        })
    }





}