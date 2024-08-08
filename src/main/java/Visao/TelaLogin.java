package Visao;

import java.awt.Color;
import java.awt.Font;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import Aplicacao.App;

public class TelaLogin extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblBackground;
	private JLabel lblLogo;
	private JLabel lblTitulo;
	private JLabel lblIconUsuario;
	private JLabel lblIconSenha;
	private JButton btLogar;
	private JFormattedTextField txtUsuario;
	private JPasswordField txtSenha;
	private JLabel lblAlerta;

	public TelaLogin() {
		App.controleTela = 0;
		this.setLayout(null);
		this.setBounds(0, 0, 1080, 780);
		addItens();
		this.setVisible(true);
	}

	private void addItens() {
		add(getLblAlerta());
		add(getTxtSenha());
		add(getTxtUsuario());
		add(getBtLogar());
		add(getLblTitulo());
		add(getLblIconSenha());
		add(getLblIconUsuario());
		add(getLblLogo());
		add(getLblBackground());
	}

	public JLabel getLblBackground() {
		if (lblBackground == null) {
			lblBackground = new JLabel();
			lblBackground.setBounds(0, 0, 1080, 780);
			lblBackground.setIcon(new ImageIcon(TelaInicial.class.getResource("/Imagens/Background.png")));
		}
		return lblBackground;
	}

	public JLabel getLblLogo() {
		if (lblLogo == null) {
			lblLogo = new JLabel();
			lblLogo.setBounds(34, 22, 175, 175);
			lblLogo.setIcon(new ImageIcon(TelaLogin.class.getResource("/Imagens/MiniLogoMarket4.png")));
		}
		return lblLogo;
	}

	public JLabel getLblTitulo() {
		if (lblTitulo == null) {
			lblTitulo = new JLabel();
			lblTitulo.setIcon(new ImageIcon(TelaLogin.class.getResource("/Imagens/BEM-VINDO.png")));
			lblTitulo.setBounds(615, 224, 245, 75);
		}
		return lblTitulo;
	}

	public JLabel getLblIconUsuario() {
		if (lblIconUsuario == null) {
			lblIconUsuario = new JLabel();
			lblIconUsuario.setIcon(new ImageIcon(TelaLogin.class.getResource("/Imagens/IconUsuario.png")));
			lblIconUsuario.setBounds(615, 347, 40, 40);
		}
		return lblIconUsuario;
	}

	public JLabel getLblIconSenha() {
		if (lblIconSenha == null) {
			lblIconSenha = new JLabel();
			lblIconSenha.setIcon(new ImageIcon(TelaLogin.class.getResource("/Imagens/IconSenha.png")));
			lblIconSenha.setBounds(615, 408, 40, 40);
		}
		return lblIconSenha;
	}

	public JButton getBtLogar() {
		if (btLogar == null) {
			btLogar = new JButton();
			btLogar.setIcon(new ImageIcon(TelaLogin.class.getResource("/Imagens/btEntrar.png")));
			btLogar.setBounds(610, 473, 250, 40);
			btLogar.setBorder(null);
			btLogar.setContentAreaFilled(false);
			btLogar.setFocusPainted(false);
		}
		return btLogar;
	}

	public JFormattedTextField getTxtUsuario() {
		if (txtUsuario == null) {
			txtUsuario = new JFormattedTextField();
			txtUsuario.setForeground(new Color(114, 114, 114));
			txtUsuario.setText("Usuário");
			txtUsuario.setFont(new Font("Microsoft YaHei", Font.PLAIN, 17));
			txtUsuario.setBounds(660, 347, 200, 40);

			// Adiciona um DocumentListener ao documento do TextField
			txtUsuario.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void insertUpdate(DocumentEvent e) {
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					// Método chamado quando o texto é removido
					verificaTextoVazio();
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					// Método chamado quando há uma alteração que afeta o atributo do documento
					verificaTextoVazio();
				}

				private void verificaTextoVazio() {
					SwingUtilities.invokeLater(() -> {
						// Verifica se o texto do TextField está vazio ou não contém o caracter '-'
						if (txtUsuario.getText().isEmpty()
								|| txtUsuario.getText().length() <= 12 && txtUsuario.getText().length() != 7) {
							try {
								txtUsuario.setFormatterFactory(new DefaultFormatterFactory(getMascaraCpf()));
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
						}

					});
				}

				private MaskFormatter getMascaraCpf() throws ParseException {
					MaskFormatter mf = new MaskFormatter("###.###.###-##");
					mf.setPlaceholderCharacter(' ');
					return mf;
				}
			});
		}
		return txtUsuario;
	}

	public JPasswordField getTxtSenha() {
		if (txtSenha == null) {
			txtSenha = new JPasswordField();
			txtSenha.setForeground(new Color(114, 114, 114));
			txtSenha.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
			txtSenha.setText("senhaaaa");
			txtSenha.setBounds(660, 408, 200, 40);
		}
		return txtSenha;
	}

	public JLabel getLblAlerta() {
		if (lblAlerta == null) {
			lblAlerta = new JLabel("Usuário ou senha incorretos!");
			lblAlerta.setVerticalAlignment(SwingConstants.TOP);
			lblAlerta.setHorizontalAlignment(SwingConstants.CENTER);
			lblAlerta.setForeground(new Color(255, 0, 0));
			lblAlerta.setFont(new Font("DialogInput", Font.BOLD, 15));
			lblAlerta.setVisible(false);
			lblAlerta.setBounds(596, 524, 281, 80);
		}
		return lblAlerta;
	}
}
