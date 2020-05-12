package com.bolaware.walk_through_guider

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.bolaware.corehighlighterview.CircleInRectangleView
import kotlinx.android.synthetic.main.controls_layout.view.*
import kotlinx.android.synthetic.main.walkthough_guide_layout.view.*
import kotlin.math.roundToInt


class WalkThroughDialog @JvmOverloads constructor(
    private val activity: Activity,
    private val anchorViews: List<AnchorView>
) : AlertDialog(activity, R.style.Theme_AppCompat_DayNight_NoActionBar) {

    private lateinit var dialogRootView: View

    private var currentViewIndex = 0

    @ColorRes private var aroundColor: Int = R.color.transparentColor
    @ColorRes private var highlighterColor : Int = android.R.color.white
    @ColorRes private var contentTintColor: Int = android.R.color.white
    @ColorRes private var stepsPageIndicatorTextColor: Int = android.R.color.darker_gray


    fun setAroundColor(@ColorRes aroundColor: Int) : WalkThroughDialog{
        this.aroundColor = aroundColor
        return this
    }


    fun setHighLighterColor(@ColorRes highlighterColor: Int) : WalkThroughDialog{
        this.highlighterColor = highlighterColor
        return this
    }

    fun setContentTintColor(@ColorRes contentTintColor: Int) : WalkThroughDialog{
        this.contentTintColor = contentTintColor
        return this
    }

    fun setStepsPageIndicatorTextColor(@ColorRes stepsPageIndicatorTextColor: Int) : WalkThroughDialog{
        this.stepsPageIndicatorTextColor = stepsPageIndicatorTextColor
        return this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialogRootView = layoutInflater.inflate(R.layout.walkthough_guide_layout, null)
        setContentView(dialogRootView)

        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        anchorViews.firstOrNull()?.anchorView?.highlightAView()
    }

    private fun View.highlightAView() {
        val circleRectView = CircleInRectangleView(
            activity, this, aroundColor, highlighterColor
        )

        dialogRootView.highLighterLay.addView(
            circleRectView,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )

        circleRectView.animateEnlarge()

        circleRectView.post {
            addControls(circleRectView)

            setUpOnClickListeners()
        }
    }

    private fun addControls(circleRectView: CircleInRectangleView) {
        val safeControlsLay = getSafeControlRegion(circleRectView)

        val controlsLay = ControlsLay(activity).apply {
            id = View.generateViewId()
            setDescriptionText(anchorViews[currentViewIndex].description)
            setPageText("${currentViewIndex + 1} of ${anchorViews.size}")
            setCloseListener { this@WalkThroughDialog.dismiss() }
            setContentColorTintMode(contentTintColor)
            stepsPageIndicatorTextColor(stepsPageIndicatorTextColor)
        }

        safeControlsLay.addView(
            controlsLay, ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
        )

        val set = ConstraintSet()
        set.clone(safeControlsLay)
        set.connect(controlsLay.id, ConstraintSet.TOP, safeControlsLay.id, ConstraintSet.TOP)
        set.connect(controlsLay.id, ConstraintSet.BOTTOM, safeControlsLay.id, ConstraintSet.BOTTOM)
        set.applyTo(safeControlsLay)
    }


    private fun getSafeControlRegion(circleInRectangleView: CircleInRectangleView): ConstraintLayout {
        val topRegionRect = Rect()
        val middleRegionRect = Rect()
        val bottomRegionRect = Rect()

        dialogRootView.topGroundLay.getGlobalVisibleRect(topRegionRect)
        dialogRootView.middleGroundLay.getGlobalVisibleRect(middleRegionRect)
        dialogRootView.bottomGroundLay.getGlobalVisibleRect(bottomRegionRect)

        val anchorCoordinates = circleInRectangleView.getCentre()

        dialogRootView.topGroundLay.removeAllViews()
        dialogRootView.bottomGroundLay.removeAllViews()
        dialogRootView.middleGroundLay.removeAllViews()

        return if (topRegionRect.contains(
                anchorCoordinates[0].roundToInt(),
                anchorCoordinates[1].roundToInt()
            )
        ) {
            dialogRootView.middleGroundLay
        } else if (bottomRegionRect.contains(
                anchorCoordinates[0].roundToInt(),
                anchorCoordinates[1].roundToInt()
            )
        ) {
            dialogRootView.middleGroundLay
        } else {
            dialogRootView.bottomGroundLay
        }
    }


    private fun setUpOnClickListeners() {

        dialogRootView.rightIV.setOnClickListener {
            currentViewIndex++
            dialogRootView.highLighterLay.removeAllViews()
            anchorViews[currentViewIndex].anchorView.highlightAView()
        }

        dialogRootView.leftIV.setOnClickListener {
            currentViewIndex--
            dialogRootView.highLighterLay.removeAllViews()
            anchorViews[currentViewIndex].anchorView.highlightAView()
        }

        dialogRootView.leftIV.enable(currentViewIndex != 0)

        dialogRootView.rightIV.enable(currentViewIndex < anchorViews.size - 1)
    }

    private fun View.enable(enable: Boolean) {
        if (enable) {
            this.isEnabled = true
            this.visibility = View.VISIBLE
        } else {
            this.isEnabled = false
            this.visibility = View.INVISIBLE
        }
    }
}