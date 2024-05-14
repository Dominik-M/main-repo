package tonfolge;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import javax.sound.midi.MidiEvent;
import javax.swing.JPanel;

public class Tasten extends JPanel
{

    private final LinkedList<TastenListener> tastenListener = new LinkedList<>();
    public static final int TASTEN_ANZ = 40;
    public static final int TIEFSTER_TON = 60 - TASTEN_ANZ / 2;
    private int cursor;
    private Tonfolge sampler = new Tonfolge();
    private final LinkedList<Integer> pressedKeys = new LinkedList<>();
    private final LinkedList<MidiEvent> pressedKeysAssociatedEvents = new LinkedList<>();
    public final MouseAdapter MOUSE_HANDLER = new MouseAdapter()
    {
        @Override
        public void mouseMoved(MouseEvent me)
        {
            cursor = me.getX() / (getWidth() / TASTEN_ANZ);
            repaint();
            for (TastenListener tl : tastenListener)
            {
                tl.cursorMoved(cursor);
            }
        }

        @Override
        public void mouseReleased(MouseEvent me)
        {
            for (TastenListener tl : tastenListener)
            {
                tl.keyPress(TIEFSTER_TON + cursor, 1);
            }
        }

    };

    public final KeyAdapter KEY_HANDLER = new KeyAdapter()
    {
        @Override
        public void keyPressed(KeyEvent ke)
        {
            System.out.println("Tasten.KeyPress(): " + ke.getKeyCode());
            int key = getConvertedKeyCode(ke.getKeyCode());
            if (key >= 0 && !pressedKeys.contains(key))
            {
                pressedKeys.add(key);
                for (TastenListener tl : tastenListener)
                {
                    tl.keyPress(key, 1);
                }
                if (!sampler.nochTöne())
                {
                    sampler.start(50);
                }
                pressedKeysAssociatedEvents.add(sampler.startNote(key, 1));
                repaint();
            }
        }

        @Override
        public void keyReleased(KeyEvent ke)
        {
            int key = getConvertedKeyCode(ke.getKeyCode());
            if (key >= 0 && pressedKeys.contains(key))
            {
                MidiEvent event = pressedKeysAssociatedEvents.remove(pressedKeys.indexOf((Integer) key));
                sampler.removeMidiEventFromTrack(event);
                pressedKeys.remove((Integer) key);
                repaint();
            }
        }

    };

    public Tasten()
    {
        setPreferredSize(new java.awt.Dimension(TASTEN_ANZ * 10, 50));
        setMinimumSize(getPreferredSize());
        addMouseMotionListener(MOUSE_HANDLER);
        addMouseListener(MOUSE_HANDLER);
        addKeyListener(KEY_HANDLER);
        sampler.setInstrument(1);
        sampler.start(50);
    }

    private static final int[] KEYBOARD_MAPPING =
    {
        49, 50, 51, 52, 53, 54, 55, 56, 57, 48,
        81, 87, 69, 82, 84, 90, 85, 73, 79, 80,
        65, 83, 68, 70, 71, 72, 74, 75, 76, 89,
        88, 67, 86, 66, 78, 77, 44, 46, 45
    };

    public int getConvertedKeyCode(int keycode)
    {
        for (int i = 0; i < KEYBOARD_MAPPING.length; i++)
        {
            if (KEYBOARD_MAPPING[i] == keycode)
            {
                return TIEFSTER_TON + i;
            }
        }
        return -1;
    }

    public void addTastenListener(TastenListener tl)
    {
        tastenListener.add(tl);
    }

    public boolean removeTastenListener(TastenListener tl)
    {
        return tastenListener.remove(tl);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        int tastenbreite = getWidth() / TASTEN_ANZ;
        for (int i = 0; i < TASTEN_ANZ; i++)
        {
            if (i == cursor)
            {
                g.setColor(Color.BLUE);
                g.fillRect(i * tastenbreite, 0, tastenbreite, getHeight());
                g.setColor(Color.BLACK);
            }
            for (int key : pressedKeys)
            {
                if (i + TIEFSTER_TON == key)
                {
                    g.setColor(Color.GREEN);
                    g.fillRect(i * tastenbreite, 0, tastenbreite, getHeight());
                    g.setColor(Color.BLACK);
                }
            }
            int x = i * tastenbreite;
            g.drawString(TIEFSTER_TON + i + "", x, getHeight());
            g.drawLine(x, 0, x, getHeight());
        }
    }
}
