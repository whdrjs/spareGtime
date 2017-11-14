package spareTime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import spareTime.ChatClient;
import spareTime.UI;

public class Client {
	
	static public String text; //text 추가
	BufferedReader in;	
	PrintWriter out;	
	ChatClient chat;
	public Client() {
		
	}
	/*
	 * 예:
	 * 선택한 정보를 서버로 보내주는 class 필요
	 * 
	 * */
	// 종 : 아직 손안댄부분 -> 예: 채팅 창 GUI 보여주고  위스퍼 , 텍스트 등을 처리함 - 서버 연결하는 것만 논의 해보면됨.
	public void run() throws IOException {
		UI userIn=new UI();				//UI 클래스 생성
		userIn.setVisible(true);	//can be show frame
		// Make connection and initialize streams
		//String serverAddress = UI.getServerAddress();	//type using getAddress method
		Socket socket = new Socket("128.0.0.1", 9001); //create client socket
		in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));	//receive from server, message.
		out = new PrintWriter(socket.getOutputStream(), true); //send to server.
		
		String name="";
	    String result = "";
		String clientName=null;	//stored client name. and initialization
		while (true) {
	         result = in.readLine(); //서버로 부터 데이터 읽어옴
	         
	         //chatting
	         if(result.equals("SUBMITNAME")){ //서버로 부터 읽어온 값이 SUBMITNAME이면
	            chat.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	            chat.frame.setVisible(true); //Chatclient에서 만든 GUI를 보여줌
	            name = chat.getName(); //이름을 입력받음
	            if(name.equals("")){
	            	JOptionPane.showMessageDialog(null, "Please input your name!", "No name",
							JOptionPane.WARNING_MESSAGE);
	            	name = chat.getName();
	            }
	            chat.textName.setText(name); //이름을 창에 보여줌
	            out.println(name); //서버로 이름을 보내줌
	         } else if (result.startsWith("NAMEACCEPTED")) { //서버로 부터 읽어온 값이 NAMEACCEPT로 시작하면
	            chat.textField.setEditable(true); //textField가 수정가능하게 바꿈
	         } else if (result.startsWith("ENTRANCE")) {  //서버로 부터 읽어온 값이 ENTRANCE로 시작하면
	            chat.textName.setEditable(false); //이제 textField 수정못하게 바꿈
	            text = result.substring(8); // ENTRANCE 뒤부터 메세지이기 때문에 
	            chat.messageArea.append("<" +text+ ">" + "님이 입장하셨습니다.\n"); //messageArea에 메세지를 보여줌
	            
	         } else if (result.startsWith("WHISPER")) {   //서버로 부터 읽어온 값이 WHISPER로 시작하면
	            text = result.substring(7); // WHISPER 뒤부터 메세지이기 때문에 
	            chat.messageArea.append(text);//messageArea에 메세지를 보여줌
	         } else if (result.startsWith("MESSAGE")) {//서버로 부터 읽어온 값이 MESSAGE로 시작하면
	            text = result.substring(8); // MESSAGE 뒤부터 메세지이기 때문에 
	            chat.messageArea.append(text+"\n"); //messageArea에 메세지를 보여줌
	         } else if (result.startsWith("EXIT")) {
	            
	            text = result.substring(4); // EXIT 뒤부터 이름이기 때문에 
	            chat.messageArea.append("<" +text+ ">" + "님이 나가셨습니다.\n"); //messageArea에 메세지를 보여줌
	         }
	         
	      }
	}
	//메인
	public static void main(String[] args) throws Exception {
		Client client = new Client();		

		client.run();
	}
}
