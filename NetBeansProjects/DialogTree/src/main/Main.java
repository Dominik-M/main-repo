package main;

/**
 *
 * @author Dominik Messerschmidt
 */
public class Main
{

    public static final Node BARD = new Node("Bard");
    public static final Node DRUID = new Node("Druid");
    public static final Node ROGUE = new Node("Rogue");
    public static final Node PALADIN = new Node("Paladin");
    public static final Node MONK = new Node("Monk");
    public static final Node CLERIC = new Node("Cleric");
    public static final Node WARLOCK = new Node("Warlock");
    public static final Node SORCERER = new Node("Sorcerer");
    public static final Node WIZARD = new Node("Wizard");
    public static final Node RANGER = new Node("Ranger");
    public static final Node FIGHTER = new Node("Fighter");
    public static final Node BARBAR = new Node("Barbarian");

    public static final Node ANIMALS_BRANCH = new Node("Do you like animals?",
            new Node("I prefer magic creatures", WIZARD),
            new Node("I actually prefer animals to people", DRUID)
    );
    public static final Node MARTIAL_BRANCH = new Node("Do you know martial arts?",
            new Node("Nope", PALADIN),
            new Node("My fists hunger for Justice!", MONK)
    );
    public static final Node WEALTHY_BRANCH = new Node("Do you want to be wealthy?",
            new Node("I don't understand the question", ROGUE),
            new Node("Meh", MARTIAL_BRANCH)
    );
    public static final Node SPELLS_BRANCH = new Node("Do you prefer spells over melee?",
            new Node("Yes Melee is for losers", BARD),
            new Node("No, spells are bonus", WEALTHY_BRANCH)
    );
    public static final Node SNEAKY_BRANCH = new Node("Are you sneaky?",
            new Node("Not so much", SPELLS_BRANCH),
            new Node("I am the night!", ROGUE)
    );
    public static final Node ANIMALS_BRANCH2 = new Node("Do you like animals?",
            new Node("They're so FLUFFY!", DRUID),
            new Node("Eh, they're okay", SNEAKY_BRANCH)
    );
    public static final Node BORNMAGIC_BRANCH = new Node("Were you born with magic?",
            new Node("Not exactly", WARLOCK),
            new Node("Yes, it's in my Blood", SORCERER)
    );
    public static final Node MUSIC_BRANCH = new Node("Are you musically inclined?",
            new Node("Not really", ANIMALS_BRANCH),
            new Node("Yes! I write poetry too", BARD)
    );
    public static final Node STUDY_BRANCH = new Node("Do you like to study?",
            new Node("No, studying is for losers", BORNMAGIC_BRANCH),
            new Node("Yes, I love learning!", MUSIC_BRANCH)
    );
    public static final Node MAGIC_BRANCH = new Node("Are you religious?",
            new Node("Would you like to hear about my God?", CLERIC),
            new Node("No not really", STUDY_BRANCH)
    );
    public static final Node BOTH_BRANCH = new Node("Are you good with people?",
            new Node("No, not really", ANIMALS_BRANCH2),
            new Node("Yes, people usually like me", SNEAKY_BRANCH)
    );
    public static final Node RANGED_BRANCH = new Node("So, are you good with people?",
            new Node("Yes, people usually like me", SNEAKY_BRANCH),
            new Node("No, I prefer animals", RANGER)
    );
    public static final Node MONEYCAUSE_BRANCH = new Node("Are you civilized?",
            new Node("Yes, I mean I don't eat people or anything...", FIGHTER),
            new Node("No, I was raised in the wilds", BARBAR)
    );
    public static final Node MELEE_BRANCH = new Node("Do you fight for a cause?",
            new Node("Yes, my beliefs define me", MARTIAL_BRANCH),
            new Node("Is money a cause?", MONEYCAUSE_BRANCH)
    );
    public static final Node SMASH_BRANCH = new Node("Melee or Ranged?",
            new Node("Ranged", RANGED_BRANCH),
            new Node("Melee", MELEE_BRANCH)
    );

    private static final MainFrame FRAME = new MainFrame();
    private static final Node ROOT = new Node("Do you want to cast magic or smash things?",
            new Node("Cast Magic!", MAGIC_BRANCH),
            new Node("Can I do both?", BOTH_BRANCH),
            new Node("Me Smash!", SMASH_BRANCH)
    );

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        restart();
        FRAME.setVisible(true);
    }

    public static void restart()
    {
        FRAME.handleNode(ROOT);
    }

}
