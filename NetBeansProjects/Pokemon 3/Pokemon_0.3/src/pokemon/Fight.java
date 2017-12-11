/**
 * Copyright (C) 2016 Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package pokemon;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pokemon.Pokemon.Stat;
import pokemon.attacks.Attack;
import pokemon.attacks.Effect;
import pokemon.world.Item;
import pokemon.world.Ort;
import utils.Constants;
import utils.Dictionary;
import utils.IO;
import utils.Utilities;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 28.07.2016
 */
public class Fight extends utils.SerializableReflectObject
{

    /**
     *
     */
    public enum State
    {

        START_FIGHT,
        START_ROUND,
        PLAYERS_TURN,
        OPPONENT_TURN,
        PLAYER_KO,
        OPPONENT_KO,
        END_ROUND,
        END_VICTORY,
        END_DEFEAT,
        END_DRAW,
        END,
        INVALID;
    }

    /**
     *
     */
    public final boolean isTrainerFight;
    private State state;
    public final boolean fleeAllowed;
    private boolean playerFirst;
    private final Spieler player;
    private Trainer opponentTrainer;
    private Pokemon playersPok, opponentPok;
    private final LinkedList<Pokemon> allPlayerPoks = new LinkedList<>();
    private FightAction playerAction, opponentAction;
    private FightKI opponentKI;
    /**
     * Flag indicating if state changed since last state machine execution. Used
     * like a semaphore to ensure determinism
     */
    private boolean stateChanged;

    /**
     * Create a Fight against another Trainer.
     *
     * @param player
     * @param opponent
     * @param fleeAllowed
     */
    public Fight(Spieler player, Trainer opponent, boolean fleeAllowed)
    {
        this.player = player;
        this.opponentTrainer = opponent;
        this.fleeAllowed = fleeAllowed;
        isTrainerFight = true;
        init();
    }

    /**
     * Create a Fight against a wild Pokemon.
     *
     * @param player
     * @param opponent
     * @param fleeAllowed
     */
    public Fight(Spieler player, Pokemon opponent, boolean fleeAllowed)
    {
        this.player = player;
        this.opponentPok = opponent;
        this.fleeAllowed = fleeAllowed;
        isTrainerFight = false;
        init();
    }

    private void init()
    {
        stateChanged = false;
        opponentKI = FightKI.TRAINER_KI_DUMMY;
        if (player == null)
        {
            throw new java.lang.IllegalArgumentException("Player must not be null");
        }
        if (isTrainerFight)
        {
            if (opponentTrainer == null)
            {
                throw new java.lang.IllegalArgumentException("Opponent Trainer must not be null");
            }
            opponentPok = opponentTrainer.getFirst();
        }
        playersPok = player.getFirst();
        allPlayerPoks.add(playersPok);
        if (opponentPok == null)
        {
            throw new java.lang.IllegalArgumentException("Opponents Pokemon must not be null");
        }
        if (playersPok == null)
        {
            throw new java.lang.IllegalArgumentException("Players Pokemon must not be null");
        }
        setState(State.START_FIGHT);
    }

