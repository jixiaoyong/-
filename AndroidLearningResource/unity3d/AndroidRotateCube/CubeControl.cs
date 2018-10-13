using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CubeControl : MonoBehaviour {

    private float speed = 2f;

	// Use this for initialization
	void Start () {
		
	}
	
	// Update is called once per frame
	void Update () {
        
    }

 
    void SeekCube(string json)
    {
        Data data = JsonUtility.FromJson<Data>(json);
        switch (data.direction)
        {
            case "x":
                transform.Rotate(new Vector3(data.offset, 0, 0));
                break;
            case "y":
                transform.Rotate(new Vector3( 0, data.offset, 0));
                break;
            case "z":
                transform.Rotate(new Vector3( 0, 0, data.offset));
                break;
        }
    }

    class Data
    {
        public string direction;
        public float offset;
    }


}
