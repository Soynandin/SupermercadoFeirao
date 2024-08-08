package Visao;

import javax.swing.JPanel;

import Controle.ControllerPanelGerenciamentoEstoque;

import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;

public class TelaGerenciamentoEstoque extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel lblBackground;
	private JButton btRelatorioEstoque;
	private JButton btBalancoFinanceiro;
	private JPanel exibirRelatorios;

	public TelaGerenciamentoEstoque() {
		this.setBounds(0, 0, 1080, 780);
		setLayout(null);
		addItens();
		this.setVisible(true);
	}

	private void addItens() {
		add(getExibirRelatorios());
		add(getBtBalancoFinanceiro());
		add(getBtRelatorioEstoque());
		add(getLblBackground());
	}
	
	public JLabel getLblBackground() {
		if (lblBackground == null) {
			lblBackground = new JLabel();
			lblBackground.setIcon(new ImageIcon(TelaGerenciamentoEstoque.class.getResource("/Imagens/BackgroundBalancoFinanceiro.png")));
			lblBackground.setBounds(0, 0, 1080, 780);
		}
		return lblBackground;
	}
	public JButton getBtRelatorioEstoque() {
		if (btRelatorioEstoque == null) {
			btRelatorioEstoque = new JButton("");
			btRelatorioEstoque.setBorder(null);
			btRelatorioEstoque.setContentAreaFilled(false);
			btRelatorioEstoque.setFocusPainted(false);
			btRelatorioEstoque.setBounds(744, 11, 53, 63);
		}
		return btRelatorioEstoque;
	}
	public JButton getBtBalancoFinanceiro() {
		if (btBalancoFinanceiro == null) {
			btBalancoFinanceiro = new JButton("");
			btBalancoFinanceiro.setBorder(null);
			btBalancoFinanceiro.setContentAreaFilled(false);
			btBalancoFinanceiro.setFocusPainted(false);
			btBalancoFinanceiro.setBounds(862, 11, 63, 63);
		}
		return btBalancoFinanceiro;
	}
	public JPanel getExibirRelatorios() {
		if (exibirRelatorios == null) {
			exibirRelatorios = new JPanel();
			exibirRelatorios.setLayout(new BorderLayout());
			exibirRelatorios.setBounds(10, 83, 1060, 683);
			PanelGerenciamentoEstoque panelGerenciamentoEstoque = new PanelGerenciamentoEstoque();
			panelGerenciamentoEstoque.getCbxCategoriaEstoque().setFont(new Font("Dialog", Font.PLAIN, 18));
			ControllerPanelGerenciamentoEstoque ctrlPanelGerenciamentoEstoque = new ControllerPanelGerenciamentoEstoque(panelGerenciamentoEstoque);
			setExibirRelatorio(ctrlPanelGerenciamentoEstoque.getTela());
		}
		return exibirRelatorios;
	}
	
	public void setExibirRelatorio(JPanel exibirRelatorio) {
		this.exibirRelatorios.add(exibirRelatorio, BorderLayout.CENTER);
	}
}
