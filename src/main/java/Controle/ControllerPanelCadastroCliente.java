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
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import Aplicacao.App;
import Modelo.Cliente;
import Monitoramento.DAOCliente;
import Visao.PanelCadastroCliente;

public class ControllerPanelCadastroCliente implements ActionListener {

	PanelCadastroCliente telaCadastroCliente;
	private FocusListener adaptLimCredito;
	int auxCredito = 0;

	public ControllerPanelCadastroCliente(PanelCadastroCliente telaCadastroCliente) {
		this.telaCadastroCliente = telaCadastroCliente;
		addEventos();
	}

	private void addEventos() {
		telaCadastroCliente.getBtCadastrar().addActionListener(this);
		telaCadastroCliente.getBtEditarCliente().addActionListener(this);
		telaCadastroCliente.getTfCpf().addFocusListener(addAcaoTfCpf());
		telaCadastroCliente.getTfDataNasc().addFocusListener(addAcaoTfDataNasc());
		telaCadastroCliente.getTfLimCredito().addFocusListener(addAcaoTfLimCred());
		telaCadastroCliente.getCbxLimCredPadrao().addActionListener(this);
	}

	public PanelCadastroCliente getTela() {
		return telaCadastroCliente;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == telaCadastroCliente.getBtCadastrar()) {
			cadastrarCliente();
		}
		if (e.getSource() == telaCadastroCliente.getBtEditarCliente()) {
			somClique();
		}
		if (e.getSource() == telaCadastroCliente.getCbxLimCredPadrao()) {

			if (telaCadastroCliente.getCbxLimCredPadrao().isSelected() == false) {
				telaCadastroCliente.getTfLimCredito().setEnabled(true);
				auxCredito = 1;
			} else {
				telaCadastroCliente.getTfLimCredito().setEnabled(false);
				auxCredito = 0;
			}

		}
	}

	private void cadastrarCliente() {

		if (buscaClientePorCpf(telaCadastroCliente.getTfCpf().getText())) {
			telaCadastroCliente.getLblAlerta()
					.setText("<html><p>Há uma conta já cadastrada no sistema com o CPF digitado!</p></html>");
			telaCadastroCliente.getLblAlerta().setVisible(true);
			somErro();
			new Thread(() -> {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				SwingUtilities.invokeLater(() -> {
					telaCadastroCliente.getLblAlerta().setVisible(false);
					telaCadastroCliente.repaint();
				});
			}).start();
		} else {// Caso não haja um cpf cadastrado com o cpf que foi digitado
			if (buscaEspacoVazio()) {// Testa se há um espaço obrigatório que não foi preenchido
				telaCadastroCliente.getLblAlerta().setText(
						"<html><p>Por favor preencha todos os espaços obrigatórios(*) corretamente!</p></html>");
				telaCadastroCliente.getLblAlerta().setVisible(true);

				somErro();

				new Thread(() -> {

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					SwingUtilities.invokeLater(() -> {
						telaCadastroCliente.getLblAlerta().setVisible(false);
						telaCadastroCliente.repaint();
					});
				}).start();

			} else {// Caso todos os espaços obrigatórios tenham sido preenchidos

				if (validarData() == true && validarCPF(telaCadastroCliente.getTfCpf().getText())) {
					telaCadastroCliente.getTfCpf().setBorder(BorderFactory.createLineBorder(Color.BLACK));
					telaCadastroCliente.getTfDataNasc().setBorder(BorderFactory.createLineBorder(Color.BLACK));
					if (!telaCadastroCliente.getTfEmail().getText().trim().isEmpty()) {// Caso o campo do email não
																						// esteja vazio
						if (validarEmail(telaCadastroCliente.getTfEmail().getText())) {
							telaCadastroCliente.getTfEmail().setBorder(BorderFactory.createLineBorder(Color.BLACK));
							Cliente novoCliente = new Cliente();
							novoCliente.setNomeCompleto(telaCadastroCliente.getTfNomeCompleto().getText());
							novoCliente.setCpf(telaCadastroCliente.getTfCpf().getText());
							String dataNasc = telaCadastroCliente.getTfDataNasc().getText();
							try {
								novoCliente.setDataNasc(dataNasc);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							novoCliente.setLimCredito(verificaLimCredito());
							novoCliente.setEmail(telaCadastroCliente.getTfEmail().getText());
							salvarDadosCliente(novoCliente);
							App.listaClientes.add(novoCliente);
							somCadastrar();
							reiniciarCampos();
						} else {
							telaCadastroCliente.getTfEmail().setBorder(BorderFactory.createLineBorder(Color.RED));
							telaCadastroCliente.getLblAlerta().setText("Por favor digite um Email válido!!");
							telaCadastroCliente.getLblAlerta().setVisible(true);

							somErro();

							new Thread(() -> {

								try {
									Thread.sleep(5000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}

								SwingUtilities.invokeLater(() -> {
									telaCadastroCliente.getTfEmail()
											.setBorder(BorderFactory.createLineBorder(Color.BLACK));
									telaCadastroCliente.getLblAlerta().setVisible(false);
									telaCadastroCliente.repaint();
								});
							}).start();
						}
					} else {
						Cliente novoCliente = new Cliente();
						novoCliente.setNomeCompleto(telaCadastroCliente.getTfNomeCompleto().getText());
						novoCliente.setCpf(telaCadastroCliente.getTfCpf().getText());
						String dataNasc = telaCadastroCliente.getTfDataNasc().getText();
						try {
							novoCliente.setDataNasc(dataNasc);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						novoCliente.setLimCredito(verificaLimCredito());
						salvarDadosCliente(novoCliente);
						App.listaClientes.add(novoCliente);
						somCadastrar();
						reiniciarCampos();
					}
				} else {
					somErro();
					if (validarCPF(telaCadastroCliente.getTfCpf().getText()) == false) {
						telaCadastroCliente.getTfCpf().setBorder(BorderFactory.createLineBorder(Color.RED));
					}
					if (validarData() == false) {
						telaCadastroCliente.getTfDataNasc().setBorder(BorderFactory.createLineBorder(Color.RED));
					}
					telaCadastroCliente.getLblAlerta().setText(
							"<html><p>Há algum dado inválido! O cadastro foi rejeitado, corrija e tente novamente!</p></html>");
					telaCadastroCliente.getLblAlerta().setVisible(true);

					somErro();

					new Thread(() -> {

						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

						SwingUtilities.invokeLater(() -> {
							telaCadastroCliente.getTfCpf().setBorder(BorderFactory.createLineBorder(Color.BLACK));
							telaCadastroCliente.getTfDataNasc().setBorder(BorderFactory.createLineBorder(Color.BLACK));
							telaCadastroCliente.getLblAlerta().setVisible(false);
							telaCadastroCliente.repaint();
						});
					}).start();
				}
			}
		}
	}

	// --------------------------------- Verifica se há um cliente cadastrado
	private boolean buscaClientePorCpf(String cpfBuscado) {
		DAOCliente daoCliente = new DAOCliente();
		try {
			Cliente cliente = daoCliente.buscarCliente(cpfBuscado);
			return cliente != null;
		} catch (Exception e) {
			System.err.println("Erro ao buscar cliente: " + e.getMessage());
		}
		return false;
	}

	private void salvarDadosCliente(Cliente cliente) {
		DAOCliente daoCliente = new DAOCliente();
		try {
			if (!telaCadastroCliente.getTfEmail().getText().trim().isEmpty()) {
				cliente.setEmail(telaCadastroCliente.getTfEmail().getText());
			} else {
				cliente.setEmail(null);
			}
			daoCliente.salvarCliente(cliente);
		} catch (Exception e) {
			System.out.println("Erro ao salvar cliente: " + e.getMessage());
		}
	}

	private void reiniciarCampos() {
		telaCadastroCliente.getTfCpf().setText(null);
		telaCadastroCliente.getTfDataNasc().setText(null);
		telaCadastroCliente.getTfNomeCompleto().setText(null);
		telaCadastroCliente.getTfEmail().setText(null);
		telaCadastroCliente.getTfLimCredito().setText(null);
		telaCadastroCliente.getCbxLimCredPadrao().setSelected(false);
	}

	private double verificaLimCredito() {
		if (auxCredito == 1) {
			return 500.00;
		} else if (auxCredito == 2) {
			try {
				telaCadastroCliente.getTfLimCredito().setBorder(BorderFactory.createLineBorder(Color.BLACK));
				return Double.parseDouble(telaCadastroCliente.getTfLimCredito().getText());
			} catch (NumberFormatException e) {
				telaCadastroCliente.getTfLimCredito().setBorder(BorderFactory.createLineBorder(Color.RED));
			}
		}
		return 500.00;
	}

	// --------------------------------- Verifica se a Data de Nascimento digitada é
	// válida
	private boolean validarData() {
		SimpleDateFormat dataValida = new SimpleDateFormat("dd/MM/yyyy");
		dataValida.setLenient(false);
		try {
			Date dataValidada = dataValida.parse(telaCadastroCliente.getTfDataNasc().getText());
			String[] data = dataValida.format(dataValidada).split("/");
			if (data.length == 3) {
				telaCadastroCliente.getTfDataNasc().setBorder(BorderFactory.createLineBorder(Color.BLACK));
				return true;
			}
		} catch (ParseException e) {
			telaCadastroCliente.getTfDataNasc().setBorder(BorderFactory.createLineBorder(Color.RED));
		}
		return false;
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

	// --------------------------------- Verifica de forma genérica se o conteúdo
	// digitado no campo Email possui o formato de um email
	private boolean validarEmail(String email) {
		Pattern p = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	// --------------------------------- Verifica se os campos obrigatórios estão
	// vazios (Considerando os campos formatados)
	private boolean buscaEspacoVazio() {
		if (telaCadastroCliente.getTfNomeCompleto().getText().trim().isEmpty()
				|| telaCadastroCliente.getTfCpf().getText().trim().isEmpty()
				|| telaCadastroCliente.getTfCpf().getText().trim().isEmpty()
				|| (telaCadastroCliente.getTfCpf().getText().trim().compareTo("   .   .   -  ") == 14)
				|| telaCadastroCliente.getTfDataNasc().getText().trim().isEmpty()
				|| telaCadastroCliente.getTfDataNasc().getText().trim().compareTo("  /  /    ") == 15) {
			return true;
		} else {
			return false;
		}
	}

	// --------------------------------- Controla a relação do Campo Limite de
	// credito com o combobox limite de credito
	private FocusListener addAcaoTfLimCred() {
		if (adaptLimCredito == null) {
			adaptLimCredito = new FocusAdapter() {

				public void focusGained(FocusEvent e) {
					telaCadastroCliente.getCbxLimCredPadrao().setEnabled(false);
					auxCredito = 2;
				}

				public void focusLost(FocusEvent e) {
					if (telaCadastroCliente.getTfLimCredito().getText().trim().isEmpty()) {
						telaCadastroCliente.getCbxLimCredPadrao().setEnabled(true);
						auxCredito = 0;
					}
				}

			};
		}
		return adaptLimCredito;
	}

	// --------------------------------- Adiciona e retira a formatação do campo
	// Data de Nascimento através do foco
	private MaskFormatter getMascaraDataNasc() throws ParseException {
		MaskFormatter mascara = new MaskFormatter("##/##/####");
		mascara.setPlaceholderCharacter(' ');
		return mascara;
	}

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
				if (telaCadastroCliente.getTfDataNasc().getText().trim().isEmpty()) {
					telaCadastroCliente.getTfDataNasc().setText("");
					telaCadastroCliente.getTfDataNasc().setForeground(Color.BLACK);
					try {
						telaCadastroCliente.getTfDataNasc()
								.setFormatterFactory(new DefaultFormatterFactory(getMascaraDataNasc()));
					} catch (ParseException ex) {
						ex.printStackTrace();
					}
				}

			}

			public void focusLost(FocusEvent e) {
				telaCadastroCliente.getTfDataNasc().setFormatterFactory(null);
				String dataValidadeText = telaCadastroCliente.getTfDataNasc().getText().trim();
				if (dataValidadeText.isEmpty() || dataValidadeText.compareTo("  /  /    ") == 0
						|| apagarData(dataValidadeText)) {
					telaCadastroCliente.getTfDataNasc().setText("");
					telaCadastroCliente.getTfDataNasc().setForeground(new Color(114, 114, 114));
				}
			}
		};
	}

	private MaskFormatter getMascaraCpf() throws ParseException {
		MaskFormatter mf = new MaskFormatter("###.###.###-##");
		mf.setPlaceholderCharacter('_');
		return mf;
	}

	public FocusListener addAcaoTfCpf() {
		return new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if (telaCadastroCliente.getTfCpf().getText().trim().isEmpty()) {
					telaCadastroCliente.getTfCpf().setForeground(Color.BLACK);
					try {
						telaCadastroCliente.getTfCpf()
								.setFormatterFactory(new DefaultFormatterFactory(getMascaraCpf()));

					} catch (ParseException ex) {
						ex.printStackTrace();
					}
				}

			}

			public void focusLost(FocusEvent e) {
				telaCadastroCliente.getTfCpf().setFormatterFactory(null);// ||
																			// telaCadastroFuncionario.getTfCpf().getText().equals("___.___.___-__")
				if (telaCadastroCliente.getTfCpf().getText().trim().isEmpty()
						|| telaCadastroCliente.getTfCpf().getText().trim().compareTo("___.___.___-__") == 0) {
					telaCadastroCliente.getTfCpf().setForeground(new Color(114, 114, 114));
					telaCadastroCliente.getTfCpf().setText("");
				}
			}
		};
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

	private void somCadastrar() {
		try {
			URL url = App.class.getResource("/Audio/cadastrar.wav");
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

}
