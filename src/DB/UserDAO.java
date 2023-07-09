package DB;

import java.sql.SQLException;
import java.util.Vector;

public class UserDAO {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	DB db = new DB();
	Vector<String> userId = null;
	
	public Vector<String> getUserId() {
		return userId;
	}
	
	public Vector<UserDAO> getAllUser() {
		db.connectDB();
		db.sql = "select * from user where id not in ('admin')";
		
		Vector<UserDAO> userList = new Vector<UserDAO>();
		userId = new Vector<String>();
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.rs = db.pstmt.executeQuery();
			
			while(db.rs.next()) {
				UserDAO user = new UserDAO();
				user.setId(db.rs.getString("id"));
				
				userList.add(user);
				userId.add(db.rs.getString("id"));
				
			}
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			db.closeDB();
		}
		
		return userList;
	}
	
	public boolean insertUser(String userID) {
		db.connectDB();
		db.sql = "insert into user values(?)";
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setString(1, userID);
			db.pstmt.executeUpdate();
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		finally {
			db.closeDB();
		}
		return true;
	}
	
	public boolean deleteUser(String userID) {
		db.connectDB();
		db.sql = "delete from user where id = ?";
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setString(1, userID);
			db.pstmt.executeUpdate();
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		finally {
			db.closeDB();
		}
		return true;
	}
}
