package programm;

import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import mathe.Matrix;
import mathe.Vektor;

public class MatrixComponent extends javax.swing.JPanel {
    private Matrix m;
    private JTextField[][] werte;
    private JTextField name;

    /**
     * Creates new form MatrixComponent
     */
    public MatrixComponent() {
        this("E",new Vektor(1,0));
    }
    
    public MatrixComponent(Matrix matrix) {
        this("M",matrix);
    }
    
    public MatrixComponent(String n,Matrix matrix) {
        m=matrix;
        name=new JTextField();
        name.addActionListener(new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setName(name.getText());
            }
        });
        setName(n);
        initComponents();
    }
    
    @Override
    public String toString(){
        return name.getText();
    }
    
    public Matrix getValues(){
        if(m.getSpalten()==1){
          double[] a=new double[m.getZeilen()];
          for(int z=0;z<m.getZeilen();z++){
              a[z]=Double.parseDouble(werte[z][0].getText());
          }
          Vektor x=new Vektor(a);
          x.setName(name.getText());
          return x;
        }
        double[][] a=new double[m.getZeilen()][m.getSpalten()];
        for(int z=0;z<m.getZeilen();z++){
            for(int s=0;s<m.getSpalten();s++) {
                a[z][s]=Double.parseDouble(werte[z][s].getText());
            }
        }
        Matrix x=new Matrix(a);
        x.setName(name.getText());
        return x;
    }
    
    public void setMatrix(Matrix matrix){
        m=matrix;
        initComponents();
    }
    
    @Override
    public void setName(String n){
        super.setName(n);
        name.setText(n);
    }

    private void initComponents() {
        removeAll();
        werte=new JTextField[m.getZeilen()][m.getSpalten()];
        for(int z=0;z<m.getZeilen();z++){
            for(int s=0;s<m.getSpalten();s++){
                werte[z][s]=new JTextField(m.getWert(z, s)+"");
            }
        }
        setLayout(new java.awt.GridLayout(1+m.getZeilen(),1+m.getSpalten()));
        add(name);
        for(int s=0;s<m.getSpalten();s++)add(new JLabel("Spalte "+s));
        for(int z=0;z<m.getZeilen();z++){
            add(new JLabel("Zeile "+z));
            for(int s=0;s<m.getSpalten();s++)add(werte[z][s]);
        }
    }
}