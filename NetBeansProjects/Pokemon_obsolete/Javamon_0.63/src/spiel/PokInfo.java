package spiel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PokInfo extends javax.swing.JDialog implements KeyListener{
    private pokemon.Pokemon dieses;
    
    /**
     * Creates new form PokInfo
     */
    public PokInfo(pokemon.Pokemon pok) {
        setTitle(pok.toString());
        setModal(true);
        setLocation(Javamon.getLoc());
        initComponents();
        dieses=pok;
        this.addKeyListener(this);
        paintLabels();
    }
    
    public final void paintLabels(){
      int prozent=(int)(dieses.getEP()*100.0/dieses.getBenEP());
        epBar.setValue(prozent);
        epBar.setString(prozent+"%");
        prozent=(int)(dieses.getKP()*100.0/dieses.getMaxKP());
        kpBar.setValue(prozent);
        kpBar.setString(dieses.getKP()+"/"+dieses.getMaxKP());
        level.setText("Lvl: "+dieses.getLevel());
        pokName.setText(dieses.toString());
        originName.setText(dieses.getOriginName());
        status.setText(dieses.getStatus().name());
        bild.setIcon(dieses.getIcon());
        wesen.setText("Wesen: "+dieses.getWesen());
        wesen.setToolTipText(dieses.getWesen().tooltip);
        if(dieses.getItem()==null){
          item.setText("Item: Keins");
          nehmen.setEnabled(false);
        }
        else{
          item.setText("Item: "+dieses.getItem());
          item.setToolTipText(dieses.getItem().getToolTipText());
          nehmen.setEnabled(true);
        }
        if(dieses.getFähigkeit()==null)skill.setText("Fähigkeit: Keine");
        else{
          skill.setText("Fähigkeit: "+dieses.getFähigkeit());
          skill.setToolTipText(dieses.getFähigkeit().tooltip);
        }
        attacken.Attacke[] att=dieses.getAttacken();
        if(att[0]!=null){
          String tooltip="Dmg: "+att[0].getDmg()+" Gen: "+att[0].getGena()+
                  " Typ: "+att[0].getTyp().name()+", ";
          if(att[0].isPhysisch())tooltip=tooltip+"physisch. ";
          else tooltip=tooltip+"spezial. ";
          att1.setText(att[0].toString()+" "+att[0].getAP()+"/"+att[0].getMaxAP());
          att1.setToolTipText(tooltip+att[0].getToolTipText());
        }
        if(att[1]!=null){
          String tooltip="Dmg: "+att[1].getDmg()+" Gen: "+att[1].getGena()+
                  " Typ: "+att[1].getTyp().name()+", ";
          if(att[1].isPhysisch())tooltip=tooltip+"physisch. ";
          else tooltip=tooltip+"spezial. ";
          att2.setText(att[1].toString()+" "+att[1].getAP()+"/"+att[1].getMaxAP());
          att2.setToolTipText(tooltip+att[1].getToolTipText());
        }
        if(att[2]!=null){
          String tooltip="Dmg: "+att[2].getDmg()+" Gen: "+att[2].getGena()+
                  " Typ: "+att[2].getTyp().name()+", ";
          if(att[2].isPhysisch())tooltip=tooltip+"physisch. ";
          else tooltip=tooltip+"spezial. ";
          att3.setText(att[2].toString()+" "+att[2].getAP()+"/"+att[2].getMaxAP());
          att3.setToolTipText(tooltip+att[2].getToolTipText());
        }
        if(att[3]!=null){
          String tooltip="Dmg: "+att[3].getDmg()+" Gen: "+att[3].getGena()+
                  " Typ: "+att[3].getTyp().name()+", ";
          if(att[3].isPhysisch())tooltip=tooltip+"physisch. ";
          else tooltip=tooltip+"spezial. ";
          att4.setText(att[3].toString()+" "+att[3].getAP()+"/"+att[3].getMaxAP());
          att4.setToolTipText(tooltip+att[3].getToolTipText());
        }
        ep.setText("EP: "+dieses.getEP()+"/"+dieses.getBenEP());
        typ1.setText(dieses.getTyp1().name()+" /");
        typ2.setText(dieses.getTyp2().name());
        typ1.setForeground(Javamon.TYPEN[dieses.getTyp1().index]);
        typ2.setForeground(Javamon.TYPEN[dieses.getTyp2().index]);
        atk.setText("Angriff:                       "+dieses.getAtk());
        atk.setToolTipText("Angriffskraft: Beeinflusst die Stärke von physichen Attacken");
        vert.setText("Verteidigung:            "+dieses.getVert());
        vert.setToolTipText("Verteidigung: Verringert erlittenen Schaden durch physische Attacken");
        spezatk.setText("Spezialangriff:           "+dieses.getSpezAtk());
        spezatk.setToolTipText("Spezial-Angriffskraft: Beeinflusst die Stärke von nicht-physichen Attacken");
        spezvert.setText("Spezialverteidigung: "+dieses.getSpezVert());
        spezvert.setToolTipText("Spezial-Verteidigung: Verringert erlittenen Schaden durch nicht-physische Attacken");
        init.setText("Initiative:                    "+dieses.getInit());
        init.setToolTipText("Initiative/Beweglichkeit: Beeinflusst Genauigkeit,Volltrefferquote, Ausweichvermögen und Erstschlagchance");
        sum.setText("Wertesumme:         "+dieses.getWerteSumme());
        bonus.setText("Bonus Punkte: "+dieses.getEV());
        if(dieses.getEV()<=0){
          kpplus.setEnabled(false);
          atkplus.setEnabled(false);
          vertplus.setEnabled(false);
          sAtkplus.setEnabled(false);
          sVertplus.setEnabled(false);
          initplus.setEnabled(false);
        }
        att=dieses.getBekannte();
        if(att.length>0){
          String[] attnamen=new String[att.length];
          for(int i=0;i<att.length;i++)attnamen[i]=att[i].toString();
          bekannte.setModel(new javax.swing.DefaultComboBoxModel(attnamen));
          bekannte.setEnabled(true);
        }
        pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pokName = new javax.swing.JLabel();
        bild = new javax.swing.JLabel();
        epBar = new javax.swing.JProgressBar();
        kpBar = new javax.swing.JProgressBar();
        status = new javax.swing.JLabel();
        level = new javax.swing.JLabel();
        ep = new javax.swing.JLabel();
        atk = new javax.swing.JLabel();
        vert = new javax.swing.JLabel();
        spezatk = new javax.swing.JLabel();
        spezvert = new javax.swing.JLabel();
        init = new javax.swing.JLabel();
        att1 = new javax.swing.JLabel();
        att2 = new javax.swing.JLabel();
        att3 = new javax.swing.JLabel();
        att4 = new javax.swing.JLabel();
        typ = new javax.swing.JLabel();
        typ1 = new javax.swing.JLabel();
        typ2 = new javax.swing.JLabel();
        bekannte = new javax.swing.JComboBox();
        sum = new javax.swing.JLabel();
        bonus = new javax.swing.JLabel();
        atkplus = new javax.swing.JButton();
        vertplus = new javax.swing.JButton();
        sAtkplus = new javax.swing.JButton();
        sVertplus = new javax.swing.JButton();
        initplus = new javax.swing.JButton();
        kpplus = new javax.swing.JButton();
        originName = new javax.swing.JLabel();
        wesen = new javax.swing.JLabel();
        skill = new javax.swing.JLabel();
        item = new javax.swing.JLabel();
        nehmen = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pokName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pokName.setText("Pokemon");

        bild.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/img/abstract.png"))); // NOI18N

        epBar.setValue(50);
        epBar.setStringPainted(true);

        kpBar.setBackground(new java.awt.Color(204, 0, 0));
        kpBar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kpBar.setForeground(new java.awt.Color(51, 204, 0));
        kpBar.setValue(50);
        kpBar.setStringPainted(true);

        status.setText("status");

        level.setText("level");

        ep.setText("ep von benEp");

        atk.setText("Angriff:                     ");

        vert.setText("Verteidigung:           ");

        spezatk.setText("Spezialangriff:          ");

        spezvert.setText("Spezialverteidigung: ");

        init.setText("Initiative:                  ");

        att1.setText("-------------");

        att2.setText("-------------");

        att3.setText("-------------");

        att4.setText("-------------");

        typ.setText("Typ: ");

        typ1.setForeground(new java.awt.Color(204, 0, 0));
        typ1.setText("Typ1 /");

        typ2.setForeground(new java.awt.Color(255, 0, 0));
        typ2.setText("Typ2");

        bekannte.setEnabled(false);
        bekannte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bekannteActionPerformed(evt);
            }
        });

        sum.setText("Wertesumme:         ");

        bonus.setText("Bonus Punkte: ");

        atkplus.setText("ATK+");
        atkplus.setFocusPainted(false);
        atkplus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atkplusActionPerformed(evt);
            }
        });

        vertplus.setText("VER+");
        vertplus.setFocusPainted(false);
        vertplus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vertplusActionPerformed(evt);
            }
        });

        sAtkplus.setText("SpA+");
        sAtkplus.setFocusPainted(false);
        sAtkplus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sAtkplusActionPerformed(evt);
            }
        });

        sVertplus.setText("SpV+");
        sVertplus.setFocusPainted(false);
        sVertplus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sVertplusActionPerformed(evt);
            }
        });

        initplus.setText("INI+");
        initplus.setFocusPainted(false);
        initplus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initplusActionPerformed(evt);
            }
        });

        kpplus.setText("KP +");
        kpplus.setFocusPainted(false);
        kpplus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kpplusActionPerformed(evt);
            }
        });

        originName.setText("Pokemon");

        wesen.setText("Wesen: Mild");

        skill.setText("Fähigkeit: Adlerauge");

        item.setText("Item: Keins");

        nehmen.setText("nehmen");
        nehmen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nehmenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bild)
                    .addComponent(att1)
                    .addComponent(att2)
                    .addComponent(att3)
                    .addComponent(att4)
                    .addComponent(bekannte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nehmen)
                    .addComponent(item))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(atk)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(atkplus))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(vert)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(vertplus))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(spezatk)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sAtkplus))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(spezvert)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sVertplus))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(init)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(initplus, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(typ)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(typ1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(typ2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(kpplus, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(sum)
                            .addGap(18, 18, 18)
                            .addComponent(bonus)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pokName, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(level, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(originName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(skill)
                                    .addComponent(wesen))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(ep))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(kpBar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(epBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(status)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(kpBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(status))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pokName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(originName)
                                    .addComponent(wesen))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(epBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(level)
                                .addComponent(skill)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ep)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(typ)
                            .addComponent(typ1)
                            .addComponent(typ2)
                            .addComponent(kpplus, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(bild))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(atk)
                            .addComponent(atkplus, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(vert)
                            .addComponent(vertplus, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spezatk)
                            .addComponent(sAtkplus, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spezvert)
                            .addComponent(sVertplus, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(init)
                            .addComponent(initplus, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sum)
                            .addComponent(bonus)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(item)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nehmen, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(att1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(att2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(att3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(att4))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(bekannte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bekannteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bekannteActionPerformed
      new Verlernen(dieses.getBekannte()[bekannte.getSelectedIndex()],dieses);
      paintLabels();
    }//GEN-LAST:event_bekannteActionPerformed

    private void atkplusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atkplusActionPerformed
      dieses.verteilEV(Javamon.ANGR);
      paintLabels();
    }//GEN-LAST:event_atkplusActionPerformed

    private void vertplusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vertplusActionPerformed
      dieses.verteilEV(Javamon.VERT);
      paintLabels();
    }//GEN-LAST:event_vertplusActionPerformed

    private void sAtkplusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sAtkplusActionPerformed
      dieses.verteilEV(Javamon.SPEZANGR);
      paintLabels();
    }//GEN-LAST:event_sAtkplusActionPerformed

    private void sVertplusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sVertplusActionPerformed
      dieses.verteilEV(Javamon.SPEZVERT);
      paintLabels();
    }//GEN-LAST:event_sVertplusActionPerformed

    private void initplusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initplusActionPerformed
      dieses.verteilEV(Javamon.INIT);
      paintLabels();
    }//GEN-LAST:event_initplusActionPerformed

    private void kpplusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kpplusActionPerformed
      dieses.verteilEV(Javamon.KP);
      paintLabels();
    }//GEN-LAST:event_kpplusActionPerformed

    private void nehmenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nehmenActionPerformed
      Javamon.getSpieler().addItem(dieses.nimmItem());
      paintLabels();
    }//GEN-LAST:event_nehmenActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void start(pokemon.Pokemon pok) {
        /*
         * Create and display the form
         */
        new PokInfo(pok).setVisible(true);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel atk;
    private javax.swing.JButton atkplus;
    private javax.swing.JLabel att1;
    private javax.swing.JLabel att2;
    private javax.swing.JLabel att3;
    private javax.swing.JLabel att4;
    private javax.swing.JComboBox bekannte;
    private javax.swing.JLabel bild;
    private javax.swing.JLabel bonus;
    private javax.swing.JLabel ep;
    private javax.swing.JProgressBar epBar;
    private javax.swing.JLabel init;
    private javax.swing.JButton initplus;
    private javax.swing.JLabel item;
    private javax.swing.JProgressBar kpBar;
    private javax.swing.JButton kpplus;
    private javax.swing.JLabel level;
    private javax.swing.JButton nehmen;
    private javax.swing.JLabel originName;
    private javax.swing.JLabel pokName;
    private javax.swing.JButton sAtkplus;
    private javax.swing.JButton sVertplus;
    private javax.swing.JLabel skill;
    private javax.swing.JLabel spezatk;
    private javax.swing.JLabel spezvert;
    private javax.swing.JLabel status;
    private javax.swing.JLabel sum;
    private javax.swing.JLabel typ;
    private javax.swing.JLabel typ1;
    private javax.swing.JLabel typ2;
    private javax.swing.JLabel vert;
    private javax.swing.JButton vertplus;
    private javax.swing.JLabel wesen;
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==Steuerung.getB()){
            dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
