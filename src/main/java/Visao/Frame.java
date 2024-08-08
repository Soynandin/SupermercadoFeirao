package Visao;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static JButton btFecharJanela;
	
	public Frame() {
		super();
		this.setSize(1080, 780);
		this.setUndecorated(true);
		addFerramentas();
	}

	private void addFerramentas() {
		getContentPane().setLayout(null);
		add(getBtFecharJanela());
	}

	public static JButton getBtFecharJanela() {
		if(btFecharJanela==null) {
			btFecharJanela = new JButton();
			btFecharJanela.setBounds(1024, 11, 46, 42);
			btFecharJanela.setBorder(null);
			btFecharJanela.setContentAreaFilled(false);
			btFecharJanela.setFocusPainted(false);
		}
		return btFecharJanela;
	}
	
	
	

}
