/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package akinator;

/**
 *
 * @author Dominik
 */
public class EditableKnoten {

    public EditableKnoten vorher;
    public EditableKnoten ja, nein;
    public String text;

    public EditableKnoten(EditableKnoten vor) {
        vorher = vor;
        text = null;
        ja = null;
        nein = null;
    }

    public void setFrageknoten(Frageknoten kopf) {
        if (kopf != null) {
            text = kopf.toString();
            if (kopf.getJaKnoten() != null) {
                ja = new EditableKnoten(this);
                ja.setFrageknoten(kopf.getJaKnoten());
            }
            if(kopf.getNeinKnoten() != null)
            {
                nein = new EditableKnoten(this);
                nein.setFrageknoten(kopf.getNeinKnoten());
            }
        }
    }
    
    public Frageknoten toFrageknoten()
    {
        Frageknoten k = new Frageknoten(text);
        if(ja != null)
            k.setJaKnoten(ja.toFrageknoten());
        if(nein != null)
            k.setNeinKnoten(nein.toFrageknoten());
        return k;
    }
}