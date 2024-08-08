package Controle;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import Aplicacao.App;
import Modelo.Produto;
import Monitoramento.DAOProduto;
import Visao.PanelEditarProduto;
import Visao.TelaCadastroProdutos;
import Visao.TelaInicial;

public class ControllerTelaCadastroProdutos implements ActionListener {

	TelaCadastroProdutos telaCadastroProdutos;
	private FocusListener adaptTxtDataFabricacao;
	private FocusListener adaptTxtDataValidade;
	int auxTrocaTela;

	public ControllerTelaCadastroProdutos(TelaCadastroProdutos telaCadastroProdutos, int aux) {
		this.telaCadastroProdutos = telaCadastroProdutos;
		auxTrocaTela = aux;
		addEventos();
	}

	private void addEventos() {
		telaCadastroProdutos.getBtTrocarModo().addActionListener(this);
		telaCadastroProdutos.getBtModoEdicaoBuscar().addActionListener(this);
		telaCadastroProdutos.getBtCadastrarProduto().addActionListener(this);
		telaCadastroProdutos.getTfDataFabricacao().addFocusListener(addAcaoTfDataFabricacao());
		telaCadastroProdutos.getTfDataValidade().addFocusListener(addAcaoTfDataValidade());
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == telaCadastroProdutos.getBtTrocarModo()) {
			somTroca();
			auxTrocaTela++;
			trocarTela();
		}

