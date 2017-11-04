package spareTime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import spareTime.UI;


public class Client {

	BufferedReader in;	
	PrintWriter out;	
	
	public Client() {
		
	}
	
	private void run() throws IOException {

		// Make connection and initialize streams
		String serverAddress = UI.getServerAddress();	//type using getAddress method
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
		UI userIn=new UI();				//UI 클래스 생성
		userIn.mp=new mainPanel(userIn);//메인 페널 생성
		userIn.frame.add(userIn.mp);	//mainpanel 이 default panel
		userIn.frame.setVisible(true);	//can be show frame

		userIn.ap=new actPanel(userIn);//act패널
		
		client.run();
	}
}
