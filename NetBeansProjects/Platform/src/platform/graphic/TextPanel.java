package platform.graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import platform.graphic.InputConfig.Key;
import platform.utils.Settings;

public class TextPanel extends MainPanel
{

    private javax.swing.JLabel label;

    public TextPanel()
    {
        init();
    }

    private void init()
    {
    }

    /**
     *
     */
    private static final long serialVersionUID = -6094447515371261862L;

    @Override
    public void onSelect()
    {
        requestFocus();
    }

    @Override
    public void onDisselect()
    {
    }

    @Override
    public void drawGUI(Graphics2D g)
    {
        g.setColor((Color) Settings.get("backgroundColor"));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        // TODO draw GUI in the graphics context
        if (!this.printQueueIsEmpty())
        {
            g.setColor((Color) Settings.get("fontColor"));
            g.setFont((Font) Settings.get("font"));
            g.drawString(printQueue.getFirst(), 10, ((Font) Settings.get("font")).getSize() + 10);
        }
        if (label != null)
        {
            label.paint(g);
        }
    }

    @Override
    public void keyPressed(Key key)
    {

    }

    @Override
    public void keyReleased(Key key)
    {

    }

}
