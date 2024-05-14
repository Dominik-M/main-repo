using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class InteractionController : MonoBehaviour
{
    public enum Type
    {
        NONE,
        SLEEP,
        WORK,
        BUY,
        INSPECT,
        USE,
        TALK

    }

    public string objectName;
    public List<Type> interactions;

    // Start is called before the first frame update
    void Start()
    {

    }

    // Update is called once per frame
    void Update()
    {

    }


    void OnTriggerEnter(Collider other)
    {
        if (other.tag == "Player")
        {
            print("Player got in contact with " + this);
            //Debug.Log("Add interaction: " + it);
            GameController.getInstance().addInteractable(this);
        }
    }

    void OnTriggerExit(Collider other)
    {
        if (other.tag == "Player")
        {
            print("Player no longer in contact with " + this);
            //Debug.Log("Remove interaction: " + it);
            GameController.getInstance().removeInteractable(this);
        }
    }
}
