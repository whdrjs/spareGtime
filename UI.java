package spareTime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

class mainPanel extends JPanel{
	
	private JButton findBtn;
	private JButton eventBtn;
	private JButton mateBtn;
	private UI frm;	
	
	public mainPanel(UI frm) {
		this.frm=frm;//UI 클래스 받아오기 메인(client.java 에서)
		setLayout(null);
		
		findBtn=new JButton("Find activity");
		findBtn.setBounds(40, 40, 100, 24);
		add(findBtn);
		
		eventBtn=new JButton("Event");
		//event 새 창 띄우기
		eventBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new eventFrame();
            }
        });
		eventBtn.setBounds(40, 120, 100, 24);
		add(eventBtn);
		
		//findmate는 채팅방 구현 생각해보고 액션이벤트 추가하려구
		mateBtn=new JButton("Find mate");
		mateBtn.setBounds(40, 200, 100, 24);
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
		
		dessertBtn.addActionListener(new dstEvent());
		entBtn.addActionListener(new entEvent());
		univBtn.addActionListener(new univEvent());
		back.addActionListener(new backEvent());
	}
	class dstEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			frm.change("dessert");
		}
	}
	class entEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			frm.change("ent");
		}
	}
	class univEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			frm.change("univ");
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
		setSize(450,200);
		setLocation(0, 120);
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		close.setBounds(350, 150, 50, 24);
		ePanel.add(close);
		getContentPane().add(ePanel);
		setVisible(true);
	}
}
/**
 * 디저트 패널
 * @author 종건
 *
 */
class dstPanel extends JPanel{
	
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

		back.addActionListener(new backEvent());
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

		back.addActionListener(new backEvent());
	}
	class backEvent implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			frm.change("act");
		}
	}
}
/**학교 이벤트 패널
 * 근데 이걸 다른 패널처럼 버튼개수를 두고 만들지 이벤트목록으로
 * 만들지 정확히 기억이 안나넴...
 * @author 종건
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
		
		/*billBtn=new JButton("Billiard");
		billBtn.setBounds(40, 120, 100, 24);
		add(billBtn);
		
		pcBtn=new JButton("PC room");
		pcBtn.setBounds(40, 200, 100, 24);
		add(pcBtn);*/
		
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


public class UI {

		static JFrame frame = new JFrame("Gtime");	
		public mainPanel mp=null;
		public actPanel ap=null;
		public dstPanel dp=null; 
		public entPanel ep=null;
		public univPanel up=null;
		
		public UI() {
			// Layout GUI
			frame.setSize(700,400);
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
