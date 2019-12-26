package external;

import java.util.Set;
import java.util.HashSet;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import commons.AppCategoryItem;

public class AndroidAppStoreClient {
	public final static String GOOGLE_STORE_URL = "https://play.google.com/store/apps/details?id=";

	
	public Set<AppCategoryItem> getAppCategory(Set<String> appIds)	{
		Set<AppCategoryItem> appCategories = new HashSet<>();
		try {
			for (String appId : appIds) {
				String query_url = GOOGLE_STORE_URL + appId;		// appId:  package name;
				Document doc = Jsoup.connect(query_url).get();
				Elements elems = doc.select("a[itemprop=genre]");
				String category = elems.first().text();
				appCategories.add(new AppCategoryItem(appId, category));
			}
        } catch (Exception e) {
        	e.printStackTrace();
        }
		
		return appCategories;
	}
	
	// for testing purpose
	public static void main(String[] args)	{
		AndroidAppStoreClient client = new AndroidAppStoreClient();
		Set<String>	appIds = new HashSet<>();
		appIds.add("com.google.android.youtube");
		appIds.add("com.android.chrome");
		appIds.add("org.mozilla.firefox");
		appIds.add("com.tencent.mm");	// wechat
		Set<AppCategoryItem> appCategories = client.getAppCategory(appIds);
		System.out.println(appCategories);
	}
	
}