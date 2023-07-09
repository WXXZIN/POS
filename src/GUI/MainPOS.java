package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainPOS extends JFrame implements ActionListener {
	private JLabel iconLabel;
	private JLabel user = new JLabel("");
	private JButton btnLogout;
	private JButton btnSell;
	private JButton btnUM;
	private JButton btnReceipt;
	private JButton btnSM;
	private boolean admin;
	private ImageIcon imgUser = new ImageIcon("img/imgUser.png");
	private ImageIcon imgLogout = new ImageIcon("img/imgLogout.png");
	private ImageIcon imgSell = new ImageIcon("img/imgSell.png");
	private ImageIcon imgUM = new ImageIcon("img/imgUM.png");
	private ImageIcon imgReceipt = new ImageIcon("img/imgReceipt.png");
	private ImageIcon imgSM = new ImageIcon("img/imgSM.png");
	
	public MainPOS() {
		admin = true;
		POS_display();
		user.setText("Administrator");
	}
	
	public MainPOS(boolean a) {
		admin = false;
		POS_display();
		user.setText("User");
	}
	
	private void POS_display() {
		Container ct = getContentPane();
		ct.setLayout(null);
		ct.setBackground(new Color(248, 248, 248));
		
		JPanel userPanel = new JPanel();
		userPanel.setLayout(null);
		userPanel.setBackground(Color.white);
		
		iconLabel = new JLabel(imgUser);
		
		userPanel.add(iconLabel).setBounds(10, 5, 30, 30);
		userPanel.add(user).setBounds(50, 5, 100, 30);
		
		btnLogout = new JButton(imgLogout);
		btnLogout.setBorderPainted(false);
		btnLogout.setContentAreaFilled(false);
		btnLogout.setRolloverIcon(new ImageIcon("img/Sel_imgLogout.png"));
		
		btnSell = new JButton(imgSell);
		btnSell.setBorderPainted(false);
		btnSell.setRolloverIcon(new ImageIcon("img/Sel_imgSell.png"));
		
		btnUM = new JButton(imgUM);
		btnUM.setBorderPainted(false);
		btnUM.setRolloverIcon(new ImageIcon("img/Sel_imgUM.png"));
		
		btnReceipt = new JButton(imgReceipt);
		btnReceipt.setBorderPainted(false);
		btnReceipt.setRolloverIcon(new ImageIcon("img/Sel_imgReceipt.png"));
		
		btnSM = new JButton(imgSM);
		btnSM.setBorderPainted(false);
		btnSM.setRolloverIcon(new ImageIcon("img/Sel_imgSM.png"));
		
		ct.add(userPanel).setBounds(675, 15, 250, 40);
		
		ct.add(btnLogout).setBounds(935, 15, 40, 40);
		
		ct.add(btnSell).setBounds(25, 130, 300, 300);
		ct.add(btnUM).setBounds(350, 130, 300, 300);
		ct.add(btnReceipt).setBounds(675, 130, 300, 140);
		ct.add(btnSM).setBounds(675, 290, 300, 140);
		
		btnLogout.addActionListener(this);
		btnSell.addActionListener(this);
		btnUM.addActionListener(this);
		btnReceipt.addActionListener(this);
		btnSM.addActionListener(this);

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
			
			if (admin)
				new POS_Sell();
			
			else 
				new POS_Sell(!admin);
		}

		else if (obj == btnUM) {
			if (admin) {
				this.dispose();
				new POS_UM();
			}
			
			else 
				JOptionPane.showMessageDialog(null, "권한이 없습니다.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		else if (obj == btnReceipt) {
			this.dispose();
			
			if (admin)
				new POS_Receipt();
			
			else {
				new POS_Receipt(!admin); 
			}
		}
		
		else if (obj == btnSM) {
			this.dispose();
			if (admin)
				new POS_SM();
			
			else
				new POS_SM(!admin);
		}
	}
}