package io.github.jixiaoyong.unity3d.maindemo

import android.os.Bundle
import android.widget.SeekBar
import cf.android.unity3d.demo.UnityPlayerActivity
import com.unity3d.player.UnityPlayer
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : UnityPlayerActivity() {

    private var mOldProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        unity3d_layout.addView(mUnityPlayer.view)

        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var d = when (radio_group.checkedRadioButtonId) {
                    R.id.radio_x -> "x"
                    R.id.radio_y -> "y"
                    R.id.radio_z -> "z"
                    else -> "x"
                }

                var json = JSONObject()
                json.put("direction", d)
                json.put("offset", progress - mOldProgress)
                mOldProgress = progress

                UnityPlayer.UnitySendMessage("Cube", "SeekCube", json.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

    }


    companion object {
        private const val STEP = "" + 20
    }
}
