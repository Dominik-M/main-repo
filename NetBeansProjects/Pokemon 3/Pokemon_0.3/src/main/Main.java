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
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import pokemon.GameData;
import pokemon.Pokemon;
import pokemon.PokemonBasis;
import pokemon.world.Item;
import sound.Sound;
import utils.Constants;
import utils.IO;

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
        utils.IO.loadLanguageFile(IO.getCurrentLanguage());
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
        final PrintStream stdout = System.out;
        System.setOut(new PrintStream(new OutputStream()
        {
            @Override
            public void write(int b) throws IOException
            {
                stdout.write(b);
                MainFrame.FRAME.writeToConsole("" + (char) b);
            }
        }));
        utils.IO.println(IO.translate("HELLO"), utils.IO.MessageType.IMPORTANT);
        File savefile = new File(Constants.DATA_DIRECTORY + Constants.SAVE_FILENAME);
        if (savefile.exists() && GameData.load(savefile) != null)
        {
            IO.println("Spielstand geladen", IO.MessageType.IMPORTANT);
        }
        else
        {
            GameData.newGame("Peter");
            GameData.getCurrentGame().getPlayer().givePokemon(new Pokemon(PokemonBasis.GLUTEXO, 35));
            GameData.getCurrentGame().getPlayer().givePokemon(new Pokemon(PokemonBasis.BISASAM, 5));
            // GameData.getCurrentGame().getPlayer().givePokemon(new Pokemon(PokemonBasis.GLUMANDA, 100));
            GameData.getCurrentGame().getPlayer().addItem(Item.POTION);
            GameData.getCurrentGame().getPlayer().addItem(Item.SUPERPOTION, 2);
            GameData.getCurrentGame().getPlayer().addItem(Item.SONDERBONBON, 50);
            GameData.getCurrentGame().getPlayer().addItem(Item.POKEBALL, 5);
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
        IO.createAllLanguageFiles();
        utils.Settings.saveSettings();
        java.io.File dir = new java.io.File(utils.Constants.IMAGE_DIRECTORY);
        if (!dir.exists())
        {
            dir.mkdir();
        }
        dir = sound.Sound.SOUNDFILE;
        if (!dir.exists())
        {
            dir.mkdir();
        }
        dir = new java.io.File(utils.Constants.DATA_DIRECTORY);
        if (!dir.exists())
        {
            dir.mkdir();
        }
    }
}
