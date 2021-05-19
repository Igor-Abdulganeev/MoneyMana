package ru.gorinih.moneymana.ui.camera

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.toRectF
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.gorinih.moneymana.data.camera.ManaCameraX
import ru.gorinih.moneymana.databinding.FragmentCameraBinding
import ru.gorinih.moneymana.ui.AccessPermissionsActivity
import ru.gorinih.moneymana.ui.NavigationActivity
import ru.gorinih.moneymana.utils.calculateMaxSide
import java.util.concurrent.Executor

class CameraFragment : Fragment() {

    private lateinit var _binding: FragmentCameraBinding
    private val binding get() = _binding
    private var permissions: AccessPermissionsActivity? = null
    private lateinit var barView: NavigationActivity

    private lateinit var cameraX: ManaCameraX
    private var executor: Executor? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        permissions = context as AccessPermissionsActivity
        barView = context as NavigationActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCameraBinding.inflate(inflater, container, false).run {
        _binding = this
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        executor = ContextCompat.getMainExecutor(requireContext())
        onBindCameraX()
        onSurfaceSetting()
        barView.setBarVisibility(View.GONE)
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

        val side: Int = binding.panelView.calculateMaxSide()
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
        when (permissions?.allPermissionsGranted()) {
            true -> binding.flagPermissionCamera.visibility = View.GONE
            false -> binding.flagPermissionCamera.visibility = View.VISIBLE
        }
    }

    override fun onDetach() {
        executor = null
        permissions = null
        super.onDetach()
    }

    private fun onBindCameraX() {
        cameraX = ManaCameraX(
            context = requireContext(),
            executor = executor!!,
            previewView = binding.cameraView,
            lifecycle = this
        )
        cameraX.qrCode.observe(viewLifecycleOwner, { qrc ->
            qrc?.let {
//                lifecycleScope.launch { flashRect(rects,Color.RED,Color.GREEN) }
                //ТЕКСТ СКАНА = t=20210315T180100&s=234.60&fn=9960440300119563&i=7611&fp=3036044891&n=1
                val list = it.split("&")
                val dateCheck = "${list[0].subSequence(8, 10)}" +
                        ".${list[0].subSequence(6, 8)}" +
                        ".${list[0].subSequence(2, 6)}" +
                        " ${list[0].subSequence(11, 13)}" +
                        ":${list[0].subSequence(13, 15)}"
                val sum = list[1].substring(2)
                // val fn = list[2].substring(3).toLong()
                // val i = list[3].substring(2).toLong()
                // val fp = list[4].substring(3).toLong()
/*
                scanningCheck = Check(
                    id = 0,
                    dateCheck = 0,
                    category = Category(0, 0, ""),
                    sumCheck = 0.00,
                    fnCheck = fn,
                    iCheck = i,
                    fpCheck = fp
                )
*/
                binding.dateTextedit.setText(dateCheck)
                binding.moneyTextedit.setText(sum)
            }
        })
        cameraX.bindCamera()
    }

    companion object {
        fun newInstance() = CameraFragment()
    }
}