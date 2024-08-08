package Controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

import Aplicacao.App;
import Modelo.BalancoFinanceiro;
import Modelo.BalancoGeral;
import Monitoramento.DAOBalancoFinanceiro;
import Monitoramento.DAOBalancoGeral;
import Visao.PanelBalancoFinanceiro;

public class ControllerPanelBalancoFinanceiro implements ActionListener {

	PanelBalancoFinanceiro panelBalancoFinanceiro;

	public double total;
	public double divida;
	public double lucro;

	public ControllerPanelBalancoFinanceiro(PanelBalancoFinanceiro panelBalancoFinanceiro) {
		this.panelBalancoFinanceiro = panelBalancoFinanceiro;
		panelBalancoFinanceiro.getBtFazerBalanco().setEnabled(true);
		addEventos();
	}

	public PanelBalancoFinanceiro getTela() {
		return panelBalancoFinanceiro;
	}

	private void addEventos() {
		panelBalancoFinanceiro.getBtFazerBalanco().addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == panelBalancoFinanceiro.getBtFazerBalanco()) {
            
			somClique();
			panelBalancoFinanceiro.getLblBackground().setIcon(new ImageIcon(PanelBalancoFinanceiro.class.getResource("/Imagens/BalancoFinanceiroMensal.png")));
			panelBalancoFinanceiro.getLblDataHoraBalanco().setVisible(true);
			panelBalancoFinanceiro.getLblValorMovimentacaoMensal().setVisible(true);
			panelBalancoFinanceiro.getLblValorLucroMensal().setVisible(true);
			panelBalancoFinanceiro.getLblValorDividaMensal().setVisible(true);
			panelBalancoFinanceiro.repaint();
			exibirDadosBalanco();
			panelBalancoFinanceiro.getBtFazerBalanco().setEnabled(false);
		}
	}

	private void salvarBalancoFinanceiro(BalancoFinanceiro b) {
		DAOBalancoFinanceiro daoBalancoFinanceiro = new DAOBalancoFinanceiro();
		try {
			daoBalancoFinanceiro.salvarOuAtualizarBalancoFinanceiro(b);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void exibirDadosBalanco() {
		try {
			lerArquivoHistoricoBalancoDiario();
			panelBalancoFinanceiro.getLblDataHoraBalanco().setText("Data do Balanço: " + obterDataBalanço());
			panelBalancoFinanceiro.getLblValorMovimentacaoMensal().setText("Valor da Movimentação Mensal: " + obterValorMovimentacaoMensal());
			panelBalancoFinanceiro.getLblValorLucroMensal().setText("Lucro Mensal: " + calcularLucroMensal());
			panelBalancoFinanceiro.getLblValorDividaMensal()
					.setText("Valor da Dívida Mensal: " + calcularValorDividaMensal());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void lerArquivoHistoricoBalancoDiario() {
		DAOBalancoGeral daoBalancoGeral = new DAOBalancoGeral();
		List<BalancoGeral> balancos = daoBalancoGeral.buscarBalancosGeraisDoMesAtual();
		try {
			double somaMovimentacaoMensal = 0.0;
			double somaMovimentacaoBruta = 0.0;
			double somaDivida = 0.0;
			for (BalancoGeral balanco : balancos) {
				double valorTotal = balanco.getValorTotal();
				double valorDivida = balanco.getValorDividas();
				double valorSubTotal = balanco.getMovCredito() 
						+ balanco.getMovDebito() 
						+ balanco.getMovDinheiro()
						+ balanco.getMovFiado();
				somaMovimentacaoMensal += valorTotal;
				somaMovimentacaoBruta += valorSubTotal;
				somaDivida += valorDivida;
			}
			
			String formato1 = String.format("%.2f", somaMovimentacaoMensal);
			formato1 = formato1.replace(",", ".");
			total = Double.parseDouble(formato1);

			String formato2 = String.format("%.2f", somaDivida);
			formato2 = formato2.replace(",", ".");
			divida = Double.parseDouble(formato2);

			double lucroCalculado = somaMovimentacaoMensal - somaMovimentacaoBruta;
			String formato3 = String.format("%.2f", lucroCalculado);
			formato3 = formato3.replace(",", ".");
			lucro = Double.parseDouble(formato3);
			
			LocalDateTime dataHora = LocalDateTime.now();
			BalancoFinanceiro novoBalancoF = new BalancoFinanceiro(dataHora, total, lucro, divida);
			salvarBalancoFinanceiro(novoBalancoF);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Métodos fictícios para obter as informações do balanço financeiro
	private String obterDataBalanço() {
		LocalDateTime agora = LocalDateTime.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return dateTimeFormatter.format(agora);
	}

	private String obterValorMovimentacaoMensal() {
		return "R$ " + String.format("%.2f", total);
	}

	private String calcularLucroMensal() {
		return "R$ " + String.format("%.2f", lucro);
	}

	private String calcularValorDividaMensal() {
		return "R$ " + String.format("%.2f", divida);
	}

	private void somClique() {
		try {
			URL url = App.class.getResource("/Audio/cliquei.wav");
			AudioInputStream stream = AudioSystem.getAudioInputStream(url);
			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}