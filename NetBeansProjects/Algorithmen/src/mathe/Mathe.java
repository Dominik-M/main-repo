package mathe;

public class Mathe {

    public static final double PI = 3.14159265358979323846;
    public static final double E = 2.7182818284590452354;

    public static double potenz(double basis, int exponent) {
        double potenz = 1;
        for (; exponent > 0; exponent--) {
            potenz = potenz * basis;
        }
        for (; exponent < 0; exponent++) {
            potenz = potenz / basis;
        }
        return potenz;
    }

    public static double wurzel(int n, double r) {
        if (r <= 0) {
            return 0;
        }
        double wurzel = 1;
        int i = 0;
        while (i >= -15 && potenz(wurzel, n) != r) {
            if (potenz(wurzel, n) < r) {
                wurzel += potenz(10, i);
            } else {
                wurzel -= potenz(10, i);
                i--;
            }
        }
        return wurzel;
    }

    public static int[] primfaktoren(int zahl) {
        int anz = 0;
        int[] prims = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
        int index = 0;
        java.util.LinkedList<Integer> faktoren = new java.util.LinkedList<>();
        
        System.out.print("Teiler:");
        while (zahl > 1) {
            if (index >= prims.length) {
                System.err.println("Die Primzahlen haben nicht ausgereicht.");
                break;
            }
            while ((zahl % prims[index]) == 0) {
                anz++;
                if (anz == 1) {
                    faktoren.add(prims[index]);
                }
                zahl /= prims[index];
            }
            if (anz > 0) {
                System.out.print(" " + prims[index] + " hoch " + anz);
                anz = 0;
            }
            index++;
        }
        int[] faktorArray = new int[faktoren.size()];
        for (int i = 0; i < faktorArray.length; i++) {
            faktorArray[i] = faktoren.get(i);
        }
        return faktorArray;
    }

    /**
     * berechnet die kumulierte Binomialverteilung
     *
     * @param n Anzahl der Wiederholungen
     * @param p Trefferwahrscheinlichkeit
     * @param k1 minimale Anzahl an Treffern
     * @param k2 maximale Anzahl an Treffern
     * @return F(n,p,k2)-F(n,p,k1)
     */
    public static double kBV(int n, double p, int k1, int k2) {
        double summe = 0;
        for (int i = k1; i <= k2; i++) {
            summe += binomial(n, i) * potenz(p, i) * potenz(1 - p, n - i);
        }
        return summe;
    }

    /**
     * Der Binomialkoeffizient von n über k
     *
     * @param n
     * @param k
     * @return n über k
     */
    public static double binomial(int n, int k) {
        return (fakultät(n) / (fakultät(n - k) * fakultät(k)));
    }

    public static double fakultät(int n) {
        double f = 1;
        for (int i = 1; i <= n; i++) {
            f = f * i;
        }
        return f;
        /* rekursiv:
         * if(n<=1)return 1;
         * return n*fakultät(n-1);
         */
    }

    public static double gaußFunktion(int n, double p, int k) {
        double sigma = wurzel(2, n * p * (1.0 - p));
        double z = (k - n * p) / sigma;
        return 1 / (sigma * wurzel(2, 2 * PI)) * potenz(E, Integer.parseInt(Math.round(-0.5 * z * z) + ""));
    }

    public static int fibonacci(int n) {
        if (n <= 1) {
            return 1;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static Vektor solve(Matrix koeff, Vektor lsg) {
        double det = koeff.det();
        double[] x = new double[lsg.getDimension()];
        for (int zeile = 1; zeile <= lsg.getDimension(); zeile++) {
            Matrix ersatz = koeff.ersetzeSpalte(lsg, zeile);
          //ersatz.print();
            // System.out.println(ersatz.det());
            x[zeile - 1] = ersatz.det() / det;
        }
        return new Vektor(x);
    }

    /**
     * Algorithmus zur Bestimmung des größten gemeinsamen Teilers nach Euklid.
     *
     * @param u eine Zahl
     * @param v eine andere Zahl
     * @return ggT von u und v
     */
    public static int ggT(int u, int v) {
        if (u == v) {
            return u;
        }
        if (u > v) {
            return ggT(v, u - v);
        } else {
            return ggT(u, v - u);
        }
    }

    public static void gibGekürzt(int zähler, int nenner) {
        int ggt = ggT(zähler, nenner);
        System.out.println(zähler + "/" + nenner + " = " + zähler / ggt + "/" + nenner / ggt);
    }

    public static int querSumme(int n) {
        int sum = 0;
        while (n != 0) {
            sum += n % 10;
            n = n / 10;
        }
        return sum;
    }

    public static double sin(double degrees) {
        double sin = degrees / 180 * PI;
        return sin;
    }

    public static int pythTripleProduct(int sum) {
        int a = 3, b = 4, c = a * a + b * b;
        c = (int) Math.sqrt(c);
        while (a + b + c != sum && b < 500) {
            do {
                a++;
                if (a >= b) {
                    b++;
                    a = 1;
                }
                c = a * a + b * b;
            } while (!isQuadratzahl(c));
            c = (int) Math.sqrt(c);
        }
        if (a + b + c == sum) {
            System.out.println(a + ", " + b + ", " + c + " ist das Pythagorean triplet von " + sum);
            return a * b * c;
        }
        System.out.println("Keine Lösung");
        return -1;
    }

    public static boolean isQuadratzahl(double quadrat) {
        double wurzel = Math.sqrt(quadrat);
        return (wurzel == (int) wurzel);
    }

    public static boolean isPrim(int n) {
        int wurzel = (int) Math.sqrt(n);
        for (int i = 2; i <= wurzel; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return n > 1;
    }
}
