package services;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import commons.ActualUsageItem;
import commons.ExpectedUsageItem;

import db.DBConnection;
import db.DBConnectionFactory;

public class OverviewService {
	// input: user_id
	// output: exp_usage + act_usage (if success) or empty (o/w)
	public boolean verifyOverview(JSONObject input, JSONObject output) {
		try {
			// verify if user exists first
			if (!userExists(input, output)) {
				return false;
			}
			// fetch data
			if (getExpectedUsage(input, output) && getActualUsage(input, output)) {
				return true;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean userExists(JSONObject input, JSONObject output) {
		DBConnection connection = DBConnectionFactory.getConnection();
		try {
			// get user_id
			String user_id = input.getString("user_id");
			// verify
			if (connection.verifyUser(user_id)) {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean getExpectedUsage(JSONObject input, JSONObject output) {
		DBConnection connection = DBConnectionFactory.getConnection();
		// convert ExpectedUsage items into a JSONObject(output)
		try {
			// get user id
			String user_id = input.getString("user_id");
			// get expected usage items
			Set<ExpectedUsageItem> expUsageItems = connection.getExpectedUsage(user_id);
			// create JSONArray
			JSONArray exp_usage = new JSONArray();
			for (ExpectedUsageItem expUsageItem : expUsageItems) {
				JSONArray expUsagePair = new JSONArray();
				expUsagePair.put(expUsageItem.getAppId());
				Duration usage = Duration.ofSeconds(expUsageItem.getUsage().getSeconds());
				expUsagePair.put(DurationHelper.reformatDuration(usage));
				// put JSONArray into JSONArray
				exp_usage.put(expUsagePair);
			}
			// put JSONArray into JSONObject(output)
			output.put("exp_usage", exp_usage);
			// return true if successful
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		// return false if not successful
		return false;
	}
	
	public boolean getActualUsage(JSONObject input, JSONObject output) {
		DBConnection connection = DBConnectionFactory.getConnection();
		// convert ActualUsage items into a JSONObject(output)
		try {
			// get user id
			String user_id = input.getString("user_id");
			// get actual usage items
			Set<ActualUsageItem> actUsageItems = connection.getActualUsage(user_id);
			// create JSONArray
			JSONArray act_usage = new JSONArray();
			for (ActualUsageItem actUsageItem : actUsageItems) {
				JSONArray actUsagePair = new JSONArray();
				actUsagePair.put(actUsageItem.getAppId());
				Duration usage = Duration.ofSeconds(actUsageItem.getUsage().getSeconds());
				actUsagePair.put(DurationHelper.reformatDuration(usage));
				// put JSONArray into JSONArray
				act_usage.put(actUsagePair);
			}
			// put JSONArray into JSONObject(output)
			output.put("act_usage", act_usage);
			// return true if successful
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		// return false if not successful
		return false;
	}
}
