using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraController : MonoBehaviour {

	public GameObject player;

	private Vector3 offset;

	void Start ()
	{
		offset = transform.position;
    }

	void LateUpdate ()
	{
        if (player != null)
        {
            transform.position = player.transform.position + offset;
        }
	}

	public void zoomIn(){
		if(offset.y > 1)
			offset.y--;
	}

	public void zoomOut(){
		offset.y++;
	}

	void OnMouseWheel()
	{
		float delta = Input.GetAxis("Mouse ScrollWheel");
		if (delta > 0) {
			zoomIn ();
		} else if (delta < 0) {
			zoomOut ();
		}
	}
}
