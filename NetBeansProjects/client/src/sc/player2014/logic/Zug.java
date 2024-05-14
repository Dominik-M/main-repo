package sc.player2014.logic;

import java.util.ArrayList;
import sc.plugin2014.entities.*;
import sc.plugin2014.util.Constants;

public class Zug {

    private Stone stone;
    private Field worauf;
    private ArrayList<Zug> weitere;
    private ArrayList<SmartField> neu;
    private int punkte;
    private boolean ersterzug;
    private boolean horizontal;

    public Zug(Stone stone, Field field, boolean ersterzug, boolean horizontal, int punkte) {
        this.stone = stone;
        this.ersterzug = ersterzug;
        this.horizontal = horizontal;
        this.punkte = punkte;
        neu=new ArrayList<>();
        worauf = field;
        weitere = new ArrayList<>();
        punkte = 0;
    }

    public void setChangedSmartFields(ArrayList<SmartField> smartFields) {
        neu = smartFields;
    }

    public ArrayList<SmartField> getSmartFields() {
        return neu;
    }

    public ArrayList<Field> versucheWeiterenZug(Stone stone) {
        if (weitere.isEmpty()) {
            for (SmartField sField : neu) {
                if (sField.trytosetStein(stone)) {
                }
            }
        } else {
        }

        return null;
    }

    public int getPunkte() {
        return punkte;
    }

    public Field getField() {
        return worauf;
    }

    public Stone getStone() {
        return stone;
    }

    public boolean needField(Field field) {
        return (field.getPosX() == worauf.getPosX() && field.getPosY() == worauf.getPosY());
    }

    public boolean needStone(Stone stone) {
        return (stone.getColor() == this.stone.getColor() && stone.getShape() == this.stone.getShape());
    }

    public void setNachbarFields(Board board, ArrayList<SmartField> list) {
        Field nachbar;
        SmartField sField;
        ArrayList<Field> update = new ArrayList<>();
        int x = worauf.getPosX();
        int y = worauf.getPosY();
        for (int i = x - 1; i >= 0; i--) {
            nachbar = board.getField(i, y);
            if (nachbar.isFree()) {
                update.add(nachbar);
                break;
            }
        }
        for (int i = x + 1; i < Constants.FIELDS_IN_X_DIM; i++) {
            nachbar = board.getField(i, y);
            if (nachbar.isFree()) {
                update.add(nachbar);
                break;
            }
        }
        for (int i = y - 1; i >= 0; i--) {
            nachbar = board.getField(x, i);
            if (nachbar.isFree()) {
                update.add(nachbar);
                break;
            }
        }
        for (int i = y + 1; i < Constants.FIELDS_IN_Y_DIM; i++) {
            nachbar = board.getField(x, i);
            if (nachbar.isFree()) {
                update.add(nachbar);
                break;
            }
        }
        
        //2. Teil
        
        for (Field relevant : update) {
            sField = new SmartField(relevant);
            for (int i = x - 1; i >= 0; i--) {
                nachbar = board.getField(i, y);
                if (nachbar.isFree()) {
                    break;
                } else {
                    sField.addnachbar(nachbar.getStone(), true);
                }
            }
            for (int i = x + 1; i < Constants.FIELDS_IN_X_DIM; i++) {
                nachbar = board.getField(i, y);
                if (nachbar.isFree()) {
                    break;
                } else {
                    sField.addnachbar(nachbar.getStone(), true);
                }
            }
            for (int i = y - 1; i >= 0; i--) {
                nachbar = board.getField(x, i);
                if (nachbar.isFree()) {
                    break;
                } else {
                    sField.addnachbar(nachbar.getStone(), false);
                }
            }
            for (int i = y + 1; i < Constants.FIELDS_IN_Y_DIM; i++) {
                nachbar = board.getField(x, i);
                if (nachbar.isFree()) {
                    break;
                } else {
                    sField.addnachbar(nachbar.getStone(), false);
                }
            }
            neu.add(sField);
        }
    }
}

