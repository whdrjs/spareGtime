package spareTime;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JList;


import spareTime.ChatClient;
import spareTime.Client;;

class mainPanel extends JPanel{
	private JButton findBtn;
	private JButton eventBtn;
	private JButton mateBtn;
	private UI frm;	
	//JFrame chat=new ChatClient().frame;

	public mainPanel(UI frm) throws IOException {
		this.frm=frm;//UI 클래스 받아오기 메인(client.java 에서)
		setLayout(null);
		//이미지 추가
		findBtn=new JButton(new ImageIcon("img/findactivity2.jpg"));
		findBtn.setBackground(Color.red);
		findBtn.setBounds(573, 400, 454, 108);
		findBtn.setBorderPainted(false);
		findBtn.setFocusPainted(false); 
		findBtn.setContentAreaFilled(false); 
		add(findBtn);

		eventBtn=new JButton(new ImageIcon("img/promotion.jpg"));
		//event 새 창 띄우기
		eventBtn.setBackground(Color.blue);
		eventBtn.setBorderPainted(false);
		eventBtn.setFocusPainted(false); 
		eventBtn.setContentAreaFilled(false); 
		eventBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new eventFrame();
			}
		});
		eventBtn.setBounds(573, 558, 454, 108);
		add(eventBtn);

		//findmate는 채팅방 구현 생각해보고 액션이벤트 추가하려구
		mateBtn=new JButton(new ImageIcon("img/findmate.jpg"));
		mateBtn.setBackground(Color.blue);
		mateBtn.setBorderPainted(false);
		mateBtn.setFocusPainted(false); 
		mateBtn.setContentAreaFilled(false); 
		mateBtn.setBounds(573, 716, 454, 108);
		mateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new mateFrame("main");
			}
		});
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
	private UI frm;

	public actPanel(UI frm) {
		this.frm=frm;
		setLayout(null);

		dessertBtn=new JButton("Dessert");
		dessertBtn.setBounds(40, 40, 130, 24);
		add(dessertBtn);

		entBtn=new JButton("Entertainment");
		entBtn.setBounds(40, 120, 130, 24);
		add(entBtn);

		univBtn=new JButton("Gachon Event");
		univBtn.setBounds(40, 200, 130, 24);
		add(univBtn);

		back=new JButton("Back");
		back.setBounds(550, 310, 100, 24);
		add(back);

		dessertBtn.addActionListener(new nextEvent("dessert"));
		entBtn.addActionListener(new nextEvent("ent"));
		univBtn.addActionListener(new nextEvent("univ"));
		back.addActionListener(new backEvent());
	}
	class nextEvent implements ActionListener{
		String name;
		public nextEvent(String name)
		{
			this.name=name;
		}
		public void actionPerformed(ActionEvent e) {
			frm.change(name);
		}
	}
	class backEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			frm.change("main");
		}
	}
}
/**
 * 이벤트 목록 보여주기 위한 팝업 창
 * 팝업창이어서 eventFrame 은 Frame으로
 * @author 종건
 *
 */
class eventFrame extends JFrame {
	private JButton close=new JButton("close");
	JPanel ePanel =new JPanel();

	public eventFrame() {
		setTitle("Event!");
		setSize(450,500);
		setLocation(0, 80);

		getContentPane().add(ePanel);
		setVisible(true);
	}
}
//디저트 패널
class dstPanel extends JPanel{
	BufferedReader in;	//receive to server
	PrintWriter out;	//send to server
	private JButton bakeBtn;
	private JButton iceBtn;
	private JButton coffBtn;
	private JButton back;
	private UI frm;

	public dstPanel(UI frm) {
		this.frm=frm;
		setLayout(null);

		bakeBtn=new JButton("Bakery");
		bakeBtn.setBounds(40, 40, 100, 24);
		add(bakeBtn);

		iceBtn=new JButton("Ice-cream");
		iceBtn.setBounds(40, 120, 100, 24);
		add(iceBtn);

		coffBtn=new JButton("Coffee");
		coffBtn.setBounds(40, 200, 100, 24);
		add(coffBtn);

		back=new JButton("Back");
		back.setBounds(550, 310, 100, 24);
		add(back);

		bakeBtn.addActionListener(new listEvent("Bakery"));
		coffBtn.addActionListener(new listEvent("Coffee"));
		iceBtn.addActionListener(new listEvent("Ice-cream"));
		back.addActionListener(new backEvent());
	}
	class listEvent implements ActionListener{
		String name;
		public listEvent(String name)
		{
			this.name=name;
		}
		public void actionPerformed(ActionEvent arg0) {
			new listFrame(name);
		}
	}
	class backEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			frm.change("act");
		}
	}
}
//오락 pannel
class entPanel extends JPanel{

