package Visao;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Aplicacao.App;

public class TelaInicial extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblBackground;
	private JLabel lblLogo;
	private JLabel lblMensagemPress;
	private JButton btInicializar;
	
	public TelaInicial() {
		App.controleTela=0;
		this.setLayout(null);
		this.setBounds(0, 0, 1080, 780);
		addItens();
		this.setVisible(true);
	}

	private void addItens() {
		add(getBtInicializar());
		add(getLblLogo());
		add(getLlblMensagemPress());
		add(getLblBackground());
	}

	public JLabel getLblBackground() {
		if(lblBackground==null) {
			lblBackground = new JLabel();
			lblBackground.setBounds(0, 0, 1080, 780);
			lblBackground.setIcon(new ImageIcon(TelaInicial.class.getResource("/Imagens/Background.png")));
		}
		return lblBackground;
	}
	
	public JLabel getLblLogo() {
		if (lblLogo == null) {
			lblLogo = new JLabel();
			lblLogo.setBounds(658, 210, 250, 250);
			lblLogo.setIcon(new ImageIcon(TelaInicial.class.getResource("/Imagens/jh (2).png")));
		}
		return lblLogo;
	}
	
	public JLabel getLlblMensagemPress() {
		if(lblMensagemPress==null) {
			lblMensagemPress = new JLabel();
			lblMensagemPress.setBounds(581, 469, 400, 50);
			lblMensagemPress.setIcon(new ImageIcon(TelaInicial.class.getResource("/Imagens/press (1).gif")));
		}
		return lblMensagemPress;
	}


	public JButton getBtInicializar() {
		if(btInicializar==null) {
			btInicializar = new JButton();
			btInicializar.setBounds(399, 106, 671, 626);
			btInicializar.setBorder(null);
			btInicializar.setContentAreaFilled(false);
			btInicializar.setFocusPainted(false);
		}
		return btInicializar;
	}
}
