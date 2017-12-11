package mathe;

public class Matrix extends Ausdruck{
    private final double[][] M;
    public final int zeilen,spalten;
    
    public Matrix(int n,int m,double... werte){
        zeilen=n;
        spalten=m;
        M=new double[zeilen][spalten];
        if(werte!=null && werte.length==n){
          for(int i=0;i<M.length;i++){
            for(int k=0;k<M[i].length;k++){
                M[i][k]=werte[i*spalten+k];
            }
          }
        }else{
          for(int i=0;i<M.length;i++){
            for(int k=0;k<M[i].length;k++){
                if(i==k)M[i][k]=1;
                else M[i][k]=0;
            }
          }  
        }
    }
    
    public Matrix(double[][] werte){
        M=new double[werte.length][werte[0].length];
        zeilen=M.length;
        spalten=werte[0].length;
        for(int i=0;i<M.length;i++){
            System.arraycopy(werte[i], 0, M[i], 0, M[i].length);
          }
    }
    
    @Override
    public String toString(){
        return super.toString()+"["+zeilen+"x"+spalten+"]";
    }
    
    public String getName()
    {
        return super.toString();
    }
    
    public double getWert(int zeile,int spalte){
        return M[zeile][spalte];
    }
    
    public void set(int zeile, int spalte, double wert)
    {
        M[zeile][spalte]=wert;
    }
    
    public int getZeilen(){
        return zeilen;
    }
    
    public int getSpalten(){
        return spalten;
    }
    
    public void mal(double skalar){
        for(int i=0;i<M.length;i++){
            for(int k=0;k<M[i].length;k++){
                M[i][k]*=skalar;
            }
        }
    }
    
    public Matrix unterMatrix(int zeile,int spalte){
        if(spalten<=1 || zeilen<=1) return new Matrix(0,0);
        double[][] u=new double[spalten-1][zeilen-1];
        for(int i1=0,i2=0;i1<M.length;i1++){
            if(i1==zeile-1)continue;
            for(int k1=0,k2=0;k1<M[i1].length;k1++){
                if(k1==spalte-1)continue;
                u[i2][k2]=M[i1][k1];
                k2++;
            }
            i2++;
        }
        return new Matrix(u);
    }
    
    public Matrix ersetzeSpalte(Vektor ersatz,int spalte){
        if(ersatz.getDimension()!=zeilen || spalte>spalten)throw new java.lang.IllegalArgumentException("falsche Dimension");
        double[][] neu=new double[zeilen][spalten];
        for(int i=0;i<zeilen;i++)
            for(int j=0;j<spalten;j++)
            if(j==spalte-1) neu[i][j]=ersatz.getWert(i);
            else neu[i][j]=M[i][j];
        return new Matrix(neu);
    }
    
    public double det(){
        if(zeilen<1 || spalten!=zeilen) {
            throw new java.lang.IllegalArgumentException("falsche Dimension");
        }
        if(zeilen==1) return M[0][0];
        if(zeilen==2)return M[0][0]*M[1][1]-M[0][1]*M[1][0];
        double det=0;
        for(int i=0;i<spalten;i++){
           // System.out.println(Mathe.potenz(-1,2+i)+"*"+M[0][i]+"*"+unterMatrix(1,i+1).det());
            det+=(Mathe.potenz(-1,2+i)*M[0][i]*unterMatrix(1,i+1).det());
        }
        return det;
    }
    
    public void print(){
        for(double[] z:M){
            System.out.print('|');
            for(int i=0;i<z.length;i++){
                System.out.print(" "+z[i]+" ");
            }
            System.out.println('|');
        }
    }
    
    public void print(javax.swing.JTextArea out){
        for(double[] z:M){
            out.append("|");
            for(int i=0;i<z.length;i++){
                out.append(" "+z[i]+" ");
            }
            out.append("|\n");
        }
    }
}