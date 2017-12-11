package mathe;

public class Vektor extends Matrix{
    
    public Vektor(double... werte){
        super(werte.length,1,werte);
    }
    
    public int getDimension(){
        return getZeilen();
    }
    
    public double getWert(int zeile){
        return getWert(zeile,0);
    }
    
    public double getBetrag(){
        double b=0;
        for(int i=0;i<getDimension();i++){
            b+=getWert(i)*getWert(i);
        }
        return Mathe.wurzel(2, b);
    }
    
    public Vektor kreuzprodukt(Vektor b){
        if(this.getZeilen()==3 && b.getZeilen()==3){
            return new Vektor(
                    this.getWert(1)*b.getWert(2)-this.getWert(2)*b.getWert(1),
                    this.getWert(2)*b.getWert(0)-this.getWert(0)*b.getWert(2),
                    this.getWert(0)*b.getWert(1)-this.getWert(1)*b.getWert(0)
                    );
        }else {
            return null;
        }
    }
}