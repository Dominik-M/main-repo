/*
 * Copyright (C) 2023 Dominik Messerschmidt
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dominik Messerschmidt
 */
public abstract class Quest implements Serializable
{

    public enum QuestType
    {
        KILL,
        GET,
        FIND,
        ESCORT
    }

    public final String NAME, DESCRIPTION;
    public final File CONFIG_FILE;

    public Quest(String name, String description, String configFilePath)
    {
        NAME = name;
        DESCRIPTION = description;
        CONFIG_FILE = new File(configFilePath);
    }

    public static final Quest QUEST1 = new Quest("Test Quest", "This is the test scenario\r\nObjective: Find and kill the death knight.", "quests/quest1.txt")
    {
        private boolean bossFound = false, bossKilled = false;
        private Actor boss = null;

        @Override
        public boolean isDone()
        {
            if (bossFound)
            {
                bossKilled = boss.getHp() <= 0;
            }
            else
            {
                for (Actor a : Game.getInstance().getActors())
                {
                    if (a.getType() == Actor.ActorType.KNIGHT)
                    {
                        boss = a;
                        bossFound = true;
                    }
                }
            }
            return bossFound && bossKilled;
        }
    };

    public abstract boolean isDone();

    public String getConfig()
    {
        return readFile(CONFIG_FILE);
    }

    @Override
    public String toString()
    {
        return NAME;
    }

    public static String readFile(File f)
    {
        String config = "";
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line = reader.readLine();
            while (line != null)
            {
                config += line + "\n";
                line = reader.readLine();
            }
        }
        catch (Exception ex)
        {
            Logger.getLogger(Quest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return config;
    }

    public static Quest loadQuestFromFile(File file)
    {
        String config = readFile(file);
        String name = "Unnamed Quest";
        String description = "Empty description";
        QuestType type = QuestType.KILL;

        switch (type)
        {
            case KILL:
            default:
                return new Quest(name, description, config)
                {
                    @Override
                    public boolean isDone()
                    {
                        boolean done = true;
                        for (Actor a : Game.getInstance().getActors())
                        {
                            if (a.isEnemy())
                            {
                                done = false;
                                break;
                            }
                        }
                        return done;
                    }
                };
        }
    }

}
