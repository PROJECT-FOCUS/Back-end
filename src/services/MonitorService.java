package services;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.time.Duration;

import org.json.JSONArray;
import org.json.JSONObject;

import commons.ActualUsageItem;
import commons.ExpectedUsageItem;
import db.DBConnection;
import db.DBConnectionFactory;

public class MonitorService {
	
	public boolean monitor(JSONObject input, JSONObject output)	{
		DBConnection connection = DBConnectionFactory.getConnection();
		try {
			// parse input
			String userId = input.getString("user_id");
			String date = input.getString("date");		/// TBA
			JSONArray actUsageArray = input.getJSONArray("act_usage");
			Set<ActualUsageItem> actUsageItems = new HashSet<>();
			for (int i = 0; i < actUsageArray.length(); ++i) {
				JSONArray item = actUsageArray.getJSONArray(i);
				String appId = item.getString(0);
				String usageStr = item.getString(1);
				actUsageItems.add(new ActualUsageItem(appId, usageStr));
			}
			
			// update ActualUsage
			connection.setActualUsage(userId, actUsageItems);
			
			// identify overtimed apps
			Set<ExpectedUsageItem>	expUsageItems = connection.getExpectedUsage(userId);
			Map<String, Duration>  expUsageMap = new HashMap<>();
			for (ExpectedUsageItem expUsageItem : expUsageItems)	{
				expUsageMap.put(expUsageItem.getAppId(), expUsageItem.getUsage());
			}
			JSONArray overtimeAppArray = new JSONArray();
			for (ActualUsageItem actUsageItem : actUsageItems) {
				String appId = actUsageItem.getAppId();
				Duration actUsage = actUsageItem.getUsage();
				Duration expUsage = expUsageMap.get(appId);
				if (null!=expUsage && actUsage.compareTo(expUsage)>0)	{
					overtimeAppArray.put(appId);
				}
			}
			// write to ouput
			output.put("overtimed_apps", overtimeAppArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return true;
	}
}
