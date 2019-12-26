package services;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import App.app;
import App.app.ItemBuilder;
import commons.ActualUsageItem;
import commons.ExpectedUsageItem;
import db.DBConnection;
import db.DBConnectionFactory;

public class RecommendService {
	public List<app> recommend(String userId){
		List<app> apps = new ArrayList<>();
		DBConnection connection = DBConnectionFactory.getConnection();
		Set<ActualUsageItem> actualUsage = connection.getActualUsage(userId);
		Set<ExpectedUsageItem> expectUsage = connection.getExpectedUsage(userId);
		for(ActualUsageItem actual_item:actualUsage) {
			String app_id = actual_item.getAppId();
			Duration actual_usage = actual_item.getUsage();
			for(ExpectedUsageItem expect_item:expectUsage) {
				if(app_id.contentEquals(expect_item.getAppId())) {
					Duration expect_usage = expect_item.getUsage();
					int difference = actual_usage.compareTo(expect_usage);
					ItemBuilder builder = new ItemBuilder();
					builder.setUserId(userId);
					builder.setAppId(app_id);
					builder.setActual(actual_usage);
					builder.setExpect(expect_usage);
					builder.setDifference(difference);
					if(difference<=0) {
						builder.setPrompt("Good job! You have not exceeded the expected time for "+app_id+". Keep it up!");
					}else{
						builder.setPrompt("You exceeded the expected time for "+app_id+". Please stay away from it!");
					}
					apps.add(builder.build());
					break;
				}
			}
		}
		return apps;
	}
}
