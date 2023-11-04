using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;


public class GameManager : MonoBehaviour {
	
	public string sceneNameArcade, sceneNameAdventure;

	public void loadSceneArcade()
	{
		SceneManager.LoadScene (sceneNameArcade);
	}

	public void loadSceneAdventure()
	{
		SceneManager.LoadScene (sceneNameAdventure);
	}

}
