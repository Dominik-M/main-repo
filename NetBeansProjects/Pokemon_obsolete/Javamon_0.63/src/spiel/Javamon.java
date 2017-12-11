package spiel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import pokemon.*;
import tools.OrtEditor;

public class Javamon extends JFrame implements java.io.Serializable,ActionListener,java.awt.event.WindowListener{
  public static final double[] MULTI={
      //Schadensmultiplikator nach Effektivitätstabelle
      1.0,1.0,1.0,1.0,1.0,0.0,1.0,1.0,1.5,1.0,1.0,2.0,1.0,1.0,
      1.0,0.5,2.0,0.5,0.5,1.0,1.0,1.5,1.0,1.0,1.0,1.0,0.5,1.0,
      1.0,0.4,0.5,2.5,0.5,1.0,2.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,
      1.0,2.0,0.2,0.5,2.0,1.0,0.5,1.5,2.0,1.0,2.0,1.0,1.0,1.0,
      1.0,2.0,0.3,1.0,0.5,1.0,1.0,1.5,1.0,1.0,1.0,1.5,1.5,1.0,
      0.0,1.0,1.0,1.0,1.0,2.0,1.0,1.0,0.5,2.0,1.0,0.0,0.0,2.0,
      1.0,1.0,1.0,1.0,1.0,1.0,0.5,2.0,1.0,1.0,1.0,1.0,1.0,1.0,
      0.6,0.5,2.5,3.0,2.0,1.0,0.0,0.5,0.5,1.0,0.5,1.5,2.0,1.0,
      1.0,1.5,1.0,0.2,1.5,1.0,1.0,1.5,0.5,1.0,1.5,0.5,1.0,1.0,
      1.0,1.0,1.0,1.0,1.0,2.0,1.0,1.0,2.0,0.5,1.0,0.5,1.0,2.0,
      1.0,0.5,0.6,0.4,3.0,1.0,2.0,0.0,0.5,0.5,1.5,0.5,0.5,1.0,
      1.0,1.0,1.0,1.0,1.0,2.0,1.0,1.0,1.5,2.0,2.0,1.0,1.0,0.5,
      0.4,2.0,1.0,0.5,1.0,1.0,2.0,0.5,0.0,1.0,0.5,2.0,0.5,1.0,
      1.0,1.0,1.0,1.0,1.0,0.5,1.0,1.0,1.5,0.0,1.0,2.0,1.0,0.5
  };
  public static final Color[] TYPEN={
      Color.WHITE, // 0: Typ Normal
      Color.RED, //   1: Typ Feuer
      Color.BLUE, //  2: Typ Wasser
      Color.GREEN,//  3: Typ Pflanze
      Color.CYAN, // 4: TYP Eis
      Color.GRAY, // 5: TYP Geist
      Color.YELLOW, // 6: TYP Elektro
      Color.DARK_GRAY, // 7: TYP Gestein/Boden
      Color.ORANGE, // 8: TYP Käfer/Gift
      Color.MAGENTA, // 9: TYP Psycho
      Color.PINK,     // 10: TYP Drache/Flug
      new Color(140,88,60), // 11: TYP Kampf
      new Color(200,200,230), // 12: Typ Metall("Stahl")
      new Color(120,0,120) // 13: Typ Unlicht
  };
  public static final boolean MAKE_FILES=false;
  public static final int POKE_ANZ=6,ATTACKEN_ANZ=4;
  public static final int ANGR=0,VERT=1,SPEZANGR=2,SPEZVERT=3,INIT=4,KP=5;
  public static final int TYPENANZ=14; //Anzahl der Typen
  public static final int BREIT=12; //Breite der Spielwelt in Spots
  public static final int HOCH=12; //Höhe der Spielwelt in Spots
  public static final char NEXT_LINE='#'; // Zeilenumbruchszeichen
  public static welt.orte.Ort startort;
  private static JLabel ausgabe=new JLabel(" "); //Ausgabezeile (Dialoge,Ereignisse etc.)
  private static Stringqueue texte=new Stringqueue();
  private static java.util.LinkedList<OutputListener> listener=new java.util.LinkedList();
  private static Spielwelt spiel; //Die Spielwelt
  private static Javamon fenster; //Das Fenster mit dem Spiel
  private static Point loc=new Point(); //Die Position des Fensters
  private static Trainer rivale;
  private static Kampf kampf;
  private static java.awt.Component active;
  private static boolean soundAn=false;
  private javax.swing.Timer t;
  private String text;
  private boolean blink;
  private JMenuBar menuBar=new JMenuBar();
  private JMenu option=new JMenu("Optionen");
  private JMenu system=new JMenu("System");
  private JMenuItem spielerpos=new JMenuItem("Spielerposition ausgeben");
  private JMenuItem reset=new JMenuItem("Reset");
  private JMenuItem editor=new JMenuItem("Im Editor öffnen");
  private JMenuItem gr=new JMenuItem("Vergrößern");
  private JMenuItem kl=new JMenuItem("Verkleinern");
  private JCheckBoxMenuItem gitter=new javax.swing.JCheckBoxMenuItem("Gitter zeichnen",false);
  private JCheckBoxMenuItem sound=new javax.swing.JCheckBoxMenuItem("Sound",soundAn);
  private JCheckBoxMenuItem mausAn=new javax.swing.JCheckBoxMenuItem("Maussteuerung",true);
  private JCheckBoxMenuItem tastaturAn=new javax.swing.JCheckBoxMenuItem("Tastatursteuerung",true);
  private JMenuItem steuerung=new javax.swing.JMenuItem("Steuerung");
  private JMenu hilfe=new JMenu("Hilfe");
  private JMenuItem verlaufen=new JMenuItem("Ich hab mich verlaufen...");
  
