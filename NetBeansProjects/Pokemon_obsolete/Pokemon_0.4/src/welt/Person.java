/*
 * Copyright (C) 2016 Dominik
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
package welt;

import image.ImageIO;
import image.ImageIO.ImageFile;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import language.Text;
import spiel.IO;
import spiel.InputListener;
import static spiel.Konstanten.HAUTFARBEN;
import spiel.Spieler;
import spiel.Spielwelt;

/**
 *
 * @author Dominik
 */
public class Person extends Objekt {

    private spiel.Queue<Text> dialog;
    private String name;
    private Text text;
    protected int richtung;
    private Item item;
    private Color farbe;
    private Color hautfarbe;
    private int imgUp, imgRight, imgDown, imgLeft;

    public Person(String n) {
        this(n, InputListener.DOWN, (Item) null);
    }

    public Person(String n, int blickrichtung, Text... texte) {
        this(n, blickrichtung, (Item) null, texte);
    }

    public Person(String n, int blickrichtung, Item anItem, Text... texte) {
        this(n, blickrichtung, anItem, HAUTFARBEN[(int) (Math.random() * HAUTFARBEN.length)], new java.awt.Color((int) (Math.random() * Integer.MAX_VALUE)), texte);
    }

    public Person(String n, int blickrichtung, Item anItem, Color skincolor, Color clothcolor, Text... texte) {
        super(ImageFile.TRAINER_DOWN, false);
        dialog = new spiel.Queue();
        for (Text txt : texte) {
            dialog.add(txt);
        }
        name = n;
        item = anItem;
        farbe = clothcolor;
        hautfarbe = skincolor;
        initImages();
        setRichtung(blickrichtung);
    }

    @Override
    public void init() {
        initImages();
    }

