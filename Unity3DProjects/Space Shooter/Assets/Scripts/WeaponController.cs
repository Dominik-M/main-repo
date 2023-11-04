using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public enum WeaponType{
	SINGLE_SHOT,
	MULTI_SHOT,
	MULTI_SHOT_RAND,
	SALVO
};

[System.Serializable]
public class Weapon
{
	public float fireRate;
	public float spreadAngle;
	public int shotCount;
	public WeaponType type;
}

public class WeaponController : MonoBehaviour {
	public GameObject shot;
	public Transform shotSpawn;
	public Weapon weapon;
	public bool firing;

	private float nextFire;
	private Rigidbody rb;

	// Use this for initialization
	void Start () {
		rb = GetComponent<Rigidbody> ();
	}
	
	void Update(){
		if (firing && Time.time > nextFire) {
			switch (weapon.type) {
			case WeaponType.MULTI_SHOT:
				{
					// multiple equal spreaded shots
					for (int i = 0; i < weapon.shotCount; i++) {
						Vector3 rotOffset = new Vector3 (0.0f, i * weapon.spreadAngle / weapon.shotCount - weapon.spreadAngle / 2, 0.0f);
						Quaternion rotation = Quaternion.Euler (shotSpawn.rotation.eulerAngles + rotOffset);
						GameObject instance = Instantiate (shot, shotSpawn.position, rotation);
						if (rb != null) {
							instance.GetComponent<Rigidbody> ().velocity += rb.velocity;
						}
					}
					break;
				}
			case WeaponType.MULTI_SHOT_RAND:
				{
					// multiple random spreaded shots
					for (int i = 0; i < weapon.shotCount; i++) {
						Vector3 rotOffset = new Vector3 (0.0f, Random.Range (-weapon.spreadAngle, weapon.spreadAngle), 0.0f);
						Quaternion rotation = Quaternion.Euler (shotSpawn.rotation.eulerAngles + rotOffset);
						GameObject instance = Instantiate (shot, shotSpawn.position, rotation);
						if (rb != null) {
							instance.GetComponent<Rigidbody> ().velocity += rb.velocity;
						}
					}
					break;
				}
			case WeaponType.SALVO:
				{
					StartCoroutine (fireSalvo ());
					break;
				}
			default:
				{
					// single random spreaded shot
					Vector3 rotOffset = new Vector3 (0.0f, Random.Range (-weapon.spreadAngle, weapon.spreadAngle), 0.0f);
					Quaternion rotation = Quaternion.Euler (shotSpawn.rotation.eulerAngles + rotOffset);
					GameObject instance = Instantiate (shot, shotSpawn.position, rotation);
					if (rb != null) {
						instance.GetComponent<Rigidbody> ().velocity += rb.velocity;
					}
					break;
				}
			}
				
			nextFire = Time.time + weapon.fireRate;
			GetComponent<AudioSource>().Play ();
		}
	}

	IEnumerator fireSalvo(){
		for (int i = 0; i < weapon.shotCount; i++) {
			Vector3 rotOffset = new Vector3 (0.0f, Random.Range (-weapon.spreadAngle, weapon.spreadAngle), 0.0f);
			Quaternion rotation = Quaternion.Euler (shotSpawn.rotation.eulerAngles + rotOffset);
			GameObject instance = Instantiate (shot, shotSpawn.position, rotation);
			if (rb != null) {
				instance.GetComponent<Rigidbody> ().velocity += rb.velocity;
			}
			GetComponent<AudioSource>().Play ();
			yield return new WaitForSeconds (weapon.fireRate / 20.0f / weapon.shotCount);
		}
	}
}
