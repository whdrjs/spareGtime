package spareTime;
/**
 * This class covers the GUI design and events of the program.
 * Form information and chatting to users.
 */
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
import javax.swing.JList;

import spareTime.Client;
/*
 * It informs you of the events that are under way at our university
 * Read event information from database on server
 */
class eventFrame extends JFrame {
   JPanel contentPane=new JPanel() {
      Image bg= new ImageIcon("img/mainBG.jpg").getImage();
      public void paintComponent(Graphics g) {
         g.drawImage(bg,0,0,getWidth(),getHeight(),this);
      }
   };	//create panel  
   private JButton close=new JButton("close");	//close to this frame
   private JTextArea ad=new JTextArea(); //show advertise information

   String info; //stored event information
   public eventFrame() {
      setTitle("Event!");//title
      setSize(700,500);	//layout
      setLocation(0, 80);//set start location
      try {
         info=new String(Client.getStoreData("ACT", "Advertise", " ", "0"));         //Send server to inform of advertising information, and receive 
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      String[] store=info.split("\\^");//split one sentence information to each advertise
      String[] infoSplit;	//each advertises are splited to more partial inform
      for(int i=0;i<store.length;i++)
      {
         infoSplit=store[i].split("\\_");	//store (above notes)
         ad.append((String)infoSplit[1]+"\n\n");	//show for text field
      }
      ad.setBounds(150, 200, 400, 300);//layout
      ad.setOpaque(false);		//transparent
      ad.setEditable(false);	//NOT EDIT
      contentPane.add(ad);		//add textfield to panel 
      getContentPane().add(contentPane);	//add panel to frame 
      setVisible(true);
   }
}
/*
 * This frame shows the list of stores according to the conditions required by the user.
 */
class listFrame extends JFrame{
//Form list and elements use swing
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

   public String type1="ACT"; //act or event
   public String type2;//bakery, coffee,ice_cream
   public String type3=" ";// priority for distance or star
   public String rawStr;

   public ArrayList<String> storeList=new ArrayList<String>();
   
   //Store the name and address of the store.
   public ArrayList<String> names=new ArrayList<String>();
   public ArrayList<String> star=new ArrayList<String>();
   public ArrayList<String> address=new ArrayList<String>();

   public String select;
   public String imgName;
   
