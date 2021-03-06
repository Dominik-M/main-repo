/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphic;

/**
 *
 * @author Dominik
 */
public class ProgressPanel extends MainPanel {

    private static ProgressPanel activePanel;

    /**
     * Creates new form ProgressPanel
     */
    private ProgressPanel() {
        initComponents();
        progress.setValue(0);
    }

    public static ProgressPanel getInstance() {
        if (activePanel == null) {
            activePanel = new ProgressPanel();
        }
        return activePanel;
    }

    public static void setProgress(int percent) {
        if (activePanel != null && percent >= 0 && percent <= 100) {
            activePanel.progress.setValue(percent);
        }
    }

    public static void setText(String txt) {
        if (activePanel != null) {
            activePanel.text.setText(txt);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        text = new javax.swing.JLabel();
        progress = new javax.swing.JProgressBar();

        text.setText("Loading...");

        progress.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addComponent(text))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(text)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar progress;
    private javax.swing.JLabel text;
    // End of variables declaration//GEN-END:variables

    @Override
    protected void onSelect() {
    }

    @Override
    protected void onDisselect() {
    }

    @Override
    public void ButtonPressed(int button) {
    }

    @Override
    public void ButtonReleased(int button) {
    }
}
