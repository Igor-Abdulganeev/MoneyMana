package ru.gorinih.moneymana.ui

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.ViewStructure
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.transition.Visibility
import ru.gorinih.moneymana.R
import ru.gorinih.moneymana.databinding.ActivityMainBinding
import ru.gorinih.moneymana.ui.camera.CameraFragment
import ru.gorinih.moneymana.ui.categories.ManaCategoriesFragment

class MainActivity : AppCompatActivity(), AccessPermissionsActivity, NavigationActivity {

    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragments_container, ManaCategoriesFragment.newInstance(),
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

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        private const val CATEGORIES_FRAGMENT = "ManaCategoriesFragment"
        private const val CAMERA_FRAGMENT = "CameraFragment"
    }

}