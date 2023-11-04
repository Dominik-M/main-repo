/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package versionsystem;

import java.io.File;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class VersionSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        File f1 = new File("C:\\Users\\Steven\\Documents\\NetBeansProjects\\VersionSystem\\src\\neu\\");
//        File f2 = new File("C:\\Users\\Steven\\Documents\\NetBeansProjects\\VersionSystem\\src\\alt\\");
//        System.out.println(compareFiles(f1,f2));
        graphic.MainFrame.setLookAndFeel();
        new graphic.MainFrame().setVisible(true);
    }

    /**
     * Compares last modified dates of two files and (if they are directorys)
     * their subfiles. d1 seems to be newer either if it contains non-directory
     * files which are newer than same-named files in d2 or it contains files
     * which are not contained in d2 and are newer than d2. d1 seems to be older
     * if d1 contains non-directory files which are older than same-named files
     * in d2. otherwise the directories seem to be equal.
     *
     * @param d1 Directory 1
     * @param d2 Directory 2
     * @return greater than 0 if d1 is newer than d2; less than 0 if d1 is
     * older; 0 if equal.
     */
    public static long compareFiles(File d1, File d2) {
        if (d1.isDirectory()) {
            for (File f1 : d1.listFiles()) {
                boolean exists = false;
                for (File f2 : d2.listFiles()) {
                    if (f1.getName().equals(f2.getName())) {
                        exists = true;
                        long diff = compareFiles(f1, f2);
                        if (diff < 0) {
                            System.out.println(f1 + " ist älter als " + f2);
                            return -1;
                        }
                        if (diff > 0) {
                            //System.out.println("f1.lastModified = "+f1.lastModified()/1000);
                            //System.out.println("f2.lastModified = "+f2.lastModified()/1000);
                            //System.out.println(f1+" ist neuer als "+f2);
                            return 1;
                        }
                        break;
                    }
                }
                if (!exists) {
                    if ((f1.lastModified() / 1000 - d2.lastModified() / 1000) > 0) {
                        //System.out.println(f1+" ist neuer als "+d2);
                        return 1;
                    }
                }
            }
        } else {
            return d1.lastModified() / 1000 - d2.lastModified() / 1000;
        }
        return 0;
    }
}
