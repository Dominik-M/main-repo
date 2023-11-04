/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tonfolge;

import javax.swing.JFileChooser;

/**
 *
 * @author Dundun
 */
public class TonGUI extends javax.swing.JFrame implements TonListener
{

    private Tonfolge tf = new Tonfolge();

    /**
     * Creates new form TonGUI
     */
    public TonGUI()
    {
        initComponents();
        tasten2.addTastenListener(noten);
        noten.addKeyListener(tasten2.KEY_HANDLER);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        start = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        instr = new javax.swing.JComboBox();
        bpm = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        noten = new tonfolge.Notenblatt();
        clear = new javax.swing.JButton();
        save = new javax.swing.JButton();
        load = new javax.swing.JButton();
        repeat = new javax.swing.JCheckBox();
        tasten2 = new tonfolge.Tasten();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu6 = new javax.swing.JMenu();
        neuTakt = new javax.swing.JMenuItem();
        zufall = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        start.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tonfolge/icons/play.jpg"))); // NOI18N
        start.setText("Abspielen");
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });

        jLabel1.setText("Instrument:");

        instr.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Klavier1", "Klavier2", "Klavier3", "Xylophon1", "E-Piano1", "Klavier4", "E-Piano2", "Xylophon2", "Xylophon3", "Xylophon4", "Xylophon5", "Xylophon6", "Xylophon7", "Glocken1", "Klavier5", "Orgel1", "Orgel2", "Orgel3", "Orgel4", "Flöte1", "Mandoline1", "Mandoline2", "Mandoline3", "E-Piano3", "E-Piano4", "E-Piano5", "Citar1", "E-Piano6", "Melodika1", "Melodika2", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70" }));

        bpm.setModel(new javax.swing.SpinnerNumberModel(50, 1, 300, 1));

        jLabel2.setText("bpm");

        javax.swing.GroupLayout notenLayout = new javax.swing.GroupLayout(noten);
        noten.setLayout(notenLayout);
        notenLayout.setHorizontalGroup(
            notenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 647, Short.MAX_VALUE)
        );
        notenLayout.setVerticalGroup(
            notenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(noten);

        clear.setText("Löschen");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });

        save.setText("Speichern");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveActionPerformed(evt);
            }
        });

        load.setText("Laden");
        load.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadActionPerformed(evt);
            }
        });

        repeat.setText("repeat");
        repeat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repeatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tasten2Layout = new javax.swing.GroupLayout(tasten2);
        tasten2.setLayout(tasten2Layout);
        tasten2Layout.setHorizontalGroup(
            tasten2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 666, Short.MAX_VALUE)
        );
        tasten2Layout.setVerticalGroup(
            tasten2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 61, Short.MAX_VALUE)
        );

        jMenu6.setText("Optionen");

        neuTakt.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PLUS, java.awt.event.InputEvent.CTRL_MASK));
        neuTakt.setText("Takt hinzufügen");
        neuTakt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                neuTaktActionPerformed(evt);
            }
        });
        jMenu6.add(neuTakt);

        zufall.setText("zufällige Noten");
        zufall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zufallActionPerformed(evt);
            }
        });
        jMenu6.add(zufall);

        jMenuBar3.add(jMenu6);

        setJMenuBar(jMenuBar3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bpm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(instr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(start)
                .addGap(18, 18, 18)
                .addComponent(clear)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(save)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(load)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(repeat)
                .addContainerGap(30, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
            .addComponent(tasten2, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bpm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(start)
                    .addComponent(instr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(clear)
                    .addComponent(save)
                    .addComponent(load)
                    .addComponent(repeat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tasten2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startActionPerformed
        if (tf.nochTöne())
        {
            tf.stop();
        }
        else
        {
            tf = new Tonfolge();
            tf.addTonListener(this);
            tf.setInstrument(instr.getSelectedIndex() + 1);
            noten.füllTonfolge(tf);
            tf.endnote();
            tf.start((int) bpm.getValue());
            start.setText("    Stop      ");
            start.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tonfolge/icons/stop.jpg")));
        }
    }//GEN-LAST:event_startActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        if (javax.swing.JOptionPane.showConfirmDialog(this, "Wirklich alle Noten löschen?", "Löschen",
                javax.swing.JOptionPane.YES_NO_OPTION) == javax.swing.JOptionPane.YES_OPTION)
        {
            noten.clear();
            noten.repaint();
        }
    }//GEN-LAST:event_clearActionPerformed

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveActionPerformed
        JFileChooser jFC = new javax.swing.JFileChooser();
        if (jFC.showSaveDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION)
        {
            noten.save(jFC.getSelectedFile());
        }
    }//GEN-LAST:event_saveActionPerformed

    private void loadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadActionPerformed
        if (noten.wurdeGeändert() && javax.swing.JOptionPane.showConfirmDialog(
                this, "Es wurden Änderungen vorgenommen.\n Speichern?", "Warnung", javax.swing.JOptionPane.YES_NO_OPTION)
                == javax.swing.JOptionPane.YES_OPTION)
        {
            saveActionPerformed(evt);
        }
        else
        {
            JFileChooser jFC = new javax.swing.JFileChooser();
            if (jFC.showOpenDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION)
            {
                noten.lade(jFC.getSelectedFile());
                setTitle(jFC.getSelectedFile().getName());
            }
        }
    }//GEN-LAST:event_loadActionPerformed

    private void neuTaktActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_neuTaktActionPerformed
        noten.addTakt();
    }//GEN-LAST:event_neuTaktActionPerformed

    private void repeatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repeatActionPerformed
        //
    }//GEN-LAST:event_repeatActionPerformed

    private void zufallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zufallActionPerformed
        noten.zufallNoten(100);
    }//GEN-LAST:event_zufallActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(TonGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(TonGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(TonGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(TonGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new TonGUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner bpm;
    private javax.swing.JButton clear;
    private javax.swing.JComboBox instr;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton load;
    private javax.swing.JMenuItem neuTakt;
    private tonfolge.Notenblatt noten;
    private javax.swing.JCheckBox repeat;
    private javax.swing.JButton save;
    private javax.swing.JButton start;
    private tonfolge.Tasten tasten2;
    private javax.swing.JMenuItem zufall;
    // End of variables declaration//GEN-END:variables

    @Override
    public void ende()
    {
        if (repeat.isSelected())
        {
            tf.start((int) bpm.getValue());
            return;
        }
        start.setText("Abspielen");
        start.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tonfolge/icons/play.jpg")));
    }
}
