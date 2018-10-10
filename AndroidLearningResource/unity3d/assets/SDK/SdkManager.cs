using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SdkManager : MonoBehaviour {

    private static SdkManager mInstance;

    public static SdkManager Instance()
    {
        //get {
            if (mInstance == null)
            {
                mInstance = new SdkManager();
            }
            return mInstance;
        //}
    }

    private SdkBase mSdk;

    private void Awake()
    {
        mInstance = this;
#if UNITY_EDITOR
        mSdk = new SdkBase();
//#elif UNITY_ANDROID
        mSdk = new SdkForAndroid();
#endif
    }

    public void Init()
    {
        mSdk.Init();
    }

    public void Login()
    {
        mSdk.Login();
    }
    public void Logout()
    {
        mSdk.Logout();
    }
    public void Pay(string s)
    {
        mSdk.Pay( s);
    }
    public void GameCenter(string s)
    {
        mSdk.GameCenter( s);
    }


    // Use this for initialization
    void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		
	}
}
