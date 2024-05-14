/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Dominik Messerschmidt
 */
public interface ServerListener {

    public void onStart();

    public void onClose();

    public void onUserlistChange();

    public void println(String txt);
}