	private JButton singBtn;
	private JButton billBtn;
	private JButton pcBtn;
	private JButton back;
	private UI frm;

	public entPanel(UI frm) {
		this.frm=frm;
		setLayout(null);

		singBtn=new JButton("Singing");
		singBtn.setBounds(40, 40, 100, 24);
		add(singBtn);

		billBtn=new JButton("Billiard");
		billBtn.setBounds(40, 120, 100, 24);
		add(billBtn);

		pcBtn=new JButton("PC room");
		pcBtn.setBounds(40, 200, 100, 24);
		add(pcBtn);

		back=new JButton("Back");
		back.setBounds(550, 310, 100, 24);
		add(back);

		singBtn.addActionListener(new listEvent("Singing"));
		billBtn.addActionListener(new listEvent("Billiard"));
		pcBtn.addActionListener(new listEvent("PC room"));
		back.addActionListener(new backEvent());
	}
	class listEvent implements ActionListener{
		String name;
		public listEvent(String name)
		{
			this.name=name;
		}
		public void actionPerformed(ActionEvent arg0) {
			new listFrame(name);
		}
	}
	class backEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			frm.change("act");
		}
	}
}
/**학교 이벤트 패널
 * 
 */
class univPanel extends JPanel{

	private JButton Btn;
	private JButton Btn2;
	private JButton back;
	private UI frm;

	public univPanel(UI frm) {
		this.frm=frm;
		setLayout(null);

		Btn=new JButton("Gachon Univ event");
		Btn.setBounds(40, 40, 160, 24);
		add(Btn);

		back=new JButton("Back");
		back.setBounds(550, 310, 100, 24);
		add(back);

		back.addActionListener(new backEvent());
	}
	class backEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			frm.change("act");
		}
	}
}
/**
 *bakery, ice, coffee , 노래방 당구장, pc방에 콤보박스는 넣겠는데
 *그 밑에 콘테츠 디비와연동하고 정렬 방법... 
 */
class listFrame extends JFrame{
	private JPanel mainList=new JPanel();
	private JPanel infoPanel=new JPanel();
	private JComboBox<String> combo= new JComboBox<String>();
	private JButton check;
	private JButton rateBtn;
	private JButton mateBtn;
	private JButton backBtn;
	private JList list=new JList();
	private JScrollPane scroll=new JScrollPane();
	private UI frm;
	Client client=new Client();
	
	//DB에서 받아올 목록
	public String[] listStr= {"임시 1","임시 2","임시 3","임시 4","임시 5","임시 6","임시 7"};//,"임시 1","임시 2","임시 3","임시 4","임시 5","임시 6","임시 7"};
	public String select;

	public listFrame(String pre) {
		setTitle(pre);
		setSize(600,400);
		setLocation(0, 0);

		mainList.setLayout(null);
		combo.addItem("평점순");
		combo.addItem("거리순");
		combo.addItem("가격순");
		combo.setEditable(false);
		combo.setBounds(210, 20, 180, 30);
		mainList.add(combo);

		//list
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setListData(listStr); //리스트의 데이터가 될 목록 설정
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {					
				select=" ";//데이터베이스에서 가져온 가게이름 선택하면 집어넣기
			}
		}); //이벤트리스너 장착

		scroll.setViewportView(list);
		scroll.setBounds(175, 75, 250, 200);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); //가로바정책

		scroll.setViewportView(list);
		mainList.add(scroll);

		check=new JButton("pick");
		check.setBounds(260, 300, 80, 24);
		check.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				getContentPane().removeAll();
				getContentPane().add(infoPanel);
				revalidate();
				repaint();
			}
		});
		mainList.add(check);
		getContentPane().add(mainList);
		setVisible(true);
		
		infoPanel.setLayout(null);
		
		rateBtn=new JButton("rate");
		rateBtn.setBounds(440, 40, 100, 24);
		infoPanel.add(rateBtn);
		
		mateBtn=new JButton("Find mate");
		mateBtn.setBounds(440, 88, 100, 24);
		infoPanel.add(mateBtn);
		
		backBtn=new JButton("Back");
		backBtn.setBounds(440, 300, 100, 24);
		backBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				getContentPane().removeAll();
				getContentPane().add(mainList);
				revalidate();
				repaint();
			}
		});
		
		mateBtn.addActionListener(new mateEvent());
		infoPanel.add(backBtn);
	}
	class mateEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			new mateFrame(select);
		}
	}
}
class mateFrame extends JFrame{
	private JPanel panel = new JPanel();
	private JLabel label = new JLabel();
	private JComboBox<String> toMain= new JComboBox<String>(); 
	private JComboBox<String> time= new JComboBox<String>(); 
	private JButton check;
	
