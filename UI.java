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
	private JTextArea ad=new JTextArea();
	String info;
	public ArrayList<String> names=new ArrayList<String>();

	public eventFrame() {
		setTitle("Event!");
		setSize(700,500);
		setLocation(0, 80);
		try {
			info=new String(Client.getStoreData("ACT", "Advertise", " ", "0"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] store=info.split("\\^");
		String[] infoSplit;
		for(int i=0;i<store.length;i++)
		{
			System.out.println(store[i]);
			infoSplit=store[i].split("\\_");
			ad.append((String)infoSplit[1]+"\n\n");
		}
		ad.setBounds(150, 200, 400, 300);
		ad.setOpaque(false);
		ad.setEditable(false);
		contentPane.add(ad);
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

		visionRaBtn.setBounds(40, 40, 100,50);
		visionRaBtn.setContentAreaFilled(false); 
		visionRaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //거리순 을 클릭하고 비타, 복파, 동서울 을 클릭하면 서버에 필요한 정보를 주고 그거에 해당하는 가게 리스트 top4를 받을 꺼야
				pop.setVisible(false);
				try {
					rawStr=new String(Client.getStoreData(type1, type2 , type3 ,"1"));
					makeList(rawStr);//makeList가 스트링 자르고 list만들기까지다함
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
					makeList(rawStr);//makeList가 스트링 자르고 list만들기까지다함
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
						try {
							rawStr=new String(Client.getStoreData(type1, type2 , type3 ,"0"));
							makeList(rawStr);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			});
			combo.setEditable(false);
			combo.setBounds(150, 30, 240, 40);
			mainList.add(combo);

			check=new JButton(new ImageIcon("img/select2.PNG"));
			check.setBounds(135, 580, 270, 65);
			check.addActionListener(new ActionListener(){//누르면 정보 보여주는거로 바뀐다
				public void actionPerformed(ActionEvent arg0) {
					System.out.println(imgName);
					getContentPane().removeAll();
					//여기부터는 정보받고 나오는 패널 !-액션안에 패널 넣어놓음-!
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
							new mateFrame(type2,select);
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
							//정보초기화
							info.selectAll();
							info.replaceSelection("");
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
					//여기까지가 액션문안의 패널 설정
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
		//arraylist초기화
		storeList.clear();
		names.clear();
		star.clear();
		address.clear();
		//Jlist초기화
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
}
//mate!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
class mateFrame extends JFrame{
	private JPanel panel ;
	private JLabel label = new JLabel();
	private JComboBox<String> toMain= new JComboBox<String>(); 
	private JComboBox<String> time= new JComboBox<String>(); 
	private JButton check;
	String category;
	String whatTime;
	public mateFrame(String cont,String name)
	{

		panel =new JPanel() {
			Image bg= new ImageIcon("img/submain.png").getImage();

			public void paintComponent(Graphics g) {
				g.drawImage(bg,0,0,getWidth(),getHeight(),this);
			}
		};
		panel.setLayout(null);
		label.setText(cont);
		label.setBounds(250,30,100,40);

		toMain.addItem("Bakery");
		toMain.addItem("Coffee");
		toMain.addItem("Ice-cream");
		toMain.addItem("PC room");
		toMain.addItem("Billiard");
		toMain.addItem("Singing");
		toMain.setBounds(210,30,180,30);
		toMain.addActionListener(new ActionListener(){//누르면 정보 보여주는거로 바뀐다
			public void actionPerformed(ActionEvent arg0) {
				category=new String((String)toMain.getSelectedItem());
			}});

		if(name.equals("main"))
			panel.add(toMain);
		else
		{
			panel.add(label);
			category=cont;
		}

		setTitle(name);
		setSize(600,400);
		setLocation(0, 0);

		time.addItem("09:00 ~ 10:00");
		time.addItem("10:00 ~ 11:00");
		time.addItem("11:00 ~ 12:00");
		time.addItem("12:00 ~ 01:00");
		time.addItem("01:00 ~ 02:00");
		time.addItem("02:00 ~ 03:00");
		time.addItem("03:00 ~ 04:00");
		time.addItem("04:00 ~ 05:00");
		time.setBounds(180, 120, 240, 30);	
		time.addActionListener(new ActionListener(){//누르면 정보 보여주는거로 바뀐다
			public void actionPerformed(ActionEvent arg0) {
				//보내기 직전		
				whatTime=new String((String)time.getSelectedItem());
			}});
		panel.add(time);

		check=new JButton(new ImageIcon("img/select3.PNG"));
		check.setBounds(219, 300, 162, 41);
		check.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) { //roomcondtion 보내기
				//whatTime 쪼개서 시작시간만 가져오기
				//roomInforming
				Client.roomInforming(category,whatTime);
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

	ImageIcon icon;
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
				new mateFrame(null,"main");
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
		JButton univBtn;//univ버튼은 이벤트랑 액션 주요!
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
		univBtn.addActionListener(new ActionListener(){//누르면 새패널이 아니라 기존 패널에서 변경
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				revalidate();
				repaint();
				JTextArea uni=new JTextArea();
				String info = null;
				JButton back2;
					try {
						info=new String(Client.getStoreData("ACT", "Event", " ", "0"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					String[] store=info.split("\\^");
					String[] infoSplit;
					for(int i=0;i<store.length;i++)
					{
						System.out.println(store[i]);
						infoSplit=store[i].split("\\_");
						uni.append((String)infoSplit[1]+" "+(String)infoSplit[3]+"\n\n");
					}
					uni.setBounds(500, 150, 600, 450);
					uni.setOpaque(false);
					uni.setEditable(false);
					panel.add(uni);

					back2=new JButton(new ImageIcon("img/캡처3.PNG"));
					back2.setBackground(Color.red);
					back2.setBounds(650, 730, 300, 74);
					back2.setBorderPainted(false);
					back2.setFocusPainted(false); 
					back2.addActionListener(new ActionListener(){//원래패너로 돌려놓는다
						public void actionPerformed(ActionEvent e) {
							panel.removeAll();
							revalidate();
							repaint();
							panel.add(back);
							panel.add(dessertBtn);
							panel.add(entBtn);
							panel.add(univBtn);
						}	
					});
					panel.add(back2);
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

}
