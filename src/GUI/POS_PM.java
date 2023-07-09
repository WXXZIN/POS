package GUI;

import DB.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class POS_SM extends JFrame implements ActionListener, MouseListener{
    private String [] header = new String [] {"id", "이름", "재고", "가격"};
	private JLabel iconLabel;
	private JLabel user = new JLabel("");
    private JLabel label = new JLabel("재고 현황");
    private JLabel labelName = new JLabel("물품");
    private JLabel labelCount = new JLabel("수량");
    private JLabel labelPrice = new JLabel("가격");
    private JTable table;
	private JButton btnBack;
	private JButton btnLogout;
    private JButton btnAdd;
    private JButton btnMod;
    private JButton btnDel;
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
    
    protected Vector<Stock> data = new Vector<Stock>();
    protected Vector<String> name_Data = new Vector<String>();
    protected StockDAO S_db = new StockDAO();
  
    
    public POS_SM() {
    	admin = true;
    	user.setText("Administrator");
    	POS_SM_Display();
        RefreshDB();
    }
    
    public POS_SM(boolean a) {
    	admin = false;
    	user.setText("User");
    	POS_SM_Display();
    	RefreshDB();
    }
    
    private void POS_SM_Display() {
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
		
        label.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        labelName.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        labelCount.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        labelPrice.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        
        FieldName = new JTextField();
		FieldCount = new JTextField();		
		FieldPrice = new JTextField();
		
        btnAdd = new JButton("등록");
        btnMod = new JButton("수정");
        btnDel = new JButton("삭제");

        table = new JTable(model);
		table.setRowHeight(50);
		table.setFillsViewportHeight(true);
        
		ct.add(userPanel).setBounds(675, 15, 250, 40);
		
		ct.add(btnBack).setBounds(25, 15, 40, 40);
		ct.add(btnLogout).setBounds(935, 15, 40, 40);
        
		ct.add(new JScrollPane(table)).setBounds(25, 70, 625, 475);
        
		ct.add(label).setBounds(685, 70, 100, 30);
        ct.add(labelName).setBounds(685, 125, 57, 30);
        ct.add(labelCount).setBounds(685, 180, 57, 30);
        ct.add(labelPrice).setBounds(685, 235, 57, 30);
        ct.add(FieldName).setBounds(765, 120, 200, 40);
		ct.add(FieldCount).setBounds(765, 175, 200, 40);
		ct.add(FieldPrice).setBounds(765, 230, 200, 40);
        
		ct.add(btnAdd).setBounds(685, 300, 280, 40);
        ct.add(btnMod).setBounds(685, 360, 280, 40);
        ct.add(btnDel).setBounds(685, 420, 280, 40);

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
        
        else if (obj == btnAdd) {
        	if (admin == true) {
                if (FieldName.getText().equals("") || FieldCount.getText().equals("") || FieldPrice.getText().equals("")) {
                	JOptionPane.showMessageDialog(null, "다시 입력해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                else if (isExist(name_Data, FieldName.getText())) {
                	JOptionPane.showMessageDialog(null, "존재하는 상품입니다.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                else {
                	Stock register = new Stock();
                    
                    register.setStock_Name(FieldName.getText());
                    register.setStock_Count(Integer.parseInt(FieldCount.getText()));
                    register.setStock_Price(Integer.parseInt(FieldPrice.getText()));

                    int result = JOptionPane.showConfirmDialog(null, "선택한 상품 " + FieldName.getText() + "을 DB에 등록하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
                    
                    if (result == JOptionPane.YES_OPTION) {
                    	S_db.insertStock(register);
                        JOptionPane.showMessageDialog(null, "등록하려는 " + FieldName.getText() + "을 \n등록하였습니다.");
                    } 
                    
                    else if (result == JOptionPane.NO_OPTION)
                        JOptionPane.showMessageDialog(null, "등록을 취소하였습니다.");
                }
                setBlank();
                RefreshDB();
        	}
        	
        	else
        		JOptionPane.showMessageDialog(null, "권한이 없습니다.", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        
        else if (obj == btnMod) {
            int row = table.getSelectedRow();
            
            if (row == -1)
                JOptionPane.showMessageDialog(null, "DB를 불러오지 않았거나 셀을 선택하지 않았습니다.", "Error", JOptionPane.ERROR_MESSAGE);

            else if (FieldName.getText().equals("") || FieldCount.getText().equals("") || FieldPrice.getText().equals(""))
            	JOptionPane.showMessageDialog(null, "다시 입력해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
            
            else if (!table.getModel().getValueAt(row, 1).equals(FieldName.getText()))
            	JOptionPane.showMessageDialog(null, "선택한 상품과 수정하려는 상품이 다릅니다.", "Error", JOptionPane.ERROR_MESSAGE);
            
            	
        	else {
        		String number = (String) table.getValueAt(row, 0);

        		Stock stock = new Stock();

                stock.setNumber(Integer.parseInt(number));
                stock.setStock_Name(FieldName.getText());
                stock.setStock_Count(Integer.parseInt(FieldCount.getText()));
                stock.setStock_Price(Integer.parseInt(FieldPrice.getText()));
                
                int result = JOptionPane.showConfirmDialog(null, "선택한 상품 " + FieldName.getText() + "을 DB에서 수정하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
                
                if (result == JOptionPane.YES_OPTION) {
                	S_db.updateStock(stock);
                    JOptionPane.showMessageDialog(null, "수정하려는 " + FieldName.getText() + "을 \n수정하였습니다.");
                } 
                
                else if (result == JOptionPane.NO_OPTION) 
                    JOptionPane.showMessageDialog(null, "수정을 취소하였습니다.");	
        	}
            setBlank();
            RefreshDB();
        } 
        
        else if (obj == btnDel) {
            int row = table.getSelectedRow();
            
            if (admin == true) {
            	if (row == -1)
                    JOptionPane.showMessageDialog(null, "DB를 불러오지 않았거나 셀을 선택하지 않았습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            	
            	else if (FieldName.getText().equals("") || FieldCount.getText().equals("") || FieldPrice.getText().equals(""))
                	JOptionPane.showMessageDialog(null, "다시 입력해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
        		
        		else if (!table.getModel().getValueAt(row, 1).equals(FieldName.getText()))
        			JOptionPane.showMessageDialog(null, "선택한 상품과 삭제하려는 상품이 다릅니다.", "Error", JOptionPane.ERROR_MESSAGE);
        		
        		else {
        			String number = (String) table.getValueAt(row, 0);
                    
                    int result = JOptionPane.showConfirmDialog(null, "선택한 상품 " + FieldName.getText() + "을 DB에서 삭제하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
                    
                    if (result == JOptionPane.YES_OPTION) {
                    	S_db.deleteStock(Integer.parseInt(number));
                        JOptionPane.showMessageDialog(null, "삭제하려는 " + FieldName.getText() + "을 \n삭제하였습니다.");
                    } 
                    
                    else if (result == JOptionPane.NO_OPTION)
                        JOptionPane.showMessageDialog(null, "삭제를 취소하였습니다.");
        		}
            	setBlank();
            	RefreshDB();
        	}
        	
        	else
        		JOptionPane.showMessageDialog(null, "권한이 없습니다.", "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void RefreshDB() {
    	data = S_db.getAllStock();
    	name_Data = S_db.getStock();
    	
        int rows = model.getRowCount();
        
        for (int i = rows-1; i >= 0; i--) {
            model.removeRow(i);
        }
        
        for (Stock stock : data) {
            String number = String.valueOf(stock.getNumber());
            String name = stock.getStock_Name();
            String count = String.valueOf(stock.getStock_Count());
            String price = String.valueOf(stock.getStock_Price());
            
            model.addRow(new String[]{number, name, count, price});
        }
    }
    
    private boolean isExist(Vector<String> Data, String Text) {
    	boolean Exist = false;
    	
    	if (Data.contains(Text))
    		Exist = true;
    	
    	return Exist;
    }
    
    private void setBlank() {
    	FieldName.setText("");
    	FieldCount.setText("");
    	FieldPrice.setText("");
    	FieldName.requestFocus();
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = table.getSelectedRow();
		
		if (row == -1)
			JOptionPane.showMessageDialog(null, "셀을 선택하세요.");
		
		else {
			FieldName.setText(table.getModel().getValueAt(row, 1).toString());
			FieldCount.setText(table.getModel().getValueAt(row, 2).toString());
			FieldPrice.setText(table.getModel().getValueAt(row, 3).toString());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}