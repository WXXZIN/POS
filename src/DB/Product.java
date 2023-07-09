package DB;

public class Stock {

	// stock Table
	private int number;
	private String Stock_Name;
	private int Stock_Count;
	private int Stock_Price;
	
	// Getter & Setter 메소드
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getStock_Name() {
		return Stock_Name;
	}
	public void setStock_Name(String stock_Name) {
		Stock_Name = stock_Name;
	}
	public int getStock_Count() {
		return Stock_Count;
	}
	public void setStock_Count(int stock_Count) {
		Stock_Count = stock_Count;
	}
	public int getStock_Price() {
		return Stock_Price;
	}
	public void setStock_Price(int stock_Price) {
		Stock_Price = stock_Price;
	}
}