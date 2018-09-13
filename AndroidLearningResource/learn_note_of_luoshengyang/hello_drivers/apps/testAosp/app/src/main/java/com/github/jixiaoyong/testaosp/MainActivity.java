package com.github.jixiaoyong.testaosp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.os.IHelloService;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener {

    private IHelloService mHelloService;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mHelloService = IHelloService.Stub.asInterface(ServiceManager.getService("hello"));

         mHelloService = (IHelloService) getSystemService("hello");

        Log.d("TAG", "is hello null " + (mHelloService == null));


        findViewById(R.id.read_btn).setOnClickListener(this);
        findViewById(R.id.write_btn).setOnClickListener(this);
        findViewById(R.id.clean_btn).setOnClickListener(this);
        text = (EditText) findViewById(R.id.text);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.read_btn:
                try {
                    text.setText(mHelloService.getVal());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.write_btn:
                try {
                    mHelloService.setVal(Integer.parseInt(text.getText().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.clean_btn:
                text.setText("");
                break;

        }
    }
}
