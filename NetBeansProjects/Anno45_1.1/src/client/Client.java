/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dundun
 */
public class Client extends Thread {

    /**
     * Default Port number
     */
    public static final int PORT = 52056;

    private boolean noAck = false;
    private String[] names;
    private Socket socket;
    private BufferedReader vomserver;
    private PrintWriter zumserver;

    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    public boolean connect(String name, String adress, int port) {
        try {
            System.out.println("Verbindung wird hergestellt...");
            socket = new Socket(adress, port);
            zumserver = new PrintWriter(socket.getOutputStream(), true);
            vomserver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            sleep(100);
            setUserName(name);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public void disconnect() {
        try {
            socket.close();
            zumserver.close();
            vomserver.close();
            socket = null;
            System.out.println("Verbindung unterbrochen.");
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void run() {
        if (isConnected()) {
            System.out.println("verbunden mit " + socket.toString());
            while (socket != null && !socket.isClosed()) {
                String text;
                try {
                    text = vomserver.readLine();
                } catch (java.net.SocketException ex) {
                    disconnect();
                    break;
                } catch (Exception ex) {
                    System.err.println("Fehler: " + ex);
                    continue;
                }
                System.out.println(text);
                if (text == null) {
                    System.out.println("Server offline");
                    disconnect();
                    break;
                } else if (text.startsWith(Constants.USERLIST_COMMAND)) {
                    text = text.substring(Constants.USERLIST_COMMAND.length());
                    names = text.split(" ");
                    break;
                } else if (text.startsWith(Constants.ACK)) {
                    notify();
                } else {
                    // System.out.println(text);
                }
            }
        } else {
            System.out.println("Es konnte keine Verbindung hergestellt werden.");
        }
    }

    public boolean send(String text) {
        if (isConnected()) {
            zumserver.println(text);
            return true;
        }
        return false;
    }

    public synchronized boolean sendAndWaitOnAck(String text, int timeout) {
        if (send(text)) {
            try {
                noAck = true;
                wait(timeout);
            } catch (Exception ex) {
            }
            return !noAck;
        }
        return false;
    }

    public boolean setUserName(String name) {
        if (sendAndWaitOnAck(Constants.REQUEST + Constants.REQUEST_NAME + " " + name, 2000)) {
            //this.name = name;
            return true;
        }
        return false;
    }

    public String[] getUsers() {
        return names;
    }
}
