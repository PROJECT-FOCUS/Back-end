package services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import commons.ActualUsageItem;
import commons.AppCategoryItem;
import commons.ExpectedUsageItem;
import db.DBConnection;
import db.DBConnectionFactory;

public class RecommendService {
	

	// input  = { 'user_id' : 'xxxx' }
	// output = { ‘recommendation' : [ ‘recommend-text-1', ‘recommend-text-2', … ] }

	public boolean recommend(JSONObject input, JSONObject output) {
		DBConnection connection = DBConnectionFactory.getConnection();
		
		try {
			// parse input
			String userId = input.getString("user_id");
			// prepare
			Set<String> appIds = connection.getUserApps(userId);
			Set<AppCategoryItem> categories = connection.getAppCategories(appIds);
			Set<ActualUsageItem> actualUsage = connection.getActualUsage(userId);
			Set<ExpectedUsageItem> expectUsage = connection.getExpectedUsage(userId);
			Map<String,Long> usageMap = new HashMap<>();	//key = category, value = usage
			Map<String,String> categoryMap = new HashMap<>();	//key = appid, value = category
			for(AppCategoryItem category:categories) {
				categoryMap.put(category.getAppId(),category.getCategory());
			}
			// aggregate usage by "category"
			for(ActualUsageItem actual_item:actualUsage) {
				String app_id = actual_item.getAppId();
				String category = categoryMap.get(app_id);
				Duration actual_usage = actual_item.getUsage();
				for(ExpectedUsageItem expect_item:expectUsage) {
					if(app_id.contentEquals(expect_item.getAppId())){
						Duration expect_usage = expect_item.getUsage();
						usageMap.put(category,usageMap.getOrDefault(category, (long) 0)+actual_usage.getSeconds()-expect_usage.getSeconds());
						break;
					}
				}
			}
			// compare and recommend 
			JSONArray recArray = new JSONArray();
			for(Map.Entry<String, Long> entry : usageMap.entrySet()){
				String category = entry.getKey();
				long difference = entry.getValue();
				if(difference<=0) {
					recArray.put( "Good job! You have done well on " + category + " apps. Keep it up!");
				} else {
					recArray.put( "Oops! You are a heavy user on " + category + " apps. Please cut it off.");
				}
			}
			// write to output
			output.put("recommendation", recArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		return true;
	}
}
