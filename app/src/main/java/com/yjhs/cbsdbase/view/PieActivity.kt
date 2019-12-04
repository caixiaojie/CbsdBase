package com.yjhs.cbsdbase.view

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.animation.*
import com.yjhs.cbsd.base.BaseActivity
import com.yjhs.cbsdbase.R
import com.yjhs.cbsdbase.view.model.PieData
import kotlinx.android.synthetic.main.activity_pie.*




/**
 * author: Administrator
 * date: 2019-11-28
 * desc:
 */
class PieActivity : BaseActivity() {
    private val datas = ArrayList<PieData>()
    override fun getLayout(): Int {
        return R.layout.activity_pie
    }

    override fun initData() {
        val pieData = PieData("sloop", 60f)
        val pieData2 = PieData("sloop", 30f)
        val pieData3 = PieData("sloop", 40f)
        val pieData4 = PieData("sloop", 20f)
        val pieData5 = PieData("sloop", 20f)
        datas.add(pieData)
        datas.add(pieData2)
        datas.add(pieData3)
        datas.add(pieData4)
        datas.add(pieData5)

        //变换
        datas.map {

        }

//        datas.map(PieData :: name)

        //过滤
        datas.filter {
            it.name == "sloop"
        }.map {

        }


//        datas.all {
//
//        }
//
//        datas.any {
//
//        }
//
//        datas.count {
//
//        }
//
//        datas.find {
//
//        }

//        datas.groupBy {
//            it.color
//        }

    }

    override fun initView() {
        pieView.setData(datas)
    }

    override fun start() {
    }

    override fun init(savedInstanceState: Bundle?) {
        val trans = TranslateAnimation(0f,50f,0f,50f)
        val alpha = AlphaAnimation(1f,0.5f)
        alpha.repeatCount = Animation.INFINITE
        val scale = ScaleAnimation(0f,0.5f,0f,0.5f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)
        val rotate = RotateAnimation(0f,360f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)
        rotate.repeatCount = Animation.INFINITE

        rotate.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        })


        val animationSet = AnimationSet(true)
//        animationSet.addAnimation(trans)
        animationSet.addAnimation(alpha)
//        animationSet.addAnimation(scale)
        animationSet.addAnimation(rotate)




        animationSet.interpolator = LinearInterpolator()
//        animationSet.repeatCount = Animation.INFINITE
//        animationSet.repeatMode = Animation.RESTART
        animationSet.duration = 2000

        pretty_image_round.setOnClickListener {
            pretty_image_round.startAnimation(animationSet)
        }

//        AnimationUtils.loadAnimation()
//        AnimatorInflater.loadAnimator()

    }

}