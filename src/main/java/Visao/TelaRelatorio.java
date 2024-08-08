package Visao;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controle.ControllerPanelRelatorio;

public class TelaRelatorio extends JPanel{

	private static final long serialVersionUID = 1L;

	private JPanel exibirRelatorio;
	private JLabel lblBackground;
	private JButton btRelatorioCaixaUnico;
	private JButton btRelatorioBalancoGeral;
	
	public TelaRelatorio(String estadoCaixa) {
		this.setBounds(0, 0, 1080, 780);
		setLayout(null);
		addItens(estadoCaixa);
		this.setVisible(true);
	}

	private void addItens(String estadoCaixa) {
		add(getBtRelatorioBalancoGeral());
		add(getBtRelatorioCaixaUnico());
		add(getExibirRelatorio());
		add(getLblBackground(estadoCaixa));
	}
	
	public JPanel getExibirRelatorio() {
		if(exibirRelatorio == null) {
			exibirRelatorio = new JPanel();
			exibirRelatorio.setLayout(new BorderLayout());
			exibirRelatorio.setBounds(10, 85, 1060, 683);
			PanelRelatorioCaixaDiario panelRelatorioCaixaDiario = new PanelRelatorioCaixaDiario();
			ControllerPanelRelatorio ctrlPanelRelatorio = new ControllerPanelRelatorio(panelRelatorioCaixaDiario);
			setExibirRelatorio(ctrlPanelRelatorio.getTela());
		}
		return exibirRelatorio;
	}
	
	public void setExibirRelatorio(JPanel exibirRelatorio) {
		this.exibirRelatorio.add(exibirRelatorio, BorderLayout.CENTER);
	}

	public JLabel getLblBackground(String e) {
		if (lblBackground == null) {
			lblBackground = new JLabel();
			if(e.equals("ADMINISTRADOR")) {
				lblBackground.setIcon(new ImageIcon(TelaRelatorio.class.getResource("/Imagens/BackgroundTelaRelatorioAdmin.png")));
			}else {
				lblBackground.setIcon(new ImageIcon(TelaRelatorio.class.getResource("/Imagens/BackgroundTelaRelatorioAtendente.png")));
			}
			lblBackground.setBounds(0, 0, 1080, 780);
		}
		return lblBackground;
	}
	public JButton getBtRelatorioCaixaUnico() {
		if (btRelatorioCaixaUnico == null) {
			btRelatorioCaixaUnico = new JButton("");
			btRelatorioCaixaUnico.setBounds(801, 23, 50, 50);
			btRelatorioCaixaUnico.setBorder(null);
			btRelatorioCaixaUnico.setContentAreaFilled(false);
			btRelatorioCaixaUnico.setFocusPainted(false);
		}
		return btRelatorioCaixaUnico;
	}
	public JButton getBtRelatorioBalancoGeral() {
		if (btRelatorioBalancoGeral == null) {
			btRelatorioBalancoGeral = new JButton("");
			btRelatorioBalancoGeral.setBounds(890, 23, 50, 50);
			btRelatorioBalancoGeral.setBorder(null);
			btRelatorioBalancoGeral.setContentAreaFilled(false);
			btRelatorioBalancoGeral.setFocusPainted(false);
		}
		return btRelatorioBalancoGeral;
	}
}
