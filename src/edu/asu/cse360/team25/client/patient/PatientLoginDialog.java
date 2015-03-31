package edu.asu.cse360.team25.client.patient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class PatientLoginDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7969631039381989668L;

	PatientClient pc;

	private JTextField tfUserID;
	private JPasswordField pfPassword;
	private JLabel lbUsername;
	private JLabel lbPassword;
	private JButton btnLogin;
	private JButton btnSignup;
	private JButton btnCancel;

	public PatientLoginDialog(Frame parent, PatientClient pc, int id, String password) {

		super(parent, "Login", true);

		this.pc = pc;

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();

		cs.fill = GridBagConstraints.HORIZONTAL;

		lbUsername = new JLabel("User ID: ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(lbUsername, cs);

		tfUserID = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(tfUserID, cs);
		if (id != -1)
			tfUserID.setText(String.valueOf(id));

		lbPassword = new JLabel("Password: ");
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		panel.add(lbPassword, cs);

		pfPassword = new JPasswordField(20);
		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 2;
		panel.add(pfPassword, cs);
		panel.setBorder(new LineBorder(Color.GRAY));
		if(password != null)
			pfPassword.setText(password);

		btnLogin = new JButton("Login");

		btnLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				int id = 0;
				try {
					id = Integer.parseInt(tfUserID.getText().trim());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Invalid user ID!",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				PatientLoginDialog.this.pc.setIDnPW(id,
						new String(pfPassword.getPassword()), true);
				PatientLoginDialog.this.pc.signup = false;
				dispose();

			}
		});
		btnSignup = new JButton("Sign Up");
		btnSignup.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				PatientLoginDialog.this.pc.setIDnPW(-1,
						null, false);
				PatientLoginDialog.this.pc.signup = true;
				dispose();
			}
		});
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				PatientLoginDialog.this.pc.setIDnPW(-1,
						null, false);
				PatientLoginDialog.this.pc.signup = false;
				dispose();
			}
		});
		JPanel bp = new JPanel();
		bp.add(btnLogin);
		bp.add(btnSignup);
		bp.add(btnCancel);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
	}

}