package com.yjhs.cbsdbase.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import com.yjhs.cbsdbase.view.model.PieData
import android.graphics.RectF
import android.util.Log







/**
 * author: Administrator
 * date: 2019-11-28
 * desc:
 */
class PieView @JvmOverloads constructor(context: Context,attrs: AttributeSet? = null,defStyleAttr: Int = 0) :
    View(context,attrs,defStyleAttr){
    // 颜色表
    private val mColors = intArrayOf(
        0xFFCCFF00.toInt(),
        0xFF6495ED.toInt(),
        0xFFE32636.toInt(),
        0xFF800000.toInt(),
        0xFF808000.toInt(),
        0xFFFF8C69.toInt(),
        0xFF808080.toInt(),
        0xFFE6B800.toInt(),
        0xFF7CFC00.toInt()
    )
    // 饼状图初始绘制角度w
    private var mStartAngle = 0f
    // 数据
    private lateinit var mData: ArrayList<PieData>
    // 宽高
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    // 画笔
    private val mPaint = Paint()

    // 文字色块部分
    private val mStartPoint = PointF(20f, 20f)
    private val mCurrentPoint = PointF(mStartPoint.x, mStartPoint.y)
    private val mColorRectSideLength = 20f
    private val mTextInterval = 10f
    private val mRowMaxLength: Float = 0f

    init {
        mPaint.style = Paint.Style.FILL;
        mPaint.isAntiAlias = true;
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (null == mData)
            return
        var currentStartAngle = mStartAngle                      // 当前起始角度
        canvas?.translate((mWidth / 2).toFloat(), (mHeight / 2).toFloat())                  // 将画布坐标原点移动到中心位置
        val r = (Math.min(mWidth, mHeight) / 2 * 0.8).toFloat()    // 饼状图半径
        val rect = RectF(-r, -r, r, r)                       // 饼状图绘制区域

        for (i in mData.indices) {
            val pie = mData[i]
            mPaint.color = pie.color!!
            canvas?.drawArc(rect, currentStartAngle, pie.angle!!, true, mPaint)
            currentStartAngle += pie.angle!!

            canvas?.save()
            canvas?.translate((-mWidth / 2).toFloat(), (-mHeight / 2).toFloat())
            val colorRect = RectF(
                mCurrentPoint.x,
                mCurrentPoint.y,
                mCurrentPoint.x + mColorRectSideLength,
                mCurrentPoint.y + mColorRectSideLength
            )
            canvas?.restore()
        }
    }

    // 设置起始角度
    fun setStartAngle(mStartAngle: Int) {
        this.mStartAngle = mStartAngle.toFloat()
        invalidate()   // 刷新
    }

    // 设置数据
    fun setData(mData: ArrayList<PieData>) {
        this.mData = mData
        initData(mData)
        invalidate()   // 刷新
    }

    // 初始化数据
    private fun initData(mData: ArrayList<PieData>?) {
        if (null == mData || mData.size === 0)
        // 数据有问题 直接返回
            return

        var sumValue = 0f
        for (i in 0 until mData.size) {
            val pie = mData[i]

            sumValue += pie.value       //计算数值和

            val j = i % mColors.size       //设置颜色
            pie.color = mColors[j]
        }

        var sumAngle = 0f
        for (i in 0 until mData.size) {
            val pie = mData[i]

            val percentage = pie.value / sumValue   // 百分比
            val angle = percentage * 360                 // 对应的角度

            pie.percentage = percentage                  // 记录百分比
            pie.angle = angle                            // 记录角度大小
            sumAngle += angle

            Log.i("angle", "" + pie.angle!!)
        }
    }

}