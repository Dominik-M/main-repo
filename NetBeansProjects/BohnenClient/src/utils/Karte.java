/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public abstract class Karte {
    
    private final String NAME;
    
    public Karte(String n){
        NAME=n;
    }
    
    @Override
    public String toString(){
        return NAME;
    }
}
