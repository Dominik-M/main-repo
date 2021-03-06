package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Worker extends Thread {

    private String name;
    private final Socket socket;
    private final PrintWriter raus;
    private final BufferedReader rein;
    private final WorkerListener server;

    public Worker(Socket s, WorkerListener server) throws Exception {
        socket = s;
        this.server = server;
        rein = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        raus = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        String text;
        while (socket.isConnected()) {
            try {
                text = rein.readLine();
            } catch (Exception ex) {
                System.out.println("Fehler: " + ex);
                text = null;
            }
            if (text == null) {
                break;
            } else {
                server.receive(this, text);
            }
        }
        server.fire(this);
    }

    public void send(String text) {
        raus.println(text);
    }

    public void setArbeiterName(String s) {
        name = s;
        send("Your name was changed to " + s);
    }

    @Override
    public String toString() {
        if (name == null || name.isEmpty()) {
            return "unknown User";
        }
        return name;
    }

    public void close() {
        try {
            socket.close();
            rein.close();
            raus.close();
        } catch (IOException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
