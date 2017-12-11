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

import java.util.LinkedList;
import pokemon.attacks.Attack;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 06.08.2016
 */
public interface FightKI
{

    /**
     *
     * @param fight
     * @return
     */
    public FightAction getAction(Fight fight);

    /**
     * Default Trainer KI. Does randomly use attacks.
     */
    public static final pokemon.FightKI TRAINER_KI_DUMMY = new pokemon.FightKI()
    {

        @Override
        public pokemon.FightAction getAction(Fight fight)
        {
            Pokemon mPok = fight.getPokOnTurn();
            Trainer mTrainer = fight.isPlayersTurn() ? fight.getPlayer() : (fight.isTrainerFight ? fight.getOpponentTrainer() : null);
            int index = 0;
            if (mPok == null)
            {
                return new pokemon.FightAction(FightAction.ActionType.NONE, 0);
            }
            else if (mPok.isBsg() && mTrainer != null)
            {
                for (index = 0; index < mTrainer.getPoks().length; index++)
                {
                    if (!mTrainer.getPoks()[index].isBsg())
                    {
                        return new pokemon.FightAction(FightAction.ActionType.SWITCH, index);
                    }
                }
            }
            LinkedList<Attack> attList = new LinkedList<>();
            for (Attack a : mPok.getAtacks())
            {
                if (a != null && a.getAP() > 0)
                {
                    attList.add(a);
                }
            }
            index = (int) (Math.random() * attList.size());
            return new pokemon.FightAction(FightAction.ActionType.ATTACK, index);
        }
    };
}
