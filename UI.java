package spareTime;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.*;
import javax.swing.JList;

//import spareTime.ChatClient;
import spareTime.Client;

class eventFrame extends JFrame {
	JPanel contentPane=new JPanel() {
		Image bg= new ImageIcon("img/mainBG.jpg").getImage();
		public void paintComponent(Graphics g) {
			g.drawImage(bg,0,0,getWidth(),getHeight(),this);
		}
	};
	private JButton close=new JButton("close");

	public eventFrame() {
		setTitle("Event!");
		setSize(450,500);
		setLocation(0, 80);
		getContentPane().add(contentPane);
		setVisible(true);
	}
}

class listFrame extends JFrame{
	private JPanel mainList;
	private JPanel infoPanel;
	private JComboBox<String> combo= new JComboBox<String>();
	private JTextArea info=new JTextArea();
	private JButton check;
	private JButton mateBtn;
	private JButton back;
	private JButton img;
	private JList<String> list=new JList<String>();
	private JScrollPane scroll=new JScrollPane();

	public String type1="ACT"; //act 인지 event 인지
	public String type2;//bakery, coffee,ice_cream 인지
	public String type3=" ";// priority (distance 아니면 star 인지 ).
	public String rawStr;

	Client client=new Client();

	public ArrayList<String> storeList=new ArrayList<String>();//가게 리스트
	//가게 정보 저장
	public ArrayList<String> names=new ArrayList<String>();
	public ArrayList<String> star=new ArrayList<String>();
	public ArrayList<String> address=new ArrayList<String>();

