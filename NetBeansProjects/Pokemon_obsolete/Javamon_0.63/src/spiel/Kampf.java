package spiel;

import attacken.Attacke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;
import pokemon.Pokemon;

public class Kampf extends javax.swing.JPanel implements ActionListener,KeyListener,OutputListener{
    private java.util.LinkedList<Pokemon> beteiligte;
    private Pokemon neues;
    private int index;
    private Pokemon spielers;
    private Pokemon gegners;
    private Spieler spieler;
    private Trainer gegner;
    boolean wild;
    private boolean ende=false;
    private boolean dran;
    private Attacke auswahlAtt;
    private int runde=0;
    private Timer t;
    private Clip sound;

    /**
     * Creates new form Kampf
     */
    public Kampf(Spieler ich,Pokemon wildpok) {
        if(Javamon.SoundOn())playSound("battle.wav");
        spieler=ich;
        beteiligte=new java.util.LinkedList();
        for(Pokemon pok:spieler.pokemon){
          if(pok==null)continue;
          if(!pok.isBsg()){
            spielers=pok;
            beteiligte.add(pok);
            break;
          }
        }
        gegners=wildpok;
        wild=true;
        initComponents();
        addListeners();
        paintLabels();
        dran=spielers.getInit()>=gegners.getInit();
    }
    
    public Kampf(Spieler ich, Trainer geg){
        if(Javamon.SoundOn())playSound("battle.wav");
        spieler=ich;
        beteiligte=new java.util.LinkedList();
        for(Pokemon pok:spieler.pokemon){
          if(pok==null || pok.isBsg())continue;
          else{
            spielers=pok;
            beteiligte.add(pok);
            break;
          }
        }
        gegner=geg;
        gegners=geg.pokemon[0];
        wild=false;
        initComponents();
        addListeners();
        paintLabels();
        dran=spielers.getInit()>=gegners.getInit();
    }
    
    private void addListeners(){
      t=new Timer(100,this);
      t.setDelay(1000);
      addKeyListener(this);
      flucht.addActionListener(this);
      angriff.addActionListener(this);
      inventar.addActionListener(this);
      fangen.addActionListener(this);
      attacke0.addActionListener(this);
      attacke1.addActionListener(this);
      attacke2.addActionListener(this);
      attacke3.addActionListener(this);
      flucht.addKeyListener(this);
      angriff.addKeyListener(this);
      inventar.addKeyListener(this);
      fangen.addKeyListener(this);
      attacke0.addKeyListener(this);
      attacke1.addKeyListener(this);
      attacke2.addKeyListener(this);
      attacke3.addKeyListener(this);
    }
    
