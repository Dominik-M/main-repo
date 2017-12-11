package programm;

public class Main {

    public static void main(String[] args) {
        new MainMenu(new java.awt.Point(200, 100));
//        for(int i=2; i<=100; i++)
//        {
//            if(mathe.Mathe.isQuadratzahl(i))
//            {
//                int wurzel = (int) Math.sqrt(i);
//                System.out.println(i+" ist Qudratzahl von "+wurzel);
//            }
//        }
//        System.out.println(mathe.Mathe.pythTripleProduct(1000));
//        int[] primfaktoren = mathe.Mathe.primfaktoren(13860);
//        System.out.println("");
//        for(int i=0; i<primfaktoren.length; i++){
//            System.out.print(primfaktoren[i]+" ");
//        }
    }

    /**
     * Transforms each character of a String to a binary value.
     *
     * @param s a String
     * @return A String of the binary values separated with \n
     */
    public static String binString(String s) {
        byte[] bytes = s.getBytes();
        String bin = "";
        for (byte b : bytes) {
            bin = bin + binString(b) + "\n";
        }
        return bin;
    }

    /**
     * Transforms a byte value to a String of '0' and '1'.
     *
     * @param b a byte
     * @return a String representing the byte value.
     */
    public static String binString(Byte b) {
        String bin = Integer.toBinaryString(b);
        int i = 8 - bin.length();
        while (i > 0) {
            bin = "0" + bin;
            i--;
        }
        bin = bin.substring(0, 4) + " " + bin.substring(4);
        return bin;
    }

    public static int anzahlDosen(int h) {
        return h * (h + 1) / 2;
    }

    public static int pyramidenHöhe(int n) {
        return (int) (Math.sqrt(2 * n + 0.25) - 0.5);
    }

    /**
     * Versucht für alle Tage und Monate (1.1.-31.12.) eine Kombination der
     * Zahlen durch Addition, Subtraktion, Multiplikation oder Division zu
     * finden, dessen Ergebnis die Jahreszahl ist.
     *
     * @param jahr Die Jahreszahl, die die Rechnungen ergeben sollen.
     * @return die Anzahl der Daten, bei denen eine Kombinationsmöglichkeit
     * gegeben ist.
     */
    public static int datenRechnung(int jahr) {
        int anz = 0;
        int tag = 1, monat = 1;
        konstanten.Monat[] monate = konstanten.Monat.values();
        while (monat <= monate.length) {
            int add = tag + monat;
            int sub = tag - monat;
            int mul = tag * monat;
            int div = tag / monat;
            System.out.println(tag + " + " + monat + " = " + add);
            System.out.println(tag + " - " + monat + " = " + sub);
            System.out.println(tag + " * " + monat + " = " + mul);
            System.out.println(tag + " / " + monat + " = " + div);
            if (((add) == jahr)
                    || ((sub) == jahr)
                    || ((mul) == jahr)
                    || ((div) == jahr)) {
                anz++;
            }
            tag++;
            if (tag > monate[monat - 1].TAGE) {
                tag = 1;
                monat++;
            }
        }
        return anz;
    }
}
