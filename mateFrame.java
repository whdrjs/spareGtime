package spareTime;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * when user want friends, this frame help to match friend. check to condition
 * user can access it two way.
 * first is looking at the store information.
 * second is directly first income frame.
 * we divide two ways.
 */
class mateFrame extends JFrame {
	// create swings
	Client client = new Client();
	private JPanel panel;
	private JLabel label = new JLabel();
	private JComboBox<String> toMain = new JComboBox<String>();
	private JComboBox<String> time = new JComboBox<String>();
	private JButton check;
	String category = new String("Bakery"); // create and init
	String whatTime = new String("09:00 ~ 10:00");// create and init

	public mateFrame(String cont, String name)// Where did the users come from
	{
		// layout of panel
		panel = new JPanel() {
			Image bg = new ImageIcon("img/submain.png").getImage();

			public void paintComponent(Graphics g) {
				g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
			}
		};
		panel.setLayout(null);
		label.setText(cont);
		label.setBounds(250, 30, 100, 40);
		// create combobox item. beforehand user directly come to main screen.
		toMain.addItem("Bakery");
		toMain.addItem("Coffee");
		toMain.addItem("Ice");
		toMain.addItem("Pcroom");
		toMain.addItem("Billiard");
		toMain.addItem("Sing");
		toMain.setBounds(210, 30, 180, 30);
		toMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				category = new String((String) toMain.getSelectedItem());// click=> change
			}
		});

		if (name.equals("main"))// if user come to main then add to above items including combobox
			panel.add(toMain);
		else // user come to look at the store information.
		{
			panel.add(label);
			category = cont;
		}
		// set frame init
		setTitle(name);
		setSize(600, 400);
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
		time.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// before send
				whatTime = new String((String) time.getSelectedItem());
			}
		});
		panel.add(time); // add box to panel
		// init combo box
		check = new JButton(new ImageIcon("img/select3.png"));
		check.setBounds(219, 300, 162, 41);
		check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { // room condition
				// split time sentence only number
				String[] strs = whatTime.split(":");// split
				System.out.println(strs[0]);
				try {
					client.roomInforming(category, strs[0]); // In client
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		panel.add(check);

		getContentPane().add(panel);
		// setDefaultCloseOperation(pre.);
		setVisible(true);
	}
}