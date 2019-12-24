package services;

import org.json.JSONObject;
import servlets.JsonHelper;

import db.DBConnection;
import db.DBConnectionFactory;

public class LoginService {

	// input: user_id + password
	// output: user_id + name (if success) or empty (o/w)
	public boolean verifyLogin(JSONObject input, JSONObject output) {
		DBConnection connection = DBConnectionFactory.getConnection();
		try {
			String userId = input.getString("user_id");
			String password = input.getString("password");
			if (connection.verifyLogin(userId, password)) {
				//output.put("user_id", userId).put("name", connection.getFullname(userId));
				return true;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return false;
	}
}
