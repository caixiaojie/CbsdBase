package com.yjhs.cbsdbase

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.yjhs.cbsd.mvp.BaseVMActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseVMActivity(), BaseQuickAdapter.OnItemChildClickListener,
    BaseQuickAdapter.RequestLoadMoreListener {
    override fun onLoadMoreRequested() {

    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun init(savedInstanceState: Bundle?) {
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        textMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)


        mViewModel.infoBean.observe(this, Observer {
//            ed_search.hint = it.defaultSearch
//            val adapter = HotTagAdapter(it.hotItems)
//            adapter.onItemChildClickListener = this
//            adapter.bindToRecyclerView(rv_hot_list)
//            rv_hot_list.addItemDecoration(SpaceItemDecoration(DisplayUtils.dp2px(5f)))
//            rv.layoutManager = FlowLayoutManager()
        })




        var map: HashMap<String,Any> = HashMap<String,Any>()
        map.put("pageSize",10)
        map.put("pageNumber",1)
        mViewModel.queryData(map)
    }

    private val mViewModel by lazy { createViewModel<TestViewModel>() }
    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                textMessage.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                textMessage.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                textMessage.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
