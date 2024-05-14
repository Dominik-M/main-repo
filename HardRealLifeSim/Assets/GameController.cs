using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GameController : MonoBehaviour
{
	static GameController gameController = null;
	static GameObject gameControllerObject = null;
    private int score, cash, selectedInteraction;
    private List<InteractionController> interactablesInRange;
    public string currency = "€";
    public Text cashmoneyText, affordanceText, scoreText;


    // Start is called before the first frame update
    void Start()
    {
        score = 0;
        cash = 0;
        interactablesInRange = new List<InteractionController>();
        UpdateUI();
    }

    // Update is called once per frame
    void Update()
    {
    }

    void UpdateUI()
    {
        UpdateAffordance();
        UpdateCashText();
        UpdateScoreText();
    }

    public int getCash()
    {
        return cash;
    }

    public void setCash(int cashnew)
    {
        cash = cashnew;
        UpdateCashText();
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int scorenew)
    {
        score = scorenew;
        UpdateScoreText();
    }

    public List<InteractionController> getInteractablesInRange()
    {
        return interactablesInRange;
    }

    public void addInteractable(InteractionController ic)
    {
        interactablesInRange.Add(ic);
        UpdateAffordance();
    }

    public void removeInteractable(InteractionController ic)
    {
        interactablesInRange.Remove(ic);
        UpdateAffordance();
    }

    void UpdateCashText()
    {
        int aftercomma = cash % 100;
        cashmoneyText.text = "Cash: " + (cash / 100) + "," + ((aftercomma < 10) ? ("0" + aftercomma) : ("" + aftercomma)) + currency;
    }

    void UpdateAffordance()
    {
        if (interactablesInRange.Count > 0)
        {
            if (selectedInteraction >= interactablesInRange.Count || selectedInteraction < 0)
            {
                selectedInteraction = 0;
            }
            affordanceText.text = getSelectedInteractionAffordance();
        }
        else
        {
            affordanceText.text = "";
        }
    }

    string getSelectedInteractionAffordance()
    {
        string text = "NOT FOUND";
        int i = 0;
        foreach (InteractionController ic in interactablesInRange)
        {
            foreach (InteractionController.Type ict in ic.interactions)
            {
                if (i == selectedInteraction)
                {
                    text = ict + " " + ic.objectName;
                    return text;
                }
                i++;
            }
        }
        return text;
    }

    void UpdateScoreText()
    {
        scoreText.text = "Score: " + score;
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
