package services;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;

public class LogoutService {
	// session: not null or null
	// output: empty (o/w)
	public boolean verifyLogout(HttpSession session, JSONObject output) {
		if (session != null) {
			return true;
		}
		return false;
	}
}
