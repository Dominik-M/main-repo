package dragdroptest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.OverlayLayout;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

public class DragDropTest {

    public static void main(String[] args) {
        CustomComponent c1 = new CustomComponent(Color.red),
                c2 = new CustomComponent(Color.green);
        JToolBar toolBar = new JToolBar();
        toolBar.add(c1);
        toolBar.add(c2);
        JPanel dropPanel = new JPanel(null);
        dropPanel.setBackground(Color.white);
        JPanel loPanel = new JPanel(new BorderLayout());
        loPanel.add(toolBar, "North");
        loPanel.add(dropPanel);
        JPanel hiPanel = new JPanel(null);
        hiPanel.setOpaque(false);
        DragAction action = new DragAction(c1, c2, dropPanel);
        hiPanel.addMouseListener(action);
        hiPanel.addMouseMotionListener(action);
        JPanel panel = new JPanel();
        OverlayLayout overlay = new OverlayLayout(panel);
        panel.setLayout(overlay);
        panel.add(hiPanel);
        panel.add(loPanel);
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(panel);
        f.setSize(400, 400);
        f.setLocation(200, 200);
        f.setVisible(true);
    }
}

class CustomComponent extends JPanel {

    public CustomComponent(Color color) {
        setPreferredSize(new Dimension(40, 40));
        setBackground(color);
    }
}

class DragAction extends MouseInputAdapter {

    CustomComponent red, green;
    JPanel dropPanel, dragPanel;
    Point offset;
    boolean dragging;

    public DragAction(CustomComponent c1, CustomComponent c2, JPanel p) {
        red = c1;
        green = c2;
        dropPanel = p;
        offset = new Point();
        dragging = false;
    }

    public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();
        JPanel panel = (JPanel) e.getSource();
        Point p1 = SwingUtilities.convertPoint(panel, p, red);
        if (red.contains(p1)) {
            dragPanel = red;
            dragging = true;
        }
        Point p2 = SwingUtilities.convertPoint(panel, p, green);
        if (green.contains(p2)) {
            dragPanel = green;
            dragging = true;
        }
        if (dragging) {
            if (!panel.isAncestorOf(dragPanel)) {
                Container c = dragPanel.getParent();
                c.remove(dragPanel);
                panel.add(dragPanel);
                Point loc = new Point(dragPanel.getX(), dragPanel.getY());
                p1 = SwingUtilities.convertPoint((Component) c, loc, panel);
                offset.x = p.x - p1.x;
                offset.y = p.y - p1.y;
            }
            dragPanel.setLocation(p.x - offset.x, p.y - offset.y);
        }
    }

    public void mouseReleased(MouseEvent e) {
        Point p = e.getPoint();
        JPanel panel = (JPanel) e.getSource();
        if (panel.isAncestorOf(dragPanel)) {
            panel.remove(dragPanel);
            dropPanel.add(dragPanel);
            Point p1 = SwingUtilities.convertPoint(panel, p, dropPanel);
            int x = p1.x - offset.x;
            int y = p1.y - offset.y;
            dragPanel.setLocation(x, y);
            panel.repaint();
            dropPanel.repaint();
            dragging = false;
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (dragging) {
            JPanel panel = (JPanel) e.getSource();
            int x = e.getX() - offset.x;
            int y = e.getY() - offset.y;
            dragPanel.setLocation(x, y);
            panel.repaint();
        }
    }
}
