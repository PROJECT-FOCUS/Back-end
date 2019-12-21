package db;

public interface DBConnection {
	/**
	 * Close the connection.
	 */
	public void close();
	
	/**
	 * Return whether the credential is correct. 
	 * 
	 * @param userId
	 * @param password
	 * @return boolean
	 */
	public boolean verifyLogin(String userId, String password);
	
	/**
	 * Get full name of a user. (This is not needed for main course, just for demo
	 * and extension).
	 * 
	 * @param userId
	 * @return full name of the user
	 */
	public String getFullname(String userId);
	
	/**
	 * Register one user
	 * 
	 * @param userId
	 * @param password
	 * @param firstname
	 * @param lastname
	 * @return boolean
	 */
	public boolean registerUser(String userId, String password, String firstname, String lastname);

}
