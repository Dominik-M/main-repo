/*
 * Copyright (C) 2018 Dominik Messerschmidt
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
package dmsr.anno.main;

import dmsr.anno.gui.MainFrame;
import dmsr.anno.gui.MainPanel;
import java.awt.Dimension;

/**
 *
 * @author Dominik Messerschmidt
 */
public class GameController
{

    public static final GameData DATA = new GameData();

    private GameController()
    {
    }

    public static void startNewGame()
    {
        DATA.init();
        MainPanel mainPanel = new MainPanel();
        mainPanel.setPreferredSize(new Dimension(400, 300));
        MainFrame.getInstance().setMainPanel(mainPanel);
    }

    public static void exit()
    {
        System.exit(0);
    }
}
