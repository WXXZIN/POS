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
    
    // mainProcess       
    public void setMain(MainProcess main) {
        this.main = main;
    }
    
    private boolean LoginCheck(String id) {
		boolean login = false;
		
		db.connectDB();
		db.sql = "select * from users where ID = ?";
		
		try {
			db.pstmt = db.conn.prepareStatement(db.sql);
			db.pstmt.setString(1, id);
			
			db.rs = db.pstmt.executeQuery();
			
			while(db.rs.next()) {
				if(id.equals(db.rs.getString("ID")))
					login = true;
								
				else
					login = false;
			}
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			db.disconnectDB();
		}
		
		return login;
	}
    
    @Override
	public void actionPerformed(ActionEvent e) {
    	db = new DB();
    	Object obj = e.getSource();
		
		if (obj == btnLogin)
			if(LoginCheck(IDText.getText())) {
				JOptionPane.showMessageDialog(null, "Login Success");
				
				if (IDText.getText().equals("admin"))
					admin = true;
				else
					admin = false;
				
				main.showMain(admin);
			}
			
			else
				JOptionPane.showMessageDialog(null, "ID가 존재하지 않습니다.", "Error", JOptionPane.ERROR_MESSAGE);	
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