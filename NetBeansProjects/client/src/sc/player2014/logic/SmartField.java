package sc.player2014.logic;

import java.util.ArrayList;
import sc.plugin2014.entities.*;

/**Ein SmartField ist ein Field, das weiß, welche Stones auf
 * ihm liegen können und wie viele Punkte es dafür gibt.
 *
 * @author tobias.viehmann
 */
public class SmartField extends Field implements Cloneable {

    /**Gibt an, ob es für dieses SmartField passende Stones gibt.
     */
    private boolean interessant;
    private int hpunkte = 0;
    private int vpunkte = 0;
    private ArrayList<Integer> moeglichesteine;
    private int first;

    public SmartField(Field field) {
        super(field.getPosX(), field.getPosY());
        interessant = true;
        moeglichesteine = new ArrayList<>();
    }

    /**
     * 
     *
     * @param stein
     * @param horizontal
     */
    public void addnachbar(Stone stein, boolean horizontal) {
        if (!interessant) {
            return;
        }
        int shape = stein.getShape().ordinal();
        int color = stein.getColor().ordinal();
        if (hpunkte == 0 && vpunkte == 0) {
            first = 5;
            for (int i = 0; i < 6; i++) {
                if (color != i) {
                    moeglichesteine.add(shape * 10 + i);
                }

            }
            for (int i = 0; i < 6; i++) {
                if (shape != i) {
                    moeglichesteine.add(i * 10 + color);
                }
            }
            if (horizontal) {
                hpunkte = 2;
            } else {
                vpunkte = 2;
            }
        } else {
            int x = moeglichesteine.indexOf(shape * 10 + color);
            if (x != -1) {
                if (x < first) {
                    for (int i = moeglichesteine.size() - 1; i >= first; i--) {
                        moeglichesteine.remove(i);
                    }
                    first--;
                } else {
                    for (int i = 0; i < first; i++) {
                        moeglichesteine.remove(i);
                    }
                    first = 0;
                }
                moeglichesteine.remove(new Integer(shape * 10 + color));
                if (horizontal) {
                    if (hpunkte == 0) {
                        hpunkte = 2;
                    } else {
                        if (hpunkte == 5) {
                            hpunkte = 12;
                        } else {
                            hpunkte++;
                        }
                    }
                } else {
                    if (vpunkte == 0) {
                        vpunkte = 2;
                    } else {
                        if (vpunkte == 5) {
                            vpunkte = 12;
                        } else {
                            vpunkte++;
                        }
                    }
                }
            } else {
                interessant = false;
            }
        }
    }

    public boolean nochinteressant() {
        return interessant;
    }

    /**Gibt zurück, ob ein gegebener Stone auf dieses SmartField
     * gelegt werden kann.
     *
     * @param stein der zu testende Stone
     * @return true genau dann, wenn der Stone legal gelegt werden kann.
     */
    public boolean trytosetStein(Stone stein) {
        if (!interessant) {
            return false;
        }
        int shape = stein.getShape().ordinal();
        int color = stein.getColor().ordinal();
        if (moeglichesteine.contains(
                shape * 10 + color)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean gleicheFieldPos(Field field) {
        if (field.getPosX() == getPosX() && field.getPosY() == getPosY()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean gleicheFieldPos(int x, int y) {
        if (x == getPosX() && y == getPosY()) {
            return true;
        } else {
            return false;
        }
    }

    public int getPunkte() {
        return vpunkte + hpunkte;
    }

    @Override
    public SmartField clone() {
        try {
            SmartField clone = new SmartField((Field) super.clone());
            clone.hpunkte = hpunkte;
            clone.interessant = interessant;
            clone.moeglichesteine = (ArrayList) moeglichesteine.clone();
            clone.vpunkte = vpunkte;
            return clone;
        } catch (CloneNotSupportedException ex) {
            System.out.println("Komisch");
        }
        return null;
    }
}
