package bohnen.gui;

import bohnen.Bohnenkarte;
import bohnen.SpielListener;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class GamePanel extends javax.swing.JPanel implements MouseListener,MouseMotionListener{
    private SpielListener listener;
    private String selPos,dropPos; // Position description of MouseDrag
    private Bohnenkarte selected; // dragged Karte
    private Image selImage; // Image of selected
    private int mouseX,mouseY; // last position of mouse cursor when dragged
    // Positionsbeschreibung von Stapel und Ablage
    public static final String STACK=SpielListener.STACK;
    public static final String ABLAGE=SpielListener.ABLAGE;
    // Positionsbeschreibung der Karten von Spieler 1 (Mitte)
    public static final String S0HAND=0+SpielListener.SEPARATOR+SpielListener.HAND_POSTFIX;
    public static final String S0ABLAGE=0+SpielListener.SEPARATOR+SpielListener.ABLAGE_POSTFIX;
    public static final String S0FELD0=0+SpielListener.SEPARATOR+SpielListener.FELD_POSTFIX+0;
    public static final String S0FELD1=0+SpielListener.SEPARATOR+SpielListener.FELD_POSTFIX+1;
    public static final String S0FELD2=0+SpielListener.SEPARATOR+SpielListener.FELD_POSTFIX+2;
    // Positionsbeschreibung der Karten von Spieler 2 (Links)
    public static final String S1HAND=1+SpielListener.SEPARATOR+SpielListener.HAND_POSTFIX;
    public static final String S1ABLAGE=1+SpielListener.SEPARATOR+SpielListener.ABLAGE_POSTFIX;
    public static final String S1FELD0=1+SpielListener.SEPARATOR+SpielListener.FELD_POSTFIX+0;
    public static final String S1FELD1=1+SpielListener.SEPARATOR+SpielListener.FELD_POSTFIX+1;
    public static final String S1FELD2=1+SpielListener.SEPARATOR+SpielListener.FELD_POSTFIX+2;
    // Positionsbeschreibung der Karten von Spieler 3 (Rechts)
    public static final String S2HAND=2+SpielListener.SEPARATOR+SpielListener.HAND_POSTFIX;
    public static final String S2ABLAGE=2+SpielListener.SEPARATOR+SpielListener.ABLAGE_POSTFIX;
    public static final String S2FELD0=2+SpielListener.SEPARATOR+SpielListener.FELD_POSTFIX+0;
    public static final String S2FELD1=2+SpielListener.SEPARATOR+SpielListener.FELD_POSTFIX+1;
    public static final String S2FELD2=2+SpielListener.SEPARATOR+SpielListener.FELD_POSTFIX+2;

    /**
     * Creates new form GamePanel
     */
    public GamePanel() {
        initComponents();
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
    }
    
    public void setListener(SpielListener sl){
        listener=sl;
    }
    
    public void karteMoved(Bohnenkarte karte,String from, String to){
        System.out.println("move "+karte+" from "+from+" to "+to);
        if(addKarte(karte,to))
          removeKarte(from);
        repaint();
    }
    
    private Bohnenkarte removeKarte(String pos){
        KartenView view=findViewByName(pos);
        if(view != null){
            return view.poll();
        }
        return null;
    }
    
    private boolean addKarte(Bohnenkarte karte, String pos) {
        if (karte != null && pos != null) {
            KartenView view=findViewByName(pos);
            if(view != null){
                view.addKarte(karte);
                return true;
            }
        }
        return false;
    }
    
    private boolean addFirst(Bohnenkarte karte, String pos) {
        if (karte != null && pos != null) {
            KartenView view=findViewByName(pos);
            if(view != null){
                view.addFirst(karte);
                return true;
            }
        }
        return false;
    }
    
    private void select(String pos){
        KartenView view=findViewByName(pos);
        if(view != null){
            selImage=view.peekImage();
            selected=view.poll();
        }
    }
    
    private KartenView findViewByName(String pos){
        switch(pos){
            case STACK:
                return stack;
            case ABLAGE:
                return ablage;
        }
        if(pos.startsWith(S0ABLAGE)){
            return s0ablage;
        }else if(pos.startsWith(S1ABLAGE)){
            return s1ablage;
        }else if(pos.startsWith(S2ABLAGE)){
            return s2ablage;
        }else if(pos.startsWith(S0HAND)){
            return s0hand;
        }else if(pos.startsWith(S0FELD0)){
            return s0feld0;
        }else if(pos.startsWith(S0FELD1)){
            return s0feld1;
        }else if(pos.startsWith(S0FELD2)){
            return s0feld2;
        }else if(pos.startsWith(S1HAND)){
            return s1hand;
        }else if(pos.startsWith(S1FELD0)){
            return s1feld0;
        }else if(pos.startsWith(S1FELD1)){
            return s1feld1;
        }else if(pos.startsWith(S1FELD2)){
            return s1feld2;
        }else if(pos.startsWith(S2HAND)){
            return s2hand;
        }else if(pos.startsWith(S2FELD0)){
            return s2feld0;
        }else if(pos.startsWith(S2FELD1)){
            return s2feld1;
        }else if(pos.startsWith(S2FELD2)){
            return s2feld2;
        }
        return null;
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(selected!=null){
            g.drawImage(selImage, mouseX-getLocationOnScreen().x, mouseY-getLocationOnScreen().y, null);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        stack = new KartenView(STACK,this,this);
        ablage = new KartenView(ABLAGE,this,this);
        s0ablage = new KartenView(S0ABLAGE,this,this);
        s0feld0 = new KartenView(S0FELD0,this,this);
        s0feld1 = new KartenView(S0FELD1,this,this);
        s0feld2 = new KartenView(S0FELD2,this,this);
        s0hand = new KartenView(S0HAND,this,this);
        s2hand = new KartenView(S2HAND,null,null);
        s1hand = new KartenView(S1HAND,null,null);
        s2ablage = new KartenView(S2ABLAGE,this,this);
        s1ablage = new KartenView(S1ABLAGE,this,this);
        s1feld0 = new KartenView(S1FELD0,null,null);
        s1feld1 = new KartenView(S1FELD1,null,null);
        s1feld2 = new KartenView(S1FELD2,null,null);
        s2feld0 = new KartenView(S2FELD0,null,null);
        s2feld1 = new KartenView(S2FELD1,null,null);
        s2feld2 = new KartenView(S2FELD2,null,null);

        stack.setBackground(new java.awt.Color(255, 255, 255));
        stack.setVerdeckt(true);
        stack.setVertical(false);

        ablage.setBackground(new java.awt.Color(255, 255, 255));
        ablage.setVerdeckt(true);
        ablage.setVertical(false);

        s0ablage.setBackground(new java.awt.Color(255, 255, 255));
        s0ablage.setVerdeckt(false);
        s0ablage.setVertical(false);

        s0feld0.setBackground(new java.awt.Color(102, 51, 0));
        s0feld0.setVerdeckt(false);
        s0feld0.setVertical(true);

        s0feld1.setBackground(new java.awt.Color(102, 51, 0));
        s0feld1.setVerdeckt(false);
        s0feld1.setVertical(true);

        s0feld2.setBackground(new java.awt.Color(102, 51, 0));
        s0feld2.setVerdeckt(false);
        s0feld2.setVertical(true);

        s0hand.setBackground(new java.awt.Color(255, 255, 255));
        s0hand.setVerdeckt(false);
        s0hand.setVertical(false);

        s2hand.setBackground(new java.awt.Color(255, 255, 255));
        s2hand.setDirection(KartenView.RIGHT);
        s2hand.setVertical(true);

        s1hand.setBackground(new java.awt.Color(255, 255, 255));
        s1hand.setDirection(KartenView.LEFT);
        s1hand.setVertical(true);

        s2ablage.setBackground(new java.awt.Color(255, 255, 255));
        s2ablage.setDirection(KartenView.RIGHT);
        s2ablage.setVerdeckt(false);
        s2ablage.setVertical(true);

        s1ablage.setBackground(new java.awt.Color(255, 255, 255));
        s1ablage.setDirection(KartenView.LEFT);
        s1ablage.setVerdeckt(false);
        s1ablage.setVertical(true);

        s1feld0.setBackground(new java.awt.Color(102, 51, 0));
        s1feld0.setDirection(KartenView.LEFT);
        s1feld0.setVerdeckt(false);

        s1feld1.setBackground(new java.awt.Color(102, 51, 0));
        s1feld1.setDirection(KartenView.LEFT);
        s1feld1.setVerdeckt(false);

        s1feld2.setBackground(new java.awt.Color(102, 51, 0));
        s1feld2.setDirection(KartenView.LEFT);
        s1feld2.setVerdeckt(false);

        s2feld0.setBackground(new java.awt.Color(102, 51, 0));
        s2feld0.setDirection(KartenView.RIGHT);
        s2feld0.setVerdeckt(false);

        s2feld1.setBackground(new java.awt.Color(102, 51, 0));
        s2feld1.setDirection(KartenView.RIGHT);
        s2feld1.setVerdeckt(false);

        s2feld2.setBackground(new java.awt.Color(102, 51, 0));
        s2feld2.setDirection(KartenView.RIGHT);
        s2feld2.setVerdeckt(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(s1ablage, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(s1hand, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(s1feld2, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s1feld0, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s1feld1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(stack, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ablage, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(s0hand, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(s0feld0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(s0feld1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(s0feld2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(s2feld2, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                        .addComponent(s2feld1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(s2feld0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(s0ablage, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(s2ablage, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(s2hand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(s1feld0, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(s1feld1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(s1feld2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(s1hand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(s2feld2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(s2feld1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(s2feld0, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(s0ablage, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(s1ablage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 38, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(s2hand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(s2ablage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ablage, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(stack, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(s0feld0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(s0feld1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(s0feld2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(s0hand, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private bohnen.gui.KartenView ablage;
    private bohnen.gui.KartenView s0ablage;
    private bohnen.gui.KartenView s0feld0;
    private bohnen.gui.KartenView s0feld1;
    private bohnen.gui.KartenView s0feld2;
    private bohnen.gui.KartenView s0hand;
    private bohnen.gui.KartenView s1ablage;
    private bohnen.gui.KartenView s1feld0;
    private bohnen.gui.KartenView s1feld1;
    private bohnen.gui.KartenView s1feld2;
    private bohnen.gui.KartenView s1hand;
    private bohnen.gui.KartenView s2ablage;
    private bohnen.gui.KartenView s2feld0;
    private bohnen.gui.KartenView s2feld1;
    private bohnen.gui.KartenView s2feld2;
    private bohnen.gui.KartenView s2hand;
    private bohnen.gui.KartenView stack;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        selPos=e.getSource().toString();
        select(selPos);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(selected!=null){
          addFirst(selected,selPos);
          if(listener!=null && dropPos!=null && !dropPos.equals(selPos)){
            listener.karteMoved(this,selected, selPos, dropPos);
          }
          selected=null;
          selPos=null;
          dropPos=null;
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(selPos!=null && !e.getSource().equals(this)){
            dropPos=e.getSource().toString();
        }
        else dropPos=null;
    }

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX=e.getXOnScreen();
        mouseY=e.getYOnScreen();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
}
