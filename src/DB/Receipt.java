package DB;

public class Receipt {
	
	// sales Table
	private String date;
	private String purchase;
	private int sales;
	
	// Getter & Setter 메소드
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPurchase() {
		return purchase;
	}
	public void setPurcahse(String purchase) {
		this.purchase = purchase;
	}
	public int getSales() {
		return sales;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}	
}
