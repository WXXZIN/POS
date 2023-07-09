package DB;

import java.sql.SQLException;
import java.util.Vector;

public class StockDAO {
	DB db = new DB();
	Vector<String> stocks = null;
	
	public Vector<String> getStock() {
		return stocks;
	}

	public Vector<Stock> getAllStock() {
		db.connectDB();
		db.sql = "select * from stock";
		
		Vector<Stock> list = new Vector<Stock>();
		
		stocks = new Vector<String>();
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.rs = db.pstmt.executeQuery();
			
			while(db.rs.next()) {
				Stock stock = new Stock();
				stock.setNumber(db.rs.getInt("number"));
				stock.setStock_Name(db.rs.getString("name"));
				stock.setStock_Count(db.rs.getInt("count"));
				stock.setStock_Price(db.rs.getInt("price"));
				
				if (db.rs.getInt("count") > 0)
					stocks.add(db.rs.getString("name"));
				
				list.add(stock);
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
	
	public int getPrice(String name) {
		db.connectDB();
		db.sql = "select price from stock where name = ?";
		int price = 0;
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setString(1, name);
			db.rs = db.pstmt.executeQuery();
			
			while(db.rs.next()) {
				price = db.rs.getInt("price");
			}
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			db.closeDB();
		}
		
		return price;
	}
	
	public int getCount(String name) {
		db.connectDB();
		db.sql = "select count from stock where name = ?";
		int count = 0;
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setString(1, name);
			db.rs = db.pstmt.executeQuery();
			
			while(db.rs.next()) {
				count = db.rs.getInt("count");
			}
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			db.closeDB();
		}
		
		return count;
	}
	
	public boolean insertStock(Stock stock) {
		db.connectDB();
		db.sql = "insert into stock(name, count, price) values(?, ?, ?)";
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setString(1, stock.getStock_Name());
			db.pstmt.setInt(2, stock.getStock_Count());
			db.pstmt.setInt(3, stock.getStock_Price());
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
	
	public boolean updateStock(int Stock_Count, int Purchase_Count, String Stock_Name) {
		db.connectDB();
		db.sql = "update stock set count = ? - ?  where name = ?";
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setInt(1, Stock_Count);
			db.pstmt.setInt(2, Purchase_Count);
			db.pstmt.setString(3, Stock_Name);
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
	
	public boolean updateStock(Stock stock) {
		db.connectDB();
		db.sql = "update stock set name = ?, count = ?, price = ? where number = ?";
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setString(1, stock.getStock_Name());
			db.pstmt.setInt(2, stock.getStock_Count());
			db.pstmt.setInt(3, stock.getStock_Price());
			db.pstmt.setInt(4, stock.getNumber());
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
	
	public boolean deleteStock(int number) {
		db.connectDB();
		db.sql = "delete from stock where number = ?";
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setInt(1, number);
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