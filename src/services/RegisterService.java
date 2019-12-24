package services;

import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;

public class RegisterService {
	// input: user_id + password + first_name + last_name
	// output: empty (o/w)
	public boolean verifyRegister(JSONObject input, JSONObject output) {
		DBConnection connection = DBConnectionFactory.getConnection();
		try {
			String userId = input.getString("user_id");
			String password = input.getString("password");
			String firstname = input.getString("first_name");
			String lastname = input.getString("last_name");
			if (connection.registerUser(userId, password, firstname, lastname)) {
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
