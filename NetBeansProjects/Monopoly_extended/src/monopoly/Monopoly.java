package monopoly;

import java.awt.Color;

public class Monopoly {
  // Namen der Strassen und Felder
  public static final String[] NAMEN_STANDART={
      "Los","Badstraße","Gemeinschaftsfeld","Turmstraße","Einkommensteuer","Nordbahnhof",
      "Chausseestraße","Ereignisfeld","Elisenstraße","Poststraße","Gefängnis",
      "Seestraße","Elektrizitätswerk","Hafenstraße","Neue Straße","Westbahnhof",
      "Münchner Straße","Gemeinschaftsfeld","Wiener Straße","Berliner Straße","Frei Parken",
      "Theaterstraße","Ereignisfeld","Museumstraße","Opernplatz","Südbahnhof",
      "Lessingstraße","Schillerstraße","Wasserwerk","Goethestraße","Haftbefehl Junge",
      "Rathausplatz","Hauptstraße","Gemeinschaftsfeld","Bahnhofstraße","Hauptbahnhof",
      "Ereignisfeld","Parkstraße","Zusatzsteuer","Schloßallee"
  };
  public static final String[] NAMEN_NAZI={ 
      "Los","Judengasse","Gemeinschaftsfeld","Synagogenasche","Reichssteuer","Südbahnhof",
      "Juden-weg-Weg","Ereignisfeld","Braune Straße","KZ-Strasse","KZ",
      "Kruppstraße","Krupp-Stahlwerk","Hitlerstraße","Deutsche Straße","Westbahnhof",
      "Münchner Straße","Gemeinschaftsfeld","Wiener Straße","Berliner Straße","Sieg Heil",
      "Arierkeller","Ereignisfeld","Arierweg","Hitlerplatz","Nordbahnhof",
      "Adolf-Hitler-Strasse","Himmlerstraße","Zyklon-B Farm","Goebbelsallee","Haftbefehl Junge",
      "Sportpalast","Panzerstraße","Gemeinschaftsfeld","Bahnhofstraße","Hauptbahnhof",
      "Ereignisfeld","Brandenburger Tor","Propagandazuschlag","Führerbunker"
  };
  public static String[] namen=NAMEN_STANDART;
  static Feld[] felder=new Feld[40];
  static String währung="€";
  
  public static void main(String[] args) {
    new StartPage();
  }
  
  public static Feld get(String name){
    try{
      int n=Integer.parseInt(name);
      if(n<0){ return new Feld("Im "+namen[10],-1,Color.BLACK); }
      return felder[n];
    }
    catch(Exception e){
      for(Feld f: felder){
        if(f.name.equals(name)){ return f; }
      }  
    }
    return null;
  }
}
