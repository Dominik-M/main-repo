using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameController : MonoBehaviour {
	static GameController gameController = null;
	static GameObject gameControllerObject = null;

	public Vector3 spawnValues;
	public GameObject[] hazards;
	public GameObject enemy;
	public GameObject boss;
	public GameObject pickup;
	public GameObject player;
	public float spawnWait;
	public float startWait;
	public float waveWait;
	public float pickupchance;
	public float pickupWait;
	public int hazardCount;
	public int enemyCount;
	public Camera mainCamera;
	public int score;
	public GUIText scoreText;
	public GUIText restartText;
	public GUIText gameoverText;
	public GUIText waveText;
	public SimpleTouchAreaButton restartButton;

	protected int wave;
	protected bool gameover;

	void Start(){
		gameover = false;
		score = 0;
		wave = 1;
		restartText.text = "";
		gameoverText.text = "";
		waveText.text = "Wave " + wave;
		UpdateScore ();
		StartCoroutine( spawnHazards ());
		StartCoroutine( spawnWaves ());
		StartCoroutine( spawnPickUps ());
	}

	void Update()
	{
		if (Input.GetKeyDown (KeyCode.PageDown))
		{
			Debug.Log ("Zooming in");
			mainCamera.GetComponent<CameraController> ().zoomIn ();
		} else if (Input.GetKeyDown (KeyCode.PageUp))
		{
			Debug.Log ("Zooming out");
			mainCamera.GetComponent<CameraController> ().zoomOut ();
		}
		if (gameover)
		{
			if (Input.GetKeyDown (KeyCode.R) || restartButton.Touched())
			{
				Application.LoadLevel (Application.loadedLevel);
			}
		}
	}

	IEnumerator spawnPickUps(){
		yield return new WaitForSeconds (startWait);
		while (!gameover) {
			if (Random.value < pickupchance) {
				Vector3 spawnPosition = new Vector3 (Random.Range (-spawnValues.x, spawnValues.x), 0.0f, Random.Range (-spawnValues.z, spawnValues.z));
				Quaternion spawnRotation = Quaternion.identity;
				GameObject instance = Instantiate (pickup, spawnPosition, spawnRotation);
				instance.GetComponent<PickUp> ().type = Random.value < 0.5 ? PickUp.PickUpType.UPGRADE : PickUp.PickUpType.HEALTH;
			}
			yield return new WaitForSeconds (pickupWait);
		}
	}

	IEnumerator spawnHazards(){
		yield return new WaitForSeconds (startWait);
		while (!gameover) {
			for (int i = 0; i < hazardCount; i++) {
				GameObject hazard = hazards[Random.Range (0, hazards.Length)];
				Vector3 spawnPosition = new Vector3 (Random.Range (-spawnValues.x, spawnValues.x), 0.0f, Random.Range (-spawnValues.z, spawnValues.z));
				Quaternion spawnRotation = Quaternion.identity;
				Instantiate (hazard, spawnPosition, spawnRotation);
				yield return new WaitForSeconds (spawnWait);
			}
			yield return new WaitForSeconds (waveWait);
		}
	}

	IEnumerator spawnWaves(){
		LinkedList<GameObject> spawnedEnemys = new LinkedList<GameObject>();
		yield return new WaitForSeconds (startWait);
		while (!gameover) {
			// spawn enemys
			if (wave % 5 == 0) {
				Vector3 spawnPosition = new Vector3 (Random.Range (-spawnValues.x, spawnValues.x), 0.0f, Random.Range (-spawnValues.z, spawnValues.z));
				Quaternion spawnRotation = Quaternion.identity;
				GameObject instance = Instantiate (boss, spawnPosition, spawnRotation);
				// instance.GetComponent<AlienAI> ().target = player;
				// instance.GetComponent<WeaponController> ().weapon = GetRandomWeapon ();
				spawnedEnemys.AddLast(instance);
				yield return new WaitForSeconds (spawnWait);
			}
			for (int i = 0; i < enemyCount+wave; i++) {
				Vector3 spawnPosition = new Vector3 (Random.Range (-spawnValues.x, spawnValues.x), 0.0f, Random.Range (-spawnValues.z, spawnValues.z));
				Quaternion spawnRotation = Quaternion.identity;
				GameObject instance = Instantiate (enemy, spawnPosition, spawnRotation);
				instance.GetComponent<AlienAI> ().target = player;
				instance.GetComponent<WeaponController> ().weapon = GetRandomWeapon ();
				spawnedEnemys.AddLast(instance);
				yield return new WaitForSeconds (spawnWait);
			}

			// check if all killed
			bool living;
			do {
				yield return new WaitForSeconds (spawnWait);
				living = false;
				foreach(GameObject obj in spawnedEnemys){
					if(obj != null && obj.activeInHierarchy)
					{
						living = true;
						break;
					}
				}
			} while(living);
				
			wave++;
			waveText.text = "Wave " + wave;
			spawnedEnemys.Clear ();
			yield return new WaitForSeconds (waveWait);
		}
	}

	public void AddScore (int newScoreValue)
	{
		score += newScoreValue;
		UpdateScore ();
	}

	void UpdateScore ()
	{
		scoreText.text = "Score: " + score;
	}

	public void GameOver(){
		gameover = true;
		gameoverText.text = "GAME OVER";
		restartText.text = "Press 'R' to restart";
	}

	public static Weapon GetRandomWeapon(){
		Weapon weapon = new Weapon ();
		switch ((int)(Random.value * 5)) {
		case 1:
			weapon.type = WeaponType.MULTI_SHOT;
			break;
		case 2:
			weapon.type = WeaponType.MULTI_SHOT_RAND;
			break;
		case 3:
			weapon.type = WeaponType.SALVO;
			break;
		default:
			weapon.type = WeaponType.SINGLE_SHOT;
			break;
		}
		weapon.fireRate = 0.5f + 2.0f * Random.value;
		weapon.shotCount = (int)(1.0f + Random.value * 5.5f);
		weapon.spreadAngle = 10.0f + 30.0f * Random.value;
		return weapon;
	}

	public static GameController getInstance(){

		if (gameController == null) {
			gameControllerObject = GameObject.FindWithTag ("GameController");
			if (gameControllerObject != null) {
				gameController = gameControllerObject.GetComponent <GameController> ();
			}
			if (gameController == null) {
				Debug.Log ("Cannot find 'GameController' script");
			}
		}
		return gameController;
	}
}