	public mateFrame(String pre)
	{
		panel.setLayout(null);
		label.setText(pre);
		label.setBounds(250,30,100,40);
		
		toMain.addItem("Bakery");
		toMain.addItem("Coffee");
		toMain.addItem("Ice-cream");
		toMain.addItem("PC room");
		toMain.addItem("Billiard");
		toMain.addItem("Singing");
		toMain.setBounds(210,30,180,30);
		
		if(pre.equals("main"))
			panel.add(toMain);
		else
			panel.add(label);
			
		setTitle(pre);
		setSize(600,400);
		setLocation(0, 0);
		
		time.addItem("9:00 ~ 10:00");
		time.addItem("10:00 ~ 11:00");
		time.addItem("11:00 ~ 12:00");
		time.addItem("12:00 ~ 1:00");
		time.addItem("1:00 ~ 2:00");
		time.addItem("2:00 ~ 3:00");
		time.addItem("3:00 ~ 4:00");
		time.addItem("4:00 ~ 5:00");
		time.addItem("5:00 ~ 6:00");
		time.addItem("6:00 ~ 7:00");
		time.setBounds(180, 120, 240, 30);	
		panel.add(time);
		
		check=new JButton("check");
		check.setBounds(260, 320, 80, 24);
		check.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
	
			}
		});
		panel.add(check);		
		
		getContentPane().add(panel);
		
		setVisible(true);
	}
}

public class UI {

	static JFrame frame = new JFrame("Gtime");
	public mainPanel mp=null;
	public actPanel ap=null;
	public dstPanel dp=null; 
	public entPanel ep=null;
	public univPanel up=null;

	public UI() {
		// Layout GUI
		frame.setSize(1600,900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//when window exit button(x) is clicked, process is terminated. 
	}
	//패널 바꾸어 주는 함수!
	public void change(String panelName)
	{
		if(panelName.equals("main")) {
			frame.getContentPane().removeAll(); //기존 패널 제거
			frame.getContentPane().add(mp);     //새 패널 추가
			frame.revalidate();
			frame.repaint();
		}
		else if(panelName.equals("act")) {
			frame.getContentPane().removeAll();
			frame.getContentPane().add(ap);
			frame.revalidate();
			frame.repaint();
		}
		else if(panelName.equals("dessert")) {
			frame.getContentPane().removeAll();
			frame.getContentPane().add(dp);
			frame.revalidate();
			frame.repaint();
		}
		else if(panelName.equals("ent")) {
			frame.getContentPane().removeAll();
			frame.getContentPane().add(ep);
			frame.revalidate();
			frame.repaint();
		}
		else if(panelName.equals("univ")) {
			frame.getContentPane().removeAll();
			frame.getContentPane().add(up);
			frame.revalidate();
			frame.repaint();
		}
	}

	public static String getServerAddress() {	//help to clients socket for input ip address
		return JOptionPane.showInputDialog(
				frame,
				"Enter IP Address of the Server:",
				"Welcome to the Chatter",
				JOptionPane.QUESTION_MESSAGE);
	}

	public static String getName() {		//help to clients, deciding name.
		return JOptionPane.showInputDialog(
				frame,
				"Your Nickname:",
				"Screen name selection",
				JOptionPane.PLAIN_MESSAGE);
	}
}
