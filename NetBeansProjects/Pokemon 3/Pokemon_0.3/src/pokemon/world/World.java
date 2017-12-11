/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemon.world;

import image.ImageIO.ImageFile;
import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import pokemon.GameData;
import pokemon.PokemonBasis;
import static pokemon.world.Objekt.BLUMEN;
import static pokemon.world.Objekt.GRAS;
import static pokemon.world.Objekt.STEIN2;
import static pokemon.world.Objekt.WEG;
import static pokemon.world.Objekt.WIESE;
import static pokemon.world.Objekt.ZAUN;
import static pokemon.world.Ort.createDefaultHouse;
import static pokemon.world.Ort.placeHaus;
import static pokemon.world.Ort.placeHuette;
import utils.Constants;
import utils.Dictionary;
import utils.DynText;
import utils.IO;
import utils.SerializableReflectObject;

/**
 * Container class for all places in the world except special places like
 * houses.
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 * Created 17.05.2017
 */
public class World extends SerializableReflectObject
{

    public final Ort AlABASTIA, ROUTE1, VERTANIA_CITY;

    public World()
    {
        AlABASTIA = new Ort("ALABASTIA", 20, 18, 5, 6, 2, 10);
        ROUTE1 = new Ort("ROUTE1", 20, 20, 4, 7, 2, 7);
        VERTANIA_CITY = new Ort("VERTANIA_CITY", 20, 20, 4, 7, 3, 10);
        init();
    }

    public final void init()
    {
        initAlabastia();
        initRoute1();
        initVertania();
        // gateways alabastia <-> route1
        AlABASTIA.set(8, 0, new GateObjekt(ROUTE1, 8, 18, ImageFile.GRAS));
        AlABASTIA.set(9, 0, new GateObjekt(ROUTE1, 9, 18, ImageFile.GRAS));
        ROUTE1.set(8, 19, new GateObjekt(AlABASTIA, 8, 1, ImageFile.GRAS));
        ROUTE1.set(9, 19, new GateObjekt(AlABASTIA, 9, 1, ImageFile.GRAS));
        addGateway(8, 0, AlABASTIA, 8, 19, ROUTE1, ImageFile.GRAS);
        addGateway(9, 0, AlABASTIA, 9, 19, ROUTE1, ImageFile.GRAS);
        // gateways route1 <-> vertania city
        //TODO
    }

