package welt.objekte;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import spiel.Javamon;
import spiel.OutputListener;
import spiel.Steuerung;

public class Münztauscher extends Person implements OutputListener{
    private welt.items.Item[] angebot;
    private String dialog;
    
    public Münztauscher(){
      dialog="";
    }
    
    public Münztauscher(int blickrichtung, welt.items.Item... items){
      super(blickrichtung);
      angebot=items;
      dialog="";
    }
    
    public Münztauscher(int blickrichtung,String text, welt.items.Item... items){
      super(blickrichtung);
      angebot=items;
      dialog=text;
    }
    
    @Override
    public void benutzt(){
      super.benutzt();
      Javamon.sout("Hier kannst du deine Münzen gegen Preise eintauschen. "+dialog);
      Javamon.addListener(this);
    }

    @Override
    public void ausgabeFertig() {
      new Tauschen(angebot).setVisible(true);
    }
}

class Tauschen extends javax.swing.JDialog implements KeyListener{

    public Tauschen(final welt.items.Item[] items) {
      initComponents();
      setModal(true);
      setLocation(Javamon.getLoc());
      angebot.setModel(new javax.swing.AbstractListModel() {
            public int getSize() { return items.length; }
            public Object getElementAt(int i) { return items[i]; }
        });
      geld.setText("Münzen: "+Javamon.getSpieler().getMünzen());
      this.addKeyListener(this);
      kaufen.addKeyListener(this);
      angebot.addKeyListener(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        angebot = new javax.swing.JList();
        preis = new javax.swing.JLabel();
        kaufen = new javax.swing.JButton();
        geld = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        angebot.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        angebot.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                angebotValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(angebot);

        preis.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        kaufen.setText("Kaufen");
        kaufen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kaufenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(preis)
                    .addComponent(kaufen)
                    .addComponent(geld))
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(geld)
                .addGap(18, 18, 18)
                .addComponent(preis)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kaufen)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>

    private void kaufenActionPerformed(java.awt.event.ActionEvent evt) {
      if(angebot.getSelectedIndex()<0)return;
      welt.items.Item item=(welt.items.Item)angebot.getSelectedValue();
      int münzen=Javamon.getSpieler().getMünzen();
      if(münzen>=item.getWert()){
        münzen-=item.getWert();
        Javamon.getSpieler().addMünzen(-item.getWert());
        Javamon.getSpieler().addItem(item);
        dispose();
        Javamon.sout(Javamon.getSpieler()+" erhält "+item);
      }else{
          Javamon.sout("Du hast nicht genug Münzen.");
      }
    }

    private void angebotValueChanged(javax.swing.event.ListSelectionEvent evt) {
      welt.items.Item item=(welt.items.Item)angebot.getSelectedValue();
      if(item!=null){
        preis.setText("Preis: "+item.getWert());
        angebot.setToolTipText(item.getToolTipText());
      }
    }

    // Variables declaration - do not modify
    private javax.swing.JList angebot;
    private javax.swing.JLabel geld;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton kaufen;
    private javax.swing.JLabel preis;
    // End of variables declaration

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(Javamon.isPrinting())Javamon.printNext();
        else if(e.getKeyCode()==Steuerung.getB())dispose();
        else if(this.hasFocus()) kaufen.requestFocus();
        else if(kaufen.hasFocus()){
            if(e.getKeyCode()==Steuerung.getRight())angebot.requestFocus();
        }else if(angebot.hasFocus()){
            int key=e.getKeyCode();
            if(key==Steuerung.getLeft())kaufen.requestFocus();
            else if(key==Steuerung.getA())kaufen.doClick();
            else if(key==Steuerung.getUp()){
                if(angebot.getSelectedIndex()<=0)return;
                angebot.setSelectedIndex(angebot.getSelectedIndex()-1);
            }else if(key==Steuerung.getDown()){
                angebot.setSelectedIndex(angebot.getSelectedIndex()+1);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}