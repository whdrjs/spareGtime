package spareTime;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;


//import spareTime.ChatClient;
import spareTime.Client;;

/**
 * 이벤트 목록 보여주기 위한 팝업 창
 * 팝업창이어서 eventFrame 은 Frame으로
 * @author 종건
 *
 */
//배경 넣음
class eventFrame extends JFrame {
	
	JPanel contentPane;// 메인패널
	private JButton close=new JButton("close");
	
	public eventFrame() {
				
		contentPane =new JPanel() {
			Image bg= new ImageIcon("img/mainBG.jpg").getImage(); // 메인 패널에 배경 넣기
			
			public void paintComponent(Graphics g) {
				g.drawImage(bg,0,0,getWidth(),getHeight(),this);//프레임에 맞게 조절 되는 이미지
			}
		};

		setTitle("Event!");
		setSize(450,500);
		setLocation(0, 80);

		getContentPane().add(contentPane);
		setVisible(true);
	}
}
/**
 *bakery, ice, coffee , 노래방 당구장, pc방에 콤보박스는 넣겠는데
 *그 밑에 콘테츠 디비와연동하고 정렬 방법... 
 */
//배경 넣음
class listFrame extends JFrame{
	

	private JPanel mainList;
	private JPanel infoPanel;
	private JComboBox<String> combo= new JComboBox<String>();
	private JButton check;
	private JButton rateBtn;
	private JButton mateBtn;
	private JButton back;
	private JList list=new JList();
	private JScrollPane scroll=new JScrollPane();
	private UI frm;
	
	Client client=new Client();

	//DB에서 받아올 목록
	public String[] listStr= {"임시 1","임시 2","임시 3","임시 4","임시 5","임시 6","임시 7"};
	//,"임시 1","임시 2","임시 3","임시 4","임시 5","임시 6","임시 7"};
	public String select;

	public listFrame(String pre) {
		setTitle(pre);
		setSize(540,720);
		setLocation(530, 50);
		
		mainList =new JPanel() {
			Image bg= new ImageIcon("img/submain.png").getImage(); // 메인 패널에 배경 넣기
			
			public void paintComponent(Graphics g) {
				g.drawImage(bg,0,0,getWidth(),getHeight(),this);//프레임에 맞게 조절 되는 이미지
			}
		};
		mainList.setLayout(null);
		combo.addItem("평점순");
		combo.addItem("거리순");
		combo.addItem("가격순");
		combo.setEditable(false);
		combo.setBounds(150, 30, 240, 40);
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
		scroll.setBounds(70, 105, 400, 450);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); //가로바정책

		scroll.setViewportView(list);
		mainList.add(scroll);

		check=new JButton(new ImageIcon("img/select2.PNG"));
		check.setBounds(135, 580, 270, 65);
		check.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				getContentPane().removeAll();
				getContentPane().add(infoPanel);
				revalidate();
				repaint();
				setSize(960,720);
				setLocation(370, 50);
			}
		});
		mainList.add(check);
		getContentPane().add(mainList);
		setVisible(true);

		infoPanel =new JPanel() {
			Image bg= new ImageIcon("img/submain.png").getImage(); // 메인 패널에 배경 넣기
			
			public void paintComponent(Graphics g) {
				g.drawImage(bg,0,0,getWidth(),getHeight(),this);//프레임에 맞게 조절 되는 이미지
			}
		};
		infoPanel.setLayout(null);

		mateBtn=new JButton(new ImageIcon("img/findmate2.jpg"));
		mateBtn.setBackground(Color.blue);
		mateBtn.setBorderPainted(false);
		mateBtn.setFocusPainted(false); 
		mateBtn.setContentAreaFilled(false); 
		mateBtn.setBounds(550, 60, 364, 87);
		mateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new mateFrame(select);
			}
		});

		infoPanel.add(mateBtn);

		back=new JButton(new ImageIcon("img/캡처4.PNG"));
		back.setBackground(Color.red);
		back.setBounds(640, 550, 225, 56);
		back.setBorderPainted(false);
		back.setFocusPainted(false); 
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				getContentPane().removeAll();
				getContentPane().add(mainList);
				revalidate();
				repaint();
			}
		});
		infoPanel.add(back);
		
	}
	
}

class mateFrame extends JFrame{
	private JPanel panel ;
	private JLabel label = new JLabel();
	private JComboBox<String> toMain= new JComboBox<String>(); 
	private JComboBox<String> time= new JComboBox<String>(); 
	private JButton check;