    public synchronized void doStep()
    {
        stateChanged = false;
        try
        {
            IO.println("Executing FightStateMachine: State = " + state.name(), IO.MessageType.DEBUG);
            switch (state)
            {
                case START_FIGHT:
                    if (isTrainerFight)
                    {
                        IO.println(IO.translate("START_FIGHT") + " " + opponentTrainer, IO.MessageType.IMPORTANT);
                    }
                    else
                    {
                        IO.println(IO.translate("START_FIGHT_WILD") + " " + opponentPok, IO.MessageType.IMPORTANT);
                    }
                    setState(State.START_ROUND);
                    break;
                case START_ROUND:
                    playerFirst = playersPok.getStateValue(Pokemon.Stat.INIT) >= opponentPok.getStateValue(Pokemon.Stat.INIT);
                    if (playerFirst)
                    {
                        setState(State.PLAYERS_TURN);
                    }
                    else
                    {
                        setState(State.OPPONENT_TURN);
                    }
                    break;
                case PLAYERS_TURN:
                    if (playerAction != null)
                    {
                        if (doAction(playerAction) && !stateChanged)
                        {
                            setState(playerFirst ? State.OPPONENT_TURN : State.END_ROUND);
                        }
                    }
                    else
                    {
                        IO.println("No Action by player", IO.MessageType.DEBUG);
                    }
                    playerAction = null;
                    break;
                case OPPONENT_TURN:
                    opponentAction = opponentKI.getAction((Fight) this.clone());
                    if (opponentAction != null)
                    {
                        if (!doAction(opponentAction))
                        {
                            IO.println("Opponent KI offered an invalid action!", IO.MessageType.ERROR);
                        }
                    }
                    else
                    {
                        IO.println("Opponent KI offered an invalid action!", IO.MessageType.ERROR);
                    }
                    if (!stateChanged)
                    {
                        setState(playerFirst ? State.END_ROUND : State.PLAYERS_TURN);
                    }
                    break;
                case END_ROUND:
                    int dmg;
                    if (playersPok.getStatus() == Status.GFT)
                    {
                        dmg = playersPok.getMaxKp() / 8;
                        dmg = dmg <= 0 ? 1 : dmg;
                        playersPok.damage(dmg);
                        IO.println(playersPok + " " + IO.translate("POISON_DAMAGE"), IO.MessageType.IMPORTANT);
                        IO.println(playersPok + " took " + dmg + "KP damage by poison", IO.MessageType.DEBUG);
                    }
                    else if (playersPok.getStatus() == Status.BRT)
                    {
                        dmg = playersPok.getMaxKp() / 8;
                        dmg = dmg <= 0 ? 1 : dmg;
                        playersPok.damage(dmg);
                        IO.println(playersPok + " " + IO.translate("BURN_DAMAGE"), IO.MessageType.IMPORTANT);
                        IO.println(playersPok + " took " + dmg + "KP damage by burn", IO.MessageType.DEBUG);
                    }
                    if (opponentPok.getStatus() == Status.GFT)
                    {
                        dmg = opponentPok.getMaxKp() / 8;
                        dmg = dmg <= 0 ? 1 : dmg;
                        opponentPok.damage(dmg);
                        IO.println(IO.translate("OPPONENTS") + opponentPok + " " + IO.translate("POISON_DAMAGE"), IO.MessageType.IMPORTANT);
                        IO.println(opponentPok + " took " + dmg + "KP damage by poison", IO.MessageType.DEBUG);
                    }
                    else if (opponentPok.getStatus() == Status.BRT)
                    {
                        dmg = opponentPok.getMaxKp() / 8;
                        dmg = dmg <= 0 ? 1 : dmg;
                        opponentPok.damage(dmg);
                        IO.println(IO.translate("OPPONENTS") + opponentPok + " " + IO.translate("BURN_DAMAGE"), IO.MessageType.IMPORTANT);
                        IO.println(opponentPok + " took " + dmg + "KP damage by burn", IO.MessageType.DEBUG);
                    }
                    checkHPs();
                    setState(State.START_ROUND);
                    break;
                case PLAYER_KO:
                    // wait until Player switched to next Pokemon or End if all defeated
                    if (player.isDefeated())
                    {
                        IO.println(player + IO.translate("IS_DEFEATED1"), IO.MessageType.IMPORTANT);
                        IO.println(player + IO.translate("IS_DEFEATED2"), IO.MessageType.IMPORTANT);
                        setState(State.END_DEFEAT);
                    }
                    else if (playerAction != null && (playerAction.TYPE == FightAction.ActionType.SWITCH || playerAction.TYPE == FightAction.ActionType.FLEE))
                    {
                        if (doAction(playerAction) && !stateChanged)
                        {
                            setState(State.END_ROUND);
                        }
                    }
                    else
                    {
                        IO.println("No Action by player", IO.MessageType.DEBUG);
                    }
                    playerAction = null;
                    break;
                case OPPONENT_KO:
                    // let Opponent switch to next Pokemon or End if all defeated
                    opponentAction = opponentKI.getAction((Fight) this.clone());
                    if (opponentTrainer == null || opponentTrainer.isDefeated())
                    {
                        setState(State.END_VICTORY);
                    }
                    else if (opponentAction != null && opponentAction.TYPE == FightAction.ActionType.SWITCH)
                    {
                        if (doAction(playerAction) && !stateChanged)
                        {
                            setState(State.END_ROUND);
                        }
                    }
                    else
                    {
                        IO.println("No Action by opponent", IO.MessageType.DEBUG);
                    }
                    return;
                case END_VICTORY:
                    int score;
                    if (isTrainerFight)
                    {
                        IO.println(opponentTrainer + " " + IO.translate("POK_KO"), IO.MessageType.IMPORTANT);
                        score = opponentTrainer.getGeld();
                    }
                    else
                    {
                        score = (int) ((opponentPok.getTotalDV() * opponentPok.getLvl()) * Math.random() + 1);
                    }
                    player.setGeld(player.getGeld() + score);
                    IO.println(player + " " + IO.translate("GETS") + " " + score + IO.translate("§"), IO.MessageType.IMPORTANT);
                    state = State.END;
                    return;
                case END_DEFEAT:
                    Ort startPos = GameData.getCurrentGame().getLastSafePlace();
                    GameData.getCurrentGame().switchOrt(startPos.getStartX(), startPos.getStartY(), startPos);
                    player.heal();
                    state = State.END;
                    return;
                case END_DRAW:
                    state = State.END;
                    return;
                case END:
                    for (Pokemon pok : allPlayerPoks)
                    {
                        pok.resetBoosts();
                    }
                    if (isTrainerFight)
                    {
                        for (Pokemon pok : opponentTrainer.getPoks())
                        {
                            pok.resetBoosts();
                        }
                    }
                    else
                    {
                        opponentPok.resetBoosts();
                    }
                    return;
                default:
                    throw new java.lang.IllegalStateException("State = " + state);
            }
        } catch (Exception ex)
        {
            IO.printException(ex);
            IO.println("Fight aborted", IO.MessageType.DEBUG);
        }
    }

