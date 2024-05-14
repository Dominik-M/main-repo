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

import image.ImageIO;
import image.ImageIO.ImageFile;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Timer;
import pokemon.Fight;
import pokemon.GameData;
import pokemon.GameDataListener;
import pokemon.Pokemon;
import pokemon.PokemonBasis;
import pokemon.Spieler;
import pokemon.world.Item;
import pokemon.world.Objekt;
import pokemon.world.Ort;
import sound.Sound;
import utils.Constants;
import utils.DecisionCallback;
import utils.IO;
import utils.Settings;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 22.07.2016
 */
public class GameGrid extends MainPanel implements GameDataListener
{

    public static final int WIDTH = Constants.GRAPHIC_WIDTH, HEIGHT = Constants.GRAPHIC_HEIGHT;

    public static final int SPOT = 16;

    public final DecisionCallback ITEM_USE = new DecisionCallback()
    {
        @Override
        public void decisionEntered(int index)
        {
            if (index != Constants.INVALID_INDEX)
            {
                Spieler player = gameData.getPlayer();
                Item item = player.getItem(index);
                if (item != null)
                {
                    IO.println("Player uses " + item, IO.MessageType.DEBUG);
                    switch (item.TYP)
                    {
                        case SONDERBONBON:
                        {
                            int pokIndex = PokemonMenuPanel.getPokemonSelection(gamegrid, player, true, true);
                            if (pokIndex != Constants.INVALID_INDEX)
                            {
                                Pokemon playersPok = player.getPoks()[pokIndex];
                                IO.println(player + " " + IO.translate("USES") + " " + item, IO.MessageType.IMPORTANT);
                                playersPok.lvlUp();
                                if (playersPok.getBasis().EVO_LVL > 0 && playersPok.getLvl() >= playersPok.getBasis().EVO_LVL)
                                {
                                    // TODO postpone this action after printing
                                    utils.Utilities.evolve(GameGrid.getInstance(), playersPok, PokemonBasis.values()[playersPok.getBasis().ordinal() + 1]);
                                }
                            }
                            else
                            {
                                IO.println("Auswahl abgebrochen", IO.MessageType.DEBUG);
                            }
                            break;
                        }
                        case POTION:
                        {
                            int pokIndex = PokemonMenuPanel.getPokemonSelection(gamegrid, player, true, true);
                            if (pokIndex != Constants.INVALID_INDEX)
                            {
                                Pokemon playersPok = player.getPoks()[pokIndex];
                                if (playersPok.getKp() < playersPok.getMaxKp() && playersPok.getKp() > 0)
                                {
                                    int n = playersPok.heal(item.getValue());
                                    IO.println(player + " " + IO.translate("USES") + " " + item, IO.MessageType.IMPORTANT);
                                    IO.println(playersPok + " erhält " + n + " KP", IO.MessageType.IMPORTANT);
                                    player.removeItem(index);
                                }
                                else
                                {
                                    IO.println("Das hätte keinen Effekt.", IO.MessageType.IMPORTANT);
                                }
                            }
                            else
                            {
                                IO.println("Auswahl abgebrochen", IO.MessageType.DEBUG);
                            }
                            break;
                        }
                        default:
                            IO.println("Das kannst du jetzt nicht benutzen.", IO.MessageType.IMPORTANT);
                    }
                }
            }
        }
    };

    private static GameGrid gamegrid;