   /*
    * This show sorted store list to distance 
    * first input to user for Three randomly divided points 
    * next server compute least distance top 5 store
    */
   private void distance(){
      JFrame pop=new JFrame();
      //radio button for user location
      JRadioButton visionRaBtn = new JRadioButton("비전타워");
      JRadioButton policeRaBtn = new JRadioButton("복정파출소");
      JRadioButton otherRaBtn = new JRadioButton("동서울대");
      //this fram init
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
      //Group the button
      ButtonGroup bg=new ButtonGroup();
      bg.add(visionRaBtn);
      bg.add(policeRaBtn);
      bg.add(otherRaBtn);
      //Radio button layout setting
      visionRaBtn.setBounds(40, 40, 100,50);
      visionRaBtn.setContentAreaFilled(false); 
      visionRaBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { //if click button, Give the corresponding information
            pop.setVisible(false);
            try {
               rawStr=new String(Client.getStoreData(type1, type2 , type3 ,"1"));        //Send server to inform of choose user information, and receive 
               makeList(rawStr);//makeList split one sentence string and make list that user's want
            } catch (IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
         }
      });
      //button layout
      policeRaBtn.setBounds(150, 40, 100,50);
      policeRaBtn.setContentAreaFilled(false); 
      //button action
      policeRaBtn.addActionListener(new ActionListener() { 
         public void actionPerformed(ActionEvent e) {//if click button, Give the corresponding information
            pop.setVisible(false);
            try {
               rawStr=new String(Client.getStoreData(type1, type2 , type3 ,"2"));//Send server to inform of choose user information, and receive 
               makeList(rawStr);
            } catch (IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
         }
      });
      //button layout
      otherRaBtn.setBounds(280, 40, 100,50);
      otherRaBtn.setContentAreaFilled(false); 
      //button action
      otherRaBtn.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            pop.setVisible(false);
            try {
               rawStr=new String(Client.getStoreData(type1, type2 , type3 ,"3"));//Send server to inform of choose user information, and receive 
               makeList(rawStr);
            } catch (IOException e1) {
               // TODO Auto-generated catch block
               e1.printStackTrace();
            }
         }
      });
      //add bottons to panel
      pan.add(visionRaBtn);
      pan.add(policeRaBtn);
      pan.add(otherRaBtn);
      pan.setVisible(true);
      //add pnale to frame
      pop.getContentPane().add(pan,BorderLayout.CENTER);
      pop.setVisible(true);
   }
   //set this frame initial screen
   public listFrame(String pre) { 
      setTitle(pre);
      type2=pre;	//user set information is title
      //layout
      setSize(540,720);
      setLocation(530, 50);
      mainList =new JPanel() {
         Image bg= new ImageIcon("img/submain.png").getImage();
         public void paintComponent(Graphics g) {
            g.drawImage(bg,0,0,getWidth(),getHeight(),this);
         }};//set panel image
         try {
            rawStr=new String(Client.getStoreData(type1, type2 , type3 ,"0"));//Send server to inform of choose user information, and receive 
         } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
         }
         mainList.setLayout(null);
         mainList.add(list);
         makeList(rawStr);//makeList split one sentence string and make list that user's want
         //if user want sorted information
         combo.addItem("Distance");
         combo.addItem("Rate");
         combo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
               if(((String)combo.getSelectedItem()).compareTo("Distance")==0)
               {
                  type3="distance"; //check distance to user
                  distance();
               }
               if(((String)combo.getSelectedItem()).compareTo("Rate")==0)
               {
                  type3="star"; //check rate to user
                  try {
                     rawStr=new String(Client.getStoreData(type1, type2 , type3 ,"0"));//Send server to inform of choose user information, and receive 
                     makeList(rawStr);
                  } catch (IOException e1) {
                     // TODO Auto-generated catch block
                     e1.printStackTrace();
                  }
               }
            }
         });
         //layout of combobox(select sorted inform)
         combo.setEditable(false);
         combo.setBounds(150, 30, 240, 40);
         mainList.add(combo);	//add to panel

         check=new JButton(new ImageIcon("img/select2.PNG"));
         check.setBounds(135, 580, 270, 65);
         check.addActionListener(new ActionListener(){//if click, the show information that user's want
            public void actionPerformed(ActionEvent arg0) {
               System.out.println(imgName);
               getContentPane().removeAll();
               //!this is information that user's want, it in action
               //layout panel
               infoPanel =new JPanel() {
                  Image bg= new ImageIcon("img/submain.png").getImage(); 
                  public void paintComponent(Graphics g) {
                     g.drawImage(bg,0,0,getWidth(),getHeight(),this);
                  }
               };
               infoPanel.setLayout(null);
               
               //layout of map image
               img=new JButton(new ImageIcon(imgName));
               img.setBackground(Color.blue);
               img.setBorderPainted(false);
               img.setFocusPainted(false); 
               img.setContentAreaFilled(false); 
               img.setBounds(10, 10, 570, 435);
               //show store information use textarea
               int idx= names.indexOf(select);
               info.append(names.get(idx)+"\n");
               info.append(address.get(idx));
               info.setBounds(10, 455, 500, 100);
               info.setOpaque(false);
               info.setEditable(false);
               //start matching, before user input that wants time
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
               //panel layout
               infoPanel.add(img);
               infoPanel.add(info);
               infoPanel.add(mateBtn);
               //back to before step in mainlist
               back=new JButton(new ImageIcon("img/캡처4.PNG"));
               back.setBackground(Color.red);
               back.setBounds(640, 550, 225, 56);
               back.setBorderPainted(false);
               back.setFocusPainted(false); 
               back.addActionListener(new ActionListener(){
                  public void actionPerformed(ActionEvent arg0) {
                     //reset had information
                     info.selectAll();
                     info.replaceSelection("");
                     getContentPane().removeAll();	//remove all to panel
                     getContentPane().add(mainList);//add new list
                     //Relocation
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
               //!end action to create new panel
            }
         });
         mainList.add(check);
         getContentPane().add(mainList);
         setVisible(true);//initially come  for using swing
   }
   /*
    * it is important dealing with the information that received to server
    * it split to lapped many information from one sentence
    * Next splited information map suit variable
    */
   private void makeList (String str) {
	   //split lapped whole information
      String[] store=str.split("\\^");
      String[] infoSplit;
      //Initialization arraylist
      storeList.clear();
      names.clear();
      star.clear();
      address.clear();
      //Initialization Jlist
      mainList.remove(list);
      mainList.revalidate();
      mainList.repaint();
      //split and add to array list variable
      for(int i=0;i<store.length;i++)
      {
         System.out.println(store[i]);
         infoSplit=store[i].split("\\_");
         names.add(infoSplit[1]);	//store name
         star.add(infoSplit[2]);	//store rate
         address.add(infoSplit[3]);	//store address
      }
      //add to list above array list
      list=new JList(names.toArray());
      list.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {               
            select=list.getSelectedValue();// select user
            imgName=new String("img/"+select+".png");//set map site to string
            System.out.println(select);
         }
      }); 
      //layout of list
      list.setBounds(70, 105, 400, 450);
      list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      mainList.add(list);
   }
}
/*
 * when user want friends, this frame help to match friend. check to condition
 * user can access it two way.
 * first is looking at the store information.
 * second is directly first income frame.
 * we divide two ways.
 */
