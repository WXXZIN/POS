package GUI;

public class MainProcess {
	static MainProcess main;
	public Login login;
	public MainPOS pos;

	public static void main(String[] args) {
		main = new MainProcess();
		main.login = new Login();
		main.login.setMain(main);
	}
	
	public void showMainAdmin() {
		login.dispose();
		this.pos = new MainPOS();
	}
	
	public void showMainUser() {
		login.dispose();
		this.pos = new MainPOS(!main.login.admin);
	}
}