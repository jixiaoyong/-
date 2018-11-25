package cf.android666.myapplication

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mSharedPreferences: SharedPreferences
    private val REQUEST_CAMERA: Int = 1
    private val KEY_COUNT_REQUEST_CAMERA_PERMISSION = "count_request_camera_permission"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        checkCameraDeviceAndPremissions()

        mPreview = CameraPreview(this, surface_view)
        layout.addView(mPreview)
    }


    private fun checkCameraDeviceAndPremissions() {
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            var noCameraDialog = AlertDialog.Builder(this@MainActivity)
                .setTitle("警告⚠️")
                .setMessage("本机没有相机，不可继续！")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ -> finish() }
                .create()
            noCameraDialog.show()
        }

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            safeRequestCameraPermission()
        } else {
            safeOpenCamera(cameraId)
        }
    }

    private fun safeRequestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            /**
             * 第一次请求时为false
             * 第二次请求时为true，需要解释为什么需要这个权限
             * 若用户选择了不再提示则一直为false
             * 综上，如果不是第一次请求该权限，并且返回值为false，那么可以判断用户选择了不再提示
             */
            var noCameraDialog = AlertDialog.Builder(this@MainActivity)
                .setTitle("提示️")
                .setMessage("本应用正常运行需要相机权限，点击确认开始赋予权限")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    doRequestCameraPermission()
                }
                .create()
            noCameraDialog.show()
        } else {
            Log.d("TAG", "count " + mSharedPreferences.getInt(KEY_COUNT_REQUEST_CAMERA_PERMISSION, 0))
            if (mSharedPreferences.getInt(KEY_COUNT_REQUEST_CAMERA_PERMISSION, 0) > 1) {
                Toast.makeText(this, "你拒绝了赋予相机权限，请在设置中开启", Toast.LENGTH_LONG).show()
            } else {
                doRequestCameraPermission()
            }
        }
    }

    private fun doRequestCameraPermission() {
        var count = mSharedPreferences.getInt(KEY_COUNT_REQUEST_CAMERA_PERMISSION, 0) + 1
        mSharedPreferences.edit().putInt(KEY_COUNT_REQUEST_CAMERA_PERMISSION, count).apply()
        requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CAMERA -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    safeOpenCamera(cameraId)
                } else {
                    var noCameraPermissionDialog = AlertDialog.Builder(this@MainActivity)
                        .setTitle("警告⚠️")
                        .setMessage("没有相机权限，不可继续！\n请赋予相机权限")
                        .setCancelable(false)
                        .setPositiveButton("Yes") { _, _ ->
                            safeRequestCameraPermission()
                        }
                        .setNegativeButton("No") { _, _ -> finish() }
                        .create()
                    noCameraPermissionDialog.show()
                }
            }
        }
    }

    private val cameraId: Int = 0

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "On Resume()")
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
