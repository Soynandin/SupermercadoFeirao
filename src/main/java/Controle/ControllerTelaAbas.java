package Controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Visao.Frame;
import Visao.TelaAbas;

public class ControllerTelaAbas implements ActionListener{
	
	TelaAbas telaAbas;
	private KeyListener teclaBtFecharCaixa;
	
	public ControllerTelaAbas(TelaAbas telaAbas) {
		this.telaAbas=telaAbas;
		addEventos();
	}
	
	public TelaAbas getTela() {
		return telaAbas;
	}

	private void addEventos() {
		telaAbas.getBtAba1().addActionListener(this);
		telaAbas.getBtAba2().addActionListener(this);
		telaAbas.getBtAba3().addActionListener(this);
		telaAbas.getBtAba4().addActionListener(this);
		telaAbas.getBtAba5().addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == telaAbas.getBtAba1()) {
			telaAbas.setSelectedIndex(0);
		}
		if(e.getSource() == telaAbas.getBtAba2()) {
			telaAbas.setSelectedIndex(1);
		}
		if(e.getSource() == telaAbas.getBtAba3()) {
			telaAbas.setSelectedIndex(2);
		}
		if(e.getSource() == telaAbas.getBtAba4()) {
			telaAbas.setSelectedIndex(3);
		}
		if(e.getSource() == telaAbas.getBtAba5()) {
			telaAbas.setSelectedIndex(4);
		}
	}

	public KeyListener acaoTeclaBtFecharCaixa() {
		if (teclaBtFecharCaixa == null) {
			teclaBtFecharCaixa = new KeyListener() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						Frame.getBtFecharJanela().doClick();
					}
				}
				@Override
				public void keyReleased(KeyEvent e) {}
				@Override
				public void keyTyped(KeyEvent e) {}
			};
		}
		return teclaBtFecharCaixa;
	}
}