    /**
     * Performs the given fight action.
     *
     * @param a The FightAction to perform
     * @return true if the action is valid
     */
    private boolean doAction(FightAction a)
    {
        boolean actionValid = false;
        Pokemon pok = null;
        switch (a.TYPE)
        {
            case ATTACK:
                pok = getPokOnTurn();
                if (pok != null)
                {
                    Attack att = pok.getAttack(a.INDEX);
                    if (att != null)
                    {
                        if (att.getAP() > 0)
                        {
                            if (pok.getStatus() == pokemon.Status.SLF)
                            {
                                actionValid = utils.Utilities.randomEvent(Constants.WAKEUP_PROPABILITY);
                                if (actionValid)
                                {
                                    IO.println(pok.toString() + IO.translate("WAKEUP"), IO.MessageType.IMPORTANT);
                                }
                                else
                                {
                                    IO.println(pok.toString() + IO.translate("SLEEPING"), IO.MessageType.IMPORTANT);
                                }
                            }
                            else if (pok.getStatus() == pokemon.Status.PAR)
                            {
                                actionValid = utils.Utilities.randomEvent(Constants.PARRESIST_PROPABILITY);
                                if (actionValid)
                                {
                                    IO.println(pok.toString() + " kann trotz paralyse angreifen", IO.MessageType.DEBUG);
                                }
                                else
                                {
                                    IO.println(pok.toString() + IO.translate("PARALYZED"), IO.MessageType.IMPORTANT);
                                }
                            }
                            else if (pok.getStatus() == pokemon.Status.GFR)
                            {
                                actionValid = false;
                                IO.println(pok.toString() + IO.translate("FROZEN"), IO.MessageType.IMPORTANT);
                            }
                            else
                            {
                                actionValid = true;
                            }
                            if (actionValid)
                            {
                                // can attack
                                if (isOpponentsTurn())
                                {
                                    IO.println(IO.translate("OPPONENTS") + pok + " " + IO.translate("USES") + " " + att, IO.MessageType.IMPORTANT);
                                }
                                else
                                {
                                    IO.println(pok + " " + IO.translate("USES") + " " + att, IO.MessageType.IMPORTANT);
                                }
                                att.setAP(att.getAP() - 1);
                                // check if hit
                                int p = att.BASE.GENA * pok.getGena() / getPokNotOnTurn().getFlu();
                                if (utils.Utilities.randomEvent(p == 0 ? 1 : p))
                                {
                                    if (att.BASE.DMG != 0)
                                    {
                                        int damage = utils.Utilities.calculateDamage(att, pok, getPokNotOnTurn());
                                        getPokNotOnTurn().damage(damage);
                                        IO.println("Calculated " + damage + " KP damage", IO.MessageType.DEBUG);
                                    }
                                    for (Effect e : att.BASE.EFFECTS)
                                    {
                                        if (Utilities.randomEvent(e.P))
                                        {
                                            e.apply(att.BASE.SELF ? pok : getPokNotOnTurn());
                                        }
                                    }
                                    checkHPs();
                                }
                                else
                                {
                                    IO.println(IO.translate("ATTACK_MISSED"), IO.MessageType.IMPORTANT);
                                }
                            }

                            actionValid = true;
                        }
                        else
                        {
                            IO.println("doAction(): Out of AP!", IO.MessageType.DEBUG);
                        }
                    }
                    else
                    {
                        IO.println("doAction():  Attack must not be null!", IO.MessageType.DEBUG);
                    }
                }
                else
                {
                    IO.println("doAction(): Pok on turn must not be null!", IO.MessageType.DEBUG);
                }
                break;
            case ITEM:
                actionValid = useItem(a.INDEX);
                // MainFrame.FRAME.promptItemDecision(ITEM_USE_IN_COMBAT);
                break;
            case SWITCH:
                try
                {
                    if (isPlayersTurn() || state == State.PLAYER_KO)
                    {
                        pok = player.getPoks()[a.INDEX];
                        if (pok != null)
                        {
                            if (!pok.isBsg())
                            {
                                if (!playersPok.isBsg())
                                {
                                    IO.println(player.toString() + ": " + IO.translate("FIGHT_POK_RETREAT") + " " + playersPok, IO.MessageType.IMPORTANT);
                                }
                                IO.println(player.toString() + ": " + IO.translate("GO") + " " + pok + " " + IO.translate("FIGHT_POK_SWITCH"), IO.MessageType.IMPORTANT);
                                setPlayersPok(pok);
                                actionValid = true;
                            }
                            else
                            {
                                IO.println(pok + " ist besiegt und kann nicht kämpfen!", IO.MessageType.DEBUG);
                            }
                        }
                    }
                    else if (isOpponentsTurn() && opponentTrainer != null)
                    {
                        pok = opponentTrainer.getPoks()[a.INDEX];
                        if (pok != null)
                        {
                            if (!pok.isBsg())
                            {
                                if (!opponentPok.isBsg())
                                {
                                    IO.println(opponentTrainer + ": " + IO.translate("FIGHT_POK_RETREAT") + " " + opponentPok, IO.MessageType.IMPORTANT);
                                }
                                IO.println(opponentTrainer + ": " + IO.translate("GO") + " " + pok + " " + IO.translate("FIGHT_POK_SWITCH"), IO.MessageType.IMPORTANT);
                                setOpponentPok(pok);
                                actionValid = true;
                            }
                            else
                            {
                                IO.println(pok + " ist besiegt und kann nicht kämpfen!", IO.MessageType.DEBUG);
                            }
                        }
                    }
                } catch (Exception ex)
                {
                    IO.printException(ex);
                    actionValid = false;
                }
                break;
            case FLEE:
                if (canFlee())
                {
                    if (isPlayersTurn())
                    {
                        IO.println(player.toString() + IO.translate("FLEED"), IO.MessageType.IMPORTANT);
                    }
                    else
                    {
                        IO.println(IO.translate("OPPONENTS") + opponentPok + IO.translate("FLEED"), IO.MessageType.IMPORTANT);
                    }
                    setState(State.END_DRAW);
                    actionValid = true;
                }
                else
                {
                    IO.println(IO.translate("CANNOT_FLEE"), IO.MessageType.IMPORTANT);
                }
                break;
        }
        return actionValid;
    }

