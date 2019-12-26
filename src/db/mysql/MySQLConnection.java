package db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import commons.ActualUsageItem;
import commons.ExpectedUsageItem;
import db.DBConnection;

public class MySQLConnection implements DBConnection {

	private Connection conn;
	
	public MySQLConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			conn = DriverManager.getConnection(MySQLDBUtil.URL);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean verifyLogin(String userId, String password) {
		// TODO Auto-generated method stub
		if (conn == null) {
			return false;
		}
		try {
			String sql = "SELECT user_id FROM users WHERE user_id = ? AND password = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean registerUser(String userId, String password, String firstname, String lastname) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}

		try {
			String sql = "INSERT IGNORE INTO users VALUES (?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, password);
			ps.setString(3, firstname);
			ps.setString(4, lastname);
			
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;	
	}
	
	@Override
	public Set<ExpectedUsageItem> getExpectedUsage(String userId)	{
		if (conn == null) {
			return null;
		}
		
		Set<ExpectedUsageItem> expUsageItems = new HashSet<>();
		
		try {
			String sql = "SELECT app_id, exp_usage FROM expected_usage WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String appId = rs.getString("app_id");
				Long durationInSec = rs.getLong("exp_usage");
		        Duration duration = Duration.ofSeconds(durationInSec);
		        expUsageItems.add(new ExpectedUsageItem(appId, duration));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return expUsageItems;
	}

	
	@Override
	public void deleteExpectedUsage(String userId)		{
		if (conn == null) {
			System.err.println("DB connection failed");
			return;
		}

		try {
			String sql = "DELETE FROM expected_usage WHERE user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void setExpectedUsage(String userId, Set<ExpectedUsageItem> items)	{
		if (conn == null) {
			System.err.println("DB connection failed");
			return;
		}
		try {
			String sql = "INSERT IGNORE INTO expected_usage VALUES (?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,  userId);
			for (ExpectedUsageItem expUsageItem : items) {
				ps.setString(2, expUsageItem.getAppId());
				ps.setLong(3, expUsageItem.getUsage().getSeconds() );
				ps.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	@Override
	public Set<ActualUsageItem> getActualUsage(String userId)	{
		if (conn == null) {
			return null;
		}
		
		Set<ActualUsageItem> actUsageItems = new HashSet<>();
		
		try {
			String sql = "SELECT app_id, act_usage FROM actual_usage WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String appId = rs.getString("app_id");
				Long durationInSec = rs.getLong("act_usage");
		        Duration duration = Duration.ofSeconds(durationInSec);
		        actUsageItems.add(new ActualUsageItem(appId, duration));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return actUsageItems;
	}

	@Override
	public void deleteActualUsage(String userId)	{
		if (conn == null) {
			System.err.println("DB connection failed");
			return;
		}

		try {
			String sql = "DELETE FROM actual_usage WHERE user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setActualUsage(String userId, Set<ActualUsageItem> items)	{
		if (conn == null) {
			System.err.println("DB connection failed");
			return;
		}
		try {
			String sql = "INSERT IGNORE INTO actual_usage VALUES (?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			for (ActualUsageItem actUsageItem : items) {
				ps.setString(2, actUsageItem.getAppId());
				ps.setLong(3, actUsageItem.getUsage().getSeconds() );
				ps.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