    /*
     * POKEDEX
     * POKEMON
     * ITEMS
     * PLAYER
     * SAVE
     * OPTIONS
     * BACK
     */
    private final InputOption POKEDEX = new InputOption("POKEDEX", new Rectangle(Constants.MENU_BOUNDS.x + Constants.BORDER_SIZE, 0, Constants.GRAPHIC_WIDTH / 3, Constants.GRAPHIC_HEIGHT / 7)),
            POKEMON = new InputOption("POKEMON", new Rectangle(Constants.MENU_BOUNDS.x + Constants.BORDER_SIZE, Constants.GRAPHIC_HEIGHT / 7, Constants.GRAPHIC_WIDTH / 3, Constants.GRAPHIC_HEIGHT / 7)),
            ITEMS = new InputOption("ITEMS", new Rectangle(Constants.MENU_BOUNDS.x + Constants.BORDER_SIZE, 2 * Constants.GRAPHIC_HEIGHT / 7, Constants.GRAPHIC_WIDTH / 3, Constants.GRAPHIC_HEIGHT / 7)),
            PLAYER = new InputOption("PLAYER", new Rectangle(Constants.MENU_BOUNDS.x + Constants.BORDER_SIZE, 3 * Constants.GRAPHIC_HEIGHT / 7, Constants.GRAPHIC_WIDTH / 3, Constants.GRAPHIC_HEIGHT / 7)),
            SAVE = new InputOption("SAVE", new Rectangle(Constants.MENU_BOUNDS.x + Constants.BORDER_SIZE, 4 * Constants.GRAPHIC_HEIGHT / 7, Constants.GRAPHIC_WIDTH / 3, Constants.GRAPHIC_HEIGHT / 7)),
            OPTIONS = new InputOption("OPTIONS", new Rectangle(Constants.MENU_BOUNDS.x + Constants.BORDER_SIZE, 5 * Constants.GRAPHIC_HEIGHT / 7, Constants.GRAPHIC_WIDTH / 3, Constants.GRAPHIC_HEIGHT / 7)),
            BACK = new InputOption("BACK", new Rectangle(Constants.MENU_BOUNDS.x + Constants.BORDER_SIZE, 6 * Constants.GRAPHIC_HEIGHT / 7, Constants.GRAPHIC_WIDTH / 3, Constants.GRAPHIC_HEIGHT / 7));

    private InputOption selectedOption;
    private final InputOption[] mainmenu = new InputOption[]
    {
        POKEDEX, POKEMON, ITEMS, PLAYER, SAVE, OPTIONS, BACK
    };
    private GameData gameData;
    private int stepX, stepY, creditsProgress, nextDirection;
    private boolean moving, sprintPressed, menuOpen;
    private Timer repaintClock;

    private GameGrid()
    {
        try
        {
            init();
        }
        catch (Exception ex)
        {
            IO.printException(ex);
        }
    }

