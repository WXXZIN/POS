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
		
		Vector<Receipt> receiptList = new Vector<Receipt>();		
		receipts = new Vector<String>();
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.rs = db.pstmt.executeQuery();
			
			while (db.rs.next()) {
				Receipt receipt = new Receipt();
				receipt.setDate(db.rs.getString("r_date"));
				receipt.setRid(db.rs.getInt("rid"));
				receipt.setType(db.rs.getString("type"));
				receipt.setPurchase(db.rs.getString("purchase"));
				receipt.setSales(db.rs.getInt("sales"));
				receipt.setRefund(db.rs.getBoolean("refund"));
				receiptList.add(receipt);
				receipts.add(db.rs.getString("rid"));
			}
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		} 
		
		finally {
			db.disconnectDB();
		}
		
		return receiptList;
	}
	
	public int insertReceipt(Receipt receipt) {
		int result = 0;
		
		db.connectDB();
		db.sql = "insert into receipt(type, purchase, sales) values(?, ?, ?)";
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setString(1, receipt.getType());
			db.pstmt.setString(2, receipt.getPurchase());
			db.pstmt.setInt(3, receipt.getSales());
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
	
	public int updateReceipt(Receipt receipt) {
		int result = 0;
		
		db.connectDB();
		db.sql = "update receipt set refund = 1 where rid = ?";
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setInt(1, receipt.getRid());
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