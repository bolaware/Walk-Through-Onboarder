package com.bolaware.walkthroughguider

import android.os.Bundle
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bolaware.walk_through_guider.AnchorView
import com.bolaware.walk_through_guider.WalkThroughDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ConstraintLayout>(R.id.rootView).viewTreeObserver.addOnGlobalLayoutListener(
            object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    launchWalkThroughDialog()

                    findViewById<ConstraintLayout>(R.id.rootView).viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })


        findViewById<Button>(R.id.fourthView).setOnClickListener {
            launchWalkThroughDialog()
        }
    }

    private fun launchWalkThroughDialog(){
        WalkThroughDialog(
            this@MainActivity,
            listOf(
                AnchorView(
                    findViewById<TextView>(R.id.firstView),
                    "This is our first view and its an image view which has an image we called launcher"
                ),
                AnchorView(
                    findViewById<TextView>(R.id.secondView),
                    "This is our second view which is a textview and it has an orange colour"
                ),
                AnchorView(
                    findViewById<EditText>(R.id.thirdView),
                    "This is our third view which is an edittext, do you like it, we like it too"
                ),
                AnchorView(
                    findViewById<Button>(R.id.fourthView),
                    "Fourth view is a button, which launches our walkthrough dialog"
                ),
                AnchorView(
                    findViewById<TextView>(R.id.fifthView),
                    "Fifth view is a great view that we are so excited about"
                )
            )
        ).setAroundColor(R.color.transparentColor) //advisable to make this give color some transarency
            .setContentTintColor(android.R.color.white)
            .setHighLighterColor(android.R.color.white)
            .setStepsPageIndicatorTextColor(android.R.color.darker_gray)
            .show()

    }


}
