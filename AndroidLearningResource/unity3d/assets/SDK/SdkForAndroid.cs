using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SdkForAndroid : SdkBase {

    public override void Init()
    {
        using(AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer"))
        {
            using(AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity"))
            {
                jo.Call("init");
            }
        }
    }

    public override void Login()
    {
        using (AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer"))
        {
            using (AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity"))
            {
                jo.Call("login");
            }
        }
    }

    public override void Logout()
    {
        using (AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer"))
        {
            using (AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity"))
            {
                jo.Call("logout");
            }
        }
    }

    public override void Pay(string s)
    {
        using (AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer"))
        {
            using (AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity"))
            {
                jo.Call("pay",s);
            }
        }
    }

    public override void GameCenter(string s)
    {
        using (AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer"))
        {
            using (AndroidJavaObject jo = jc.GetStatic<AndroidJavaObject>("currentActivity"))
            {
                jo.Call("gameCenter", s);
            }
        }
    }
}
