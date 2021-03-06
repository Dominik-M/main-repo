/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spiel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;

/**
 *
 * @author Dundun
 */
public class Steuerung extends javax.swing.JDialog implements ActionListener,KeyListener{
    private static int up=KeyEvent.VK_W,
          left=KeyEvent.VK_A,
          down=KeyEvent.VK_S,
          right=KeyEvent.VK_D,
          a=KeyEvent.VK_SPACE,
          b=KeyEvent.VK_SHIFT,
          enter=KeyEvent.VK_ENTER,
          save=KeyEvent.VK_1,
          load=KeyEvent.VK_F1;

    /**
     * Creates new form Steuerung
     */
    public Steuerung() {
        initComponents();
        upB.setText(KeyEvent.getKeyText(up));
        leftB.setText(KeyEvent.getKeyText(left));
        downB.setText(KeyEvent.getKeyText(down));
        rightB.setText(KeyEvent.getKeyText(right));
        interact.setText(KeyEvent.getKeyText(a));
        fastB.setText(KeyEvent.getKeyText(b));
        enterB.setText(KeyEvent.getKeyText(enter));
        saveB.setText(KeyEvent.getKeyText(save));
        loadB.setText(KeyEvent.getKeyText(load));
        upB.addActionListener(this);
        leftB.addActionListener(this);
        downB.addActionListener(this);
        rightB.addActionListener(this);
        interact.addActionListener(this);
        fastB.addActionListener(this);
        enterB.addActionListener(this);
        saveB.addActionListener(this);
        loadB.addActionListener(this);
        reset.addActionListener(this);
        setLocation(Javamon.getLoc());
        setModal(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        upB = new javax.swing.JButton();
        leftB = new javax.swing.JButton();
        downB = new javax.swing.JButton();
        rightB = new javax.swing.JButton();
        interact = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        reset = new javax.swing.JButton();
        saveB = new javax.swing.JButton();
        loadB = new javax.swing.JButton();
        fastB = new javax.swing.JButton();
        enterB = new javax.swing.JButton();

        jLabel7.setText("jLabel7");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("UP");

        jLabel2.setText("LEFT");

        jLabel3.setText("DOWN");

        jLabel4.setText("RIGHT");

        jLabel5.setText("INTERACT");

        jLabel6.setText("ENTER");

        upB.setText("jButton1");

        leftB.setText("jButton1");

        downB.setText("jButton1");

        rightB.setText("jButton1");

        interact.setText("jButton1");

        jLabel8.setText("FAST");

        jLabel9.setText("SAVE");

        jLabel10.setText("LOAD");

        reset.setText("Reset");

        saveB.setText("jButton1");

        loadB.setText("jButton1");

        fastB.setText("jButton1");

        enterB.setText("jButton1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(downB)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(leftB)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(rightB)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(interact)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(upB)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fastB)
                    .addComponent(saveB)
                    .addComponent(loadB)
                    .addComponent(reset)
                    .addComponent(enterB))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(fastB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reset))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(upB))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(leftB)
                                    .addComponent(jLabel8)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(enterB)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(downB)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(rightB)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(interact))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton downB;
    private javax.swing.JButton enterB;
    private javax.swing.JButton fastB;
    private javax.swing.JButton interact;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton leftB;
    private javax.swing.JButton loadB;
    private javax.swing.JButton reset;
    private javax.swing.JButton rightB;
    private javax.swing.JButton saveB;
    private javax.swing.JButton upB;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
      JButton bu=(JButton)e.getSource();
      if(bu.equals(reset)){
        up=KeyEvent.VK_W;
        left=KeyEvent.VK_A;
        down=KeyEvent.VK_S;
        right=KeyEvent.VK_D;
        a=KeyEvent.VK_SPACE;
        b=KeyEvent.VK_SHIFT;
        enter=KeyEvent.VK_ENTER;
        save=KeyEvent.VK_1;
        load=KeyEvent.VK_F1;
        upB.setText(KeyEvent.getKeyText(up));
        leftB.setText(KeyEvent.getKeyText(left));
        downB.setText(KeyEvent.getKeyText(down));
        rightB.setText(KeyEvent.getKeyText(right));
        interact.setText(KeyEvent.getKeyText(a));
        fastB.setText(KeyEvent.getKeyText(b));
        enterB.setText(KeyEvent.getKeyText(enter));
        saveB.setText(KeyEvent.getKeyText(save));
        loadB.setText(KeyEvent.getKeyText(load));
      }else bu.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
      JButton bu=(JButton)e.getSource();
      bu.removeKeyListener(this);
      bu.setText(KeyEvent.getKeyText(e.getKeyCode()));
      if(bu.equals(upB))up=e.getKeyCode();
      else if(bu.equals(leftB))left=e.getKeyCode();
      else if(bu.equals(downB))down=e.getKeyCode();
      else if(bu.equals(rightB))right=e.getKeyCode();
      else if(bu.equals(enterB))enter=e.getKeyCode();
      else if(bu.equals(fastB))b=e.getKeyCode();
      else if(bu.equals(interact))a=e.getKeyCode();
      else if(bu.equals(saveB))save=e.getKeyCode();
      else if(bu.equals(loadB))load=e.getKeyCode();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
  public static int getUp(){
    return up;
  }
  
  public static int getLeft(){
    return left;
  }
  
  public static int getDown(){
    return down;
  }
  
  public static int getRight(){
    return right;
  }
  
  public static int getEnter(){
    return enter;
  }
  
  public static int getA(){
    return a;
  }
  
  public static int getB(){
    return b;
  }
  
  public static int getSave(){
    return save;
  }
  
  public static int getLoad(){
    return load;
  }
}