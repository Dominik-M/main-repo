using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[System.Serializable]
public class Boundary
{
	public float xMin, xMax, zMin, zMax;
}

public class PlayerController : MonoBehaviour
{

	public enum State
	{
		LANDED,
		LANDING,
		STARTING,
		STARTED
	}

	public float speed, landingSpeed, landingRange;
	public float maxSpeed;
	public float rotSpeed;
	public float tilt;
	public Boundary boundary;
	public bool boundaryCheckEnabled;
	public SimpleTouchPad touchPad;
	public SimpleTouchAreaButton fireButton;

	private Rigidbody rb;
	private WeaponController weapon;
	private float angle;
	private Quaternion calibrationQuaternion;
	private State state;
	private GameObject landingSpot;
	private Vector3 startingTargetPosition;
	private Quaternion startingTargetRotation;

	void Start ()
	{
		rb = GetComponent<Rigidbody> ();
		weapon = GetComponent<WeaponController> ();
		angle = 0.0f;
		state = State.STARTED;
		landingSpot = null;
		startingTargetPosition = Vector3.zero;
		startingTargetRotation = Quaternion.identity;
		CalibrateAccelerometer ();
	}

	public State getState ()
	{
		return state;
	}

	public void setState (State newstate)
	{
		state = newstate;
	}

	//Used to calibrate the Iput.acceleration input
	void CalibrateAccelerometer ()
	{
		Vector3 accelerationSnapshot = Input.acceleration;
		Quaternion rotateQuaternion = Quaternion.FromToRotation (new Vector3 (0.0f, 0.0f, -1.0f), accelerationSnapshot);
		calibrationQuaternion = Quaternion.Inverse (rotateQuaternion);
	}

	//Get the 'calibrated' value from the Input
	Vector3 FixAcceleration (Vector3 acceleration)
	{
		Vector3 fixedAcceleration = calibrationQuaternion * acceleration;
		return fixedAcceleration;
	}

	void Update ()
	{
		weapon.firing = Input.GetButton ("Fire1");
		weapon.firing |= fireButton.Touched ();
	}

	void FixedUpdate ()
	{
		switch (state) {
		case (State.LANDED):
			{
				GetComponent<Rigidbody> ().velocity = Vector3.zero;
				bool start = Input.GetButtonDown ("Submit");
				if (start) {
					Debug.Log ("Starting");
					GameControllerAdventure.getInstance ().startFrom (landingSpot);
					//transform.parent = null;
					startingTargetPosition = Vector3.zero;
					startingTargetRotation = Quaternion.identity;
					state = State.STARTING;
				}
				break;
			}
		case (State.LANDING):
			{
				if (landingSpot) {
					// perform landing maneuver
					Vector3 direction = landingSpot.transform.position - transform.position;
					rb.AddForce (landingSpeed * direction);
					transform.localScale = transform.localScale * 0.97f;
					if (transform.localScale.x < 0.1)
						transform.localScale = Vector3.one * 0.1f;
				}
				break;
			}
		case (State.STARTING):
			{
				Vector3 direction = (startingTargetPosition - transform.position);
				bool moveDone = direction.magnitude < 1;
				if (moveDone) {
					// jump the last bit and finalize the start
					transform.position = startingTargetPosition;
					rb.velocity = Vector3.zero;
					angle = 0;
					transform.localScale = Vector3.one;
					Debug.Log ("Starting done");
					state = State.STARTED;
				} else {
					rb.AddForce (landingSpeed * direction);
					transform.rotation = Quaternion.Slerp (transform.rotation, startingTargetRotation, 0.2f);
					transform.localScale = transform.localScale * 1.02f;
					if (transform.localScale.x > 1.0)
						transform.localScale = Vector3.one;
				}
				break;
			}
		case (State.STARTED):
			{
				// Keyboard
				float rotation = Input.GetAxis ("Horizontal");
				float moveVertical = Input.GetAxis ("Vertical");
				bool tryland = Input.GetButtonDown ("Submit");

				if (tryland) {
					landingSpot = GameObject.FindWithTag ("Landing Spot");
					if (landingSpot && (landingSpot.transform.position - transform.position).magnitude < landingRange) {
						Debug.Log ("Start landing on " + landingSpot);
						state = State.LANDING;
						return;
					}
				}

				// Accelerator
				//Vector3 acceleration = Input.acceleration;
				//Vector3 fixedAcceleration = FixAcceleration (acceleration);
				//float rotation = fixedAcceleration.x * 2;
				//float moveVertical = fixedAcceleration.y * 10;

				// Touchpad
				Vector2 direction = touchPad.GetDirection ();
				rotation += direction.x;
				moveVertical += direction.y;

				angle += rotation * rotSpeed;
				rb.rotation = Quaternion.Euler (0.0f, angle, rotation * -tilt);

				if (moveVertical < 0) {
					if (rb.velocity.magnitude < speed * 2.0f)
						rb.velocity = Vector3.zero;
					else
						rb.velocity -= rb.velocity.normalized * speed;
				} else {
					Vector3 movement = new Vector3 (Mathf.Sin (angle * 0.0174528f) * moveVertical, 0.0f, Mathf.Cos (angle * 0.0174528f) * moveVertical);
					rb.velocity += movement * speed;
					if (rb.velocity.magnitude > maxSpeed)
						rb.velocity = rb.velocity.normalized * maxSpeed;
				}

				if (boundaryCheckEnabled) {
					rb.position = new Vector3 (
						Mathf.Clamp (rb.position.x, boundary.xMin, boundary.xMax), 
						0.0f, 
						Mathf.Clamp (rb.position.z, boundary.zMin, boundary.zMax)
					);
				}
				break;
			}
		default:
			break;
		}
	}

	void OnCollisionEnter (Collision collision)
	{
		if (collision.gameObject.tag == "Landing Spot") {
			landingSpot = collision.gameObject;
			state = State.LANDED;
			//transform.parent = landingSpot.transform;
			GetComponent<Rigidbody> ().velocity = Vector3.zero;
			GameControllerAdventure.getInstance ().landOn (landingSpot);
		}
	}

	public float getAngle ()
	{
		return angle;
	}
}
