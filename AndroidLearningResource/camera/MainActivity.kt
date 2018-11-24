package cf.android666.myapplication

import android.hardware.Camera
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPreview = CameraPreview(this, surface_view)
        layout.addView(mPreview)
    }

    private val cameraId: Int = 0

    override fun onResume() {
        super.onResume()
        safeOpenCamera(cameraId)
    }

    override fun onPause() {
        super.onPause()
        releaseCameraAndPreview()
    }

    private var mCamera: Camera? = null

    private fun safeOpenCamera(cameraId: Int): Boolean {
        return try {
            releaseCameraAndPreview()
            mCamera = Camera.open(cameraId)
            mPreview?.setCamera(mCamera)
            true
        } catch (e: Exception) {
            Log.e("TAG", "fail to open camera", e)
            false
        }
    }

    private var mPreview: CameraPreview? = null

    private fun releaseCameraAndPreview() {
        mPreview?.setCamera(null)
        mCamera?.also {
            it.release()
            mCamera = null
        }
    }
}
