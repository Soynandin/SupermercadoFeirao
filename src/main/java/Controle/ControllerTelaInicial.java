package Controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Aplicacao.App;
import Visao.Frame;
import Visao.TelaInicial;
import Visao.TelaLogin;

public class ControllerTelaInicial implements ActionListener {

	private TelaInicial telaInicial;
	private KeyListener acaoTeclado;

	public ControllerTelaInicial(TelaInicial TelaInicial) {
		telaInicial = TelaInicial;
		addEventos();
	}

	public TelaInicial getTela() {
		return telaInicial;
	}

	private void addEventos() {
		telaInicial.getBtInicializar().addActionListener(this);
		telaInicial.getBtInicializar().addKeyListener(addAcaoTeclado());
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == telaInicial.getBtInicializar()) {
			carregarTelaLogin();
		}
	}

	private void carregarTelaLogin() {
		App.janela.getContentPane().removeAll();
		TelaLogin telaLogin = new TelaLogin();
		ControllerTelaLogin ctrlTelaLogin = new ControllerTelaLogin(telaLogin);
		App.janela.getContentPane().add(ctrlTelaLogin.getTela());
		App.janela.getContentPane().add(Frame.getBtFecharJanela());
		App.janela.repaint();
		App.janela.revalidate();
		App.janela.setVisible(true);

	}

	public KeyListener addAcaoTeclado() {
		if (acaoTeclado == null) {
			acaoTeclado = new KeyAdapter() {
				public void keyPressed(KeyEvent ek) {
					if (ek.getKeyCode() == KeyEvent.VK_ENTER && telaInicial.getBtInicializar().hasFocus()) {
						telaInicial.getBtInicializar().doClick();
					}
				}
			};
		}
		return acaoTeclado;
	}

}