	public mateFrame(String pre)
	{
		panel =new JPanel() {
			Image bg= new ImageIcon("img/submain.png").getImage(); // 메인 패널에 배경 넣기
			
			public void paintComponent(Graphics g) {
				g.drawImage(bg,0,0,getWidth(),getHeight(),this);//프레임에 맞게 조절 되는 이미지
			}
		};
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

public class UI extends JFrame{

	private JPanel mp=mainPanel();
	private JPanel ap=actPanel();
	private JPanel dp=dstPanel();
	private JPanel ep=entPanel();
	private JPanel up=univPanel();
	
	ImageIcon icon;
	
	public UI() {
		
		// Layout GUI
		setTitle("Gtime");
		setSize(1600,900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//when window exit button(x) is clicked, process is terminated. 
		getContentPane().add(mp);
	}

	//패널 바꾸어 주는 함수!
	public void change(String panelName)
	{
		if(panelName.equals("main")) {
			getContentPane().removeAll(); //기존 패널 제거
			getContentPane().add(mp);     //새 패널 추가
			revalidate();
			repaint();
		}
		else if(panelName.equals("act")) {
			getContentPane().removeAll();
			getContentPane().add(ap);
			revalidate();
			repaint();
		}
		else if(panelName.equals("dessert")) {
			getContentPane().removeAll();
			getContentPane().add(dp);
			revalidate();
			repaint();
		}
		else if(panelName.equals("ent")) {
			getContentPane().removeAll();
			getContentPane().add(ep);
			revalidate();
			repaint();
		}
		else if(panelName.equals("univ")) {
			getContentPane().removeAll();
			getContentPane().add(up);
			revalidate();
			repaint();
		}
	}

	//메인 패널
	public JPanel mainPanel()
	{
		JPanel panel;
		panel =new JPanel() {
			Image bg= new ImageIcon("img/mainBG.jpg").getImage(); // 메인 패널에 배경 넣기
			
			public void paintComponent(Graphics g) {
				g.drawImage(bg,0,0,getWidth(),getHeight(),this);//프레임에 맞게 조절 되는 이미지
			}
		};
		JButton findBtn;
		JButton eventBtn;
		JButton mateBtn;
		panel.setLayout(null);
		//이미지 추가
		findBtn=new JButton(new ImageIcon("img/findactivity2.jpg"));
		findBtn.setBackground(Color.red);
		findBtn.setBounds(573, 400, 454, 108);
		findBtn.setBorderPainted(false);
		findBtn.setFocusPainted(false); 
		findBtn.setContentAreaFilled(false); 
		findBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				change("act");
			}});
		panel.add(findBtn);

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
		panel.add(eventBtn);

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
		panel.add(mateBtn);

		return panel;
	}
	//activity 패널
	public JPanel actPanel() {
		JPanel panel ;
		JButton dessertBtn;
		JButton entBtn;
		JButton univBtn;
		JButton back;
		
		panel =new JPanel() {
			Image bg= new ImageIcon("img/mainBG.jpg").getImage(); // 메인 패널에 배경 넣기
			
			public void paintComponent(Graphics g) {
				g.drawImage(bg,0,0,getWidth(),getHeight(),this);//프레임에 맞게 조절 되는 이미지
			}
		};
			
		panel.setLayout(null);

		dessertBtn=new JButton(new ImageIcon("img/dessert.jpg"));
		dessertBtn.setBackground(Color.red);
		dessertBtn.setBounds(573, 280, 454, 108);
		dessertBtn.setBorderPainted(false);
		dessertBtn.setFocusPainted(false); 
		panel.add(dessertBtn);

		entBtn=new JButton(new ImageIcon("img/entertainment.PNG"));
		entBtn.setBackground(Color.red);
		entBtn.setBounds(573, 430, 454, 108);
		entBtn.setBorderPainted(false);
		entBtn.setFocusPainted(false); 
		panel.add(entBtn);

		univBtn=new JButton(new ImageIcon("img/Gachonevent.PNG"));
		univBtn.setBackground(Color.red);
		univBtn.setBounds(573, 580, 454, 108);
		univBtn.setBorderPainted(false);
		univBtn.setFocusPainted(false); 
		panel.add(univBtn);

		back=new JButton(new ImageIcon("img/캡처3.PNG"));
		back.setBackground(Color.red);
		back.setBounds(650, 730, 300, 74);
		back.setBorderPainted(false);
		back.setFocusPainted(false); 
		panel.add(back);

		dessertBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				change("dessert");
			}
		});
		entBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				change("ent");
			}
		});
		univBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				change("univ");
			}
		});
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				change("main");
			}	
		});
		return panel;
	}
	//디저트
	public JPanel dstPanel(){
		BufferedReader in;	//receive to server
		PrintWriter out;	//send to server
		JPanel panel;
		JButton bakeBtn;
		JButton iceBtn;
		JButton coffBtn;
		JButton back;

		panel =new JPanel() {
			Image bg= new ImageIcon("img/mainBG.jpg").getImage(); // 메인 패널에 배경 넣기
			
			public void paintComponent(Graphics g) {
				g.drawImage(bg,0,0,getWidth(),getHeight(),this);//프레임에 맞게 조절 되는 이미지
			}
		};
		panel.setLayout(null);

		bakeBtn=new JButton(new ImageIcon("img/bakery.jpg"));
		bakeBtn.setBackground(Color.red);
		bakeBtn.setBounds(573, 280, 454, 108);
		bakeBtn.setBorderPainted(false);
		bakeBtn.setFocusPainted(false); 
		panel.add(bakeBtn);

		iceBtn=new JButton(new ImageIcon("img/icecream.jpg"));
		iceBtn.setBackground(Color.red);
		iceBtn.setBounds(573, 430, 454, 108);
		iceBtn.setBorderPainted(false);
		iceBtn.setFocusPainted(false); 
		panel.add(iceBtn);

		coffBtn=new JButton(new ImageIcon("img/coffee.jpg"));
		coffBtn.setBackground(Color.red);
		coffBtn.setBounds(573, 580, 454, 108);
		coffBtn.setBorderPainted(false);
		coffBtn.setFocusPainted(false); 
		panel.add(coffBtn);

		back=new JButton(new ImageIcon("img/캡처3.PNG"));
		back.setBackground(Color.red);
		back.setBounds(650, 730, 300, 74);
		back.setBorderPainted(false);
		back.setFocusPainted(false); 
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				change("act");
			}	
		});
		panel.add(back);

		bakeBtn.addActionListener(new listEvent("Bakery"));
		coffBtn.addActionListener(new listEvent("Coffee"));
		iceBtn.addActionListener(new listEvent("Ice-cream"));
		return panel;
	}
	//오락 pannel
	public JPanel entPanel(){
		JPanel panel;
		JButton singBtn;
		JButton billBtn;
		JButton pcBtn;
		JButton back;
		panel =new JPanel() {
			Image bg= new ImageIcon("img/mainBG.jpg").getImage(); // 메인 패널에 배경 넣기
			
			public void paintComponent(Graphics g) {
				g.drawImage(bg,0,0,getWidth(),getHeight(),this);//프레임에 맞게 조절 되는 이미지
			}
		};

		panel.setLayout(null);

		singBtn=new JButton(new ImageIcon("img/singing.jpg"));
		singBtn.setBackground(Color.red);
		singBtn.setBounds(573, 280, 454, 108);
		singBtn.setBorderPainted(false);
		singBtn.setFocusPainted(false); 
		panel.add(singBtn);

		billBtn=new JButton(new ImageIcon("img/billiard.jpg"));
		billBtn.setBackground(Color.red);
		billBtn.setBounds(573, 430, 454, 108);
		billBtn.setBorderPainted(false);
		billBtn.setFocusPainted(false); 
		panel.add(billBtn);

		pcBtn=new JButton(new ImageIcon("img/pcroom.jpg"));
		pcBtn.setBackground(Color.red);
		pcBtn.setBounds(573, 580, 454, 108);
		pcBtn.setBorderPainted(false);
		pcBtn.setFocusPainted(false); 
		panel.add(pcBtn);

		back=new JButton(new ImageIcon("img/캡처3.PNG"));
		back.setBackground(Color.red);
		back.setBounds(650, 730, 300, 74);
		back.setBorderPainted(false);
		back.setFocusPainted(false); 
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				change("act");
			}	
		});
		panel.add(back);

		singBtn.addActionListener(new listEvent("Bakery"));
		billBtn.addActionListener(new listEvent("Coffee"));
		pcBtn.addActionListener(new listEvent("Ice-cream"));
		return panel;
	}
	public class listEvent implements ActionListener{
		String name;
		public listEvent(String name)
		{
			this.name=name;
		}
		public void actionPerformed(ActionEvent arg0) {
			new listFrame(name);
		}
	}

/**학교 이벤트 패널
 * 
 */
public JPanel univPanel(){
	JPanel panel=new JPanel();
	JButton back;
	panel.setLayout(null);

	panel =new JPanel() {
		Image bg= new ImageIcon("img/mainBG.jpg").getImage(); // 메인 패널에 배경 넣기
		
		public void paintComponent(Graphics g) {
			g.drawImage(bg,0,0,getWidth(),getHeight(),this);//프레임에 맞게 조절 되는 이미지
		}
	};
		back=new JButton(new ImageIcon("img/캡처3.PNG"));
		back.setBackground(Color.red);
		back.setBounds(650, 730, 300, 74);
		back.setBorderPainted(false);
		back.setFocusPainted(false); 
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				change("main");
			}	
		});
		panel.add(back);
		
		return panel;
	}
}

