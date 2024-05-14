package programm;

import eds.IntListe;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import mathe.Primzahlen;
import sort.BubbleSort;
import sort.Comparator;
import sort.Heapsort;
import sort.InsertionSort;
import sort.SelectionSort;
import sort.SortingAlgorithm;
import sort.SortingListener;

public class MainMenu extends JFrame implements ActionListener, SortingListener {

    public JTextArea output = new JTextArea("Hallo", 15, 50);
    private JButton fill = new JButton("mit Zufallszahlen füllen");
    private JButton sort = new JButton("Sortieren");
    private JComboBox box = new JComboBox(new String[]{"SelectionSort", "InsertionSort", "BubbleSort", "Heapsort"});
    private JTextField input = new JTextField("10");
    private JTextField vonIn;
    private JTextField bisIn;
    private JSlider slider = new JSlider(1, 10, 1);
    private FeldElement[] feld;
    private JPanel mitte = new JPanel();
    private JScrollPane center;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu option = new JMenu("Optionen");
    private JMenu system = new JMenu("System");
    private JCheckBoxMenuItem zeigFeld = new JCheckBoxMenuItem("Feld anzeigen");
    private JCheckBoxMenuItem dopplungen = new JCheckBoxMenuItem("Dopplungen zulassen");
    private JMenuItem prüf = new JMenuItem("Überprüfen");
    private JRadioButtonMenuItem sekunden;
    private JMenuItem exit = new JMenuItem("Exit");
    private JMenuItem sortAlg = new JMenuItem("Sortieralgorithmen");
    private JMenuItem prims = new JMenuItem("Primzahlen");
    private JMenuItem binomial = new JMenuItem("Binomialverteilung");
    private JMenuItem matrix = new JMenuItem("Matrizen");
    private JMenuItem rechner = new JMenuItem("Taschenrechner");
    private static int einheitsFaktor = 1000000;
    private static String zeiteinheit = "Millisekunden";

