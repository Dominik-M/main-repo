using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DestroyByContact : MonoBehaviour {
	public GameObject explosion;

	void OnTriggerEnter (Collider other)
	{
		if (other.tag == "Boundary" || other.tag == gameObject.tag)
		{
			return;
		}
		if (explosion != null) {
			Instantiate (explosion, transform.position, transform.rotation);
		}
		Destroy (gameObject);
		GameController.getInstance ().AddScore (2);
	}
}
