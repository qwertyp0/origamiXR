package com.example.origamixr

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class InstructionStartActivity : AppCompatActivity() {
    private var title : String? = null
    private var info : String? = null
    private var design : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get values
        title = intent?.extras?.getString("title")
        info = intent?.extras?.getString("info")
        design = intent?.extras?.getInt("design")

        setContentView(R.layout.instruction_start_activity)

        // set image
        val imageView = this.findViewById<ImageView>(R.id.start_activity_image_view)
        imageView.setImageResource(design!!)

        // extract values from info
        val regex = ("(\\d+)").toRegex()
        val matches = regex.findAll(info!!, 0).toList()
        val steps = matches[0].value.toInt()
        val minutes = matches[1].value.toInt()

        // replace text with values
        findViewById<TextView>(R.id.start_activity_title).text = title!!
        findViewById<TextView>(R.id.start_activity_steps).text = "Time required: $minutes"
        findViewById<TextView>(R.id.start_activity_time).text = "Number of steps: $steps"

        // on click to start instructions ... uses extracted values instead of passed-in info string
        findViewById<MaterialButton>(R.id.start_activity_start_button).setOnClickListener {
            val intent = Intent(this, InstructionActivity::class.java)
            intent.putExtra("title", title!!)
            intent.putExtra("design", design!!)
            intent.putExtra("steps", steps)
            intent.putExtra("minutes", minutes)
            startActivity(intent)
            this.finish()
        }

        // on click to go back
        findViewById<MaterialButton>(R.id.start_activity_cancel_button).setOnClickListener {
            this.finish()
        }
    }
}