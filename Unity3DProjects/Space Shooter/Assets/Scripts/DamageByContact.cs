using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class DamageByContact : MonoBehaviour {
	public GameObject explosion;
	public int maxHitpoints;
	public bool superExplosion;
	public Slider healthSlider;

	private int hitpoints;

	private bool destroyed = false;

	void Start(){
		hitpoints = maxHitpoints;
		updateUI ();
	}

	void OnTriggerEnter (Collider other)
	{
		if (other.tag == "Boundary" || other.tag == gameObject.tag)
		{
			return;
		}
		if (--hitpoints <= 0 && !destroyed) {
			destroyed = true;
			if (superExplosion) {
				StartCoroutine (explode());
			} else {
				Instantiate (explosion, transform.position, transform.rotation);
				Destroy (gameObject);
				if (gameObject.tag == "Player")
					GameController.getInstance ().GameOver ();
			}
		}
		updateUI ();
	}

	public void refill(){
		hitpoints = maxHitpoints;
		updateUI ();
	}

	public void updateUI(){
		if (healthSlider != null) {
			healthSlider.value = 100.0f * hitpoints / maxHitpoints;
		}
	}

	IEnumerator explode(){
		Destroy (gameObject, 2.0f);
		for (int i = 0; i < 10; i++) {
			Instantiate (explosion, transform.position + transform.forward * ((i%3)*-4), transform.rotation);
			yield return new WaitForSeconds (0.2f);
		}
	}
}
