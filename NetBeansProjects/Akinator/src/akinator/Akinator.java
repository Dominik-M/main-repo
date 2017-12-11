/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package akinator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dominik
 */
public class Akinator {

    public static final int JA = 1, NEIN = 2, VIELLEICHT = 3, KA = 0;
    public static final String[] ANTWORTEN={"KA","JA","NEIN","VIELLEICHT"};
    private static Frageknoten erster;
    private static Frageknoten aktFrage;
    private static final java.util.LinkedList<Frageknoten> verbleibend = new java.util.LinkedList<>();
    private static int antwort = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        io.IO.initIO();
        do {
            start();
            print("Nochmal Spielen?");
            antwort = io.IO.getAntwort();
            io.IO.clear();
        } while (antwort == JA);
        System.exit(0);
    }

    private static void start() {
        erster = lade();
        aktFrage = erster;
        verbleibend.clear();
        raten();
    }

    private static void raten() {
        while (!aktFrage.isEndknoten()) {
            print(aktFrage.toString());
            antwort = io.IO.getAntwort();
            if (antwort == JA) {
//                if(aktFrage.getJaKnoten() == null)
//                {
//                    print("Ich bin ratlos. Wer ist es?");
//                    aktFrage.addJaFrage(io.IO.getName(), aktFrage.toString());
//                }
                aktFrage = aktFrage.getJaKnoten();
            } else if (antwort == NEIN) {
//                if(aktFrage.getNeinKnoten() == null)
//                {
//                    print("Ich bin ratlos. Wer ist es?");
//                    aktFrage.addNeinFrage(io.IO.getName(), aktFrage.toString());
//                }
                aktFrage = aktFrage.getNeinKnoten();
            } else if (antwort == VIELLEICHT) {
                verbleibend.add(aktFrage.getNeinKnoten());
                aktFrage = aktFrage.getJaKnoten();
            }
        }
        print("Ist es " + aktFrage + "?");
        do {
            antwort = io.IO.getAntwort();
            if(antwort == VIELLEICHT)
                print("JA oder NEIN ??");
        } while (antwort != JA && antwort != NEIN);
        if (antwort == JA) {
            print("Haha ich wusste es!");
        } else {
            if (verbleibend.size() < 1) {
                verloren();
            } else {
                aktFrage = verbleibend.poll();
                raten();
            }
        }
    }

    private static void verloren() {
        print("Wer ist es dann?");
        String name = io.IO.getName();
        print("Welche Frage wird für " + name + " mit JA beantwortet und für " + aktFrage + " mit NEIN?");
        String frage = io.IO.getFrage();
        aktFrage.addJaFrage(name, frage);
        try {
            speicher(erster);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Akinator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Akinator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void speicher(Frageknoten kopf) throws FileNotFoundException, IOException {
        erster = kopf;
        java.io.FileOutputStream fos = new java.io.FileOutputStream(new java.io.File("data.dat"));
        java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(fos);
        oos.writeObject(erster);
        oos.close();
        fos.close();
        print("Speichern erfolgreich.");
    }

    public static Frageknoten lade() {
        try {
            java.io.FileInputStream fis = new java.io.FileInputStream(new java.io.File("data.dat"));
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(fis);
            erster = (Frageknoten) ois.readObject();
            ois.close();
            fis.close();
            print("Laden erfolgreich.");
        } catch (IOException ex) {
            Logger.getLogger(Akinator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Akinator.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (erster == null) {
            return new Frageknoten("Dominik Messerschmidt");
        } else {
            return erster;
        }
    }

    public static void printAll(String[] texts) {
        for (String text : texts) {
            print(text);
        }
    }

    public static void print(String text) {
        System.out.println(text);
        io.IO.print(text);
    }

    public static void printTree() {
        printAll(erster.printAlphabetical());
    }
}