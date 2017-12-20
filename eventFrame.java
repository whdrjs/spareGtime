package spareTime;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/*
 * It informs you of the events that are under way at our university
 * Read event information from database on server
 */
class eventFrame extends JFrame {
	Client client = new Client();
	JPanel contentPane = new JPanel() {
		Image bg = new ImageIcon("img/mainBG.jpg").getImage();

		public void paintComponent(Graphics g) {
			g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
		}
	}; // create panel
	private JButton close = new JButton("close"); // close to this frame
	private JTextArea ad = new JTextArea(); // show advertise information

	String info; // stored event information

	public eventFrame() {
		setTitle("Event!");// title
		setSize(700, 500); // layout
		setLocation(0, 80);// set start location
		try {
			info = new String(client.getStoreData("ACT", "Advertise", " ", "0")); // Send server to inform of
																					// advertising information, and
																					// receive
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] store = info.split("\\^");// split one sentence information to each advertise
		String[] infoSplit; // each advertises are splited to more partial inform
		ad.append("\n\n");
		for (int i = 0; i < store.length; i++) {
			infoSplit = store[i].split("\\_"); // store (above notes)
			ad.append((String) infoSplit[1] + "\n\n"); // show for text field
		}
		ad.setBounds(150, 200, 400, 300);// layout
		ad.setOpaque(false); // transparent
		ad.setEditable(false); // NOT EDIT
		ad.setFont(new Font("바탕",Font.BOLD ,30));
		contentPane.add(ad); // add textfield to panel
		getContentPane().add(contentPane); // add panel to frame
		setVisible(true);
	}
}
