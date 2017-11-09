dlspackage spareTime;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import javax.swing.JButton;

public class LoginWindow extends JFrame { // 로그인화면
	private JPanel contentPane;
	private JTextField idField;
	private JTextField txtID;
	private JTextField nameField;
	private JTextField txtName;
	private JButton btnNewButton;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow frame = new LoginWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 450); //전체 창
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 350, 450);
		contentPane.add(panel);
		panel.setLayout(null);
		//ID
		idField = new JTextField();
		//idField.setEnabled(false);
		idField.setEditable(false);
		idField.setText("Student ID :");
		idField.setBounds(70, 100, 80, 24);
		panel.add(idField);
		idField.setColumns(10);

		txtID = new JTextField();
		txtID.setBounds(160, 100, 120, 24);
		panel.add(txtID);
		txtID.setColumns(10);
		//PW
		nameField = new JTextField();
		nameField.setText("Name:");
		nameField.setEditable(false);
		nameField.setColumns(10);
		nameField.setBounds(70, 150, 80, 24);
		panel.add(nameField);
		
		txtName = new JTextField();
		txtName.setBounds(160, 150, 120, 24);
		panel.add(txtName);
				
		btnNewButton = new JButton("Login");
		btnNewButton.setBounds(120, 290, 100, 24);
		panel.add(btnNewButton);
		

	}
}
