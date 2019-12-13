package com.yjhs.cbsdbase.activity

import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yjhs.cbsd.base.BaseActivity
import com.yjhs.cbsd.utils.ResUtils
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.adapter.CityDropDownAdapter
import com.yjhs.cbsdbase.adapter.ConstellationAdapter
import com.yjhs.cbsdbase.adapter.ListDropDownAdapter
import kotlinx.android.synthetic.main.activity_dropdown_menu.*
import kotlinx.android.synthetic.main.layout_drop_down_custom.*

/**
 * Created by 二哈 on 2019/12/13.
 */
class DropDownMenuActivity : BaseActivity() {
    private val mHeaders = arrayOf("城市", "年龄", "性别", "星座")
    private val mPopupViews = ArrayList<View>()
    private lateinit var mCitys: Array<String>
    private lateinit var mAges: Array<String>
    private lateinit var mSexs: Array<String>
    private lateinit var mConstellations: Array<String>

    private var mCityAdapter: CityDropDownAdapter? = null
    private var mAgeAdapter: ListDropDownAdapter? = null
    private var mSexAdapter: ListDropDownAdapter? = null
    private var mConstellationAdapter: ConstellationAdapter? = null

    override fun getLayout(): Int {
        return R.layout.activity_dropdown_menu
    }

    override fun initData() {
        mCitys = ResUtils.getStringArray(R.array.city_entry)
        mAges = ResUtils.getStringArray(R.array.age_entry)
        mSexs = ResUtils.getStringArray(R.array.sex_entry)
        mConstellations = ResUtils.getStringArray(R.array.constellation_entry)
    }

    override fun initView() {
        val cityView = RecyclerView(this)
        mCityAdapter = CityDropDownAdapter(mCitys.toList() as ArrayList<String>)
        cityView.layoutManager = LinearLayoutManager(this)
        cityView.adapter = mCityAdapter

        //init age menu
        val ageView = RecyclerView(this)
        mAgeAdapter = ListDropDownAdapter(mAges.toList() as ArrayList<String>)
        ageView.layoutManager = LinearLayoutManager(this)
        ageView.adapter = mAgeAdapter

        //init sex menu
        val sexView = RecyclerView(this)
        mSexAdapter = ListDropDownAdapter(mSexs.toList() as ArrayList<String>)
        sexView.layoutManager = LinearLayoutManager(this)
        sexView.adapter = mSexAdapter

        //init constellation
        val constellationView = layoutInflater.inflate(R.layout.layout_drop_down_custom, null)
        mConstellationAdapter = ConstellationAdapter(mConstellations.toList() as ArrayList<String>)
        val btn_ok = constellationView.findViewById<Button>(R.id.btn_ok)
        val constellation = constellationView.findViewById<RecyclerView>(R.id.constellation)
        constellation.layoutManager = GridLayoutManager(this,4)
        constellation.adapter = mConstellationAdapter
        btn_ok.setOnClickListener {
            mDropDownMenu.setTabMenuText(if (mConstellationAdapter!!.getSelectPosition() === 0) mHeaders[3]
            else mConstellationAdapter!!.getSelectItem())
            mDropDownMenu.closeMenu()
        }

        //init mPopupViews
        mPopupViews.add(cityView)
        mPopupViews.add(ageView)
        mPopupViews.add(sexView)
        mPopupViews.add(constellationView)


        //add item click event
        mCityAdapter?.setOnItemClickListener { adapter, view, position ->
            mCityAdapter?.setSelectPosition(position)
            mDropDownMenu.setTabMenuText(if (position == 0) mHeaders[0] else mCitys[position])
            mDropDownMenu.closeMenu()
        }
        mAgeAdapter?.setOnItemClickListener { adapter, view, position ->
            mAgeAdapter?.setSelectPosition(position)
            mDropDownMenu.setTabMenuText(if (position == 0) mHeaders[1] else mAges[position])
            mDropDownMenu.closeMenu()
        }

        mSexAdapter?.setOnItemClickListener { adapter, view, position ->
            mSexAdapter?.setSelectPosition(position)
            mDropDownMenu.setTabMenuText(if (position == 0) mHeaders[2] else mSexs[position])
            mDropDownMenu.closeMenu()
        }

        mConstellationAdapter?.setOnItemClickListener { adapter, view, position ->
            mConstellationAdapter?.setSelectPosition(position)
        }
        //init context view
        val contentView = TextView(this)
        contentView.layoutParams =
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        contentView.text = "内容显示区域"
        contentView.gravity = Gravity.CENTER
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)

        //init dropdownview
        mDropDownMenu.setDropDownMenu(mHeaders, mPopupViews, contentView)
    }

    override fun start() {
    }

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            handleBackPressed()
        }
        return true
    }

    private fun handleBackPressed() {
        if (mDropDownMenu.isShowing) {
            mDropDownMenu.closeMenu()
        } else {
            finish()
        }
    }
}