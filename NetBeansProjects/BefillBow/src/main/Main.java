/*
 * Copyright (C) 2021 Dundun <dominikmesserschmidt@googlemail.com>
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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Dundun <dominikmesserschmidt@googlemail.com>
 * Created 13.05.2021
 */
public class Main {

    public static final String INPUT_FILENAME = "charakterbogen.jpg";
    public static final int START_Y = 780;
    public static final int START_X1 = 180, START_X2 = 521, START_X3 = 850;
    public static final int GAP_Y = 57;
    public static final String STD_FONT_NAME = "Consolas";

    public static final String[] HANDELN = {"Nahkampf", "Fernkampf", "Basteln", "Rauchen", "Saufen", "Tanzen", "Klettern", "Verstecken", "Schleichen", "Stehlen"};
    public static final String[] WISSEN = {"Mathematik", "Physik", "Geschichte", "Geographie", "Kultur", "Medizin", "Pflanzenkunde", "Tierkunde", "Unnützes Wissen", "Mystik"};
    public static final String[] INTERACT = {"Begeistern/Führen", "Lügen", "Empathie", "Menschenkenntnis", "Humor", "Gesang", "Tierliebe", "Feilschen", "Verführen", "Sprachen"};

    public static void main(String[] args) {
        try {
            BufferedImage img = ImageIO.read(new File(INPUT_FILENAME));
            Graphics g = img.createGraphics();
            g.setColor(Color.BLACK);

            for (int i = 0; i < HANDELN.length; i++) {
                String text = HANDELN[i];
                int fontsize = calcFontSize(text);
                g.setFont(new Font(STD_FONT_NAME, 0, fontsize));
                g.drawString(text, START_X1, START_Y + GAP_Y * i);
            }
            for (int i = 0; i < WISSEN.length; i++) {
                String text = WISSEN[i];
                int fontsize = calcFontSize(text);
                g.setFont(new Font(STD_FONT_NAME, 0, fontsize));
                g.drawString(text, START_X2, START_Y + GAP_Y * i);
            }
            for (int i = 0; i < INTERACT.length; i++) {
                String text = INTERACT[i];
                int fontsize = calcFontSize(text);
                g.setFont(new Font(STD_FONT_NAME, 0, fontsize));
                g.drawString(text, START_X3, START_Y + GAP_Y * i);
            }

            File outputfile = new File("charakterbogen_filled.png");
            ImageIO.write(img, "png", outputfile);
            System.out.println("Printed to " + outputfile.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static int calcFontSize(String text) {
        //@TODO this function is crap
        return text.length() > 8 ? Math.max(16, (34 - text.length())) : 25;
    }

}
