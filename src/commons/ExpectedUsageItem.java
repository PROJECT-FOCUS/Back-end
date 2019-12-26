package commons;

import java.time.Duration;

public class ExpectedUsageItem {
	private final String appId;
	private final Duration usage; 
	public ExpectedUsageItem(String id, Duration use)	{
		appId = id;
		usage = use;
	}
	
	public String getAppId()	{
		return appId;
	}
	
	public Duration getUsage()	{
		return usage;
	}
}
