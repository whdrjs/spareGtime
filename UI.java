package spareTime;
/**
 * This class covers the GUI design and events of the program.
 * Form information and chatting to users.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JList;

import spareTime.Client;



/*
 * main frame when user income to first
 * 
 * 
 */
public class UI extends JFrame{
	 Client client = new Client();  
   private JPanel mp=mainPanel();
   private JPanel ap=actPanel();
   private JPanel dp=dstPanel();
   private JPanel ep=entPanel();
   //initially frame
   public UI() {
	   
      setTitle("SSG");
      setSize(1000,664);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //when window exit button(x) is clicked, process is terminated. 
      getContentPane().add(mp);
   }
/*
 * this method can change panel to easy
 */
   public void change(String panelName)
   {
      if(panelName.equals("main")) {
         getContentPane().removeAll(); //remove previous panel
         getContentPane().add(mp);	   // add to new panel
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
   /*
    * this is init panel
    * when user come into program at first
    * can show this panel 
    */
   //main
   public JPanel mainPanel()
   {
      JPanel panel;
      panel =new JPanel() {
         Image bg= new ImageIcon("img/mainBG.jpg").getImage();
         public void paintComponent(Graphics g) {
            g.drawImage(bg,0,0,getWidth(),getHeight(),this);
         }
      };//panel set and image
      //create JButton
      JButton findBtn;
      JButton eventBtn;
      JButton mateBtn;
      panel.setLayout(null);
      //button layout and action
      findBtn=new JButton(new ImageIcon("img/findactivity.jpg"));
      findBtn.setBackground(Color.red);
      findBtn.setBounds(342, 330, 316, 74);
      findBtn.setBorderPainted(false);
      findBtn.setFocusPainted(false); 
      findBtn.setContentAreaFilled(false); 
      findBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            change("act");//send to change method , what it want to next step
         }});
      panel.add(findBtn);

      eventBtn=new JButton(new ImageIcon("img/promotion.jpg"));
      eventBtn.setBackground(Color.blue);
      eventBtn.setBorderPainted(false);
      eventBtn.setFocusPainted(false); 
      eventBtn.setContentAreaFilled(false); 
      eventBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            new eventFrame();//send to change method , what it want to next step
         }
      });
      eventBtn.setBounds(342, 430, 316, 74);
      panel.add(eventBtn);

      mateBtn=new JButton(new ImageIcon("img/findmate.jpg"));
      mateBtn.setBackground(Color.blue);
      mateBtn.setBorderPainted(false);
      mateBtn.setFocusPainted(false); 
      mateBtn.setContentAreaFilled(false); 
      mateBtn.setBounds(342, 530, 316, 74);
      mateBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            new mateFrame(null,"main");	//call and run mate matching frame (mateFrame) with from "main"
         }
      });
      panel.add(mateBtn);

      return panel;
   }