    private void init()
    {
        stepX = 0;
        stepY = 0;
        moving = false;
        menuOpen = false;
        gameData = GameData.getCurrentGame();
        creditsProgress = -1;
        repaintClock = new Timer(1000 / Settings.getSettings().fps, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                walk();
                repaint();
            }
        });

        POKEDEX.setLower(POKEDEX);
        POKEDEX.setLower(POKEMON);
        POKEMON.setUpper(POKEDEX);
        POKEMON.setLower(ITEMS);
        ITEMS.setUpper(POKEMON);
        ITEMS.setLower(PLAYER);
        PLAYER.setUpper(ITEMS);
        PLAYER.setLower(SAVE);
        SAVE.setUpper(PLAYER);
        SAVE.setLower(OPTIONS);
        OPTIONS.setUpper(SAVE);
        OPTIONS.setLower(BACK);
        BACK.setUpper(OPTIONS);
        BACK.setLower(BACK);
        selectedOption = POKEDEX;
        selectedOption.setSelected(true);
    }

    /**
     *
     * @return
     */
    public static GameGrid getInstance()
    {
        if (gamegrid == null)
        {
            synchronized (GameGrid.class)
            {
                if (gamegrid == null)
                {
                    gamegrid = new GameGrid();
                }
            }
        }
        return gamegrid;
    }

    public void setMoving(boolean moving)
    {
        this.moving = moving;
    }

    public boolean isMoving()
    {
        return moving || Math.abs(stepX + stepY) >= 3;
    }

    public Objekt getObjektInFront()
    {
        switch (gameData.getPlayer().getDirection())
        {
            case Constants.DIRECTION_DOWN:
                return gameData.getPos().get(gameData.getPlayer().getX(), gameData.getPlayer().getY() + 1);
            case Constants.DIRECTION_UP:
                return gameData.getPos().get(gameData.getPlayer().getX(), gameData.getPlayer().getY() - 1);
            case Constants.DIRECTION_LEFT:
                return gameData.getPos().get(gameData.getPlayer().getX() - 1, gameData.getPlayer().getY());
            case Constants.DIRECTION_RIGHT:
                return gameData.getPos().get(gameData.getPlayer().getX() + 1, gameData.getPlayer().getY());
            default:
                return gameData.getPos().getDefaultObjekt();
        }
    }

    private void walk()
    {
        // move
        if (isMoving() && getObjektInFront().isPassable())
        {
            int walkspeed = sprintPressed ? 2 : 1;
            switch (gameData.getPlayer().getDirection())
            {
                case Constants.DIRECTION_DOWN:
                    stepY += walkspeed;
                    break;
                case Constants.DIRECTION_UP:
                    stepY -= walkspeed;
                    break;
                case Constants.DIRECTION_LEFT:
                    stepX -= walkspeed;
                    break;
                case Constants.DIRECTION_RIGHT:
                    stepX += walkspeed;
                    break;
            }
            if (stepX >= SPOT)
            {
                gameData.getPlayer().setPos(gameData.getPlayer().getX() + 1, gameData.getPlayer().getY());
                stepX = 0;
            }
            else if (stepX <= -SPOT)
            {
                gameData.getPlayer().setPos(gameData.getPlayer().getX() - 1, gameData.getPlayer().getY());
                stepX = 0;
            }
            if (stepY >= SPOT)
            {
                gameData.getPlayer().setPos(gameData.getPlayer().getX(), gameData.getPlayer().getY() + 1);
                stepY = 0;
            }
            else if (stepY <= -SPOT)
            {
                gameData.getPlayer().setPos(gameData.getPlayer().getX(), gameData.getPlayer().getY() - 1);
                stepY = 0;
            }
            if (stepX + stepY == 0)
            {
                IO.println("Moved to " + gameData.getPlayer().getX() + " | " + gameData.getPlayer().getY(), IO.MessageType.DEBUG);
                gameData.getPos().get(gameData.getPlayer().getX(), gameData.getPlayer().getY()).betreten();
                if (gameData.getPlayer().getDirection() != nextDirection)
                {
                    moving = true;
                }
                gameData.getPlayer().setDirection(nextDirection);
            }
        }
        else
        {
            stepX = 0;
            stepY = 0;
        }
    }

    /**
     *
     */
    @Override
    public void onSelect()
    {
        requestFocus();
        setPaused(false);
        GameData.addGameListener(this);
    }

    /**
     *
     */
    @Override
    public void onDisselect()
    {
        setPaused(true);
        GameData.removeGameListener(this);
        setMoving(false);
        sprintPressed = false;
    }

    /**
     *
     */
    @Override
    public void drawGUI(Graphics2D g)
    {
        g.setColor(Settings.getSettings().backgroundColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.translate(-stepX, -stepY);
        drawWorld(g);
        g.translate(stepX, stepY);
        drawPlayer(g);
        drawInterface(g);
        if (creditsProgress >= 0)
        {
            drawCredits(g);
        }
    }

    private void drawPlayer(Graphics2D g)
    {
        ImageIO.ImageFile img;
        int richtung = gameData.getPlayer().getDirection();
        if (isMoving())
        {
            if (Math.abs(stepX + stepY) > SPOT / 2)
            {
                if (richtung == Constants.DIRECTION_UP)
                {
                    img = ImageIO.ImageFile.TRAINER_UP;
                }
                else if (richtung == Constants.DIRECTION_LEFT)
                {
                    img = ImageIO.ImageFile.TRAINER_LEFT;
                }
                else if (richtung == Constants.DIRECTION_RIGHT)
                {
                    img = ImageIO.ImageFile.TRAINER_RIGHT;
                }
                else
                {
                    img = ImageIO.ImageFile.TRAINER_DOWN;
                }
            }
            else if (richtung == Constants.DIRECTION_UP)
            {
                img = ImageIO.ImageFile.TRAINER_UP;
            }
            else if (richtung == Constants.DIRECTION_LEFT)
            {
                img = ImageIO.ImageFile.TRAINER_LEFT;
            }
            else if (richtung == Constants.DIRECTION_RIGHT)
            {
                img = ImageIO.ImageFile.TRAINER_RIGHT;
            }
            else
            {
                img = ImageIO.ImageFile.TRAINER_DOWN;
            }
        }
        else if (richtung == Constants.DIRECTION_UP)
        {
            img = ImageIO.ImageFile.TRAINER_UP;
        }
        else if (richtung == Constants.DIRECTION_LEFT)
        {
            img = ImageIO.ImageFile.TRAINER_LEFT;
        }
        else if (richtung == Constants.DIRECTION_RIGHT)
        {
            img = ImageIO.ImageFile.TRAINER_RIGHT;
        }
        else
        {
            img = ImageIO.ImageFile.TRAINER_DOWN;
        }
        ImageIO.getSprite(img.getFile().getName()).draw(g, (WIDTH) / 2, (HEIGHT) / 2, 0);
    }

    private void drawWorld(Graphics2D g)
    {
        // draw world
        int viewX = gameData.getPlayer().getX() - WIDTH / SPOT / 2;
        int viewY = gameData.getPlayer().getY() - HEIGHT / SPOT / 2;
        ImageFile img;
        for (int x = -1; x <= WIDTH / SPOT; x++)
        {
            for (int y = -1; y <= HEIGHT / SPOT; y++)
            {
                img = gameData.getPos().getBackground().getImageFile();
                if (img != null)
                {
                    ImageIO.getSprite(img.getFile().getName()).draw(g, x * SPOT, y * SPOT, 0);
                }
                else
                {
                    g.setColor(Color.BLACK);
                    g.fillRect(x * SPOT, y * SPOT, SPOT, SPOT);
                }
                img = gameData.getPos().get(x + viewX, y + viewY).getImageFile();
                if (img != null)
                {
                    ImageIO.getSprite(img.getFile().getName()).draw(g, x * SPOT, y * SPOT, 0);
                }
                else
                {
                    g.setColor(Color.BLACK);
                    g.fillRect(x * SPOT, y * SPOT, SPOT, SPOT);
                }
            }
        }
    }

    private void drawInterface(Graphics2D g)
    {
        if (menuOpen)
        {
            drawFrame(g, Constants.MENU_BOUNDS);
            for (InputOption o : mainmenu)
            {
                o.draw(g);
            }
        }
    }

    private void drawCredits(Graphics g)
    {
        int x = WIDTH / 5;
        int y = HEIGHT - creditsProgress / 4;
        g.setFont(Settings.getSettings().font);
        g.setColor(Settings.getSettings().fontColor);
        g.drawString("CREDITS", x, y);
        y += g.getFont().getSize() * 3;
        g.drawString("Author", x, y);
        y += g.getFont().getSize() * 3;
        g.drawString(Constants.AUTHOR, x, y);
        y += g.getFont().getSize() * 3;
        g.drawString("Co producer", x, y);
        y += g.getFont().getSize() * 3;
        for (int i = 0; i < Constants.COAUTHORS.length; i++)
        {
            g.drawString(Constants.COAUTHORS[i], x, y);
            y += g.getFont().getSize() * 3;
        }
        creditsProgress++;
    }

    private void setPaused(boolean pause)
    {
        if (!isPaused() && pause)
        {
            repaintClock.stop();
            //sound.Sound.pause();
        }
        else if (isPaused() && !pause)
        {
            repaintClock.start();
            //sound.Sound.play();
        }
    }

    /**
     *
     */
    public void showCredits()
    {
        creditsProgress = 0;
        setPaused(false);
    }

    private boolean isPaused()
    {
        return !repaintClock.isRunning();
    }

    @Override
    public void startFight(Fight f)
    {
        MainFrame.FRAME.setMainPanel(new graphic.FightPanel(f));
        Sound.playSoundClip(new java.io.File(utils.Constants.SOUND_FILENAME_BATTLE), Constants.SOUND_LOOP_CONTINUOUSLY);
    }

    @Override
    public void switchPos(Ort pos)
    {
        File currentSoundfile = Sound.getCurrentSoundfile();
        File nextSoundfile = new File(pos.getSoundfilename());
        if (!((currentSoundfile != null) && (currentSoundfile.equals(nextSoundfile))))
        {
            Sound.playSoundClip(new java.io.File(pos.getSoundfilename()), Constants.SOUND_LOOP_CONTINUOUSLY);
        }
    }

    public void setSelectedMenuOption(InputOption o)
    {
        if (o != null)
        {
            if (selectedOption != null)
            {
                selectedOption.setSelected(false);
            }
            selectedOption = o;
            selectedOption.setSelected(true);
        }
    }

    private void enterSelectedMenuOption()
    {
        if (selectedOption.ID == POKEDEX.ID)
        {
            MainFrame.FRAME.setMainPanel(new PokedexPanel(this));
        }
        else if (selectedOption.ID == POKEMON.ID)
        {
            MainFrame.FRAME.setMainPanel(new PokemonMenuPanel(this));
        }
        else if (selectedOption.ID == ITEMS.ID)
        {
            MainFrame.FRAME.promptItemDecision(ITEM_USE);
        }
        else if (selectedOption.ID == PLAYER.ID)
        {
            IO.println("Spieler nicht verfügbar", IO.MessageType.IMPORTANT);
        }
        else if (selectedOption.ID == OPTIONS.ID)
        {
            IO.println("Optionen nicht verfügbar", IO.MessageType.IMPORTANT);
        }
        else if (selectedOption.ID == SAVE.ID)
        {
            File savefile = new File(Constants.DATA_DIRECTORY + Constants.SAVE_FILENAME);
            if (savefile.exists())
            {
                IO.println("Es ist schon ein Spielstand vorhanden", IO.MessageType.IMPORTANT);
            }
            if (gameData.save(savefile))
            {
                IO.println(IO.translate("GAME_SAVED"), IO.MessageType.IMPORTANT);
            }
        }
        else if (selectedOption.ID == BACK.ID)
        {
            menuOpen = false;
        }
    }

    @Override
    public boolean keyPressed(InputConfig.Key key)
    {
        switch (key)
        {
            case A:
                if (menuOpen)
                {
                    enterSelectedMenuOption();
                }
                else
                {
                    getObjektInFront().benutzt();
                }
                return false;
            case B:
                if (menuOpen)
                {
                    menuOpen = false;
                    return true;
                }
                sprintPressed = true;
                return false;
            case LEFT:
                if (!menuOpen)
                {
                    nextDirection = Constants.DIRECTION_LEFT;
                    if (!isMoving())
                    {
                        gameData.getPlayer().setDirection(nextDirection);
                        setMoving(true);
                    }
                }
                return false;
            case RIGHT:
                if (!menuOpen)
                {
                    nextDirection = Constants.DIRECTION_RIGHT;
                    if (!isMoving())
                    {
                        gameData.getPlayer().setDirection(nextDirection);
                        setMoving(true);
                    }
                }
                return false;
            case UP:
                if (menuOpen)
                {
                    setSelectedMenuOption(selectedOption.getUpper());
                    return true;
                }
                nextDirection = Constants.DIRECTION_UP;
                if (!isMoving())
                {
                    gameData.getPlayer().setDirection(nextDirection);
                    setMoving(true);
                }
                return false;
            case DOWN:
                if (menuOpen)
                {
                    setSelectedMenuOption(selectedOption.getLower());
                    return true;
                }
                nextDirection = Constants.DIRECTION_DOWN;
                if (!isMoving())
                {
                    gameData.getPlayer().setDirection(nextDirection);
                    setMoving(true);
                }
                return false;
            case SELECT:
                if (!menuOpen)
                {
                    IO.println(IO.translate("SELECTION_EMPTY"), IO.MessageType.IMPORTANT);
                }
                return false;
            case START:
                //TODO open menu
                menuOpen = !menuOpen;
                // showCredits();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void keyReleased(InputConfig.Key key)
    {
        switch (key)
        {
            case B:
                sprintPressed = false;
                break;
            case LEFT:
                if (gameData.getPlayer().getDirection() == Constants.DIRECTION_LEFT)
                {
                    setMoving(false);
                }
                break;
            case RIGHT:
                if (gameData.getPlayer().getDirection() == Constants.DIRECTION_RIGHT)
                {
                    setMoving(false);
                }
                break;
            case UP:
                if (gameData.getPlayer().getDirection() == Constants.DIRECTION_UP)
                {
                    setMoving(false);
                }
                break;
            case DOWN:
                if (gameData.getPlayer().getDirection() == Constants.DIRECTION_DOWN)
                {
                    setMoving(false);
                }
                break;
            default:
                break;
        }
    }
}
