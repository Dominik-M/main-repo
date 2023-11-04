/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Dominik Messerschmidt
 */
public enum Team
{
    NONE, BLUE, RED, GREEN, YELLOW;

    @Override
    public String toString()
    {
        return this.name();
    }

}
