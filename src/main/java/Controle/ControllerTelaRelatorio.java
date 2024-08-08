package Controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Aplicacao.App;
import Visao.PanelRelatorioBalancoGeral;
import Visao.PanelRelatorioCaixaDiario;
import Visao.TelaRelatorio;

public class ControllerTelaRelatorio implements ActionListener{

	TelaRelatorio telaRelatorio;
	
	public ControllerTelaRelatorio(TelaRelatorio telaRelatorio) {
		this.telaRelatorio=telaRelatorio;
		addEventos();
	}
	
	public TelaRelatorio getTela() {
		return telaRelatorio;
	}

	private void addEventos() {
		telaRelatorio.getBtRelatorioCaixaUnico().addActionListener(this);
		telaRelatorio.getBtRelatorioBalancoGeral().addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == telaRelatorio.getBtRelatorioCaixaUnico()) {
			somClique();
			telaRelatorio.getExibirRelatorio().removeAll();
			PanelRelatorioCaixaDiario panelRelatorioCaixaDiario = new PanelRelatorioCaixaDiario();
			ControllerPanelRelatorio ctrlPanelRelatorio = new ControllerPanelRelatorio(panelRelatorioCaixaDiario);
			telaRelatorio.setExibirRelatorio(ctrlPanelRelatorio.getTela());
			ctrlPanelRelatorio.getTela().setVisible(true);
			telaRelatorio.revalidate();
			telaRelatorio.repaint();
		}
		if(e.getSource() == telaRelatorio.getBtRelatorioBalancoGeral()) {
			somClique();
			telaRelatorio.getExibirRelatorio().removeAll();
			PanelRelatorioBalancoGeral panelRelatorioBalancoGeral = new PanelRelatorioBalancoGeral();
			ControllerPanelRelatorioGeral ctrlPanelRelatorioGeral = new ControllerPanelRelatorioGeral(panelRelatorioBalancoGeral);
			telaRelatorio.setExibirRelatorio(ctrlPanelRelatorioGeral.getTela());
			ctrlPanelRelatorioGeral.getTela().setVisible(true);
			telaRelatorio.revalidate();
			telaRelatorio.repaint();
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