    public final void initImages() {
        int b = 16;
        int spot = b;
        BufferedImage img = new java.awt.image.BufferedImage(b, b, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        g.setColor(image.ImageIO.COLOR_TRANSPARENT);
        g.fillRect(0, 0, b, b);
        g.setColor(farbe);
        // Left image
        int[] schwarze = {5, 6, 7, 8, 9, 10, 4 + b, 11 + b, 3 + 2 * b, 12 + 2 * b, 2 + 3 * b, 3 + 3 * b, 12 + 3 * b,
            1 + 4 * b, 11 + 4 * b, 12 + 4 * b, 13 + 4 * b, 2 + 5 * b, 3 + 5 * b, 7 + 5 * b, 8 + 5 * b, 9 + 5 * b, 10 + 5 * b, 11 + 5 * b,
            12 + 5 * b, 13 + 5 * b, 3 + 6 * b, 5 + 6 * b, 8 + 6 * b, 9 + 6 * b, 10 + 6 * b, 11 + 6 * b, 12 + 6 * b, 13 + 6 * b,
            3 + 7 * b, 5 + 7 * b, 8 + 7 * b, 11 + 7 * b, 12 + 7 * b, 3 + 8 * b, 11 + 8 * b, 4 + 9 * b, 9 + 9 * b, 10 + 9 * b, 12 + 9 * b,
            5 + 10 * b, 6 + 10 * b, 7 + 10 * b, 8 + 10 * b, 9 + 10 * b, 12 + 10 * b, 6 + 11 * b, 7 + 11 * b, 10 + 11 * b, 12 + 11 * b,
            6 + 12 * b, 7 + 12 * b, 10 + 12 * b, 12 + 12 * b, 5 + 13 * b, 8 + 13 * b, 9 + 13 * b, 10 + 13 * b, 11 + 13 * b,
            5 + 14 * b, 10 + 14 * b, 6 + 15 * b, 7 + 15 * b, 8 + 15 * b, 9 + 15 * b};
        int[] rote = {5 + b, 6 + b, 7 + b, 8 + b, 9 + b, 10 + b, 4 + 2 * b, 5 + 2 * b, 6 + 2 * b, 7 + 2 * b, 8 + 2 * b, 9 + 2 * b,
            10 + 2 * b, 11 + 2 * b, 5 + 3 * b, 6 + 3 * b, 7 + 3 * b, 8 + 3 * b, 9 + 3 * b, 10 + 3 * b, 11 + 3 * b, 6 + 4 * b, 7 + 4 * b,
            8 + 4 * b, 9 + 4 * b, 10 + 4 * b, 4 + 5 * b, 5 + 5 * b, 6 + 5 * b, 5 + 9 * b, 11 + 9 * b, 10 + 10 * b, 11 + 10 * b, 11 + 11 * b,
            11 + 12 * b, 6 + 13 * b, 7 + 13 * b, 6 + 14 * b, 7 + 14 * b, 8 + 14 * b, 9 + 14 * b, 10 + 14 * b};
        int[] weisse = {4 + 3 * b, 2 + 4 * b, 3 + 4 * b, 4 + 4 * b, 5 + 4 * b, 4 + 6 * b, 6 + 6 * b, 7 + 6 * b, 4 + 7 * b, 6 + 7 * b,
            7 + 7 * b, 9 + 7 * b, 10 + 7 * b, 4 + 8 * b, 5 + 8 * b, 6 + 8 * b, 7 + 8 * b, 8 + 8 * b, 9 + 8 * b, 10 + 8 * b, 6 + 9 * b, 7 + 9 * b,
            8 + 9 * b, 8 + 11 * b, 9 + 11 * b, 8 + 12 * b, 9 + 12 * b};
        for (int i : rote) {
            g.fillRect((i % b * (spot / b)), (i / b * (spot / b)), spot / b, spot / b);
        }
        g.setColor(Color.BLACK);
        for (int i : schwarze) {
            g.fillRect((i % b * (spot / b)), (i / b * (spot / b)), spot / b, spot / b);
        }
        g.setColor(hautfarbe);
        for (int i : weisse) {
            g.fillRect((i % b * (spot / b)), (i / b * (spot / b)), spot / b, spot / b);
        }
        imgLeft = image.ImageIO.imgAlloc(img);
        img = new java.awt.image.BufferedImage(b, b, BufferedImage.TYPE_INT_ARGB);
        g = img.getGraphics();
        g.setColor(image.ImageIO.COLOR_TRANSPARENT);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        g.setColor(farbe);
        // Right image
        schwarze = new int[]{5, 6, 7, 8, 9, 10, 4 + b, 11 + b, 3 + 2 * b, 12 + 2 * b, 3 + 3 * b, 12 + 3 * b, 13 + 3 * b,
            2 + 4 * b, 3 + 4 * b, 4 + 4 * b, 14 + 4 * b, 2 + 5 * b, 3 + 5 * b, 4 + 5 * b, 5 + 5 * b, 6 + 5 * b, 7 + 5 * b, 8 + 5 * b,
            12 + 5 * b, 13 + 5 * b, 2 + 6 * b, 3 + 6 * b, 4 + 6 * b, 5 + 6 * b, 6 + 6 * b, 7 + 6 * b, 10 + 6 * b, 12 + 6 * b,
            3 + 7 * b, 4 + 7 * b, 7 + 7 * b, 10 + 7 * b, 12 + 7 * b, 4 + 8 * b, 12 + 8 * b, 3 + 9 * b, 5 + 9 * b, 6 + 9 * b, 11 + 9 * b,
            3 + 10 * b, 6 + 10 * b, 7 + 10 * b, 8 + 10 * b, 9 + 10 * b, 10 + 10 * b, 3 + 11 * b, 5 + 11 * b, 8 + 11 * b, 9 + 11 * b,
            3 + 12 * b, 5 + 12 * b, 8 + 12 * b, 9 + 12 * b, 4 + 13 * b, 5 + 13 * b, 6 + 13 * b, 7 + 13 * b, 10 + 13 * b,
            5 + 14 * b, 10 + 14 * b, 6 + 15 * b, 7 + 15 * b, 8 + 15 * b, 9 + 15 * b};
        rote = new int[]{5 + b, 6 + b, 7 + b, 8 + b, 9 + b, 10 + b, 4 + 2 * b, 5 + 2 * b, 6 + 2 * b, 7 + 2 * b, 8 + 2 * b, 9 + 2 * b,
            10 + 2 * b, 11 + 2 * b, 4 + 3 * b, 5 + 3 * b, 6 + 3 * b, 7 + 3 * b, 8 + 3 * b, 9 + 3 * b, 10 + 3 * b, 5 + 4 * b, 6 + 4 * b,
            7 + 4 * b, 8 + 4 * b, 9 + 4 * b, 9 + 5 * b, 10 + 5 * b, 11 + 5 * b, 4 + 9 * b, 10 + 9 * b, 4 + 10 * b, 5 + 10 * b, 4 + 11 * b,
            4 + 12 * b, 8 + 13 * b, 9 + 13 * b, 6 + 14 * b, 7 + 14 * b, 8 + 14 * b, 9 + 14 * b};
        weisse = new int[]{11 + 3 * b, 10 + 4 * b, 11 + 4 * b, 12 + 4 * b, 13 + 4 * b, 8 + 6 * b, 9 + 6 * b, 11 + 6 * b,
            5 + 7 * b, 6 + 7 * b, 8 + 7 * b, 9 + 7 * b, 11 + 7 * b, 5 + 8 * b, 6 + 8 * b, 7 + 8 * b, 8 + 8 * b, 9 + 8 * b, 10 + 8 * b,
            11 + 8 * b, 7 + 9 * b, 8 + 9 * b, 9 + 9 * b, 6 + 11 * b, 7 + 11 * b, 6 + 12 * b, 7 + 12 * b};
        for (int i : rote) {
            g.fillRect((i % b * (spot / b)), (i / b * (spot / b)), spot / b, spot / b);
        }
        g.setColor(Color.BLACK);
        for (int i : schwarze) {
            g.fillRect((i % b * (spot / b)), (i / b * (spot / b)), spot / b, spot / b);
        }
        g.setColor(hautfarbe);
        for (int i : weisse) {
            g.fillRect((i % b * (spot / b)), (i / b * (spot / b)), spot / b, spot / b);
        }
        imgRight = image.ImageIO.imgAlloc(img);
        img = new java.awt.image.BufferedImage(b, b, BufferedImage.TYPE_INT_ARGB);
        g = img.getGraphics();
        g.setColor(image.ImageIO.COLOR_TRANSPARENT);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        g.setColor(farbe);
        // Up image
        schwarze = new int[]{5, 6, 7, 8, 9, 10, 4 + b, 11 + b, 3 + 2 * b, 12 + 2 * b, 3 + 3 * b, 12 + 3 * b,
            2 + 4 * b, 3 + 4 * b, 12 + 4 * b, 13 + 4 * b, 2 + 5 * b, 3 + 5 * b, 4 + 5 * b, 11 + 5 * b, 12 + 5 * b, 13 + 5 * b,
            1 + 6 * b, 3 + 6 * b, 4 + 6 * b, 5 + 6 * b, 6 + 6 * b, 7 + 6 * b, 8 + 6 * b, 9 + 6 * b, 10 + 6 * b, 11 + 6 * b, 12 + 6 * b,
            14 + 6 * b, 1 + 7 * b, 4 + 7 * b, 5 + 7 * b, 6 + 7 * b, 7 + 7 * b, 8 + 7 * b, 9 + 7 * b, 10 + 7 * b, 11 + 7 * b, 14 + 7 * b,
            2 + 8 * b, 3 + 8 * b, 6 + 8 * b, 7 + 8 * b, 8 + 8 * b, 9 + 8 * b, 12 + 8 * b, 13 + 8 * b, 2 + 9 * b, 3 + 9 * b, 4 + 9 * b,
            5 + 9 * b, 10 + 9 * b, 11 + 9 * b, 12 + 9 * b, 13 + 9 * b, 1 + 10 * b, 3 + 10 * b, 4 + 10 * b, 6 + 10 * b, 7 + 10 * b,
            8 + 10 * b, 9 + 10 * b, 11 + 10 * b, 12 + 10 * b, 14 + 10 * b, 1 + 11 * b, 3 + 11 * b, 4 + 11 * b, 11 + 11 * b,
            12 + 11 * b, 14 + 11 * b, 2 + 12 * b, 3 + 12 * b, 4 + 12 * b, 5 + 12 * b, 10 + 12 * b, 11 + 12 * b, 12 + 12 * b,
            13 + 12 * b, 3 + 13 * b, 5 + 13 * b, 6 + 13 * b, 7 + 13 * b, 8 + 13 * b, 9 + 13 * b, 10 + 13 * b, 12 + 13 * b,
            3 + 14 * b, 7 + 14 * b, 8 + 14 * b, 12 + 14 * b, 4 + 15 * b, 5 + 15 * b, 6 + 15 * b, 9 + 15 * b, 10 + 15 * b, 11 + 15 * b};
        rote = new int[]{5 + b, 6 + b, 7 + b, 8 + b, 9 + b, 10 + b, 4 + 2 * b, 5 + 2 * b, 6 + 2 * b, 7 + 2 * b, 8 + 2 * b, 9 + 2 * b,
            10 + 2 * b, 11 + 2 * b, 4 + 3 * b, 5 + 3 * b, 6 + 3 * b, 7 + 3 * b, 8 + 3 * b, 9 + 3 * b, 10 + 3 * b, 11 + 3 * b, 4 + 4 * b,
            5 + 4 * b, 6 + 4 * b, 7 + 4 * b, 8 + 4 * b, 9 + 4 * b, 10 + 4 * b, 11 + 4 * b, 5 + 5 * b, 6 + 5 * b, 7 + 5 * b, 8 + 5 * b,
            9 + 5 * b, 10 + 5 * b, 6 + 9 * b, 7 + 9 * b, 8 + 9 * b, 9 + 9 * b, 5 + 10 * b, 10 + 10 * b, 5 + 11 * b, 6 + 11 * b,
            9 + 11 * b, 10 + 11 * b, 6 + 12 * b, 7 + 12 * b, 8 + 12 * b, 9 + 12 * b, 4 + 13 * b, 11 + 13 * b,
            4 + 14 * b, 5 + 14 * b, 6 + 14 * b, 9 + 14 * b, 10 + 14 * b, 11 + 14 * b};
        weisse = new int[]{2 + 6 * b, 13 + 6 * b, 2 + 7 * b, 3 + 7 * b, 12 + 7 * b, 13 + 7 * b, 4 + 8 * b, 5 + 8 * b, 10 + 8 * b,
            11 + 8 * b, 2 + 10 * b, 13 + 10 * b, 2 + 11 * b, 7 + 11 * b, 8 + 11 * b, 13 + 11 * b};
        for (int i : rote) {
            g.fillRect((i % b * (spot / b)), (i / b * (spot / b)), spot / b, spot / b);
        }
        g.setColor(Color.BLACK);
        for (int i : schwarze) {
            g.fillRect((i % b * (spot / b)), (i / b * (spot / b)), spot / b, spot / b);
        }
        g.setColor(hautfarbe);
        for (int i : weisse) {
            g.fillRect((i % b * (spot / b)), (i / b * (spot / b)), spot / b, spot / b);
        }
        imgUp = image.ImageIO.imgAlloc(img);
        img = new java.awt.image.BufferedImage(b, b, BufferedImage.TYPE_INT_ARGB);
        g = img.getGraphics();
        g.setColor(image.ImageIO.COLOR_TRANSPARENT);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        g.setColor(farbe);
        // Down image
        schwarze = new int[]{5, 6, 7, 8, 9, 10, 4 + b, 11 + b, 3 + 2 * b, 12 + 2 * b, 3 + 3 * b, 12 + 3 * b,
            2 + 4 * b, 3 + 4 * b, 4 + 4 * b, 11 + 4 * b, 12 + 4 * b, 13 + 4 * b, 2 + 5 * b, 3 + 5 * b,
            5 + 5 * b, 6 + 5 * b, 7 + 5 * b, 8 + 5 * b, 9 + 5 * b, 10 + 5 * b, 12 + 5 * b, 13 + 5 * b,
            1 + 6 * b, 3 + 6 * b, 12 + 6 * b, 14 + 6 * b, 1 + 7 * b, 6 + 7 * b, 9 + 7 * b, 14 + 7 * b,
            2 + 8 * b, 3 + 8 * b, 6 + 8 * b, 9 + 8 * b, 12 + 8 * b, 13 + 8 * b, 2 + 9 * b, 3 + 9 * b,
            4 + 9 * b, 11 + 9 * b, 12 + 9 * b, 13 + 9 * b, 1 + 10 * b, 4 + 10 * b, 5 + 10 * b,
            6 + 10 * b, 7 + 10 * b, 8 + 10 * b, 9 + 10 * b, 10 + 10 * b, 11 + 10 * b, 14 + 10 * b,
            1 + 11 * b, 4 + 11 * b, 5 + 11 * b, 6 + 11 * b, 7 + 11 * b, 8 + 11 * b, 9 + 11 * b,
            10 + 11 * b, 11 + 11 * b, 14 + 11 * b, 2 + 12 * b, 3 + 12 * b, 4 + 12 * b, 7 + 12 * b,
            8 + 12 * b, 11 + 12 * b, 12 + 12 * b, 13 + 12 * b, 3 + 13 * b, 5 + 13 * b, 6 + 13 * b,
            9 + 13 * b, 10 + 13 * b, 12 + 13 * b, 3 + 14 * b, 7 + 14 * b, 8 + 14 * b, 12 + 14 * b,
            4 + 15 * b, 5 + 15 * b, 6 + 15 * b, 9 + 15 * b, 10 + 15 * b, 11 + 15 * b};
        rote = new int[]{5 + b, 6 + b, 7 + b, 8 + b, 9 + b, 10 + b, 4 + 2 * b, 5 + 2 * b, 6 + 2 * b, 7 + 2 * b, 8 + 2 * b,
            9 + 2 * b, 10 + 2 * b, 11 + 2 * b, 4 + 3 * b, 5 + 3 * b, 6 + 3 * b, 7 + 3 * b, 8 + 3 * b,
            9 + 3 * b, 10 + 3 * b, 11 + 3 * b, 5 + 4 * b, 10 + 4 * b, 7 + 9 * b, 8 + 9 * b, 5 + 12 * b,
            6 + 12 * b, 9 + 12 * b, 10 + 12 * b, 4 + 13 * b, 7 + 13 * b, 8 + 13 * b, 11 + 13 * b,
            4 + 14 * b, 5 + 14 * b, 6 + 14 * b, 9 + 14 * b, 10 + 14 * b, 11 + 14 * b};
        weisse = new int[]{6 + 4 * b, 7 + 4 * b, 8 + 4 * b, 9 + 4 * b, 4 + 5 * b, 11 + 5 * b, 2 + 6 * b, 4 + 6 * b, 5 + 6 * b,
            6 + 6 * b, 7 + 6 * b, 8 + 6 * b, 9 + 6 * b, 10 + 6 * b, 11 + 6 * b, 13 + 6 * b, 2 + 7 * b,
            3 + 7 * b, 4 + 7 * b, 5 + 7 * b, 7 + 7 * b, 8 + 7 * b, 10 + 7 * b, 11 + 7 * b, 12 + 7 * b, 13 + 7 * b,
            4 + 8 * b, 5 + 8 * b, 7 + 8 * b, 8 + 8 * b, 10 + 8 * b, 11 + 8 * b, 5 + 9 * b, 6 + 9 * b,
            9 + 9 * b, 10 + 9 * b, 2 + 10 * b, 3 + 10 * b, 12 + 10 * b, 13 + 10 * b, 2 + 11 * b,
            3 + 11 * b, 12 + 11 * b, 13 + 11 * b};
        for (int i : rote) {
            g.fillRect((i % b * (spot / b)), (i / b * (spot / b)), spot / b, spot / b);
        }
        g.setColor(Color.BLACK);
        for (int i : schwarze) {
            g.fillRect((i % b * (spot / b)), (i / b * (spot / b)), spot / b, spot / b);
        }
        g.setColor(hautfarbe);
        for (int i : weisse) {
            g.fillRect((i % b * (spot / b)), (i / b * (spot / b)), spot / b, spot / b);
        }
        imgDown = image.ImageIO.imgAlloc(img);
    }

    @Override
    public void benutzt() {
        Spieler s = Spielwelt.getCurrentWorld().getSpieler();
        setRichtung(spiel.Utils.invertDirection(s.getDirection()));
        if (dialog.peak() != null) {
            text = dialog.pull();
        }
        IO.IOMANAGER.println(this + ": " + text, IO.MessageType.IMPORTANT);
        if (item != null) {
            s.addItem(item);
            IO.IOMANAGER.println(s.toString() + " erhält " + item.toString(), IO.MessageType.IMPORTANT);
            item = null;
        }
    }

    @Override
    public void betreten() {
    }

    @Override
    public void draw(Graphics g) {
        Image img = null;
        int width = g.getClipBounds().width;
        int height = g.getClipBounds().height;
        if (richtung == InputListener.UP && imgUp >= 0) {
            img = ImageIO.getImage(imgUp).getScaledInstance(width, height, 0);
        } else if (richtung == InputListener.LEFT && imgLeft >= 0) {
            img = ImageIO.getImage(imgLeft).getScaledInstance(width, height, 0);
        } else if (richtung == InputListener.RIGHT && imgRight >= 0) {
            img = ImageIO.getImage(imgRight).getScaledInstance(width, height, 0);
        } else if (imgDown >= 0) {
            img = ImageIO.getImage(imgDown).getScaledInstance(width, height, 0);
        } else {
            super.draw(g);
            return;
        }
        g.drawImage(img, 0, 0, null);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item anItem) {
        item = anItem;
    }

    public Color getColor() {
        return farbe;
    }

    public Color getSkinColor() {
        return hautfarbe;
    }

    public void setColor(Color kleidungsfarbe) {
        if (kleidungsfarbe != null) {
            farbe = kleidungsfarbe;
        }
    }

    public void setSkinColor(Color hautfarb) {
        if (hautfarb != null) {
            hautfarbe = hautfarb;
        }
    }

    public final void setRichtung(int blickrichtung) {
        richtung = blickrichtung;
        if (richtung == InputListener.UP) {
            setImageFile(ImageFile.TRAINER_UP);
        } else if (richtung == InputListener.LEFT) {
            setImageFile(ImageFile.TRAINER_LEFT);
        } else if (richtung == InputListener.RIGHT) {
            setImageFile(ImageFile.TRAINER_RIGHT);
        } else {
            setImageFile(ImageFile.TRAINER_DOWN);
        }
    }

    public void setDialog(Text... texte) {
        dialog = new spiel.Queue();
        for (Text txt : texte) {
            dialog.add(txt);
        }
    }

    public void addDialog(Text... texte) {
        for (Text txt : texte) {
            dialog.add(txt);
        }
    }

    public Object[] getDialog() {
        return dialog.toArray();
    }

    @Override
    public String toString() {
        return name;
    }
}
