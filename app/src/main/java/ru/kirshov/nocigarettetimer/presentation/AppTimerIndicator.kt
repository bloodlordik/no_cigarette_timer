package ru.kirshov.nocigarettetimer.presentation

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import ru.kirshov.nocigarettetimer.R
import kotlin.properties.Delegates


class AppTimerIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
):View(context, attrs, defStyleAttr){
    private var backColor by Delegates.notNull<Int>()
    init {
            if (attrs!=null){
                initAttribute(attrs, defStyleAttr)
            }
    }
    private fun initAttribute(attrs: AttributeSet?, defStyleAttr: Int){
         context.obtainStyledAttributes(attrs,R.styleable.AppTimerIndicator)

    }

}