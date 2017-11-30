package spareTime;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JLabel;
import spareTime.Client;
//쿄쿄
public class ChatClient extends Thread {
   String text="";
   String name="";
    JFrame frame = new JFrame("SpareGtime 채팅창");
    JTextField textField = new JTextField();
    JTextField textName = new JTextField();
    JTextArea messageArea= new JTextArea();
    JPanel panel;
    
    public static BufferedReader in;   // 채팅할때 쓰는 
   public static PrintWriter out;       // 채팅할때 쓸거
    String other="";
    public static Socket socket=null; // 채팅할때 쓰는 소켓

    public ChatClient() throws IOException,InterruptedException 
    {
        Image bg= new ImageIcon("img/submain.png").getImage();
       messageArea = new JTextArea(){
           { setOpaque( false ) ; }
           public void paintComponent(Graphics g){
               g.drawImage(bg,0,0,null);       //이미지 그리기
               super.paintComponent(g);
           }
       };       

       /*
       panel = new JPanel(){
          {setOpaque(false);}
          
       };
       */
       
       panel =new JPanel() {
         // {setOpaque(false);}
         Image bg= new ImageIcon("img/submain.png").getImage();

         public void paintComponent(Graphics g) {
            g.drawImage(bg,0,0,getWidth(),getHeight(),this);      
         }
      };
      
       frame.getContentPane().setBackground(new Color(79,54,29));
       //전송, 귓속말 , 나가기를 각각 버튼을 만듬
       JButton b1 = new JButton(new ImageIcon(
                ((new ImageIcon("img/mainBG.jpg")).getImage()).getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH))); //전송버튼
       JButton whisper = new JButton(new ImageIcon(
                ((new ImageIcon("img/mainBG.jpg")).getImage()).getScaledInstance(80, 35, java.awt.Image.SCALE_SMOOTH))); //귓속말버튼
       JButton b2 = new JButton(new ImageIcon(
                ((new ImageIcon("img/mainBG.jpg")).getImage()).getScaledInstance(80, 55, java.awt.Image.SCALE_SMOOTH))); //나가기버튼
        whisper.setBackground(new Color(225,219,249));
        // Layout GUI
        
