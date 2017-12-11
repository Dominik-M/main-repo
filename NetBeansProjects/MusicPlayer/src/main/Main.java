/*
 * Copyright (C) 2015 Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
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
package main;

import graphic.MainFrame;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        graphic.MainFrame.setLookAndFeel();
        MainFrame frame = new graphic.MainFrame();
        frame.setMainPanel(new graphic.ProgressPanel());
        frame.setVisible(true);
        sound.Sound.initClips();
        frame.setMainPanel(new graphic.SoundPlayerPanel());
    }
    
}
