package com.bolaware.walk_through_guider

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

class ControlsLay constructor(context: Context) : ConstraintLayout(context) {
    init {
        View.inflate(context, R.layout.controls_layout, this)

    }

    fun setCloseListener(action : () -> Unit){
        findViewById<ImageView>(R.id.closeIV).setOnClickListener {
            action.invoke()
        }
    }

    fun setContentColorTintMode(@ColorRes color : Int){
        findViewById<TextView>(R.id.descTV).setTextColor(
            ContextCompat.getColor(context, color)
        )
        findViewById<ImageView>(R.id.leftIV).setColorFilter(
            ContextCompat.getColor(context, color),
            android.graphics.PorterDuff.Mode.SRC_IN)
        findViewById<ImageView>(R.id.rightIV).setColorFilter(
            ContextCompat.getColor(context, color),
            android.graphics.PorterDuff.Mode.SRC_IN)
        findViewById<ImageView>(R.id.closeIV).setColorFilter(
            ContextCompat.getColor(context, color),
            android.graphics.PorterDuff.Mode.SRC_IN)
    }

    fun stepsPageIndicatorTextColor(@ColorRes color: Int){
        findViewById<TextView>(R.id.pageIndicatorTV).setTextColor(
            ContextCompat.getColor(context, color)
        )
    }

    fun setDescriptionText(text : String){
        findViewById<TextView>(R.id.descTV).text = text
    }

    fun setPageText(text: String){
        findViewById<TextView>(R.id.pageIndicatorTV).text = text
    }

}