package Visao;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;

public class PanelBalancoFinanceiro extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel lblBackground;
	private JButton btFazerBalanco;
	private JLabel lblDataHoraBalanco;
	private JLabel lblValorMovimentacaoMensal;
	private JLabel lblValorLucroMensal;
	private JLabel lblValorDividaMensal;

	public PanelBalancoFinanceiro(){
		this.setBounds(10, 86, 1060, 683);
		setLayout(null);
		addItens();
		this.setVisible(true);
	}

	private void addItens() {
		add(getLblValorDividaMensal());
		add(getLblValorLucroMensal());
		add(getLblValorMovimentacaoMensal());
		add(getLblDataHoraBalanco());
		add(getBtFazerBalanco());
		add(getLblBackground());
	}
	
	public JLabel getLblBackground() {
		if (lblBackground == null) {
			lblBackground = new JLabel("");
			lblBackground.setIcon(new ImageIcon(PanelGerenciamentoEstoque.class.getResource("/Imagens/balancoFinanceiro.png")));
			lblBackground.setBounds(0, 0, 1060, 683);
		}
		return lblBackground;
	}

	public JButton getBtFazerBalanco() {
		if (btFazerBalanco == null) {
			btFazerBalanco = new JButton("");
			btFazerBalanco.setIcon(new ImageIcon(PanelBalancoFinanceiro.class.getResource("/Imagens/Iniciar Balanço.png")));
			btFazerBalanco.setBounds(861, 18, 150, 40);
			btFazerBalanco.setBorder(null);
			btFazerBalanco.setContentAreaFilled(false);
			btFazerBalanco.setFocusPainted(false);
		}
		return btFazerBalanco;
	}
	public JLabel getLblDataHoraBalanco() {
		if (lblDataHoraBalanco == null) {
			lblDataHoraBalanco = new JLabel("");
			lblDataHoraBalanco.setVisible(false);
			lblDataHoraBalanco.setFont(new Font("DialogInput", Font.BOLD, 25));
			lblDataHoraBalanco.setHorizontalAlignment(SwingConstants.LEFT);
			lblDataHoraBalanco.setBounds(67, 147, 659, 53);
		}
		return lblDataHoraBalanco;
	}
	public JLabel getLblValorMovimentacaoMensal() {
		if (lblValorMovimentacaoMensal == null) {
			lblValorMovimentacaoMensal = new JLabel("");
			lblValorMovimentacaoMensal.setVisible(false);
			lblValorMovimentacaoMensal.setHorizontalAlignment(SwingConstants.LEFT);
			lblValorMovimentacaoMensal.setFont(new Font("DialogInput", Font.BOLD, 25));
			lblValorMovimentacaoMensal.setBounds(67, 263, 659, 89);
		}
		return lblValorMovimentacaoMensal;
	}
	public JLabel getLblValorLucroMensal() {
		if (lblValorLucroMensal == null) {
			lblValorLucroMensal = new JLabel("");
			lblValorLucroMensal.setVisible(false);
			lblValorLucroMensal.setHorizontalAlignment(SwingConstants.LEFT);
			lblValorLucroMensal.setFont(new Font("DialogInput", Font.BOLD, 25));
			lblValorLucroMensal.setBounds(67, 399, 659, 89);
		}
		return lblValorLucroMensal;
	}
	public JLabel getLblValorDividaMensal() {
		if (lblValorDividaMensal == null) {
			lblValorDividaMensal = new JLabel("");
			lblValorDividaMensal.setVisible(false);
			lblValorDividaMensal.setHorizontalAlignment(SwingConstants.LEFT);
			lblValorDividaMensal.setFont(new Font("DialogInput", Font.BOLD, 25));
			lblValorDividaMensal.setBounds(67, 534, 659, 89);
		}
		return lblValorDividaMensal;
	}
}
