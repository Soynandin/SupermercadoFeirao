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
import javax.swing.SwingUtilities;

import Aplicacao.App;
import Modelo.BalancoGeral;
import Modelo.Funcionario;
import Modelo.Venda;
import Monitoramento.DAOBalancoGeral;
import Monitoramento.DAOVenda;
import Recursos.PopupEscolhaCaixa;
import Visao.PanelRelatorioBalancoGeral;
import Visao.TelaAbas;

public class ControllerPanelRelatorioGeral implements ActionListener {

	PanelRelatorioBalancoGeral panelRelatorioBalancoGeral;
	public double totalDinheiro = 0.0;
	public double totalDebito = 0.0;
	public double totalCredito = 0.0;
	public double totalFiado = 0.0;
	public double subTotal = 0.0;
	public double total = 0.0;

	public ControllerPanelRelatorioGeral(PanelRelatorioBalancoGeral panelRelatorioBalancoGeral) {
		this.panelRelatorioBalancoGeral = panelRelatorioBalancoGeral;
		addEventos();
	}

	private void addEventos() {
		panelRelatorioBalancoGeral.getBtSangria().addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == panelRelatorioBalancoGeral.getBtSangria()) {
			if (App.statusTela.equals("ABERTO")) {
				somErro();
				mostraAlerta();
			} else if (App.listaVendas.isEmpty()) {
				somErro();
				mostraAlertaAlterado();
			} else {
				somClique();
				panelRelatorioBalancoGeral.getBtSangria().setVisible(false);
				SwingUtilities.invokeLater(() -> {
					panelRelatorioBalancoGeral.getLblBackground().setIcon(new ImageIcon(PanelRelatorioBalancoGeral.class.getResource("/Imagens/BalancoDiarioSangria (1).png")));
					panelRelatorioBalancoGeral.getLblDataBalancoDiario().setVisible(true);
					panelRelatorioBalancoGeral.getLblNomeUsuarioCaixa().setVisible(true);
					panelRelatorioBalancoGeral.getLblDataHoraCaixaAberto().setVisible(true);
					panelRelatorioBalancoGeral.getLblDataHoraCaixaFechado().setVisible(true);
					panelRelatorioBalancoGeral.getLblValorDinheiro().setVisible(true);
					panelRelatorioBalancoGeral.getLblValorDebito().setVisible(true);
					panelRelatorioBalancoGeral.getLblValorCredito().setVisible(true);
					panelRelatorioBalancoGeral.getLblValorFiado().setVisible(true);
					panelRelatorioBalancoGeral.getLblValorAbertura().setVisible(true);
					panelRelatorioBalancoGeral.getLblValorTotal().setVisible(true);
					panelRelatorioBalancoGeral.getLblValorDivida().setVisible(true);
					panelRelatorioBalancoGeral.repaint();
					exibirDadosUsuarioLogado();
				});
			}
		}
	}

	public PanelRelatorioBalancoGeral getTela() {
		return panelRelatorioBalancoGeral;
	}
	
	private void exibirDadosUsuarioLogado() {
		Funcionario funcionario = PopupEscolhaCaixa.FuncAtivo;
		if (funcionario != null) {
			String data = obterDataBalançoDiario();
			panelRelatorioBalancoGeral.getLblNomeUsuarioCaixa().setText(funcionario.getNomeCompleto());
			panelRelatorioBalancoGeral.getLblDataHoraCaixaAberto().setText(obterDataHoraAberturaCaixa());
			panelRelatorioBalancoGeral.getLblDataHoraCaixaFechado().setText(obterDataHoraFechamentoCaixa());
			panelRelatorioBalancoGeral.getLblDataBalancoDiario().setText(data);
			coletarDadosBalanco();
			panelRelatorioBalancoGeral.getLblValorDinheiro().setText(obterValorDinheiro());
			panelRelatorioBalancoGeral.getLblValorDebito().setText(obterValorDebito());
			panelRelatorioBalancoGeral.getLblValorCredito().setText(obterValorCredito());
			panelRelatorioBalancoGeral.getLblValorFiado().setText(obterValorFiado());
			panelRelatorioBalancoGeral.getLblValorAbertura().setText(obterValorAberturaCaixa());
			panelRelatorioBalancoGeral.getLblValorTotal().setText(calcularValorTotal());
			panelRelatorioBalancoGeral.getLblValorDivida().setText(calcularValorDivida());
			if (!TelaAbas.getEstd().equals("ADMINISTRADOR")) {
				salvarDadosDeBalanco();
			}
		} else {
			panelRelatorioBalancoGeral.getLblNomeUsuarioCaixa().setText("Usuário não encontrado");
		}
	}

	// Método atualizado para salvar ou atualizar os dados de balanço
	public void salvarDadosDeBalanco() {
		Funcionario funcionario = PopupEscolhaCaixa.FuncAtivo;
		DAOBalancoGeral daoBalanco =  new DAOBalancoGeral();
		if (funcionario != null) {
			BalancoGeral atualizarBalanco = daoBalanco.obterOuCriarBalancoGeralParaHoje(funcionario);
			atualizarBalanco.setDataHoraCaixaFechado(LocalDateTime.now());
			atualizarBalanco.setMovDinheiro(Double.parseDouble(obterValorDinheiro()));
			atualizarBalanco.setMovCredito(Double.parseDouble(obterValorCredito()));
			atualizarBalanco.setMovDebito(Double.parseDouble(obterValorDebito()));
			atualizarBalanco.setMovFiado(Double.parseDouble(obterValorFiado()));
			atualizarBalanco.setValorSaldo(Double.parseDouble(calcularValorTotal())-Double.parseDouble(obterValorFiado()));
			atualizarBalanco.setValorTotal(Double.parseDouble(calcularValorTotal()));
			atualizarBalanco.setValorDividas(Double.parseDouble(calcularValorDivida()));
			DAOBalancoGeral daoB =  new DAOBalancoGeral();
			daoB.atualizarBalancoGeral(atualizarBalanco);
		} else {
			panelRelatorioBalancoGeral.getLblNomeUsuarioCaixa().setText("Usuário não encontrado");
		}
	}
	
	private void coletarDadosBalanco() {
		DAOVenda daoVenda = new DAOVenda();
		List<Venda> listaTeste = daoVenda.listarVendasPorBalancoGeral(PopupEscolhaCaixa.BalancoAtivo);
		for (int i = 0; i < listaTeste.size(); i++) {
			if (listaTeste.get(i).getFormaPagVenda().equals("Dinheiro")
					|| listaTeste.get(i).getFormaPagVenda().equals("Pix")) {
				totalDinheiro += Double.parseDouble(listaTeste.get(i).getValorTotalVenda());
			} else if (listaTeste.get(i).getFormaPagVenda().equals("Cartão Débito")) {
				totalDebito += Double.parseDouble(listaTeste.get(i).getValorTotalVenda());
			} else if (listaTeste.get(i).getFormaPagVenda().equals("Cartão Crédito")) {
				totalCredito += Double.parseDouble(listaTeste.get(i).getValorTotalVenda());
			} else {
				totalFiado += Double.parseDouble(listaTeste.get(i).getValorTotalVenda());
			}
			subTotal+= Double.parseDouble(listaTeste.get(i).getSubTotalVenda());
			total += Double.parseDouble(listaTeste.get(i).getValorTotalVenda());
		}
	}

	private void mostraAlertaAlterado() {
		panelRelatorioBalancoGeral.getLblAlerta().setIcon(new ImageIcon(PanelRelatorioBalancoGeral.class.getResource("/Imagens/alertaBalancoCaixaVazio.png")));
		panelRelatorioBalancoGeral.getLblAlerta().setVisible(true);
		panelRelatorioBalancoGeral.repaint();

		new Thread(() -> {

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			SwingUtilities.invokeLater(() -> {
				panelRelatorioBalancoGeral.getLblAlerta().setVisible(false);
				panelRelatorioBalancoGeral.getLblAlerta().setIcon(new ImageIcon(
						PanelRelatorioBalancoGeral.class.getResource("/Imagens/alertaBalancoCaixaAberto.png")));
				panelRelatorioBalancoGeral.repaint();
			});
		}).start();
	}

	private void mostraAlerta() {
		panelRelatorioBalancoGeral.getLblAlerta().setVisible(true);
		panelRelatorioBalancoGeral.repaint();

		new Thread(() -> {

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			SwingUtilities.invokeLater(() -> {
				panelRelatorioBalancoGeral.getLblAlerta().setVisible(false);
				panelRelatorioBalancoGeral.repaint();
			});
		}).start();
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

	private void somErro() {
		try {
			URL url = App.class.getResource("/Audio/erro.wav");
			AudioInputStream stream = AudioSystem.getAudioInputStream(url);
			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private String obterDataBalançoDiario() {
		LocalDateTime agora = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataHoraString = agora.format(formatter);
		return dataHoraString;
	}
	
	private String obterDataHoraAberturaCaixa() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			LocalDateTime l = PopupEscolhaCaixa.BalancoAtivo.getDataHoraCaixaAberto();
			String dataHoraString = l.format(formatter);
			return dataHoraString;
		} catch (Exception e) {
			return "";
		}

	}

	private String obterDataHoraFechamentoCaixa() {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			LocalDateTime l = PopupEscolhaCaixa.BalancoAtivo.getDataHoraCaixaFechado();
			String dataHoraString = l.format(formatter);
			return dataHoraString;
		} catch (Exception e) {
			return "";
		}
	}

	private String obterValorDinheiro() {
		return String.valueOf(totalDinheiro);
	}

	private String obterValorDebito() {
		return String.valueOf(totalDebito);
	}

	private String obterValorCredito() {
		return String.valueOf(totalCredito);
	}

	private String obterValorFiado() {
		return String.valueOf(totalFiado);
	}

	private String obterValorAberturaCaixa() {
		return "0.00";
	}

	private String calcularValorTotal() {
		return String.valueOf(total);
	}

	private String calcularValorDivida() {
		return String.valueOf(totalFiado);
	}
}
