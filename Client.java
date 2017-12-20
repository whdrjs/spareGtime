package spareTime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import spareTime.ChatClient;
import spareTime.UI;

public class Client {

	public String text; // text 추가
	public static BufferedReader in; // 채팅할때 쓰는
	public static PrintWriter out; // 채팅할때 쓸거

	static BufferedReader inFromServer = null;// 서버에서 보내주기
	static DataOutputStream outToServer = null; // 서버로 데이타 보내주는
	// 소켓을 두개 만들꺼예요
	public static Socket dataSocket = null; // 데이타 보내고 받을때 소켓

	UI userIn; // UI 변수
	String type1;
	ChatClient chat;
	int port;

	public Client() {
		try {
			dataSocket = new Socket("127.0.0.1", 9000);
			outToServer = new DataOutputStream(dataSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // data 보내고 받을 소켓을 만듬
	}

	public void run() throws IOException {
		// run 안에서는 채팅만 하게
		// String serverAddress = UI.getServerAddress(); //type using getAddress method
		/*
		 * socket = new Socket("127.0.0.1", 9000); //채팅소켓을 만듬 + port부여했음 in = new
		 * BufferedReader(new InputStreamReader( socket.getInputStream())); //receive
		 * from server, message. out = new PrintWriter(socket.getOutputStream(), true);
		 * //send to server.
		 */ }

	// UI 에서 가져오고 서버로 보내고 가게리스트를 받아오는 메소드
	public String getStoreData(String type1, String type2, String type3, String type4)
			throws UnsupportedEncodingException, IOException {
		String input = type1 + " " + type2 + " " + type3 + " " + type4;
		// outToServer.write(input.getBytes("UTF-8")); //이렇게 안보내면 한글이깨져요
		out = new PrintWriter(dataSocket.getOutputStream(), true);
		out.println(input);
		in = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
		String result = in.readLine();
		// System.out.println("client"+result);
		return result; // 서버로 받은 스트링
	}

	public void roomInforming(String category, String time) throws IOException, InterruptedException { // 같이드실래요 버튼을 눌렀을
																										// 때 서버로 보내는 메세지
		String type1 = "Roo"; // 이걸 받았을떄 서버에서 취하는 부분 추가
		String input = type1 + " " + category + " " + time; // bakery_1
		System.out.println("input :" + input);
		out = new PrintWriter(dataSocket.getOutputStream(), true);
		out.println(input);
		in = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
		String portNum = in.readLine();
		port = Integer.parseInt(portNum);
		ChatClient chat = new ChatClient(port);
		chat.start();
	}

	public static void main(String[] args) throws Exception {

		UI userIn = new UI(); // UI 클래스 생성
		userIn.setVisible(true); // can be show frame
	}

	public int getPort() {
		return port;
	}
}
