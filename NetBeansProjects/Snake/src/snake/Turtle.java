package snake;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

/** Diese Klasse verwaltet eine Liste von geradlinigen Bewegungen, die
  * mit oder ohne Zeichnung einer Spur zurück gelegt werden. Als Zustände
  * werden die aktuelle Position, die aktuelle Richtung, sowie aktuelle
  * Strichdicke und Strichfarbe gespeichert.
  * Die Angabe von Winkeln erfolgt durchgängig im Gradmaß, wobei ein Winkel
  * von 0&deg; der Richtung der positiven x-Achse entspricht.
  * 
  * Da die Klasse für den Schuleinsatz optimiert ist, hat sie ein paar seltsame
  * Eigenschaften. Insbesondere die statische Verwaltung mehrerer Turtles und
  * deren Anzeige in einem gemeinsamen statischen JFrame ist hier zweckmäßig aber
  * sehr ungewöhnlich.
  * 
  * Die Befehlsnamen wurden in möglichst naher historischer Anlehnung englisch
  * belassen.
  * 
  * @author franz.beslmeisl@gmail.com
  */
public class Turtle{
  /** Alle Turtle-Instanzen werden in diesem einzigen Fenster angezeigt. */
  /*private final*/static final JFrame anzeige;
  /** Die Menge aller jeweils erzeugten Turtle-Instanzen. */
  private static LinkedList<Turtle> turtles;
  /** Die Abbildungsmatrix für Maßstab, Verschiebung und korrekte Achsenausrichtung.*/
  private static AffineTransform trans;

  /**Dicke, Farbe und Endposition eines zu zeichnenden geraden Striches.
   * Sichtbar ist ein Schritt nur, wenn sein stroke nicht null ist.
   * Instanzen dieser Klasse werden nie außerhalb gesehen. Die Attribute sind
   * deshalb alle private, aber von hier innen zugreifbar.
   */
  private static class Schritt{
    private BasicStroke stroke;
    private Color farbe;
    private double newx, newy;

    private Schritt(double x, double y, BasicStroke s, Color f){
      newx=x;
      newy=y;
      stroke=s;
      farbe=f;
    }

    private Schritt(double x, double y){
      this(x, y, null, null);
    }
  }//---------- Ende von Schritt ----------

  static{// Hier wird das einzige Hauptfenster für alle Turtles erzeugt.
    turtles=new LinkedList<Turtle>();
    trans=new AffineTransform(1.0, 0, 0, -1.0, 0, 0);
    anzeige=new JFrame();
    anzeige.getContentPane().add(new JPanel(){
      private Line2D.Double line=new Line2D.Double();

      @Override
      public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2=(Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setTransform(trans);
        for(Turtle turtle: turtles){
          double x, y; x=y=0;
          synchronized(turtle.pfad){//verhindere asynchrone Veränderung
            for(Schritt s: turtle.pfad){
              line.setLine(x, y, x=s.newx, y=s.newy);
              if(s.stroke!=null){
                g2.setStroke(s.stroke);
                g2.setColor(s.farbe);
                g2.draw(line);
              }
            }
          }
        }
      }
    }, BorderLayout.CENTER);
    anzeige.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    anzeige.setSize(800, 600);
    //anzeige.setExtendedState(JFrame.MAXIMIZED_BOTH);
    //anzeige.setVisible(true);
    int w=anzeige.getWidth();
    int h=anzeige.getHeight();
    setView(w/2, -h/2, 1);
  }

  /**Skaliert das Bild auf das zoom-fache und verschiebt es danach so, dass
  * der Ursprung nicht mehr links oben liegt, sondern um mx weiter rechts
  * und um my weiter oben.
  */
  public static void setView(double mx, double my, double zoom){
    trans.setTransform(zoom,0,0,-zoom,mx,-my);
    anzeige.repaint();
  }

  /**Zeigt von allen Turtles den aktuellen Zustand in einem gemeinsamen Fenster
   * an. Aus Effizienzgründen wird nicht nach jedem Turtleschritt die Anzeige auf
   * den neuesten Stand gebracht. Vielmehr entscheidet der Programmierer, an
   * welcher Stelle seines Turtle-Programms diese Methode aufgerufen wird.
   */
  public static void show(){
    anzeige.setVisible(true);
    anzeige.repaint();
  }
  //------------------- Ende des ungewöhnlichen static-Teils -------------------

	/**Wartet eine bestimmte Zeit. Diese Methode kann aufgerufen werden, wenn
   * die Schritte einer Turtle verzögert dargestellt werden sollen.
   * @param millis Anzahl der zu wartenden Millisekunden
   */
  public void sleep(int millis){
		try{
			Thread.sleep(millis);
		} catch(InterruptedException ex){}
	}

  /** Die Liste der Schritte. Sie startet stets mit einem move.*/
  private final List<Schritt> pfad;
  /** Die aktuelle Position der Turtle.*/
  double x0, y0;
  /** Die aktuelle Blickrichtung der Turtle.*/
  double grad0;
  /** Die aktuelle Farbe der Turtle.*/
  private Color farbe;
  /** Die aktuelle Strichart der Turtle.*/
  private BasicStroke stroke;

  /**Erzeugt eine neue Turtle an Position (0|0), die nach Osten schaut. */
  public Turtle(){
    pfad=Collections.synchronizedList(new LinkedList<Schritt>());
    clear();
    turtles.add(this);
  }

  @Override
  public String toString(){
    return "("+x0+"|"+y0+"); "+grad0+"°; Farbe: "+farbe;
  }

