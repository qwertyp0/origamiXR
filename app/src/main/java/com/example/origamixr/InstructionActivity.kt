package com.example.origamixr

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Size
import android.graphics.Matrix
import android.view.Surface
import android.view.TextureView
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.TimeUnit
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraX
import androidx.camera.core.Preview
import androidx.camera.core.PreviewConfig
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val REQUEST_CODE_PERMISSIONS = 10
private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

class InstructionActivity : AppCompatActivity(), LifecycleOwner {
    private lateinit var viewFinder : TextureView
    private lateinit var exitButton : FloatingActionButton
    private lateinit var nextButton: FloatingActionButton
    private lateinit var backButton : FloatingActionButton
    private lateinit var stepText: TextView
    private lateinit var chip: Chip
    private var title : String? = null
    private var steps: Int? = null
    private var minutes: Int? = null
    private var design : Int? = null
    private var stepProgress : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.instruction_activity)

        // get variables passed in
        title = intent?.extras?.getString("title")
        steps = intent?.extras?.getInt("steps")
        minutes = intent?.extras?.getInt("minutes")
        design = intent?.extras?.getInt("design")

        // getting views
        viewFinder = findViewById(R.id.view_finder)
        exitButton = findViewById(R.id.instruction_exit_button)
        stepText = findViewById(R.id.instruction_steps)
        nextButton = findViewById(R.id.instruction_next_step)
        backButton = findViewById(R.id.instruction_prev_step)
        chip = findViewById(R.id.instruction_chip)

        // setting up views
        stepText.setText("$stepProgress/$steps")
        chip.text = "This is the instruction for Step $stepProgress"
        exitButton.setOnClickListener { this.finish() }
        nextButton.setOnClickListener {
            if (stepProgress < steps!!) {
                stepProgress++
                backButton.isClickable = true
                stepText.setText("$stepProgress/$steps")
                chip.text = "This is the instruction for Step $stepProgress"
            }
            else {
                val intent = Intent(this, InstructionEndActivity::class.java)
                intent.putExtra("title", title!!)
                intent.putExtra("design", design!!)
                intent.putExtra("steps", steps!!)
                intent.putExtra("minutes", minutes!!)
                startActivity(intent)
                this.finish()
            }

        }
        backButton.isClickable = false
        backButton.setOnClickListener {
            stepProgress--
            if (stepProgress == 1)
                backButton.isClickable = false
            stepText.setText("$stepProgress/$steps")
            chip.text = "This is the instruction for Step $stepProgress"
        }

        if (allPermissionsGranted()) {
            viewFinder.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        viewFinder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }
    }

    // start camera or exit after permissions are requested
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                viewFinder.post { startCamera() }
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    // checks if all permissions are granted
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    // binds cameraX to lifecycle
    private fun startCamera() {
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(640, 480))
        }.build()

        val preview = Preview(previewConfig)

        preview.setOnPreviewOutputUpdateListener {
            val parent = viewFinder.parent as ViewGroup
            parent.removeView(viewFinder)
            parent.addView(viewFinder, 0)

            viewFinder.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        CameraX.bindToLifecycle(this, preview)
    }

    // updates the viewfinder based on the rotation
    private fun updateTransform() {
        val matrix = Matrix()

        val centerX = viewFinder.width / 2f
        val centerY = viewFinder.height / 2f
        val rotationDegrees = when(viewFinder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }

        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)
        viewFinder.setTransform(matrix)
    }
}