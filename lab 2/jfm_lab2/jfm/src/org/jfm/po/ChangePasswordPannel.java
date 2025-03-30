/**
 * 
 */
package org.jfm.po;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


import edu.asu.ser335.jfm.RolesSingleton;
import edu.asu.ser335.jfm.UsersSingleton;



/**
 * @author Nikhil Hiremath
 *
 */
public class ChangePasswordPannel extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JLabel labelUsername = new JLabel("Enter username: ");
	private JLabel labelPassword = new JLabel("Enter password: ");
	private JLabel labelRole = new JLabel("Enter Role: ");
	private JLabel message;
	private JTextField textUsername = new JTextField(20);
	private JPasswordField fieldPassword = new JPasswordField(20);
	private JButton buttonChangePassword = new JButton("Submit");
	private JPanel newPanel;
	private JComboBox<String> roleList;

	public ChangePasswordPannel() {
		// create a new panel with GridBagLayout manager
		newPanel = new JPanel(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10, 10, 10, 10);

		// add components to the panel
		// UserName
		constraints.gridx = 0;
		constraints.gridy = 0;
		newPanel.add(labelUsername, constraints);

		constraints.gridx = 1;
		newPanel.add(textUsername, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;

		// Password
		newPanel.add(labelPassword, constraints);

		constraints.gridx = 1;
		newPanel.add(fieldPassword, constraints);

		constraints.gridx = 0;
		constraints.gridy = 2;

		// Role
		newPanel.add(labelRole, constraints);
		constraints.gridx = 1;

		// drop down
		roleList = new JComboBox<String>(RolesSingleton.getRoleMapping().getDisplayRoles());

		// add to the parent container (e.g. a JFrame):
		newPanel.add(roleList, constraints);

		// System.out.println("Selected role: " + role);

		constraints.gridx = 0;
		constraints.gridy = 3;

		message = new JLabel();
		newPanel.add(message, constraints);
		constraints.gridx = 1;

		constraints.gridwidth = 3;
		constraints.anchor = GridBagConstraints.CENTER;
		newPanel.add(buttonChangePassword, constraints);

		// Adding the listeners to components..

		buttonChangePassword.addActionListener(this);

		// set border for the panel
		newPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Admin Panel"));

		// add the panel to this frame
		add(newPanel);

		pack();
		setLocationRelativeTo(null);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String userName = textUsername.getText().trim();
		// String userName = (String) roleList.getSelectedItem();
		String password = String.valueOf(fieldPassword.getPassword()).trim();;
		String role = (String) roleList.getSelectedItem();
		
		// TODO: for you to complete!
		//JOptionPane.showMessageDialog(null, "NOT IMPLEMENTED YET!!");
           //Step a: Check for empty fields
		if (userName.isEmpty() || password.isEmpty() || role.isEmpty()) {
			JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			//Step b: Check if user exists
			if (!UsersSingleton.getUserPasswordMapping().containsKey(userName)) {
				JOptionPane.showMessageDialog(this, "User does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Step c: Check if role matches
			if (!UsersSingleton.getUserRoleMapping().get(userName).equals(role)) {
				JOptionPane.showMessageDialog(this, "Role does not match the user's current role.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Step d: Update password
			boolean updated = UsersSingleton.updateUserPassword(userName, password, role);

			if (updated) {
				JOptionPane.showMessageDialog(this, "Password updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
				dispose();  // Close the panel
			} else {
				JOptionPane.showMessageDialog(this, "Failed to update password.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "An error occurred while updating the password.", "Error", JOptionPane.ERROR_MESSAGE);
		}


	}

}
