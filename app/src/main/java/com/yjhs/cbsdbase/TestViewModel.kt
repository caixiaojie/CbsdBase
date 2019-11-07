package com.yjhs.cbsdbase

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yjhs.cbsd.mvp.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *
 * @author  Lai
 *
 * @time 2019/10/14 16:02
 * @describe describe
 *
 */
class TestViewModel : BaseViewModel() {

    val infoBean = MutableLiveData<List<CommonListResp.InforModelBean>>()




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