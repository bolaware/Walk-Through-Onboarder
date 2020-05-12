package com.bolaware.corehighlighterview

import android.animation.ObjectAnimator
import android.app.Activity
import android.graphics.*
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat


class CircleInRectangleView : View{
    private var  circlePaint: Paint = Paint()
    private var  rectPaint: Paint  = Paint()

    var radius = 50.0f
    private var circleCentreX = 0.0f
    private var circleCentreY = 0.0F

    constructor(activity: Activity,
                anchorView: View,
                @ColorRes aroundColor: Int = R.color.default_background_color,
                @ColorRes highLighterColor : Int = android.R.color.white) : super(activity) {

        circlePaint.color = ContextCompat.getColor(activity, highLighterColor)
        circlePaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.XOR)
        circlePaint.isAntiAlias = true

        rectPaint.color = ContextCompat.getColor(activity, aroundColor)
        rectPaint.isAntiAlias = true

        this.setLayerType(LAYER_TYPE_SOFTWARE, null)

        radius = anchorView.getRadius()
        val anchorViewCoordinates= anchorView.getAnchorViewLocationCoordinates(activity)
        circleCentreX = anchorViewCoordinates[0]
        circleCentreY = anchorViewCoordinates[1]
    }

    fun animateEnlarge(){
        ObjectAnimator.ofFloat(this, "radius", 0.0f, radius).apply {
            duration = 1000
            start()
        }
    }

    fun getCentre() : FloatArray{
        val array = FloatArray(2)
        array[0] = circleCentreX
        array[1] = circleCentreY
        return array
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val h = this.height
        val w = this.width

        //val radius = (if (h > w) w else h)/2

        canvas?.drawRect(0f, 0f, w.toFloat(), h.toFloat(), rectPaint)

        canvas?.drawCircle(circleCentreX, circleCentreY, radius, circlePaint)

        invalidate()
    }
}