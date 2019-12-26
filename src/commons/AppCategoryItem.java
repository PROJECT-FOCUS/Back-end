package commons;


public class AppCategoryItem {
	private final String appId;
	private final String category; 
	public AppCategoryItem(String id, String cat)	{
		appId = id;
		category = cat;
	}
	
	public String getAppId()	{
		return appId;
	}
	
	public String getCategory()	{
		return category;
	}
}
