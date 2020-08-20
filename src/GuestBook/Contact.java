package GuestBook;

public class Contact implements Comparable<Contact>{

	/** First name of the contact */
	private String firstName;
	
	/** Last name of the contact */
	private String lastName;
	
	/** Address for the contact */
	private String address;
	
	/** Phone number for the contact */
	private String phoneNumber;
	
	
	/**
	 * Creates a new contact with the given name, address, and phone number
	 * @param firstName First name of the contact
	 * @param lastName Last name of the contact
	 * @param address Address for the contact
	 * @param phoneNumber Phone number for the contact
	 */
	public Contact(String firstName, String lastName, String address,String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}
	
	
	/**
	 * @return First name of the contact
	 */
	public String getFirstName() {
		return firstName;
	}
	
	
	/**
	 * @return Last name of the contact
	 */
	public String getLastName() {
		return lastName;
	}
	
	
	/**
	 * @return Address for the contact
	 */
	public String getAddress() {
		return address;
	}
	
	
	/**
	 * @return Phone number for the contact
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	
	/**
	 * Compares each Contact by first name
	 */
	@Override
	public int compareTo(Contact o) {
		return this.firstName.compareTo(o.firstName);
	}
	
	
	/**
	 * Contacts are equal if they have the same first name and last name
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof Contact)) {
			return false;
		}
		
		final Contact other = (Contact) obj;
		
		return this.firstName.equals(other.firstName) && this.lastName.equals(other.lastName);
	}
}
