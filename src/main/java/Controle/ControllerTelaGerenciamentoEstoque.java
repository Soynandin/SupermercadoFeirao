package Controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Aplicacao.App;
import Visao.PanelBalancoFinanceiro;
import Visao.PanelGerenciamentoEstoque;
import Visao.TelaGerenciamentoEstoque;

public class ControllerTelaGerenciamentoEstoque implements ActionListener{

	TelaGerenciamentoEstoque telaGerenciamentoEstoque;
	
	public ControllerTelaGerenciamentoEstoque(TelaGerenciamentoEstoque telaGerenciamentoEstoque) {
		this.telaGerenciamentoEstoque = telaGerenciamentoEstoque;
		addEventos();
	}

	private void addEventos() {
		telaGerenciamentoEstoque.getBtRelatorioEstoque().addActionListener(this);
		telaGerenciamentoEstoque.getBtBalancoFinanceiro().addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == telaGerenciamentoEstoque.getBtRelatorioEstoque()) {
			somClique();
			telaGerenciamentoEstoque.getExibirRelatorios().removeAll();
			PanelGerenciamentoEstoque panelGerenciamentoEstoque = new PanelGerenciamentoEstoque();
			ControllerPanelGerenciamentoEstoque ctrlPanelGerenciamentoEstoque = new ControllerPanelGerenciamentoEstoque(panelGerenciamentoEstoque);
			telaGerenciamentoEstoque.setExibirRelatorio(ctrlPanelGerenciamentoEstoque.getTela());
			ctrlPanelGerenciamentoEstoque.getTela().setVisible(true);
			telaGerenciamentoEstoque.revalidate();
			telaGerenciamentoEstoque.repaint();
		}
		if(e.getSource() == telaGerenciamentoEstoque.getBtBalancoFinanceiro()) {
			somClique();
			telaGerenciamentoEstoque.getExibirRelatorios().removeAll();
			PanelBalancoFinanceiro panelBalancoFinanceiro = new PanelBalancoFinanceiro();
			ControllerPanelBalancoFinanceiro ctrlPanelBalancoFinanceiro = new ControllerPanelBalancoFinanceiro(panelBalancoFinanceiro);
			telaGerenciamentoEstoque.setExibirRelatorio(ctrlPanelBalancoFinanceiro.getTela());
			ctrlPanelBalancoFinanceiro.getTela().setVisible(true);
			telaGerenciamentoEstoque.revalidate();
			telaGerenciamentoEstoque.repaint();
		}
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
