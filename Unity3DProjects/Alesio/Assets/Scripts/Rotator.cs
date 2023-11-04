using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Rotator : MonoBehaviour {

	public float speed;

	void Start () {
		Vector3 v = new Vector3 (0.0f, speed, 0.0f);
		GetComponent<Rigidbody> ().angularVelocity = v;
	}
}
