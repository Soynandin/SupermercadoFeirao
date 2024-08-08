package Controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

import Aplicacao.App;
import Visao.PanelCadastroCliente;
import Visao.PanelCadastroFuncionario;
import Visao.TelaCadastroUsuario;

public class ControllerTelaCadastroUsuario implements ActionListener{
	
	TelaCadastroUsuario telaCadastroUsuario;

	public ControllerTelaCadastroUsuario(TelaCadastroUsuario telaCadastroUsuario) {
		this.telaCadastroUsuario = telaCadastroUsuario;
		addEvent();
	}

	private void addEvent() {
		telaCadastroUsuario.getBtCarregaCliente().addActionListener(this);
		telaCadastroUsuario.getBtCarregarFuncionario().addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == telaCadastroUsuario.getBtCarregaCliente()) {
			somClique();
			telaCadastroUsuario.getLblBackground().setIcon(new ImageIcon(PanelCadastroCliente.class.getResource("/Imagens/BackgroundTelaCadastroCliente.png")));
			carregaPanelFuncionario();
		}
		if(e.getSource() == telaCadastroUsuario.getBtCarregarFuncionario()) {
			somClique();
			telaCadastroUsuario.getLblBackground().setIcon(new ImageIcon(PanelCadastroCliente.class.getResource("/Imagens/BackgroundTelaCadastroFuncionario.png")));
			
				carregaPanelCliente();
		}
	}

	private void carregaPanelCliente(){
		
	    telaCadastroUsuario.getExibirCadastro().removeAll();
	    PanelCadastroFuncionario panelCadastroFuncionario = new PanelCadastroFuncionario();
	    ControllerPanelCadastroFuncionario ctrlPanelCadastroFuncionario = new ControllerPanelCadastroFuncionario(panelCadastroFuncionario);
	    telaCadastroUsuario.setExibirCadastro(ctrlPanelCadastroFuncionario.getTela());
	    ctrlPanelCadastroFuncionario.getTela().setVisible(true);
	    telaCadastroUsuario.revalidate();
	    telaCadastroUsuario.repaint();
	}
	
	
	
	private void carregaPanelFuncionario(){
	    telaCadastroUsuario.getExibirCadastro().removeAll();
	    PanelCadastroCliente panelCadastroCliente = new PanelCadastroCliente();
	    ControllerPanelCadastroCliente ctrlPanelCadastroCliente = new ControllerPanelCadastroCliente(panelCadastroCliente);
	    telaCadastroUsuario.setExibirCadastro(ctrlPanelCadastroCliente.getTela());
	    ctrlPanelCadastroCliente.getTela().setVisible(true);
	    telaCadastroUsuario.revalidate();
	    telaCadastroUsuario.repaint();
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
