package DB;

import java.sql.SQLException;
import java.util.Vector;

public class ReceiptDAO {
	DB db = new DB();
	Vector<String> receipts = null;
	
	public Vector<String> getSales() {
		return receipts;
	}

	public Vector<Receipt> getAllSales() {
		db.connectDB();
		db.sql = "select * from receipt";
		
		Vector<Receipt> list = new Vector<Receipt>();
		
		receipts = new Vector<String>();
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.rs = db.pstmt.executeQuery();
			
			while(db.rs.next()) {
				Receipt receipt = new Receipt();
				receipt.setDate(db.rs.getString("date"));
				receipt.setPurcahse(db.rs.getString("purchase"));
				receipt.setSales(db.rs.getInt("sales"));
				
				list.add(receipt);
				receipts.add(db.rs.getString("date"));
			}
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			db.closeDB();
		}
		
		return list;
	}
	
	public boolean insertReceipt(Receipt receipt) {
		db.connectDB();
		db.sql = "insert into receipt values(?, ?, ?)";
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setString(1, receipt.getDate());
			db.pstmt.setString(2, receipt.getPurchase());
			db.pstmt.setInt(3, receipt.getSales());
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