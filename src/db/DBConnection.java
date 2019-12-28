package db;

import java.util.Set;

import commons.ExpectedUsageItem;
import commons.ActualUsageItem;
import commons.AppCategoryItem;


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
	 * Register one user
	 * 
	 * @param userId
	 * @param password
	 * @param firstname
	 * @param lastname
	 * @return boolean
	 */
	public boolean registerUser(String userId, String password, String firstname, String lastname);
	
	// get first & last name
	public String[] getFullname(String userId);

	// Given a userId, return set of appIds
	public Set<String> getUserApps(String userId); 
	// Given a set of appIds, return set of <appId, category> pairs 
	public Set<AppCategoryItem> getAppCategories(Set<String> appIds); 
	// set category for a given set of appIds. 
	public void setAppCategories(Set<AppCategoryItem> appCategories); 
	
	//
	public Set<ExpectedUsageItem> getExpectedUsage(String userId);
	// 
	public void deleteExpectedUsage(String userId);
	//
	public void setExpectedUsage(String userId, Set<ExpectedUsageItem> items); 
	
	//
	public Set<ActualUsageItem> getActualUsage(String userId);
	//
	public void deleteActualUsage(String userId);
	//
	public void setActualUsage(String userId, Set<ActualUsageItem> items);
	
}
