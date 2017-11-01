import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import javax.swing.JButton;

public class LoginWindow extends JFrame {
	private JPanel contentPane;
	private JTextField idField;
	private JTextField txtID;
	private JTextField pwField;
	private JPasswordField pw;
	private JButton btnNewButton;
	private JButton btnJoin;
	private JButton btnIdPwFind;

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
		idField.setText("  ID :");
		idField.setBounds(85, 100, 50, 24);
		panel.add(idField);
		idField.setColumns(10);

		txtID = new JTextField();
		txtID.setBounds(150, 100, 100, 24);
		panel.add(txtID);
		txtID.setColumns(10);
		//PW
		pwField = new JTextField();
		pwField.setText("  PW :");
		pwField.setEditable(false);
		pwField.setColumns(10);
		pwField.setBounds(85, 150, 50, 24);
		panel.add(pwField);
		
		pw = new JPasswordField();
		pw.setBounds(150, 150, 100, 24);
		panel.add(pw);
				
		btnNewButton = new JButton("Login");
		btnNewButton.setBounds(120, 210, 100, 24);
		panel.add(btnNewButton);
		
		btnJoin = new JButton("Join");
		btnJoin.setBounds(120, 270, 100, 24);
		panel.add(btnJoin);
		
		btnIdPwFind = new JButton("Find ID/PW");
		btnIdPwFind.setBounds(120, 330, 100, 24);
		panel.add(btnIdPwFind);

	}
}
