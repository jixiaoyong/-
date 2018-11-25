package cf.android666.myapplication

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mSharedPreferences: SharedPreferences
    private val REQUEST_CAMERA: Int = 1
    private val REQUEST_SD_CARD: Int = 2
    private val KEY_COUNT_REQUEST_PERMISSION = "count_request_permission_"
    private var readyToTakePicture = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)

        mSharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)

        initView()
        bindEvent()

    }

    private fun bindEvent() {
        take_picture.setOnClickListener {
            if (!readyToTakePicture) {
                Toast.makeText(this,"相机还没准备好，稍后再试",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            try {
                mCamera?.takePicture(null, null, { data, _ ->
                    readyToTakePicture = true
                    Log.d("TAG", "jpeg data is " + data)
                    Thread(Runnable {
                        safeSaveImageData2SdCard(data)
                    }).start()
                    mCamera?.startPreview()
                    mPreview?.setCamera(mCamera)
                })
                readyToTakePicture = false
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun safeSaveImageData2SdCard(data: ByteArray?) {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            safeRequestPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_SD_CARD,
                "应用需要SD卡权限以保存图片", "你拒绝了赋予本应用读取SD卡权限，请在设置中重新赋予"
            )
        } else {
            doSaveImageData2SdCard(data)
        }
    }

    private fun safeRequestPermission(
        permission: String,
        requestCode: Int,
        permissionRationale: String,
        deniedString: String
    ) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            /**
             * 第一次请求时为false
             * 第二次请求时为true，需要解释为什么需要这个权限
             * 若用户选择了不再提示则一直为false
             * 综上，如果不是第一次请求该权限，并且返回值为false，那么可以判断用户选择了不再提示
             */
            var noCameraDialog = AlertDialog.Builder(this@MainActivity)
                .setTitle("提示️")
                .setMessage(permissionRationale)
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ ->
                    doRequestPermission(permission, requestCode)
                }
                .create()
            noCameraDialog.show()
        } else {
            if (mSharedPreferences.getInt(KEY_COUNT_REQUEST_PERMISSION, 0) > 1) {
                Toast.makeText(this, deniedString, Toast.LENGTH_LONG).show()
            } else {
                doRequestPermission(permission, requestCode)
            }
        }
    }

    private fun doSaveImageData2SdCard(data: ByteArray?) {
        if (data == null) {
            Log.d("TAG", "camera data is null")
            return
        }

        var imgPath =
            Environment.getExternalStorageDirectory().absolutePath + File.separator + "temp_images" + File.separator
        if (!File(imgPath)?.exists()) {
            File(imgPath).mkdirs()
        }
        var imageFile = File(imgPath + Date().toString() + ".jpg")
        if (!imageFile.exists()) {
            imageFile.createNewFile()
        }
        var fileOutputStream = FileOutputStream(imageFile)
        fileOutputStream?.write(data)
        fileOutputStream?.flush()
        fileOutputStream.close()

    }

    private fun initView() {
        mPreview = CameraPreview(this, surface_view)
        layout.addView(mPreview)
    }


    private fun checkCameraDeviceAndPermissions() {
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
            safeRequestPermission(
                Manifest.permission.CAMERA, REQUEST_CAMERA,
                "应用需要相机权限以拍取图片", "你拒绝了赋予本应用使用相机的权限，请在设置中重新赋予"
            )
        } else {
            safeOpenCamera(cameraId)
        }
    }

    private fun doRequestPermission(permission: String, requestCode: Int) {
        var count = mSharedPreferences.getInt(KEY_COUNT_REQUEST_PERMISSION + permission, 0) + 1
        mSharedPreferences.edit().putInt(KEY_COUNT_REQUEST_PERMISSION + permission, count).apply()
        requestPermissions(arrayOf(permission), requestCode)
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
                            safeRequestPermission(
                                Manifest.permission.CAMERA, REQUEST_CAMERA,
                                "应用需要相机权限以拍取图片", "你拒绝了赋予本应用使用相机的权限，请在设置中重新赋予"
                            )
                        }
                        .setNegativeButton("No") { _, _ -> finish() }
                        .create()
                    noCameraPermissionDialog.show()
                }
            }
            REQUEST_SD_CARD -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "可以保存图片了", Toast.LENGTH_SHORT).show()
                } else {
                    var noCameraPermissionDialog = AlertDialog.Builder(this@MainActivity)
                        .setTitle("警告⚠️")
                        .setMessage("没有SD卡权限，不可继续！\n请赋予SD卡权限")
                        .setCancelable(false)
                        .setPositiveButton("Yes") { _, _ ->
                            safeRequestPermission(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_SD_CARD,
                                "应用需要SD卡权限以保存图片", "你拒绝了赋予本应用读取SD卡权限，请在设置中重新赋予"
                            )
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
        Log.d("TAG", "onREsume")
        checkCameraDeviceAndPermissions()
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
        mCamera?.also {
            it.stopPreview()
            it.release()
            mCamera = null
        }
        mPreview?.setCamera(null)
    }
}
