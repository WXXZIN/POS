package GUI;

import DB.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class POS_PM extends JFrame implements ActionListener, MouseListener{
    private String [] header = new String [] {"품번", "상품명", "수량", "가격"};
	private JLabel userIcon;
	private JLabel userName = new JLabel("");
    private JLabel label = new JLabel("재고 현황");
    private JLabel labelPid = new JLabel("품번");
    private JLabel labelName = new JLabel("상품명");
    private JLabel labelCount = new JLabel("수량");
    private JLabel labelPrice = new JLabel("가격");
	private JButton btnBack;
	private JButton btnLogout;
    private JButton btnAdd;
    private JButton btnMod;
    private JButton btnDel;
    private JTable table;
    private JTextField FieldPid;
    private JTextField FieldName;
	private JTextField FieldCount;
	private JTextField FieldPrice;
    private boolean admin;
	private ImageIcon imgBack = new ImageIcon("img/imgBack.png");
	private ImageIcon imgUser = new ImageIcon("img/imgUser.png");
	private ImageIcon imgLogout = new ImageIcon("img/imgLogout.png");
	
    public DefaultTableModel model = new DefaultTableModel(header, 0) {
    	public boolean isCellEditable(int row, int column) {
    		return false;
    	}
    };
    
    protected Vector<Product> product_Data = new Vector<Product>();
    protected Vector<String> product_Name = new Vector<String>();
    protected ProductDAO P_db = new ProductDAO();
  
    int row;
    
    public POS_PM(boolean isAdmin) {
    	this.admin = isAdmin;
    	userName.setText(admin ? "Admin" : "User");
    	display_POS_PM();
        RefreshDB();
    }
    
    private void display_POS_PM() {
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
        labelPid.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        labelName.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        labelCount.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        labelPrice.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        
        FieldPid = new JTextField();
        FieldPid.setEditable(false);
        FieldName = new JTextField();
		FieldCount = new JTextField();		
		FieldPrice = new JTextField();
		
        btnAdd = new JButton("등록");
        btnAdd.setEnabled(admin);
        btnMod = new JButton("수정");
        btnDel = new JButton("삭제");
        btnDel.setEnabled(admin);

        table = new JTable(model);
		table.setRowHeight(50);
		table.setFillsViewportHeight(true);
        
		ct.add(userPanel).setBounds(675, 15, 250, 40);
		
		ct.add(btnBack).setBounds(25, 15, 40, 40);
		ct.add(btnLogout).setBounds(935, 15, 40, 40);
        
		ct.add(new JScrollPane(table)).setBounds(25, 70, 625, 475);
        
		ct.add(label).setBounds(685, 70, 100, 30);
		ct.add(labelPid).setBounds(685, 125, 57, 30);
		ct.add(labelName).setBounds(685, 180, 57, 30);
        ct.add(labelCount).setBounds(685, 235, 57, 30);
        ct.add(labelPrice).setBounds(685, 290, 57, 30);
        ct.add(FieldPid).setBounds(765, 120, 200, 40);
        ct.add(FieldName).setBounds(765, 175, 200, 40);
		ct.add(FieldCount).setBounds(765, 230, 200, 40);
		ct.add(FieldPrice).setBounds(765, 285, 200, 40);
        
		ct.add(btnAdd).setBounds(685, 355, 280, 40);
        ct.add(btnMod).setBounds(685, 415, 280, 40);
        ct.add(btnDel).setBounds(685, 475, 280, 40);

        btnBack.addActionListener(this);
        btnLogout.addActionListener(this);
        btnAdd.addActionListener(this);
        btnMod.addActionListener(this);
        btnDel.addActionListener(this);
        table.addMouseListener(this);
        
        setSize(1016, 599);
        setTitle("재고 관리");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        MainProcess main;
 
        model = (DefaultTableModel) table.getModel();
        
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
        
        else if (obj == btnAdd) {
	        if (FieldName.getText().equals("") || FieldCount.getText().equals("") || FieldPrice.getText().equals("")) {
	        	JOptionPane.showMessageDialog(null, "다시 입력 해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	        
	        else if (isExist(product_Name, FieldName.getText())) {
	        	JOptionPane.showMessageDialog(null, "존재하는 상품입니다.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	        
	        else {
	        	Product product = new Product();
	            
	        	product.setProduct_name(FieldName.getText());
	        	product.setProduct_count(Integer.parseInt(FieldCount.getText()));
	        	product.setProduct_price(Integer.parseInt(FieldPrice.getText()));
	
	            int result = JOptionPane.showConfirmDialog(null, "선택한 상품 " + FieldName.getText() + "을 데이터베이스에 등록하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
	            
	            if (result == JOptionPane.YES_OPTION) {
	            	P_db.insertProduct(product);
	                JOptionPane.showMessageDialog(null, "등록하려는 " + FieldName.getText() + "을 \n등록하였습니다.");
	            } 
	            
	            else if (result == JOptionPane.NO_OPTION)
	                JOptionPane.showMessageDialog(null, "등록을 취소하였습니다.");
	        }
	        setBlank();
	        RefreshDB();
        } 
        
        else if (obj == btnMod) {
            row = table.getSelectedRow();
            
            if (row == -1)
                JOptionPane.showMessageDialog(null, "DB를 불러오지 않았거나  셀을 선택하지 않았습니다.", "Error", JOptionPane.ERROR_MESSAGE);

            else if (FieldName.getText().equals("") || FieldCount.getText().equals("") || FieldPrice.getText().equals(""))
            	JOptionPane.showMessageDialog(null, "다시 입력 해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
            
        	else {
        		Product product = new Product();

        		product.setPid(Integer.parseInt(FieldPid.getText()));
        		product.setProduct_name(FieldName.getText());
        		product.setProduct_count(Integer.parseInt(FieldCount.getText()));
        		product.setProduct_price(Integer.parseInt(FieldPrice.getText()));
                
                int result = JOptionPane.showConfirmDialog(null, "선택한 상품 " + FieldName.getText() + "을 데이터베이스에서 수정하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
                
                if (result == JOptionPane.YES_OPTION) {
                	P_db.updateProduct(product);
                    JOptionPane.showMessageDialog(null, "수정하려는 " + FieldName.getText() + "을 \n수정하였습니다.");
                } 
                
                else if (result == JOptionPane.NO_OPTION) 
                    JOptionPane.showMessageDialog(null, "수정을 취소하였습니다.");	
        	}
            setBlank();
            RefreshDB();
        } 
        
        else if (obj == btnDel) {
            row = table.getSelectedRow();
            
        	if (row == -1)
                JOptionPane.showMessageDialog(null, "DB를 불러오지 않았거나  셀을 선택하지 않았습니다.", "Error", JOptionPane.ERROR_MESSAGE);
        	
        	else if (FieldName.getText().equals("") || FieldCount.getText().equals("") || FieldPrice.getText().equals(""))
            	JOptionPane.showMessageDialog(null, "다시 입력 해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
    		
    		else {
    			int result = JOptionPane.showConfirmDialog(null, "선택한 상품 " + FieldName.getText() + "을 데이터베이스에서 삭제하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
                
                if (result == JOptionPane.YES_OPTION) {
                	P_db.deleteProduct(Integer.parseInt(FieldPid.getText()));
                    JOptionPane.showMessageDialog(null, "삭제하려는 " + FieldName.getText() + "을 \n삭제하였습니다.");
                } 
                
                else if (result == JOptionPane.NO_OPTION)
                    JOptionPane.showMessageDialog(null, "삭제를 취소하였습니다.");
    		}
        	setBlank();
        	RefreshDB();
        }
    }

    private void RefreshDB() {
    	product_Data = P_db.getAllProduct();
    	product_Name = P_db.getProduct();
    	
        int rows = model.getRowCount();
        
        for (int i = rows - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        
        for (Product product : product_Data) {
            int pid = product.getPid();
            String name = product.getProduct_name();
            int count = product.getProduct_count();
            int price = product.getProduct_price();
            
            model.addRow(new Object[]{pid, name, count, price});
        }
    }
    
    private boolean isExist(Vector<String> Data, String Text) {
    	boolean Exist = false;
    	
    	if (Data.contains(Text))
    		Exist = true;
    	
    	return Exist;
    }
    
    private void setBlank() {
    	FieldPid.setText("");
    	FieldName.setText("");
    	FieldCount.setText("");
    	FieldPrice.setText("");
    	FieldName.requestFocus();
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		row = table.getSelectedRow();
		
		if (row == -1)
			JOptionPane.showMessageDialog(null, "셀을 선택하세요.");
		
		else {
			FieldPid.setText(model.getValueAt(row, 0).toString());
			FieldName.setText(model.getValueAt(row, 1).toString());
			FieldCount.setText(model.getValueAt(row, 2).toString());
			FieldPrice.setText(model.getValueAt(row, 3).toString());
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