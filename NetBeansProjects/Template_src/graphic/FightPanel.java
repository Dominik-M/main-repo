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

import static graphic.GameGrid.HEIGHT;
import static graphic.GameGrid.WIDTH;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import utils.Settings;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 22.07.2016
 */
public class FightPanel extends MainPanel {

    @Override
    public BufferedImage createGUI() {
        BufferedImage gui = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = gui.createGraphics();
        g.setColor(Settings.getSettings().backgroundColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        drawInterface(g);
        drawOutput(g);
        return gui;
    }

    private void drawInterface(Graphics2D g) {
        //TODO draw Interface
    }
}
