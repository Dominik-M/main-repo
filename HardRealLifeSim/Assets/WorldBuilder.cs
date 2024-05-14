using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class WorldBuilder : MonoBehaviour
{
    public GameObject roadStraight,roadEnd,
     bakery,bar,autoService,factory,
     fastfood,chicken,drugstore,
     restaurant, pizza, gasstation,supermarkt,
     skySmall,skyBig,apartments,house;

    // Start is called before the first frame update
    void Start()
    {
        generate();
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void generate()
    {
        int mainRoadLen = 10;
        int lanePieceLen = 20;
		for (int i = 0; i < mainRoadLen; i++) {
			Vector3 spawnPosition = new Vector3 (0.0f, 0.0f, i * lanePieceLen);
			Quaternion spawnRotation = Quaternion.identity;
            if(i==0 )
			    Instantiate (roadEnd, spawnPosition, Quaternion.Euler (0.0f, 180.0f, 0.0f));
            else if(i+1 == mainRoadLen )
			    Instantiate (roadEnd, spawnPosition, spawnRotation);
            else
			    Instantiate (roadStraight, spawnPosition, spawnRotation);

			spawnPosition = new Vector3 (15.0f, 0.0f, i * lanePieceLen);
			spawnRotation = Quaternion.Euler (0.0f, -90.0f, 0.0f);
			Instantiate (apartments, spawnPosition, spawnRotation);
		}
    }
}
