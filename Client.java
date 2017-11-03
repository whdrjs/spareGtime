package spareTime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


class mainPanel extends JPanel{
	
	private JButton findBtn;
	private JButton eventBtn;
	private JButton mateBtn;
	private Client frm;
	
	public mainPanel(Client frm) {
		this.frm=frm;
		setLayout(null);
		
		findBtn=new JButton("Find activity");
		findBtn.setBounds(40, 40, 100, 24);
		add(findBtn);
		
		eventBtn=new JButton("Event");
		eventBtn.setBounds(40, 120, 100, 24);
		add(eventBtn);
		
		mateBtn=new JButton("Find mate");
		mateBtn.setBounds(40, 200, 100, 24);
		add(mateBtn);
		
		findBtn.addActionListener(new actEvent());
	}
	class actEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			frm.change("act");
		}
	}
}

class actPanel extends JPanel{
	
	private JButton dessertBtn;
	private JButton entBtn;
	private JButton univBtn;
	private JButton back;
	private Client frm;
	
	public actPanel(Client frm) {
		this.frm=frm;
		setLayout(null);
		
		dessertBtn=new JButton("Dessert");
		dessertBtn.setBounds(40, 40, 100, 24);
		add(dessertBtn);
		
		entBtn=new JButton("Entertaiment");
		entBtn.setBounds(40, 120, 100, 24);
		add(entBtn);
		
		univBtn=new JButton("Gachon Event");
		univBtn.setBounds(40, 200, 100, 24);
		add(univBtn);
		
		back=new JButton("Back");
		back.setBounds(550, 310, 100, 24);
		add(back);
		
		dessertBtn.addActionListener(new dstEvent());
		entBtn.addActionListener(new entEvent());
		univBtn.addActionListener(new univEvent());
		back.addActionListener(new backEvent());
	}
	class dstEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			frm.change("dessert");
		}
	}
	class entEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			frm.change("ent");
		}
	}
	class univEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			frm.change("univ");
		}
	}
	class backEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			frm.change("main");
		}
	}
}
public class Client {

	BufferedReader in;	
	PrintWriter out;	
	JFrame frame = new JFrame("Gtime");	
	public mainPanel mp=null;
	public actPanel ap=null;
	
	public Client() {
		frame.setSize(700,400);
		// Layout GUI
		// Add Listeners
	/*	textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					out.println(textField.getText());	//out to text field
					textField.setText("");	//Initialization text field.
			}
		});*/
		
	}
	
	public void change(String panelName)
	{
		if(panelName.equals("main")) {
			frame.getContentPane().removeAll();
			frame.getContentPane().add(mp);
			frame.revalidate();
			frame.repaint();
		}
		else if(panelName.equals("act")) {
			frame.getContentPane().removeAll();
			frame.getContentPane().add(ap);
			frame.revalidate();
			frame.repaint();
		}
			
	}
	
	private String getServerAddress() {	//help to clients socket for input ip address
		return JOptionPane.showInputDialog(
				frame,
				"Enter IP Address of the Server:",
				"Welcome to the Chatter",
				JOptionPane.QUESTION_MESSAGE);
	}

	private String getName() {		//help to clients, deciding name.
		return JOptionPane.showInputDialog(
				frame,
				"Your Nickname:",
				"Screen name selection",
				JOptionPane.PLAIN_MESSAGE);
	}

	private void run() throws IOException {

		// Make connection and initialize streams
		String serverAddress = getServerAddress();	//type using getAddress method
		Socket socket = new Socket(serverAddress, 9001); //create client socket
		in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));	//receive from server, message.
		out = new PrintWriter(socket.getOutputStream(), true); //send to server.
		
		String clientName=null;	//stored client name. and initialization
		// Process all messages from server, according to the protocol.
		while (true) {
			String line = in.readLine();
		}
	}

	public static void main(String[] args) throws Exception {
		Client client = new Client();		
		client.mp=new mainPanel(client);
		client.ap=new actPanel(client);
		
		client.frame.add(client.mp);
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//when window exit button(x) is clicked, process is terminated.  
		client.frame.setVisible(true);	//can be show frame
		client.run();
	}
}
