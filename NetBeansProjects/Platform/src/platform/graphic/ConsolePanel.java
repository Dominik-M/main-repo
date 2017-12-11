/*
 * Copyright (C) 2016 Dundun
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package platform.graphic;

import java.util.LinkedList;

import platform.utils.Interpreter;

/**
 * 
 * @author Dominik Messerschmidt <dominik.messerschmidt@continental-corporation.com> Created 14.06.2016
 */
public class ConsolePanel extends javax.swing.JPanel {

	private final LinkedList<String> lastInputs = new LinkedList<>();
	private int index = -1;

	/**
	 * Creates new form ConsolePanel
	 */
	public ConsolePanel() {
		initComponents();
	}

	public void append(String text) {
		output.append(text);
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
	 * content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		scrollPane = new javax.swing.JScrollPane();
		output = new javax.swing.JTextArea();
		jLabel1 = new javax.swing.JLabel();
		input = new javax.swing.JTextField();
		jButton1 = new javax.swing.JButton();

		output.setBackground(new java.awt.Color(0, 0, 0));
		output.setColumns(20);
		output.setForeground(new java.awt.Color(255, 255, 255));
		output.setRows(5);
		output.setFocusable(false);
		scrollPane.setViewportView(output);

		jLabel1.setText("Console");

		input.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				inputActionPerformed(evt);
			}
		});
		input.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent evt) {
				inputKeyPressed(evt);
			}
		});

		jButton1.setText("close");
		jButton1.setFocusable(false);
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
		.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		.addComponent(input)
		.addGroup(
		layout
		.createSequentialGroup()
		.addComponent(jLabel1)
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
		javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(jButton1)
		.addContainerGap())
		.addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
		javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		layout
		.createSequentialGroup()
		.addGroup(
		layout
		.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
		.addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE,
		javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		.addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE,
		javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addComponent(scrollPane)
		.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		.addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE,
		javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		.addContainerGap()));
	}// </editor-fold>//GEN-END:initComponents

	private void inputActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_inputActionPerformed
		String in = input.getText();
		append(">" + in + "\n");
		Interpreter.execute(in);
		lastInputs.addFirst(in);
		input.setText("");
		index = -1;
		// GameGrid.getInstance().requestFocusInWindow();
	}// GEN-LAST:event_inputActionPerformed

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		MainFrame.FRAME.setConsoleEnabled(false);
	}// GEN-LAST:event_jButton1ActionPerformed

	private void inputKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_inputKeyPressed
		switch (evt.getKeyCode()) {
			case java.awt.event.KeyEvent.VK_UP:
				if (index + 1 < lastInputs.size()) {
					index++;
					input.setText(lastInputs.get(index));
				}
				break;
			case java.awt.event.KeyEvent.VK_DOWN:
				if (index - 1 >= 0) {
					index--;
					input.setText(lastInputs.get(index));
				}
				break;
		}
	}// GEN-LAST:event_inputKeyPressed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JTextField input;
	private javax.swing.JButton jButton1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JTextArea output;
	private javax.swing.JScrollPane scrollPane;
	// End of variables declaration//GEN-END:variables
}