/*
 * if previous step user want activity from gachon univ and want information
 */
   public JPanel actPanel() {
	   //create button
      JPanel panel ;
      JButton dessertBtn;
      JButton entBtn;
      JButton univBtn;
      JButton back;

      panel =new JPanel() {
         Image bg= new ImageIcon("img/mainBG2.jpg").getImage();
         public void paintComponent(Graphics g) {
            g.drawImage(bg,0,0,getWidth(),getHeight(),this);
         }
      };
      panel.setLayout(null);
      //layout buttons
      dessertBtn=new JButton(new ImageIcon("img/dessert.jpg"));
      dessertBtn.setBackground(Color.red);
      dessertBtn.setBounds(342, 230, 316, 74);
      dessertBtn.setBorderPainted(false);
      dessertBtn.setFocusPainted(false); 
      panel.add(dessertBtn);

      entBtn=new JButton(new ImageIcon("img/entertainment.PNG"));
      entBtn.setBackground(Color.red);
      entBtn.setBounds(342, 330, 316, 72);
      entBtn.setBorderPainted(false);
      entBtn.setFocusPainted(false); 
      panel.add(entBtn);

      univBtn=new JButton(new ImageIcon("img/Gachonevent.PNG"));
      univBtn.setBackground(Color.red);
      univBtn.setBounds(342, 430, 316, 74);
      univBtn.setBorderPainted(false);
      univBtn.setFocusPainted(false); 
      panel.add(univBtn);

      back=new JButton(new ImageIcon("img/back1.PNG"));
      back.setBackground(Color.red);
      back.setBounds(373, 550, 254, 63);
      back.setBorderPainted(false);
      back.setFocusPainted(false); 
      panel.add(back);
//buttons action
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
    //univ button doesn't call change, it show new panel
      univBtn.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e) {
            panel.removeAll();
            revalidate();
            repaint();
            JTextArea uni=new JTextArea();
            String info = null;
            JButton back2;
               try {//Send server to information of event
                  info=new String(client.getStoreData("ACT", "Event", " ", "0"));
               } catch (IOException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
               }
               //split from server data
               String[] store=info.split("\\^");
               String[] infoSplit;
               for(int i=0;i<store.length;i++)
               {
                  System.out.println(store[i]);
                  infoSplit=store[i].split("\\_");
                  uni.append((String)infoSplit[1]+" "+(String)infoSplit[3]+"\n\n");
               }
               uni.setBounds(150, 150, 800, 600);
               uni.setOpaque(false);
               uni.setEditable(false);
               uni.setFont(new Font("배달의민족 주아",Font.PLAIN,30));
               panel.add(uni);

               back2=new JButton(new ImageIcon("img/캡처3.PNG"));
               back2.setBackground(Color.red);
               back2.setBounds(350, 500, 300, 74);
               back2.setBorderPainted(false);
               back2.setFocusPainted(false); 
               back2.addActionListener(new ActionListener(){//change to previous panel
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
/*
 * user want dessert (ice cream coffee and bread)
 * so clicked previous panel dessert button
 * we show this panel
 */
   public JPanel dstPanel(){
      //create panel
	  JPanel panel;
      //create button
	  JButton bakeBtn;
      JButton iceBtn;
      JButton coffBtn;
      JButton back;
      
      panel =new JPanel() {
         Image bg= new ImageIcon("img/mainBG2.jpg").getImage();
         public void paintComponent(Graphics g) {
            g.drawImage(bg,0,0,getWidth(),getHeight(),this);
         }
      };
      panel.setLayout(null);
//layout buttons and action all button's action is change to next step
      bakeBtn=new JButton(new ImageIcon("img/bakery.jpg"));
      bakeBtn.setBackground(Color.red);
      bakeBtn.setBounds(342, 230, 316, 74);
      bakeBtn.setBorderPainted(false);
      bakeBtn.setFocusPainted(false); 
      panel.add(bakeBtn);

      iceBtn=new JButton(new ImageIcon("img/icecream.jpg"));
      iceBtn.setBackground(Color.red);
      iceBtn.setBounds(342, 330, 316, 72);
      iceBtn.setBorderPainted(false);
      iceBtn.setFocusPainted(false); 
      panel.add(iceBtn);

      coffBtn=new JButton(new ImageIcon("img/coffee.jpg"));
      coffBtn.setBackground(Color.red);
      coffBtn.setBounds(342, 430, 316, 74);
      coffBtn.setBorderPainted(false);
      coffBtn.setFocusPainted(false); 
      panel.add(coffBtn);

      back=new JButton(new ImageIcon("img/back1.PNG"));
      back.setBackground(Color.red);
      back.setBounds(373, 550, 254, 63);
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
   // this panel same above panel but difference its entertainment activity
   public JPanel entPanel(){
      JPanel panel;
      JButton singBtn;
      JButton billBtn;
      JButton pcBtn;
      JButton back;
      panel =new JPanel() {
         Image bg= new ImageIcon("img/mainBG2.jpg").getImage();

         public void paintComponent(Graphics g) {
            g.drawImage(bg,0,0,getWidth(),getHeight(),this);
         }
      };

      panel.setLayout(null);

      singBtn=new JButton(new ImageIcon("img/singing.jpg"));
      singBtn.setBackground(Color.red);
      singBtn.setBounds(342, 230, 315, 73);
      singBtn.setBorderPainted(false);
      singBtn.setFocusPainted(false); 
      panel.add(singBtn);

      billBtn=new JButton(new ImageIcon("img/billiard.jpg"));
      billBtn.setBackground(Color.red);
      billBtn.setBounds(342, 330, 316, 72);
      billBtn.setBorderPainted(false);
      billBtn.setFocusPainted(false); 
      panel.add(billBtn);

      pcBtn=new JButton(new ImageIcon("img/pcroom.jpg"));
      pcBtn.setBackground(Color.red);
      pcBtn.setBounds(342, 430, 316, 74);
      pcBtn.setBorderPainted(false);
      pcBtn.setFocusPainted(false); 
      panel.add(pcBtn);

      back=new JButton(new ImageIcon("img/back1.PNG"));
      back.setBackground(Color.red);
      back.setBounds(373, 550, 254, 63);
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


}
