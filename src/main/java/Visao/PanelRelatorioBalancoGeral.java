package Visao;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class PanelRelatorioBalancoGeral extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblBackground;
	private JButton btSangria;
	private JLabel lblAlerta;
	private JLabel lblDataBalancoDiario;
	private JLabel lblNomeUsuarioCaixa;
	private JLabel lblDataHoraCaixaFechado;
	private JLabel lblDataHoraCaixaAberto;
	private JLabel lblValorDinheiro;
	private JLabel lblValorCredito;
	private JLabel lblValorDebito;
	private JLabel lblValorFiado;
	private JLabel lblValorAbertura;
	private JLabel lblValorTotal;
	private JLabel lblValorDivida;
	
	public PanelRelatorioBalancoGeral() {
		this.setBounds(10, 86, 1060, 683);
		setLayout(null);
		addItens();
		this.setVisible(true);
	}

	private void addItens() {
		add(getLblValorDivida());
		add(getLblValorTotal());
		add(getLblValorAbertura());
		add(getLblValorFiado());
		add(getLblValorDebito());
		add(getLblValorCredito());
		add(getLblValorDinheiro());
		add(getLblDataHoraCaixaAberto());
		add(getLblDataHoraCaixaFechado());
		add(getLblNomeUsuarioCaixa());
		add(getLblDataBalancoDiario());
		add(getLblAlerta());
		add(getBtSangria());
		
		add(getLblBackground());
	}
	
	public JLabel getLblBackground() {
		if (lblBackground == null) {
			lblBackground = new JLabel();
			lblBackground.setIcon(new ImageIcon(PanelRelatorioBalancoGeral.class.getResource("/Imagens/Balanço geral do caixa.png"))); //
			lblBackground.setBounds(0, 0, 1060, 683);
		}
		return lblBackground;
	}
	public JButton getBtSangria() {
		if (btSangria == null) {
			btSangria = new JButton("");
			btSangria.setIcon(new ImageIcon(PanelRelatorioBalancoGeral.class.getResource("/Imagens/btBalanco.png")));
			btSangria.setBounds(418, 288, 200, 70);
			btSangria.setBorder(null);
			btSangria.setContentAreaFilled(false);
			btSangria.setFocusPainted(false);
		}
		return btSangria;
	}
	public JLabel getLblAlerta() {
		if (lblAlerta == null) {
			lblAlerta = new JLabel("");
			lblAlerta.setIcon(new ImageIcon(PanelRelatorioBalancoGeral.class.getResource("/Imagens/alertaBalancoCaixaAberto.png")));
			lblAlerta.setBounds(318, 395, 400, 40);
			lblAlerta.setVisible(false);
		}
		return lblAlerta;
	}
	public JLabel getLblDataBalancoDiario() {
		if (lblDataBalancoDiario == null) {
			lblDataBalancoDiario = new JLabel("");
			lblDataBalancoDiario.setVisible(false);
			lblDataBalancoDiario.setFont(new Font("Dialog", Font.BOLD, 25));
			lblDataBalancoDiario.setBounds(288, 107, 150, 31);
		}
		return lblDataBalancoDiario;
	}
	public JLabel getLblNomeUsuarioCaixa() {
		if (lblNomeUsuarioCaixa == null) {
			lblNomeUsuarioCaixa = new JLabel("");
			lblNomeUsuarioCaixa.setVisible(false);
			lblNomeUsuarioCaixa.setFont(new Font("Dialog", Font.BOLD, 25));
			lblNomeUsuarioCaixa.setBounds(620, 107, 425, 31);
		}
		return lblNomeUsuarioCaixa;
	}
	public JLabel getLblDataHoraCaixaFechado() {
		if (lblDataHoraCaixaFechado == null) {
			lblDataHoraCaixaFechado = new JLabel("");
			lblDataHoraCaixaFechado.setVisible(false);
			lblDataHoraCaixaFechado.setFont(new Font("Dialog", Font.BOLD, 25));
			lblDataHoraCaixaFechado.setBounds(344, 163, 274, 31);
		}
		return lblDataHoraCaixaFechado;
	}
	public JLabel getLblDataHoraCaixaAberto() {
		if (lblDataHoraCaixaAberto == null) {
			lblDataHoraCaixaAberto = new JLabel("");
			lblDataHoraCaixaAberto.setVisible(false);
			lblDataHoraCaixaAberto.setFont(new Font("Dialog", Font.BOLD, 25));
			lblDataHoraCaixaAberto.setBounds(310, 217, 274, 31);
		}
		return lblDataHoraCaixaAberto;
	}
	public JLabel getLblValorDinheiro() {
		if (lblValorDinheiro == null) {
			lblValorDinheiro = new JLabel("");
			lblValorDinheiro.setVisible(false);
			lblValorDinheiro.setFont(new Font("Dialog", Font.BOLD, 25));
			lblValorDinheiro.setBounds(221, 377, 187, 31);
		}
		return lblValorDinheiro;
	}
	public JLabel getLblValorCredito() {
		if (lblValorCredito == null) {
			lblValorCredito = new JLabel("");
			lblValorCredito.setVisible(false);
			lblValorCredito.setFont(new Font("Dialog", Font.BOLD, 25));
			lblValorCredito.setBounds(359, 430, 187, 31);
		}
		return lblValorCredito;
	}
	public JLabel getLblValorDebito() {
		if (lblValorDebito == null) {
			lblValorDebito = new JLabel("");
			lblValorDebito.setVisible(false);
			lblValorDebito.setFont(new Font("Dialog", Font.BOLD, 25));
			lblValorDebito.setBounds(344, 478, 187, 31);
		}
		return lblValorDebito;
	}
	public JLabel getLblValorFiado() {
		if (lblValorFiado == null) {
			lblValorFiado = new JLabel("");
			lblValorFiado.setVisible(false);
			lblValorFiado.setFont(new Font("Dialog", Font.BOLD, 25));
			lblValorFiado.setBounds(167, 528, 187, 31);
		}
		return lblValorFiado;
	}
	public JLabel getLblValorAbertura() {
		if (lblValorAbertura == null) {
			lblValorAbertura = new JLabel("");
			lblValorAbertura.setVisible(false);
			lblValorAbertura.setHorizontalAlignment(SwingConstants.CENTER);
			lblValorAbertura.setFont(new Font("Dialog", Font.BOLD, 25));
			lblValorAbertura.setBounds(646, 217, 358, 76);
		}
		return lblValorAbertura;
	}
	public JLabel getLblValorTotal() {
		if (lblValorTotal == null) {
			lblValorTotal = new JLabel("");
			lblValorTotal.setVisible(false);
			lblValorTotal.setHorizontalAlignment(SwingConstants.CENTER);
			lblValorTotal.setFont(new Font("Dialog", Font.BOLD, 25));
			lblValorTotal.setBounds(646, 352, 358, 76);
		}
		return lblValorTotal;
	}
	public JLabel getLblValorDivida() {
		if (lblValorDivida == null) {
			lblValorDivida = new JLabel("");
			lblValorDivida.setVisible(false);
			lblValorDivida.setHorizontalAlignment(SwingConstants.CENTER);
			lblValorDivida.setFont(new Font("Dialog", Font.BOLD, 25));
			lblValorDivida.setBounds(646, 488, 358, 76);
		}
		return lblValorDivida;
	}
}
