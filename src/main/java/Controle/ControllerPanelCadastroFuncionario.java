package Controle;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import Aplicacao.App;
import Modelo.Funcionario;
import Monitoramento.DAOFuncionario;
import Visao.PanelCadastroFuncionario;

public class ControllerPanelCadastroFuncionario implements ActionListener {

	PanelCadastroFuncionario telaCadastroFuncionario;

	public ControllerPanelCadastroFuncionario(PanelCadastroFuncionario telaCadastroFuncionario) {
		this.telaCadastroFuncionario = telaCadastroFuncionario;
		addEventos();
	}

	public PanelCadastroFuncionario getTela() {
		return telaCadastroFuncionario; // ou o painel apropriado
	}

	private void addEventos() {
		telaCadastroFuncionario.getBtCadastrar().addActionListener(this);
		telaCadastroFuncionario.getTfCpf().addFocusListener(addAcaoTfCpf());
		telaCadastroFuncionario.getTfDataNasc().addFocusListener(addAcaoTfDataNasc());
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == telaCadastroFuncionario.getBtCadastrar()) {
			cadastrarFuncionario();
		}
	}

	private void cadastrarFuncionario() {
		if (buscaFuncionario(telaCadastroFuncionario.getTfCpf().getText())) { // Testa se há um funcionário cadastrado
																				// com o cpf que foi digitado no campo
																				// CPF
			telaCadastroFuncionario.getLblAlerta()
					.setText("<html><p>Há uma conta já cadastrada no sistema com o CPF digitado!</p></html>");
			telaCadastroFuncionario.getLblAlerta().setVisible(true);
			somErro();
			new Thread(() -> {

				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				SwingUtilities.invokeLater(() -> {
					telaCadastroFuncionario.getLblAlerta().setVisible(false);
					telaCadastroFuncionario.repaint();
				});
			}).start();

		} else { // Caso não haja um cpf cadastrado com o cpf que foi digitado

			if (buscaEspacoVazio()) {// Testa se há um espaço obrigatório que não foi preenchido
				telaCadastroFuncionario.getLblAlerta().setText(
						"<html><p>Por favor preencha todos os espaços obrigatórios(*) corretamente!</p></html>");
				telaCadastroFuncionario.getLblAlerta().setVisible(true);

				somErro();

				new Thread(() -> {

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					SwingUtilities.invokeLater(() -> {
						telaCadastroFuncionario.getLblAlerta().setVisible(false);
						telaCadastroFuncionario.repaint();
					});
				}).start();

			} else {// Caso todos os espaços obrigatórios tenham sido preenchidos
				if (validarData() == true && validarCPF(telaCadastroFuncionario.getTfCpf().getText()) == true) { // Se a
																													// data
																													// e
																													// cpf
																													// forem
																													// válidos
					telaCadastroFuncionario.getTfCpf().setBorder(BorderFactory.createLineBorder(Color.BLACK));
					telaCadastroFuncionario.getTfDataNasc().setBorder(BorderFactory.createLineBorder(Color.BLACK));
					if (!telaCadastroFuncionario.getTfEmail().getText().trim().isEmpty()) {// Caso o campo do email não
																							// esteja vazio
						if (validarEmail(telaCadastroFuncionario.getTfEmail().getText())) {// Caso o email digitado seja
																							// válido
							telaCadastroFuncionario.getTfEmail().setBorder(BorderFactory.createLineBorder(Color.BLACK));
							Funcionario novoFunc = new Funcionario();
							novoFunc.setNomeCompleto(telaCadastroFuncionario.getTfNomeCompleto().getText());
							novoFunc.setCpf(telaCadastroFuncionario.getTfCpf().getText());
							String dataNasc = telaCadastroFuncionario.getTfDataNasc().getText();
							try {
								novoFunc.setDataNasc(dataNasc);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							novoFunc.setCategoriaFunc(selecionaCategoria());
							novoFunc.setEmail(telaCadastroFuncionario.getTfEmail().getText());
							novoFunc.setSenha("feirao." + novoFunc.getCpf().replace(".", "").replace("-", ""));
							salvarFuncionario(novoFunc);
							App.listaFuncionarios.add(novoFunc);
							somCadastrar();
							JOptionPane.showMessageDialog(null,
									"Funcionário cadastrado com sucesso! Senha: " + novoFunc.getSenha());
							reiniciarCampos();
						} else {// Caso o email digitado não seja válido
							somErro();
							telaCadastroFuncionario.getTfEmail().setBorder(BorderFactory.createLineBorder(Color.RED));
							telaCadastroFuncionario.getLblAlerta().setText("Por favor digite um Email válido!!");
							telaCadastroFuncionario.getLblAlerta().setVisible(true);

							somErro();

							new Thread(() -> {

								try {
									Thread.sleep(5000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}

								SwingUtilities.invokeLater(() -> {
									telaCadastroFuncionario.getTfEmail()
											.setBorder(BorderFactory.createLineBorder(Color.BLACK));
									telaCadastroFuncionario.getLblAlerta().setVisible(false);
									telaCadastroFuncionario.repaint();
								});
							}).start();
						}
					} else {// Caso o campo do email esteja vazio
						Funcionario novoFunc = new Funcionario();
						novoFunc.setNomeCompleto(telaCadastroFuncionario.getTfNomeCompleto().getText());
						novoFunc.setCpf(telaCadastroFuncionario.getTfCpf().getText());
						String dataNasc = telaCadastroFuncionario.getTfDataNasc().getText();
						try {
							novoFunc.setDataNasc(dataNasc);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						novoFunc.setCategoriaFunc(selecionaCategoria());
						novoFunc.setSenha("feirao." + novoFunc.getCpf().replace(".", "").replace("-", ""));
						salvarFuncionario(novoFunc);
						App.listaFuncionarios.add(novoFunc);
						somCadastrar();
						JOptionPane.showMessageDialog(null,
								"Funcionário cadastrado com sucesso! Senha: " + novoFunc.getSenha());
						reiniciarCampos();
					}
				} else { // Se a data e CPF forem inválidos
					somErro();
					if (validarCPF(telaCadastroFuncionario.getTfCpf().getText()) == false) {
						telaCadastroFuncionario.getTfCpf().setBorder(BorderFactory.createLineBorder(Color.RED));
					}
					if (validarData() == false) {
						telaCadastroFuncionario.getTfDataNasc().setBorder(BorderFactory.createLineBorder(Color.RED));
					}
					telaCadastroFuncionario.getLblAlerta().setText(
							"<html><p>Há algum dado inválido! O cadastro foi rejeitado, corrija e tente novamente!</p></html>");
					telaCadastroFuncionario.getLblAlerta().setVisible(true);

					somErro();

					new Thread(() -> {

						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						SwingUtilities.invokeLater(() -> {
							telaCadastroFuncionario.getTfCpf().setBorder(BorderFactory.createLineBorder(Color.BLACK));
							telaCadastroFuncionario.getTfDataNasc()
									.setBorder(BorderFactory.createLineBorder(Color.BLACK));
							telaCadastroFuncionario.getLblAlerta().setVisible(false);
							telaCadastroFuncionario.repaint();
						});
					}).start();
				}
			}
		}
	}

	private boolean buscaFuncionario(String cpf) {
		DAOFuncionario daoFuncionario = new DAOFuncionario();
		try {
			Funcionario funcionario = daoFuncionario.BuscarFuncionario(cpf);
			return funcionario != null;
		} catch (Exception e) {
			System.err.println("Erro ao buscar funcionario: " + e.getMessage());
		}
		return false;
	}

	private void salvarFuncionario(Funcionario funcionario) {
		DAOFuncionario daoFuncionario = new DAOFuncionario();
		try {
			if (!telaCadastroFuncionario.getTfEmail().getText().trim().isEmpty()) {
				funcionario.setEmail(telaCadastroFuncionario.getTfEmail().getText());
			} else {
				funcionario.setEmail(null);
			}
			daoFuncionario.SalvarFuncionario(funcionario);
		} catch (Exception e) {
			System.err.println("Erro ao salvar funcionario: " + e.getMessage());
		}
	}

	// --------------------------------- Verifica de forma genérica se o conteúdo
	// digitado no campo Email possui o formato de um email
	private boolean validarEmail(String email) {
		Pattern p = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	private void reiniciarCampos() {
		telaCadastroFuncionario.getTfCpf().setText(null);
		telaCadastroFuncionario.getTfDataNasc().setText(null);
		telaCadastroFuncionario.getTfNomeCompleto().setText(null);
		telaCadastroFuncionario.getTfEmail().setText(null);
	}

	private int selecionaCategoria() {
		return telaCadastroFuncionario.getCbxCategoriaFunc().getSelectedItem().equals("Atendente") ? 0 : 1;
	}

	private boolean validarData() {
		SimpleDateFormat dataValida = new SimpleDateFormat("dd/MM/yyyy");
		dataValida.setLenient(false);
		try {
			Date dataValidada = dataValida.parse(telaCadastroFuncionario.getTfDataNasc().getText());
			String[] data = dataValida.format(dataValidada).split("/");
			if (data.length == 3) {
				telaCadastroFuncionario.getTfDataNasc().setBorder(BorderFactory.createLineBorder(Color.BLACK));
				return true;
			}
		} catch (ParseException e) {
			telaCadastroFuncionario.getTfDataNasc().setBorder(BorderFactory.createLineBorder(Color.RED));
		}
		return false;
	}

	// --------------------------------- Verifica se os campos obrigatórios estão
	// vazios (Considerando os campos formatados)
	private boolean buscaEspacoVazio() {
		if (telaCadastroFuncionario.getTfNomeCompleto().getText().trim().isEmpty()
				|| telaCadastroFuncionario.getTfCpf().getText().trim().isEmpty()
				|| telaCadastroFuncionario.getTfCpf().getText().trim().isEmpty()
				|| (telaCadastroFuncionario.getTfCpf().getText().trim().compareTo("   .   .   -  ") == 14)
				|| telaCadastroFuncionario.getTfDataNasc().getText().trim().isEmpty()
				|| telaCadastroFuncionario.getTfDataNasc().getText().trim().compareTo("  /  /    ") == 15) {
			return true;
		} else {
			return false;
		}
	}

	// --------------------------------- Verifica se o cpf digitado é válido
	private boolean validarCPF(String cpf) {
		cpf = cpf.replaceAll("[^0-9]", "");

		if (cpf.length() != 11) {
			return false;
		}

		boolean todosDigitosIguais = true;
		for (int i = 1; i < 11; i++) {
			if (cpf.charAt(i) != cpf.charAt(0)) {
				todosDigitosIguais = false;
				break;
			}
		}
		if (todosDigitosIguais) {
			return false;
		}

		int soma = 0;
		for (int i = 0; i < 9; i++) {
			soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
		}
		int resto = soma % 11;
		int digitoVerificador1 = (resto < 2) ? 0 : (11 - resto);

		if (Character.getNumericValue(cpf.charAt(9)) != digitoVerificador1) {
			return false;
		}

		soma = 0;
		for (int i = 0; i < 10; i++) {
			soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
		}
		resto = soma % 11;
		int digitoVerificador2 = (resto < 2) ? 0 : (11 - resto);
		if (Character.getNumericValue(cpf.charAt(10)) != digitoVerificador2) {
			return false;
		}
		return true;
	}

	private void somCadastrar() {
		try {
			URL url = getClass().getResource("/Audio/cadastrar.wav");
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
			URL url = getClass().getResource("/Audio/erro.wav");
			AudioInputStream stream = AudioSystem.getAudioInputStream(url);
			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public FocusListener addAcaoTfCpf() {
		return new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if (telaCadastroFuncionario.getTfCpf().getText().trim().isEmpty()) {
					telaCadastroFuncionario.getTfCpf().setForeground(Color.BLACK);
					try {
						telaCadastroFuncionario.getTfCpf()
								.setFormatterFactory(new DefaultFormatterFactory(getMascaraCpf()));

					} catch (ParseException ex) {
						ex.printStackTrace();
					}
				}

			}

			public void focusLost(FocusEvent e) {
				telaCadastroFuncionario.getTfCpf().setFormatterFactory(null);// ||
																				// telaCadastroFuncionario.getTfCpf().getText().equals("___.___.___-__")
				if (telaCadastroFuncionario.getTfCpf().getText().trim().isEmpty()
						|| telaCadastroFuncionario.getTfCpf().getText().trim().compareTo("___.___.___-__") == 0) {
					telaCadastroFuncionario.getTfCpf().setForeground(new Color(114, 114, 114));
					telaCadastroFuncionario.getTfCpf().setText("");
				}
			}
		};
	}

	/*
	  
	*/
	public static boolean apagarData(String dataString) {
		try {
			String[] partesData = dataString.trim().split("/");
			if (partesData.length != 3) {
				return true; // String não contém exatamente 3 partes separadas por barras
			}

			String diaStr = partesData[0].trim();
			String mesStr = partesData[1].trim();
			String anoStr = partesData[2].trim();

			if (diaStr.isEmpty() || mesStr.isEmpty() || anoStr.isEmpty()) {
				return true; // Uma das partes está vazia
			}

			int dia = Integer.parseInt(diaStr);
			int mes = Integer.parseInt(mesStr);
			int ano = Integer.parseInt(anoStr);

			if (dia <= 0 || dia > 31 || mes <= 0 || mes > 12 || ano <= 0) {
				return false; // Valores inválidos para dia, mês ou ano
			} else {
				return false; // Valores válidos
			}
		} catch (NumberFormatException e) {
			return true; // Se ocorrer um erro de formatação, retorna true
		}
	}

	public FocusListener addAcaoTfDataNasc() {
		return (FocusListener) new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if (telaCadastroFuncionario.getTfDataNasc().getText().trim().isEmpty()) {
					telaCadastroFuncionario.getTfDataNasc().setText("");
					telaCadastroFuncionario.getTfDataNasc().setForeground(Color.BLACK);
					try {
						telaCadastroFuncionario.getTfDataNasc()
								.setFormatterFactory(new DefaultFormatterFactory(getMascaraDataNasc()));
					} catch (ParseException ex) {
						ex.printStackTrace();
					}
				}

			}

			public void focusLost(FocusEvent e) {
				telaCadastroFuncionario.getTfDataNasc().setFormatterFactory(null);
				String dataValidadeText = telaCadastroFuncionario.getTfDataNasc().getText().trim();
				if (dataValidadeText.isEmpty() || dataValidadeText.compareTo("  /  /    ") == 0
						|| apagarData(dataValidadeText)) {
					telaCadastroFuncionario.getTfDataNasc().setText("");
					telaCadastroFuncionario.getTfDataNasc().setForeground(new Color(114, 114, 114));
				}
			}
		};
	}

	private MaskFormatter getMascaraCpf() throws ParseException {
		MaskFormatter mf = new MaskFormatter("###.###.###-##");
		mf.setPlaceholderCharacter('_');
		return mf;
	}

	private MaskFormatter getMascaraDataNasc() throws ParseException {
		MaskFormatter mf = new MaskFormatter("##/##/####");
		mf.setPlaceholderCharacter('_');
		return mf;
	}
}