  public Javamon(){
    super("Javamon");
    getContentPane().add(spiel, BorderLayout.CENTER);
    active=spiel;
    getContentPane().add(ausgabe,BorderLayout.SOUTH);
    setLocation(loc);
    t=new javax.swing.Timer(100,this);
    blink=true;
    gr.addActionListener(this);
    kl.addActionListener(this);
    verlaufen.addActionListener(this);
    gitter.addActionListener(this);
    sound.addActionListener(this);
    mausAn.addActionListener(this);
    tastaturAn.addActionListener(this);
    spielerpos.addActionListener(this);
    reset.addActionListener(this);
    steuerung.addActionListener(this);
    editor.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              OrtEditor.showOrtEditorDialog(spiel.getOrt());
              dispose();
          }
      });
    system.add(spielerpos);
    system.add(reset);
    system.add(editor);
    option.add(gr);
    option.add(kl);
    option.add(gitter);
    option.add(sound);
    option.add(mausAn);
    option.add(tastaturAn);
    option.add(steuerung);
    hilfe.add(verlaufen);
    menuBar.add(system);
    menuBar.add(option);
    menuBar.add(hilfe);
    setJMenuBar(menuBar);
    ausgabe.setFont(new java.awt.Font("Calibri",1, 26));
    ausgabe.setFocusable(false);
    this.addWindowListener(this);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//    setResizable(false);
    pack();
    setVisible(true);
  }
  
    public static void neustart(String name,Pokemon starter){
      Pokemon gegners;
        switch (starter.getOriginName()) {
            case "Schiggy":
                gegners=new Bisasam(5);
                break;
            case "Glumanda":
                gegners=new Schiggy(5);
                break;
            default:
                gegners=new Glumanda(5);
                break;
        }
      gegners.gefangen(false);
      rivale=new Trainer("Hans",1500,Trainer.DOWN,gegners);
      rivale.setDialog1("Hey!#Was machst du denn hier,#du Pisser?!"+
              "#Ich mach dich fertig, Altaah!!");
      rivale.setDialog2("Verflixt!#Du hast gewonnen!#"
              + "Nächstes Mal geht unser Kampf anders aus.#"
              + "Ich muss nur noch etwas mehr trainieren.#"
              + "Das solltest du auch tun! #Bis bald!");
      if(startort==null)
        spiel=new Spielwelt();
      else 
          spiel=new Spielwelt(startort);
      spiel.rivale=rivale;
      fenster=new Javamon();
      spiel.getSpieler().name=name;
      spiel.getSpieler().giveGeld(3000);
      spiel.getSpieler().givePokemon(starter);
//      spiel.getSpieler().givePokemon(new pokemon.Bisasam(5));
//      spiel.getSpieler().givePokemon(new pokemon.Schiggy(5));
//      spiel.getSpieler().givePokemon(new pokemon.Glumanda(5));
//      spiel.getSpieler().givePokemon(new pokemon.Taubsi(5));
//      spiel.getSpieler().givePokemon(new pokemon.Prinz(5));
    }
    
    public static void sout(String txt){
      if(txt==null)return;
      int linewrap=32;
      while(txt.length()>linewrap||txt.indexOf(NEXT_LINE)>=0){
        int schnitt=txt.indexOf(NEXT_LINE);
        if(schnitt<0||schnitt>=linewrap)schnitt=txt.substring(0,linewrap).lastIndexOf(" ");
        texte.add(txt.substring(0,schnitt));
        txt=txt.substring(schnitt+1);
      }
      texte.add(txt);
      if(!isPrinting())printNext();
    }
    
    public static void printNext(){
      String text=texte.pull();
      if(text==null){
        ausgabe.setText(" ");
        fenster.t.stop();
        for(OutputListener ol:listener)ol.ausgabeFertig();
        listener.clear();
      }
      else if(fenster!=null){
        if(!fenster.t.isRunning())fenster.t.start();
        ausgabe.setText(text);
        System.out.println(text);
        fenster.text=text;
      }
    }
    
    public static boolean isPrinting(){
      return !ausgabe.getText().equals(" ");
    }
    
    public static void save(){
      try{
        FileOutputStream fos=new FileOutputStream("spiel.dat");
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(spiel);
        fos.close();
        oos.close();
        loc.setLocation(fenster.getLocation());
        sout("Spielstand gespeichert");
      }catch(Exception ex){
        sout(ex.toString());
      }
    }
    
    public static void load()throws Exception{
        File datei=new File("spiel.dat");
        FileInputStream fis=new FileInputStream(datei);
        ObjectInputStream ois=new ObjectInputStream(fis);
        spiel=(Spielwelt)ois.readObject();
        if(fenster!=null)fenster.dispose();
        fenster=new Javamon();
        spiel.requestFocus();
        Sound.playSound("route4.wav");
        fis.close();
        ois.close();
        sout("Spielstand geladen");
    }

    public static void starteKampf(){
      starteKampf(spiel.getOrt().getPok());
    }
    
    public static void starteKampf(Pokemon wild){
      if(wild==null || getSpieler().isBesiegt())return;
      spiel.stopp();
      sout("Ein wildes "+wild.toString()+" greift an!");
      kampf=new Kampf(spiel.getSpieler(),wild);
      fenster.setJMenuBar(null);
      display(kampf);
    }
    
    public static void starteKampf(Trainer gegner){
      if(gegner.isBesiegt() || getSpieler().isBesiegt()) return;
      spiel.stopp();
      sout("Herausforderung von "+gegner.toString());
      kampf=new Kampf(spiel.getSpieler(),gegner);
      fenster.setJMenuBar(null);
      display(kampf);
    }
    
    public static Kampf getAktuellKampf(){
      return kampf;
    }
    
    public static void kampfEnde(boolean flag) {
      if(flag){
        spiel.getOrt().removePok(kampf.getGegners());
      }
      if(getSpieler().isBesiegt()){
          spiel.ortwechsel();
          getSpieler().heile();
      }
      display(spiel);
      fenster.setJMenuBar(fenster.menuBar);
      kampf=null;
      Sound.playSound();
      spiel.repaint();
    }
    
    public static void display(java.awt.Component c){
        fenster.getContentPane().remove(active);
        fenster.getContentPane().add(c,BorderLayout.CENTER);
        c.requestFocus();
        active=c;
        fenster.pack();
    }
    
    public static void displayDefault(){
        if(kampf==null)display(spiel);
        else display(kampf);
    }
    
    public static Point getLoc(){
      if(fenster==null)return new Point(0,0);
      return fenster.getLocation();
    }
    
    public static Spieler getSpieler(){
      return spiel.getSpieler();
    }
    
    static java.util.LinkedList getBox(){
      return spiel.boxPoks;
    }
    
    public static Trainer getRivale() {
      if(spiel==null)return rivale;
      return spiel.rivale;
    }
    
    public static void rivaleBsg(){
      if(spiel.rivale.pokemon[1]==null){
        spiel.getOrt().erstelle();
        spiel.rivale.heile();
        spiel.rivale.giveGeld(8000);
        Pokemon neu=null;
        while(neu==null)neu=spiel.rivale.pokemon[0].levelUp();
        spiel.rivale.pokemon[0]=neu;
        neu.bekommEP(neu.getBenEP()*5);
        neu.verteilEV();
        spiel.rivale.givePokemon(new pokemon.Taubsi(neu.getLevel()-2));
        spiel.rivale.tausche(0,1);
        spiel.rivale.dialog1="So sieht man sich also wieder, #Sackgesicht!  "
                + "#Wie gehts deinen Pokemon, "+spiel.getSpieler()+"? #"
                + "Also meine sind viel stärker geworden,"
                + " #das solltest du dir ansehen!";
        spiel.rivale.dialog2="Wie konnte das geschehen?#"
                + "Ich habe schon wieder versagt...   #"
                + "Ich muss mich noch mehr anstrengen.";
      }else if(spiel.rivale.pokemon[2]==null){
        spiel.getOrt().erstelle();
        spiel.rivale.heile();
        spiel.rivale.giveGeld(24000);
        Pokemon neu=null;
        while(neu==null)neu=spiel.rivale.pokemon[1].levelUp();
        spiel.rivale.pokemon[1]=neu;
        neu.bekommEP(neu.getBenEP()*15);
        neu.verteilEV();
        neu=spiel.rivale.pokemon[0];
        neu.bekommEP(neu.getBenEP()*25);
        neu.verteilEV();
        spiel.rivale.givePokemon(new pokemon.Dahaka(50));
        spiel.rivale.dialog1="Du schon wieder?! -.-'#"
                + "Du hast wohl immernoch nicht genug.#"
                + "Na schön, #diesmal werde ich dir eine Abrreibung verpassen,"
                + " #die du nicht so schnell vergessen wirst! #"
                + "Ich habe ein beinahe UNBESIEGBARES Pokemon gefangen! #"
                + "Du hast keine Chance! #HAHAHAHAHA #HOHOHOHO";
        spiel.rivale.dialog2="WAS?! #Das kann nicht sein! #"
                + "Ich habe verloren? #Nach all den Mühen steh ich"
                + " wieder als Verlierer da? #Und ich dachte endlich das"
                + " perfekte Team zusammengestellt zu haben....#"
                + " Hör zu, "+spiel.getSpieler()+", mir reicht es! #Ich gebe "
                + "meine Karriere als Pokemon-Trainer auf... #Ich muss zugeben,"
                + " dass du einfach der bessere bist.. #Und jetzt verpiss dich!";
      }else{
        spiel.rivale.heile();
        spiel.rivale.dialog1="Du schon wieder?! -.-'";
        spiel.rivale.dialog2="Was willst du noch hier? #Verpiss dich!";
      }
    }
    
    public static boolean SoundOn(){
      return soundAn;
    }
    
    public static void Ortwechsel(welt.orte.Ort neu,int x,int y){
      spiel.ortwechsel(neu,x,y);
    }
    
    public static void addListener(OutputListener ol){
      if(isPrinting() && !listener.contains(ol))listener.add(ol);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if(e.getSource().equals(t)){
        if(blink)ausgabe.setText(text+" <");
        else ausgabe.setText(text);
        blink=!blink;
      }else if(e.getSource().equals(verlaufen)){
        spiel.ortwechsel();
        // Hilfemenu
      }else if(e.getSource().equals(gitter)){
        spiel.gitterAn=gitter.getState();
        spiel.repaint();
      }else if(e.getSource().equals(sound)){
        soundAn=sound.getState();
        if(soundAn)Sound.playSound();
        else Sound.stopSound();
      }else if(e.getSource().equals(gr)){
        gr.setEnabled(spiel.größer());
        kl.setEnabled(true);
        pack();
      }else if(e.getSource().equals(kl)){
        kl.setEnabled(spiel.kleiner());
        gr.setEnabled(true);
        pack();
      }else if(e.getSource().equals(spielerpos)){
        sout("X: "+spiel.getSpieler().x+" Y: "+spiel.getSpieler().y);
      }else if(e.getSource().equals(reset)){
        if(kampf==null){
          Start.main(null);
          fenster.dispose();
        }
      }else if(e.getSource().equals(mausAn)){
        if(mausAn.getState())spiel.addMouseListener(spiel);
        else spiel.removeMouseListener(spiel);
      }else if(e.getSource().equals(tastaturAn)){
        if(tastaturAn.getState()) spiel.addKeyListener(spiel);
        else spiel.removeKeyListener(spiel);
      }else if(e.getSource().equals(steuerung)){
        new Steuerung().setVisible(true);
      }
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
      if(javax.swing.JOptionPane.showConfirmDialog(this,"Javamon wirklich beenden?","Javamon beenden",
              javax.swing.JOptionPane.YES_NO_OPTION)==javax.swing.JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
    
    public static Image makeColorTransparent(Image im, final Color color) {
        ImageFilter filter = new RGBImageFilter() {
            // the color we are looking for... Alpha bits are set to opaque
            public int markerRGB = color.getRGB() | 0xFF000000;

            @Override
            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    // Mark the alpha bits as zero - transparent
                    return 0x00FFFFFF & rgb;
                } else {
                    // nothing to do
                    return rgb;
                }
            }
        };

        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }
}