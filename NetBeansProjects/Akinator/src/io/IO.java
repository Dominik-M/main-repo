/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

/**
 *
 * @author Dominik
 */
public class IO {
    private static MainFrame aktFrame;
    
    public static void initIO()
    {
        setLookAndFeel();
        aktFrame = new MainFrame();
        aktFrame.setVisible(true);
    }
    
    public static void print(String text)
    {
        aktFrame.println(text);
    }
    
    public static void clear()
    {
        aktFrame.clearOutput();
    }
    
    public static int getAntwort()
    {
        int antwort;
        antwort = aktFrame.getAnwort();
        print("-"+akinator.Akinator.ANTWORTEN[antwort]);
        return antwort;
    }
    
    public static String getName()
    {
        String name;
        name = aktFrame.getInput();
        print("-"+name);
        return name;
    }
    
    public static String getFrage()
    {
        String frage;
        frage = aktFrame.getInput();
        print("-"+frage);
        return frage;
    }
    
    public static void setLookAndFeel() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }
}