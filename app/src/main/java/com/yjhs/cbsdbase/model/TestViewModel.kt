package com.yjhs.cbsdbase.model

import androidx.lifecycle.MutableLiveData
import com.yjhs.cbsd.base.BaseViewModel
import com.yjhs.cbsdbase.bean.InforModel
import com.yjhs.cbsdbase.RetrofitClient

/**
 *
 * @author  Lai
 *
 * @time 2019/10/14 16:02
 * @describe describe
 *
 */
class TestViewModel : BaseViewModel() {

    val infoBean = MutableLiveData<List<InforModel>>()




    fun queryData(req: HashMap<String,Any>) {
        request({
            RetrofitClient.service.commonQuery(req)
        }, {
            infoBean.value = it.inforModel
        })
    }

//    fun getHistory() {
//        viewModelScope.launch {
//            mHistoryBean.value = withContext(Dispatchers.IO) {
//                return@withContext mBookDao.findHistoryRecord()
//            }
//        }
//    }



}