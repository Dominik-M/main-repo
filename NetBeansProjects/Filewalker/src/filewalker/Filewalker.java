/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filewalker;

import javax.swing.JFileChooser;

/**
 *
 * @author Dundun
 */
public class Filewalker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFileChooser jfc = new javax.swing.JFileChooser("\\\\Fritz-nas\\fritz.nas\\WD-Elements1023-01");
        int result = jfc.showDialog(null, "Pfad anzeigen");
        if(result == JFileChooser.APPROVE_OPTION)
            System.out.println("Pfad: "+jfc.getSelectedFile());
    }
}