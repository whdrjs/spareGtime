package spareTime;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Vector;
//server receive message, search data from SQL, send result to client.
public class server {
	private   final int PORT = 9000;
	private   HashSet<String> names = new HashSet<String>();
	private   HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
	Vector<Handler> vc;

	public server() throws Exception {
		System.out.println("The server is running.");
		ServerSocket listener = new ServerSocket(PORT);
		//plus user handler stack
 		vc = new Vector<Handler>();
		try {
			while (true) {
				Handler hd = new Handler(listener.accept());
				hd.start();
				vc.add(hd);
			}
		} finally {
			listener.close();
		}
	}
	
	public void removeClient(Handler hd) {
		// Remove client to vector.
		vc.remove(hd);
	}
	//handler connect with client.
	private class Handler extends Thread {
		private String name;
		private Socket socket;
		private BufferedReader in;
		private PrintWriter out;

		public Handler(Socket socket) {
			this.socket = socket;
		}


		public void run()  {
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				String a;

				while (true) {
					String input = in.readLine();
					if (input == null) {
						return;
					}
					String command=input.substring(0,3);
					//search store name
					if(command.equals("ACT")) {
						//command example.
						//"ACT content distance 1"
						//"ACT content star"
						String input1[]=input.split(" ");
						ArrayList<content_info> inform = new ArrayList<content_info>();
						inform = search_content(input1[1]);
						if(input1[2].equals("distance")){
							//return 4 stores sort by distance 
							//each store has number to bokjeong from gachon subway station n.
							//user select own location a, sort by |a-n| and rank. 
							int i,j;
							ArrayList<content_info> rank = new ArrayList<content_info>();
							switch (input1[3]){
							case "1": //vision tower 7
								for(i=0;i<inform.size();i++){
									inform.get(i).distance=inform.get(i).distance-7;
									if(inform.get(i).distance<0)
										inform.get(i).distance=inform.get(i).distance*-1;
								}
								break;
							case "2": //police station 20
								for(i=0;i<inform.size();i++){
									inform.get(i).distance=inform.get(i).distance-20;
									if(inform.get(i).distance<0)
										inform.get(i).distance=inform.get(i).distance*-1;
								}
								break;
							case "3": //dong seoul univercity 27
								for(i=0;i<inform.size();i++){
									inform.get(i).distance=inform.get(i).distance-27;
									if(inform.get(i).distance<0)
										inform.get(i).distance=inform.get(i).distance*-1;
								}
							}
							int min[]={0,1,2,3};
							for(i=4;i<inform.size();i++){
								int c=0;
								for(j=0;j<4;j++){
									if(inform.get(min[j]).distance>inform.get(min[c]).distance){
										c=j;
									}
								}
								if(inform.get(i).distance<inform.get(min[c]).distance){
									min[c]=i;
								}
							}					int m1=0,m2=0;
							for(i=0;i<4;i++){
								if(inform.get(min[i]).distance>inform.get(min[m1]).distance){
									m1=i;
								}
								if(inform.get(min[i]).distance<inform.get(min[m2]).distance){
									m2=i;
								}
							}
							int temp;
							temp=min[3];
							min[3]=min[m1];
							min[m1]=temp;
							temp=min[0];
							min[0]=min[m2];
							min[m2]=temp;
							if(inform.get(min[2]).distance<inform.get(min[1]).distance){
								temp=min[1];
								min[1]=min[2];
								min[2]=temp;
							}
							for(i=0;i<4;i++){
								rank.add(inform.get(min[i]));
							}	 
							a="";
							for(i=0;i<rank.size();i++) {
								a=a+rank.get(i).output();
							}
							out.println(a);
						}
						else if(input1[2].equals("star")){
						//return 4 stores sort by star 
							int i,j;
							ArrayList<content_info> rank = new ArrayList<content_info>();
							int min[]={0,1,2,3};
							for(i=4;i<inform.size();i++){
								int c=0;
								for(j=0;j<4;j++){
									if(inform.get(min[j]).star>inform.get(min[c]).star){
										c=j;
									}
								}
								if(inform.get(i).star<inform.get(min[c]).star){
									min[c]=i;
								}
							}
							int m1=0,m2=0;
							//if equal star, low distance is more priority
							for(i=0;i<4;i++){
								if(inform.get(min[i]).distance>inform.get(min[m1]).distance){
									m1=i;
								}
								if(inform.get(min[i]).distance<inform.get(min[m2]).distance){
									m2=i;
								}
							}
							int temp;
							temp=min[3];
							min[3]=min[m1];
							min[m1]=temp;
							temp=min[0];
							min[0]=min[m2];
							min[m2]=temp;
							if(inform.get(min[2]).distance<inform.get(min[1]).distance){
								temp=min[1];
								min[1]=min[2];
								min[2]=temp;
							}
							for(i=0;i<4;i++){
								rank.add(inform.get(min[i]));
							}	 
							a="";
							for(i=0;i<rank.size();i++) {
								a=a+rank.get(i).output();
							}
							out.println(a);
						}
						else{
							a="";
							for(int i=0;i<inform.size();i++) {
								a=a+inform.get(i).output();
								
							}
							out.println(a);
						}
					}
					
					//enter chatting room.
					//if chatting room's port open already, just enter.
					//else make new port about chatting room
					if(command.equals("Roo")) {
						String input1[]=input.split(" ");
						System.out.println(input1[1]+" "+input1[2]);
						int port[]=search_room(input1[2],input1[1]);
						out.println(port[0]);
						if(port[1]==0)
							new ChatServer(port[0]);
					}
				}
			} catch ( Exception e) {
				System.out.println(e);
			} finally {

				if (name != null) {
					names.remove(name);
				}
				if (out != null) {
					writers.remove(out);
				}
				removeClient(this);
				try {
					socket.close();
				} catch (IOException e) {
				}
			}

		}

