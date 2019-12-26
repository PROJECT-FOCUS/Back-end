package App;

import java.time.Duration;

import org.json.JSONException;
import org.json.JSONObject;

public class app {
	private String appId;
	private String userId;
	private Duration actual;
	private Duration expect;
	private int difference;
	private String prompt;
	
	private app(ItemBuilder builder) {
		this.appId = builder.appId;
		this.userId = builder.userId;
		this.actual = builder.actual;
		this.expect = builder.expect;
		this.difference = builder.difference;
		this.prompt = builder.prompt;
	}

	public static class ItemBuilder {
		private String appId;
		private String userId;
		private Duration actual;
		private Duration expect;
		private int difference;
		private String prompt;
		public ItemBuilder setAppId(String appId) {
			this.appId = appId;
			return this;
		}
		public ItemBuilder setUserId(String userId) {
			this.userId = userId;
			return this;
		}
		public ItemBuilder setActual(Duration actual) {
			this.actual = actual;
			return this;
		}
		public ItemBuilder setExpect(Duration expect) {
			this.expect = expect;
			return this;
		}
		public ItemBuilder setDifference(int difference) {
			this.difference = difference;
			return this;
		}
		public ItemBuilder setPrompt(String prompt) {
			this.prompt = prompt;
			return this;
		}
		public app build() {
			return new app(this);
		}
	}

	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("app_id", appId);
			obj.put("user_id", userId);
			obj.put("actual_usage", actual);
			obj.put("expect_usage", expect);
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
	public String getAppId() {
		return appId;
	}
	public Duration getActual() {
		return actual;
	}
	public Duration getExpect() {
		return expect;
	}
	public int getDifference() {
		return difference;
	}
	public String getPrompt() {
		return prompt;
	}
}
