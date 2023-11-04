using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PickUp : MonoBehaviour
{
	public enum PickUpType
	{
		HEALTH, UPGRADE
	}

	public PickUpType type;

	void OnTriggerEnter (Collider other)
	{
		if (other.tag == "Player") {
			Destroy (gameObject);
			switch (type) {
			case PickUpType.HEALTH:
				GameController.getInstance ().player.GetComponent<DamageByContact> ().refill ();
				Debug.Log ("Players Health restored");
				break;
			case PickUpType.UPGRADE:
				Weapon weapon = GameController.getInstance ().player.GetComponent<WeaponController> ().weapon;
				weapon.fireRate -= weapon.fireRate * 0.1f;
				Debug.Log ("Weapon firerate upgraded to " + weapon.fireRate);
				break;
			}
		}
	}
}
