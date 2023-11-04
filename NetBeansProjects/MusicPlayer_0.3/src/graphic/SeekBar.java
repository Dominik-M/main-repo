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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JProgressBar;
import sound.Sound;
import utils.Settings;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 15.03.2016
 */
public class SeekBar extends JProgressBar implements MouseListener, MouseMotionListener,
        ActionListener
{

    private static final long serialVersionUID = 3834307926191037165L;

    private final javax.swing.Timer refreshTimer = new javax.swing.Timer(
            utils.Constants.REFRESH_DELAY, this);

    public SeekBar()
    {
        this.setBackground(utils.Settings.settings.componentColor);
        this.setForeground(Settings.settings.fontColor);
        this.setStringPainted(true);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        this.setString(millisToTimeString(this.getValue()) + " / "
                + millisToTimeString(this.getMaximum()));
        super.paintComponent(g);
        // int size = this.getHeight();
        // g.setColor(utils.Settings.settings.componentColor.brighter());
        // g.fillOval(millisToCoordinate(getValue()) - size / 2, 0, size, size);
    }

    public String millisToTimeString(int millis)
    {
        millis /= 1000;
        if (millis % 60 < 10)
        {
            return millis / 60 + ":0" + millis % 60;
        }
        return millis / 60 + ":" + millis % 60;
    }

    public int millisToCoordinate(int millis)
    {
        return getWidth() * millis / getMaximum();
    }

    public int coordinateToMillis(int x)
    {
        return getMaximum() * x / getWidth();
    }

    public void setRefreshTimerPaused(boolean paused)
    {
        if (refreshTimer.isRunning() && paused)
        {
            refreshTimer.stop();
        }
        else if (!refreshTimer.isRunning() && !paused)
        {
            refreshTimer.start();
        }
    }

    @Override
    public void setValue(int value)
    {
        if (value >= 0)
        {
            super.setValue(value);
            Sound.setMicrosecondPosition(value * 1000L);
        }
    }

    @Override
    public void mouseDragged(MouseEvent arg0)
    {
        setValue(this.coordinateToMillis(arg0.getX()));
    }

    @Override
    public void mouseMoved(MouseEvent arg0)
    {
    }

    @Override
    public void mouseClicked(MouseEvent arg0)
    {
    }

    @Override
    public void mouseEntered(MouseEvent arg0)
    {
    }

    @Override
    public void mouseExited(MouseEvent arg0)
    {
    }

    @Override
    public void mousePressed(MouseEvent arg0)
    {
        setValue(this.coordinateToMillis(arg0.getX()));
    }

    @Override
    public void mouseReleased(MouseEvent arg0)
    {
        Sound.setMicrosecondPosition(getValue() * 1000);
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        super.setValue((int) (Sound.getMillisecondPosition()));
    }

    public void setColor(Color blue)
    {
        super.setForeground(blue);
    }
}