		if (e.getSource() == telaCadastroProdutos.getBtModoEdicaoBuscar()) {
			somClique();
			if (!telaCadastroProdutos.getTfModoEdicaoCodigo().getText().trim().isEmpty()) {
				Produto p = buscarProduto();
				if (p != null) {
					PanelEditarProduto panelEditarProduto = new PanelEditarProduto();
					ControllerPanelEditarProduto ctrlPanelEditarProduto = new ControllerPanelEditarProduto(
							panelEditarProduto, p);
					telaCadastroProdutos.setExibirProdutoBuscado(ctrlPanelEditarProduto.getTela());
					telaCadastroProdutos.getExibirProdutoBuscado().repaint();
					telaCadastroProdutos.getExibirProdutoBuscado().revalidate();
				} else {
					telaCadastroProdutos.getTfModoEdicaoCodigo().setBorder(BorderFactory.createLineBorder(Color.RED));
					new Thread(() -> {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException ec) {
							ec.printStackTrace();
						}
						telaCadastroProdutos.getTfModoEdicaoCodigo()
								.setBorder(BorderFactory.createLineBorder(Color.BLACK));
						telaCadastroProdutos.repaint();
					}).start();
				}
			}
		}
		
		if (e.getSource() == telaCadastroProdutos.getBtCadastrarProduto()) {
			cadastrarProduto();
		}
	}

	private Produto buscarProduto() {
		DAOProduto daoProduto = new DAOProduto();
		String codProduto = converterParaMaiusculas(telaCadastroProdutos.getTfModoEdicaoCodigo().getText().trim());
		Produto p = daoProduto.buscarProdutoPorCodigo(codProduto);
		return p;
	}

	private void cadastrarProduto() {
		// Verifica se há campos não preenchidos
		if (espacosVazios()) {
			mostrarMensagemDeErro("Preencha todos os espaços!");
		} else {
			// Verifica se o produto não existe no arquivo
			if (!buscaCodigoProduto(converterParaMaiusculas(telaCadastroProdutos.getTfCodigoProduto().getText()))) {
				boolean controleFormato = false;

				// Valida a data de fabricação
				if (!validarData(telaCadastroProdutos.getTfDataFabricacao().getText())) {
					controleFormato = true;
					mostrarErroCampo(telaCadastroProdutos.getTfDataFabricacao());
				}

				// Valida a data de validade
				if (!validarData(telaCadastroProdutos.getTfDataValidade().getText())) {
					controleFormato = true;
					mostrarErroCampo(telaCadastroProdutos.getTfDataValidade());
				}

				// Se formato das datas estiver correto
				if (!controleFormato) {
					boolean controlePrazo = false;

					// Verifica se a data de fabricação não é futura
					if (dataFutura(telaCadastroProdutos.getTfDataFabricacao().getText())) {
						controlePrazo = true;
						mostrarErroCampo(telaCadastroProdutos.getTfDataFabricacao());
					}

					// Verifica se a data de validade é futura
					if (!dataFutura(telaCadastroProdutos.getTfDataValidade().getText())) {
						controlePrazo = true;
						mostrarErroCampo(telaCadastroProdutos.getTfDataValidade());
					}

					// Se prazos das datas estiverem corretos
					if (!controlePrazo) {
						Produto novoProduto = criarNovoProduto();
						if (novoProduto != null) {
							salvarProduto(novoProduto);
							App.listaProdutos.add(novoProduto);
							somCadastrar();
							reiniciaComponentes();
						} else {
							telaCadastroProdutos.getLblAlerta().setText(
									"<html>Produto não cadastrado! Foi encontrado um erro no processo!</html>");
							telaCadastroProdutos.getLblAlerta().setVisible(true);
							somErro();
							new Thread(() -> {
								try {
									Thread.sleep(5000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								SwingUtilities.invokeLater(() -> {
									telaCadastroProdutos.getLblAlerta().setVisible(false);
									telaCadastroProdutos.getLblAlerta().setText("");
									telaCadastroProdutos.repaint();
								});
							}).start();
						}
					}
				}
			} else {
				mostrarErroProdutoExistente();
			}
		}
	}

	private Produto criarNovoProduto() {
		try {
			Produto novoProduto = new Produto();
			novoProduto.setCodProduto(converterParaMaiusculas(telaCadastroProdutos.getTfCodigoProduto().getText()));
			novoProduto.setNomeProduto(telaCadastroProdutos.getTfNomeProduto().getText());
			novoProduto.setCategoriaProduto((String) telaCadastroProdutos.getCbxCategoriaProduto().getSelectedItem());
			novoProduto.setDtFabProduto(telaCadastroProdutos.getTfDataFabricacao().getText());
			novoProduto.setDtValProduto(telaCadastroProdutos.getTfDataValidade().getText());
			novoProduto.setPrecoCaixaProduto(Double.parseDouble(formatarNumero(telaCadastroProdutos.getTfPrecoCompra().getText())));
			novoProduto.setPrecoUnidProduto(Double.parseDouble(formatarNumero(telaCadastroProdutos.getTfPrecoVendaProduto().getText())));
			novoProduto.setQtdCaixaProduto(Integer.parseInt(telaCadastroProdutos.getTfQtdCompraProduto().getText()));
			novoProduto.setQtdEstoqueProduto(Integer.parseInt(telaCadastroProdutos.getTfQtdEstoqueProduto().getText()));
			novoProduto.setQtdMinEstoqueProduto(
					Integer.parseInt(telaCadastroProdutos.getTfQtdMinEstoqueProduto().getText()));
			novoProduto.setAlertaMinEstoque(false);
			novoProduto.setQuantidadeAtual(novoProduto.getQtdEstoqueProduto() * novoProduto.getQtdCaixaProduto());
			return novoProduto;
		} catch (Exception e) {
			return null;
		}
	}

	private void salvarProduto(Produto produto) {
		DAOProduto daoProduto = new DAOProduto();
		try {
			daoProduto.salvarProduto(produto);
		} catch (Exception e) {
			System.err.println("Erro ao salvar produto: " + e.getMessage());
		}
	}

	private void mostrarMensagemDeErro(String mensagem) {
		telaCadastroProdutos.getLblAlerta().setText("<html>" + mensagem + "</html>");
		telaCadastroProdutos.getLblAlerta().setVisible(true);
		somErro();
		new Thread(() -> {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			SwingUtilities.invokeLater(() -> {
				telaCadastroProdutos.getLblAlerta().setVisible(false);
				telaCadastroProdutos.getLblAlerta().setText("");
				telaCadastroProdutos.repaint();
			});
		}).start();
	}

	private void mostrarErroCampo(JTextField campo) {
		campo.setBorder(BorderFactory.createLineBorder(Color.RED));
		somErro();
		new Thread(() -> {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			SwingUtilities.invokeLater(() -> {
				campo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			});
		}).start();
	}

	private void mostrarErroProdutoExistente() {
		telaCadastroProdutos.getTfCodigoProduto().setBorder(BorderFactory.createLineBorder(Color.RED));
		telaCadastroProdutos.getLblAlerta().setText("<html>Já existe um produto com este código cadastrado!</html>");
		telaCadastroProdutos.getLblAlerta().setVisible(true);
		somErro();
		new Thread(() -> {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			SwingUtilities.invokeLater(() -> {
				telaCadastroProdutos.getTfCodigoProduto().setBorder(BorderFactory.createLineBorder(Color.BLACK));
				telaCadastroProdutos.getLblAlerta().setVisible(false);
				telaCadastroProdutos.getLblAlerta().setText("");
				telaCadastroProdutos.repaint();
			});
		}).start();
	}

	public static String formatarNumero(String numero) {
		// Remove os pontos
		String semPontos = numero.replace(".", "");
		// Substitui as vírgulas por pontos
		String numeroFormatado = semPontos.replace(",", ".");
		return numeroFormatado;
	}

	public static boolean validarData(String dataString) {
		if (dataString == null || !dataString.matches("\\d{2}/\\d{2}/\\d{4}")) {
			return false; // Verifica se a string está no formato correto
		}

		try {
			DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate data = LocalDate.parse(dataString, formatoData); // Tenta fazer o parse da data

			int dia = data.getDayOfMonth();
			int mes = data.getMonthValue();
			int ano = data.getYear();

			if (ano < 1850 || ano > 2100) { // Ano anterior à 1850 e ano posterior a 2100
				return false;
			}

			// Verifica dias válidos para cada mês
			if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) {
				return false; // Meses com 30 dias
			}

			if (mes == 2) {
				if (ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0)) {
					if (dia > 29) {
						return false; // Fevereiro em ano bissexto, mas dia maior que 29
					}
				} else {
					if (dia > 28) {
						return false; // Fevereiro em ano não bissexto, mas dia maior que 28
					}
				}
			}

			return true; // Se chegou aqui, a data é válida
		} catch (DateTimeParseException e) {
			return false; // Se houver erro de parse, a data é inválida
		}
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

	public static boolean dataFutura(String dataString) {
		try {
			DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate dataAtual = LocalDate.now();
			LocalDate data = LocalDate.parse(dataString, formatoData);
			// Verifica se a data fornecida é após a data atual
			return data.isAfter(dataAtual);
		} catch (DateTimeParseException e) {
			// Se houver erro de parse, a data é inválida
			return false;
		}
	}

	public static boolean dataAtualOuAnterior(String dataString) {
		try {
			DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate dataAtual = LocalDate.now();
			LocalDate data = LocalDate.parse(dataString, formatoData);

			// Se a data da string for igual ou anterior à data atual, retorna verdadeiro
			return !data.isAfter(dataAtual);
		} catch (DateTimeParseException e) {
			// Se houver erro de parse, a data é inválida
			return false;
		}
	}

	private boolean buscaCodigoProduto(String codigo) {
		DAOProduto daoProduto = new DAOProduto();
		Produto p = daoProduto.buscarProdutoPorCodigo(telaCadastroProdutos.getTfCodigoProduto().getText());
		if (p != null)
			return true;
		return false;
	}

	private String converterParaMaiusculas(String codigo) {
		StringBuilder codigoMaiusculo = new StringBuilder();

		for (int i = 0; i < codigo.length(); i++) {
			char caractere = codigo.charAt(i);
			if (Character.isLetter(caractere)) {
				codigoMaiusculo.append(Character.toUpperCase(caractere));
			} else {
				codigoMaiusculo.append(caractere);
			}
		}
		return codigoMaiusculo.toString();
	}

	private void reiniciaComponentes() {
		SwingUtilities.invokeLater(() -> {
			telaCadastroProdutos.getTfNomeProduto().setText("");
			telaCadastroProdutos.getTfCodigoProduto().setText("");
			telaCadastroProdutos.getTfDataFabricacao().setText("");
			telaCadastroProdutos.getTfDataValidade().setText("");
			telaCadastroProdutos.getTfPrecoCompra().setText("0");
			telaCadastroProdutos.getTfQtdCompraProduto().setText("0");
			telaCadastroProdutos.getTfQtdEstoqueProduto().setText("0");
			telaCadastroProdutos.getTfQtdMinEstoqueProduto().setText("0");
			telaCadastroProdutos.getTfPrecoVendaProduto().setText("0");

			// Após limpar os campos, formate os campos de preço de compra e venda
			telaCadastroProdutos.formatarPrecoCompra();
			telaCadastroProdutos.formatarPrecoVenda();
		});
	}

	private boolean espacosVazios() {
		if (telaCadastroProdutos.getTfNomeProduto().getText().trim().isEmpty()
				|| telaCadastroProdutos.getTfCodigoProduto().getText().trim().isEmpty()
				|| telaCadastroProdutos.getTfDataFabricacao().getText().trim().isEmpty()
				|| telaCadastroProdutos.getTfDataFabricacao().getText().trim().compareTo("  /  /    ") == 15
				|| telaCadastroProdutos.getTfDataValidade().getText().trim().isEmpty()
				|| telaCadastroProdutos.getTfDataValidade().getText().trim().compareTo("  /  /    ") == 15
				|| telaCadastroProdutos.getTfPrecoCompra().getText().trim().isEmpty()
				|| telaCadastroProdutos.getTfQtdCompraProduto().getText().trim().isEmpty()
				|| telaCadastroProdutos.getTfQtdEstoqueProduto().getText().trim().isEmpty()
				|| telaCadastroProdutos.getTfQtdMinEstoqueProduto().getText().trim().isEmpty()
				|| telaCadastroProdutos.getTfPrecoVendaProduto().getText().trim().isEmpty()) {
			return true;
		}
		return false;
	}

	private void trocarTela() {
		if (auxTrocaTela % 2 == 0) {
			telaCadastroProdutos.getLblBackground()
					.setIcon(new ImageIcon(TelaInicial.class.getResource("/Imagens/BackgroundModoEdicaoProduto.png")));
			telaCadastroProdutos.getBtModoEdicaoBuscar()
					.setIcon(new ImageIcon(TelaInicial.class.getResource("/Imagens/btPesquisar.png")));
			telaCadastroProdutos.getBtCadastrarProduto()
					.setIcon(new ImageIcon(TelaInicial.class.getResource("/Imagens/btCadastrarDesabilit.png")));

			telaCadastroProdutos.getBtCadastrarProduto().setEnabled(false);
			telaCadastroProdutos.getTfNomeProduto().setEnabled(false);
			telaCadastroProdutos.getTfCodigoProduto().setEnabled(false);
			telaCadastroProdutos.getCbxCategoriaProduto().setEnabled(false);
			telaCadastroProdutos.getTfDataFabricacao().setEnabled(false);
			telaCadastroProdutos.getTfDataValidade().setEnabled(false);
			telaCadastroProdutos.getTfPrecoCompra().setEnabled(false);
			telaCadastroProdutos.getTfQtdCompraProduto().setEnabled(false);
			telaCadastroProdutos.getTfQtdCompraProduto().setEnabled(false);
			telaCadastroProdutos.getTfQtdEstoqueProduto().setEnabled(false);
			telaCadastroProdutos.getTfQtdMinEstoqueProduto().setEnabled(false);
			telaCadastroProdutos.getTfPrecoVendaProduto().setEnabled(false);

			telaCadastroProdutos.getBtModoEdicaoBuscar().setEnabled(true);
			telaCadastroProdutos.getTfModoEdicaoCodigo().setEnabled(true);
			telaCadastroProdutos.getExibirProdutoBuscado().setVisible(true);
		} else {
			telaCadastroProdutos.getLblBackground().setIcon(
					new ImageIcon(TelaInicial.class.getResource("/Imagens/BackgroundModoCadastroProduto.png")));
			telaCadastroProdutos.getBtModoEdicaoBuscar()
					.setIcon(new ImageIcon(TelaInicial.class.getResource("/Imagens/btPesquisaDesabilitado.png")));
			telaCadastroProdutos.getBtCadastrarProduto().setIcon(null);

			telaCadastroProdutos.getBtCadastrarProduto().setEnabled(true);
			telaCadastroProdutos.getTfNomeProduto().setEnabled(true);
			telaCadastroProdutos.getTfCodigoProduto().setEnabled(true);
			telaCadastroProdutos.getCbxCategoriaProduto().setEnabled(true);
			telaCadastroProdutos.getTfDataFabricacao().setEnabled(true);
			telaCadastroProdutos.getTfDataValidade().setEnabled(true);
			telaCadastroProdutos.getTfPrecoCompra().setEnabled(true);
			telaCadastroProdutos.getTfQtdCompraProduto().setEnabled(true);
			telaCadastroProdutos.getTfQtdCompraProduto().setEnabled(true);
			telaCadastroProdutos.getTfQtdEstoqueProduto().setEnabled(true);
			telaCadastroProdutos.getTfQtdMinEstoqueProduto().setEnabled(true);
			telaCadastroProdutos.getTfPrecoVendaProduto().setEnabled(true);

			telaCadastroProdutos.getBtModoEdicaoBuscar().setEnabled(false);
			telaCadastroProdutos.getTfModoEdicaoCodigo().setEnabled(false);
			telaCadastroProdutos.getExibirProdutoBuscado().removeAll();
			telaCadastroProdutos.getExibirProdutoBuscado().setVisible(false);
		}
		telaCadastroProdutos.repaint();
		telaCadastroProdutos.revalidate();
	}

	public FocusListener addAcaoTfDataFabricacao() {
		if (adaptTxtDataFabricacao == null) {
			adaptTxtDataFabricacao = new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					if (telaCadastroProdutos.getTfDataFabricacao().getText().trim().isEmpty()) {
						telaCadastroProdutos.getTfDataFabricacao().setText("");
						telaCadastroProdutos.getTfDataFabricacao().setForeground(new Color(0, 0, 0));
						try {
							telaCadastroProdutos.getTfDataFabricacao()
									.setFormatterFactory(new DefaultFormatterFactory(getMascaraData()));
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
				}

				@Override
				public void focusLost(FocusEvent e) {
					telaCadastroProdutos.getTfDataFabricacao().setFormatterFactory(null);
					String dataFabricacaoText = telaCadastroProdutos.getTfDataFabricacao().getText().trim();

					if (dataFabricacaoText.isEmpty() || dataFabricacaoText.compareTo("  /  /    ") == 0
							|| apagarData(dataFabricacaoText)) {
						telaCadastroProdutos.getTfDataFabricacao().setText("");
						telaCadastroProdutos.getTfDataFabricacao().setForeground(new Color(114, 114, 114));
					}
				}
			};
		}
		return adaptTxtDataFabricacao;
	}

	public FocusListener addAcaoTfDataValidade() {
		if (adaptTxtDataValidade == null) {
			adaptTxtDataValidade = new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					if (telaCadastroProdutos.getTfDataValidade().getText().trim().isEmpty()) {
						telaCadastroProdutos.getTfDataValidade().setText("");
						telaCadastroProdutos.getTfDataValidade().setForeground(new Color(0, 0, 0));
						try {
							telaCadastroProdutos.getTfDataValidade()
									.setFormatterFactory(new DefaultFormatterFactory(getMascaraData()));
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					}
				}

				@Override
				public void focusLost(FocusEvent e) {
					telaCadastroProdutos.getTfDataValidade().setFormatterFactory(null);
					String dataValidadeText = telaCadastroProdutos.getTfDataValidade().getText().trim();

					if (dataValidadeText.isEmpty() || dataValidadeText.compareTo("  /  /    ") == 0
							|| apagarData(dataValidadeText)) {
						telaCadastroProdutos.getTfDataValidade().setText("");
						telaCadastroProdutos.getTfDataValidade().setForeground(new Color(114, 114, 114));
					}
				}
			};
		}
		return adaptTxtDataValidade;
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

	private void somTroca() {
		try {
			URL url = App.class.getResource("/Audio/switch.wav");
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

	private MaskFormatter getMascaraData() throws ParseException {
		MaskFormatter mascara = new MaskFormatter("##/##/####");
		mascara.setPlaceholderCharacter('_');
		return mascara;
	}
}
