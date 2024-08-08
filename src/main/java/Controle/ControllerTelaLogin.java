package Controle;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;

import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import Modelo.Funcionario;
import Monitoramento.DAOFuncionario;
import Recursos.PopupEscolhaCaixa;
import Visao.TelaLogin;

public class ControllerTelaLogin implements ActionListener {

	public DAOFuncionario daoFuncionario;
	private TelaLogin telaLogin;
	private FocusListener adaptUsuario;
	private FocusListener adaptSenha;
	private KeyListener acaoTeclado;

	public ControllerTelaLogin(TelaLogin TelaLogin) {
		telaLogin = TelaLogin;
		daoFuncionario = new DAOFuncionario();
		addEventos();
	}

	public TelaLogin getTela() {
		return telaLogin;
	}

	private void addEventos() {
		telaLogin.getBtLogar().addActionListener(this);
		telaLogin.getBtLogar().addKeyListener(addAcaoTeclado());
		telaLogin.getTxtUsuario().addFocusListener(addAcaoTxtUsuario());
		telaLogin.getTxtSenha().addFocusListener(addAcaoTxtSenha());
	}

	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == telaLogin.getBtLogar()) {
	        String cpf = telaLogin.getTxtUsuario().getText().trim();
	        String senha = new String(telaLogin.getTxtSenha().getPassword()).trim();

	        if (cpf.isEmpty() || senha.isEmpty() || cpf.equals("Usuário") || senha.equals("senhaaaa")) {
	        	telaLogin.getLblAlerta().setText("<html><div style='text-align:center; color:red;'>Usuário ou senha não podem estar vazios.</div></html>");
	            telaLogin.getLblAlerta().setVisible(true);
	            new Thread(() -> {
	                try {
	                    Thread.sleep(3000);
	                } catch (InterruptedException ex) {
	                    ex.printStackTrace();
	                }
	                SwingUtilities.invokeLater(() -> {
	                    telaLogin.getLblAlerta().setVisible(false);
	                    telaLogin.repaint();
	                });
	            }).start();
	            return;
	        }

	        Funcionario aux = daoFuncionario.BuscarUsuarioSenha(cpf, senha);
	        if (aux == null) {
	            telaLogin.getLblAlerta().setText("Usuário ou senha inválidos.");
	            telaLogin.getLblAlerta().setVisible(true);
	            new Thread(() -> {
	                try {
	                    Thread.sleep(3000);
	                } catch (InterruptedException ex) {
	                    ex.printStackTrace();
	                }
	                SwingUtilities.invokeLater(() -> {
	                    telaLogin.getLblAlerta().setVisible(false);
	                    telaLogin.repaint();
	                });
	            }).start();
	        } else {
	            PopupEscolhaCaixa escolhaCaixa = new PopupEscolhaCaixa(aux);
	            escolhaCaixa.setLocationRelativeTo(telaLogin);
	        }
	    }
	}

	
	
	private MaskFormatter getMascaraUsuario() throws ParseException {
		MaskFormatter mascara = new MaskFormatter("###.###.###-##");
		mascara.setPlaceholderCharacter(' ');
		return mascara;
	}

	public FocusListener addAcaoTxtUsuario() {
		if (adaptUsuario == null) {
			adaptUsuario = new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					if (telaLogin.getTxtUsuario().getText().trim().compareTo("Usuário") == 0) {
						telaLogin.getTxtUsuario().setText("");
						telaLogin.getTxtUsuario().setForeground(new Color(0, 0, 0));
						telaLogin.getTxtUsuario().setCaretPosition(0);
						try {
							telaLogin.getTxtUsuario().setFormatterFactory(new DefaultFormatterFactory(getMascaraUsuario()));
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
				}

				public void focusLost(FocusEvent e) {
					telaLogin.getTxtUsuario().setFormatterFactory(null);
					if (telaLogin.getTxtUsuario().getText().trim().isEmpty() || telaLogin.getTxtUsuario().getText().trim().compareTo("   .   .   -  ")==14) {
						telaLogin.getTxtUsuario().setText(null);
						telaLogin.getTxtUsuario().setText("Usuário");
						telaLogin.getTxtUsuario().setForeground(new Color(114, 114, 114));
					}
				}
			};
		}
		return adaptUsuario;
	}

	public FocusListener addAcaoTxtSenha() {
		if (adaptSenha == null) {
			adaptSenha = new FocusAdapter() {
				public void focusGained(FocusEvent e) {
					if (String.valueOf(telaLogin.getTxtSenha().getPassword()).trim().compareTo("senhaaaa") == 0) {
						telaLogin.getTxtSenha().setText("");
						telaLogin.getTxtSenha().setForeground(new Color(0, 0, 0));
					}
				}

				public void focusLost(FocusEvent e) {
					if (String.valueOf(telaLogin.getTxtSenha().getPassword()).trim().isEmpty()) {
						telaLogin.getTxtSenha().setText("senhaaaa");
						telaLogin.getTxtSenha().setForeground(new Color(114, 114, 114));
					}
				}
			};
		}
		return adaptSenha;
	}

	public KeyListener addAcaoTeclado() {
		if (acaoTeclado == null) {
			acaoTeclado = new KeyAdapter() {
				public void keyPressed(KeyEvent ek) {
					if (ek.getKeyCode() == KeyEvent.VK_ENTER && telaLogin.getBtLogar().hasFocus()) {
						telaLogin.getBtLogar().doClick();
					}
				}
			};
		}
		return acaoTeclado;
	}

}
