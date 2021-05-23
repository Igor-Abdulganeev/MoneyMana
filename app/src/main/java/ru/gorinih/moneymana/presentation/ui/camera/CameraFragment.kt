package ru.gorinih.moneymana.presentation.ui.camera

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.toRectF
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.coroutines.InternalCoroutinesApi
import ru.gorinih.moneymana.data.camera.ManaCameraX
import ru.gorinih.moneymana.databinding.FragmentCameraBinding
import ru.gorinih.moneymana.presentation.AccessPermissionsActivity
import ru.gorinih.moneymana.presentation.NavigationActivity
import ru.gorinih.moneymana.presentation.ui.camera.adapter.CameraSpinAdapter
import ru.gorinih.moneymana.presentation.ui.camera.viewmodel.CameraFragmentViewModel
import ru.gorinih.moneymana.presentation.ui.camera.viewmodel.CameraFragmentViewModelFactory
import ru.gorinih.moneymana.utils.calculateMinSide
import java.util.concurrent.Executor

class CameraFragment : Fragment() {

    private lateinit var _binding: FragmentCameraBinding
    private val binding get() = _binding
    private lateinit var permissions: AccessPermissionsActivity
    private lateinit var navigateView: NavigationActivity

    private lateinit var cameraX: ManaCameraX
    private lateinit var executor: Executor

    private lateinit var listAdapter: CameraSpinAdapter

    private val cameraViewModel: CameraFragmentViewModel by viewModels {
        CameraFragmentViewModelFactory(requireContext())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        permissions = context as AccessPermissionsActivity
        navigateView = context as NavigationActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCameraBinding.inflate(inflater, container, false).run {
        _binding = this
        binding.root
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        executor = ContextCompat.getMainExecutor(requireContext())
        bindSpinner()
        onControlSet()
        onBindCameraX()
        onSurfaceSetting()
    }

    private fun onControlSet() {
        navigateView.setBarVisibility(View.GONE)
        binding.exitButton.setOnClickListener { activity?.onBackPressed() }
    }

    @InternalCoroutinesApi
    private fun bindSpinner() {
        cameraViewModel.getListCategories()
        cameraViewModel.listSpinner.observe(viewLifecycleOwner, {
            listAdapter = CameraSpinAdapter(requireContext(), it)
            binding.categorySpinner.adapter = listAdapter
        })
    }

    private fun onSurfaceSetting() {
        binding.overlay.apply {
            setZOrderOnTop(true)
            holder.setFormat(PixelFormat.TRANSPARENT)
            holder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceCreated(holder: SurfaceHolder) {
                    holder.also {
                        drawOverlay(
                            it
                        )
                    }
                }

                override fun surfaceChanged(
                    holder: SurfaceHolder,
                    format: Int,
                    width: Int,
                    height: Int
                ) {
                }

                override fun surfaceDestroyed(holder: SurfaceHolder) {
                }
            })
        }
    }

    private fun calculateRect(
        widthPanel: Int,
        heightPanel: Int,
        inputWindow: Int,
        sideSquare: Int
    ): Rect =
        Rect().apply {
            right = (widthPanel - sideSquare).div(2)
            left = widthPanel - right
            top = (heightPanel - inputWindow - sideSquare).div(2)
            bottom = top + sideSquare
        }

    @SuppressLint("SetTextI18n")
    private fun drawOverlay(
        holder: SurfaceHolder
    ) {
        val canvas = holder.lockCanvas()
        val paint = Paint().apply {
            alpha = 150
        }
        canvas.drawPaint(paint)

        val window = Rect(0, 0, 0, 0)
        val cornerRect = 60f
        binding.panelView.getHitRect(window)
        val rectNoPaint = Paint().apply {
            style = Paint.Style.FILL
            color = Color.WHITE
            xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        }
        val rectBarcodeWall = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = 5f
            color = Color.WHITE
        }
        canvas.drawRoundRect(window.toRectF(), cornerRect, cornerRect, rectNoPaint)
        window.top = window.bottom - 60
        canvas.drawRect(window, rectNoPaint)

        val side: Int = binding.panelView.calculateMinSide()
        window.set(
            calculateRect(
                binding.overlay.width,
                binding.overlay.height,
                binding.panelView.height,
                side
            )
        )
        canvas.drawRoundRect(window.toRectF(), 0f, 0f, rectNoPaint)
        canvas.drawRoundRect(window.toRectF(), 0f, 0f, rectBarcodeWall)
        holder.unlockCanvasAndPost(canvas)
    }

    override fun onResume() {
        super.onResume()
        when (permissions.allPermissionsGranted()) {
            true -> binding.flagPermissionCamera.visibility = View.GONE
            false -> binding.flagPermissionCamera.visibility = View.VISIBLE
        }
    }

    private fun onBindCameraX() {
        cameraX = ManaCameraX(
            context = requireContext(),
            executor = executor,
            previewView = binding.cameraView,
            lifecycle = this
        )
        cameraX.qrCode.observe(viewLifecycleOwner, { qrc ->
            qrc?.let {
                cameraViewModel.checkScan(it).apply {
                    if (dateCheck != binding.dateTextedit.text.toString()
                        || sumCheck != binding.moneyTextedit.text.toString()
                    ) {
                        navigateView.startVibration()
                        binding.dateTextedit.setText(dateCheck)
                        binding.moneyTextedit.setText(sumCheck)
                    }
                }
            }
        })
        cameraX.bindCamera()
    }

    companion object {
        fun newInstance() = CameraFragment()
    }
}