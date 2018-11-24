package cf.android666.myapplication

import android.content.Context
import android.hardware.Camera
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup

/**
 * author: jixiaoyong
 * email: jixiaoyong1995@gmail.com
 * website: www.jixiaoyong.github.io
 * date: 2018/11/22
 * description: todo
 */
class CameraPreview(
    context: Context, private val mSurfaceView: SurfaceView,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes), SurfaceHolder.Callback {

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (changed && childCount > 0) {
            val child = getChildAt(0)

            val width = r - l
            val height = b - t

            var previewWidth = width
            var previewHeight = height
            if (mPreviewSize != null) {
                previewWidth = mPreviewSize!!.width
                previewHeight = mPreviewSize!!.height
            }

            // Center the child SurfaceView within the parent.
            if (width * previewHeight > height * previewWidth) {
                val scaledChildWidth = previewWidth * height / previewHeight
                child.layout(
                    (width - scaledChildWidth) / 2, 0,
                    (width + scaledChildWidth) / 2, height
                )
            } else {
                val scaledChildHeight = previewHeight * width / previewWidth
                child.layout(
                    0, (height - scaledChildHeight) / 2,
                    width, (height + scaledChildHeight) / 2
                )
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec)
        val height = resolveSize(getSuggestedMinimumWidth(), heightMeasureSpec)
        setMeasuredDimension(width, height)
        if (mSupportedPreviewSizes != null) {
            mPreviewSize = getOptimalPreviewSize(mSupportedPreviewSizes as MutableList<Camera.Size>, width, height)
        }
    }

    private fun getOptimalPreviewSize(
        sizes: MutableList<Camera.Size>,
        w: Int,
        h: Int
    ): Camera.Size? {
        val ASPECT_TOLERANCE = 0.1
        val targetRatio = w.toDouble() / h.toDouble()
        if (sizes == null) return null

        var optimalSize: Camera.Size? = null
        var minDiff = java.lang.Double.MAX_VALUE

        val targetHeight = h

        // Try to find an size match aspect ratio and size
        for (size in sizes) {
            val ratio = size.width.toDouble() / size.height.toDouble()
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size
                minDiff = Math.abs(size.height - targetHeight).toDouble()
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = java.lang.Double.MAX_VALUE
            for (size in sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size
                    minDiff = Math.abs(size.height - targetHeight).toDouble()
                }
            }
        }
        return optimalSize
    }

    private var mPreviewSize: Camera.Size? = null

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        mCamera?.apply {
            parameters.also {
                it.setPreviewSize(mPreviewSize!!.width, mPreviewSize!!.height)
                requestLayout()
                parameters = it
            }
            startPreview()
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        mCamera?.stopPreview();
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        try {
            mCamera?.setPreviewDisplay(holder)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private var mCamera: Camera? = null

    private var mSupportedPreviewSizes: List<Camera.Size>? = null

    fun setCamera(camera: Camera?) {
        if (camera == null) {
            return
        }
        stopPreviewAndFreeCamera()
        mCamera = camera
        mCamera?.apply {
            mSupportedPreviewSizes = parameters.supportedPreviewSizes
            requestLayout()
            try {
                setPreviewDisplay(mHolder)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            startPreview()
        }

    }

    private fun stopPreviewAndFreeCamera() {

    }

    var mHolder: SurfaceHolder = mSurfaceView.holder.apply {
        addCallback(this@CameraPreview)
        setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }


}