package GUI;

import DB.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class POS_UM extends JFrame implements ActionListener, MouseListener{
    private String [] header = new String [] {"사용자 현황"};
	private JLabel userIcon;
	private JLabel userName = new JLabel("");
    private JLabel label = new JLabel("사용자 현황");
    private JLabel labelId = new JLabel("ID");
    private JTable table;
	private JButton btnBack;
	private JButton btnLogout;
    private JButton btnAdd;
    private JButton btnDel;
	private JTextField FieldId;
	private ImageIcon imgBack = new ImageIcon("img/imgBack.png");
	private ImageIcon imgUser = new ImageIcon("img/imgUser.png");
	private ImageIcon imgLogout = new ImageIcon("img/imgLogout.png");
	
    public DefaultTableModel model = new DefaultTableModel(header, 0) {
    	public boolean isCellEditable(int row, int column) {
    		return false;
    	}
    };
    
    protected Vector<User> user_Data = new Vector<User>(); 
    protected Vector<String> user_Id = new Vector<String>();
    protected UserDAO A_db = new UserDAO();
    
    public POS_UM() {
    	userName.setText("Admin");
    	POS_UM_Display();
    	RefreshDB();
    }
    
    private void POS_UM_Display() {
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
        labelId.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        FieldId = new JTextField();
		
        btnAdd = new JButton("등록");
        btnDel = new JButton("삭제");

        table = new JTable(model);
		table.setRowHeight(50);
		table.setFillsViewportHeight(true);
        
		ct.add(userPanel).setBounds(675, 15, 250, 40);

        ct.add(btnBack).setBounds(25, 15, 40, 40);
		ct.add(btnLogout).setBounds(935, 15, 40, 40);

		ct.add(new JScrollPane(table)).setBounds(25, 70, 625, 475);

		ct.add(label).setBounds(685, 70, 100, 30);
        ct.add(labelId).setBounds(685, 125, 57, 30);
		ct.add(FieldId).setBounds(765, 120, 200, 40);
		
        ct.add(btnAdd).setBounds(685, 300, 280, 40);
        ct.add(btnDel).setBounds(685, 360, 280, 40);
        
        btnBack.addActionListener(this);
        btnLogout.addActionListener(this);
        btnAdd.addActionListener(this);
        btnDel.addActionListener(this);
        table.addMouseListener(this);
        
        setSize(1016, 599);
        setTitle("사용자 관리");
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
			
			new MainPOS(true);
		}
		
		else if (obj == btnLogout) {
			this.dispose();
			main = new MainProcess();
			main.login = new Login();
			main.login.setMain(main);
		}
        
        else if (obj == btnAdd) {
    		if (FieldId.getText().equals(""))
            	JOptionPane.showMessageDialog(null, "다시 입력 해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
    		
    		else if (isExist(user_Id, FieldId.getText()))
    			JOptionPane.showMessageDialog(null, "존재하는 사용자입니다.", "Error", JOptionPane.ERROR_MESSAGE);

    		else {
                int result = JOptionPane.showConfirmDialog(null, "선택한 사용자 " + FieldId.getText() + "을 데이터베이스에 등록하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
                
                if (result == JOptionPane.YES_OPTION) {
                    A_db.insertUser(FieldId.getText());
                    JOptionPane.showMessageDialog(null, "등록하려는 " + FieldId.getText() + "을 \n등록하였습니다.");
                } 
                
                else if (result == JOptionPane.NO_OPTION)
                    JOptionPane.showMessageDialog(null, "등록을 취소하였습니다.");
    		}
    		setBlank();
            RefreshDB();
    	} 
        
        else if (obj == btnDel) {
            if (FieldId.getText().equals(""))
            	JOptionPane.showMessageDialog(null, "다시 입력 해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
         
            else if (!isExist(user_Id, FieldId.getText()))
                JOptionPane.showMessageDialog(null, "존재하지 않는 사용자입니다.", "Error", JOptionPane.ERROR_MESSAGE);
            
    		else {
                int result = JOptionPane.showConfirmDialog(null, "선택한 사용자 " + FieldId.getText() + "을 데이터베이스에서 삭제하시겠습니까?", "Message", JOptionPane.YES_NO_OPTION);
                
                if (result == JOptionPane.YES_OPTION) {
                	A_db.deleteUser(FieldId.getText());
                    JOptionPane.showMessageDialog(null, "삭제하려는 " + FieldId.getText() + "을 \n삭제하였습니다.");
                } 
                
                else if (result == JOptionPane.NO_OPTION)
                    JOptionPane.showMessageDialog(null, "삭제를 취소하였습니다.");
    		}
            setBlank();
            RefreshDB();
        }
    }

    private void RefreshDB() {
    	user_Data = A_db.getAllUserId();
    	user_Id = A_db.getUserId();
    	
    	int rows = model.getRowCount();
        
        for (int i = rows - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        
        for (User user : user_Data) {
            String Id = user.getId();
            
            model.addRow(new String[]{Id});
        }
    }

    private boolean isExist(Vector<String> Data, String Text) {
    	boolean Exist = false;
    	
    	if (Data.contains(Text))
    		Exist = true;
    	
    	return Exist;
    }
    
    private void setBlank() {
    	FieldId.setText("");
    	FieldId.requestFocus();
    }
    
	@Override
	public void mouseClicked(MouseEvent e) {
		int row = table.getSelectedRow();
		
		if (row == -1)
			JOptionPane.showMessageDialog(null, "셀을 선택하세요.");
		
		else
			FieldId.setText(model.getValueAt(row, 0).toString());
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