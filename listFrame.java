package spareTime;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
/*
 * This frame shows the list of stores according to the conditions required by the user.
 */

class listFrame extends JFrame {
	Client client = new Client();
	// Form list and elements use swing
	private JPanel mainList;
	private JPanel infoPanel;
	private JComboBox<String> combo = new JComboBox<String>();
	private JTextArea info = new JTextArea();
	private JButton check;
	private JButton mateBtn;
	private JButton back;
	private JButton img;
	private JList<String> list = new JList<String>();
	private JScrollPane scroll = new JScrollPane();

	public String type1 = "ACT"; // act or event
	public String type2;// bakery, coffee,ice_cream
	public String type3 = " ";// priority for distance or star
	public String rawStr;

	public ArrayList<String> storeList = new ArrayList<String>();

	// Store the name and address of the store.
	public ArrayList<String> names = new ArrayList<String>();
	public ArrayList<String> star = new ArrayList<String>();
	public ArrayList<String> address = new ArrayList<String>();

	public String select;
	public String imgName;

	/*
	 * This show sorted store list to distance first input to user for Three
	 * randomly divided points next server compute least distance top 5 store
	 */
	private void distance() {
		JFrame pop = new JFrame();
		// radio button for user location
		JRadioButton visionRaBtn = new JRadioButton("비전타워");
		JRadioButton policeRaBtn = new JRadioButton("복정파출소");
		JRadioButton otherRaBtn = new JRadioButton("동서울대");
		// this fram init
		pop.setTitle("위치");
		pop.setSize(420, 200);
		pop.setLocation(470, 40);
		JPanel pan = new JPanel() {
			Image bg = new ImageIcon("img/submain.png").getImage();

			public void paintComponent(Graphics g) {
				g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		pan.setLayout(null);
		// Group the button
		ButtonGroup bg = new ButtonGroup();
		bg.add(visionRaBtn);
		bg.add(policeRaBtn);
		bg.add(otherRaBtn);
		// Radio button layout setting
		visionRaBtn.setBounds(40, 40, 100, 50);
		visionRaBtn.setContentAreaFilled(false);
		visionRaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { // if click button, Give the corresponding information
				pop.setVisible(false);
				try {
					rawStr = new String(client.getStoreData(type1, type2, type3, "1")); // Send server to inform of
																						// choose user information, and
																						// receive
					makeList(rawStr);// makeList split one sentence string and make list that user's want
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		// button layout
		policeRaBtn.setBounds(150, 40, 100, 50);
		policeRaBtn.setContentAreaFilled(false);
		// button action
		policeRaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {// if click button, Give the corresponding information
				pop.setVisible(false);
				try {
					rawStr = new String(client.getStoreData(type1, type2, type3, "2"));// Send server to inform of
																						// choose user information, and
																						// receive
					makeList(rawStr);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		// button layout
		otherRaBtn.setBounds(280, 40, 100, 50);
		otherRaBtn.setContentAreaFilled(false);
		// button action
		otherRaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pop.setVisible(false);
				try {
					rawStr = new String(client.getStoreData(type1, type2, type3, "3"));// Send server to inform of
																						// choose user information, and
																						// receive
					makeList(rawStr);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		// add bottons to panel
		pan.add(visionRaBtn);
		pan.add(policeRaBtn);
		pan.add(otherRaBtn);
		pan.setVisible(true);
		// add pnale to frame
		pop.getContentPane().add(pan, BorderLayout.CENTER);
		pop.setVisible(true);
	}

	// set this frame initial screen
	public listFrame(String pre) {
		setTitle(pre);
		type2 = pre; // user set information is title
		// layout
		setSize(540, 720);
		setLocation(530, 50);
		mainList = new JPanel() {
			Image bg = new ImageIcon("img/submain.png").getImage();

			public void paintComponent(Graphics g) {
				g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
			}
		};// set panel image
		try {
			rawStr = new String(client.getStoreData(type1, type2, type3, "0"));// Send server to inform of choose user
																				// information, and receive
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mainList.setLayout(null);
		mainList.add(list);
		makeList(rawStr);// makeList split one sentence string and make list that user's want
		// if user want sorted information
		combo.addItem("Distance");
		combo.addItem("Rate");
		combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((String) combo.getSelectedItem()).compareTo("Distance") == 0) {
					type3 = "distance"; // check distance to user
					distance();
				}
				if (((String) combo.getSelectedItem()).compareTo("Rate") == 0) {
					type3 = "star"; // check rate to user
					try {
						rawStr = new String(client.getStoreData(type1, type2, type3, "0"));// Send server to inform of
																							// choose user information,
																							// and receive
						makeList(rawStr);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		// layout of combobox(select sorted inform)
		combo.setEditable(false);
		combo.setBounds(150, 30, 240, 40);
		mainList.add(combo); // add to panel

		check = new JButton(new ImageIcon("img/select2.PNG"));
		check.setBounds(135, 580, 270, 65);
		check.addActionListener(new ActionListener() {// if click, the show information that user's want
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(imgName);
				getContentPane().removeAll();
				// !this is information that user's want, it in action
				// layout panel
				infoPanel = new JPanel() {
					Image bg = new ImageIcon("img/submain.png").getImage();

					public void paintComponent(Graphics g) {
						g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
					}
				};
				infoPanel.setLayout(null);

				// layout of map image
				img = new JButton(new ImageIcon(imgName));
				img.setBackground(Color.blue);
				img.setBorderPainted(false);
				img.setFocusPainted(false);
				img.setContentAreaFilled(false);
				img.setBounds(10, 10, 570, 435);
				// show store information use textarea
				int idx = names.indexOf(select);
				info.append(names.get(idx) + "\n");
				info.append(address.get(idx));
				info.setBounds(10, 455, 500, 100);
				info.setOpaque(false);
				info.setEditable(false);
				// start matching, before user input that wants time
				mateBtn = new JButton(new ImageIcon("img/findmate3.jpg"));
				mateBtn.setBackground(Color.blue);
				mateBtn.setBorderPainted(false);
				mateBtn.setFocusPainted(false);
				mateBtn.setContentAreaFilled(false);
				mateBtn.setBounds(610, 60, 295, 71);
				mateBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						new mateFrame(type2, select);
					}
				});
				// panel layout
				infoPanel.add(img);
				infoPanel.add(info);
				infoPanel.add(mateBtn);
				// back to before step in mainlist
				back = new JButton(new ImageIcon("img/캡처4.PNG"));
				back.setBackground(Color.red);
				back.setBounds(640, 550, 225, 56);
				back.setBorderPainted(false);
				back.setFocusPainted(false);
				back.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						// reset had information
						info.selectAll();
						info.replaceSelection("");
						getContentPane().removeAll(); // remove all to panel
						getContentPane().add(mainList);// add new list
						// Relocation
						revalidate();
						repaint();
					}
				});
				infoPanel.add(back);
				getContentPane().add(infoPanel);
				revalidate();
				repaint();
				setSize(960, 720);
				setLocation(370, 50);
				// !end action to create new panel
			}
		});
		mainList.add(check);
		getContentPane().add(mainList);
		setVisible(true);// initially come for using swing
	}

	/*
	 * it is important dealing with the information that received to server it split
	 * to lapped many information from one sentence Next splited information map
	 * suit variable
	 */
	private void makeList(String str) {
		// split lapped whole information
		String[] store = str.split("\\^");
		String[] infoSplit;
		// Initialization arraylist
		storeList.clear();
		names.clear();
		star.clear();
		address.clear();
		// Initialization Jlist
		mainList.remove(list);
		mainList.revalidate();
		mainList.repaint();
		// split and add to array list variable
		for (int i = 0; i < store.length; i++) {
			System.out.println(store[i]);
			infoSplit = store[i].split("\\_");
			names.add(infoSplit[1]); // store name
			star.add(infoSplit[2]); // store rate
			address.add(infoSplit[3]); // store address
		}
		// add to list above array list
		list = new JList(names.toArray());
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				select = list.getSelectedValue();// select user
				imgName = new String("img/" + select + ".png");// set map site to string
				System.out.println(select);
			}
		});
		// layout of list
		list.setBounds(70, 105, 400, 450);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mainList.add(list);
	}
}