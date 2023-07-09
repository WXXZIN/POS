package DB;

import java.sql.SQLException;
import java.util.Vector;

public class UserDAO {
	
	DB db = new DB();
	Vector<String> userId = null;
	
	public Vector<String> getUserId() {
		return userId;
	}
	
	public Vector<User> getAllUserId() {
		db.connectDB();
		db.sql = "select * from users where id not in ('admin')";
		
		Vector<User> userList = new Vector<User>();
		userId = new Vector<String>();
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.rs = db.pstmt.executeQuery();
			
			while (db.rs.next()) {
				User user = new User();
				user.setId(db.rs.getString("id"));
				
				userList.add(user);
				userId.add(db.rs.getString("id"));
			}
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		} 
		
		finally {
			db.disconnectDB();
		}
		
		return userList;
	}
	
	public int insertUser(String userId) {
		int result = 0;
		
		db.connectDB();
		db.sql = "insert into users values(?)";
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setString(1, userId);
			result = db.pstmt.executeUpdate();
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		} 
		
		finally {
			db.disconnectDB();
		}
		
		return result;
	}
	
	public int deleteUser(String userId) {
		int result = 0;
		
		db.connectDB();
		db.sql = "delete from users where id = ?";
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setString(1, userId);
			result = db.pstmt.executeUpdate();
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		} 
		
		finally {
			db.disconnectDB();
		}
		
		return result;
	}
}