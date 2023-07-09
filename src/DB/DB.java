package DB;

import java.sql.*;

public class DB {

	private static final String jdbcUrl = "jdbc:oracle:thin:@localhost:1521/xepdb1";
	private static final String id = "POS";
	private static final String pw = "1234";
	
	public Connection conn = null;
	public PreparedStatement pstmt = null;
	public ResultSet rs = null;	
	public String sql = null;
	
	public void connectDB() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(jdbcUrl, id, pw);
		} 
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disconnectDB() {
		try {
			if (rs != null) rs.close();
			if (pstmt != null) pstmt.close();
			if (conn != null) conn.close();
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}