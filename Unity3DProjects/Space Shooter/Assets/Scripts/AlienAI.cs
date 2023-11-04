using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AlienAI : MonoBehaviour {

	public float speed;
	public float maxSpeed;
	public float rotSpeed;
	public float tilt;
	public Boundary boundary;

	public GameObject target;

	private Rigidbody rb;
	private float angle, destAngle;

	void Start ()
	{
		rb = GetComponent<Rigidbody>();
		angle = 0.0f;
		destAngle = 0.0f;
		GetComponent<WeaponController> ().firing = true;
	}

	void Update(){
		if(target != null)
		{
			Vector3 dist = (target.transform.position - gameObject.transform.position).normalized;
			if (dist.x < 0) {
				destAngle = Mathf.Abs(Mathf.Asin (dist.z) * Mathf.Rad2Deg + 270);
			} else {
				destAngle = Mathf.Abs(- Mathf.Asin (dist.z) * Mathf.Rad2Deg + 90);
			}
			// Debug.Log ("DestAngle: " + destAngle);
		}
	}

	void FixedUpdate ()
	{
		float rotation = (angle-destAngle > 1) ? -1 : ((angle-destAngle < -1) ? 1 : 0);
		float moveVertical = 0.5f;

		angle += rotation * rotSpeed;
		while (angle > 360)
			angle -= 360;
		while (angle < 0)
			angle += 360;
		rb.rotation = Quaternion.Euler (0.0f, angle, rotation * -tilt);

		if(moveVertical < 0)
		{
			if(rb.velocity.magnitude < speed)
				rb.velocity = Vector3.zero;
			else
				rb.velocity -= rb.velocity.normalized * speed * 2.0f;
		}
		else
		{
			Vector3 movement = new Vector3(Mathf.Sin(angle*Mathf.Deg2Rad) * moveVertical, 0.0f, Mathf.Cos(angle*Mathf.Deg2Rad) * moveVertical);
			rb.velocity += movement * speed;
			if (rb.velocity.magnitude > maxSpeed)
				rb.velocity = rb.velocity.normalized * maxSpeed;
		}

		rb.position = new Vector3 
			(
				Mathf.Clamp (rb.position.x, boundary.xMin, boundary.xMax), 
				0.0f, 
				Mathf.Clamp (rb.position.z, boundary.zMin, boundary.zMax)
			);
	}
}
