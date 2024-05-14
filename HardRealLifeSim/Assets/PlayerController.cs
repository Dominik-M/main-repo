using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerController : MonoBehaviour
{
    
	public enum State
	{
		IDLE,
		RUNNING_FORWARD,
		RUNNING_BACKWARD,
		GATHERING,
        DEAD
	}


    public float speed, rotSpeed, jumppower;

	private Rigidbody rb;
    private float angle;
    private State state;
	private bool grounded;


    public State getState()
    {
        return state;
    }


    // Start is called before the first frame update
    void Start()
    {
		rb = GetComponent<Rigidbody> ();
        angle = rb.rotation.y;
		grounded = false;
    }

    // Update is called once per frame
    void Update()
    {

    }

    void FixedUpdate ()
	{
		// Keyboard
		float rotation = Input.GetAxis ("Horizontal");
		float moveVertical = Input.GetAxis ("Vertical");
		bool interact = Input.GetButtonDown ("Submit");
		bool jump = Input.GetButton ("Jump");
		// Accelerator
		//Vector3 acceleration = Input.acceleration;
		//Vector3 fixedAcceleration = FixAcceleration (acceleration);
		//float rotation = fixedAcceleration.x * 2;
		//float moveVertical = fixedAcceleration.y * 10;
		// Touchpad
		//Vector2 direction = touchPad.GetDirection ();
		//rotation += direction.x;
		//moveVertical += direction.y;

		rb.velocity = new Vector3 (Mathf.Sin (angle * 0.0174528f) * moveVertical * speed, rb.velocity.y, Mathf.Cos (angle * 0.0174528f) * moveVertical * speed);

		if(moveVertical > 0.01)
			state = State.RUNNING_FORWARD;
		else if(moveVertical < -0.01)
			state = State.RUNNING_BACKWARD;
		else 
			state = State.IDLE;

		angle += rotation * rotSpeed;
		rb.rotation = Quaternion.Euler (0.0f, angle, 0.0f);

		
		if (interact)
        {
			Debug.Log ("Interaction");
		}
		if (jump && grounded)
        {
			Debug.Log ("Jump");
			rb.AddForce(new Vector3(0.0f, jumppower, 0.0f));
			grounded = false;
		}

	}

	
	void OnCollisionEnter (Collision collision)
	{
        print("Got in contact with " + collision.transform.name);
		if (collision.gameObject.tag == "Ground") {
			grounded = true;
		}
	}

	void OnCollisionExit(Collision collision)
    {
        print("No longer in contact with " + collision.transform.name);
		if (collision.gameObject.tag == "Ground") {
			grounded = false;
		}
    }

}