    private boolean useItem(int index)
    {
        boolean retVal = false;
        Item item = player.getItem(index);
        if (item != null)
        {
            IO.println("Player uses " + item, IO.MessageType.IMPORTANT);
            switch (item.TYP)
            {
                case POTION:
                    if (playersPok.getKp() < playersPok.getMaxKp() && playersPok.getKp() > 0)
                    {
                        int n = playersPok.heal(item.getValue());
                        IO.println(player + " " + IO.translate("USES") + " " + item, IO.MessageType.IMPORTANT);
                        IO.println(playersPok + " erhält " + n + " KP", IO.MessageType.IMPORTANT);
                        player.removeItem(index);
                        retVal = true;
                    }
                    else
                    {
                        IO.println("Das hätte keinen Effekt.", IO.MessageType.IMPORTANT);
                    }
                    break;
                case POKEBALL:
                    if (isTrainerFight)
                    {
                        IO.println("Pokemon stehlen ist nicht erlaubt!", IO.MessageType.IMPORTANT);
                    }
                    else
                    {
                        IO.println(player + " " + IO.translate("USES") + " " + item, IO.MessageType.IMPORTANT);
                        //TODO calculate catch propability
                        int p = Utilities.calculateCatchPropability(item.getValue(), opponentPok.getLvl(), 1);
                        if (Utilities.randomEvent(p))
                        {
                            IO.println(IO.translate("GREAT") + "!", IO.MessageType.IMPORTANT);
                            IO.println(opponentPok + " " + IO.translate("CAUGHT"), IO.MessageType.IMPORTANT);
                            if (player.givePokemon(opponentPok))
                            {
                                IO.println(player + " " + IO.translate("GETS") + " " + opponentPok, IO.MessageType.IMPORTANT);
                            }
                            setState(State.END_DRAW);
                        }
                        player.removeItem(index);
                    }
                    break;
                default:
                    IO.println("Das kannst du jetzt nicht benutzen.", IO.MessageType.IMPORTANT);
            }
        }
        return retVal;
    }

