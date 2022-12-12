package DB;

import java.sql.SQLException;
import java.util.Vector;

public class ProductDAO {
	DB db = new DB();
	Vector<String> products = null;
	
	public Vector<String> getProduct() {
		return products;
	}

	public Vector<Product> getAllProduct() {
		db.connectDB();
		db.sql = "select * from product";
		
		Vector<Product> list = new Vector<Product>();
		
		products = new Vector<String>();
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.rs = db.pstmt.executeQuery();
			
			while(db.rs.next()) {
				Product product = new Product();
				product.setPid(db.rs.getInt("pid"));
				product.setProduct_name(db.rs.getString("name"));
				product.setProduct_count(db.rs.getInt("count"));
				product.setProduct_price(db.rs.getInt("price"));
				
				if (db.rs.getInt("count") > 0)
					products.add(db.rs.getString("name"));
				
				list.add(product);
			}
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			db.disconnectDB();
		}
		
		return list;
	}
	
	public int getPrice(String name) {
		db.connectDB();
		db.sql = "select price from product where name = ?";
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
			db.disconnectDB();
		}
		
		return price;
	}
	
	public int getCount(String name) {
		db.connectDB();
		db.sql = "select count from product where name = ?";
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
			db.disconnectDB();
		}
		
		return count;
	}
	
	public int insertProduct(Product product) {
		int result = 0;
		
		db.connectDB();
		db.sql = "insert into product(name, count, price) values(?, ?, ?)";
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setString(1, product.getProduct_name());
			db.pstmt.setInt(2, product.getProduct_count());
			db.pstmt.setInt(3, product.getProduct_price());
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
	
	public int updateProduct(String type, int Purchase_Count, String Product_Name) {
		int result = 0;
		
		db.connectDB();
		
		if (type.equals("판매"))
			db.sql = "update product set count = count - ?  where name = ?";
		else
			db.sql = "update product set count = count + ?  where name = ?";
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setInt(1, Purchase_Count);
			db.pstmt.setString(2, Product_Name);
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
	
	public int updateProduct(Product product) {
		int result = 0;
		
		db.connectDB();
		db.sql = "update product set name = ?, count = ?, price = ? where pid = ?";
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setString(1, product.getProduct_name());
			db.pstmt.setInt(2, product.getProduct_count());
			db.pstmt.setInt(3, product.getProduct_price());
			db.pstmt.setInt(4, product.getPid());
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
	
	public int deleteProduct(int pid) {
		int result = 0;
		
		db.connectDB();
		db.sql = "delete from product where pid = ?";
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setInt(1, pid);
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