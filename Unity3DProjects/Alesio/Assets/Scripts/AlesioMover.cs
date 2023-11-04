using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AlesioMover : MonoBehaviour {
	
	public float speed;

	private Rigidbody rb;
	
	// Use this for initialization
	void Start () {
		rb = GetComponent<Rigidbody>();
	}
	
	// Update is called once per frame
	void Update () {
		float moveHorizontal = Input.GetAxis ("Vertical");
		float moveVertical = Input.GetAxis ("Horizontal");

		Vector3 movement = new Vector3 (moveHorizontal, 0.0f, -moveVertical).normalized;
		rb.AddForce (movement * speed);
	}
}
