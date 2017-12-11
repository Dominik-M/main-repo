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
package graphic;

import java.awt.Graphics2D;

/**
 * Created 04.08.2016
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 *
 */
public class MainMenuPanel extends MainPanel {

    @Override
    protected void drawGUI(Graphics2D g) {

    }

    @Override
    public boolean keyPressed(InputConfig.Key key) {
        return false;
    }

    @Override
    public void keyReleased(InputConfig.Key key) {

    }

}
