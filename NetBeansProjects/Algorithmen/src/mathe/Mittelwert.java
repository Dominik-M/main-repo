/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathe;

/**
 *
 * @author Dominik
 */
public class Mittelwert {
    private int[] werte;
    
    public Mittelwert(int anz){
        werte = new int[anz];
    }
    
    public int[] getWerte(){
        return werte;
    }
    
    public void setWerte(int[] neuWerte){
        werte = neuWerte.clone();
    }
    
    public float getMittelwert(){
        int sum = 0;
        for(int x:werte)
            sum += x;
        return (float)sum / werte.length;
    }
    
    public void printWerte(){
        for(int i=0; i<werte.length; i++){
            System.out.print(werte[i]+" ");
        }
        System.out.println("");
    }
    
    public static void main(String[] args){
        Mittelwert m = new Mittelwert(5);
        int[] meinewerte = {1,2,3,4};
        m.setWerte(meinewerte);
        m.printWerte();
        meinewerte[1] = 5;
        m.printWerte();
        System.out.println("Mittelwert = "+m.getMittelwert());
    }
}