    MainMenu(Point pos) {
        super("Sortieralgorithmen");
        setLocation(pos);
        setLayout(new BorderLayout());
        Container c = getContentPane();
        // GUI
        output.setLineWrap(true);
        output.setEditable(false);
        JScrollPane sp = new JScrollPane(output);
        c.add(sp, BorderLayout.SOUTH);
        JPanel oben = new JPanel();
        oben.setLayout(new GridLayout(3, 3));
        JButton machArray = new JButton("Feld erzeugen");
        machArray.addActionListener(this);
        fill.addActionListener(this);
        fill.setEnabled(false);
        sort.setEnabled(false);
        sort.addActionListener(this);
        slider.setMajorTickSpacing(1);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        oben.add(new JLabel("Größe des Feldes: "));
        oben.add(input);
        oben.add(machArray);
        oben.add(new JLabel("Faktor für Zufallszahlen:"));
        oben.add(slider);
        oben.add(fill);
        oben.add(new JLabel("Sortieralgorithmus:"));
        oben.add(box);
        oben.add(sort);
        c.add(oben, BorderLayout.NORTH);
        center = new JScrollPane(mitte);
        c.add(center, BorderLayout.CENTER);
        // Menüeinstellungen
        zeigFeld.setState(true);
        zeigFeld.addActionListener(this);
        dopplungen.setState(true);
        option.add(zeigFeld);
        option.add(dopplungen);
        option.addSeparator();
        // Zeiteinheiten
        ActionListener a = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setZeiteinheit(((JRadioButtonMenuItem) e.getSource()).getText());
            }
        };
        JMenu zeitMenu = new JMenu("Zeiteinheit");
        ButtonGroup group = new ButtonGroup();
        sekunden = new JRadioButtonMenuItem("Sekunden");
        sekunden.addActionListener(a);
        group.add(sekunden);
        zeitMenu.add(sekunden);
        sekunden = new JRadioButtonMenuItem("Millisekunden");
        sekunden.addActionListener(a);
        sekunden.setSelected(true);
        group.add(sekunden);
        zeitMenu.add(sekunden);
        sekunden = new JRadioButtonMenuItem("Mikrosekunden");
        sekunden.addActionListener(a);
        group.add(sekunden);
        zeitMenu.add(sekunden);
        sekunden = new JRadioButtonMenuItem("Nanosekunden");
        sekunden.addActionListener(a);
        group.add(sekunden);
        zeitMenu.add(sekunden);
        option.add(zeitMenu);
        option.addSeparator();
        option.add(prüf);
        prüf.addActionListener(this);
        prüf.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        option.setMnemonic(KeyEvent.VK_O);
        menuBar.add(option);
        // System Menü
        exit.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }
        );
        system.add(exit);
        system.setMnemonic(KeyEvent.VK_S);
        JMenu algs = new JMenu("Algorithmen: ");
        sortAlg.addActionListener(this);
        algs.add(sortAlg);
        prims.addActionListener(this);
        algs.add(prims);
        binomial.addActionListener(this);
        algs.add(binomial);
        matrix.addActionListener(this);
        algs.add(matrix);
        rechner.addActionListener(this);
        algs.add(rechner);
        system.add(algs);
        menuBar.add(system);
        setJMenuBar(menuBar);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(zeigFeld)) {
            if (zeigFeld.getState()) {
                try {
                    if (Integer.parseInt(input.getText()) > 1000) {
                        output.append("\nSind Sie verrückt? (das Feld ist zu groß um angezeigt zu werden!)");
                        return;
                    }
                    mitte.removeAll();
                    mitte.setLayout(new GridLayout(1 + feld.length / 20, 20));
                    for (int i = 0; i < feld.length; i++) {
                        mitte.add(feld[i]);
                    }
                } catch (Exception ex) {
                    return;
                }
                pack();
            } else {
                mitte.removeAll();
                pack();
            }
        } else if (e.getSource().equals(fill)) {
            double faktor = 1.0 * slider.getValue();
            int[] zahlen = BubbleSort.fillIntArray(feld.length, faktor, dopplungen.getState());
            for (int i = 0; i < feld.length; i++) {
                feld[i].wert.setText("" + zahlen[i]);
            }
            output.append("\n Zufallszahlen bis " + (int) (feld.length * faktor) + " wurden eingefügt.");
        } else if (e.getSource().equals(sort)) {
            output.append("\nSortiere mit " + box.getSelectedItem() + "...");
            int[] werte = new int[feld.length];
            try {
                for (int i = 0; i < feld.length; i++) {
                    werte[i] = Integer.parseInt(feld[i].wert.getText());
                }
                Comparator<Integer> c = new Comparator<Integer>() {
                    @Override
                    public boolean compare(Integer i1, Integer i2) {
                        return i1 > i2;
                    }
                };
                SortingAlgorithm<Integer> sortierer = new SelectionSort(c);
                switch (box.getSelectedIndex()) {
                    case 0:
                        break;
                    case 1:
                        sortierer = new InsertionSort(c);
                        break;
                    case 2:
                        sortierer = new BubbleSort(c);

                        break;
                    case 3:
                        sortierer = new Heapsort(c);
                        break;
                }
                sortierer.addSortierListener(this);
                Integer[] ints = new Integer[werte.length];
                for (int i = 0; i < werte.length; i++) {
                    ints[i] = werte[i];
                }
                sortierer.setValues(ints);
                sortierer.resetCounters();
                long start = System.nanoTime();
                ints = sortierer.sort();
                long end = System.nanoTime() - start;
                for (int i = 0; i < werte.length; i++) {
                    werte[i] = ints[i];
                }
                for (int i = 0; i < feld.length; i++) {
                    feld[i].wert.setText("" + werte[i]);
                }
                output.append(" Fertig!\nBenötigte Zeit: " + 1.0 * end / einheitsFaktor + " " + zeiteinheit
                        + "\n" + sortierer.getComparisons() + " Vergleiche und " + sortierer.getSwitches() + " Austauschoperationen"
                );
            } catch (Exception ex) {
                System.out.println(ex);
                output.append("\nüberprüfen Sie Ihre Eingaben");
            }
        } else if (e.getSource().equals(sortAlg)) {
            dispose();
            new MainMenu(getLocation());
        } else if (e.getSource().equals(prims)) {
            primzahlen();
        } else if (e.getSource().equals(binomial)) {
            new Binomial().setJMenuBar(menuBar);
            dispose();
        } else if (e.getSource().equals(matrix)) {
            new MatrixPanel().setJMenuBar(menuBar);
            dispose();
        } else if (e.getSource().equals(rechner)) {
            new RechnerGUI().setJMenuBar(menuBar);
            dispose();
        } else if (e.getSource().equals(prüf)) {
            if (sort.isEnabled()) {
                prüfe();
            }
        } else if (((JButton) e.getSource()).getText().equals("Zahlen Ausgeben")) {
            try {
                int von = Integer.parseInt(vonIn.getText());
                int bis = Integer.parseInt(bisIn.getText());
                int[] primzahlen = Primzahlen.gibPrim(von, bis);
                String s = "Primzahlen von " + von + " bis " + bis + ": \n";
                for (int x : primzahlen) {
                    s = s + x + " ";
                }
                output.append("\n" + s + "\nGesamt: " + primzahlen.length);
            } catch (Exception ex) {
                output.append("\n überprüfen Sie Ihre Eingaben!");
            }
        } else {
            try {
                int anz = Integer.parseInt(input.getText());
                feld = new FeldElement[anz];
                for (int i = 0; i < feld.length; i++) {
                    feld[i] = new FeldElement(i);
                }
                output.append("\nFeld mit " + anz + " Elementen wurde erzeugt.");
                if (zeigFeld.getState()) {
                    if (Integer.parseInt(input.getText()) > 1000) {
                        output.append("\nSind Sie verrückt? (das Feld ist zu groß!)");
                        return;
                    }
                    mitte.removeAll();
                    mitte.setLayout(new GridLayout(1 + anz / 20, 20, 10, 10));
                    for (int i = 0; i < feld.length; i++) {
                        mitte.add(feld[i]);
                    }
                    pack();
                }
            } catch (Exception ex) {
                output.append("\nBitte die Größe des Feldes angeben!");
                return;
            }
            fill.setEnabled(true);
            sort.setEnabled(true);
        }
    }

    private void prüfe() {
        int[] werte = new int[feld.length];
        for (int i = 0; i < feld.length; i++) {
            werte[i] = Integer.parseInt(feld[i].wert.getText());
        }
        IntListe fehler = new IntListe();
        for (int i = 0; i < werte.length - 1; i++) {
            if (werte[i] > werte[i + 1]) {
                fehler.add(i);
            }
        }
        int fehlerAnz = fehler.getSize();
        if (fehlerAnz == 0) {
            output.append("\nDas Array ist fehlerfrei sortiert :)");
        } else if (fehlerAnz > werte.length / 2) {
            output.append("\nDas Array ist komplett unsortiert");
        } else {
            output.append("\nDas Array weist an folgenden Stellen Fehler auf:");
            for (int i = 0; i < fehlerAnz; i++) {
                output.append(" " + fehler.delete(0));
            }
        }
    }

    private void primzahlen() {
        this.setTitle("Primzahlen");
        Container c = getContentPane();
        c.removeAll();
        c.add(new JScrollPane(output), BorderLayout.SOUTH);
        JPanel mittig = new JPanel();
        mittig.setLayout(new GridLayout(2, 3));
        mittig.add(new JLabel("Primzahlen "));
        mittig.add(new JLabel(" von "));
        mittig.add(new JLabel(" bis "));
        JButton ausgeben = new JButton("Zahlen Ausgeben");
        ausgeben.addActionListener(this);
        mittig.add(ausgeben);
        vonIn = new JTextField("0");
        mittig.add(vonIn);
        bisIn = new JTextField("100");
        mittig.add(bisIn);
        c.add(mittig, BorderLayout.CENTER);
        menuBar.remove(option);
        pack();
    }

    public static void setZeiteinheit(String x) {
        zeiteinheit = x;
        switch (x) {
            case "Sekunden":
                einheitsFaktor = 1000000000;
                break;
            case "Millisekunden":
                einheitsFaktor = 1000000;
                break;
            case "Mikrosekunden":
                einheitsFaktor = 1000;
                break;
            case "Nanosekunden":
                einheitsFaktor = 1;
                break;
        }
    }

    @Override
    public void abgefragt(int i) {

    }

    @Override
    public void verglichen(int i, int j) {

    }

    @Override
    public void vertauscht(int i, int j) {

    }
}

class FeldElement extends JPanel {

    JTextField wert;
    int index;

    FeldElement(int i) {
        setLayout(new GridLayout(2, 1));
        wert = new JTextField("0");
        add(wert);
        index = i;
        add(new JLabel("" + index));
    }

    FeldElement(String x, int i) {
        setLayout(new GridLayout(2, 1));
        wert = new JTextField(x);
        add(wert);
        index = i;
        add(new JLabel("" + index));
    }
}
