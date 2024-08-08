package Visao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.MaskFormatter;

public class TelaCadastroProdutos extends JPanel {

	private static final long serialVersionUID = 1L;

	private JLabel lblBackground;
	private JButton btTrocarModo;

	private int auxTrocaTela;
	private JTextField tfNomeProduto;
	private JTextField tfCodigoProduto;
	private JComboBox<String> cbxCategoriaProduto;
	private JFormattedTextField tfDataFabricacao;
	private JFormattedTextField tfDataValidade;
	private JTextField tfPrecoCompra;
	private JTextField tfQtdCompraProduto;
	private JTextField tfQtdEstoqueProduto;
	private JTextField tfQtdMinEstoqueProduto;
	private JTextField tfPrecoVendaProduto;
	private JButton btCadastrarProduto;
	private JTextField tfModoEdicaoCodigo;
	private JButton btModoEdicaoBuscar;
	private JPanel ExibirProdutoBuscado;
	private JLabel lblAlerta;

	public TelaCadastroProdutos() {
		this.setBounds(0, 0, 1080, 780);
		auxTrocaTela = 1;
		addItens();
		this.setVisible(true);
	}

	private void addItens() {
		setLayout(null);
		add(getLblAlerta());
		add(getExibirProdutoBuscado());
		add(getBtModoEdicaoBuscar());
		add(getTfModoEdicaoCodigo());
		add(getBtCadastrarProduto());
		add(getTfPrecoVendaProduto());
		add(getTfQtdMinEstoqueProduto());
		add(getTfQtdEstoqueProduto());
		add(getTfQtdCompraProduto());
		add(getTfPrecoCompra());
		add(getTfDataValidade());
		add(getTfDataFabricacao());
		add(getCbxCategoriaProduto());
		add(getTfCodigoProduto());
		add(getTfNomeProduto());
		add(getBtTrocarModo());
		add(getLblBackground());
	}

	public JLabel getLblBackground() {
		if (lblBackground == null) {
			lblBackground = new JLabel();
			lblBackground.setBounds(0, 0, 1080, 780);
			lblBackground.setIcon(
					new ImageIcon(TelaInicial.class.getResource("/Imagens/BackgroundModoCadastroProduto.png")));
		}
		return lblBackground;
	}

	public JButton getBtTrocarModo() {
		if (btTrocarModo == null) {
			btTrocarModo = new JButton("");
			btTrocarModo.setBounds(526, 561, 58, 57);
			btTrocarModo.setBorder(null);
			btTrocarModo.setContentAreaFilled(false);
			btTrocarModo.setFocusPainted(false);
		}
		return btTrocarModo;
	}

	public int getAuxTrocaTela() {
		return auxTrocaTela;
	}

	public JTextField getTfNomeProduto() {
		if (tfNomeProduto == null) {
			tfNomeProduto = new JTextField();
			tfNomeProduto.setFont(new Font("Dialog", Font.BOLD, 15));
			tfNomeProduto.setBounds(32, 221, 501, 35);
			tfNomeProduto.setColumns(10);
		}
		return tfNomeProduto;
	}

	public JTextField getTfCodigoProduto() {
		if (tfCodigoProduto == null) {
			tfCodigoProduto = new JTextField();
			tfCodigoProduto.setFont(new Font("Dialog", Font.BOLD, 15));
			tfCodigoProduto.setBounds(32, 301, 207, 35);
			tfCodigoProduto.setColumns(10);
		}
		return tfCodigoProduto;
	}

