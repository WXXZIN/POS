package GUI;

import DB.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class POS_Sell extends JFrame implements ActionListener {
	private String [] header = {"상품명", "수량", "가격"};
	private JLabel userIcon;
	private JLabel userName = new JLabel("");
	private JButton btnBack;
	private JButton btnLogout;
	private JButton btnPurchase;
	private JButton btnAddItem;
	private JButton btnPlus;
	private JButton btnMinus;
	private JComboBox<String> jcb;
	private JTable SellTable;
	private JTable UserTable;
	private JTextField FieldTotal;
	private boolean admin;
	private ImageIcon imgBack = new ImageIcon("img/imgBack.png");
	private ImageIcon imgUser = new ImageIcon("img/imgUser.png");
	private ImageIcon imgLogout = new ImageIcon("img/imgLogout.png");
	private ImageIcon imgPurchase = new ImageIcon("img/imgPurchase.png");
	private ImageIcon imgAddItem = new ImageIcon("img/imgAddItem.png");
	private ImageIcon imgPlus = new ImageIcon("img/imgPlus.png");
	private ImageIcon imgMinus = new ImageIcon("img/imgMinus.png");
	
    public DefaultTableModel model = new DefaultTableModel(header, 0) {
    	public boolean isCellEditable(int row, int column) {
    		return false;
    	}
    };
	
    protected Vector<Product> product_Data = new Vector<Product>();
    protected ProductDAO P_db = new ProductDAO();
    protected ReceiptDAO R_db = new ReceiptDAO();
	
	public POS_Sell(boolean isAdmin) {
		this.admin = isAdmin;
		userName.setText(admin ? "Admin" : "User");
		display_POS_Sell();
		RefreshDB();
	}
	
	private void display_POS_Sell() {
		Container ct = getContentPane();
		ct.setLayout(null);
		ct.setBackground(new Color(248, 248, 248));
		
		userIcon= new JLabel(imgUser);
		
		JPanel userPanel = new JPanel();
		JPanel purchasePanel = new JPanel();
		
		userPanel.setLayout(null);
		userPanel.setBackground(Color.white);
		userPanel.add(userIcon).setBounds(10, 5, 30, 30);
		userPanel.add(userName).setBounds(50, 5, 100, 30);
		
		purchasePanel.setLayout(null);
		purchasePanel.setBackground(Color.white);
		
		UserTable = new JTable(model);
		UserTable.setRowHeight(40);
		UserTable.setFillsViewportHeight(true);
		
		FieldTotal = new JTextField();
		FieldTotal.setEditable(false);
		
		btnPurchase = new JButton(imgPurchase);
		btnPurchase.setBorderPainted(false);
		btnPurchase.setRolloverIcon(new ImageIcon("img/Sel_imgPurchase.png"));
		btnPurchase.setEnabled(false);
		
		purchasePanel.add(new JScrollPane(UserTable)).setBounds(25, 25, 250, 260);
		purchasePanel.add(FieldTotal).setBounds(25, 300,  250, 40);
		purchasePanel.add(btnPurchase).setBounds(25, 365, 250, 40);
		
		SellTable = new JTable(model);
		SellTable.setRowHeight(50);
		SellTable.setFillsViewportHeight(true);
		
		jcb = new JComboBox<String>();
		
		btnBack = new JButton(imgBack);
		btnBack.setBorderPainted(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setRolloverIcon(new ImageIcon("img/Sel_imgBack.png"));
		
		btnLogout = new JButton(imgLogout);
		btnLogout.setBorderPainted(false);
		btnLogout.setContentAreaFilled(false);
		btnLogout.setRolloverIcon(new ImageIcon("img/Sel_imgLogout.png"));
		
		btnAddItem = new JButton(imgAddItem);
		btnAddItem.setBorderPainted(false);
		btnAddItem.setContentAreaFilled(false);
		btnAddItem.setRolloverIcon(new ImageIcon("img/Sel_imgAddItem.png"));
		
		btnPlus = new JButton(imgPlus);
		btnPlus.setBorderPainted(false);
		btnPlus.setContentAreaFilled(false);
		btnPlus.setRolloverIcon(new ImageIcon("img/Sel_imgPlus.png"));
		
		btnMinus = new JButton(imgMinus);
		btnMinus.setBorderPainted(false);
		btnMinus.setContentAreaFilled(false);
		btnMinus.setRolloverIcon(new ImageIcon("img/Sel_imgMinus.png"));
		
		ct.add(userPanel).setBounds(675, 15, 250, 40);
		ct.add(purchasePanel).setBounds(675, 70, 300, 420);
		
		ct.add(btnBack).setBounds(25, 15, 40, 40);
		ct.add(btnLogout).setBounds(935, 15, 40, 40);
		
		ct.add(new JScrollPane(SellTable)).setBounds(25, 70, 625, 420);
		
		ct.add(jcb).setBounds(25, 505, 200, 40);
		ct.add(btnAddItem).setBounds(240, 505, 40, 40);
		ct.add(btnPlus).setBounds(295, 505, 40, 40);
		ct.add(btnMinus).setBounds(350, 505, 40, 40);

		btnBack.addActionListener(this);
		btnLogout.addActionListener(this);
		btnAddItem.addActionListener(this);
		btnPlus.addActionListener(this);
		btnMinus.addActionListener(this);
		btnPurchase.addActionListener(this);
		
		setSize(1016, 599);
		setTitle("판매");
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		MainProcess main;
		
		String Product_Name = (String)jcb.getSelectedItem();
		int Product_Price = P_db.getPrice(Product_Name);
		int Purchase_Count = 1;
		int Product_Count = P_db.getCount(Product_Name);
		int total = 0;
		
		if (obj == btnBack)  {
			this.dispose();
			
			new MainPOS(admin);
		}
		
		else if (obj == btnLogout) {
			this.dispose();
			main = new MainProcess();
			main.login = new Login();
			main.login.setMain(main);
		}
		
		else if (obj == btnAddItem) {
			btnPurchase.setEnabled(true);
			if (!isAdded(Product_Name, model)) {
				JOptionPane.showMessageDialog(null, "물품이 이미 추가되었습니다.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				if(Purchase_Count <= Product_Count)
					model.addRow(new String [] {Product_Name, String.valueOf(Purchase_Count), String.valueOf(Product_Price)});
				
				else
					JOptionPane.showMessageDialog(null, "물품의 재고가 없습니다.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			getTotal(total);
		}
		
		else if (obj == btnPlus) {
			int row = SellTable.getSelectedRow();
			
            if (row == -1)
                JOptionPane.showMessageDialog(null, "DB를 불러오지 않았거나  셀을 선택하지 않았습니다", "Error", JOptionPane.ERROR_MESSAGE);

            else {
            	String Sel_Product_Name = model.getValueAt(row, 0).toString();
    			int Sel_Product_Price = P_db.getPrice(Sel_Product_Name);
    			
            	if ( Integer.parseInt(model.getValueAt(row, 1).toString()) >= Product_Count)
					JOptionPane.showMessageDialog(null, "물품의 수량이 남은 재고수량 보다 많습니다.", "Error", JOptionPane.ERROR_MESSAGE);
				
				else {
					model.setValueAt(Integer.parseInt(model.getValueAt(row, 1).toString()) + 1, row, 1);
					model.setValueAt(Integer.parseInt(model.getValueAt(row, 1).toString()) * Sel_Product_Price, row, 2);
				}
            }
            
            getTotal(total);
		}
		
		else if (obj == btnMinus) {
			int row = SellTable.getSelectedRow();
			
            if (row == -1)
                JOptionPane.showMessageDialog(null, "DB를 불러오지 않았거나 셀을 선택하지 않았습니다.", "Error", JOptionPane.ERROR_MESSAGE);

            else {
            	String Sel_Product_Name = model.getValueAt(row, 0).toString();
    			int Sel_Product_Price = P_db.getPrice(Sel_Product_Name);
    			
				model.setValueAt(Integer.parseInt(model.getValueAt(row, 1).toString()) - 1, row, 1);
				model.setValueAt(Integer.parseInt(model.getValueAt(row, 1).toString()) * Sel_Product_Price, row, 2);
				
				if (Integer.parseInt(model.getValueAt(row, 1).toString()) < 1) {
					model.removeRow(row);
					
					if (model.getRowCount() < 1) {
						FieldTotal.setText("");
						btnPurchase.setEnabled(false);
					}	
				}
            }
            
            getTotal(total);
		}
		
		else if (obj == btnPurchase) {
			Receipt receipt = new Receipt();
            String product_name;
			String purchase = "";
			String pay;
			
			int count;
			int purchase_total = Integer.parseInt(FieldTotal.getText());
			int change;
			int result = JOptionPane.showConfirmDialog(null, "결제 하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
			
			for (int i = 0; i < model.getRowCount(); i++) {
				product_name = model.getValueAt(i, 0).toString();
				count = Integer.parseInt(model.getValueAt(i, 1).toString());
				purchase = purchase + product_name + "(" + count + ") ";	
			}
			
			if (result == JOptionPane.YES_OPTION) {
				pay = JOptionPane.showInputDialog("총 금액은" + purchase_total + "입니다.\n얼마를 지불하시겠습니까?");
                
                if (pay != null) {
                	if (Integer.parseInt(pay) >= purchase_total) {
                		change = Integer.parseInt(pay) - purchase_total;
                        JOptionPane.showMessageDialog(null, "지불하신 금액은 " + pay + "\n 거스름돈은 " + change + "입니다");
                        
                        receipt.setType("판매");
                        receipt.setPurchase(purchase);
                        receipt.setSales(purchase_total);
                        R_db.insertReceipt(receipt);
                        Product_Update(model);
                        RefreshDB();
                    }
                	
                	else {
                		JOptionPane.showMessageDialog(null, "결제 금액이 부족합니다.", "Error", JOptionPane.ERROR_MESSAGE);
                    	purchase = "";
                	}
                }
                
                else {
                	JOptionPane.showMessageDialog(null, "결제를 취소하였습니다.");
                    purchase = "";
                }
            } 
			
			else if (result == JOptionPane.NO_OPTION) {
                JOptionPane.showMessageDialog(null, "결제를 취소하였습니다.");
                purchase = "";
			} 
		}
	}
	
	private void RefreshDB() {
		product_Data = P_db.getAllProduct();
		jcb.setModel(new DefaultComboBoxModel<String>(P_db.getProduct()));
		
		int rows = model.getRowCount();
		
		for (int i = rows - 1; i >= 0; i--)
			model.removeRow(i);
	}
	
	private boolean isAdded(String text, DefaultTableModel model) {
        boolean added = true;

        for (int i = 0; i < model.getRowCount(); i++) {
            if (text.equals(model.getValueAt(i, 0))) {
                added = false;
            }
        }
        return added;
    }
	
	private void getTotal(int total) {
		for (int i = 0; i < model.getRowCount(); i++) {
			total += Integer.parseInt(model.getValueAt(i, 2).toString());
			FieldTotal.setText(String.valueOf(total));
		}
	}
	
    private void Product_Update(DefaultTableModel model) {
    	String type = "판매";
    	String Product_Name = null;
        int Purchase_Count = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
        	Product_Name = model.getValueAt(i, 0).toString();
        	Purchase_Count = Integer.parseInt(model.getValueAt(i, 1).toString());
        	
        	P_db.updateProduct(type, Purchase_Count, Product_Name);
        }
    }
}