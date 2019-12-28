package services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import commons.ActualUsageItem;
import commons.App;
import commons.AppCategoryItem;
import commons.App.ItemBuilder;
import commons.ExpectedUsageItem;
import db.DBConnection;
import db.DBConnectionFactory;

public class RecommendService {
	public List<App> recommend(String userId){
		List<App> apps = new ArrayList<>();
		DBConnection connection = DBConnectionFactory.getConnection();
		Set<String> appIds = connection.getUserApps(userId);
		Set<AppCategoryItem> categories = connection.getAppCategories(appIds);
		Set<ActualUsageItem> actualUsage = connection.getActualUsage(userId);
		Set<ExpectedUsageItem> expectUsage = connection.getExpectedUsage(userId);
		//key = category, value = usage
		Map<String,Long> usageMap = new HashMap<>();
		//key = appid, value = category
		Map<String,String> categoryMap = new HashMap<>();
		for(AppCategoryItem category:categories) {
			categoryMap.put(category.getAppId(),category.getCategory());
		}
		//get all categories' usage
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
		//update the results, return list of objects
		for(Map.Entry<String, Long> entry:usageMap.entrySet()){
			String category = entry.getKey();
			long difference = entry.getValue();
			App.ItemBuilder builder = new App.ItemBuilder();
			builder.setUserId(userId);
			builder.setCategory(entry.getKey());
			builder.setDifference(difference);
			if(difference<=0) {
				builder.setPrompt("Good job! You have not exceeded the expected time for "+category+". Keep it up!");
			}else{
				builder.setPrompt("You exceeded the expected time for "+category+". Please stay away from it!");
			}
			apps.add(builder.build());
			break;
		}
		return apps;
	}
}
