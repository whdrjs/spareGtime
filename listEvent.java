package spareTime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * it help to back action repetitive action event
 */
public class listEvent implements ActionListener {
	String preCont;

	public listEvent(String cont) {
		preCont = cont;
	}

	public void actionPerformed(ActionEvent arg0) {
		new listFrame(preCont);
	}
}