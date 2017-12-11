package spiel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Inventar extends javax.swing.JPanel implements ActionListener,KeyListener{
    private Spieler besitzer;
    private boolean kampf,flag;
    private int t1;
    
    public Inventar(boolean imKampf){
        setLocation(Javamon.getLoc());
        besitzer=Javamon.getSpieler();
        kampf=imKampf;
        flag=false;
        initComponents();
        detail1.addActionListener(this);
        detail2.addActionListener(this);
        detail3.addActionListener(this);
        detail4.addActionListener(this);
        detail5.addActionListener(this);
        detail6.addActionListener(this);
        tausch1.addActionListener(this);
        tausch2.addActionListener(this);
        tausch3.addActionListener(this);
        tausch4.addActionListener(this);
        tausch5.addActionListener(this);
        tausch6.addActionListener(this);
        frei1.addActionListener(this);
        frei2.addActionListener(this);
        frei3.addActionListener(this);
        frei4.addActionListener(this);
        frei5.addActionListener(this);
        frei6.addActionListener(this);
        detail1.addKeyListener(this);
        detail2.addKeyListener(this);
        detail3.addKeyListener(this);
        detail4.addKeyListener(this);
        detail5.addKeyListener(this);
        detail6.addKeyListener(this);
        tausch1.addKeyListener(this);
        tausch2.addKeyListener(this);
        tausch3.addKeyListener(this);
        tausch4.addKeyListener(this);
        tausch5.addKeyListener(this);
        tausch6.addKeyListener(this);
        frei1.addKeyListener(this);
        frei2.addKeyListener(this);
        frei3.addKeyListener(this);
        frei4.addKeyListener(this);
        frei5.addKeyListener(this);
        frei6.addKeyListener(this);
        items.addKeyListener(this);
        this.addKeyListener(this);
        paintLabels();
        setVisible(true);
    }
    
    public void paintLabels(){
      sname.setText(besitzer.toString());
      geld.setText("Geld: "+besitzer.getGeld());
      items.setModel(new javax.swing.AbstractListModel() {
            welt.items.Item[] i = besitzer.getitems();
            public int getSize() { return i.length; }
            public Object getElementAt(int j) { return i[j]; }
        });
      pokemon.Pokemon pok=besitzer.pokemon[0];
      if(pok!=null){
        int prozent=(int)(pok.getEP()*100.0/pok.getBenEP());
        epBar1.setValue(prozent);
        epBar1.setString(prozent+"%");
        prozent=(int)(pok.getKP()*100.0/pok.getMaxKP());
        kpBar1.setValue(prozent);
        kpBar1.setString(pok.getKP()+"/"+pok.getMaxKP());
        level1.setText("Lvl: "+pok.getLevel());
        pokName1.setText(pok.toString());
        status1.setText(pok.getStatus().name());
        bild1.setIcon(pok.getIcon());
        epBar1.setEnabled(true);
        kpBar1.setEnabled(true);
        level1.setEnabled(true);
        pokName1.setEnabled(true);
        status1.setEnabled(true);
        bild1.setEnabled(true);
        detail1.setEnabled(true);
        tausch1.setEnabled(true);
        if(!kampf)frei1.setEnabled(true);
      }else{
        epBar1.setEnabled(false);
        kpBar1.setEnabled(false);
        level1.setEnabled(false);
        pokName1.setEnabled(false);
        status1.setEnabled(false);
        bild1.setEnabled(false);
        detail1.setEnabled(false);
        tausch1.setEnabled(false);
        frei1.setEnabled(false);
      }
      pok=besitzer.pokemon[1];
      if(pok!=null){
        int prozent=(int)(pok.getEP()*100.0/pok.getBenEP());
        epBar2.setValue(prozent);
        epBar2.setString(prozent+"%");
        prozent=(int)(pok.getKP()*100.0/pok.getMaxKP());
        kpBar2.setValue(prozent);
        kpBar2.setString(pok.getKP()+"/"+pok.getMaxKP());
        level2.setText("Lvl: "+pok.getLevel());
        pokName2.setText(pok.toString());
        status2.setText(pok.getStatus().name());
        bild2.setIcon(pok.getIcon());
        epBar2.setEnabled(true);
        kpBar2.setEnabled(true);
        level2.setEnabled(true);
        pokName2.setEnabled(true);
        status2.setEnabled(true);
        bild2.setEnabled(true);
        detail2.setEnabled(true);
        tausch2.setEnabled(true);
        if(!kampf)frei2.setEnabled(true);
      }else{
        epBar2.setEnabled(false);
        kpBar2.setEnabled(false);
        level2.setEnabled(false);
        pokName2.setEnabled(false);
        status2.setEnabled(false);
        bild2.setEnabled(false);
        detail2.setEnabled(false);
        tausch2.setEnabled(false);
        frei2.setEnabled(false);
      }
      pok=besitzer.pokemon[2];
      if(pok!=null){
        int prozent=(int)(pok.getEP()*100.0/pok.getBenEP());
        epBar3.setValue(prozent);
        epBar3.setString(prozent+"%");
        prozent=(int)(pok.getKP()*100.0/pok.getMaxKP());
        kpBar3.setValue(prozent);
        kpBar3.setString(pok.getKP()+"/"+pok.getMaxKP());
        level3.setText("Lvl: "+pok.getLevel());
        pokName3.setText(pok.toString());
        status3.setText(pok.getStatus().name());
        bild3.setIcon(pok.getIcon());
        epBar3.setEnabled(true);
        kpBar3.setEnabled(true);
        level3.setEnabled(true);
        pokName3.setEnabled(true);
        status3.setEnabled(true);
        bild3.setEnabled(true);
        detail3.setEnabled(true);
        tausch3.setEnabled(true);
        if(!kampf)frei3.setEnabled(true);
      }else{
        epBar3.setEnabled(false);
        kpBar3.setEnabled(false);
        level3.setEnabled(false);
        pokName3.setEnabled(false);
        status3.setEnabled(false);
        bild3.setEnabled(false);
        detail3.setEnabled(false);
        tausch3.setEnabled(false);
        frei3.setEnabled(false);
      }
      pok=besitzer.pokemon[3];
      if(pok!=null){
        int prozent=(int)(pok.getEP()*100.0/pok.getBenEP());
        epBar4.setValue(prozent);
        epBar4.setString(prozent+"%");
        prozent=(int)(pok.getKP()*100.0/pok.getMaxKP());
        kpBar4.setValue(prozent);
        kpBar4.setString(pok.getKP()+"/"+pok.getMaxKP());
        level4.setText("Lvl: "+pok.getLevel());
        pokName4.setText(pok.toString());
        status4.setText(pok.getStatus().name());
        bild4.setIcon(pok.getIcon());
        epBar4.setEnabled(true);
        kpBar4.setEnabled(true);
        level4.setEnabled(true);
        pokName4.setEnabled(true);
        status4.setEnabled(true);
        bild4.setEnabled(true);
        detail4.setEnabled(true);
        tausch4.setEnabled(true);
        if(!kampf)frei4.setEnabled(true);
      }else{
        epBar4.setEnabled(false);
        kpBar4.setEnabled(false);
        level4.setEnabled(false);
        pokName4.setEnabled(false);
        status4.setEnabled(false);
        bild4.setEnabled(false);
        detail4.setEnabled(false);
        tausch4.setEnabled(false);
        frei4.setEnabled(false);
      }
      pok=besitzer.pokemon[4];
      if(pok!=null){
        int prozent=(int)(pok.getEP()*100.0/pok.getBenEP());
        epBar5.setValue(prozent);
        epBar5.setString(prozent+"%");
        prozent=(int)(pok.getKP()*100.0/pok.getMaxKP());
        kpBar5.setValue(prozent);
        kpBar5.setString(pok.getKP()+"/"+pok.getMaxKP());
        level5.setText("Lvl: "+pok.getLevel());
        pokName5.setText(pok.toString());
        status5.setText(pok.getStatus().name());
        bild5.setIcon(pok.getIcon());
        epBar5.setEnabled(true);
        kpBar5.setEnabled(true);
        level5.setEnabled(true);
        pokName5.setEnabled(true);
        status5.setEnabled(true);
        bild5.setEnabled(true);
        detail5.setEnabled(true);
        tausch5.setEnabled(true);
        if(!kampf)frei5.setEnabled(true);
      }else{
        epBar5.setEnabled(false);
        kpBar5.setEnabled(false);
        level5.setEnabled(false);
        pokName5.setEnabled(false);
        status5.setEnabled(false);
        bild5.setEnabled(false);
        detail5.setEnabled(false);
        tausch5.setEnabled(false);
        frei5.setEnabled(false);
      }
      pok=besitzer.pokemon[5];
      if(pok!=null){
        int prozent=(int)(pok.getEP()*100.0/pok.getBenEP());
        epBar6.setValue(prozent);
        epBar6.setString(prozent+"%");
        prozent=(int)(pok.getKP()*100.0/pok.getMaxKP());
        kpBar6.setValue(prozent);
        kpBar6.setString(pok.getKP()+"/"+pok.getMaxKP());
        level6.setText("Lvl: "+pok.getLevel());
        pokName6.setText(pok.toString());
        status6.setText(pok.getStatus().name());
        bild6.setIcon(pok.getIcon());
        epBar6.setEnabled(true);
        kpBar6.setEnabled(true);
        level6.setEnabled(true);
        pokName6.setEnabled(true);
        status6.setEnabled(true);
        bild6.setEnabled(true);
        detail6.setEnabled(true);
        tausch6.setEnabled(true);
        if(!kampf)frei6.setEnabled(true);
      }else{
        epBar6.setEnabled(false);
        kpBar6.setEnabled(false);
        level6.setEnabled(false);
        pokName6.setEnabled(false);
        status6.setEnabled(false);
        bild6.setEnabled(false);
        detail6.setEnabled(false);
        tausch6.setEnabled(false);
        frei6.setEnabled(false);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        items = new javax.swing.JList();
        kpBar1 = new javax.swing.JProgressBar();
        level1 = new javax.swing.JLabel();
        epBar1 = new javax.swing.JProgressBar();
        pokName1 = new javax.swing.JLabel();
        status1 = new javax.swing.JLabel();
        bild1 = new javax.swing.JLabel();
        kpBar2 = new javax.swing.JProgressBar();
        level2 = new javax.swing.JLabel();
        epBar2 = new javax.swing.JProgressBar();
        pokName2 = new javax.swing.JLabel();
        status2 = new javax.swing.JLabel();
        bild2 = new javax.swing.JLabel();
        kpBar3 = new javax.swing.JProgressBar();
        level3 = new javax.swing.JLabel();
        epBar3 = new javax.swing.JProgressBar();
        pokName3 = new javax.swing.JLabel();
        status3 = new javax.swing.JLabel();
        bild3 = new javax.swing.JLabel();
        kpBar4 = new javax.swing.JProgressBar();
        level4 = new javax.swing.JLabel();
        epBar4 = new javax.swing.JProgressBar();
        pokName4 = new javax.swing.JLabel();
        status4 = new javax.swing.JLabel();
        bild4 = new javax.swing.JLabel();
        kpBar5 = new javax.swing.JProgressBar();
        level5 = new javax.swing.JLabel();
        epBar5 = new javax.swing.JProgressBar();
        pokName5 = new javax.swing.JLabel();
        status5 = new javax.swing.JLabel();
        bild5 = new javax.swing.JLabel();
        kpBar6 = new javax.swing.JProgressBar();
        level6 = new javax.swing.JLabel();
        epBar6 = new javax.swing.JProgressBar();
        pokName6 = new javax.swing.JLabel();
        status6 = new javax.swing.JLabel();
        bild6 = new javax.swing.JLabel();
        detail1 = new javax.swing.JButton();
        detail2 = new javax.swing.JButton();
        detail3 = new javax.swing.JButton();
        detail4 = new javax.swing.JButton();
        detail5 = new javax.swing.JButton();
        detail6 = new javax.swing.JButton();
        sname = new javax.swing.JLabel();
        geld = new javax.swing.JLabel();
        tausch1 = new javax.swing.JButton();
        tausch2 = new javax.swing.JButton();
        tausch3 = new javax.swing.JButton();
        tausch4 = new javax.swing.JButton();
        tausch5 = new javax.swing.JButton();
        tausch6 = new javax.swing.JButton();
        frei1 = new javax.swing.JButton();
        frei2 = new javax.swing.JButton();
        frei3 = new javax.swing.JButton();
        frei4 = new javax.swing.JButton();
        frei5 = new javax.swing.JButton();
        frei6 = new javax.swing.JButton();

        items.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(items);

        kpBar1.setBackground(new java.awt.Color(204, 0, 0));
        kpBar1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kpBar1.setForeground(new java.awt.Color(51, 204, 0));
        kpBar1.setValue(100);
        kpBar1.setEnabled(false);
        kpBar1.setStringPainted(true);

        level1.setText("level");
        level1.setEnabled(false);

        epBar1.setEnabled(false);
        epBar1.setStringPainted(true);

        pokName1.setText("Pokemon");
        pokName1.setEnabled(false);

        status1.setText("status");
        status1.setEnabled(false);

        bild1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/img/abstract.png"))); // NOI18N
        bild1.setEnabled(false);

        kpBar2.setBackground(new java.awt.Color(204, 0, 0));
        kpBar2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kpBar2.setForeground(new java.awt.Color(51, 204, 0));
        kpBar2.setValue(100);
        kpBar2.setEnabled(false);
        kpBar2.setStringPainted(true);

        level2.setText("level");
        level2.setEnabled(false);

        epBar2.setEnabled(false);
        epBar2.setStringPainted(true);

        pokName2.setText("Pokemon");
        pokName2.setEnabled(false);

        status2.setText("status");
        status2.setEnabled(false);

        bild2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/img/abstract.png"))); // NOI18N
        bild2.setEnabled(false);

        kpBar3.setBackground(new java.awt.Color(204, 0, 0));
        kpBar3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kpBar3.setForeground(new java.awt.Color(51, 204, 0));
        kpBar3.setValue(100);
        kpBar3.setEnabled(false);
        kpBar3.setStringPainted(true);

        level3.setText("level");
        level3.setEnabled(false);

        epBar3.setEnabled(false);
        epBar3.setStringPainted(true);

        pokName3.setText("Pokemon");
        pokName3.setEnabled(false);

        status3.setText("status");
        status3.setEnabled(false);

        bild3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/img/abstract.png"))); // NOI18N
        bild3.setEnabled(false);

        kpBar4.setBackground(new java.awt.Color(204, 0, 0));
        kpBar4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kpBar4.setForeground(new java.awt.Color(51, 204, 0));
        kpBar4.setValue(100);
        kpBar4.setEnabled(false);
        kpBar4.setStringPainted(true);

        level4.setText("level");
        level4.setEnabled(false);

        epBar4.setEnabled(false);
        epBar4.setStringPainted(true);

        pokName4.setText("Pokemon");
        pokName4.setEnabled(false);

        status4.setText("status");
        status4.setEnabled(false);

        bild4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/img/abstract.png"))); // NOI18N
        bild4.setEnabled(false);

        kpBar5.setBackground(new java.awt.Color(204, 0, 0));
        kpBar5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kpBar5.setForeground(new java.awt.Color(51, 204, 0));
        kpBar5.setValue(100);
        kpBar5.setEnabled(false);
        kpBar5.setStringPainted(true);

        level5.setText("level");
        level5.setEnabled(false);

        epBar5.setEnabled(false);
        epBar5.setStringPainted(true);

        pokName5.setText("Pokemon");
        pokName5.setEnabled(false);

        status5.setText("status");
        status5.setEnabled(false);

        bild5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/img/abstract.png"))); // NOI18N
        bild5.setEnabled(false);

        kpBar6.setBackground(new java.awt.Color(204, 0, 0));
        kpBar6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kpBar6.setForeground(new java.awt.Color(51, 204, 0));
        kpBar6.setValue(100);
        kpBar6.setEnabled(false);
        kpBar6.setStringPainted(true);

        level6.setText("level");
        level6.setEnabled(false);

        epBar6.setEnabled(false);
        epBar6.setStringPainted(true);

        pokName6.setText("Pokemon");
        pokName6.setEnabled(false);

        status6.setText("status");
        status6.setEnabled(false);

        bild6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/img/abstract.png"))); // NOI18N
        bild6.setEnabled(false);

        detail1.setText("Details");
        detail1.setEnabled(false);

        detail2.setText("Details");
        detail2.setEnabled(false);

        detail3.setText("Details");
        detail3.setEnabled(false);

        detail4.setText("Details");
        detail4.setEnabled(false);

        detail5.setText("Details");
        detail5.setEnabled(false);

        detail6.setText("Details");
        detail6.setEnabled(false);

        sname.setText("Spielername");

        geld.setText("Geld: ");

        tausch1.setText("Tausch");
        tausch1.setEnabled(false);

        tausch2.setText("Tausch");
        tausch2.setEnabled(false);

        tausch3.setText("Tausch");
        tausch3.setEnabled(false);

        tausch4.setText("Tausch");
        tausch4.setEnabled(false);

        tausch5.setText("Tausch");
        tausch5.setEnabled(false);

        tausch6.setText("Tausch");
        tausch6.setEnabled(false);

        frei1.setText("Freilassen");
        frei1.setEnabled(false);

        frei2.setText("Freilassen");
        frei2.setEnabled(false);

        frei3.setText("Freilassen");
        frei3.setEnabled(false);

        frei4.setText("Freilassen");
        frei4.setEnabled(false);

        frei5.setText("Freilassen");
        frei5.setEnabled(false);

        frei6.setText("Freilassen");
        frei6.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bild1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(level1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(pokName1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(kpBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(epBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(status1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(detail1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tausch1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(frei1))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bild2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(level2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(pokName2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(kpBar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(epBar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(status2))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(detail2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tausch2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(frei2))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bild4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(level4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(pokName4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(detail4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(kpBar4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(epBar4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(status4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tausch4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(frei4))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bild5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(level5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(pokName5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(kpBar5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(epBar5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(status5))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(detail5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tausch5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(frei5))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bild6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(level6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(pokName6, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(kpBar6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(epBar6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(status6))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(detail6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tausch6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(frei6)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sname)
                            .addComponent(geld)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bild3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(level3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(pokName3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(kpBar3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(epBar3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(status3))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(detail3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tausch3)
                                .addGap(18, 18, 18)
                                .addComponent(frei3)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(kpBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(epBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pokName1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(level1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(detail1)
                            .addComponent(tausch1)
                            .addComponent(frei1)))
                    .addComponent(bild1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(status1)
                            .addComponent(sname))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(geld)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kpBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(status2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(epBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pokName2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(level2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(detail2)
                            .addComponent(tausch2)
                            .addComponent(frei2)))
                    .addComponent(bild2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kpBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(status3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(epBar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pokName3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(level3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(detail3)
                            .addComponent(tausch3)
                            .addComponent(frei3)))
                    .addComponent(bild3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kpBar4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(status4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(epBar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pokName4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(level4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(detail4)
                            .addComponent(tausch4)
                            .addComponent(frei4)))
                    .addComponent(bild4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kpBar5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(status5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(epBar5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pokName5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(level5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(detail5)
                            .addComponent(tausch5)
                            .addComponent(frei5)))
                    .addComponent(bild5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kpBar6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(status6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(epBar6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pokName6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(level6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(detail6)
                            .addComponent(tausch6)
                            .addComponent(frei6)))
                    .addComponent(bild6)))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bild1;
    private javax.swing.JLabel bild2;
    private javax.swing.JLabel bild3;
    private javax.swing.JLabel bild4;
    private javax.swing.JLabel bild5;
    private javax.swing.JLabel bild6;
    private javax.swing.JButton detail1;
    private javax.swing.JButton detail2;
    private javax.swing.JButton detail3;
    private javax.swing.JButton detail4;
    private javax.swing.JButton detail5;
    private javax.swing.JButton detail6;
    private javax.swing.JProgressBar epBar1;
    private javax.swing.JProgressBar epBar2;
    private javax.swing.JProgressBar epBar3;
    private javax.swing.JProgressBar epBar4;
    private javax.swing.JProgressBar epBar5;
    private javax.swing.JProgressBar epBar6;
    private javax.swing.JButton frei1;
    private javax.swing.JButton frei2;
    private javax.swing.JButton frei3;
    private javax.swing.JButton frei4;
    private javax.swing.JButton frei5;
    private javax.swing.JButton frei6;
    private javax.swing.JLabel geld;
    private javax.swing.JList items;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JProgressBar kpBar1;
    private javax.swing.JProgressBar kpBar2;
    private javax.swing.JProgressBar kpBar3;
    private javax.swing.JProgressBar kpBar4;
    private javax.swing.JProgressBar kpBar5;
    private javax.swing.JProgressBar kpBar6;
    private javax.swing.JLabel level1;
    private javax.swing.JLabel level2;
    private javax.swing.JLabel level3;
    private javax.swing.JLabel level4;
    private javax.swing.JLabel level5;
    private javax.swing.JLabel level6;
    private javax.swing.JLabel pokName1;
    private javax.swing.JLabel pokName2;
    private javax.swing.JLabel pokName3;
    private javax.swing.JLabel pokName4;
    private javax.swing.JLabel pokName5;
    private javax.swing.JLabel pokName6;
    private javax.swing.JLabel sname;
    private javax.swing.JLabel status1;
    private javax.swing.JLabel status2;
    private javax.swing.JLabel status3;
    private javax.swing.JLabel status4;
    private javax.swing.JLabel status5;
    private javax.swing.JLabel status6;
    private javax.swing.JButton tausch1;
    private javax.swing.JButton tausch2;
    private javax.swing.JButton tausch3;
    private javax.swing.JButton tausch4;
    private javax.swing.JButton tausch5;
    private javax.swing.JButton tausch6;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
      if(e.getSource().equals(detail1)){
        PokInfo.start(besitzer.pokemon[0]);
      }else if(e.getSource().equals(detail2)){
        PokInfo.start(besitzer.pokemon[1]);
      }else if(e.getSource().equals(detail3)){
        PokInfo.start(besitzer.pokemon[2]);
      }else if(e.getSource().equals(detail4)){
        PokInfo.start(besitzer.pokemon[3]);
      }else if(e.getSource().equals(detail5)){
        PokInfo.start(besitzer.pokemon[4]);
      }else if(e.getSource().equals(detail6)){
        PokInfo.start(besitzer.pokemon[5]);
      }
      else if(e.getSource().equals(tausch1)){
        if(kampf && !besitzer.pokemon[0].isBsg()){
          Javamon.getAktuellKampf().austauschen(besitzer.pokemon[0],true);
          Javamon.displayDefault();
        }else{
          if(flag) {
            besitzer.tausche(t1,0);
            flag=false;
          }
          else{
            t1=0;
            flag=true;
          }
        }
      }
      else if(e.getSource().equals(tausch2)){
        if(kampf && !besitzer.pokemon[1].isBsg()){
          Javamon.getAktuellKampf().austauschen(besitzer.pokemon[1],true);
          Javamon.displayDefault();
        }else{
          if(flag) {
            besitzer.tausche(t1,1);
            flag=false;
          }
          else{
            t1=1;
            flag=true;
          }
        }
      }
      else if(e.getSource().equals(tausch3)){
        if(kampf && !besitzer.pokemon[2].isBsg()){
          Javamon.getAktuellKampf().austauschen(besitzer.pokemon[2],true);
          Javamon.displayDefault();
        }else{
          if(flag) {
            besitzer.tausche(t1,2);
            flag=false;
          }
          else{
            t1=2;
            flag=true;
          }
        }
      }
      else if(e.getSource().equals(tausch4)){
        if(kampf && !besitzer.pokemon[3].isBsg()){
          Javamon.getAktuellKampf().austauschen(besitzer.pokemon[3],true);
          Javamon.displayDefault();
        }else{
          if(flag) {
            besitzer.tausche(t1,3);
            flag=false;
          }
          else{
            t1=3;
            flag=true;
          }
        }
      }
      else if(e.getSource().equals(tausch5)){
        if(kampf && !besitzer.pokemon[4].isBsg()){
          Javamon.getAktuellKampf().austauschen(besitzer.pokemon[4],true);
          Javamon.displayDefault();
        }else{
          if(flag) {
            besitzer.tausche(t1,4);
            flag=false;
          }
          else{
            t1=4;
            flag=true;
          }
        }
      }
      else if(e.getSource().equals(tausch6)){
        if(kampf && !besitzer.pokemon[5].isBsg()){
          Javamon.getAktuellKampf().austauschen(besitzer.pokemon[5],true);
          Javamon.displayDefault();
        }else{
          if(flag){
            besitzer.tausche(t1,5);
            flag=false;
          }
          else{
            t1=5;
            flag=true;
          }
        }
      }
      else if(e.getSource().equals(frei1)){
        String n=besitzer.pokemon[0].toString();
        if(javax.swing.JOptionPane.showConfirmDialog(this,n+" wirklich freilassen?")
                ==javax.swing.JOptionPane.YES_OPTION){
          if(besitzer.removePok(0)){
            Javamon.sout("Tschüss "+n+"!");
          }
        }
      }
      else if(e.getSource().equals(frei2)){
        String n=besitzer.pokemon[1].toString();
        if(javax.swing.JOptionPane.showConfirmDialog(this,n+" wirklich freilassen?")
                ==javax.swing.JOptionPane.YES_OPTION){
          if(besitzer.removePok(1)){
            Javamon.sout("Tschüss "+n+"!");
          }
        }
      }
      else if(e.getSource().equals(frei3)){
        String n=besitzer.pokemon[2].toString();
        if(javax.swing.JOptionPane.showConfirmDialog(this,n+" wirklich freilassen?")
                ==javax.swing.JOptionPane.YES_OPTION){
          if(besitzer.removePok(2)){
            Javamon.sout("Tschüss "+n+"!");
          }
        }
      }
      else if(e.getSource().equals(frei4)){
        String n=besitzer.pokemon[3].toString();
        if(javax.swing.JOptionPane.showConfirmDialog(this,n+" wirklich freilassen?")
                ==javax.swing.JOptionPane.YES_OPTION){
          if(besitzer.removePok(3)){
            Javamon.sout("Tschüss "+n+"!");
          }
        }
      }
      else if(e.getSource().equals(frei5)){
        String n=besitzer.pokemon[4].toString();
        if(javax.swing.JOptionPane.showConfirmDialog(this,n+" wirklich freilassen?")
                ==javax.swing.JOptionPane.YES_OPTION){
          if(besitzer.removePok(4)){
            Javamon.sout("Tschüss "+n+"!");
          }
        }
      }
      else if(e.getSource().equals(frei6)){
        String n=besitzer.pokemon[5].toString();
        if(javax.swing.JOptionPane.showConfirmDialog(this,n+" wirklich freilassen?")
                ==javax.swing.JOptionPane.YES_OPTION){
          if(besitzer.removePok(5)){
            Javamon.sout("Tschüss "+n+"!");
          }
        }
      }
      paintLabels();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
      if(Javamon.isPrinting()){
        Javamon.printNext();
        return;
      }
      if(items.hasFocus()&&e.getKeyCode()==Steuerung.getA()){
        int index=items.getSelectedIndex();
        if(index<0)return;
        items.setToolTipText(((welt.items.Item)items.getSelectedValue()).getToolTipText());
        if(((welt.items.Item)(items.getSelectedValue())).benutzt()){
          Javamon.sout(besitzer.toString()+" benutzt "+items.getSelectedValue().toString());
          besitzer.removeItem(index);
          if(Javamon.getAktuellKampf()==null){
            paintLabels(); 
          }else{
            Javamon.getAktuellKampf().nextRound();
            Javamon.displayDefault();
          }
        }
      }
      if(e.getKeyCode()==Steuerung.getB() || e.getKeyCode()==Steuerung.getEnter()){
        Javamon.displayDefault();
      }else if(e.getKeyCode()==Steuerung.getUp()){
        if(detail2.hasFocus()&&detail1.isEnabled())detail1.requestFocusInWindow();
        else if(detail3.hasFocus()&&detail2.isEnabled())detail2.requestFocusInWindow();
        else if(detail4.hasFocus()&&detail3.isEnabled())detail3.requestFocusInWindow();
        else if(detail5.hasFocus()&&detail4.isEnabled())detail4.requestFocusInWindow();
        else if(detail6.hasFocus()&&detail5.isEnabled())detail5.requestFocusInWindow();
        else if(tausch2.hasFocus()&&tausch1.isEnabled())tausch1.requestFocusInWindow();
        else if(tausch3.hasFocus()&&tausch2.isEnabled())tausch2.requestFocusInWindow();
        else if(tausch4.hasFocus()&&tausch3.isEnabled())tausch3.requestFocusInWindow();
        else if(tausch5.hasFocus()&&tausch4.isEnabled())tausch4.requestFocusInWindow();
        else if(tausch6.hasFocus()&&tausch5.isEnabled())tausch5.requestFocusInWindow();
        else if(frei2.hasFocus()&&frei1.isEnabled())frei1.requestFocusInWindow();
        else if(frei3.hasFocus()&&frei2.isEnabled())frei2.requestFocusInWindow();
        else if(frei4.hasFocus()&&frei3.isEnabled())frei3.requestFocusInWindow();
        else if(frei5.hasFocus()&&frei4.isEnabled())frei4.requestFocusInWindow();
        else if(frei6.hasFocus()&&frei5.isEnabled())frei5.requestFocusInWindow();
        else if(items.hasFocus()&&items.getSelectedIndex()>0)items.setSelectedIndex(items.getSelectedIndex()-1);
      }else if(e.getKeyCode()==Steuerung.getLeft()){
        if(tausch1.hasFocus()&&detail1.isEnabled())detail1.requestFocusInWindow();
        else if(tausch2.hasFocus()&&detail2.isEnabled())detail2.requestFocusInWindow();
        else if(tausch3.hasFocus()&&detail3.isEnabled())detail3.requestFocusInWindow();
        else if(tausch4.hasFocus()&&detail4.isEnabled())detail4.requestFocusInWindow();
        else if(tausch5.hasFocus()&&detail5.isEnabled())detail5.requestFocusInWindow();
        else if(tausch6.hasFocus()&&detail6.isEnabled())detail6.requestFocusInWindow();
        else if(frei1.hasFocus()&&tausch1.isEnabled())tausch1.requestFocusInWindow();
        else if(frei2.hasFocus()&&tausch2.isEnabled())tausch2.requestFocusInWindow();
        else if(frei3.hasFocus()&&tausch3.isEnabled())tausch3.requestFocusInWindow();
        else if(frei4.hasFocus()&&tausch4.isEnabled())tausch4.requestFocusInWindow();
        else if(frei5.hasFocus()&&tausch5.isEnabled())tausch5.requestFocusInWindow();
        else if(frei6.hasFocus()&&tausch6.isEnabled())tausch6.requestFocusInWindow();
        else if(items.hasFocus())this.requestFocusInWindow();
      }else if(e.getKeyCode()==Steuerung.getDown()){
        if(detail1.hasFocus()&&detail2.isEnabled())detail2.requestFocusInWindow();
        else if(detail2.hasFocus()&&detail3.isEnabled())detail3.requestFocusInWindow();
        else if(detail3.hasFocus()&&detail4.isEnabled())detail4.requestFocusInWindow();
        else if(detail4.hasFocus()&&detail5.isEnabled())detail5.requestFocusInWindow();
        else if(detail5.hasFocus()&&detail6.isEnabled())detail6.requestFocusInWindow();
        else if(tausch1.hasFocus()&&tausch2.isEnabled())tausch2.requestFocusInWindow();
        else if(tausch2.hasFocus()&&tausch3.isEnabled())tausch3.requestFocusInWindow();
        else if(tausch3.hasFocus()&&tausch4.isEnabled())tausch4.requestFocusInWindow();
        else if(tausch4.hasFocus()&&tausch5.isEnabled())tausch5.requestFocusInWindow();
        else if(tausch5.hasFocus()&&tausch6.isEnabled())tausch6.requestFocusInWindow();
        else if(frei1.hasFocus()&&frei2.isEnabled())frei2.requestFocusInWindow();
        else if(frei2.hasFocus()&&frei3.isEnabled())frei3.requestFocusInWindow();
        else if(frei3.hasFocus()&&frei4.isEnabled())frei4.requestFocusInWindow();
        else if(frei4.hasFocus()&&frei5.isEnabled())frei5.requestFocusInWindow();
        else if(frei5.hasFocus()&&frei6.isEnabled())frei6.requestFocusInWindow();
        else if(items.hasFocus()&&items.getSelectedIndex()<besitzer.getitems().length-1)items.setSelectedIndex(items.getSelectedIndex()+1);
      }else if(e.getKeyCode()==Steuerung.getRight()){
        if(tausch1.hasFocus()&&frei1.isEnabled())frei1.requestFocusInWindow();
        else if(tausch2.hasFocus()&&frei2.isEnabled())frei2.requestFocusInWindow();
        else if(tausch3.hasFocus()&&frei3.isEnabled())frei3.requestFocusInWindow();
        else if(tausch4.hasFocus()&&frei4.isEnabled())frei4.requestFocusInWindow();
        else if(tausch5.hasFocus()&&frei5.isEnabled())frei5.requestFocusInWindow();
        else if(tausch6.hasFocus()&&frei6.isEnabled())frei6.requestFocusInWindow();
        else if(detail1.hasFocus()&&tausch1.isEnabled())tausch1.requestFocusInWindow();
        else if(detail2.hasFocus()&&tausch2.isEnabled())tausch2.requestFocusInWindow();
        else if(detail3.hasFocus()&&tausch3.isEnabled())tausch3.requestFocusInWindow();
        else if(detail4.hasFocus()&&tausch4.isEnabled())tausch4.requestFocusInWindow();
        else if(detail5.hasFocus()&&tausch5.isEnabled())detail5.requestFocusInWindow();
        else if(detail6.hasFocus()&&tausch6.isEnabled())tausch6.requestFocusInWindow();
        else items.requestFocusInWindow();
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
    @Override
    public boolean requestFocusInWindow(){
      return detail1.requestFocusInWindow();
    }
}