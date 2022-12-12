package DB;

public class Receipt {

	private String date;
	private int rid;
	private String type;
	private String purchase;
	private int sales;
	private boolean refund;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPurchase() {
		return purchase;
	}
	public void setPurchase(String purchase) {
		this.purchase = purchase;
	}
	public int getSales() {
		return sales;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	public boolean getRefund() {
		return refund;
	}
	public void setRefund(boolean refund) {
		this.refund = refund;
	}
}