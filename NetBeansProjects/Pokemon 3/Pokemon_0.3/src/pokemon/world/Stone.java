/*
 * Copyright (C) 2016 Dominik
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package pokemon.world;

import image.ImageIO.ImageFile;
import utils.Decision;
import utils.DecisionCallback;
import utils.IO;

/**
 *
 * @author Dominik
 */
public class Stone extends ItemObjekt
{

    /**
     *
     * @param img
     */
    public Stone(ImageFile img)
    {
        super(img, false);
    }

    /**
     *
     */
    @Override
    public void benutzt()
    {
        if (this.getItem() == null)
        {
            IO.println("Hallo, ich bin ein Stein", IO.MessageType.IMPORTANT);
            IO.promptDecision(Decision.YES_NO, new DecisionCallback()
            {
                @Override
                public void decisionEntered(int index)
                {
                    IO.println("decisionEntered(): " + index, IO.MessageType.DEBUG);
                    if (index == 0)
                    {
                        IO.println("Richtig! Ich bin ein Stein", IO.MessageType.IMPORTANT);
                    }
                    else
                    {
                        IO.println("Doch! Ich bin ein Stein!", IO.MessageType.IMPORTANT);
                    }
                }
            });
        }
    }
}
