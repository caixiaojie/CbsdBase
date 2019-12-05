package com.yjhs.cbsdbase.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.yjhs.cbsd.base.BaseActivity
import com.yjhs.cbsdbase.R

/**
 * author: Administrator
 * date: 2019-11-13
 * desc:
 */
class SimpleActivity : BaseActivity(), View.OnClickListener {
    private var btn_activity: Button? = null
    private var btn_fragment: Button? = null
    override fun getLayout(): Int {
        return R.layout.activity_other
    }

    override fun initData() {

    }

    override fun initView() {
        btn_activity = findViewById(R.id.btn_activity)
        btn_fragment = findViewById(R.id.btn_fragment)
        btn_activity!!.setOnClickListener(this)
        btn_fragment!!.setOnClickListener(this)
    }

    override fun start() {

    }

    override fun init(savedInstanceState: Bundle?) {}

    override fun onClick(view: View) {
        val intent: Intent
        when (view.id) {
            R.id.btn_activity -> {
                intent = Intent(this@SimpleActivity, PhotoMainActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_fragment -> {
                intent = Intent(this@SimpleActivity, PhotoFragmentActivity::class.java)
                startActivity(intent)
            }
            else -> {
            }
        }
    }
}
