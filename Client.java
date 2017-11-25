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

import javax.swing.JFrame;
import javax.swing.JOptionPane;

//import spareTime.ChatClient;
import spareTime.UI;

public class Client {

	public static String text; //text 추가
	public static BufferedReader in;	// 채팅할때 쓰는 
	public static PrintWriter out;       // 채팅할때 쓸거

	static BufferedReader inFromServer=null;//서버에서  보내주기	
	static DataOutputStream outToServer=null; // 서버로 데이타 보내주는 
	//소켓을 두개 만들꺼예요
	public static Socket dataSocket= null ; //데이타 보내고 받을때 소켓
	public static Socket chatSocket=null; // 채팅할때 쓰는 소켓

	UI userIn; // UI 변수
	static String type1;
	//ChatClient chat;
	public Client() {

	}

	/*
	 * 예:
	 * 선택한 정보를 서버로 보내주는 class 필요
	 * 
	 * */
	// 종 : 아직 손안댄부분 -> 예: 채팅 창 GUI 보여주고  위스퍼 , 텍스트 등을 처리함 - 서버 연결하는 것만 논의 해보면됨.
	public void run() throws IOException {
		userIn = new UI();				//UI 클래스 생성
		userIn.setVisible(true);	//can be show frame

		//String serverAddress = UI.getServerAddress();	//type using getAddress method
		chatSocket = new Socket("127.0.0.1", 9000); //채팅소켓을 만듬 +  port부여했음
		in = new BufferedReader(new InputStreamReader(
				chatSocket.getInputStream()));	//receive from server, message.
		out = new PrintWriter(chatSocket.getOutputStream(), true); //send to server.

		dataSocket=new Socket("127.0.0.1", 9000); //data 보내고 받을 소켓을 만듬
		outToServer=new DataOutputStream(dataSocket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(
				dataSocket.getInputStream()));	


		String name="";
		String clientName=null;	
		//String storeInfo = ""; //서버에서 한 스트링으로 넘겨주니까 (가게 1, 가게 2 ,가게3, 가게 4)
		// run 안에서는 채팅만 하게
		//chatting-> chatServer에 넣을지 Server 에서 합칠지, 그런데 후자가 더 바람직할거 같아.
/*
 -	         if(result.equals("SUBMITNAME")){ //서버로 부터 읽어온 값이 SUBMITNAME이면
 -	            chat.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
 -	            chat.frame.setVisible(true); //Chatclient에서 만든 GUI를 보여줌
 -	            name = chat.getName(); //이름을 입력받음
 -	            if(name.equals("")){
 -	            	JOptionPane.showMessageDialog(null, "Please input your name!", "No name",
 -							JOptionPane.WARNING_MESSAGE);
 -	            	name = chat.getName();
 -	            }
 -	            chat.textName.setText(name); //이름을 창에 보여줌
 -	            out.println(name); //서버로 이름을 보내줌
 -	         } else if (result.startsWith("NAMEACCEPTED")) { //서버로 부터 읽어온 값이 NAMEACCEPT로 시작하면
 -	            chat.textField.setEditable(true); //textField가 수정가능하게 바꿈
 -	         } else if (result.startsWith("ENTRANCE")) {  //서버로 부터 읽어온 값이 ENTRANCE로 시작하면
 -	            chat.textName.setEditable(false); //이제 textField 수정못하게 바꿈
 -	            text = result.substring(8); // ENTRANCE 뒤부터 메세지이기 때문에 
 -	            chat.messageArea.append("<" +text+ ">" + "님이 입장하셨습니다.\n"); //messageArea에 메세지를 보여줌
 -
 -	         } else if (result.startsWith("WHISPER")) {   //서버로 부터 읽어온 값이 WHISPER로 시작하면
 -	            text = result.substring(7); // WHISPER 뒤부터 메세지이기 때문에 
 -	            chat.messageArea.append(text);//messageArea에 메세지를 보여줌
 -	         } else if (result.startsWith("MESSAGE")) {//서버로 부터 읽어온 값이 MESSAGE로 시작하면
 -	            text = result.substring(8); // MESSAGE 뒤부터 메세지이기 때문에 
 -	            chat.messageArea.append(text+"\n"); //messageArea에 메세지를 보여줌
 -	         } else if (result.startsWith("EXIT")) {
 -
 -	            text = result.substring(4); // EXIT 뒤부터 이름이기 때문에 
 -	            chat.messageArea.append("<" +text+ ">" + "님이 나가셨습니다.\n"); //messageArea에 메세지를 보여줌
 -	         }
 -		 */

	}
	//UI 에서 가져오고 서버로 보내고  가게리스트를 받아오는 메소드
	public static String getStoreData(String type1, String type2 , String type3 , String type4) throws UnsupportedEncodingException, IOException{
		String input=type1+" "+type2+" "+type3+" "+type4;
		//outToServer.write(input.getBytes("UTF-8")); //이렇게 안보내면 한글이깨져요
		out = new PrintWriter(dataSocket.getOutputStream(), true);
		out.println(input);
		in = new BufferedReader(new InputStreamReader(
				dataSocket.getInputStream()));
		String result = in.readLine();
	//	System.out.println("client"+result);
		return result; //서버로 받은 스트링
	}
	public static void main(String[] args) throws Exception {
		Client client = new Client();		

		client.run();

	}
}
