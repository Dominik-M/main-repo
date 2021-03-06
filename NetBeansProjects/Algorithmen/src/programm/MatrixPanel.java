package programm;

import mathe.Matrix;
import mathe.Vektor;

public class MatrixPanel extends javax.swing.JFrame {

    private java.util.LinkedList<Matrix> matrizen;

    /**
     * Creates new form MatrixPanel
     */
    public MatrixPanel() {
        matrizen = new java.util.LinkedList();
        initComponents();
        list.setModel(new javax.swing.AbstractListModel() {
            public int getSize() {
                return matrizen.size();
            }

            public Object getElementAt(int i) {
                return matrizen.get(i);
            }
        });
        setVisible(true);
    }

    public Matrix getListValue(String n) {
        for (Matrix m : matrizen) {
            if (m.getName().equals(n)) {
                return m;
            }
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        matrix = new programm.MatrixComponent();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        zeilen = new javax.swing.JSpinner();
        spalten = new javax.swing.JSpinner();
        add = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        list = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        output = new javax.swing.JTextArea();
        input = new javax.swing.JTextField();
        remove = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(1);

        jLabel1.setText("Zeilen:");

        jLabel2.setText("Spalten:");

        zeilen.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        zeilen.setEditor(new javax.swing.JSpinner.NumberEditor(zeilen, ""));
        zeilen.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                zeilenStateChanged(evt);
            }
        });

        spalten.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        spalten.setEditor(new javax.swing.JSpinner.NumberEditor(spalten, ""));
        spalten.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spaltenStateChanged(evt);
            }
        });

        add.setText("Add to list");
        add.setToolTipText("");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        list.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Einheitsmatrix[1x1]" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listMouseClicked(evt);
            }
        });
        list.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(list);

        output.setEditable(false);
        output.setColumns(20);
        output.setRows(5);
        jScrollPane2.setViewportView(output);

        input.setText("input");
        input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputActionPerformed(evt);
            }
        });

        remove.setText("Remove from list");
        remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(zeilen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spalten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(matrix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(add)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                                .addComponent(remove)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(input)
                            .addComponent(jScrollPane2))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(matrix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(zeilen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(spalten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(add)
                            .addComponent(remove)))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputActionPerformed
        String in = input.getText();
        output.append(in + ":\n");
        if (in.equals("-h")) {
            output.append("Ausgeben: <Variablennamen>\n"
                    + "Determinante: det(<Matrix>)\n"
                    + "Kreuzprodukt: <Vektor>x<Vektor>\n"
                    + "LGS lösen: solve(<Matrix>,<Vektor>)");
        } else if (in.startsWith("det(") && in.endsWith(")")) { // Determinante
            String n = in.substring(in.indexOf("(") + 1, in.indexOf(")"));
            Matrix m = getListValue(n);
            if (m == null) {
                output.append("Variable " + n + " ist nicht in der Liste\n");
            } else {
                output.append("= " + m.det() + "\n");
            }
        } else if (in.indexOf("x") > 0) { //Kreuzprodukt
            Matrix a, b;
            String n = in.substring(0, in.indexOf("x"));
            a = getListValue(n);
            if (a == null) {
                output.append("Variable " + n + " ist nicht in der Liste\n");
                return;
            }
            n = in.substring(in.indexOf("x") + 1);
            b = getListValue(n);
            if (b == null) {
                output.append("Variable " + n + " ist nicht in der Liste\n");
                return;
            }
            if (a.getClass() == Vektor.class && b.getClass() == Vektor.class) {
                Vektor produkt = ((Vektor) a).kreuzprodukt((Vektor) b);
                if (produkt != null) {
                    produkt.print(output);
                } else {
                    output.append("Falsche Dimensionen");
                }
            } else {
                output.append("Die Parameter müssen Vektoren sein\n");
            }
        } else if (in.startsWith("solve(") && in.endsWith(")")) { // LGS lösen
            String n = in.substring(in.indexOf("(") + 1, in.indexOf(","));
            Matrix m = getListValue(n);
            if (m == null) {
                output.append("Variable " + n + " ist nicht in der Liste\n");
                return;
            }
            n = in.substring(in.indexOf(",") + 1, in.indexOf(")"));
            Matrix x = getListValue(n);
            try {
                Vektor lsg = mathe.Mathe.solve(m, (Vektor) x);
                lsg.print(output);
            } catch (Exception ex) {
                output.append(ex.toString());
            }
        } else {
            Matrix m = getListValue(in);
            if (m == null) {
                output.append("Unbekannter Ausdruck\nfür Hilfe -h eingeben");
            } else {
                m.print(output);
            }
        }
        input.setText("");
    }//GEN-LAST:event_inputActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        Matrix neu = matrix.getValues();
        if (listContains(neu)) {
            output.append("Die Variable ist bereits in der Liste\n");
            return;
        }
        addToList(neu);
    }//GEN-LAST:event_addActionPerformed

    private void zeilenStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_zeilenStateChanged
        neuMatrix();
    }//GEN-LAST:event_zeilenStateChanged

    private void spaltenStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spaltenStateChanged
        neuMatrix();
    }//GEN-LAST:event_spaltenStateChanged

    private void removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeActionPerformed
        Matrix alt = (Matrix) list.getSelectedValue();
        if (alt != null) {
            removeFromList(alt);
        }
    }//GEN-LAST:event_removeActionPerformed

    private void listValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listValueChanged
        remove.setEnabled(list.getSelectedIndex() >= 0);
    }//GEN-LAST:event_listValueChanged

    private void listMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listMouseClicked
        if (evt.getClickCount() > 1) {
            int index = list.getSelectedIndex();
            if (index >= 0) {
                showMatrix(matrizen.get(index));
            }
        }
    }//GEN-LAST:event_listMouseClicked

    public void addToList(Matrix neu) {
        matrizen.add(neu);
        list.setModel(new javax.swing.AbstractListModel() {
            public int getSize() {
                return matrizen.size();
            }

            public Object getElementAt(int i) {
                return matrizen.get(i);
            }
        });
    }

    public void removeFromList(Matrix alt) {
        matrizen.remove(alt);
        list.setModel(new javax.swing.AbstractListModel() {
            public int getSize() {
                return matrizen.size();
            }

            public Object getElementAt(int i) {
                return matrizen.get(i);
            }
        });
    }

    public boolean listContains(Matrix x) {
        for (Matrix y : matrizen) {
            if (y.equals(x)) {
                return true;
            }
        }
        return false;
    }

    public void showMatrix(Matrix x) {
        zeilen.setValue(x.zeilen);
        spalten.setValue(x.spalten);
        matrix.setMatrix(x);
    }

    private void neuMatrix() {
        Matrix neu = new mathe.Matrix((int) zeilen.getValue(), (int) spalten.getValue());
        Matrix alt = matrix.getValues();
        for (int z = 0; z < alt.getZeilen() && z < neu.getZeilen(); z++) {
            for (int s = 0; s < alt.getSpalten() && s < neu.getSpalten(); s++) {
                neu.set(z, s, alt.getWert(z, s));
            }
        }
        matrix.setMatrix(neu);
        pack();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JTextField input;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList list;
    private programm.MatrixComponent matrix;
    private javax.swing.JTextArea output;
    private javax.swing.JButton remove;
    private javax.swing.JSpinner spalten;
    private javax.swing.JSpinner zeilen;
    // End of variables declaration//GEN-END:variables
}