		public Handler findThread(String name) {
			Handler hd = null;
			for (int i = 0; i < vc.size(); i++) {
				hd = vc.get(i);
				if (hd.name.equals(name))
					break;
			}
			return hd;
		}
	}


	public   Scanner in = new Scanner(System.in);

	public   Connection getConnection() throws ClassNotFoundException, SQLException {
		// connect SQL database
		String url = "jdbc:mysql://localhost:3306/sparegtime";// 경로
		String user = "root";// ID
		String pass = "1234";// password

		Connection conn = null;
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, pass);
		System.out.println("접속");

		return conn;
	}

	
	public int[] search_room(String time, String content)
			throws ClassNotFoundException, SQLException {

		// 방목록을 가져오는 함수
		// 공강시간 요일 컨텐츠로 sql에서 검색해서 반환
		
		Connection conn = getConnection();
		String sql = "select port,open from roomlist where time = ? and content = ?";// sql 쿼리
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, time);
		pstmt.setString(2, content);

		ResultSet res = pstmt.executeQuery();
		if (res != null) {
			System.out.println("탐색완료");
		} else {
			System.out.println("정보에 맞는 방이 없습니다");
			return null;
		}
		int port[] = new int[2];
		res.next();
		port[0]=res.getInt("port");
		port[1]=res.getInt("open");
		return port;
	}

	public ArrayList<content_info> search_content(String content) throws ClassNotFoundException, SQLException {

		// 카테고리에서 컨텐츠를 찾아오는 함수
		// 매뉴에서 컨텐츠를 보여줄때 사용

		Connection conn = getConnection();
		ArrayList<content_info>inform = new ArrayList<content_info>();
		
		String sql = "select * from contents where content = ?";// sql 쿼리
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, content);

		ResultSet res = pstmt.executeQuery();
		if (res != null)
			System.out.println("탐색완료");
		int i=0;
		while (res.next()) {
			content_info a=new content_info();
			a.add(res);
			inform.add(a);
			inform.set(i, a);
			i++;
		}
		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();
		
		return inform;
	}

	public static void main(String[] args) throws Exception {
		new server();
	}
}