	public JComboBox<String> getCbxCategoriaProduto() {
		if (cbxCategoriaProduto == null) {
			cbxCategoriaProduto = new JComboBox<String>();
			cbxCategoriaProduto.setFont(new Font("Dialog", Font.BOLD, 15));
			cbxCategoriaProduto.setBounds(269, 301, 264, 35);
			cbxCategoriaProduto.addItem("Padaria");
			cbxCategoriaProduto.addItem("Carnes e Aves");
			cbxCategoriaProduto.addItem("Frutos do Mar");
			cbxCategoriaProduto.addItem("Frutas e Vegetais");
			cbxCategoriaProduto.addItem("Laticínios");
			cbxCategoriaProduto.addItem("Congelados");
			cbxCategoriaProduto.addItem("Mercearia");
			cbxCategoriaProduto.addItem("Bebidas");
			cbxCategoriaProduto.addItem("Limpeza");
			cbxCategoriaProduto.addItem("Cosméticos");
			cbxCategoriaProduto.addItem("Higiene Pessoal");
			cbxCategoriaProduto.addItem("Bebês e Crianças");
			cbxCategoriaProduto.addItem("Higiene Pet");
			cbxCategoriaProduto.addItem("Bem-Estar e Saúde");
			cbxCategoriaProduto.addItem("Queijos Especiais");
			cbxCategoriaProduto.addItem("Confeitaria");
			cbxCategoriaProduto.addItem("Tabacaria");
		}
		return cbxCategoriaProduto;
	}

	public JFormattedTextField getTfDataFabricacao() {
		if (tfDataFabricacao == null) {
			tfDataFabricacao = new JFormattedTextField();
			tfDataFabricacao.setFont(new Font("Dialog", Font.BOLD, 20));
			tfDataFabricacao.setBounds(35, 379, 119, 35);
			tfDataFabricacao.setColumns(10);
			
			// Adiciona um DocumentListener ao documento do TextField
			tfDataFabricacao.getDocument().addDocumentListener(new DocumentListener() {
	            @Override
	            public void insertUpdate(DocumentEvent e) {
	                verificaTextoVazio();
	            }

	            @Override
	            public void removeUpdate(DocumentEvent e) {
	                verificaTextoVazio();
	            }

	            @Override
	            public void changedUpdate(DocumentEvent e) {
	                verificaTextoVazio();
	            }

	            private void verificaTextoVazio() {
	                SwingUtilities.invokeLater(() -> {
	                    String text = tfDataFabricacao.getText().trim();
	                    if (text.compareTo("") != 0 && text.length() < 10) {
	                        try {
	                        	tfDataFabricacao.setFormatterFactory(new DefaultFormatterFactory(getMascaraDataNasc()));
	                        } catch (ParseException e1) {
	                            e1.printStackTrace();
	                        }
	                    }
	                });
	            }

	            private MaskFormatter getMascaraDataNasc() throws ParseException {
	                MaskFormatter mf = new MaskFormatter("##/##/####");
	                mf.setPlaceholderCharacter('_');
	                return mf;
	            }
	        });
		}
		return tfDataFabricacao;
	}

	public JFormattedTextField getTfDataValidade() {
		if (tfDataValidade == null) {
			tfDataValidade = new JFormattedTextField();
			tfDataValidade.setFont(new Font("Dialog", Font.BOLD, 20));
			tfDataValidade.setBounds(269, 379, 119, 35);
			tfDataValidade.setColumns(10);
			
			// Adiciona um DocumentListener ao documento do TextField
			tfDataValidade.getDocument().addDocumentListener(new DocumentListener() {
	            @Override
	            public void insertUpdate(DocumentEvent e) {
	                verificaTextoVazio();
	            }

	            @Override
	            public void removeUpdate(DocumentEvent e) {
	                verificaTextoVazio();
	            }

	            @Override
	            public void changedUpdate(DocumentEvent e) {
	                verificaTextoVazio();
	            }

	            private void verificaTextoVazio() {
	                SwingUtilities.invokeLater(() -> {
	                    String text = tfDataValidade.getText().trim();
	                    if (text.compareTo("") != 0 && text.length() < 10) {
	                        try {
	                        	tfDataValidade.setFormatterFactory(new DefaultFormatterFactory(getMascaraDataNasc()));
	                        } catch (ParseException e1) {
	                            e1.printStackTrace();
	                        }
	                    }
	                });
	            }

	            private MaskFormatter getMascaraDataNasc() throws ParseException {
	                MaskFormatter mf = new MaskFormatter("##/##/####");
	                mf.setPlaceholderCharacter('_');
	                return mf;
	            }
	        });
		}
		return tfDataValidade;
	}