	public String select;
	public String imgName;
	private void distance(){
		System.out.println("켜짐");
		JFrame pop=new JFrame();
		JRadioButton visionRaBtn = new JRadioButton("비전타워");
		JRadioButton policeRaBtn = new JRadioButton("복정파출소");
		JRadioButton otherRaBtn = new JRadioButton("동서울대");

		pop.setTitle("위치");
		pop.setSize(420,200);
		pop.setLocation(470, 40);
		JPanel pan =new JPanel() {
			Image bg= new ImageIcon("img/submain.png").getImage();
			public void paintComponent(Graphics g) {
				g.drawImage(bg,0,0,getWidth(),getHeight(),this);
			}
		};
		pan.setLayout(null);
		//버튼 그룹으로
		ButtonGroup bg=new ButtonGroup();
		bg.add(visionRaBtn);
		bg.add(policeRaBtn);
		bg.add(otherRaBtn);
		storeList.clear();
		names.clear();
		star.clear();
		address.clear();
		
		visionRaBtn.setBounds(40, 40, 100,50);
		visionRaBtn.setContentAreaFilled(false); 
		visionRaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //거리순 을 클릭하고 비타, 복파, 동서울 을 클릭하면 서버에 필요한 정보를 주고 그거에 해당하는 가게 리스트 top4를 받을 꺼야
				pop.setVisible(false);
				try {
					rawStr=new String(Client.getStoreData(type1, type2 , type3 ,"1"));
					makeList(rawStr);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(rawStr);
			}
		});
		policeRaBtn.setBounds(150, 40, 100,50);
		policeRaBtn.setContentAreaFilled(false); 
		policeRaBtn.addActionListener(new ActionListener() { //거리순 을 클릭하고 비타, 복파, 동서울 을 클릭하면 서버에 필요한 정보를 주고 그거에 해당하는 가게 리스트 top4를 받을 꺼야
			public void actionPerformed(ActionEvent e) {
				pop.setVisible(false);
				try {
					rawStr=new String(Client.getStoreData(type1, type2 , type3 ,"2"));
					makeList(rawStr);//makeList가 스트링 자르고 list만들기까지다함
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		otherRaBtn.setBounds(280, 40, 100,50);
		otherRaBtn.setContentAreaFilled(false); 
		otherRaBtn.addActionListener(new ActionListener() { //거리순 을 클릭하고 비타, 복파, 동서울 을 클릭하면 서버에 필요한 정보를 주고 그거에 해당하는 가게 리스트 top4를 받을 꺼야
			public void actionPerformed(ActionEvent e) {
				pop.setVisible(false);
				System.out.println("버튼눌러짐");
				try {
					rawStr=new String(Client.getStoreData(type1, type2 , type3 ,"3"));
					makeList(rawStr);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		//추가
		pop.getContentPane().add(new JLabel("가장 근접한 위치를 골라주세요"));
		pan.add(visionRaBtn);
		pan.add(policeRaBtn);
		pan.add(otherRaBtn);
		pan.setVisible(true);
		pop.getContentPane().add(pan,BorderLayout.CENTER);
		pop.setVisible(true);
	}
	public listFrame(String pre) { 
		setTitle(pre);
		type2=pre;
		setSize(540,720);
		setLocation(530, 50);

		mainList =new JPanel() {
			Image bg= new ImageIcon("img/submain.png").getImage();
			public void paintComponent(Graphics g) {
				g.drawImage(bg,0,0,getWidth(),getHeight(),this);
			}};
		//기본값
		try {
			rawStr=new String(Client.getStoreData(type1, type2 , type3 ,"0"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mainList.setLayout(null);
		
		mainList.add(list);
		makeList(rawStr);//makeList가 스트링 자르고 list만들기까지다함

		combo.addItem("Distance");
		combo.addItem("Rate");
		combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(((String)combo.getSelectedItem()).compareTo("Distance")==0)
				{
					type3="distance"; //distance 를 넣어줄꺼야
					distance();
				}
				if(((String)combo.getSelectedItem()).compareTo("Rate")==0)
				{
					type3="star"; //distance 를 넣어줄꺼야
					list.removeAll();
				}
			}
		});

		combo.setEditable(false);
		combo.setBounds(150, 30, 240, 40);
		mainList.add(combo);

		check=new JButton(new ImageIcon("img/select2.PNG"));
		check.setBounds(135, 580, 270, 65);
		//누르면 정보 보여주는거로 바뀐다
		check.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(imgName);
				getContentPane().removeAll();
				//여기부터는 정보받고 나오는 패널
				infoPanel =new JPanel() {
					Image bg= new ImageIcon("img/submain.png").getImage(); 

					public void paintComponent(Graphics g) {
						g.drawImage(bg,0,0,getWidth(),getHeight(),this);
					}
				};
				infoPanel.setLayout(null);

				img=new JButton(new ImageIcon(imgName));
				img.setBackground(Color.blue);
				img.setBorderPainted(false);
				img.setFocusPainted(false); 
				img.setContentAreaFilled(false); 
				img.setBounds(10, 10, 570, 435);

				int idx= names.indexOf(select);
				//System.out.println(new String(names.get(idx)));
				info.append(names.get(idx)+"\n");
				info.append(address.get(idx));
				info.setBounds(10, 455, 500, 100);
				info.setOpaque(false);
				info.setEditable(false);

				mateBtn=new JButton(new ImageIcon("img/findmate3.jpg"));
				mateBtn.setBackground(Color.blue);
				mateBtn.setBorderPainted(false);
				mateBtn.setFocusPainted(false); 
				mateBtn.setContentAreaFilled(false); 
				mateBtn.setBounds(610, 60, 295, 71);
				mateBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						new mateFrame(select);
					}
				});
				infoPanel.add(img);
				infoPanel.add(info);
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
				getContentPane().add(infoPanel);
				revalidate();
				repaint();
				setSize(960,720);
				setLocation(370, 50);
			}
		});
		mainList.add(check);
		getContentPane().add(mainList);
		setVisible(true);//여기까지가 맨처음 실행했을때의 swing들을 위한
	}
	//makeList가 스트링 자르고 list만들기까지다함
	private void makeList (String str) {
		System.out.println(str);
		String[] store=str.split("\\^");
		String[] infoSplit;
		mainList.remove(list);
		mainList.revalidate();
		mainList.repaint();
		for(int i=0;i<store.length;i++)
		{
			System.out.println(store[i]);
			infoSplit=store[i].split("\\_");
			names.add(infoSplit[1]);
			star.add(infoSplit[2]);
			address.add(infoSplit[3]);
		}
		list=new JList(names.toArray());
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {					
				select=list.getSelectedValue();// 선택!
				imgName=new String("img/"+select+".png");//지도 위치 스트링으로
				System.out.println(select);
			}
		}); 
		list.setBounds(70, 105, 400, 450);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mainList.add(list);
	}
	public static ArrayList<String> cloneList(ArrayList<String>list) {
		ArrayList<String> clone = new ArrayList<String>(list.size());
		for(String item: list) clone.add(new String(item));
		return clone;
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
			Image bg= new ImageIcon("img/submain.png").getImage();

			public void paintComponent(Graphics g) {
				g.drawImage(bg,0,0,getWidth(),getHeight(),this);
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
		//setDefaultCloseOperation(pre.);
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
	String type1;
	String type2;
	String type3;

	String name;
	public String getType1() {
		return this.type1;
	}
	public UI() {
		setTitle("Gtime");
		setSize(1600,900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//when window exit button(x) is clicked, process is terminated. 
		getContentPane().add(mp);
	}

	public void change(String panelName)
	{
		if(panelName.equals("main")) {
			getContentPane().removeAll(); 
			getContentPane().add(mp);
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

	//main
	public JPanel mainPanel()
	{
		JPanel panel;
		panel =new JPanel() {
			Image bg= new ImageIcon("img/mainBG.jpg").getImage(); //이미지파일

			public void paintComponent(Graphics g) {
				g.drawImage(bg,0,0,getWidth(),getHeight(),this);
			}
		};
		JButton findBtn;
		JButton eventBtn;
		JButton mateBtn;
		panel.setLayout(null);
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
	//activity
	public JPanel actPanel() {
		JPanel panel ;
		JButton dessertBtn;
		JButton entBtn;
		JButton univBtn;
		JButton back;

		panel =new JPanel() {
			Image bg= new ImageIcon("img/mainBG.jpg").getImage(); // ���� �гο� ��� �ֱ�

			public void paintComponent(Graphics g) {
				g.drawImage(bg,0,0,getWidth(),getHeight(),this);//�����ӿ� �°� ���� �Ǵ� �̹���
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

	public JPanel dstPanel(){
		BufferedReader in;	//receive to server
		PrintWriter out;	//send to server
		JPanel panel;
		JButton bakeBtn;
		JButton iceBtn;
		JButton coffBtn;
		JButton back;

		panel =new JPanel() {
			Image bg= new ImageIcon("img/mainBG.jpg").getImage();

			public void paintComponent(Graphics g) {
				g.drawImage(bg,0,0,getWidth(),getHeight(),this);
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
		iceBtn.addActionListener(new listEvent("Ice"));
		return panel;

	}
	// pannel
	public JPanel entPanel(){
		JPanel panel;
		JButton singBtn;
		JButton billBtn;
		JButton pcBtn;
		JButton back;
		panel =new JPanel() {
			Image bg= new ImageIcon("img/mainBG.jpg").getImage();

			public void paintComponent(Graphics g) {
				g.drawImage(bg,0,0,getWidth(),getHeight(),this);
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

		singBtn.addActionListener(new listEvent("Sing"));
		billBtn.addActionListener(new listEvent("Billiard"));
		pcBtn.addActionListener(new listEvent("Pcroom"));
		return panel;
	}

	public class listEvent implements ActionListener{
		String preCont;

		public listEvent(String cont)
		{
			preCont=cont;
		}

		public void actionPerformed(ActionEvent arg0) {
			new listFrame(preCont);
		}
	}
	public JPanel univPanel(){
		JPanel panel=new JPanel();
		JButton back;
		panel.setLayout(null);

		panel =new JPanel() {
			Image bg= new ImageIcon("img/mainBG.jpg").getImage();

			public void paintComponent(Graphics g) {
				g.drawImage(bg,0,0,getWidth(),getHeight(),this);
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
