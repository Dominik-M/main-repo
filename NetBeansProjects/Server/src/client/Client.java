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
import java.util.LinkedList;
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
    private final LinkedList<ClientListener> LISTENER = new LinkedList<>();

    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    public void addClientListener(ClientListener l) {
        LISTENER.add(l);
    }

    public void removeClientListener(ClientListener l) {
        LISTENER.remove(l);
    }

    public boolean connect(String name, String adress, int port) {
        try {
            println("Verbindung wird hergestellt...");
            socket = new Socket(adress, port);
            zumserver = new PrintWriter(socket.getOutputStream(), true);
            vomserver = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        for (ClientListener l : LISTENER) {
            l.onConnect();
        }
        return true;
    }

    public void disconnect() {
        try {
            if (isConnected()) {
                socket.close();
                zumserver.close();
                vomserver.close();
                socket = null;
                for (ClientListener l : LISTENER) {
                    l.onDisconnect();
                }
                println("Verbindung unterbrochen.");
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public synchronized void run() {
        if (isConnected()) {
            println("verbunden mit " + socket.toString());
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
                println(text);
                if (text == null) {
                    println("Server offline");
                    disconnect();
                    break;
                } else if (text.startsWith(Constants.USERLIST_COMMAND)) {
                    text = text.substring(Constants.USERLIST_COMMAND.length());
                    names = text.split(" ");
                    for (ClientListener l : LISTENER) {
                        l.onUserlistChanged();
                    }
                } else if (text.startsWith(Constants.ACK)) {
                    noAck = false;
                    notify();
                } else /* if (!sound.Sound.isPlaying())*/ {
                    sound.Sound.playRandomSound();
                } // println(text);
            }
        } else {
            println("Es konnte keine Verbindung hergestellt werden.");
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
                for (int i = 0; i < timeout / 10 && noAck; i++) {
                    wait(10);
                }
            } catch (Exception ex) {
            }
            return !noAck;
        }
        return false;
    }

    public boolean setUserName(String name) {
        //this.name = name;

        return sendAndWaitOnAck(Constants.REQUEST + Constants.REQUEST_NAME + " " + name, 20000);
    }

    public String[] getUsers() {
        return names;
    }

    public void println(String txt) {
        for (ClientListener l : LISTENER) {
            l.println(txt);
        }
        System.out.println(Thread.currentThread().getName() + ": " + txt);
    }
}
