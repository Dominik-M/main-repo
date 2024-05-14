/*
 * Copyright (C) 2015 Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package utils;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class Vektor {
    public static final double UP=Math.PI/2,LEFT=0,DOWN=3*Math.PI/2,RIGHT=Math.PI,
            LEFT_UP=LEFT+UP/2,LEFT_DOWN=Math.PI*2-UP/2,RIGHT_UP=RIGHT-UP/2,RIGHT_DOWN=RIGHT+UP/2;
    public final int DIMENSION;
    private final double[] values;
    
    public Vektor(double... components){
        DIMENSION=components.length;
        values=new double[DIMENSION];
        System.arraycopy(components, 0, values, 0, values.length);
    }
    
    public double getBetrag(){
        double radikant=0;
        for(double n: values)radikant+=n*n;
        return Math.sqrt(radikant);
    }
    
    public void addVektor(Vektor a){
        if(a.DIMENSION!=this.DIMENSION)
            throw new java.lang.IllegalArgumentException("Required dimension is "+this.DIMENSION+" but the given dimension is "+a.DIMENSION);
        else{
            for(int i=0; i<values.length; i++){
                values[i]+=a.values[i];
            }
        }
    }
    
    public double getX(){
        if(this.DIMENSION<1)throw new java.lang.ArithmeticException("Wrong Dimension");
        else return values[0];
    }
    
    public double getY(){
        if(this.DIMENSION<2)throw new java.lang.ArithmeticException("Wrong Dimension");
        else return values[1];
    }
    
    public double getZ(){
        if(this.DIMENSION<3)throw new java.lang.ArithmeticException("Wrong Dimension");
        else return values[2];
    }
    
    public void rotate(double phi){
        if(DIMENSION==2){
            double x=values[0],y=values[1];
            values[0]=x*Math.cos(phi)-y*Math.sin(phi);
            values[1]=x*Math.sin(phi)+y*Math.cos(phi);
        }
    }
    
    public void rotateAt(Vektor m, double phi){
        if(DIMENSION==m.DIMENSION && DIMENSION==2){
            values[0]=m.getX()+(values[0]-m.getX())*Math.cos(phi)-(values[1]-m.getY())*Math.sin(phi);
            values[1]=m.getY()+(values[0]-m.getX())*Math.sin(phi)+(values[1]-m.getY())*Math.cos(phi);
        }
    }
    
    public double skalarprodukt(Vektor a){
        if(a.DIMENSION!=this.DIMENSION)
            throw new java.lang.IllegalArgumentException("Required dimension is "+this.DIMENSION+" but the given dimension is "+a.DIMENSION);
        else{
            double skalar=0;
            for(int i=0; i<values.length; i++){
                skalar+=values[i]*a.values[i];
            }
            return skalar;
        }
    }
    
    public Vektor orthogonal(){
        if(DIMENSION==2){
            return new Vektor(values[1],-1*values[0]);
        }else throw new java.lang.ArithmeticException("Wrong Dimension.");
    }
    
    public void mul(double faktor){
        for(int i=0; i<values.length; i++)values[i]*=faktor;
    }
    
    public void div(double divisor){
        for(int i=0; i<values.length; i++)values[i]/=divisor;
    }
    
    public static Vektor addition(Vektor v1,Vektor... vectors){
        if(vectors!=null && vectors.length>=1){
            Vektor sum=new Vektor(v1.values);
            for(Vektor v: vectors)sum.addVektor(v);
            return sum;
        }else return v1;
    }
}