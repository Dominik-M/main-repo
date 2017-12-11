package spiel;

import attacken.AttackenEffekt;
import attacken.AttackenEnum;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import tools.OrtEditor;
import tools.PokedexGUI;

public class Start extends javax.swing.JFrame {

    /**
     * Creates new form Start
     */
    public Start() {
        super("Javamon");
        initComponents();
        try{
          java.io.File datei=new java.io.File("spiel.dat");
          java.io.FileInputStream fis=new java.io.FileInputStream(datei);
          java.io.ObjectInputStream ois=new java.io.ObjectInputStream(fis);
          Spielwelt spiel=(Spielwelt)ois.readObject();
          alt.setText(spiel.getSpieler().name+" Geld: "+spiel.getSpieler().getGeld()+" Pokemon: "+spiel.getSpieler().pokedex);
        }catch(Exception ex){}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        neu = new javax.swing.JButton();
        laden = new javax.swing.JButton();
        name = new javax.swing.JLabel();
        nameIn = new javax.swing.JTextField();
        starterl = new javax.swing.JLabel();
        starter = new javax.swing.JComboBox();
        alt = new javax.swing.JLabel();
        editor = new javax.swing.JButton();
        pokedex = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        neu.setText("Neues Spiel");
        neu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                neuActionPerformed(evt);
            }
        });

        laden.setText("Spiel laden");
        laden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ladenActionPerformed(evt);
            }
        });

        name.setText("Name:");
        name.setEnabled(false);

        nameIn.setText("Peter");
        nameIn.setEnabled(false);
        nameIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameInActionPerformed(evt);
            }
        });

        starterl.setText("Starter: ");
        starterl.setEnabled(false);

        starter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pikachu", "Schiggy", "Bisasam", "Glumanda" }));
        starter.setEnabled(false);

        alt.setText("kein Spielstand vorhanden");

        editor.setText("Ort Editor");
        editor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editorActionPerformed(evt);
            }
        });

        pokedex.setText("Pokedex");
        pokedex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pokedexActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(neu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(name)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(starterl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(starter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(laden)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(alt))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(editor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pokedex)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(neu)
                    .addComponent(name)
                    .addComponent(nameIn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(starterl)
                    .addComponent(starter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(laden)
                    .addComponent(alt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editor)
                    .addComponent(pokedex))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void neuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_neuActionPerformed
      if(name.isEnabled())nameInActionPerformed(evt);
      else{
        name.setEnabled(true);
        nameIn.setEnabled(true);
        starterl.setEnabled(true);
        starter.setEnabled(true);
      }
    }//GEN-LAST:event_neuActionPerformed

    private void ladenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ladenActionPerformed
      try{Javamon.load();}
      catch(Exception ex){
        return;
      }
      dispose();
    }//GEN-LAST:event_ladenActionPerformed

    private void nameInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameInActionPerformed
      pokemon.Pokemon pok;
      if(starter.getSelectedIndex()==0) pok=new pokemon.Pikachu(5);
      else if(starter.getSelectedIndex()==1) pok=new pokemon.Schiggy(5);
      else if(starter.getSelectedIndex()==2) pok=new pokemon.Bisasam(5);
      else pok=new pokemon.Glumanda(5);
      Javamon.neustart(nameIn.getText(),pok);
      dispose();
    }//GEN-LAST:event_nameInActionPerformed

    private void editorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editorActionPerformed
        new OrtEditor().setVisible(true);
        dispose();
    }//GEN-LAST:event_editorActionPerformed

    private void pokedexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pokedexActionPerformed
        PokedexGUI pokedexGUI = new PokedexGUI();
        pokedexGUI.setModal(true);
        pokedexGUI.setVisible(true);
    }//GEN-LAST:event_pokedexActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
//        pokemon.Pokemon pok=new pokemon.Pikachu(5);
//        System.out.println(pok+" "+pok.getWerteSumme()+" "+pok.getBenEP());
//        pok=new pokemon.Glumanda(5);
//        System.out.println(pok+" "+pok.getWerteSumme()+" "+pok.getBenEP());
//        pok=new pokemon.Bisasam(5);
//        System.out.println(pok+" "+pok.getWerteSumme()+" "+pok.getBenEP());
//        pok=new pokemon.Schiggy(5);
//        System.out.println(pok+" "+pok.getWerteSumme()+" "+pok.getBenEP());
      if(Javamon.MAKE_FILES){
            try {
                PrintWriter writer=new PrintWriter(new java.io.File("defs.txt"));
                writer.println("Typen:");
                Typ.printAll(writer);
                writer.println("Fähigkeiten:");
                Fähigkeit.printAll(writer);
                writer.println("Wesen:");
                Wesen.printAll(writer);
                writer.println("Attacken:");
                AttackenEnum.printAll(writer);
                writer.println("Attacken Effekte:");
                AttackenEffekt.printAll(writer);
                writer.close();
                System.out.println("Dateien wurden geschrieben.");
            } catch (IOException ex) {
                Logger.getLogger(Start.class.getName()).log(Level.SEVERE, null, ex);
            }
      }
      new Start().setVisible(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel alt;
    private javax.swing.JButton editor;
    private javax.swing.JButton laden;
    private javax.swing.JLabel name;
    private javax.swing.JTextField nameIn;
    private javax.swing.JButton neu;
    private javax.swing.JButton pokedex;
    private javax.swing.JComboBox starter;
    private javax.swing.JLabel starterl;
    // End of variables declaration//GEN-END:variables
}