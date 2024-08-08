package Visao;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controle.ControllerPanelCadastroFuncionario;

public class TelaCadastroUsuario extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblBackground;
	private JPanel exibirCadastro;
	private JButton btCarregarFuncionario;
	private JButton btCarregaCliente;
	
	public TelaCadastroUsuario() {
		this.setBounds(0, 0, 1080, 780);
		addItens();
		this.setVisible(true);

	}

	private void addItens() {
		setLayout(null);
		add(getBtCarregaCliente());
		add(getBtCarregarFuncionario());
		add(getExibirCadastro());
		add(getLblBackground());
	}
	
	public JLabel getLblBackground() {
		if(lblBackground==null) {
			lblBackground = new JLabel();
			lblBackground.setIcon(new ImageIcon(PanelCadastroCliente.class.getResource("/Imagens/BackgroundTelaCadastroFuncionario.png")));
			lblBackground.setBounds(0, 0, 1080, 780);
		}
		return lblBackground;
	}

	public JPanel getExibirCadastro() {
		if (exibirCadastro == null) {
			exibirCadastro = new JPanel();
			exibirCadastro.setLayout(new BorderLayout());
			exibirCadastro.setBounds(10, 85, 1060, 683);
			PanelCadastroFuncionario telaCadastroFuncionario = new PanelCadastroFuncionario();
			ControllerPanelCadastroFuncionario ctrlTelaCadastroFuncionario = new ControllerPanelCadastroFuncionario(telaCadastroFuncionario);
			setExibirCadastro(ctrlTelaCadastroFuncionario.getTela());
		}
		return exibirCadastro;
	}
	
	public void setExibirCadastro(JPanel exibirCadastro) {
		this.exibirCadastro.add(exibirCadastro, BorderLayout.CENTER);
	}
	
	public JButton getBtCarregarFuncionario() {
		if (btCarregarFuncionario == null) {
			btCarregarFuncionario = new JButton("");
			btCarregarFuncionario.setBounds(894, 26, 70, 60);
			btCarregarFuncionario.setBorder(null);
			btCarregarFuncionario.setContentAreaFilled(false);
			btCarregarFuncionario.setFocusPainted(false);
		}
		return btCarregarFuncionario;
	}
	
	public JButton getBtCarregaCliente() {
		if (btCarregaCliente == null) {
			btCarregaCliente = new JButton("");
			btCarregaCliente.setBounds(799, 24, 74, 62);
			btCarregaCliente.setBorder(null);
			btCarregaCliente.setContentAreaFilled(false);
			btCarregaCliente.setFocusPainted(false);
		}
		return btCarregaCliente;
	}
	
	
	
	
}