	public JTextField getTfPrecoCompra() {
		if (tfPrecoCompra == null) {
			tfPrecoCompra = new JTextField();
			tfPrecoCompra.setFont(new Font("Dialog", Font.BOLD, 15));
			tfPrecoCompra.setBounds(32, 464, 207, 35);
			tfPrecoCompra.setColumns(10);

			tfPrecoCompra.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					if (verificarNumero(tfPrecoCompra.getText())) {
						formatarPrecoCompra();
					}
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (verificarNumero(tfPrecoCompra.getText())) {
						formatarPrecoCompra();
					}
				}
			});
			applyNumericFilter(tfPrecoCompra.getDocument());

		}
		return tfPrecoCompra;
	}

	public JTextField getTfQtdCompraProduto() {
		if (tfQtdCompraProduto == null) {
			tfQtdCompraProduto = new JTextField();
			tfQtdCompraProduto.setFont(new Font("Dialog", Font.BOLD, 15));
			tfQtdCompraProduto.setBounds(269, 463, 264, 35);
			tfQtdCompraProduto.setColumns(10);

			tfQtdCompraProduto.addKeyListener(new KeyAdapter() {
	            @Override
	            public void keyTyped(KeyEvent e) {
	                char c = e.getKeyChar();
	                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
	                    e.consume();
	                }
	            }
	        });
		}
		return tfQtdCompraProduto;
	}

	public JTextField getTfQtdEstoqueProduto() {
		if (tfQtdEstoqueProduto == null) {
			tfQtdEstoqueProduto = new JTextField();
			tfQtdEstoqueProduto.setFont(new Font("Dialog", Font.BOLD, 15));
			tfQtdEstoqueProduto.setBounds(34, 548, 181, 35);
			tfQtdEstoqueProduto.setColumns(10);
			// Adiciona um DocumentFilter personalizado para permitir apenas números
			// inteiros
			tfQtdEstoqueProduto.addKeyListener(new KeyAdapter() {
	            @Override
	            public void keyTyped(KeyEvent e) {
	                char c = e.getKeyChar();
	                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
	                    e.consume();
	                }
	            }
	        });
		}
		return tfQtdEstoqueProduto;
	}

	public JTextField getTfQtdMinEstoqueProduto() {
		if (tfQtdMinEstoqueProduto == null) {
			tfQtdMinEstoqueProduto = new JTextField();
			tfQtdMinEstoqueProduto.setFont(new Font("Dialog", Font.BOLD, 15));
			tfQtdMinEstoqueProduto.setBounds(250, 548, 253, 35);
			tfQtdMinEstoqueProduto.setColumns(10);
			
			tfQtdMinEstoqueProduto.addKeyListener(new KeyAdapter() {
	            @Override
	            public void keyTyped(KeyEvent e) {
	                char c = e.getKeyChar();
	                if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
	                    e.consume();
	                }
	            }
	        });
		}
		return tfQtdMinEstoqueProduto;
	}

	public JTextField getTfPrecoVendaProduto() {
		if (tfPrecoVendaProduto == null) {
			tfPrecoVendaProduto = new JTextField();
			tfPrecoVendaProduto.setFont(new Font("Dialog", Font.BOLD, 15));
			tfPrecoVendaProduto.setBounds(32, 632, 207, 35);
			tfPrecoVendaProduto.setColumns(10);

			tfPrecoVendaProduto.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					if (verificarNumero(tfPrecoVendaProduto.getText())) {
						formatarPrecoVenda();
					}
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (verificarNumero(tfPrecoVendaProduto.getText())) {
						formatarPrecoVenda();
					}
				}
			});
			applyNumericFilter(tfPrecoVendaProduto.getDocument());
		}
		return tfPrecoVendaProduto;
	}

	public JButton getBtCadastrarProduto() {
		if (btCadastrarProduto == null) {
			btCadastrarProduto = new JButton("");
			btCadastrarProduto.setBounds(269, 605, 237, 66);
			btCadastrarProduto.setBorder(null);
			btCadastrarProduto.setContentAreaFilled(false);
			btCadastrarProduto.setFocusPainted(false);
		}
		return btCadastrarProduto;
	}

	public JTextField getTfModoEdicaoCodigo() {
		if (tfModoEdicaoCodigo == null) {
			tfModoEdicaoCodigo = new JTextField();
			tfModoEdicaoCodigo.setFont(new Font("Dialog", Font.BOLD, 15));
			tfModoEdicaoCodigo.setBounds(590, 221, 395, 40);
			tfModoEdicaoCodigo.setColumns(10);
			tfModoEdicaoCodigo.setEnabled(false);
		}
		return tfModoEdicaoCodigo;
	}

	public JButton getBtModoEdicaoBuscar() {
		if (btModoEdicaoBuscar == null) {
			btModoEdicaoBuscar = new JButton("");
			btModoEdicaoBuscar.setIcon(
					new ImageIcon(TelaCadastroProdutos.class.getResource("/Imagens/btPesquisaDesabilitado.png")));
			btModoEdicaoBuscar.setBounds(994, 209, 52, 52);
			btModoEdicaoBuscar.setBorder(null);
			btModoEdicaoBuscar.setContentAreaFilled(false);
			btModoEdicaoBuscar.setFocusPainted(false);
			btModoEdicaoBuscar.setEnabled(false);
		}
		return btModoEdicaoBuscar;
	}

	public JPanel getExibirProdutoBuscado() {
		if (ExibirProdutoBuscado == null) {
			ExibirProdutoBuscado = new JPanel();
			ExibirProdutoBuscado.setLayout(new BorderLayout());
			ExibirProdutoBuscado.setBounds(589, 279, 450, 470);
		}
		return ExibirProdutoBuscado;
	}

	public void setExibirProdutoBuscado(JPanel exibirProdutoBuscado) {
		this.ExibirProdutoBuscado.add(exibirProdutoBuscado, BorderLayout.CENTER);
	}

	public JLabel getLblAlerta() {
		if (lblAlerta == null) {
			lblAlerta = new JLabel("");
			lblAlerta.setVisible(false);
			lblAlerta.setForeground(new Color(255, 0, 0));
			lblAlerta.setFont(new Font("DialogInput", Font.BOLD, 11));
			lblAlerta.setHorizontalAlignment(SwingConstants.CENTER);
			lblAlerta.setBounds(32, 711, 501, 14);
		}
		return lblAlerta;
	}

	public void formatarPrecoCompra() {
		String texto = tfPrecoCompra.getText();
		if (!texto.isEmpty()) {
			// Remove caracteres não numéricos, exceto vírgula e ponto
			texto = texto.replaceAll("[^\\d,.]", "");
			DecimalFormat formatador = new DecimalFormat("#,##0.00");
			try {
				Number number = formatador.parse(texto);
				String valorFormatado = formatador.format(number);
				tfPrecoCompra.setText(valorFormatado);
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void formatarPrecoVenda() {
		String texto = tfPrecoVendaProduto.getText();
		if (!texto.isEmpty()) {
			// Remove caracteres não numéricos, exceto vírgula e ponto
			texto = texto.replaceAll("[^\\d,.]", "");
			DecimalFormat formatador = new DecimalFormat("#,##0.00");
			try {
				Number number = formatador.parse(texto);
				String valorFormatado = formatador.format(number);
				tfPrecoVendaProduto.setText(valorFormatado);
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
		}
	}

	public boolean verificarNumero(String texto) {
		// Expressão regular para verificar se o texto representa um número double com
		// ponto ou vírgula seguido de decimais
		String regex = "^\\d+(\\.\\d+)?(,\\d+)?$";
		return texto.matches(regex);
	}

	// Método para aplicar o DocumentFilter personalizado
	private void applyNumericFilter(Document document) {
		((AbstractDocument) document).setDocumentFilter(new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
					throws BadLocationException {
				if (string.matches("[0-9,.]+")) {
					super.insertString(fb, offset, string, attr);
				}
			}

			@Override
			public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				if (text.matches("[0-9,.]+")) {
					super.replace(fb, offset, length, text, attrs);
				}
			}
		});
	}

}
