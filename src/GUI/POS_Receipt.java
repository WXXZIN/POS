package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DB.*;

public class POS_Receipt extends JFrame implements ActionListener{
	private String [] header = {"날짜", "구매", "금액"};
	private JLabel iconLabel;
	private JLabel user = new JLabel("");
	private JButton btnBack;
	private JButton btnLogout;
	private JTable table;
	private boolean admin;
	private ImageIcon imgBack = new ImageIcon("img/imgBack.png");
	private ImageIcon imgUser = new ImageIcon("img/imgUser.png");
	private ImageIcon imgLogout = new ImageIcon("img/imgLogout.png");
	
    public DefaultTableModel model = new DefaultTableModel(header, 0) {
    	public boolean isCellEditable(int row, int column) {
    		return false;
    	}
    };
    
    protected Vector<Receipt> datas = new Vector<Receipt>(); 
    protected ReceiptDAO R_db = new ReceiptDAO();
	
	public POS_Receipt() {
		admin = true;
		user.setText("Administrator");
		POS_Receipt_Display();
		RefreshDB();
	}
	
	public POS_Receipt(boolean a) {
		admin = false;
		user.setText("User");
		POS_Receipt_Display();
		RefreshDB();
	}
	
	private void POS_Receipt_Display() {
		Container ct = getContentPane();
		ct.setLayout(null);
		ct.setBackground(new Color(248, 248, 248));
		
		JPanel userPanel = new JPanel();
		userPanel.setLayout(null);
		userPanel.setBackground(Color.white);
		
		iconLabel= new JLabel(imgUser);
		
		userPanel.add(iconLabel).setBounds(10, 5, 30, 30);
		userPanel.add(user).setBounds(50, 5, 100, 30);
		
		btnBack = new JButton(imgBack);
		btnBack.setBorderPainted(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setRolloverIcon(new ImageIcon("img/Sel_imgBack.png"));
		
		btnLogout = new JButton(imgLogout);
		btnLogout.setBorderPainted(false);
		btnLogout.setContentAreaFilled(false);
		btnLogout.setRolloverIcon(new ImageIcon("img/Sel_imgLogout.png"));

		table = new JTable(model);
		table.setRowHeight(50);
		table.setFillsViewportHeight(true);
		
		ct.add(userPanel).setBounds(350, 15, 250, 40);
		
		ct.add(btnBack).setBounds(25, 15, 40, 40);
		ct.add(btnLogout).setBounds(610, 15, 40, 40);
		
		ct.add(new JScrollPane(table)).setBounds(25, 70, 625, 420);
		
		btnBack.addActionListener(this);
		btnLogout.addActionListener(this);
		
		table.addMouseMotionListener(new MouseMotionAdapter() {
			
			public void mouseMoved(MouseEvent e) {
				String msg = getToolTipText(e);
				table.setToolTipText(msg);
			}
			
			public String getToolTipText(MouseEvent e) {
				JTable t = (JTable)e.getSource();
				Point p = e.getPoint();
				
				int row = t.rowAtPoint(p);
				int column = t.columnAtPoint(p);
				
				if (row == -1 || column != 1)
					return null;
				
				return t.getModel().getValueAt(row, column).toString();
			}
		});
		
		setSize(694, 549);
		setTitle("영수증 업무");
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		MainProcess main;
		
		if (obj == btnBack) {
			this.dispose();
			if (admin == true)
				new MainPOS();
			
			else
				new MainPOS(!admin);
		}
		else if (obj == btnLogout) {
			this.dispose();
			main = new MainProcess();
			main.login = new Login();
			main.login.setMain(main);
		}
	}
	
	private void RefreshDB() {
		datas = R_db.getAllSales();
        int rows = model.getRowCount();
        
        for (int i = rows-1; i >= 0; i--) {
            model.removeRow(i);
        }
        
        for (Receipt receipt : datas) {
            String date = receipt.getDate();
            String purchase = receipt.getPurchase();
            String sales = String.valueOf(receipt.getSales());
            
            model.addRow(new String[]{date, purchase, sales});
        }
	}
}	