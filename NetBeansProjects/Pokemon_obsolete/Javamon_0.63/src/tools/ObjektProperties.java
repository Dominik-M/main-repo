package tools;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import spiel.Trainer;
import welt.items.ItemBox;
import welt.objekte.*;
import welt.orte.Ort;

/**
 *
 * @author Dundun
 */
public class ObjektProperties extends javax.swing.JDialog {

    private Objekt o;
    private Component[] controls;
    private Ort pos;
  
    public ObjektProperties(Objekt obj,Ort wo) {
        if(obj==null)throw new java.lang.IllegalArgumentException("Objekt darf nicht null sein");
        setModal(true);
        initComponents();
        o=obj;
        pos=wo;
        setTitle(o.toString());
        bild.setLayout(new java.awt.FlowLayout());
        bild.add(new ObjektPanel(o));
        edit.setLayout(new java.awt.FlowLayout());
        if(o.getClass()==Automat.class){
            initAutomatProperties((Automat)o);
        }else if(o.getClass()==Person.class || o.getClass().getSuperclass()==Person.class){
            initPersonProperties((Person)o);
        }else if(o.getClass()==Tür.class){
            initTürProperties((Tür)o);
        }
        if(o.getTrainer()!=null)edit.add(new JLabel("Trainerreferenz: "+o.getTrainer()));
        pack();
    }
    
    private void initAutomatProperties(Automat a){
        controls = new Component[1];
        o = a;
        JSpinner jSpinner = new JSpinner(new SpinnerNumberModel(100, 1, null, 1));
        jSpinner.setPreferredSize(new java.awt.Dimension(60, 28));
        controls[0] = jSpinner;
        edit.add(new javax.swing.JLabel("Delay:"));
        edit.add(controls[0]);
    }
    
