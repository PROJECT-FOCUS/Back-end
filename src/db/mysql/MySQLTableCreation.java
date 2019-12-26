package db.mysql;

import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Connection;

public class MySQLTableCreation {
	// Run this as Java application to reset db schema.
		public static void main(String[] args) {
			try {
				// Step 1 Connect to MySQL.
				System.out.println("Connecting to " + MySQLDBUtil.URL);
				Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
				Connection conn = DriverManager.getConnection(MySQLDBUtil.URL);

				if (conn == null) {
					return;
				}

				// Step 2 Drop tables in case they exist.
				Statement statement = conn.createStatement();
				String sql ;
				sql = "DROP TABLE IF EXISTS expected_usage";
				statement.executeUpdate(sql);
				sql = "DROP TABLE IF EXISTS actual_usage";
				statement.executeUpdate(sql);
				sql = "DROP TABLE IF EXISTS users";
				statement.executeUpdate(sql);
				sql = "DROP TABLE IF EXISTS apps";
				statement.executeUpdate(sql);
				
				// Step 3 Create new tables
				sql = "CREATE TABLE users (" 
						+ "user_id VARCHAR(255) NOT NULL," 
						+ "password VARCHAR(255) NOT NULL,"
						+ "first_name VARCHAR(255)," 
						+ "last_name VARCHAR(255)," 
						+ "PRIMARY KEY (user_id)" 
						+ ")";
				statement.executeUpdate(sql);
				
				sql = "CREATE TABLE apps (" 
						+ "user_id VARCHAR(255) NOT NULL," 
						+ "app_id VARCHAR(255) NOT NULL," 
						+ "package VARCHAR(255) NOT NULL,"
						+ "category VARCHAR(255) NOT NULL,"
						+ "PRIMARY KEY (user_id)" 
						+ ")";
				statement.executeUpdate(sql);
				
				sql = "CREATE TABLE expected_usage (" 
						+ "user_id VARCHAR(255) NOT NULL," 
						+ "app_id VARCHAR(255) NOT NULL," 
						+ "exp_usage BIGINT NOT NULL," 
						+ "PRIMARY KEY (user_id, app_id)," 
						+ "FOREIGN KEY (user_id) REFERENCES users(user_id)," 
						+ "FOREIGN KEY (app_id) REFERENCES apps(app_id)" 
						+ ")";
				statement.executeUpdate(sql);
				
				sql = "CREATE TABLE actual_usage (" 
						+ "user_id VARCHAR(255) NOT NULL," 
						+ "app_id VARCHAR(255) NOT NULL," 
						+ "act_usage BIGINT NOT NULL,"
						+ "PRIMARY KEY (user_id, app_id)," 
						+ "FOREIGN KEY (user_id) REFERENCES users(user_id)," 
						+ "FOREIGN KEY (app_id) REFERENCES apps(app_id)" 
						+ ")";
				statement.executeUpdate(sql);
				
				// Step 4: insert fake user 1111/3229c1097c00d497a0fd282d586be050
				sql = "INSERT INTO users VALUES('1111', '3229c1097c00d497a0fd282d586be050', 'John', 'Smith')";
				statement.executeUpdate(sql);
				
				//
				conn.close();
				System.out.println("Import done successfully");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