    private boolean checkHPs()
    {
        if (!stateChanged)
        {
            if (opponentPok.getKp() <= 0)
            {
                IO.println(IO.translate("OPPONENTS") + opponentPok + " " + IO.translate("POK_KO"), IO.MessageType.IMPORTANT);
                opponentBsg();
                setState(State.OPPONENT_KO);
            }
            if (playersPok.getKp() <= 0)
            {
                IO.println(playersPok + " " + IO.translate("POK_KO"), IO.MessageType.IMPORTANT);
                setState(State.PLAYER_KO);
            }
            return stateChanged;
        }
        return false;
    }

    private void opponentBsg()
    {
        // add FP
        Stat maxStat = Stat.ATK;
        int maxValue = opponentPok.getStateValue(maxStat);
        for (Stat stat : Stat.values())
        {
            if (stat != Stat.KP)
            {
                int value = opponentPok.getStateValue(stat);
                if (value > maxValue)
                {
                    maxValue = value;
                    maxStat = stat;
                }
            }
        }
        int fp = opponentPok.getBasis().DELTAXP / 10;
        playersPok.addFP(maxStat, fp);
        IO.println(playersPok + " erhält " + fp + " " + maxStat + " FP", IO.MessageType.IMPORTANT);
        // add XP
        int xp = opponentPok.getBenXp();
        xp = opponentTrainer == null ? xp / 2 : xp;
        IO.println(playersPok + " erhält " + xp + " EP", IO.MessageType.IMPORTANT);
        playersPok.addXp(xp * 3);
    }

