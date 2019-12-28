package services;

import org.json.JSONObject;

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
				String[] name = connection.getFullname(userId);
				output.put("first_name", name[0]).put("last_name", name[1]);
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
