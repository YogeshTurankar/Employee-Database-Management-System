import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textUsername;
	private JTextField textPassword;
	
	private String username = "yogesh";
	private String password = "12345";

	static Login frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(350, 200, 550, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(40, 80, 100, 20);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPassword.setBounds(40, 130, 100, 20);
		contentPane.add(lblPassword);
		
		textUsername = new JTextField();
		textUsername.setBounds(180, 80, 250, 30);
		contentPane.add(textUsername);
		textUsername.setColumns(10);
		
		textPassword = new JTextField();
		textPassword.setColumns(10);
		textPassword.setBounds(180, 130, 250, 30);
		contentPane.add(textPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(textUsername.getText().equals(username) && textPassword.getText().equals(password)) {
					JOptionPane.showMessageDialog(null, "Login Successfull");
					frame.setVisible(false);
					frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
					Employee emp = new Employee();
					emp.frame.setVisible(true);
				}if (!textUsername.getText().equals(username)){
					JOptionPane.showMessageDialog(null, "Invalid Username");
				}if (!textPassword.getText().equals(password)) {
					JOptionPane.showMessageDialog(null, "Invalid Password");
				}
			}
		});
		btnLogin.setBackground(new Color(40, 116, 166));
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnLogin.setBounds(20, 210, 100, 40);
		contentPane.add(btnLogin);
		
		JLabel lblNewLabel_1 = new JLabel("Employee Database Login");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(244, 208, 63));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblNewLabel_1.setBounds(0, 0, 536, 40);
		contentPane.add(lblNewLabel_1);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFrame frame = new JFrame();
				if(JOptionPane.showConfirmDialog(frame, "Do you want to exit", "Employee Database System",
						JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
		});
		
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnExit.setBackground(new Color(231, 76, 60));
		btnExit.setBounds(426, 210, 100, 40);
		contentPane.add(btnExit);
		
		JButton btnChangePassword = new JButton("Change Password");
		btnChangePassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String oldPass=JOptionPane.showInputDialog("Enter old password");
				if(oldPass.equals(password)) {
					String newpass=JOptionPane.showInputDialog("Enter new password");
					setPassword(newpass);
				}else {
					JOptionPane.showMessageDialog(null, "Invalid password");
				}
			}
		});
		btnChangePassword.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnChangePassword.setBackground(new Color(223, 255, 0));
		btnChangePassword.setBounds(175, 210, 195, 40);
		contentPane.add(btnChangePassword);
	}
	


	public void setPassword(String password) {
		this.password = password;
	}
}
