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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pokemon.world.Ort;
import pokemon.world.World;
import utils.Dictionary;
import utils.IO;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 22.07.2016
 */
public class GameData extends utils.SerializableReflectObject
{

    private static GameData currentGame;
    private static final LinkedList<GameDataListener> LISTENER = new LinkedList<>();

    private Spieler player;
    private Trainer rivale;
    private ArrayList<Pokemon> box;
    private World world;
    private Ort currentPlace, lastSafePlace;

    private GameData()
    {
        init();
    }

    private void init()
    {
        box = new ArrayList();
        player = new Spieler();
        rivale = new Trainer("Gary", new Pokemon(PokemonBasis.BISASAM, 5));
        rivale.setGeld(500);
        world = new World();
        currentPlace = world.AlABASTIA;
        lastSafePlace = currentPlace;
        player.setPos(currentPlace.getStartX(), currentPlace.getStartY());
    }

    /**
     *
     * @return
     */
    public static GameData getCurrentGame()
    {
        return currentGame;
    }

    public Pokemon[] getBoxPoks()
    {
        Pokemon[] poks = new Pokemon[box.size()];
        for (int i = 0; i < poks.length; i++)
        {
            poks[i] = box.get(i);
        }
        return poks;
    }

    public Pokemon removeBoxPok(int index)
    {
        return box.remove(index);
    }

    public boolean addBoxPok(Pokemon pok)
    {
        return box.add(pok);
    }

    public Ort getPos()
    {
        return currentPlace;
    }

    Ort getLastSafePlace()
    {
        return lastSafePlace;
    }

    /**
     *
     * @return
     */
    public Spieler getPlayer()
    {
        return player;
    }

    /**
     *
     * @return
     */
    public Trainer getRivale()
    {
        return rivale;
    }

    public boolean switchOrt(int x, int y, Ort pos)
    {
        if (pos != null)
        {
            this.currentPlace = pos;
            player.setPos(x, y);
            for (GameDataListener l : LISTENER)
            {
                l.switchPos(pos);
            }
            return true;
        }
        return false;
    }

    public void startFight()
    {
        Pokemon opponent = currentPlace.getRandomPok();
        startFight(opponent);
    }

    public void startFight(Pokemon pok)
    {
        if (pok != null)
        {
            Fight f = new Fight(player, pok, true);
            for (GameDataListener l : LISTENER)
            {
                l.startFight(f);
            }
        }
    }

    public void startFight(Trainer opponent)
    {
        Fight f = new Fight(player, opponent, false);
        for (GameDataListener l : LISTENER)
        {
            l.startFight(f);
        }
    }

    public static void addGameListener(GameDataListener l)
    {
        LISTENER.add(l);
    }

    public static boolean removeGameListener(GameDataListener l)
    {
        return LISTENER.remove(l);
    }

    /**
     *
     * @param playerName
     * @return
     */
    public static GameData newGame(String playerName)
    {
        currentGame = new GameData();
        currentGame.player.setName(playerName);
        //currentGame.player.givePokemon(new Pokemon(PokemonBasis.BISASAM, 5));
        IO.println("Started a new game", IO.MessageType.DEBUG);
        return currentGame;
    }

    /**
     *
     * @param file
     * @return
     */
    public boolean save(File file)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            fos.close();
            oos.close();
            IO.println("saved " + file, IO.MessageType.DEBUG);
            return true;
        } catch (Exception ex)
        {
            IO.println("failed to save " + file, IO.MessageType.ERROR);
            IO.printException(ex);
            return false;
        }
    }

    /**
     *
     * @param file
     * @return
     */
    public static GameData load(File file)
    {
        try
        {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            GameData g = (GameData) ois.readObject();
            fis.close();
            ois.close();
            IO.println("loaded " + file, IO.MessageType.DEBUG);
            currentGame = g;
            return g;
        } catch (IOException | ClassNotFoundException ex)
        {
            IO.println("failed to load " + file, IO.MessageType.ERROR);
            IO.printException(ex);
            return null;
        }
    }

    /**
     *
     * @return
     */
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
                Logger.getLogger(GameData.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex)
            {
                Logger.getLogger(GameData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return values;
    }

    public void healPlayer()
    {
        player.heal();
        lastSafePlace = currentPlace;
    }
}
