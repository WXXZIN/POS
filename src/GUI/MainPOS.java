package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainPOS extends JFrame implements ActionListener {
	private JLabel userIcon;
	private JLabel userName = new JLabel("");
	private JButton btnLogout;
	private JButton btnSell;
	private JButton btnUM;
	private JButton btnReceipt;
	private JButton btnPM;
	private boolean admin;
	private ImageIcon imgUser = new ImageIcon("img/imgUser.png");
	private ImageIcon imgLogout = new ImageIcon("img/imgLogout.png");
	private ImageIcon imgSell = new ImageIcon("img/imgSell.png");
	private ImageIcon imgUM = new ImageIcon("img/imgUM.png");
	private ImageIcon imgReceipt = new ImageIcon("img/imgReceipt.png");
	private ImageIcon imgPM = new ImageIcon("img/imgSM.png");
	
	public MainPOS(boolean admin) {
		this.admin = admin;
		userName.setText(admin ? "Admin" : "User");
		displayPOS();
	}
	
	private void displayPOS() {
		Container ct = getContentPane();
		ct.setLayout(null);
		ct.setBackground(new Color(248, 248, 248));
		
		userIcon = new JLabel(imgUser);
		
		JPanel userPanel = new JPanel();
		userPanel.setLayout(null);
		userPanel.setBackground(Color.white);
		userPanel.add(userIcon).setBounds(10, 5, 30, 30);
		userPanel.add(userName).setBounds(50, 5, 100, 30);
		
		btnLogout = new JButton(imgLogout);
		btnLogout.setBorderPainted(false);
		btnLogout.setContentAreaFilled(false);
		btnLogout.setRolloverIcon(new ImageIcon("img/Sel_imgLogout.png"));
		
		btnSell = new JButton(imgSell);
		btnSell.setBorderPainted(false);
		btnSell.setRolloverIcon(new ImageIcon("img/Sel_imgSell.png"));
		
		btnUM = new JButton(imgUM);
		btnUM.setEnabled(admin);
		btnUM.setBorderPainted(false);
		btnUM.setRolloverIcon(new ImageIcon("img/Sel_imgUM.png"));
		
		btnReceipt = new JButton(imgReceipt);
		btnReceipt.setBorderPainted(false);
		btnReceipt.setRolloverIcon(new ImageIcon("img/Sel_imgReceipt.png"));
		
		btnPM = new JButton(imgPM);
		btnPM.setBorderPainted(false);
		btnPM.setRolloverIcon(new ImageIcon("img/Sel_imgSM.png"));
		
		ct.add(userPanel).setBounds(675, 15, 250, 40);
		
		ct.add(btnLogout).setBounds(935, 15, 40, 40);
		
		ct.add(btnSell).setBounds(25, 130, 300, 300);
		ct.add(btnUM).setBounds(350, 130, 300, 300);
		ct.add(btnReceipt).setBounds(675, 130, 300, 140);
		ct.add(btnPM).setBounds(675, 290, 300, 140);
		
		btnLogout.addActionListener(this);
		btnSell.addActionListener(this);
		btnUM.addActionListener(this);
		btnReceipt.addActionListener(this);
		btnPM.addActionListener(this);

		setSize(1016, 599);
		setTitle("POS");
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();	
		MainProcess main;
		
		if (obj == btnLogout) {
			this.dispose();
			main = new MainProcess();
			main.login = new Login();
			main.login.setMain(main);
		}
		
		else if (obj == btnSell) {
			this.dispose();
			
			new POS_Sell(admin);
		}

		else if (obj == btnUM) {
			this.dispose();
			new POS_UM();		
		}
		
		else if (obj == btnReceipt) {
			this.dispose();
			
			new POS_Receipt(admin);
			
		}
		
		else if (obj == btnPM) {
			this.dispose();
			
			new POS_PM(admin);
		}
	}
}