    private void initPersonProperties(Person p) {
        controls = new Component[2];
        o=p;
        String text = "";
        String[] s = ((Person) o).getDialog();
        for (int i = 0; i < s.length; i++) {
            if (i == s.length - 1) {
                text = text + s[i];
            } else {
                text = text + s[i] + "\n";
            }
        }
        controls[0] = new JTextArea(text, 5, 20);
        controls[1] = new ItemBox(((Person) o).getItem());
        edit.add(new JLabel("Text:"));
        edit.add(controls[0]);
        javax.swing.JButton button = new JButton("Hautfarbe");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Person) o).setSkinColor(javax.swing.JColorChooser.showDialog(null, "Hautfarbe wählen", ((Person) o).getSkinColor()));
                repaint();
            }
        });
        edit.add(button);
        button = new JButton("Kleidung");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Person) o).setColor(JColorChooser.showDialog(null, "Kleidungsfarbefarbe wählen", ((Person) o).getSkinColor()));
                repaint();
            }
        });
        edit.add(button);
        final JComboBox richtungBox=new JComboBox(new DefaultComboBoxModel(new String[]{"Unten","Oben","Links","Rechts"}));
        richtungBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                switch(richtungBox.getSelectedIndex()){
                    case 0:
                        ((Person)o).setRichtung(Trainer.DOWN);
                        break;
                    case 1:
                        ((Person)o).setRichtung(Trainer.UP);
                        break;
                    case 2:
                        ((Person)o).setRichtung(Trainer.LEFT);
                        break;
                    case 3:
                        ((Person)o).setRichtung(Trainer.RIGHT);
                        break;
                }
                bild.repaint();
            }
        });
        edit.add(new JLabel("Blickrichtung:"));
        edit.add(richtungBox);
        edit.add(new JLabel("Item:"));
        edit.add(controls[1]);
        if(p.getClass()==TrainerObjekt.class)initTrainerProperties((TrainerObjekt)p);
    }
    
    private void initTrainerProperties(final TrainerObjekt t){
        Component[] merk=controls.clone();
        controls=new Component[controls.length+1];
        System.arraycopy(merk, 0, controls, 0, merk.length);
        // Pokemon Auswahl
        JButton button=new JButton("Pokemon");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PokedexGUI gui=new PokedexGUI(t.trainer().getPoks());
                gui.setModal(true);
                gui.setVisible(true);
                while(gui.isVisible());
                t.trainer().setPoks(gui.getPokSelection());
            }
        });
        // Sichweite Eingabe
        JTextField range=new JTextField("0");
        controls[controls.length-1]=range;
        edit.add(new JLabel("Sichtweite:"));
        edit.add(range);
    }
    
    private void initTürProperties(Tür t){
        controls = new Component[3];
        o=t;
        JTextField xy = new JTextField(t.getZielKoords().x + "", 3);
        controls[0] = xy;
        edit.add(new javax.swing.JLabel("Ziel X:"));
        edit.add(xy);
        xy = new JTextField(t.getZielKoords().y + "", 3);
        controls[1] = xy;
        edit.add(new JLabel("Ziel Y:"));
        edit.add(xy);
        Object[] nachbarn=new Object[pos.getNachbarn().length+1];
        System.arraycopy(pos.getNachbarn(),0,nachbarn,1,nachbarn.length-1);
        nachbarn[0]=t.getZielOrt();
        if(nachbarn[0]==null)nachbarn[0]="kein Ziel";
        else if(nachbarn[0].toString()==null)nachbarn[0]="unbenannt";
        JComboBox zielBox=new JComboBox(new DefaultComboBoxModel(nachbarn));
        zielBox.setSelectedIndex(0);
        controls[2]=zielBox;
        zielBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                JComboBox box=(JComboBox)controls[2];
                if(box.getSelectedIndex()>0){
                    Tür t=(Tür)o;
                    t.setZiel((Ort)box.getSelectedItem(),t.getZielKoords().x,t.getZielKoords().y);
                    initComboBoxModel();
                }
            }
        });
        edit.add(zielBox);
        // Read Ort from File
        javax.swing.JButton button = new JButton("Neues Ziel wählen");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.JFileChooser jFC = new JFileChooser();
                if (jFC.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    FileInputStream fis;
                    welt.orte.Ort o;
                    try {
                        fis = new FileInputStream(jFC.getSelectedFile());
                        try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                            o = (welt.orte.Ort) ois.readObject();
                            fis.close();
                            pos.addNachbarOrt(o);
                            initComboBoxModel();
                        }
                    } catch (java.io.IOException | ClassNotFoundException ex) {
                        System.err.println(ex);
                    }
                }
            }
        });
        edit.add(button);
        // Nachbarort bearbeiten
        button = new javax.swing.JButton("Bearbeiten");
        button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setModal(false);
                setVisible(false);
                setVisible(true);
                OrtEditor.showOrtEditorDialog(((Tür)o).getZielOrt());
                // setModal(true) ??
                initComboBoxModel();
            }
        });
        edit.add(button);
        button = new javax.swing.JButton("Entfernen");
        button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox box=(JComboBox)controls[2];
                if(box.getSelectedIndex()==0){
                    Tür t=(Tür)o;
                    if(t.getZielOrt()!=null){
                        if(JOptionPane.showConfirmDialog(rootPane,
                                  t.getZielOrt()+" wirklich entfernen?",
                                  "Nachbarort entfernen",
                                  JOptionPane.YES_NO_OPTION)
                              ==JOptionPane.YES_OPTION){
                          pos.removeNachbarOrt(t.getZielOrt());
                          t.setZiel(null,t.getZielKoords().x,t.getZielKoords().y);
                        }
                    }
                }else {
                    pos.removeNachbarOrt((Ort)box.getSelectedItem());
                }
                initComboBoxModel();
            }
        });
        edit.add(button);
    }
    
    private void initComboBoxModel(){
        JComboBox box=(JComboBox)controls[2];
        Object[] nachbarn=new Object[pos.getNachbarn().length+1];
        System.arraycopy(pos.getNachbarn(),0,nachbarn,1,nachbarn.length-1);
        nachbarn[0]=((Tür)o).getZielOrt();
        if(nachbarn[0]==null)nachbarn[0]="kein Ziel";
        else if(nachbarn[0].toString()==null)nachbarn[0]="unbenannt";
        box.setModel(new DefaultComboBoxModel(nachbarn));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bild = new javax.swing.JPanel();
        apply = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        edit = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        bild.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout bildLayout = new javax.swing.GroupLayout(bild);
        bild.setLayout(bildLayout);
        bildLayout.setHorizontalGroup(
            bildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        bildLayout.setVerticalGroup(
            bildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        apply.setText("Apply");
        apply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyActionPerformed(evt);
            }
        });

        cancel.setText("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        edit.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout editLayout = new javax.swing.GroupLayout(edit);
        edit.setLayout(editLayout);
        editLayout.setHorizontalGroup(
            editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        editLayout.setVerticalGroup(
            editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(edit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(apply)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bild, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(edit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(apply)
                    .addComponent(cancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void applyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applyActionPerformed
        if(o.getClass()==Automat.class){
            ((Automat)o).setDelay((int)(((javax.swing.JSpinner)(controls[0])).getValue()));
        }else if(o.getClass()==Person.class || o.getClass().getSuperclass()==Person.class){
            String text=((javax.swing.JTextArea)controls[0]).getText();
            ((Person)o).setDialog(text.split("\n"));
            ((Person)o).setItem((welt.items.Item)((welt.items.ItemBox)controls[1]).getSelectedItem());
            if(o.getClass()==TrainerObjekt.class){
                try {
                    TrainerObjekt t=(TrainerObjekt)o;
                    t.setRange(Integer.parseInt(((JTextField)controls[controls.length-1]).getText()));
                    pos.removeTrainerReferenz(t);
                    pos.addTrainerReferenz(t);
                } catch (InstantiationException ex) {
                    Logger.getLogger(ObjektProperties.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(ObjektProperties.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if(o.getClass()==Tür.class){
            int x=Integer.parseInt(((JTextField)controls[0]).getText());
            int y=Integer.parseInt(((JTextField)controls[1]).getText());
        }
        dispose();
    }//GEN-LAST:event_applyActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
        dispose();
    }//GEN-LAST:event_cancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton apply;
    private javax.swing.JPanel bild;
    private javax.swing.JButton cancel;
    private javax.swing.JPanel edit;
    // End of variables declaration//GEN-END:variables
}

class ObjektPanel extends JPanel{
    private Objekt o;
    
    ObjektPanel(Objekt obj){
        o=obj;
        setPreferredSize(new java.awt.Dimension(spiel.Spielwelt.SPOT*4,spiel.Spielwelt.SPOT*4));
    }
    
    @Override
    public void paintComponent(Graphics g){
        o.paintComponent(g,spiel.Spielwelt.SPOT*4);
    }
}