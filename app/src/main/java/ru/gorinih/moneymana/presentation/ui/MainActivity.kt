package ru.gorinih.moneymana.presentation.ui

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.HapticFeedbackConstants
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ru.gorinih.moneymana.R
import ru.gorinih.moneymana.databinding.ActivityMainBinding
import ru.gorinih.moneymana.presentation.AccessPermissionsActivity
import ru.gorinih.moneymana.presentation.NavigationActivity
import ru.gorinih.moneymana.presentation.ui.camera.CameraFragment
import ru.gorinih.moneymana.presentation.ui.categories.CategoriesFragment

class MainActivity : AppCompatActivity(), AccessPermissionsActivity, NavigationActivity {

    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    private lateinit var vibro: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        vibro = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragments_container, CategoriesFragment.newInstance(),
                    CATEGORIES_FRAGMENT
                )
                .commit()
        }

        binding.cameraButton.setOnClickListener {
            binding.cameraButton.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            val foundFragments = supportFragmentManager.fragments
            if (foundFragments.count() > 0) {
                val fragment = foundFragments[0]
                if (fragment !is CameraFragment) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) requestAllPermissions()
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.fragments_container,
                            CameraFragment.newInstance(),
                            CAMERA_FRAGMENT
                        )
                        .addToBackStack(null)
                        .commit()
                }
            } else {
                throw(Throwable("Unknown any fragment"))
            }
        }

        //  val mDb : ManaDatabase = ManaDatabase.getInstance(this)

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun allPermissionsGranted(): Boolean =
        REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                this,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestAllPermissions() {
        if (!allPermissionsGranted()) {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    override fun setBarVisibility(visible: Int) {
        binding.apply {
            bottomAppBar.visibility = visible
            cameraButton.visibility = visible
        }
    }

    override fun startVibration() {
        if (vibro.hasVibrator()) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    vibro.vibrate(400)
                } else {
                    vibro.vibrate(
                        VibrationEffect.createOneShot(
                            400,
                            VibrationEffect.CONTENTS_FILE_DESCRIPTOR
                        )
                    )
                }
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val CATEGORIES_FRAGMENT = "ManaCategoriesFragment"
        private const val CAMERA_FRAGMENT = "CameraFragment"
    }

}