    /**
     * Initializes or resets the place AlABASTIA in this world instance.
     */
    public void initAlabastia()
    {
        AlABASTIA.addPok(PokemonBasis.BISASAM);
        AlABASTIA.addPok(PokemonBasis.GLUMANDA);
        AlABASTIA.addPok(PokemonBasis.SCHIGGY);
        Objekt[] o = new Objekt[]
        {
            WIESE, WIESE, WIESE, STEIN2, WIESE, WIESE, WIESE, STEIN2, GRAS, GRAS, STEIN2, WIESE, WIESE, WIESE, WIESE, WIESE, WIESE, WIESE, STEIN2, WIESE,
            STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, GRAS, GRAS, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2, STEIN2
        };
        AlABASTIA.setObjekte(o);
        for (int y = 2; y < 18; y++)
        {
            AlABASTIA.set(0, y, STEIN2);
            AlABASTIA.set(19, y, STEIN2);
        }
        for (int x = 1; x < 19; x++)
        {
            AlABASTIA.set(x, 17, STEIN2);
        }
        for (int y = 2; y < 18; y++)
        {
            AlABASTIA.set(1, y, WIESE);
        }
        for (int y = 14; y < 18; y++)
        {
            AlABASTIA.set(2, y, WIESE);
            AlABASTIA.set(3, y, WIESE);
        }
        for (int x = 2; x < 18; x++)
        {
            AlABASTIA.set(x, 6, WEG);
            AlABASTIA.set(x, 7, WEG);
        }
        for (int y = 8; y < 14; y++)
        {
            AlABASTIA.set(2, y, WEG);
            AlABASTIA.set(3, y, WEG);
        }
        AlABASTIA.set(4, 9, ZAUN);
        AlABASTIA.set(5, 9, ZAUN);
        AlABASTIA.set(6, 9, ZAUN);
        AlABASTIA.set(7, 9, new Sign(IO.translate("ALABASTIA")));
        for (int x = 4; x < 8; x++)
        {
            AlABASTIA.set(x, 10, BLUMEN);
            AlABASTIA.set(x, 11, BLUMEN);
            AlABASTIA.set(x, 12, WEG);
            AlABASTIA.set(x, 13, WEG);
        }
        for (int y = 2; y < 14; y++)
        {
            AlABASTIA.set(8, y, WEG);
            AlABASTIA.set(9, y, WEG);
        }
        AlABASTIA.set(1, 17, STEIN2);
        AlABASTIA.set(4, 17, new Water(ImageFile.UFER_LINKS_MITTE));
        AlABASTIA.set(4, 16, new Water(ImageFile.UFER_LINKS_MITTE));
        AlABASTIA.set(4, 15, new Water(ImageFile.UFER_LINKS_MITTE));
        AlABASTIA.set(4, 14, new Water(ImageFile.UFER_OBEN_LINKS));
        AlABASTIA.set(5, 14, new Water(ImageFile.UFER_OBEN_MITTE));
        AlABASTIA.set(6, 14, new Water(ImageFile.UFER_OBEN_MITTE));
        AlABASTIA.set(7, 14, new Water(ImageFile.UFER_OBEN_RECHTS));
        AlABASTIA.set(7, 15, new Water(ImageFile.UFER_RECHTS_MITTE));
        AlABASTIA.set(7, 16, new Water(ImageFile.UFER_RECHTS_MITTE));
        AlABASTIA.set(7, 17, new Water(ImageFile.UFER_RECHTS_MITTE));
        AlABASTIA.set(6, 17, new Water(ImageFile.WASSER));
        AlABASTIA.set(6, 16, new Water(ImageFile.WASSER));
        AlABASTIA.set(6, 15, new Water(ImageFile.WASSER));
        AlABASTIA.set(5, 17, new Water(ImageFile.WASSER));
        AlABASTIA.set(5, 16, new Water(ImageFile.WASSER));
        AlABASTIA.set(5, 15, new Water(ImageFile.WASSER));
        Person p = new Person("Peter", Constants.DIRECTION_DOWN, null, Constants.HAUTFARBEN[0], Color.BLUE,
                IO.translate("DIALOG_PETER_1"), IO.translate("DIALOG_PETER_2"),
                IO.translate("DIALOG_PETER_3"), IO.translate("DIALOG_PETER_4")
        );
        AlABASTIA.set(5, 8, p);

        DynText signText = new DynText()
        {
            @Override
            public String toString()
            {
                return GameData.getCurrentGame().getPlayer().getName() + IO.translate("SIGN_PLAYERSHOUSE");
            }
        };
        Ort house = placeHuette(AlABASTIA, IO.translate("SIGN_PLAYERSHOUSE"), 4, 3, createDefaultHouse(IO.translate("SIGN_PLAYERSHOUSE")
        ));
        AlABASTIA.set(3, 5, new Sign(signText));
        //TODO init inside players house
        house.set(3, 2, new Person("Mama", Constants.DIRECTION_LEFT, "Du und deine Pokemon sehen erschöpft aus. Mach mal eine Pause.")
        {
            @Override
            public void benutzt()
            {
                super.benutzt();
                GameData.getCurrentGame().healPlayer();
            }
        });
        house.setStartPos(3, 3);
        signText = new DynText()
        {
            @Override
            public String toString()
            {
                return GameData.getCurrentGame().getRivale().getName() + IO.translate("SIGN_RIVALSHOUSE");
            }
        };
        house = placeHuette(AlABASTIA, IO.translate("SIGN_RIVALSHOUSE"), 12, 3, createDefaultHouse(IO.translate("SIGN_RIVALSHOUSE")
        ));
        AlABASTIA.set(11, 5, new Sign(signText));
        //TODO init inside rivals house

        house = placeHaus(AlABASTIA, 10, 8, 6, 1, IO.translate("EICHS_LABOR"), createDefaultHouse(IO.translate("EICHS_LABOR")
        ));
        //TODO init inside eichs lab

    }

    /**
     * Initializes or resets the place ROUTE1 in this world instance.
     */
    public void initRoute1()
    {
        // TODO init route 1
    }

    /**
     * Initializes or resets the place VERTANIA_CITY in this world instance.
     */
    public void initVertania()
    {
        //TODO init vertania city
    }

    /**
     *
     * @param x1 X coordinate at first place
     * @param y1 Y coordinate at first place
     * @param pos1 first place
     * @param x2 X coordinate at second place
     * @param y2 Y coordinate at second place
     * @param pos2 second place
     * @param img Image of both gateway objects
     */
    public static void addGateway(int x1, int y1, Ort pos1, int x2, int y2, Ort pos2, ImageFile img)
    {
        pos1.set(x1, y1, new GateObjekt(pos2, x2, y2, img));
        pos2.set(x2, y2, new GateObjekt(pos1, x1, y1, img));
    }

    @Override
    public Dictionary<String, Object> getAttributes()
    {
        Dictionary<String, Object> values = new Dictionary<>();
        for (Field f : Ort.class.getDeclaredFields())
        {
            try
            {
                if (!Modifier.isStatic(f.getModifiers()))
                {
                    values.add(f.getName(), f.get(this));
                }
            } catch (IllegalArgumentException | IllegalAccessException ex)
            {
                IO.printException(ex);
            }
        }
        return values;
    }
}
