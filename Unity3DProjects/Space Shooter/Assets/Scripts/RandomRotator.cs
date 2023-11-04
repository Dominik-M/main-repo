using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RandomRotator : MonoBehaviour {

	public float angularTumble, tumble;

	void Start () {
		GetComponent<Rigidbody> ().angularVelocity = Random.insideUnitSphere * angularTumble;
		Vector3 v = new Vector3 (Random.value * tumble*2 - tumble, 0.0f, Random.value * tumble*2 - tumble);
		GetComponent<Rigidbody> ().velocity = v;
	}
}
