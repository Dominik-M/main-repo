using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LandingManeuver : MonoBehaviour {
	public GameObject lander, landingSpot;
	public float speed;

	private bool attached;

	// Use this for initialization
	void Start () {
		attached = false;
	}
	
	// Update is called once per frame
	void Update () {
		if (attached) {
			//lander.GetComponent<Rigidbody> ().velocity = Vector3.zero;
		} else {
			Vector3 direction = landingSpot.transform.position - lander.transform.position;
			lander.GetComponent<Rigidbody> ().AddForce (speed * direction);
		}
	}

	void OnCollisionEnter (Collision collision)
	{
		if (collision.gameObject.tag == "Landing Spot") {
			attached = true;
			lander.transform.parent = landingSpot.transform;
			lander.GetComponent<Rigidbody> ().velocity = Vector3.zero;
			Debug.Log ("Attached to " + landingSpot);
		}
	}


}