    private void paintLabels(){
      if(t.isRunning()){
        attacke0.setEnabled(false);
        attacke1.setEnabled(false);
        attacke2.setEnabled(false);
        attacke3.setEnabled(false);
        angriff.setEnabled(false);
        flucht.setEnabled(false);
        fangen.setEnabled(false);
        inventar.setEnabled(false);
      }
      else fangen.setEnabled(wild);
      //spielers
      int prozent=(int)(spielers.getEP()*100.0/spielers.getBenEP());
        epBar.setValue(prozent);
        epBar.setString(prozent+"%");
        prozent=(int)(spielers.getKP()*100.0/spielers.getMaxKP());
        kpBar.setValue(prozent);
        kpBar.setString(spielers.getKP()+"/"+spielers.getMaxKP());
        level.setText("Lvl: "+spielers.getLevel());
        pokName.setText(spielers.toString());
        status.setText(spielers.getStatus().name());
        bild.setIcon(spielers.getIcon());
        Attacke[] att=spielers.getAttacken();
        if(att[0]!=null){
          attacke0.setText(att[0].toString()+" "+att[0].getAP()+"/"+att[0].getMaxAP());
          String tooltip="Dmg: "+att[0].getDmg()+" Gen: "+att[0].getGena()+
                  " Typ: "+att[0].getTyp().name()+", ";
          if(att[0].isPhysisch())tooltip=tooltip+"physisch. ";
          else tooltip=tooltip+"spezial. ";
          attacke0.setToolTipText(tooltip+att[0].getToolTipText());
        }else{
          attacke0.setText("----------");
        }
        if(att[1]!=null){
          attacke1.setText(att[1].toString()+" "+att[1].getAP()+"/"+att[1].getMaxAP());
          String tooltip="Dmg: "+att[1].getDmg()+" Gen: "+att[1].getGena()+
                  " Typ: "+att[1].getTyp().name()+", ";
          if(att[1].isPhysisch())tooltip=tooltip+"physisch. ";
          else tooltip=tooltip+"spezial. ";
          attacke1.setToolTipText(tooltip+att[1].getToolTipText());
        }else{
          attacke1.setText("----------");
        }
        if(att[2]!=null){
          attacke2.setText(att[2].toString()+" "+att[2].getAP()+"/"+att[2].getMaxAP());
          String tooltip="Dmg: "+att[2].getDmg()+" Gen: "+att[2].getGena()+
                  " Typ: "+att[2].getTyp().name()+", ";
          if(att[2].isPhysisch())tooltip=tooltip+"physisch. ";
          else tooltip=tooltip+"spezial. ";
          attacke2.setToolTipText(tooltip+att[2].getToolTipText());
        }else{
          attacke2.setText("----------");
        }
        if(att[3]!=null){
          attacke3.setText(att[3].toString()+" "+att[3].getAP()+"/"+att[3].getMaxAP());
          String tooltip="Dmg: "+att[3].getDmg()+" Gen: "+att[3].getGena()+
                  " Typ: "+att[3].getTyp().name()+", ";
          if(att[3].isPhysisch())tooltip=tooltip+"physisch. ";
          else tooltip=tooltip+"spezial. ";
          attacke3.setToolTipText(tooltip+att[3].getToolTipText());
        }else{
          attacke3.setText("----------");
        }
        // gegners
        prozent=(int)(gegners.getKP()*100.0/gegners.getMaxKP());
        kpBar1.setValue(prozent);
        level1.setText("Lvl: "+gegners.getLevel());
        pokName1.setText(gegners.toString());
        status1.setText(gegners.getStatus().name());
        bild1.setIcon(gegners.getIcon());
        requestFocus();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        angriff = new javax.swing.JButton();
        inventar = new javax.swing.JButton();
        flucht = new javax.swing.JButton();
        fangen = new javax.swing.JButton();
        pokName = new javax.swing.JLabel();
        status = new javax.swing.JLabel();
        level = new javax.swing.JLabel();
        epBar = new javax.swing.JProgressBar();
        kpBar = new javax.swing.JProgressBar();
        bild = new javax.swing.JLabel();
        pokName1 = new javax.swing.JLabel();
        status1 = new javax.swing.JLabel();
        level1 = new javax.swing.JLabel();
        kpBar1 = new javax.swing.JProgressBar();
        bild1 = new javax.swing.JLabel();
        attacke0 = new javax.swing.JButton();
        attacke2 = new javax.swing.JButton();
        attacke1 = new javax.swing.JButton();
        attacke3 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        angriff.setText("angreifen");
        angriff.setNextFocusableComponent(inventar);

        inventar.setText("Inventar");
        inventar.setNextFocusableComponent(fangen);

        flucht.setText("Flucht");
        flucht.setNextFocusableComponent(angriff);

        fangen.setText("Pokeball");
        fangen.setNextFocusableComponent(flucht);

        pokName.setText("Pokemon");

        status.setText("status");

        level.setText("level");

        epBar.setValue(50);
        epBar.setStringPainted(true);

        kpBar.setBackground(new java.awt.Color(204, 0, 0));
        kpBar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kpBar.setForeground(new java.awt.Color(51, 204, 0));
        kpBar.setValue(50);
        kpBar.setStringPainted(true);

        bild.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/img/abstract.png"))); // NOI18N

        pokName1.setText("Pokemon");

        status1.setText("status");

        level1.setText("level");

        kpBar1.setBackground(new java.awt.Color(204, 0, 0));
        kpBar1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        kpBar1.setForeground(new java.awt.Color(51, 204, 0));
        kpBar1.setValue(50);

        bild1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pokemon/img/abstract.png"))); // NOI18N

        attacke0.setText("----------");
        attacke0.setEnabled(false);
        attacke0.setNextFocusableComponent(attacke1);

        attacke2.setText("----------");
        attacke2.setEnabled(false);
        attacke2.setNextFocusableComponent(attacke3);

        attacke1.setText("----------");
        attacke1.setEnabled(false);
        attacke1.setNextFocusableComponent(attacke2);

        attacke3.setText("----------");
        attacke3.setEnabled(false);
        attacke3.setNextFocusableComponent(attacke0);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(attacke2)
                            .addComponent(attacke0))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(attacke1)
                            .addComponent(attacke3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(angriff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fangen, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(flucht, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inventar)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(bild)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(level, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(epBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(pokName, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(kpBar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(status)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(bild1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(level1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pokName1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kpBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(status1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kpBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(status1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pokName1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(level1))
                    .addComponent(bild1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kpBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pokName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(status))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(epBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(level)))
                    .addComponent(bild))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(angriff)
                    .addComponent(inventar)
                    .addComponent(attacke0)
                    .addComponent(attacke1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(flucht)
                    .addComponent(fangen)
                    .addComponent(attacke2)
                    .addComponent(attacke3))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton angriff;
    private javax.swing.JButton attacke0;
    private javax.swing.JButton attacke1;
    private javax.swing.JButton attacke2;
    private javax.swing.JButton attacke3;
    private javax.swing.JLabel bild;
    private javax.swing.JLabel bild1;
    private javax.swing.JProgressBar epBar;
    private javax.swing.JButton fangen;
    private javax.swing.JButton flucht;
    private javax.swing.JButton inventar;
    private javax.swing.JProgressBar kpBar;
    private javax.swing.JProgressBar kpBar1;
    private javax.swing.JLabel level;
    private javax.swing.JLabel level1;
    private javax.swing.JLabel pokName;
    private javax.swing.JLabel pokName1;
    private javax.swing.JLabel status;
    private javax.swing.JLabel status1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
      if(Javamon.isPrinting()){
        requestFocusInWindow();
        return;
      }
      if(e.getSource().equals(t)){
        if(dran){
          if(!checkStatus(spielers)){
            if(spielers.isBsg()){
              spielersBsg();
            }
          }
          else if(spielerZug()){
            gegnersBsg();
          }
        }else{
          if(!checkStatus(gegners)){
            if(gegners.isBsg()){
              gegnersBsg();
            }else if(!wild && gegner.benutzGenesung()){
              Javamon.sout(gegner+" benutzt Top-Genesung");
              gegners.heal();
            }
          }else if(!wild&&gegners.getKP()<=gegners.getMaxKP()/8 && gegner.benutzGenesung()){
            Javamon.sout(gegner+" benutzt Top-Genesung");
            gegners.heal();
          }else if(gegnerZug()){
            spielersBsg();
          }
        }
        paintLabels();
        nextRound();
      }else if(e.getSource().equals(flucht)){
        if(wild && gegners.getInit()<=spielers.getInit()*(1+Math.random())){
          Javamon.sout("Du bist entkommen");
          if(wild){
            gegners.heal();
          }else{
            gegner.heile();
          }
          wild=false;
          ende=true;
          Javamon.addListener(this);
          if(sound!=null)sound.stop();
        }else{
          Javamon.sout("Du kannst nicht flüchten!");
        }
      }else if(e.getSource().equals(angriff)){
        Attacke[] att=spielers.getAttacken();
        if(att[0]!=null) attacke0.setEnabled(true);
        if(att[1]!=null) attacke1.setEnabled(true);
        if(att[2]!=null) attacke2.setEnabled(true);
        if(att[3]!=null) attacke3.setEnabled(true);
        if(auswahlAtt==null) attacke0.requestFocus();
        else if(auswahlAtt.equals(att[0]))attacke0.requestFocus();
        else if(auswahlAtt.equals(att[1]))attacke1.requestFocus();
        else if(auswahlAtt.equals(att[2]))attacke2.requestFocus();
        else if(auswahlAtt.equals(att[3]))attacke3.requestFocus();
        else attacke0.requestFocus();
      }else if(e.getSource().equals(fangen)){
        if(wild){
          boolean nochPlatz=spieler.givePokemon(gegners);
          Javamon.sout("Du hast das wilde "+gegners+" gefangen!");
          spieler.pokedex++;
          if(!nochPlatz){
            Javamon.getBox().add(gegners);
            gegners.gefangen(true);
            Javamon.sout(gegners+" wurde in die Box gestopft.");
          }
          wild=true;
          ende=true;
          Javamon.addListener(this);
          if(sound!=null)sound.stop();
        }
      }else if(e.getSource().equals(inventar)){
        Javamon.display(new Inventar(true));
      }else{
        if(e.getSource().equals(attacke0)){
          auswahlAtt=spielers.getAttacken()[0];
        } 
        else if(e.getSource().equals(attacke1)){
          auswahlAtt=spielers.getAttacken()[1];
        }
        else if(e.getSource().equals(attacke2)){
          auswahlAtt=spielers.getAttacken()[2];
        }
        else if(e.getSource().equals(attacke3)){
          auswahlAtt=spielers.getAttacken()[3];
        }
        if(auswahlAtt.getAP()<=0){
          Javamon.sout("Die AP sind aufgebraucht.");
          return;
        }
        t.start();
      }
    }
    
    public boolean spielerZug(){
        if(spielers.isConfused()){
          Javamon.sout(spielers+" ist verwirrt.");
          if(Math.random()*3<=1){
            Javamon.sout("Es hat sich vor verwirrung selbst verletzt.");
            if(spielers.nimmSchaden(spielers.getMaxKP()/5)){
              spielersBsg();
            }
            return false;
          }
        }
        if(auswahlAtt==null)return false;
        Javamon.sout(spielers.toString()+" benutzt "+auswahlAtt.toString());
        if(gegners.getFähigkeit()==Fähigkeit.SCHWEBE&&auswahlAtt.getTyp()==Typ.BODEN){
          Javamon.sout(auswahlAtt+" hat keine Wirkung auf "+gegners+" wegen "+Fähigkeit.SCHWEBE);
          return false;
        }
        int trefferP=(int)(gegners.getFlu()*spielers.getGena()*auswahlAtt.getGena());
        if(trefferP<10)trefferP=10;
        else if(trefferP>100)trefferP=100;
        if((Math.random()*100)<=trefferP){
          double multi1=Javamon.MULTI[Javamon.TYPENANZ*gegners.getTyp1().index+
                  auswahlAtt.getTyp().index];
          double multi2=Javamon.MULTI[Javamon.TYPENANZ*gegners.getTyp2().index+
                  auswahlAtt.getTyp().index];
          double multi=multi1*multi2;
          int abzuziehen=gegners.verteidige(spielers.angriff(auswahlAtt),auswahlAtt.isPhysisch(),multi);
          if((Math.random()*1000)<=auswahlAtt.getVP()+spielers.getVP()
                  && abzuziehen>0){
            Javamon.sout("Volltreffer!");
            abzuziehen=abzuziehen*2;
          }
          if(multi>1 && abzuziehen>0)Javamon.sout("Das ist sehr effektiv!");
          else if(multi==0&&auswahlAtt.getDmg()>0)Javamon.sout(auswahlAtt+" hat keine Wirkung auf "+gegners);
          else if(multi<1 && abzuziehen>0)Javamon.sout("Das ist nicht sehr effektiv...");
          if(gegners.getFähigkeit()==Fähigkeit.STATIK&&abzuziehen>0&&
                    spielers.getStatus()==Status.OK&&Math.random()*5>=2){
            Javamon.sout(Fähigkeit.STATIK+" von "+gegners+" paralysiert "+spielers);
            spielers.setStatus(Status.PAR);
          }
          auswahlAtt.benutzt(true,spielers, gegners);
          return gegners.nimmSchaden(abzuziehen);
        }else{
          auswahlAtt.benutzt(false,spielers,gegners);
          if(auswahlAtt.getDmg()<=0)Javamon.sout("Die Attacke schlug fehl");
          else Javamon.sout("Die Attacke geht daneben.");
        }
        return false;
    }
    
    public boolean gegnerZug(){
      if(gegners.isConfused()){
        Javamon.sout(gegners+" ist verwirrt.");
        if(Math.random()*3<=1){
          Javamon.sout("Es hat sich vor Verwirrung selbst verletzt.");
          if(gegners.nimmSchaden(gegners.getMaxKP()/5)){
            gegnersBsg();
          }
          return false;
        }
      }
      Attacke[] gegnersAtt=new Attacke[gegners.getAttacken().length+gegners.getBekannte().length];
      boolean alleLeer=true;
      for(int i=0;i<gegnersAtt.length;i++){
        if(i>=gegners.getAttacken().length)gegnersAtt[i]=gegners.getBekannte()[i-gegners.getAttacken().length];
        else gegnersAtt[i]=gegners.getAttacken()[i];
        if(alleLeer&&gegnersAtt[i]!=null&&gegnersAtt[i].getAP()>0)alleLeer=false;
      }
      while(true){
        int zufall=(int)(Math.random()*gegnersAtt.length);
        if(gegnersAtt[zufall]==null)continue;
        else if(alleLeer)gegnersAtt[zufall].resetAp();
        if(gegnersAtt[zufall].getAP()>0){
          Javamon.sout("Gegn. "+gegners.toString()+" benutzt "+gegnersAtt[zufall].toString());
          if(spielers.getFähigkeit()==Fähigkeit.SCHWEBE&&gegnersAtt[zufall].getTyp()==Typ.BODEN){
            Javamon.sout(gegnersAtt[zufall]+" hat keine Wirkung auf "+spielers+" wegen "+Fähigkeit.SCHWEBE);
            return false;
          }
          int trefferP=(int)(spielers.getFlu()*gegners.getGena()*gegnersAtt[zufall].getGena());
          if(trefferP<10)trefferP=10;
          else if(trefferP>100)trefferP=100;
          if((Math.random()*100)<=trefferP){
            double multi1=Javamon.MULTI[Javamon.TYPENANZ*spielers.getTyp1().index+
                    gegnersAtt[zufall].getTyp().index];
            double multi2=Javamon.MULTI[Javamon.TYPENANZ*spielers.getTyp2().index+
                    gegnersAtt[zufall].getTyp().index];
            double multi=multi1*multi2;
            int abzuziehen=spielers.verteidige(gegners.angriff(zufall),gegnersAtt[zufall].isPhysisch(),multi);
            if((Math.random()*1000)<=gegners.getVP()+gegnersAtt[zufall].getVP()
                    && abzuziehen>0){
              Javamon.sout("Volltreffer!");
              abzuziehen=abzuziehen*2;
            }
            if(multi>1 && abzuziehen>0)Javamon.sout("Das ist sehr effektiv!");
            else if(multi==0&&gegnersAtt[zufall].getDmg()>0)
                Javamon.sout(gegnersAtt[zufall]+" hat keine Wirkung auf "+spielers);
            else if(multi<1 && abzuziehen>0)Javamon.sout("Das ist nicht sehr effektiv...");
            if(spielers.getFähigkeit()==Fähigkeit.STATIK&&abzuziehen>0&&
                    gegners.getStatus()==Status.OK&&Math.random()*5>=2){
              Javamon.sout(Fähigkeit.STATIK+" von "+spielers+" paralysiert "+gegners);
              gegners.setStatus(Status.PAR);
            }
            gegnersAtt[zufall].benutzt(true,gegners,spielers);
            return spielers.nimmSchaden(abzuziehen);
          }else{
            gegnersAtt[zufall].benutzt(false,gegners,spielers);
            if(gegnersAtt[zufall].getDmg()>0) Javamon.sout("Die Attacke geht daneben.");
            else Javamon.sout("Die Attacke schlug fehl.");
          }
          return false;
        }
      }
    }
    
    public void nextRound(){
        runde++;
        dran=!dran;
        if(runde%2==0){
          t.stop();
          Javamon.addListener(this);
        }
    }
    
    private boolean checkStatus(Pokemon pok){
      boolean kann=true;
      if(pok.getFähigkeit()==Fähigkeit.EXPIDERMIS&&pok.getStatus()!=Status.OK&&Math.random()*6<=3){
        Javamon.sout(pok+" heilt sich durch "+pok.getFähigkeit());
        pok.setStatus(Status.OK);
        return true;
      }
      switch(pok.getStatus()){
          case GFT:
            Javamon.sout(pok+" leidet an der Vergiftung.");
            kann=!pok.nimmSchaden(pok.getMaxKP()/12);
            break;
          case BRT:
            Javamon.sout(pok+" leidet an den Verbrennungen.");
            kann=!pok.nimmSchaden(pok.getMaxKP()/10);
            break;
          case SLF:
            if(Math.random()*10<=2){
              Javamon.sout(pok+" ist aufgewacht!");
              kann=true;
              pok.setStatus(Status.OK);
            }else{
              Javamon.sout(pok+" schläft...");
              kann=false;
            }
            break;
          case GFR:
            Javamon.sout(pok+" ist fest im Eis eingeschlossen...");
            kann=false;
            break;
          case PAR:
            if(Math.random()*9<=3){
              kann=false;
              Javamon.sout(pok+" ist paralysiert.");
            }
            break;
      }
      return kann;
    }
    
    public void austauschen(Pokemon next,boolean spielerstausch){
      if(spielerstausch){
        if(!spielers.isBsg()){
          Javamon.sout(spieler+": Genug, "+spielers+". Komm zurück!");
          dran=false;
          runde=1;
          t.start();
        }
        if(!beteiligte.contains(next))beteiligte.add(next);
        spielers=next;
        Javamon.sout("Los! "+next+"!");
      }else{
        if(!gegners.isBsg()){
          Javamon.sout(gegner+": Genug, "+spielers+". Komm zurück!");
          dran=true;
          runde=1;
          t.start();
        }
        Javamon.sout(gegner+" schickt "+next+" in den Kampf.");
        gegners=next;
      }
      paintLabels();
      requestFocusInWindow();
    }
    
    public boolean binDran(){
        return dran;
    }
    
    public Pokemon getSpielers(){
        return spielers;
    }
    
    public Pokemon getGegners(){
        return gegners;
    }
    
    private void gegnersBsg(){
      Javamon.sout(gegners+" wurde besiegt.");
      int ep=gegners.getBenEP()/beteiligte.size();
      if(wild){
        ep=ep/2;
      }
      for(Pokemon pok:beteiligte){
          boolean lvlUp=pok.getEP()+ep>=pok.getBenEP();
          Javamon.sout(pok+" erhält "+ep+" EP.");
          Pokemon neu = pok.bekommEP(ep);
          if(lvlUp)Javamon.sout(pok.toString()+" erreicht Level "+(pok.getLevel()));
          if(neu!=null){
            for(int i=0;i<spieler.pokemon.length;i++){
              if(spieler.pokemon[i]==null)continue;
              if(spieler.pokemon[i].equals(pok)){
                index=i;
                neues=neu;
                break;
              }
            }
          }
        }
      if(wild || gegner.isBesiegt())ende(true);
      else{
        for(Pokemon next:gegner.pokemon){
          if(next==null || next.isBsg())continue;
          else{
            austauschen(next,false);
            break;
          }
        }
      }
    }
    
    private void spielersBsg(){
      Javamon.sout(spieler+"s "+spielers+" wurde besiegt.");
      beteiligte.remove(spielers);
      if(!spieler.isBesiegt()) {
        Pokemon next=null;
        while(next==null||next.isBsg()){
          next=new PokAuswahl(spieler.pokemon).getAuswahl();
        }
        austauschen(next,true);
      }else{
        ende(false);
      }
    }
    
    public void ende(boolean gewonnen){
      if(gewonnen){
        Javamon.sout("Du hast gewonnen!");
        int geld;
        if(wild){
          geld=(int)(gegners.getLevel()*Math.random()*10);
        }else{
          geld=gegner.getGeld();
        }
        Javamon.sout("Du erhälst "+geld+" Geld");
        spieler.giveGeld(geld);
      }else{
        Javamon.sout("Alle Deine Pokemon wurden besiegt.#Du hast verloren...");
        if(!wild){
          Javamon.sout(spieler.name+" gibt "+spieler.getGeld()/4+" Preisgeld...");
          spieler.giveGeld(-spieler.getGeld()/4);
        }else{
          Javamon.sout("Die wilden Tiere fressen deine Eingeweide!");
        }
      }
      t.stop();
      if(sound!=null)sound.stop();
      wild=false;
      ende=true;
      Javamon.addListener(this);
    }
    
    private void playSound(String filename) {
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new java.io.File("sound/"+filename));
            AudioFormat af     = audioInputStream.getFormat();
            int size      = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
            byte[] audio       = new byte[size];
            DataLine.Info info      = new DataLine.Info(Clip.class, af, size);
            audioInputStream.read(audio, 0, size);
                sound = (Clip) AudioSystem.getLine(info);
                sound.open(af, audio, 0, size);
                sound.loop(100);
        }catch(UnsupportedAudioFileException | IOException | LineUnavailableException e){ e.printStackTrace(); }   
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
      if(e.getKeyCode()==Steuerung.getA()){
        if(Javamon.isPrinting())Javamon.printNext();
        else angriff.requestFocusInWindow();
      }else if(e.getKeyCode()==Steuerung.getDown()){
        if(angriff.hasFocus()&&fangen.isEnabled())fangen.requestFocusInWindow();
        else if(inventar.hasFocus()&&flucht.isEnabled())flucht.requestFocusInWindow();
        else if(attacke0.hasFocus()&&attacke2.isEnabled())attacke2.requestFocusInWindow();
        else if(attacke1.hasFocus()&&attacke3.isEnabled())attacke3.requestFocusInWindow();
      }else if(e.getKeyCode()==Steuerung.getUp()){
        if(fangen.hasFocus()&&angriff.isEnabled())angriff.requestFocusInWindow();
        else if(flucht.hasFocus()&&inventar.isEnabled())inventar.requestFocusInWindow();
        else if(attacke2.hasFocus()&&attacke0.isEnabled())attacke0.requestFocusInWindow();
        else if(attacke3.hasFocus()&&attacke1.isEnabled())attacke1.requestFocusInWindow();
      }else if(e.getKeyCode()==Steuerung.getLeft()){
        if(inventar.hasFocus()&&angriff.isEnabled())angriff.requestFocusInWindow();
        else if(flucht.hasFocus()&&fangen.isEnabled())fangen.requestFocusInWindow();
        else if(attacke1.hasFocus()&&attacke0.isEnabled())attacke0.requestFocusInWindow();
        else if(attacke3.hasFocus()&&attacke2.isEnabled())attacke2.requestFocusInWindow();
      }else if(e.getKeyCode()==Steuerung.getRight()){
        if(angriff.hasFocus()&&inventar.isEnabled())inventar.requestFocusInWindow();
        else if(fangen.hasFocus()&&flucht.isEnabled())flucht.requestFocusInWindow();
        else if(attacke0.hasFocus()&&attacke1.isEnabled())attacke1.requestFocusInWindow();
        else if(attacke2.hasFocus()&&attacke3.isEnabled())attacke3.requestFocusInWindow();
      }else if(e.getKeyCode()==Steuerung.getB()&&attacke0.isEnabled()){
        attacke0.setEnabled(false);
        attacke1.setEnabled(false);
        attacke2.setEnabled(false);
        attacke3.setEnabled(false);
        angriff.requestFocusInWindow();
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void ausgabeFertig() {
      if(!ende){
        angriff.setEnabled(true);
        flucht.setEnabled(true);
        fangen.setEnabled(wild);
        inventar.setEnabled(true);
        dran=spielers.getInit()>=gegners.getInit();
        return;
      }
      if(!wild){
        if(gegner!=null){
          if(spieler.isBesiegt()){
            gegner.heile();
          }
          for(Pokemon pok:gegner.pokemon){
            if(pok!=null)pok.resetBoosts();
          }
        }else{
          gegners.heal();
        }
      }
      if(neues!=null&&!spieler.isBesiegt()&&
            javax.swing.JOptionPane.showConfirmDialog(this,"Hey! "+spieler.pokemon[index].toString()+" will sich entwickeln!")
            ==javax.swing.JOptionPane.OK_OPTION){
          Javamon.sout(spieler.pokemon[index]+" entwickelt sich zu "+neues+"!");
          spieler.pokemon[index]=neues;
      }
      for(Pokemon pok:spieler.pokemon){
        if(pok!=null)pok.resetBoosts();
      }
      gegners.resetBoosts();
      Javamon.kampfEnde(wild);
    }
}