package PA2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A simple Swing-based client for the chat server.  Graphically
 * it is a frame with a text field for entering messages and a
 * textarea to see the whole dialog.
 *
 * The client follows the Chat Protocol which is as follows.
 * When the server sends "SUBMITNAME" the client replies with the
 * desired screen name.  The server will keep sending "SUBMITNAME"
 * requests as long as the client submits screen names that are
 * already in use.  When the server sends a line beginning
 * with "NAMEACCEPTED" the client is now allowed to start
 * sending the server arbitrary strings to be broadcast to all
 * chatters connected to the server. When the server sends a
 * line beginning with "MESSAGE " then all characters following
 * this string should be displayed in its message area.
 */
public class ChatClient {

	BufferedReader in;	//receive to server
	PrintWriter out;	//send to server
	JFrame frame = new JFrame("Chatter");	//whole frame for client program
	JTextField textField = new JTextField(40);//need to type message sending to server
	JTextArea messageArea = new JTextArea(8, 40);// show the send and receive message from other users, system.
	JButton whisperBtn=new JButton("whispher");// can be send to whisper message quickly buutton
	JTextField whisperField = new JTextField(8);//type this swing be sent to whisper user name. can be send to whisper message quickly
	JPanel whisperPanel =new JPanel();	//Be set a whisper Button, Field. 
	/**
	 * Constructs the client by laying out the GUI and registering a
	 * listener with the textfield so that pressing Return in the
	 * listener sends the textfield contents to the server.  Note
	 * however that the textfield is initially NOT editable, and
	 * only becomes editable AFTER the client receives the NAMEACCEPTED
	 * message from the server.
	 */
	public ChatClient() {

		// Layout GUI
		textField.setEditable(false); 
		messageArea.setEditable(false); //can be edit (false)
		frame.getContentPane().add(textField, "North"); //located top
		frame.getContentPane().add(new JScrollPane(messageArea), "Center");//located center
		whisperPanel.add(whisperField);
		whisperPanel.add(whisperBtn); 	//add button and field in panel
		frame.getContentPane().add(whisperPanel, "South"); //add to pannel, located bottom.
		frame.pack();	//displayed window

		// Add Listeners
		textField.addActionListener(new ActionListener() {
			/**
			 * Responds to pressing the enter key in the textfield by sending
			 * the contents of the text field to the server.    Then clear
			 * the text area in preparation for the next message.
			 */
			public void actionPerformed(ActionEvent e) {
					out.println(textField.getText());	//out to text field
					textField.setText("");	//Initialization text field.
			}
		});

		whisperField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				whisperField.setText(null);	//Initialization whisper field.
			}
		});

		whisperBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {					
				textField.setText("<"+whisperField.getText()+"/>"); //if button is clicked, fill in text field <'whisperField contents'/>
			}
		});
	}

	/**
	 * Prompt for and return the address of the server.
	 */
	private String getServerAddress() {	//help to clients socket for input ip address
		return JOptionPane.showInputDialog(
				frame,
				"Enter IP Address of the Server:",
				"Welcome to the Chatter",
				JOptionPane.QUESTION_MESSAGE);
	}

	/**
	 * Prompt for and return the desired screen name.
	 */
	private String getName() {		//help to clients, deciding name.
		return JOptionPane.showInputDialog(
				frame,
				"Choose a screen name:",
				"Screen name selection",
				JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Connects to the server then enters the processing loop.
	 */
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
			if (line.startsWith("SUBMITNAME")) {	//server send "SUBMITNAME", when name submit and name is duplicated.
				clientName=getName();	//stored
				out.println(clientName);//send to server , the client using name
			} else if (line.startsWith("NAMEACCEPTED")) {//server send "NAMEACCEPTED", when name accept to server.
				textField.setEditable(true);//can be use(edit) text field.
				messageArea.append(line.substring(12) + "\n");
			} else if (line.startsWith("MESSAGE")) {
				messageArea.append(line.substring(8) + "\n");
			} else if(line.startsWith(clientName)){  //when the whisper message arrived with prefix, the receiver name
            	messageArea.append(line.substring(clientName.length()) + "\n"); // If ¡®clientName¡¯ equal to split client name, show split message matching client¡¯s message area.
            }
		}
	}

	/**
	 * Runs the client as an application with a closeable frame.
	 */
	public static void main(String[] args) throws Exception {
		ChatClient client = new ChatClient();	//create client
		client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//when window exit button(x) is clicked, process is terminated.  
		client.frame.setVisible(true);	//can be show frame
		client.run();
	}
}