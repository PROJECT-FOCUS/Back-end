package services;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import commons.AppCategoryItem;
import commons.ExpectedUsageItem;
import commons.DurationHelper;

import external.AndroidAppStoreClient;

import db.DBConnection;
import db.DBConnectionFactory;

public class UpdateService {
	// input: user_id + ((app_id, exp_usage), (app_id, exp_usage), ...)
	// output: empty (o/w)
	public boolean verifyUpdate(JSONObject input, JSONObject output) {
		try {
			if (setExpectedUsage(input, output) && setAppCategories(input, output)) {
				return true;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
		
	public boolean setExpectedUsage(JSONObject input, JSONObject output) {
		DBConnection connection = DBConnectionFactory.getConnection();
		// delete ExpectedUsage set according to this user_id
		// and write the new set into database
		try {
			// get user id
			String user_id = input.getString("user_id");
			// get JASONArray
			JSONArray expUsageArray = input.getJSONArray("exp_usage");
			// create a HashSet to receive JASONArray
			Set<ExpectedUsageItem> items = new HashSet<ExpectedUsageItem>();
			// convert JASONArray into HashSet
			for (int i = 0; i < expUsageArray.length(); ++i) {
				// get JASONArray from JASONArray
				JSONArray expUsagePair = expUsageArray.getJSONArray(i);
				// get appId and expected usage time
				String appId = "";
				String usage_string = "";
				appId = expUsagePair.getString(0);
				usage_string = expUsagePair.getString(1);
				// convert string into duration
				Duration usage = Duration.ofSeconds(DurationHelper.getSecondsFromFormattedDuration(usage_string));
				// create an ExpectedUsageItem
				ExpectedUsageItem item = new ExpectedUsageItem(appId, usage);
				// add it into the set
				items.add(item);
			}
			// delete ExpectedUsage set according to this user_id
			connection.deleteExpectedUsage(user_id);
			// write the new set into database
			connection.setExpectedUsage(user_id, items);
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
	
	public boolean setAppCategories(JSONObject input, JSONObject output) {
		DBConnection connection = DBConnectionFactory.getConnection();
		// delete ExpectedUsage set according to this user_id
		// and write the new set into database
		try {
			// get JASONArray
			JSONArray expUsageArray = input.getJSONArray("exp_usage");		
			// convert all appIds from JASONArray into one HashSet
			Set<String> appIds = new HashSet<String>();
			for (int i = 0; i < expUsageArray.length(); ++i) {
				JSONArray expUsagePair = expUsageArray.getJSONArray(i);
				String appId = "";
				// get app id
				appId = expUsagePair.getString(0);
				// add it into HashSet
				appIds.add(appId);
			}
			// get app categories by app ids and save into another HashSet
			AndroidAppStoreClient client = new AndroidAppStoreClient();
			Set<AppCategoryItem> appCategories = client.getAppCategory(appIds);
			// write the set into database
			connection.setAppCategories(appCategories);
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
