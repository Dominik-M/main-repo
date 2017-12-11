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
package main;

import graphic.GameGrid;
import graphic.InputConfig;
import graphic.MainFrame;
import graphic.ProgressPanel;
import image.ImageIO;
import java.io.File;
import pokemon.GameData;
import pokemon.Pokemon;
import pokemon.PokemonBasis;
import sound.Sound;
import utils.Constants;
import utils.IO;
import utils.Text;

/**
 * Created 09.03.2016
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 *
 */
public class Main
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        writeAllFiles();
        MainFrame.setNimbusLookAndFeel();
        MainFrame.FRAME.setMainPanel(ProgressPanel.getInstance());
        MainFrame.FRAME.setVisible(true);
        Sound.initSounds();
        if (Constants.DEBUG_ENABLE)
        {
            ImageIO.initAllSprites();
            ImageIO.storeData();
        }
        else
        {
            ImageIO.loadData();
        }

        // TODO Enter your application logic here
        utils.IO.println(Text.HELLO.toString(), utils.IO.MessageType.IMPORTANT);
        File savefile = new File(Constants.SAVE_FILENAME);
        if (savefile.exists() && GameData.load(savefile) != null)
        {
            IO.println("Spielstand geladen", IO.MessageType.IMPORTANT);
        }
        else
        {
            GameData.newGame("Peter");
            GameData.getCurrentGame().getPlayer().givePokemon(new Pokemon(PokemonBasis.GLUMANDA, 5));
            GameData.getCurrentGame().getPlayer().givePokemon(new Pokemon(PokemonBasis.BISASAM, 5));
            // GameData.getCurrentGame().getPlayer().givePokemon(new Pokemon(PokemonBasis.GLUMANDA, 100));
        }
        MainFrame.FRAME.setMainPanel(GameGrid.getInstance());
        sound.Sound.playSoundClip(new java.io.File(GameData.getCurrentGame().getPos().getSoundfilename()), Constants.SOUND_LOOP_CONTINUOUSLY);
        if (GameData.getCurrentGame().getRivale().isDefeated())
        {
            GameData.getCurrentGame().getRivale().heal();
        }
        // GameData.getCurrentGame().startFight(GameData.getCurrentGame().getRivale());
        //MainFrame.FRAME.setMainPanel(new graphic.FightPanel(new Fight(GameData.getCurrentGame().getPlayer(),GameData.getCurrentGame().getRivale(), true)));
    }

    /**
     *
     */
    public static void reset()
    {
        IO.println("Reset trigerred", IO.MessageType.DEBUG);
        main(null);
    }

    /**
     *
     */
    public static void writeAllFiles()
    {
        utils.IO.initLogs();
        if (!InputConfig.CONFIG_FILE.exists())
        {
            InputConfig.saveConfig();
        }
        else
        {
            InputConfig.loadConfig();
        }
        Text.createAllLanguageFiles();
        utils.Settings.saveSettings();
        java.io.File dir = new java.io.File(utils.Constants.IMAGE_DIRECTORY);
        if (!dir.exists() && Constants.DEBUG_ENABLE)
        {
            dir.mkdir();
        }
        dir = sound.Sound.SOUNDFILE;
        if (!dir.exists() && Constants.DEBUG_ENABLE)
        {
            dir.mkdir();
        }
    }
}
