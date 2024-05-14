package bohnen;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public enum Bohne {
//    KIDNEYBOHNE("Kidneybohne",6),
    GARTENBOHNE("Gartenbohne",6),
    ROTE_BOHNE("Rote Bohne",8),
    AUGENBOHNE("Augenbohne",10),
//    KAFFEEBOHNE("Kaffeebohne",12),
    SOJABOHNE("Sojabohne",12),
    BRECHBOHNE("Brechbohne",14),
    SAUBOHNE("Saubohne",16),
    FEUERBOHNE("Feuerbohne",18),
//    LIMABOHNE("Limabohne",20),
    BLAUE_BOHNNE("Blaue Bohne",20);    
    
    public final String NAME;
    public final int VALUE;
    
    Bohne(String n,int anz){
        NAME=n;
        VALUE=anz;
    }
    
    public String getFileName(){
        String filename;
        if(NAME.indexOf(" ")>0){
          filename=NAME.substring(0,NAME.indexOf(" ")).toLowerCase()+NAME.substring(NAME.indexOf(" ")+1);
        }else{
            filename=NAME.toLowerCase();
        }
        return "ic_"+filename+".png";
    }
}