package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import DB.*;

public class POS_Receipt extends JFrame implements ActionListener, MouseListener{
	private String [] header = {"일자", "영수증", "분류", "구매", "금액", "테스"};
	private JLabel userIcon;
	private JLabel userName = new JLabel("");
	private JLabel label = new JLabel("영수증 현황");
    private JLabel labelRid = new JLabel("품번");
	private JButton btnBack;
	private JButton btnLogout;
	private JButton btnMod;
	private JTable table;
	private JTextField FieldRid;
	private boolean admin;
	private ImageIcon imgBack = new ImageIcon("img/imgBack.png");
	private ImageIcon imgUser = new ImageIcon("img/imgUser.png");
	private ImageIcon imgLogout = new ImageIcon("img/imgLogout.png");
	
    public DefaultTableModel model = new DefaultTableModel(header, 0) {
    	public boolean isCellEditable(int row, int column) {
    		return false;
    	}
    };
    
    protected Vector<Receipt> receipt_Data = new Vector<Receipt>();
    protected ProductDAO P_db = new ProductDAO();
    protected ReceiptDAO R_db = new ReceiptDAO();
	
    int row;
    
	public POS_Receipt(boolean admin) {
		this.admin = admin;
		userName.setText(admin ? "Admin" : "User");
		POS_Receipt_Display();
		RefreshDB();
	}
	
	private void POS_Receipt_Display() {
		Container ct = getContentPane();
		ct.setLayout(null);
		ct.setBackground(new Color(248, 248, 248));
		
		userIcon= new JLabel(imgUser);
		
		JPanel userPanel = new JPanel();
		userPanel.setLayout(null);
		userPanel.setBackground(Color.white);
		userPanel.add(userIcon).setBounds(10, 5, 30, 30);
		userPanel.add(userName).setBounds(50, 5, 100, 30);
		
		btnBack = new JButton(imgBack);
		btnBack.setBorderPainted(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setRolloverIcon(new ImageIcon("img/Sel_imgBack.png"));
		
		btnLogout = new JButton(imgLogout);
		btnLogout.setBorderPainted(false);
		btnLogout.setContentAreaFilled(false);
		btnLogout.setRolloverIcon(new ImageIcon("img/Sel_imgLogout.png"));

		label.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        labelRid.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		FieldRid = new JTextField();
        FieldRid.setEditable(false);
        
        btnMod = new JButton("환불");
        
		table = new JTable(model);
		table.setRowHeight(50);
		table.setFillsViewportHeight(true);
		
		TableColumn column = table.getColumnModel().getColumn(5);
		column.setMinWidth(0);
		column.setMaxWidth(0);
		
		ct.add(userPanel).setBounds(675, 15, 250, 40);
		
		ct.add(btnBack).setBounds(25, 15, 40, 40);
		ct.add(btnLogout).setBounds(935, 15, 40, 40);
		
		ct.add(new JScrollPane(table)).setBounds(25, 70, 625, 475);
		
		ct.add(label).setBounds(685, 70, 100, 30);
		ct.add(labelRid).setBounds(685, 125, 57, 30);
		ct.add(FieldRid).setBounds(765, 120, 200, 40);
		
		ct.add(btnMod).setBounds(685, 355, 280, 40);
		
		btnBack.addActionListener(this);
		btnLogout.addActionListener(this);
		btnMod.addActionListener(this);
		table.addMouseListener(this);
//		table.addMouseMotionListener(new MouseMotionAdapter() {
//			
//			public void mouseMoved(MouseEvent e) {
//				String msg = getToolTipText(e);
//				table.setToolTipText(msg);
//			}
//			
//			public String getToolTipText(MouseEvent e) {
//				JTable t = (JTable)e.getSource();
//				Point p = e.getPoint();
//				
//				int row = t.rowAtPoint(p);
//				int column = t.columnAtPoint(p);
//				
//				if (row == -1 || column != 1)
//					return null;
//				
//				return t.getModel().getValueAt(row, column).toString();
//			}
//		});
		
		setSize(1016, 599);
		setTitle("영수증 업무");
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		MainProcess main;
		
		row = table.getSelectedRow();
		
		if (obj == btnBack) {
			this.dispose();
			
			new MainPOS(admin);
		}
		
		else if (obj == btnLogout) {
			this.dispose();
			main = new MainProcess();
			main.login = new Login();
			main.login.setMain(main);
		}
		
		else if (obj == btnMod) {
			if (row == -1) 
                JOptionPane.showMessageDialog(null, "DB를 불러오지 않았거나 셀을 선택하지 않았습니다.", "Error", JOptionPane.ERROR_MESSAGE);
			
			else if(model.getValueAt(row, 2).equals("환불") || model.getValueAt(row, 5).equals(true))
                JOptionPane.showMessageDialog(null, "1", "Error", JOptionPane.ERROR_MESSAGE);

			else {
				Receipt receipt = new Receipt();
				
				receipt.setRid(Integer.parseInt(model.getValueAt(row, 1).toString()));
				receipt.setType("환불");
				receipt.setPurchase(model.getValueAt(row, 3).toString());
				receipt.setSales(-Integer.parseInt(model.getValueAt(row, 4).toString()));
				R_db.insertReceipt(receipt);
				R_db.updateReceipt(receipt);
				Product_Update(model);
				setBlank();
				RefreshDB();
			}
		}
	}
	
	private void RefreshDB() {
		receipt_Data = R_db.getAllSales();
        
		int rows = model.getRowCount();
        
        for (int i = rows - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        
        for (Receipt receipt : receipt_Data) {
            String date = receipt.getDate();
            int rid = receipt.getRid();
            String type = receipt.getType();
            String purchase = receipt.getPurchase();
            int sales = receipt.getSales();
            boolean refund = receipt.getRefund();
            model.addRow(new Object[]{date, rid, type, purchase, sales, refund});
        }
	}
	
	private void setBlank() {
		FieldRid.setText("");
	}

	private void Product_Update(DefaultTableModel model) {
		String data = model.getValueAt(row, 3).toString();
    	String type = "환불";
    	
    	Pattern pattern = Pattern.compile("(\\S+)\\((\\d+)\\)"); // 정규식 패턴
        Matcher matcher = pattern.matcher(data);

        while (matcher.find()) {
            String Product_Name = matcher.group(1); // 상품명 추출
            int Purchase_Count = Integer.parseInt(matcher.group(2)); // 개수 추출
        
            P_db.updateProduct(type, Purchase_Count, Product_Name);
        }
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		row = table.getSelectedRow();
		
		if (row == -1)
			JOptionPane.showMessageDialog(null, "셀을 선택하세요.");

		else {
			FieldRid.setText(model.getValueAt(row, 1).toString());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
}	