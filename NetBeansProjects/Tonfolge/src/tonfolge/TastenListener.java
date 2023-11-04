/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tonfolge;

/**
 *
 * @author Dominik Messerschmidt
 */
public interface TastenListener
{

    public void cursorMoved(int cursor);

    public void keyPress(int ton, int len);

}
