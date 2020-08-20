package GuestBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

public class ContactViewController implements ActionListener {
	
	/** List of contacts in the contact book */
	private ArrayList<Contact> contacts;
	
	/** Button to add contact info to the contact book */
	private JButton submitButton;
	
	/** Base frame for the Gui */
	private JFrame frame;
	
	/** Panel to contain the rest of the Gui panels */
	private JPanel basePanel;
	
	/** Panel to contain the input field */
	private JPanel inputPanel;
	
	/** Panel to contain the contact info scroll panel */
	private JPanel infoPanel;
	
	/** Panel to contain the submit button */
	private JPanel submitPanel;
	
	/** Scroll pane to display all contacts in the contact book */
	private JScrollPane contactInfoPane;
	
	/** Text area to display all contacts in the contact book */
	private JTextArea contactInfoText;
	
	/** Text field for the user to enter a contact's first name */
	private JTextField fNameField;
	
	/** Text field for the user to enter a contact's last name */
	private JTextField lNameField;
	
	/** Text field for the user to enter a contact's address */
	private JTextField addressField;
	
	/** Text field for the user to enter a contact's phone number */
	private JTextField phoneNumberField;
	
	
	/**
	 * Creates new Contact Book UI
	 */
	public ContactViewController() {
		contacts = new ArrayList<Contact>();
		frame = new JFrame("ContactBook");
		initializePanels();
		initializeButtons();
		initializeTextFields();
		initializeScrollPane();
		this.display();
	}
	
	
	/**
	 * Initializes all UI panels
	 */
	private void initializePanels() {
		basePanel = new JPanel();
		inputPanel = new JPanel();
		infoPanel = new JPanel();
		submitPanel = new JPanel();
		
		inputPanel.setLayout(new GridLayout(4, 2));
		basePanel.setLayout(new GridLayout(2, 2));
		
		basePanel.add(inputPanel);
		basePanel.add(infoPanel);
		basePanel.add(submitPanel);
		
		frame.add(basePanel);
	}
	
	
	/**
	 * Initializes all UI buttons
	 */
	private void initializeButtons() {
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		submitPanel.add(submitButton);
	}
	
	
	/**
	 * Initializes all input text fields
	 */
	private void initializeTextFields() {
		JLabel fNameLabel = new JLabel("First Name", JLabel.CENTER);
		JLabel lNameLabel = new JLabel("Last Name", JLabel.CENTER);
		JLabel addressLabel = new JLabel("Address", JLabel.CENTER);
		JLabel phoneNumberLabel = new JLabel("Phone", JLabel.CENTER);
		fNameField = new JTextField();
		lNameField = new JTextField();
		addressField = new JTextField();
		phoneNumberField = new JTextField();
		
		inputPanel.add(fNameLabel);
		inputPanel.add(fNameField);
		inputPanel.add(lNameLabel);
		inputPanel.add(lNameField);
		inputPanel.add(addressLabel);
		inputPanel.add(addressField);
		inputPanel.add(phoneNumberLabel);
		inputPanel.add(phoneNumberField);
	}
	
	
	/**
	 * Initializes scroll pane field and contact info display area
	 */
	private void initializeScrollPane() {
		contactInfoText = new JTextArea();
		contactInfoPane = new JScrollPane(contactInfoText);
		contactInfoPane.setPreferredSize(new Dimension(350, 125));
		
		infoPanel.add(contactInfoPane);
	}
	
	
	/**
	 * Displays the Contact Book UI
	 */
	private void display() {
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setSize(new Dimension(700, 250));
		frame.setVisible(true);
	}
	
	
	/**
	 * Checks if the input text fields contain valid data, marks the corresponding text label
	 * with red text if the input is invalid
	 * @return True if all input text fields contain valid data, false otherwise
	 */
	private boolean isValidInput() {
		boolean isValid = true;
		Component currComp;
		JLabel currLabel;
		
		for (int i = 0; i < inputPanel.getComponentCount(); i++) {
			currComp = inputPanel.getComponent(i);
			if (currComp instanceof JTextField) {
				currLabel = (JLabel) inputPanel.getComponent(i - 1);
				if (((JTextField) currComp).getText().equals("")) {
					if (currLabel.getForeground() != Color.RED) {
						currLabel.setForeground(Color.RED);
						currLabel.setText(currLabel.getText() + " (Required)");						
					}
					isValid = false;
				} else {
					if (currLabel.getForeground() == Color.RED) {
						currLabel.setForeground(Color.BLACK);
						currLabel.setText(currLabel.getText().split(" \\(")[0]);
					}
				}
			}
		}
		
		return isValid;
	}
	
	
	/**
	 * Clears the text from all input text fields
	 */
	private void clearText() {
		Component[] inputComponents = inputPanel.getComponents();
		
		for (Component comp : inputComponents) {
			comp.setForeground(Color.BLACK);
			if (comp instanceof JTextField) {
				((JTextField) comp).setText("");
			}
		}
	}
	
	
	/**
	 * Adds the user entered contact info to the contact book.
	 * @return True if the contact was added successfully, false otherwise
	 */
	private boolean addContact() {
		boolean storeSuccessful = storeContact();
		if (storeSuccessful) {
			updateContactList();
		}
		return storeSuccessful;
	}
	
	
	/**
	 * Attempts to store the user entered contact info to the contact book. If the contact
	 * is a duplicate of an existing contact in the contact book, an error message is shown.
	 * @return True if the contact was added successfully, false otherwise 
	 */
	private boolean storeContact() {
		Contact enteredContact = new Contact(fNameField.getText(), lNameField.getText(),
				addressField.getText(),	phoneNumberField.getText());
		
		if (contacts.contains(enteredContact)) {
			JOptionPane.showMessageDialog(frame, "The entered contact already exists in the" +
					" Contact Book. Please update the information and try again",
					"Duplicate Entry", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		contacts.add(enteredContact);
		Collections.sort(contacts);
		return true;
	}
	
	
	/**
	 * Adds all of the contacts in the contact book to the UI text area for display.
	 */
	private void updateContactList() {
		StringBuilder builder = new StringBuilder();
		
		for (Contact person : contacts) {
			builder.append("Contact:\n");
			builder.append("First Name:\t" + person.getFirstName() + "\n");
			builder.append("Last Name:\t" + person.getLastName() + "\n");
			builder.append("Address:\t" + person.getAddress() + "\n");
			builder.append("Phone:\t" + person.getPhoneNumber() + "\n");
			builder.append("\n");
		}
		
		contactInfoText.setText(builder.toString());
	}
	
	
	/**
	 * If the submit button is pressed, check if the UI is valid then attempt to add the contact
	 * to the contact book. If the contact is added successfully, clear all the enter data from
	 * the input text fields.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == submitButton) {
			if (isValidInput()) {
				if (addContact()) {
					clearText();				
				}
			}			
		}
	}
}
