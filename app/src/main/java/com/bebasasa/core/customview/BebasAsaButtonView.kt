package com.bebasasa.core.customview

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.bebasasa.R

class BebasAsaButtonView(
    private val ctx: Context,
    attrs: AttributeSet
) : AppCompatButton(ctx, attrs) {

    init {
        ctx.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BebasAsaButtonView,
            0,
            0
        ).apply {
            kotlin.runCatching {
                val background = getResourceId(
                    R.styleable.BebasAsaButtonView_android_background,
                    R.drawable.bg_bebasasa_button
                )
                val textColor =
                    getInt(R.styleable.BebasAsaButtonView_android_textColor, R.color.white)

                setBackgroundResource(background)
                setTextColor(ContextCompat.getColor(ctx, textColor))
            }.onSuccess { recycle() }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        gravity = Gravity.CENTER
        isAllCaps = false
    }
}