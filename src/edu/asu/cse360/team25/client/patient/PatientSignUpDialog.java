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

public class PatientSignUpDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7754480387257955405L;

	PatientClient pc;

	private JTextField tfUsername;
	private JTextField tfGender;
	private JTextField tfHeight;
	private JTextField tfWeight;
	private JTextField tfBirthday;
	private JPasswordField pfPassword;
	private JPasswordField pfPasswordCheck;
	private JLabel lbUsername;
	private JLabel lbPassword;
	private JLabel lbPasswordCheck;
	private JButton btnSignUp;
	private JButton btnCancel;

	public PatientSignUpDialog(Frame parent, PatientClient pc) {

		super(parent, "Sign Up", true);

		this.pc = pc;

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();

		cs.fill = GridBagConstraints.HORIZONTAL;

		// user name
		
		lbUsername = new JLabel("Name: ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(lbUsername, cs);

		tfUsername = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(tfUsername, cs);

		// password
		
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

		// password check
		
		lbPasswordCheck = new JLabel("Repeat Password: ");
		cs.gridx = 0;
		cs.gridy = 2;
		cs.gridwidth = 1;
		panel.add(lbPasswordCheck, cs);

		pfPasswordCheck = new JPasswordField(20);
		cs.gridx = 1;
		cs.gridy = 2;
		cs.gridwidth = 2;
		panel.add(pfPasswordCheck, cs);
		panel.setBorder(new LineBorder(Color.GRAY));

		// gender
		
		JLabel lbGender = new JLabel("Gender: ");
		cs.gridx = 0;
		cs.gridy = 3;
		cs.gridwidth = 1;
		panel.add(lbGender, cs);

		tfGender = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 3;
		cs.gridwidth = 2;
		panel.add(tfGender, cs);

		// height
		
		JLabel lbHeight = new JLabel("Height: ");
		cs.gridx = 0;
		cs.gridy = 4;
		cs.gridwidth = 1;
		panel.add(lbHeight, cs);

		tfHeight = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 4;
		cs.gridwidth = 2;
		panel.add(tfHeight, cs);
		
		// weight
		
		JLabel lbWeight = new JLabel("Weight: ");
		cs.gridx = 0;
		cs.gridy = 5;
		cs.gridwidth = 1;
		panel.add(lbWeight, cs);

		tfWeight = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 5;
		cs.gridwidth = 2;
		panel.add(tfWeight, cs);

		// birthday
		
		JLabel lbBirthday = new JLabel("Birthday: ");
		cs.gridx = 0;
		cs.gridy = 6;
		cs.gridwidth = 1;
		panel.add(lbBirthday, cs);

		tfBirthday = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 6;
		cs.gridwidth = 2;
		panel.add(tfBirthday, cs);

		
		btnSignUp = new JButton("Sign Up");

		btnSignUp.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String pass1 = new String(pfPassword.getPassword());
				String pass2 = new String(pfPasswordCheck.getPassword());
				if(!pass1.equals(pass2)) {
					JOptionPane.showMessageDialog(null,
						    "Password does not match!",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (tfUsername.getText().isEmpty() || pass1.isEmpty()
						|| pass2.isEmpty() || tfGender.getText().isEmpty()
						|| tfHeight.getText().isEmpty()
						|| tfWeight.getText().isEmpty()
						|| tfBirthday.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Must input something for all fields!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				PatientSignUpDialog.this.pc.signup = true;
				PatientSignUpDialog.this.pc.usernameSU = tfUsername.getText();
				PatientSignUpDialog.this.pc.passwordSU = pass1;
				PatientSignUpDialog.this.pc.genderSU = tfGender.getText();
				PatientSignUpDialog.this.pc.heightSU = tfHeight.getText();
				PatientSignUpDialog.this.pc.weightSU = tfWeight.getText();
				PatientSignUpDialog.this.pc.birthdaySU = tfBirthday.getText();

				dispose();

			}
		});
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				PatientSignUpDialog.this.pc.signup = false;
				
				dispose();
			}
		});
		JPanel bp = new JPanel();
		bp.add(btnSignUp);
		bp.add(btnCancel);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
	}

}