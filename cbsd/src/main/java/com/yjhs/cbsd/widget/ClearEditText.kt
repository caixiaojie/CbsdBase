package com.yjhs.cbsd.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.yjhs.cbsd.R

class ClearEditText @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = android.R.attr.editTextStyle
) :
        AppCompatEditText(context, attrs, defStyle), View.OnFocusChangeListener, TextWatcher {
    private var mClearDrawable: Drawable? = null

    private var hasFoucs: Boolean = false

    init {
        init()
    }

    private fun init() {
        mClearDrawable = compoundDrawables[2]
        if (mClearDrawable == null) {
            mClearDrawable = ContextCompat.getDrawable(context, R.drawable.delete)
        }

        mClearDrawable?.setBounds(0, 0, mClearDrawable!!.intrinsicWidth - 10,
            mClearDrawable?.intrinsicHeight?.minus(10)!!
        )
        setClearIconVisible(false)
        onFocusChangeListener = this
        addTextChangedListener(this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (compoundDrawables[2] != null) {

                val touchable = event.x > width - totalPaddingRight && event.x < width - paddingRight

                if (touchable) {
                    this.setText("")
                }
            }
        }

        return super.onTouchEvent(event)
    }

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        this.hasFoucs = hasFocus
        if (hasFocus) {
            setClearIconVisible(text!!.isNotEmpty())
        } else {
            setClearIconVisible(false)
        }
    }

    private fun setClearIconVisible(visible: Boolean) {
        val right = if (visible) mClearDrawable else null
        setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], right, compoundDrawables[3])
    }

    override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        if (hasFoucs) {
            setClearIconVisible(s.isNotEmpty())
        }
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun afterTextChanged(s: Editable) {

    }

}
