package Recursos;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;

public class ExibirProduto extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblCodigo;
	private JLabel lblNomeProduto;
	private JLabel lblPrecoU;
	private JLabel lblBackground;
	
	public ExibirProduto() {
		setBackground(new Color(255, 255, 255));
		setLayout(null);
		setSize(407,244);
		addItens();
	}

	private void addItens() {
		add(getLblCodigo());
		add(getLblNomeProduto());
		add(getLblPrecoU());
		add(getLblBackground());
	}

	public JLabel getLblCodigo() {
		if(lblCodigo==null) {
			lblCodigo = new JLabel();
			lblCodigo.setFont(new Font("Dialog", Font.BOLD, 20));
			lblCodigo.setBounds(10, 11, 87, 50);
		}
		return lblCodigo;
	}

	public JLabel getLblNomeProduto() {
		if(lblNomeProduto==null) {
			lblNomeProduto = new JLabel();
			lblNomeProduto.setFont(new Font("Dialog", Font.BOLD, 20));
			lblNomeProduto.setBounds(120, 11, 277, 50);
		}
		return lblNomeProduto;
	}

	public JLabel getLblPrecoU() {
		if(lblPrecoU==null) {
			lblPrecoU = new JLabel();
			lblPrecoU.setFont(new Font("Dialog", Font.BOLD, 60));
			lblPrecoU.setHorizontalAlignment(SwingConstants.CENTER);
			lblPrecoU.setBounds(55, 77, 295, 144);
		}
		return lblPrecoU;
	}
	
	public JLabel getLblBackground() {
		if(lblBackground==null) {
			lblBackground = new JLabel();
			lblBackground.setIcon(new ImageIcon(ExibirProduto.class.getResource("/Imagens/Design sem nome (5).png")));
			lblBackground.setBounds(0, 0, 407, 244);
		}
		return lblBackground;
	}
}