  /**Löscht alle Striche dieser Turtle und initialisiert sie.
   * @param x der neue Rechtswert der Position
   * @param y der neue Hochwert der Position
   * @param grad die neue Blickrichtung gemessen von Osten gegen den Uhrzeigersinn
   */
  public final void clear(double x, double y, double grad){
    pfad.clear();
    pfad.add(new Schritt(x, y));
    x0=x;
    y0=y;
    grad0=grad;
    farbe=Color.BLACK;
    setPen(1f);
  }

  /**Löscht alle Striche dieser Turtle, setzt sie in den Ursprung und lässt
   * sie nach Osten schauen.
   */
  public final void clear(){clear(0,0,0);}

  /**Setzt die Strichdicke. Diese gilt so lange, bis eine neue gesetzt wird.
   * @param d die Dicke in Pixeln aller folgenden line-Befehle
   */
  public void setPen(double d){
    if(d<=0) stroke=null;
    else
      stroke=new BasicStroke((float)d, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
  }

  /**Setzt die Farbe. Diese gilt so lange, bis eine neue gesetzt wird.
   * @param f die Farbe aller folgenden line-Befehle
   */
  public void setColor(Color f){farbe=f;}

  /**Bewegt die Turtle vorwärts in die aktuelle Blickrichtung, ohne eine Linie
   * zu zeichnen.
   * @param d die zurückzulegende Distanz. Diese unterliegt dem Abbildungsmaßstab.
   */
  public void move(double d){
    double dir=Math.toRadians(grad0);
    moveTo(x0+(double)(d*Math.cos(dir)), (double)(y0+d*Math.sin(dir)));
  }

  /**Bewegt die Turtle vorwärts in die aktuelle Blickrichtung, und zeichnet dabei
   * eine Linie.
   * @param d die zurückzulegende Distanz. Diese unterliegt dem Abbildungsmaßstab.
   */
  public void line(double d){
    double dir=Math.toRadians(grad0);
    lineTo((double)(x0+d*Math.cos(dir)), (double)(y0+d*Math.sin(dir)));
  }

  /**Dreht die Turtle.
   * @param grad Drehwinkel in Grad gegen den Uhrzeigersinn.
   */
  public void turn(double grad){
    grad0+=grad;
    grad0%=360;
  }

  /**Bewegt die Turtle an eine bestimmte absolute Stelle, ohne zu zeichnen.
   * @param x der Rechtswert der neuen Position
   * @param y der Hochwert der neuen Position
   */
  public void moveTo(double x, double y){
    pfad.add(new Schritt(x, y));
    goTo(x, y);
  }

  /**Bewegt die Turtle an eine bestimmte absolute Stelle und zeichnet dabe eine
   * Linie.
   * @param x der Rechtswert der neuen Position
   * @param y der Hochwert der neuen Position
   */
  public void lineTo(double x, double y){
    pfad.add(new Schritt(x, y, stroke, farbe));
    goTo(x, y);
  }

  /**Setzt die absolute Blickrichtung der Turtle.
   * @param grad neuer Blickwinkel in Grad gemessen ab Osten gegen den Uhrzeigersinn.
   */
  public void turnTo(double grad){grad0=grad;}

  /**Dreht die Turtle so, dass sie in Richtung einer bestimmten Position blickt.
   * @param x der Rechtswert der Position, zu der die Turtle blicken soll
   * @param y der Hochwert der Position, zu der die Turtle blicken soll
   */
  public void turnTo(double x, double y){
    if(x==x0 && y==y0) return;
    grad0=Math.toDegrees(Math.atan2(y-y0, x-x0));
  }

  /**Dreht die Turtle so, dass sie in Richtung einer bestimmten Position blickt
   * und bewegt sie zu dieser Position.
   * @param x der Rechtswert der neuen Position
   * @param y der Hochwert der neuen Position
   */
  private void goTo(double x, double y){
    if(x==x0 && y==y0) return;
    grad0=Math.toDegrees(Math.atan2(y-y0, x-x0));
    x0=x; y0=y;
  }

//------------------- Der folgende Bereich ist nur ein Beispiel -------------
  //==================== Befehle für einzelne Turtles =====================
  // clear()     löscht alle Striche und guckt nach Osten
  // move(s)     geht s weit in die aktuelle Richtung ohne zu zeichnen
  // line(s)     zeichnet eine s lange Linie in die aktuelle Richtung
  // turn(g)     dreht sich um g° gegen den Uhrzeigersinn (g<0 im Uhrzeigersinn)
  // setPen(d)   stellt die Stiftdicke auf die neue int d ein
  // setColor(f) stellt den Stiftfarbe auf die neue Color f ein
  // moveTo(x,y) macht Richtungswechsel und geht zur angegebenen Position
  // lineTo(x,y) macht Richtungswechsel und zeichnet zur angegebenen Position
  // turnTo(d)   guckt in die neue Richtung d (d=0 entspricht Osten)
  // turnTo(x,y) guckt in die Richtung der Koordinaten (x,y)
  // sleep(ms)   wartet ms Millisekunden lang ohne was zu tun
  //================= Befehle für alle Turtles zusammen ===================
  // Turtle.show()      Neudarstellung nach Änderungen
  public static void main(String[] args){
    Turtle erika=new Turtle();
    for(int i=0; i<60; i++){
      erika.moveTo(0, 0);
      erika.turnTo(90+i*6);
      erika.move(100);
      if(i%5==0){
        erika.setPen(2);
        erika.line(15);
      } else{
        erika.setPen(1);
        erika.line(10);
      }
    }
    Turtle.show();
    erika.sleep(1000);
    Turtle susi=new Turtle();// zweite Turtle im gleichen Bild ist kein Problem
    susi.setColor(Color.ORANGE);
    susi.setPen(12);
    susi.line(80);
    Turtle.show();
  }
}