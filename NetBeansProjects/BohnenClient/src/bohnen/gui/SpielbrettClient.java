package bohnen.gui;

import bohnen.Bohnanza;
import bohnen.Bohne;
import bohnen.Bohnenkarte;
import bohnen.SpielListener;
import bohnen.Spieler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class SpielbrettClient extends javax.swing.JFrame implements SpielListener, Runnable{
    private Socket socket;
    private PrintWriter raus;
    private BufferedReader rein;
    private final Spieler[] alleSpieler;
    private final int mSpielerIndex;

    /**
     * Creates new form SpielbrettClient
     */
    public SpielbrettClient() {
        initComponents();
        game.setListener(this);
        alleSpieler=new Spieler[]{new Spieler("Peter"),new Spieler("Hans"),new Spieler("Karl")};
        mSpielerIndex=0;
    }
    
    public SpielbrettClient(Socket connection, PrintWriter writer, BufferedReader reader,int index, String... names){
        initComponents();
        game.setListener(this);
        socket=connection;
        raus=writer;
        rein=reader;
        alleSpieler=new Spieler[names.length];
        alleSpieler[0]=new Spieler(names[index]);
        for(int i1=0,i2=1; i1<names.length; i1++){
            if(i1!=index){
              alleSpieler[i2]=new Spieler(names[i1]);
              i2++;
            }
        }
        mSpielerIndex=index;
        spieler0.setText(alleSpieler[0].toString());
        spieler1.setText(alleSpieler[1].toString());
        spieler2.setText(alleSpieler[2].toString());
        new Thread(this).start();
    }
//    
//    public static void main(String[] args){
//        new SpielbrettClient().setVisible(true);
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        game = new bohnen.gui.GamePanel();
        spieler0 = new javax.swing.JLabel();
        spieler1 = new javax.swing.JLabel();
        spieler2 = new javax.swing.JLabel();
        next = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        output = new javax.swing.JTextArea();
        input = new javax.swing.JTextField();
        send = new javax.swing.JButton();
        amzug = new javax.swing.JLabel();
        phaseLabel = new javax.swing.JLabel();
        coins0 = new javax.swing.JLabel();
        coins1 = new javax.swing.JLabel();
        coins2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        spieler0.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        spieler0.setText("Spieler 0");

        spieler1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        spieler1.setText("Spieler 1");

        spieler2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        spieler2.setText("Spieler 2");

        next.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        next.setText("Weiter");
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });

        output.setColumns(20);
        output.setRows(5);
        jScrollPane1.setViewportView(output);

        input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputActionPerformed(evt);
            }
        });

        send.setText("Senden");
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed(evt);
            }
        });

        amzug.setText("Am Zug: Peter");

        phaseLabel.setText("Phase: 0 (Muss noch Karten ablegen)");

        coins0.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        coins0.setText("Coins: 25");

        coins1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        coins1.setText("Coins: 25");

        coins2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        coins2.setText("Coins: 25");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(spieler1)
                    .addComponent(coins1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(input)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(send))
                    .addComponent(jScrollPane1)
                    .addComponent(game, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spieler2)
                    .addComponent(coins2))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(463, Short.MAX_VALUE)
                .addComponent(spieler0)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(coins0)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(next)
                .addGap(18, 18, 18)
                .addComponent(amzug)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(phaseLabel)
                .addGap(319, 319, 319))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(game, javax.swing.GroupLayout.PREFERRED_SIZE, 567, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(245, 245, 245)
                                .addComponent(spieler1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(coins1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(247, 247, 247)
                                .addComponent(spieler2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(coins2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spieler0)
                    .addComponent(next)
                    .addComponent(amzug)
                    .addComponent(phaseLabel)
                    .addComponent(coins0))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(send)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(socket!=null)
            disconnect();
    }//GEN-LAST:event_formWindowClosing

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed
        send("next");
    }//GEN-LAST:event_nextActionPerformed

    private void sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendActionPerformed
        String in=input.getText();
        input.setText("");
        send(in);
    }//GEN-LAST:event_sendActionPerformed

    private void inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputActionPerformed
        sendActionPerformed(evt);
    }//GEN-LAST:event_inputActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel amzug;
    private javax.swing.JLabel coins0;
    private javax.swing.JLabel coins1;
    private javax.swing.JLabel coins2;
    private bohnen.gui.GamePanel game;
    private javax.swing.JTextField input;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton next;
    private javax.swing.JTextArea output;
    private javax.swing.JLabel phaseLabel;
    private javax.swing.JButton send;
    private javax.swing.JLabel spieler0;
    private javax.swing.JLabel spieler1;
    private javax.swing.JLabel spieler2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void ende() {}

    @Override
    public void nextTurn(Spieler onTurn) {
        amzug.setText("Am Zug: "+onTurn);
    }

    @Override
    public void karteMoved(Object source,Bohnenkarte k, String from, String to) {
        if (game.equals(source)) {
            if(to.contains(SpielListener.FELD_POSTFIX)){
              if(from.endsWith(SpielListener.HAND_POSTFIX)){
                send("drop"+SEPARATOR+to.substring(FELD_POSTFIX.length()+to.indexOf(FELD_POSTFIX)));
              }else if(from.endsWith(ABLAGE_POSTFIX)){
                  send("dropa0"+SEPARATOR+to.substring(FELD_POSTFIX.length()+to.indexOf(FELD_POSTFIX)));
              }
            }else if(from.contains(STACK) && to.startsWith("0")){
                send("pull");
            }else if(from.contains(FELD_POSTFIX) && to.contains(ABLAGE)){
                send("ernte"+from.substring(from.indexOf(FELD_POSTFIX)+FELD_POSTFIX.length()));
            }
            System.out.println("move" + k.SORTE.name() + "from" + from + "to" + to);
        } else {
            // convert names to indices
            for (int i = 0; i < alleSpieler.length; i++) {
                String name = alleSpieler[i].toString();
                if (from.startsWith(name)) {
                    from = from.replaceFirst(name, i + "");
                }
                if (to.startsWith(name)) {
                    to = to.replaceFirst(name, i + "");
                }
            }
            game.karteMoved((Bohnenkarte) k, from, to);
        }
    }
    
    public void send(String text){
    if(socket!=null && !socket.isClosed())
      raus.println(text);
    }
    
    public void receive(String text){
    //    System.out.println(text);
        if(text.startsWith("#move")){
            int index=5,nextIndex=text.indexOf("from",index);
            String teil=text.substring(index, nextIndex);
            Bohnenkarte k=new Bohnenkarte(Bohne.valueOf(teil));
            index=nextIndex+4;
            nextIndex=text.indexOf("to", index);
            teil=text.substring(index, nextIndex);
            String from=teil;
            index=nextIndex+2;
            String to=text.substring(index);
            karteMoved(this,k,from,to);
        }else if(text.startsWith("#onturn")){
            nextTurn(new Spieler(text.substring(7)));
        }else if(text.startsWith("#stack")){
            int index=6,nextIndex;
            String teil;
            while(index<text.length()-2){
                nextIndex=text.indexOf(";",index);
                teil=text.substring(index, nextIndex);
                Bohnenkarte k=new Bohnenkarte(Bohne.values()[Integer.parseInt(teil)]);
                game.karteMoved(k, "irgendwo", STACK);
                index=nextIndex+1;
            }
        }else if(text.startsWith("#phase")){
            nextPhase(Integer.parseInt(text.substring(6)));
        }else if(text.startsWith("#coins")){
            coinsAdded(new Spieler(text.substring(6, text.indexOf(SEPARATOR))),Integer.parseInt(text.substring(text.indexOf(SEPARATOR)+SEPARATOR.length())));
        }else if(text.startsWith("#end")){
            if(text.length()==4){
                javax.swing.JOptionPane.showMessageDialog(this, "Verbindung wurde unterbrocken", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }else{
                javax.swing.JOptionPane.showMessageDialog(this, "Spiel beendet. "+text.substring(4)+" hat gewonnen!");
                dispose();
            }
        }else if(!Bohnanza.DEBUG){
            println(text);
        }
        if(Bohnanza.DEBUG){
            println(text);
        }
    }
    
    @Override
    public void println(String text){
        output.append(text+"\n");
    }

    private void disconnect() {
        try {
            socket.close();
            raus.close();
            rein.close();
        } catch (IOException ex) {
            Logger.getLogger(SpielbrettClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        send("ready");
        while(socket!=null && !socket.isClosed()){
            try {
                String msg=rein.readLine();
                receive(msg);
            } catch (IOException ex) {
                Logger.getLogger(SpielbrettClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        dispose();
    }

    @Override
    public void nextPhase(int phase) {
        String hint="";
        switch(phase){
            case Bohnanza.ABLEGPHASE_0: case Bohnanza.HANDELPHASE_1:
                hint="(Muss noch Karten ablegen)";
                break;
            case Bohnanza.ABLEGPHASE_1:
                hint="(Kann noch Karten ablegen)";
                break;
            case Bohnanza.HANDELPHASE_0: case Bohnanza.ZIEHPHASE:
                hint="(Muss noch Karten ziehen)";
                break;
        }
        phaseLabel.setText("Phase: "+phase+" "+hint);
    }

    @Override
    public void coinsAdded(Spieler to, int anz) {
        if(to.toString().equals(alleSpieler[0].toString())){
            coins0.setText("Coins: "+anz);
        }else if(to.toString().equals(alleSpieler[1].toString())){
            coins1.setText("Coins: "+anz);
        }else if(to.toString().equals(alleSpieler[2].toString())){
            coins2.setText("Coins: "+anz);
        }
    }
}
