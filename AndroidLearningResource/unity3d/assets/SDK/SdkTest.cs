using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SdkTest : MonoBehaviour {

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
		
	}

    void OnGUI()
    {
        if (GUI.Button(new Rect(0,0,300,100),"Init"))
        {
            Debug.LogError("Unity:This is Init");
            SdkManager.Instance().Init();
        }
        else if (GUI.Button(new Rect(0, 130, 300, 100), "Login"))
        {
            Debug.LogError("Unity:This is Login");
            SdkManager.Instance().Login();
        }
        else if (GUI.Button(new Rect(0, 260, 300, 100), "Logout"))
        {
            Debug.LogError("Unity:This is Logout");
            SdkManager.Instance().Logout();
        }
        else if (GUI.Button(new Rect(0, 390, 300, 100), "Pay"))
        {
            Debug.LogError("Unity:This is Login");
            SdkManager.Instance().Pay("sth");
        }
        else if (GUI.Button(new Rect(0, 520, 300, 100), "GameCenter"))
        {
            Debug.LogError("Unity:This is Login");
            SdkManager.Instance().GameCenter("sth");
        }

    }

    void CallAndroidStatic()
    {
        AndroidJavaClass androidJavaClass = new AndroidJavaClass("cf.android.unity3d.sdk.MInterface1");
        androidJavaClass.CallStatic("init");
    }

    void CallAndroid()
    {
        AndroidJavaClass unityClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject activity = unityClass.GetStatic<AndroidJavaObject>("currentActivity");
        activity.Call("login");
    }

    void ReceiverFromSdk(string s)
    {
        Debug.LogError("Unity:Receiver from sdk " + s);
    }
}