    /**
     *
     * @return
     */
    public Pokemon getPokOnTurn()
    {
        if (isPlayersTurn())
        {
            return playersPok;
        }
        else if (isOpponentsTurn())
        {
            return opponentPok;
        }
        else
        {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public Pokemon getPokNotOnTurn()
    {
        if (isPlayersTurn())
        {
            return opponentPok;
        }
        else if (isOpponentsTurn())
        {
            return playersPok;
        }
        else
        {
            return null;
        }
    }

    public boolean canFlee()
    {
        return fleeAllowed; // TODO && init >= opp.init
    }

    public boolean isPlayersTurn()
    {
        return state == State.PLAYERS_TURN;
    }

    public boolean isOpponentsTurn()
    {
        return state == State.OPPONENT_TURN;
    }

    public boolean isEnd()
    {
        return state == State.END || state == State.END_DEFEAT || state == State.END_VICTORY || state == State.END_DRAW;
    }

    public State getState()
    {
        return state;
    }

    private void setState(State state)
    {
        if (!stateChanged && (this.state != state))
        {
            this.state = state;
            stateChanged = true;
        }
    }

    public boolean getPlayerFirst()
    {
        return playerFirst;
    }

    public void setPlayerFirst(boolean playerFirst)
    {
        this.playerFirst = playerFirst;
    }

    public Spieler getPlayer()
    {
        return player;
    }

    public Trainer getOpponentTrainer()
    {
        return opponentTrainer;
    }

    public void setOpponentTrainer(Trainer opponentTrainer)
    {
        this.opponentTrainer = opponentTrainer;
    }

    public Pokemon getPlayersPok()
    {
        return playersPok;
    }

    public void setPlayersPok(Pokemon playersPok)
    {
        this.playersPok = playersPok;
        if (!allPlayerPoks.contains(this.playersPok))
        {
            allPlayerPoks.add(this.playersPok);
        }
    }

    public Pokemon getOpponentPok()
    {
        return opponentPok;
    }

    public void setOpponentPok(Pokemon opponentPok)
    {
        this.opponentPok = opponentPok;
    }

    public FightAction getPlayerAction()
    {
        return playerAction;
    }

    public void setPlayerAction(FightAction playerAction)
    {
        this.playerAction = playerAction;
    }

    public FightAction getOpponentAction()
    {
        return opponentAction;
    }

    public void setOpponentAction(FightAction opponentAction)
    {
        this.opponentAction = opponentAction;
    }

    public FightKI getOpponentKI()
    {
        return opponentKI;
    }

    public void setOpponentKI(FightKI opponentKI)
    {
        this.opponentKI = opponentKI;
    }

    @Override
    public Dictionary<String, Object> getAttributes()
    {
        Dictionary<String, Object> values = new Dictionary<>();
        for (Field f : this.getClass().getDeclaredFields())
        {
            try
            {
                if (!Modifier.isStatic(f.getModifiers()))
                {
                    values.add(f.getName(), f.get(this));
                }
            } catch (IllegalArgumentException ex)
            {
                Logger.getLogger(Fight.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex)
            {
                Logger.getLogger(Fight.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return values;
    }
}
