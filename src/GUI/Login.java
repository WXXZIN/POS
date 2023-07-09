package GUI;
 
import DB.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.*;
import java.sql.SQLException;

public class Login extends JFrame implements ActionListener, KeyListener {
    private MainProcess main;
    private DB db;
    private JButton btnLogin;
    private JTextField IDText;
    private JLabel imgLabel = new JLabel();
    public boolean admin;
    private ImageIcon imgUserLogin = new ImageIcon("img/imgUserLogin.png");
    private ImageIcon imgLogin = new ImageIcon("img/imgLogin.png");
    
    public Login() {
       display();
    }
   
    private void display() {
    	Container ct = getContentPane();
    	ct.setLayout(null);
    	ct.setBackground(new Color(248, 248, 248));
    	
        imgLabel.setIcon(imgUserLogin);
        
        IDText = new JTextField(20);
		
        btnLogin = new JButton(imgLogin);
		btnLogin.setBorderPainted(false);
        btnLogin.setRolloverIcon(new ImageIcon("img/Sel_imgLogin.png"));
        
        ct.add(imgLabel).setBounds(125, 80, 150, 150);
        ct.add(IDText).setBounds(50, 260, 300, 40);
        ct.add(btnLogin).setBounds(50, 315, 300, 40);
        
        IDText.addKeyListener(this);
        btnLogin.addActionListener(this);

        setSize(416, 509);
        setTitle("Login");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    
    // mainProcess와 연동
    public void setMain(MainProcess main) {
        this.main = main;
    }
    
    public boolean isLoginCheck(String id) {
		boolean blogin = false;
		
		db.connectDB();
		db.sql = "select * from user where ID = ?";
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setString(1, id);
			
			db.rs = db.pstmt.executeQuery();
			
			while(db.rs.next()) {
				if(id.equals(db.rs.getString("ID")))
					blogin = true;
								
				else
					blogin = false;
			}
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			db.closeDB();
		}
		
		return blogin;
	}
    
    @Override
	public void actionPerformed(ActionEvent e) {
    	db = new DB();
    	Object obj = e.getSource();
		
		if (obj == btnLogin)
			if(isLoginCheck(IDText.getText())) {
				JOptionPane.showMessageDialog(null, "Login Success");
				
				if (IDText.getText().equals("admin")) {
					admin = true;
					main.showMainAdmin(); // 메인창 메소드를 이용해 창 띄우기
				}
				
				else {
					admin = false;
					main.showMainUser();
				}
			}
			
			else
				JOptionPane.showMessageDialog(null, "ID를 정확하게 입력해주세요.", "Error", JOptionPane.ERROR_MESSAGE);	
	}
    
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			btnLogin.doClick();
		
		else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			IDText.setText("");
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}