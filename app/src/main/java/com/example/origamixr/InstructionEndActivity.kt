package com.example.origamixr

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class InstructionEndActivity : AppCompatActivity() {
    private var title : String? = null
    private var steps : Int? = null
    private var minutes : Int? = null
    private var design : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get values
        title = intent?.extras?.getString("title")
//        minutes = intent?.extras?.getInt("minutes")
//        steps = intent?.extras?.getInt("steps")
        design = intent?.extras?.getInt("design")

        setContentView(R.layout.instruction_start_activity)

        // set image
        val imageView = this.findViewById<ImageView>(R.id.start_activity_image_view)
        imageView.setImageResource(design!!)

        // replace text with values
        findViewById<TextView>(R.id.start_activity_title).text = title!!
        findViewById<TextView>(R.id.start_activity_steps).text = "Done!"
        findViewById<TextView>(R.id.start_activity_time).text = "We hope you had fun!"

        // set up first button
        findViewById<MaterialButton>(R.id.start_activity_start_button).apply {
            setText("Post it")
            setOnClickListener {
                Toast.makeText(context, "Posting feature not implemented", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // get rid of cancel button
        findViewById<MaterialButton>(R.id.start_activity_cancel_button).visibility = View.GONE

        // set up last button
        findViewById<MaterialButton>(R.id.start_activity_extra_button).apply {
            visibility = View.VISIBLE
            setText("Home")
            setOnClickListener {
                finish()
            }
        }

    }
}