using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BindRotation : MonoBehaviour {
	public GameObject parent;
	public bool bindToInput;
	public Rigidbody mover;

	private ParticleSystem.MainModule mainModule;
	private ParticleSystem ps;

	void Start()
	{
		ps = GetComponent<ParticleSystem> ();
		mainModule = ps.main;
	}

	void Update () {
		float rot = parent.transform.rotation.eulerAngles.y;
		// Debug.Log ("Setting rot = " + rot);
		mainModule.startRotation = 3.1415f * rot / 180.0f;
		if (bindToInput) {
			if (mover.velocity.magnitude > 0.1) {
				ps.Play ();			
			} else {
				ps.Stop ();
			}
		}
	}
}
