package DB;

import java.sql.*;

public class DB {

	public static final String jdbcUrl = "jdbc:mysql://localhost/pos?";
	public static final String id = "root";
	public static final String pw = "1234";
	
	public Connection conn = null;
	public PreparedStatement pstmt = null;
	public ResultSet rs = null;	
	public String sql;
	
	public void connectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(jdbcUrl, id, pw);
		} 
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeDB() {
		try {
			pstmt.close();
			conn.close();
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}