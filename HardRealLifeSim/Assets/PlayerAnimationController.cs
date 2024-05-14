using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlayerAnimationController : MonoBehaviour
{
    private Animator animator;
    private GameObject player;
    private PlayerController playerController;
    private PlayerController.State currentState;

    // Start is called before the first frame update
    void Start()
    {
        animator = GetComponent<Animator>();
        player = GameObject.FindWithTag ("Player");
        playerController = player.GetComponent<PlayerController>();
        currentState = PlayerController.State.IDLE;
    }

    // Update is called once per frame
    void Update()
    {
        PlayerController.State animState = playerController.getState();
        if(currentState != animState)
        {
            currentState = animState;
            string animStateName = "Stop";
            switch(animState)
            {
                case PlayerController.State.RUNNING_FORWARD:
                    animStateName = "StartRunForward";
                break;
                case PlayerController.State.IDLE:
                // intended fall through
                default:
                break;
            }
			Debug.Log ("Play Animation: "+animStateName);
            //animator.Play(animStateName, 0, 0.0f);
            animator.SetTrigger(animStateName);
        }
    }
}
