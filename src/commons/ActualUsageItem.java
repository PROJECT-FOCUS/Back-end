package commons;

import java.time.Duration;

public class ActualUsageItem {
	private final String appId;
	private final Duration usage; 
	public ActualUsageItem(String id, Duration use)	{
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
