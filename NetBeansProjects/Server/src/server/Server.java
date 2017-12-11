/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dundun
 */
public class Server implements Runnable, WorkerListener {

    private static final Server SERVER = new Server();
    private static Thread SERVER_THREAD;

    private final CopyOnWriteArrayList<Worker> USER;
    private final CopyOnWriteArrayList<ServerListener> LISTENER;
    private ServerSocket serverSocket;

    private Server() {
        USER = new CopyOnWriteArrayList<>();
        LISTENER = new CopyOnWriteArrayList<>();
    }

    public static final Server getInstance() {
        return SERVER;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(Constants.PORT);
            SERVER_THREAD = new Thread(SERVER);
            SERVER_THREAD.start();
            for (ServerListener l : LISTENER) {
                l.onStart();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isRunning() {
        return serverSocket != null && !serverSocket.isClosed() && SERVER_THREAD.isAlive();
    }

    @Override
    public void run() {
        try {
            String host = serverSocket.getInetAddress().getHostAddress() + ":" + serverSocket.getLocalPort();
            sendAll("Server is running on " + host);
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                Worker neu = new Worker(socket, this);
                neu.start();
                addUser(neu);
            }
            close();
        } catch (Exception ex) {
            println("Fehler: " + ex);
        }
    }

    @Override
    public synchronized void receive(Worker source, String text) {
        println(text);
        if (text.startsWith(Constants.REQUEST)) {
            text = text.substring(Constants.REQUEST.length());
            int code = 0;
            if (text.contains(" ")) {
                try {
                    code = Integer.parseInt(text.substring(0, text.indexOf(" ")));
                } catch (Exception ex) {
                }
                text = text.substring(text.indexOf(" ") + 1);
            }
            if (performRequest(source, code, text)) {
                source.send(Constants.ACK);
            } else {
                source.send(Constants.ERROR + Constants.ERROR_UNKNOWN_COMMAND);
            }
        } else if (text.startsWith(Constants.ERROR)) {

        } else if (text.startsWith(Constants.ACK)) {

        } else {
            sendAll(source + ": " + text);
        }
    }

    @Override
    public void fire(Worker a) {
        USER.remove(a);
        sendAll(a + " disconnected");
        for (ServerListener l : LISTENER) {
            l.onUserlistChange();
        }
        sendUserList();
    }

    public synchronized boolean performRequest(Worker source, int code, String params) {
        switch (code) {
            case Constants.REQUEST_NAME:
                source.setArbeiterName(params);
                sendUserList();
                notify();
                return true;
            case Constants.REQUEST_QUIT:
                sendAll(source + " requested to quit");
                close();
                return true;
            default:
                System.err.println("Unknown Request Code: " + code);
                return false;
        }
    }

    public synchronized void addUser(Worker neu) {
        USER.add(neu);
        try {
            wait(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        sendAll(neu + " joined");
        neu.send("Server: Welcome " + neu + "!");
        for (ServerListener l : LISTENER) {
            l.onUserlistChange();
        }
        sendUserList();
    }

    public String[] getUserNames() {
        String[] names = new String[USER.size()];
        for (int i = 0; i < names.length; i++) {
            names[i] = USER.get(i).toString();
        }
        return names;
    }

    public void sendUserList() {
        String cmd = Constants.COMMAND + Constants.C_USERLIST + " ";
        for (String user : getUserNames()) {
            cmd += user + " ";
        }
        sendAll(cmd);
    }

    public void sendAll(String text) {
        println(text);
        for (Worker user : USER) {
            user.send(text);
        }
    }

    public void sendAt(String text, String name) {
        for (Worker dest : USER) {
            if (name.equals(dest.toString())) {
                dest.send(text);
            }
        }
    }

    public void println(String txt) {
        for (ServerListener l : LISTENER) {
            l.println(txt);
        }
        System.out.println(txt);
    }

    public void close() {
        try {
            for (Worker user : USER) {
                user.close();
            }
            serverSocket.close();
            for (ServerListener l : LISTENER) {
                l.onClose();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addServerListener(ServerListener l) {
        LISTENER.add(l);
    }

    public void removeServerListener(ServerListener l) {
        LISTENER.remove(l);
    }
}