class mateFrame extends JFrame{
	//create swings
   private JPanel panel ;
   private JLabel label = new JLabel();
   private JComboBox<String> toMain= new JComboBox<String>(); 
   private JComboBox<String> time= new JComboBox<String>(); 
   private JButton check;
   String category=new String("Bakery"); //create and init
   String whatTime=new String("09:00 ~ 10:00");//create and init
   public mateFrame(String cont,String name)//Where did the users come from
   {
	   //layout of panel
      panel =new JPanel() {
         Image bg= new ImageIcon("img/submain.png").getImage();

         public void paintComponent(Graphics g) {
            g.drawImage(bg,0,0,getWidth(),getHeight(),this);
         }
      };
      panel.setLayout(null);
      label.setText(cont);
      label.setBounds(250,30,100,40);
      // create combobox item. beforehand user directly come to main screen.
      toMain.addItem("Bakery");
      toMain.addItem("Coffee");
      toMain.addItem("Ice");
      toMain.addItem("Pcroom");
      toMain.addItem("Billiard");
      toMain.addItem("Sing");
      toMain.setBounds(210,30,180,30);
      toMain.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent arg0) {
            category=new String((String)toMain.getSelectedItem());//click=> change
         }});

      if(name.equals("main"))//if user come to main then add to above items including combobox
         panel.add(toMain);
      else	//user come to look at the store information.
      {
         panel.add(label);
         category=cont;
      }
      //set frame init
      setTitle(name);
      setSize(600,400);
      setLocation(0, 0);
      // add to time combo box. user can set time that matching friend
      time.addItem("09:00 ~ 10:00");
      time.addItem("10:00 ~ 11:00");
      time.addItem("11:00 ~ 12:00");
      time.addItem("12:00 ~ 01:00");
      time.addItem("01:00 ~ 02:00");
      time.addItem("02:00 ~ 03:00");
      time.addItem("03:00 ~ 04:00");
      time.addItem("04:00 ~ 05:00");
      time.setBounds(180, 120, 240, 30);   
      time.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent arg0) {
            //before send
            whatTime=new String((String)time.getSelectedItem());
         }});
      panel.add(time);	//add box to panel
      //init combo box
      check=new JButton(new ImageIcon("img/select3.png"));
      check.setBounds(219, 300, 162, 41);
      check.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent arg0) { //room condition
            //split time sentence only number
            String []strs=whatTime.split(":");//split
            System.out.println(strs[0]);
            try {
               Client.roomInforming(category,strs[0]);  //In client
            } catch (Exception e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      });
      panel.add(check);      

      getContentPane().add(panel);
      //setDefaultCloseOperation(pre.);
      setVisible(true);
   }
}
/*
 * main frame when user income to first
 * 
 * 
 */
public class UI extends JFrame{

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
                  info=new String(Client.getStoreData("ACT", "Event", " ", "0"));
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
               uni.setBounds(500, 150, 600, 450);
               uni.setOpaque(false);
               uni.setEditable(false);
               panel.add(uni);

               back2=new JButton(new ImageIcon("img/캡처3.PNG"));
               back2.setBackground(Color.red);
               back2.setBounds(650, 730, 300, 74);
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
/*
 * it help to back action repetitive action event
 */
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
