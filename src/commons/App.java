package commons;

import org.json.JSONException;
import org.json.JSONObject;

public class App {
	private String category;
	private String userId;
	private long difference;
	private String prompt;
	
	private App(ItemBuilder builder) {
		this.category = builder.category;
		this.userId = builder.userId;
		this.difference = builder.difference;
		this.prompt = builder.prompt;
	}

	public static class ItemBuilder {
		private String category;
		private String userId;
		private long difference;
		private String prompt;
		public ItemBuilder setCategory(String category) {
			this.category = category;
			return this;
		}
		public ItemBuilder setUserId(String userId) {
			this.userId = userId;
			return this;
		}
		public ItemBuilder setDifference(long difference) {
			this.difference = difference;
			return this;
		}
		public ItemBuilder setPrompt(String prompt) {
			this.prompt = prompt;
			return this;
		}
		public App build() {
			return new App(this);
		}
	}

	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("category", category);
			obj.put("user_id", userId);
			obj.put("usage_difference", difference);
			obj.put("prompt_message", prompt);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public String getUserId() {
		return userId;
	}
	public String getCategory() {
		return category;
	}
	public long getDifference() {
		return difference;
	}
	public String getPrompt() {
		return prompt;
	}
}