        textField.setEditable(false);//textField를 직은 사용하지 못하게 false값으로 초기화한다.
        messageArea.setEditable(false);//messageArea를 아직은 사용하지 못하게 false값으로 초기화한다.
        
        
        frame.setLayout(null);
        frame.setBounds(10, 10, 500, 600);
        textName.setBounds(70,10,300,35);
        textName.setBackground(new Color(79,54,29));
        textName.setForeground(Color.white);
        textName.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)));
        textField.setBounds(10, 510, 335, 35);
        panel.setBounds(15, 10, 35, 35);
        textField.setBackground(new Color(255,255,255));
        textField.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)));
        frame.getContentPane().add(textField); // textField를 생성한다.
        frame.getContentPane().add(textName);
        frame.getContentPane().add(panel);
        textField.setFont(new Font("배달의민족 주아",Font.PLAIN,15)); //폰트 연습해볼려고 넣어봤음..
        textName.setFont(new Font("배달의민족 주아",Font.PLAIN,25));
        b1.setBounds(350, 510, 35, 35);
        b1.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)));
        b2.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)));
        whisper.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)));
        messageArea.setBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)));
        b1.setOpaque(true);
        frame.getContentPane().add(b1); 
        b2.setBounds(390, 10, 80, 25);
      
        b2.setBackground(new Color(255,255,255));
        frame.getContentPane().add(b2); 
        whisper.setBounds(390, 510, 80, 35);
        frame.getContentPane().add(whisper);
        messageArea.setBounds(5,30,470,500);
        messageArea.setFont(new Font("배달의민족 주아",Font.PLAIN,15));
        JScrollPane scroll = new JScrollPane(messageArea);
        scroll.setBounds(15, 50, 450, 450);
        frame.getContentPane().add(scroll);
        frame.setVisible(true);

         //textField를 사용했을 때 취하는 액션 부분이 들어갈 것임 .-textField에 입력을 받아서 서버로 보낸다.
              
        textField.addActionListener(new ActionListener() { //textField를 사용했을 때 취하는 액션
            
            public void actionPerformed(ActionEvent e) 
            {
               //Client.out.println(textField.getText());//textField에 입력을 받아서 서버로 보낸다.
               Client.out.println(textField.getText());
               textField.setText("");
            }
        });
        b2.addActionListener(new ActionListener(){ //나가기버튼을 눌렀을 때 취하는 액션
           
           public void actionPerformed(ActionEvent e)
           {
              System.exit(0);
           }
   
        });
        
        b1.addActionListener(new ActionListener(){ //전송버튼을 눌렀을 때 취하는 액션
           
           public void actionPerformed(ActionEvent e)
           {
                out.println(textField.getText()); //서버로 보냄, 서버아직 안만들어서 빨간쥴
                textField.setText("");
           }
        });
       
        whisper.addActionListener(new ActionListener(){ //귓속말 버튼을 눌렀을 때 취하는 액션
           
            public void actionPerformed(ActionEvent e) 
             {
               other = sendWhisper(); //sendWhisper함수를 호출해서 귓속말을 보낼 사람의 이름을 입력받아서 other에 저장한다.
                 out.println(other+"@"+textField.getText()); // "귓속말 받을 사람의 이름@message" format으로 서버로 보낸다. 
                 textField.setText("");
               System.out.println("");
             }
        });
    }
    
    public void run() {
      // run 안에서는 채팅만 하게
      //String serverAddress = UI.getServerAddress();   //type using getAddress method
       
      int port=Client .getPort();
      System.out.println("port : "+port);
      try {
         socket = new Socket("127.0.0.1", port);
         in = new BufferedReader(new InputStreamReader(
               socket.getInputStream()));   //receive from server, message.
         out = new PrintWriter(socket.getOutputStream(), true); //send to server.
         

         String name="";
         String result;
         
          while (true) {
               result = in.readLine(); //서버로 부터 데이터 읽어옴
               //chatting
               if(result.equals("SUBMITNAME")){ //서버로 부터 읽어온 값이 SUBMITNAME이면
                  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
                  frame.setVisible(true); //Chatclient에서 만든 GUI를 보여줌
                  name = getName2(); //이름을 입력받음
                  if(name.equals("")){
                     JOptionPane.showMessageDialog(null, "Please input your name!", "No name",
                        JOptionPane.WARNING_MESSAGE);
                     name = getName();
                  }
                  textName.setText(name); //이름을 창에 보여줌
                  out.println(name); //서버로 이름을 보내줌
               } else if (result.startsWith("NAMEACCEPTED")) { //서버로 부터 읽어온 값이 NAMEACCEPT로 시작하면
                  textField.setEditable(true); //textField가 수정가능하게 바꿈
               } else if (result.startsWith("ENTRANCE")) {  //서버로 부터 읽어온 값이 ENTRANCE로 시작하면
                  textName.setEditable(false); //이제 textField 수정못하게 바꿈
                  text = result.substring(8); // ENTRANCE 뒤부터 메세지이기 때문에 
                  messageArea.append("<" +text+ ">" + "님이 입장하셨습니다.\n"); //messageArea에 메세지를 보여줌
                  
               } else if (result.startsWith("WHISPER")) {   //서버로 부터 읽어온 값이 WHISPER로 시작하면
                  text = result.substring(7); // WHISPER 뒤부터 메세지이기 때문에 
                  messageArea.append(text);//messageArea에 메세지를 보여줌
               } else if (result.startsWith("MESSAGE")) {//서버로 부터 읽어온 값이 MESSAGE로 시작하면
                 System.out.println(""+result);
            	  text = result.substring(8); // MESSAGE 뒤부터 메세지이기 때문에 
                  messageArea.append(text+"\n"); //messageArea에 메세지를 보여줌
               } else if (result.startsWith("EXIT")) {
                  
                  text = result.substring(4); // EXIT 뒤부터 이름이기 때문에 
                  messageArea.append("<" +text+ ">" + "님이 나가셨습니다.\n"); //messageArea에 메세지를 보여줌
               }
               
            }

         
      } catch (UnknownHostException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } //채팅소켓을 만듬 +  port부여했음

      

   }
       
    String getName2() {
      return JOptionPane.showInputDialog(frame, "Choose a screen name:", "Screen name selection",
            JOptionPane.PLAIN_MESSAGE); // 클라이언트의 이름을 입력받음
   }

   String sendWhisper() {
      return JOptionPane.showInputDialog(frame, "Who will you spend the WHISPER message?", "",
            JOptionPane.PLAIN_MESSAGE); // 귓속말을 보낼 상대의 이름을 입력받음
   }
   String getText()
   {
      return text;
   }

    
    
} 
